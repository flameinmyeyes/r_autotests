package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.sf.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_04_07_06 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_06/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/pavilion/Test_04_07_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_06_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String baseURI = "https://lk.t.exportcenter.ru/bpmn/swagger-ui/";
    public String requestNumber;
    public String processID;
//    public String processID = "вписываем processid вручную";
    public String token;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 06 Годовой отчет экспортера")
    @Link(name = "Test_04_07_06", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170242979")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
//        requestNumber = "S/2022/293491";
        precondition();
        step01();
        step02();
        step03();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Предусловия")
    public void precondition() throws InterruptedException, AWTException {
        Test_04_07_03 test_04_07_03 = new Test_04_07_03();
        test_04_07_03.steps();
        requestNumber = test_04_07_03.requestNumber;
    }

    @Step("Авторизация в Swagger")
    public void step01() {
        CommonFunctions.printStep();

//        token = RESTFunctions.getAccessToken("bpmn_admin");
//        System.out.println(token);

        token = RESTFunctions.getAccessToken("https://lk.t.exportcenter.ru/", "bpmn_admin");
        System.out.println("token: " + token);

        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        System.out.println("processID: " + processID);
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);
    }

    @Step("Отправка JSON-запроса в Swagger")
    public void step02() {
        CommonFunctions.printStep();

        JSONObject correlationKeys = new JSONObject();
        JSONObject localCorrelationKeys = new JSONObject();
        JSONObject processVariables = new JSONObject();

        JSONObject requestBody = new JSONObject();
        requestBody.put("all", true);
        requestBody.put("correlationKeys", correlationKeys);
        requestBody.put("localCorrelationKeys", localCorrelationKeys);
        requestBody.put("messageName", "StartReportClientYearMessage");
        requestBody.put("processInstanceId", processID);
        requestBody.put("processVariables", processVariables);
        requestBody.put("camundaId", "camunda-exp-search");

        String response = RestAssured
                .given()
                .baseUri(baseURI)
                .basePath("/bpmn/api/v1/bpmn/process-instance/" + processID + "/message")
                .header("accept", "*/*")
                .header("camundaId", "camunda-exp-search")
                .header("Authorization", token)
//                    .header("Content-Type", "application/json")
                .header("Content-Type", ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().response().jsonPath().prettify();

        System.out.println("response: " + response);

    }
    @Step("")
    public void step03() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=pavilion&serviceId=a546931c-0eb9-4545-853a-8a683c0924f7&next_query=true");
        new GUIFunctions()
                .authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        System.out.println($x("//div[text()='Номер заявки']/following-sibling::div").getText());

        new GUIFunctions().refreshTab("//*[text()='Продолжить']", 10);

        processID = CommonFunctions.getProcessIDFromURL();
        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Страна нахождения павильона']")
                .inContainer("Сведения о демонстрационно-дегустационном павильоне")
                .inField("Страна нахождения павильона").selectValue(P.getProperty("Авторизация.Страна нахождения павильона")).assertValue()
                .waitForLoading();
        new GUIFunctions().clickButton("Далее")
                .waitForLoading();
    }
}

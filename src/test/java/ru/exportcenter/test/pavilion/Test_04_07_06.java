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
import net.sf.json.JSONObject;
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
    public String requestNumber;
    public String processID;
    public String docUUID;
    public String token;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 06 Годовой отчет экспортера")
    @Link(name = "Test_04_07_06", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302431")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        requestNumber = "S/2022/293491";
//        precondition();
        step01();
        step02();
//        step03();
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

        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        System.out.println(processID);
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);
    }

    @Step("")
    public void step01() {
        CommonFunctions.printStep();

        token = RESTFunctions.getAccessToken("bpmn_admin");
        System.out.println(token);
    }

    @Step("Отправка JSON-запроса в Swagger")
    public void step02() {
        CommonFunctions.printStep();

        docUUID = RESTFunctions.getOrderID(processID);
        System.out.println(docUUID);

        JSONObject systemProp = new JSONObject();
        systemProp.put("applicationId", docUUID);
        systemProp.put("camundaId", "camunda-exp-search");
        systemProp.put("processInstanceId", processID);
        systemProp.put("status", "cost_calculation");

        JSONObject requestBody = new JSONObject();
        requestBody.put("systemProp", systemProp);

        RestAssured
                .given()
                .baseUri("https://lk.t.exportcenter.ru")
                .basePath("/bpmn/api/v1/bpmn/process-instance/{id}/message")
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(requestBody)
                .when()
                .post()
                .then()
                .assertThat().statusCode(200);
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

package ru.exportcenter.test.agroexpress.Test_03_07_03_1;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class Test_03_07_03_1_20 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_03_1/Test_03_07_03_1_20/";
    public String WAY_TEST_FIRST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_03_1/Test_03_07_03_1_10/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_03_1_20_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String docUUID;

    @Owner(value = "Ворожко Александр")
    @Description("03 07 03.1.20 Получение результатов верификации от АО \"РЖД Логистика\"")
    @Link(name = "Test_03_07_03_1_20", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123870742")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Предусловия")
    public void precondition() {
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_FIRST + "processID.txt");
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

        if (!status.equals("Проводится проверка")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_03_1_10 test_03_07_03_1_10 = new Test_03_07_03_1_10();
            test_03_07_03_1_10.steps();
            CommonFunctions.wait(20);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_FIRST + "processID.txt");
        }
    }

    @Step("Авторизация в Swagger")
    public void step01() {
        CommonFunctions.printStep();
    }

    @Step("Отправка JSON-запроса в Swagger")
    public void step02() {
        CommonFunctions.printStep();
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_FIRST + "processID.txt");
        docUUID = RESTFunctions.getOrderID(processID);

        String token = RESTFunctions.getAccessToken("bpmn_admin");

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
                .basePath("/agroexpress-adapter/api/v1/response/order-status")
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(requestBody)
                .when()
                .post()
                .then()
                .assertThat().statusCode(200);
    }

    @Step("Открыть заявку и проверить статус")
    public void step03() {
        CommonFunctions.printStep();
        CommonFunctions.wait(20);

        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), PROPERTIES.getProperty("Авторизация.Код"))
                .waitForLoading()
                .closeAllPopupWindows();
    }

    @Step("Навигация в ЕЛК")
    public void step04() {
        CommonFunctions.printStep();
        open("https://lk.t.exportcenter.ru/ru/services/drafts/info/" + processID);
        new GUIFunctions().waitForElementDisplayed("//*[text() = 'Расчёт стоимости']");

        JupyterLabIntegration.uploadTextContent(docUUID, WAY_TEST, "docUUID.txt");
    }
}

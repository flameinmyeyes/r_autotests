package ru.exportcenter.test.agroexpress.Test_03_07_02_1_new;

import com.google.gson.JsonObject;
import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.file.JSONHandler;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.agroexpress.HooksTEST_agroexpress;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02_1_110 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_110/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_100/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_110_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String token;
    private String docUUID;

    @Owner(value="Ворожко Александр")
    @Description("03 07 02.1.110 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_110", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123882163")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
//        precondition();
        step01();
        step02();
        step03();
//        step04();
        step05();
//        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Предусловия")
    public void precondition() {
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

        if(!status.equals("Оказание услуги")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_02_1_100 test_03_07_02_1_100 = new Test_03_07_02_1_100();
            test_03_07_02_1_100.steps();
            CommonFunctions.wait(20);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        }
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        token = RESTFunctions.getAccessToken("bpmn_admin");
    }

    @Step("Навигация и отправка JSON-запроса в Swagger")
    public void step02() {
        CommonFunctions.printStep();
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        docUUID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "docUUID.txt");
        System.out.println("orderID: " + docUUID);

        String jsonContent = JupyterLabIntegration.getFileContent(WAY_TEST + "Операция 8 (код 1010).json");
        JsonObject jsonObject = JSONHandler.parseJSONfromString(jsonContent);

        JsonObject systemProp = jsonObject.get("systemProp").getAsJsonObject();
        systemProp.addProperty("applicationId", docUUID);
        systemProp.addProperty("processInstanceId", processID);
        System.out.println(jsonObject);

        RestAssured
                .given()
                        .baseUri("https://lk.t.exportcenter.ru")
                        .basePath("/agroexpress-adapter/api/v1/response/location-status")
                        .header("accept", "*/*")
                        .header("Content-Type", "application/json")
                        .header("Authorization", token)
                        .body(String.valueOf(jsonObject))
                .when()
                        .post()
                .then()
                        .assertThat().statusCode(200);
    }

//    @Step("Авторизация")
//    public void step03() {
//        CommonFunctions.printStep();
//        new GUIFunctions()
//                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), PROPERTIES.getProperty("Авторизация.Код"))
//                .waitForLoading()
//                .closeAllPopupWindows();
//
//        open("https://lk.t.exportcenter.ru/ru/services/drafts/info/" + processID);
////        switchTo().alert().accept();
//    }

    @Step("Навигация и отправка JSON-запроса в Swagger")
    public void step03() {
        CommonFunctions.printStep();
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        docUUID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "docUUID.txt");
        System.out.println("orderID: " + docUUID);

        String jsonContent = JupyterLabIntegration.getFileContent(WAY_TEST + "Операция 8 (код 1011).json");
        JsonObject jsonObject = JSONHandler.parseJSONfromString(jsonContent);

        JsonObject systemProp = jsonObject.get("systemProp").getAsJsonObject();
        systemProp.addProperty("applicationId", docUUID);
        systemProp.addProperty("processInstanceId", processID);
        System.out.println(jsonObject);

        RestAssured
                .given()
                .baseUri("https://lk.t.exportcenter.ru")
                .basePath("/agroexpress-adapter/api/v1/response/location-status")
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(String.valueOf(jsonObject))
                .when()
                .post()
                .then()
                .assertThat().statusCode(200);
    }

    @Step("Навигация")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions().waitForElementDisplayed("//div[text()='Статус']/following-sibling::div[text()='Оказание услуги']");
        refreshTab("//*[contains(text(), 'Продолжить')]", 20);

        new GUIFunctions()
                .closeAllPopupWindows()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text() = 'Осуществляется перевозка']");
    }

    @Step("Навигация и отправка JSON-запроса в Swagger")
    public void step05() {
        CommonFunctions.printStep();

        String jsonContent = JupyterLabIntegration.getFileContent(WAY_TEST + "Операция 8 (код 1015).json");
        JsonObject jsonObject = JSONHandler.parseJSONfromString(jsonContent);

        JsonObject systemProp = jsonObject.get("systemProp").getAsJsonObject();
        systemProp.addProperty("applicationId", docUUID);
        systemProp.addProperty("processInstanceId", processID);
        System.out.println(jsonObject);

        RestAssured
                .given()
                        .baseUri("https://lk.t.exportcenter.ru")
                        .basePath("/agroexpress-adapter/api/v1/response/location-status")
                        .header("accept", "*/*")
                        .header("Content-Type", "application/json")
                        .header("Authorization", token)
                        .body(String.valueOf(jsonObject))
                .when()
                        .post()
                .then()
                        .assertThat().statusCode(200);
    }

    @Step("Навигация")
    public void step06() {
        CommonFunctions.printStep();
        open("https://lk.t.exportcenter.ru/ru/services/drafts/info/" + processID);
        switchTo().alert().accept();

        new GUIFunctions().waitForElementDisplayed("//div[text()='Статус']/following-sibling::div[text()='Оказание услуги']");
        refreshTab("//*[contains(text(), 'Продолжить')]", 20);

        new GUIFunctions()
                .closeAllPopupWindows()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text() = 'Перевозка завершена']");

        JupyterLabIntegration.uploadTextContent(docUUID, WAY_TEST,"docUUID.txt");
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }



}

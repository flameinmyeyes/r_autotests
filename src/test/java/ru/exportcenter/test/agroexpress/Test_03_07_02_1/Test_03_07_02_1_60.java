package ru.exportcenter.test.agroexpress.Test_03_07_02_1;

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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.agroexpress.HooksTEST_agroexpress;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02_1_60 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_60/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_40/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_60_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String token;
    private String docUUID;

    @Owner(value="Ворожко Александр")
    @Description("03 07 02.1.60 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_60", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123882167")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();
        step03();
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

        if(!status.equals("Формирование счёта")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_02_1_50 test_03_07_02_1_50 = new Test_03_07_02_1_50();
            test_03_07_02_1_50.steps();
            CommonFunctions.wait(10);
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

        String jsonContent = JupyterLabIntegration.getFileContent(WAY_TEST + "Операция 6.json");
        JsonObject jsonObject = JSONHandler.parseJSONfromString(jsonContent);

        JsonObject systemProp = jsonObject.get("systemProp").getAsJsonObject();
        systemProp.addProperty("applicationId", docUUID);
        systemProp.addProperty("processInstanceId", processID);
        System.out.println(jsonObject);

        RestAssured
                .given()
                    .baseUri("https://lk.t.exportcenter.ru")
                    .basePath("/agroexpress-adapter/api/v1/response/download-count")
                    .header("accept", "*/*")
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .body(String.valueOf(jsonObject))
                .when()
                    .post()
                .then()
                    .assertThat().statusCode(200);
    }

    @Step("Авторизация")
    public void step03() {
        CommonFunctions.printStep();
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);
        Assert.assertEquals(status, "Оплата счёта");

        JupyterLabIntegration.uploadTextContent(docUUID, WAY_TEST,"docUUID.txt");
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

}
package ru.exportcenter.test.agroexpress;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.file.FileFunctions;
import functions.file.JSONHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_03_07_02_1_40 extends HooksTEST {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_40/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_10/";
    private String processID;
    private String token;
    private String orderID;
    private String cargoID;

    @Owner(value="Балашов Илья, Ворожко Александр")
    @Description("03 07 02.1.40 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_40", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123872990")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        preconditions();
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
    public void preconditions() {
        Test_03_07_02_1_10 previous_test = new Test_03_07_02_1_10();
        previous_test.steps();
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        System.out.println("processID: " + processID);
    }

    @Step("Авторизация")
    public void step01() {
        token = RESTFunctions.getAccessToken();
    }

    @Step("Навигация и отправка JSON-запроса в Swagger")
    public void step02() {
        CommonFunctions.printStep();

        orderID = RESTFunctions.getOrderID(processID);
        System.out.println("orderID: " + orderID);

        cargoID = RESTFunctions.getCargoID(processID);
        System.out.println("cargoID: " + cargoID);

        String jsonContent = JupyterLabIntegration.getFileContent(WAY_TEST + "Операция 3.json");
        JsonObject jsonObject = JSONHandler.parseJSONfromString(jsonContent);
        jsonObject.addProperty("applicationId", orderID);
        jsonObject.addProperty("processInstanceId", processID);
        jsonObject.addProperty("cargoId", cargoID);
        System.out.println(jsonObject);

        RestAssured
                .given()
                        .baseUri("https://lk.t.exportcenter.ru")
                        .basePath("/agroexpress-adapter/api/v1/response/order-status")
                        .header("accept", "*/*")
                        .header("Content-Type", "application/json")
                        .header("Authorization", token)
                        .body(jsonObject)
                .when()
                        .post()
                .then()
                        .assertThat().statusCode(200);
    }

    @Step("Авторизация")
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions().authorization("test-otr@yandex.ru", "Password1!", "1234");
        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step04() {
        CommonFunctions.printStep();

    }

}

package ru.exportcenter.test.agroexpress;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;

public class Test_03_07_02_1_40 extends HooksTEST {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_40/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_10/";
    private String processID;
    private String docUUID;

    @Owner(value="Балашов Илья")
    @Description("03 07 02.1.40 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_40", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123872990")

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
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        RESTFunctions.getOrderStatus(processID);

        if (!processID.equals("Черновик")) {
            System.out.println("Перепрогон предыдущего теста");
            Test_03_07_02_1_40 previous_test = new Test_03_07_02_1_40();
            previous_test.steps();
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        }
    }

    @Step("Отправка JSON-запроса в Swagger")
    public void step01() {
        CommonFunctions.printStep();
        docUUID = RESTFunctions.getOrderID(processID);

        String token = RESTFunctions.getAccessToken();

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

    @Step("Навигация и отправка JSON-запроса в Swagger")
    public void step02() {
        CommonFunctions.printStep();
        Assert.assertEquals(RESTFunctions.getOrderStatus(processID), "Расчёт стоимости");
    }

    @Step("Шаг 3 Авторизация")
    public void step03() {
        CommonFunctions.printStep();
        Assert.assertEquals(RESTFunctions.getOrderStatus(processID), "Расчёт стоимости");
    }

    @Step("Навигация")
    public void step04() {
        CommonFunctions.printStep();
        Assert.assertEquals(RESTFunctions.getOrderStatus(processID), "Расчёт стоимости");
    }



}

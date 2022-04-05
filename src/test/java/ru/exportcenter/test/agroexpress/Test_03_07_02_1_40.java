package ru.exportcenter.test.agroexpress;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import net.sf.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_03_07_02_1_40 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_40/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_20/";
    private String processID;
    private String docUUID;

    @Owner(value="Ворожко Александр")
    @Description("03 07 02.1.40 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_40", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123872990")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
//        System.out.println(RESTFunctions.getAccessToken());
        precondition();
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Предусловия")
    public void precondition() {
//        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
//
//        if (!$x("//*[text() = 'Проводится проверка']").isDisplayed()) {
//            System.out.println("Перепрогон предыдущего теста");
            Test_03_07_02_1_10 previous_test = new Test_03_07_02_1_10();
            previous_test.steps();
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
//        }
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

    @Step("Открыть заявку и проверить статус")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("test-otr@yandex.ru")
                .inField("Пароль").inputValue("Password1!")
                .clickButton("Войти");
        $x("//div[contains(@class, 'CodeInput_input' )]/input[@data-id= '0']").sendKeys("1");
        $x("//div[contains(@class, 'CodeInput_input' )]/input[@data-id= '1']").sendKeys("2");
        $x("//div[contains(@class, 'CodeInput_input' )]/input[@data-id= '2']").sendKeys("3");
        $x("//div[contains(@class, 'CodeInput_input' )]/input[@data-id= '3']").sendKeys("4");
        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/main");

        open("https://lk.t.exportcenter.ru/ru/services/drafts/info/" + processID);
        new GUIFunctions().waitForElementDisplayed("//*[text() = 'Расчёт стоимости']");

        JupyterLabIntegration.uploadTextContent(docUUID,WAY_TEST,"docUUID.txt");
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }
}

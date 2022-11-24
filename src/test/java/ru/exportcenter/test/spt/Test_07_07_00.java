package ru.exportcenter.test.spt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import io.restassured.config.ObjectMapperConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class Test_07_07_00 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_00/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_00_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    public String requestNumber = "";
    public String processID = "";
    public String docUUID = "";
    public String token = "";
    public String smevFlag = "";
    public boolean isNegative = false;

    public String url = "", login = "", password = "", code="", forma = "";

    @Owner(value = "Андрей В. Теребков")
    @Description("07 07 00 Заполнение Заявки на получение услуги, подписание Заявки УКЭП и автоматическая передача Заявки на верификацию")
    @Link(name = "Test_07_07_00", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302431")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps(){
        step01();
        step02();
        Swagger(smevFlag, isNegative);
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Отправка JSON-запроса в Swagger")
    public void Swagger(String smevFlag, boolean isNegative) {
        CommonFunctions.printStep();

        processID = CommonFunctions.getProcessIDFromURL();
        token = RESTFunctions.getAccessToken_SVT("bpmn_admin");
        docUUID = RESTFunctions.getOrderID_SVT(processID);

        System.out.println("processID = " + processID + "; docUUID = " + docUUID + "; token = " + token);
        if(isNegative) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String outerJsonString = "{\"variables\":{\"smevFlag\":{\"local\":false,\"type\":\"Object\",\"valueInfo\":{\"objectTypeName\":\"java.util.LinkedHashMap\",\"serializationDataFormat\":\"application/json\"}}}}";

            JsonObject outerJson = new JsonParser().parse(outerJsonString).getAsJsonObject();
            outerJson.addProperty("name", "CHANGEVARS-" + docUUID.replaceAll("\"", ""));
            outerJson.get("variables").getAsJsonObject().get("smevFlag").getAsJsonObject().addProperty("value", smevFlag);

            System.out.println("processID = " + processID + "; docUUID = " + docUUID + "; token = " + token + "; requestNumber = " + requestNumber);
            System.out.println(outerJson.getClass());
            System.out.println(gson.toJson(outerJson));


//            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());

                    given()
                    .baseUri("https://lk.t.exportcenter.ru/bpmn/api/v1/bpmn/execution/signal")
//                  .basePath("/bpmn/api/v1/bpmn/execution/signal")
                    .header("accept", "*/*")
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .header("camundaId", "camunda-exp-search")
                    .body(outerJson)
//                  .body(String.valueOf(outerJson))
                    .when()
                    .post()
                    .then()
                    .assertThat().statusCode(200)
            ;
        }
    }





        public static RequestSpecification given() {
            return RestAssured.given()
                    .config(RestAssured.config()
                            .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));

        }



    @Step("Авторизация»")
    public void step01() {
        CommonFunctions.printStep();

//      открыть страницу
        open(url);

//      Ввести логин и пароль
        new GUIFunctions()
            .waitForLoading()
            .authorization(login, password, code)
            .waitForElementDisplayed("//div[text()='Определение вида сертификата']")
            .waitForElementDisplayed("//body");

            requestNumber = $x("//body").getAttribute("id");

            JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");

 //       new GUIFunctions().waitForURL("");
    }


    @Step("Оформление вида сертификата")
    public void step02(){
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton(forma)
                .clickButton("Продолжить")
                .waitForLoading();

        System.out.println(forma);
    }


    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh()");
            CommonFunctions.wait(1);
        }
    }
}
package ru.exportcenter.dev.fito;

import com.google.gson.JsonObject;
import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.file.FileFunctions;
import functions.file.JSONHandler;
import functions.file.PropertiesHandler;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.agroexpress.Test_03_07_02_1.Test_03_07_02_1_10;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Swagger/* extends Hooks*/ {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/";
    public String WAY_TEST_PREVIOUS = Ways.DEV.getWay() + "/fito/Test_3_07_04/";
    private String token;
    private String processID;
    private String baseURI = "http://bpmn-api-service.bpms-dev.d.exportcenter.ru/";
//    private String baseURI = "http://uidm.uidm-dev.d.exportcenter.ru";
    private String id = "";
    private String messageName = "";
    private String fileContent = "";

    @Owner(value = "Балашов Илья")
    @Description("Метод работы со Swagger UI, загрузка XML файла")
    @Link(name = "", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183189107")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
//        step03();
    }

    @Step("Авторизация в Swagger")
    public void step01() {
        CommonFunctions.printStep();
        token = RESTFunctions.getAccessToken("http://uidm.uidm-dev.d.exportcenter.ru", "bpmn_admin");
        System.out.println("token: " + token);
    }

    private static void deleteFileIfExists(File file) {
        if(file.exists()) {
            System.out.println("файл есть");
            file.delete();
        } else {
            System.out.println("файла нет");
        }
    }

    @Step("Загрузка XML файла")
    public void step02() {
        CommonFunctions.printStep();

        String wayFile = "src/test/java/ru/exportcenter/dev/fito/file.xml";
        File file = new File(wayFile);
        deleteFileIfExists(file);

        fileContent = JupyterLabIntegration.getFileContent(WAY_TEST + "ResponseSuccess.xml");
        System.out.println("fileContent: \n" + fileContent);

        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        System.out.println("processID: " + processID);

        FileFunctions.writeValueToFile(wayFile, fileContent);

//        File file = new File("src/test/java/ru/exportcenter/dev/fito/ResponseSuccess.xml");
//        byte[] bytes = file.toString().getBytes(StandardCharsets.UTF_8);

        //не работает
//        String response = RestAssured
//                .given()
//                    .baseUri(baseURI)
//                    .basePath("/bpmn/api/v1/bpmn/process-instance/" + processID + "/attachments")
//                    .param("description", "test_description")
//                    .param("name", "test_name")
//                    .header("accept", "*/*")
//                    .header("camundaId", "camunda-exp-search")
//                    .header("Authorization", token)
//                    .header("Content-Type", "multipart/form-data") //.header("Content-Type", ContentType.MULTIPART)
////                    .multiPart("file", "ResponseSuccess.xml", fileContent.getBytes(StandardCharsets.UTF_8), String.valueOf(ContentType.XML))
//                    .multiPart("file", "ResponseSuccess.xml", fileContent, String.valueOf(ContentType.XML))
//
//                    .multiPart(/*"file",*/
//                            (
//                            new MultiPartSpecBuilder(fileContent.getBytes(StandardCharsets.UTF_8))
//                                    .fileName("ResponseSuccess.xml")
//                                    .controlName("file")
//                                    .mimeType("text/xml") //.mimeType(String.valueOf(ContentType.XML))
//                                    .build())
//                    )
//                .when()
//                    .post()
//                .then()
//                    .assertThat().statusCode(200)
//                    .extract().response().jsonPath().prettify();


        //работает с файлом на локальном ПК
//        File file = new File("src/test/java/ru/exportcenter/dev/fito/ResponseSuccess.xml");
        System.out.println(file);

        String response = RestAssured
                .given()
                    .baseUri(baseURI)
                    .basePath("/bpmn/api/v1/bpmn/process-instance/" + processID + "/attachments")
                    .param("description", "test_description")
                    .param("name", "test_name")
                    .header("accept", "*/*")
                    .header("camundaId", "camunda-exp-search")
                    .header("Authorization", token)
                    .header("Content-Type", "multipart/form-data") //.header("Content-Type", ContentType.MULTIPART)
                    .multiPart("file", file)
                .when()
                    .post()
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().prettify();

        System.out.println("response: " + response);

        JsonObject jsonObject = JSONHandler.parseJSONfromString(response);
        System.out.println("jsonObject: " + jsonObject);

        id = jsonObject.get("id").toString().replace("\"", "");
        System.out.println("id: " + id);

        deleteFileIfExists(file);
    }

    @Step("Запуск процесса")
    public void step03() {
        CommonFunctions.printStep();

        //для 1 ВС
        /*
        {
          "all": true,
          "messageName": "AccOrgContrRequestMessage",
          "processInstanceId": "",
          "camundaId": "camunda-exp-search",
          "processVariables": {
            "attachId_AccOrgContrRequestMessage": {
              "type": "string",
              "value": ""
            }
          }
        }
        */

        //для 2 ВС
        /*
        {
          "all": true,
          "messageName": "CheckAppInfRequestMessage",
          "processInstanceId": "",
          "camundaId": "camunda-exp-search",
          "processVariables": {
            "attachId_CheckAppInfRequestMessage": {
              "type": "string",
              "value": ""
            }
          }
        }
        */

        //для 3 ВС
        /*
        {
          "all": true,
          "messageName": "SendAppInfRequestMessage",
          "processInstanceId": "",
          "camundaId": "camunda-exp-search",
          "processVariables": {
            "attachId_SendAppInfRequestMessage": {
              "type": "string",
              "value": ""
            }
          }
        }
        */

        JSONObject attachId_SendAppInfRequestMessage = new JSONObject();
        attachId_SendAppInfRequestMessage.put("type", "string");
        attachId_SendAppInfRequestMessage.put("value", id);

        JSONObject processVariables = new JSONObject();
        processVariables.put("attachId_SendAppInfRequestMessage", attachId_SendAppInfRequestMessage);

        JSONObject requestBody = new JSONObject();
        requestBody.put("all", true);
        requestBody.put("messageName", messageName);
        requestBody.put("processInstanceId", processID);
        requestBody.put("camundaId", "camunda-exp-search");
        requestBody.put("processVariables", processVariables);

        System.out.println("requestBody: " + requestBody);

        RestAssured
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
                    .assertThat().statusCode(200);
    }



}

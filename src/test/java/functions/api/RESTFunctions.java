package functions.api;

import com.google.gson.JsonObject;
import framework.integration.JupyterLabIntegration;
import functions.common.Base64Encoder;
import functions.common.CommonFunctions;
import functions.file.FileFunctions;
import functions.file.JSONHandler;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RESTFunctions {

    /**
     * Отправить REST запрос (GET)
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     */
    public static String sendRESTRequest(String requestType, String requestURL) {
        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;

        if (requestType.equals("GET")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("GET");
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

//                if (!String.valueOf(con.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                /*
                //с помощью InputStream получить ответ
                final StringBuilder content = new StringBuilder();
                try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    response = content.toString();
                } catch (final Exception ex) {
//                    System.out.println("Не удалось получить ответ на REST-запрос!");
                    ex.printStackTrace();
                    return "";
                }
                 */

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }

    /**
     * Отправить REST запрос (GET) с авторизацией
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     * @param login  - логин для авторизации
     * @param password  - пароль для авторизации
     */
    public static String sendRESTRequest(String requestType, String requestURL, String login, String password) {

        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;

        if (requestType.equals("GET")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("GET");
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + Base64Encoder.encodeStringToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

//                if (!String.valueOf(httpUrlConnection.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }

    /**
     * Отправить REST запрос (PUT/POST)
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     * @param requestBody  - тело запроса
     * @param login  - логин для авторизации
     * @param password  - пароль для авторизации
     */
    public static String sendRESTRequest(String requestType, String requestURL, String requestBody, String login, String password) {

        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;
        DataOutputStream dataOutputStream = null;

        if (requestType.equals("PUT")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("PUT");
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + Base64Encoder.encodeStringToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

                //получаем исходящий поток
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                //записываем в исходящий поток тело запроса
                dataOutputStream.writeBytes(requestBody);
                //обновляем исходящий поток
                dataOutputStream.flush();

//                if (!String.valueOf(con.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                //закрываем исходящий поток
                dataOutputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }

        if (requestType.equals("POST")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + Base64Encoder.encodeStringToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

                //получаем исходящий поток
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                //записываем в исходящий поток тело запроса
                dataOutputStream.writeBytes(requestBody);
                //обновляем исходящий поток
                dataOutputStream.flush();

//                if (!String.valueOf(con.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                //закрываем исходящий поток
                dataOutputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }

    /**
     * Отправить файл через REST запрос (PUT/POST)
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     * @param file  - файл
     * @param login  - логин для авторизации
     * @param password  - пароль для авторизации
     */
    public static String sendRESTRequest(String requestType, String requestURL, File file, String login, String password) {

        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;
        DataOutputStream dataOutputStream = null;

        String filename = file.getName();

        String twoHyphens = "--";   // Define connection strings
        String boundary = "******"; // Define the delimitation string
        String end = "\r\n";    //Define end newline string

        if (requestType.equals("POST")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения:
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + Base64Encoder.encodeStringToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Accept", "*/*");
                httpUrlConnection.setRequestProperty("Connection", "keep-alive");
                httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

                //получаем исходящий поток
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);

                //записываем в исходящий поток разделитель
                dataOutputStream.writeBytes(twoHyphens + boundary + end);
                //записываем в исходящий поток параметр и имя файла
                dataOutputStream.writeBytes("Content-Disposition:form-data;name=file;filename=" + filename + end);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" + attachmentFileName + "\"" + end);
                //записываем в исходящий поток закрывающий символ
                dataOutputStream.writeBytes(end);

                //создаем входящий поток для чтения файла
                FileInputStream fileInputStream = new FileInputStream(file);
                //преобразовываем файл в байты и записываем в исходящий поток
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, length);
                }
                //после записи файла в поток, записываем в поток закрывающий символ
                dataOutputStream.writeBytes(end);

                //закрываем входящий поток
                fileInputStream.close();

                //записываем в исходящий поток разделитель
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + end);
                //обновляем исходящий поток
                dataOutputStream.flush();

                /*
                //особая уличная магия
                FileInputStream fileInputStream = new FileInputStream(file);
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                try {
                    outputStream.write(("--" + boundary + EOL +
                            "Content-Disposition: form-data; name=\"file\"; " +
                            "filename=\"" + filename + "\"" + EOL +
                            "Content-Type: application/octet-stream" + EOL + EOL)
                            .getBytes(StandardCharsets.UTF_8)
                    );
                    byte[] buffer = new byte[128];
                    int size = -1;
                    while (-1 != (size = fileInputStream.read(buffer))) {
                        outputStream.write(buffer, 0, size);
                    }
                    outputStream.write((EOL + "--" + boundary + "--" + EOL).getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                 */

//                if (!String.valueOf(httpUrlConnection.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + httpUrlConnection.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                //закрываем исходящий поток
                dataOutputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }


    public static String getAccessToken_SVT(String login) {
        String baseUri = "http://lk.t.exportcenter.ru";

        String execution = RestAssured
                .given()
                .baseUri(baseUri)
                .basePath("/sso/oauth2/access_token")
                .header("Accept", "application/json")
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("client_id", "mdm-api-service")
                .formParam("client_secret", "password")
                .formParam("realm", "/customer")
                .formParam("grant_type", "urn:roox:params:oauth:grant-type:m2m")
                .formParam("service", "dispatcher")
                .formParam("scope", "organizations currentOrgId")
                .when()
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().response().jsonPath().getString("execution");

        String response = RestAssured
                .given()
                .baseUri(baseUri)
                .basePath("/sso/oauth2/access_token")
                .header("Accept", "application/json")
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("client_id", "mdm-api-service")
                .formParam("client_secret", "password")
                .formParam("realm", "/customer")
                .formParam("grant_type", "urn:roox:params:oauth:grant-type:m2m")
                .formParam("service", "dispatcher")
                .formParam("_eventId", "next")
                .formParam("username", login)
                .formParam("password", "password")
                .formParam("scope", "organizations currentOrgId")
                .formParam("execution", execution)
                .when()
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().response().jsonPath().getString("access_token");

        String token = "Bearer sso_1.0_" + response;

        return token;
    }


    public static String getAccessToken(String login) {

//        String baseUri = "http://uidm.uidm-dev.d.exportcenter.ru";
        String baseUri = "https://lk.t.exportcenter.ru";


        String execution = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/sso/oauth2/access_token")
                    .header("Accept", "application/json")
                    .contentType("application/x-www-form-urlencoded; charset=utf-8")
                        .formParam("client_id", "rec_elk_m2m")
                        .formParam("client_secret", "password")
                        .formParam("realm", "/customer")
                        .formParam("grant_type", "urn:roox:params:oauth:grant-type:m2m")
                        .formParam("service", "dispatcher")
                .when()
                    .post()
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().getString("execution");

        String response = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/sso/oauth2/access_token")
                    .header("Accept", "application/json")
                    .contentType("application/x-www-form-urlencoded; charset=utf-8")
                        .formParam("client_id", "rec_elk_m2m")
                        .formParam("client_secret", "password")
                        .formParam("realm", "/customer")
                        .formParam("grant_type", "urn:roox:params:oauth:grant-type:m2m")
                        .formParam("service", "dispatcher")
                        .formParam("_eventId", "next")
                        .formParam("username", login)
                        .formParam("password", "password")
                        .formParam("execution", execution)
                .when()
                    .post()
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().getString("access_token");

        String token = "Bearer sso_1.0_" + response;

        return token;
    }

    public static String getAccessToken(String baseUri, String login) {

        String execution = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/sso/oauth2/access_token")
                    .header("Accept", "application/json")
                    .contentType("application/x-www-form-urlencoded; charset=utf-8")
                        .formParam("client_id", "rec_elk_m2m")
                        .formParam("client_secret", "password")
                        .formParam("realm", "/customer")
                        .formParam("grant_type", "urn:roox:params:oauth:grant-type:m2m")
                        .formParam("service", "dispatcher")
                .when()
                    .post()
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().getString("execution");

        String response = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/sso/oauth2/access_token")
                    .header("Accept", "application/json")
                    .contentType("application/x-www-form-urlencoded; charset=utf-8")
                        .formParam("client_id", "rec_elk_m2m")
                        .formParam("client_secret", "password")
                        .formParam("realm", "/customer")
                        .formParam("grant_type", "urn:roox:params:oauth:grant-type:m2m")
                        .formParam("service", "dispatcher")
                        .formParam("_eventId", "next")
                        .formParam("username", login)
                        .formParam("password", "password")
                        .formParam("execution", execution)
                .when()
                    .post()
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().getString("access_token");

        String token = "Bearer sso_1.0_" + response;

        return token;
    }

    public static String getOrderID_SVT(String processID) {

        String token = getAccessToken_SVT("bpmn_admin");

        System.out.println(token);

        String baseUri = "https://lk.t.exportcenter.ru";

        String response = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/bpmn/api/v1/bpmn/tasks/"+ processID)
                    .header("camundaId", "camunda-exp-search")
                    .header("Accept", "application/json")
                    .header("Authorization", token)
                .when()
                .get()
                .then()
//                  .assertThat().statusCode(200)
                    .extract().response().jsonPath().prettify();

        JsonObject jsonObject = JSONHandler.parseJSONfromString(response);

        String orderID = jsonObject.get("processInstanceId").toString();

        return orderID;
    }


    public static String getOrderID(String processID) {

        String token = getAccessToken("bpmn_admin");

//        String baseUri = "http://bpmn-api-service.bpms-dev.d.exportcenter.ru";
        String baseUri = "https://lk.t.exportcenter.ru/";

        String response = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/bpmn/api/v1/bpmn/history/process-instance")
//                /bpmn/api/v1/bpmn/tasks
                    .param("variableName", "mdmOrder")
                    .header("Accept", "application/json")
                    .header("camundaId", "camunda-exp-search")
                    .header("Authorization", token)
                .when()
                    .get("/" + processID + "/variables")
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().prettify();

        JsonObject jsonObject = JSONHandler.parseJSONfromString(response);

        System.out.println(response);

        String orderID = jsonObject.getAsJsonArray("content")
                .get(0).getAsJsonObject()
                .get("value").getAsJsonObject()
                .get("uuid").toString().replace("\"", "");

        return orderID;
    }

    public static String getOrderStatus(String processID) {

        String token = getAccessToken("mdm_admin");

//        String baseUri = "http://bpmn-api-service.bpms-dev.d.exportcenter.ru";
        String baseUri = "https://lk.t.exportcenter.ru/";

        JSONObject body = new JSONObject();
        body.put("processId_like", processID);

        String response = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/mdm-adapter/api/v1/catalogs/order/items/search")
                    .queryParam("hideDeprecated", "true")
                    .queryParam("showDetails", "0")
                    .queryParam("showRefs", "1")
                    .header("accept", "*/*")
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .body(body)
                .when()
                    .post()
                .then()
                    .assertThat().statusCode(200)
                .extract().response().jsonPath().prettify();

        JsonObject jsonObject = JSONHandler.parseJSONfromString(response);

        String orderStatus = jsonObject.getAsJsonArray("content")
                .get(0).getAsJsonObject()
                .get("statusCode").getAsJsonObject()
                .get("name").getAsString();

        return orderStatus;
    }

    public static String getCargoID(String processID) {

        String token = getAccessToken("bpmn_admin");

//        String baseUri = "http://bpmn-api-service.bpms-dev.d.exportcenter.ru";
        String baseUri = "https://lk.t.exportcenter.ru/";

        String response = RestAssured
                .given()
                    .baseUri(baseUri)
                    .basePath("/bpmn/api/v1/bpmn/history/process-instance")
                    .param("variableName", "goodsProTable")
                    .header("Accept", "application/json")
                    .header("camundaId", "camunda-exp-search")
                    .header("Authorization", token)
                .when()
                    .get("/" + processID + "/variables")
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().prettify();

        JsonObject jsonObject = JSONHandler.parseJSONfromString(response);

        String cargoID = jsonObject.getAsJsonArray("content")
                .get(0).getAsJsonObject()
                .get("value").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("id").toString().replace("\"", "");

        return cargoID;
    }

    public static String uploadAttachmentToProcess(String token, String baseURI, String processID, File attachmentFile) {

//        String wayFile = "src/test/java/ru/exportcenter/dev/fito/file.xml";
//        file = new File(wayFile);
//        deleteFileIfExists(file);

//        fileContent = JupyterLabIntegration.getFileContent(WAY_TEST + "ResponseSuccess.xml");
//        System.out.println("fileContent: \n" + fileContent);
//
//        FileFunctions.writeValueToFile(wayFile, fileContent);

        String attachmentID = RestAssured
                .given()
                    .baseUri(baseURI)
                    .basePath("/bpmn/api/v1/bpmn/process-instance/" + processID + "/attachments")
                    .param("description", "test_description")
                    .param("name", "test_name")
                    .header("accept", "*/*")
                    .header("camundaId", "camunda-exp-search")
                    .header("Authorization", token)
                    .header("Content-Type", "multipart/form-data") //.header("Content-Type", ContentType.MULTIPART)
                    .multiPart("file", attachmentFile)
                .when()
//                    .log().all()
                    .post()
                .then()
                    .assertThat().statusCode(200)
                    .extract().response().jsonPath().getString("id");

//                    .extract().response().jsonPath().prettify();

//        JsonObject jsonObject = JSONHandler.parseJSONfromString(response);
//        String attachmentID = jsonObject.get("id").toString().replace("\"", "");

//        deleteFileIfExists(file);

        return attachmentID;
    }

    public static String sendMessageToProcess(String token, String baseURI, String processID, JSONObject requestBody) {

        //шаблон JSON:
        /*
        {
          "all": true,
          "messageName": "",
          "processInstanceId": "",
          "camundaId": "camunda-exp-search",
          "processVariables": {
            "attachId_AccOrgContrRequestMessage": {
              "type": "string",
              "value": ""
            }
          }
        }

        //"messageName":
        //для 1 ВС "AccOrgContrRequestMessage"
        //для 2 ВС "CheckAppInfRequestMessage"
        //для 3 ВС "SendAppInfRequestMessage"

        //"processInstanceId": ID процесса

        //"attachId_":
        //для 1 ВС "attachId_AccOrgContrRequestMessage"
        //для 2 ВС "attachId_CheckAppInfRequestMessage"
        //для 3 ВС "attachId_SendAppInfRequestMessage"

        //"value": ID вложения
        */

        //строим JSON:
        /*
        JSONObject attachId_SendAppInfRequestMessage = new JSONObject();
        attachId_SendAppInfRequestMessage.put("type", "string");
        attachId_SendAppInfRequestMessage.put("value", attachmentID);

        JSONObject processVariables = new JSONObject();
        processVariables.put("attachId_SendAppInfRequestMessage", attachId_SendAppInfRequestMessage);

        JSONObject requestBody = new JSONObject();
        requestBody.put("all", true);
        requestBody.put("messageName", messageName);
        requestBody.put("processInstanceId", processID);
        requestBody.put("camundaId", "camunda-exp-search");
        requestBody.put("processVariables", processVariables);
         */

        String response = RestAssured
                .given()
                    .baseUri(baseURI)
                    .basePath("/bpmn/api/v1/bpmn/process-instance/" + processID + "/message")
                    .header("accept", "*/*")
                    .header("camundaId", "camunda-exp-search")
                    .header("Authorization", token)
                    .header("Content-Type", "application/json") //.header("Content-Type", ContentType.JSON)
                    .body(requestBody)
                .when()
                    .post()
                .then()
//                    .log().all()
                    .assertThat().statusCode(200)
                    .extract().response().toString();

        //response здесь пустой, это норма
        return response;
    }

    public static String sendAttachmentToProcess(String token, String baseURI, String processID, File attachmentFile, String messageName) {
        /**
         * 1. Загрузка XML файла
         */

        //
//        System.out.println("token: " + token);
//        System.out.println("baseURI: " + baseURI);
//        System.out.println("processID: " + processID);
//        System.out.println("attachmentFile: " + attachmentFile);
        //
        String attachmentID = uploadAttachmentToProcess(token, baseURI, processID, attachmentFile);

        /**
         * 2. Запуск процесса
         */
        //шаблон JSON:
        /*
        {
          "all": true,
          "messageName": "",
          "processInstanceId": "",
          "camundaId": "camunda-exp-search",
          "processVariables": {
            "attachId_AccOrgContrRequestMessage": {
              "type": "string",
              "value": ""
            }
          }
        }

        //"messageName":
        //для 1 ВС "AccOrgContrRequestMessage"
        //для 2 ВС "CheckAppInfRequestMessage"
        //для 3 ВС "SendAppInfRequestMessage"

        //"processInstanceId": ID процесса

        //"attachId_":
        //для 1 ВС "attachId_AccOrgContrRequestMessage"
        //для 2 ВС "attachId_CheckAppInfRequestMessage"
        //для 3 ВС "attachId_SendAppInfRequestMessage"

        //"value": ID вложения
        */

        //строим JSON:
        JSONObject attachId_SendAppInfRequestMessage = new JSONObject();
        attachId_SendAppInfRequestMessage.put("type", "string");
        attachId_SendAppInfRequestMessage.put("value", attachmentID);

        JSONObject processVariables = new JSONObject();
        processVariables.put("attachId_" + messageName, attachId_SendAppInfRequestMessage);

        JSONObject requestBody = new JSONObject();
        requestBody.put("all", true);
        requestBody.put("messageName", messageName);
        requestBody.put("processInstanceId", processID);
        requestBody.put("camundaId", "camunda-exp-search");
        requestBody.put("processVariables", processVariables);

        //
//        System.out.println("token: " + token);
//        System.out.println("baseURI: " + baseURI);
//        System.out.println("processID: " + processID);
//        System.out.println("requestBody: " + requestBody);
        //
        String response = sendMessageToProcess(token, baseURI, processID, requestBody);

        return response;
    }

}

package functions.api;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import functions.common.Base64Encoder;
import functions.common.CommonFunctions;
import io.restassured.RestAssured;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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

    public static String getAccessToken() {

        String baseUri = "http://uidm.uidm-dev.d.exportcenter.ru";

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
                        .formParam("username", "bpmn_admin")
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


}

package framework.integration;

import functions.common.Base64Encoder;
import io.restassured.RestAssured;
import net.sf.json.JSONObject;
import org.testng.Assert;

public class JupyterLabIntegration {

// ~4-5 числа каждого месяца Токен "протухает"
    static String tokenNum = "7648f69cb716fd065bf18c06586918c9dd92bae9205e2692";

    public static String getFileContent(String filePath) {
        String content = RestAssured
                .given()
                        .baseUri("http://selenoidshare.d.exportcenter.ru/")
                        .basePath("/api/contents/work")
                        .param("token", tokenNum)
                .when()
                        .get(filePath)
                .then()
                        .assertThat().statusCode(200)
                .extract().response().jsonPath().getString("content");

        return content;
    }

    public static void uploadBinaryContent(byte[] bytes, String filePath, String fileName) {
        JSONObject requestBody = new JSONObject();

        if (filePath.endsWith("/")) {
            filePath = filePath.substring(0, filePath.length()-1);
        }

        requestBody.put("type", "file");
        requestBody.put("format", "base64");
        String encodedBytes = Base64Encoder.encodeBytesToBase64(bytes);
        requestBody.put("content", encodedBytes);

        createDirectory(filePath);
        uploadContent(filePath + "/" + fileName, requestBody);
    }

    public static void uploadTextContent(String textContent, String filePath, String fileName) {
        JSONObject requestBody = new JSONObject();

        if (filePath.endsWith("/")) {
            filePath = filePath.substring(0, filePath.length()-1);
        }

        requestBody.put("type", "file");
        requestBody.put("format", "text");
        requestBody.put("content", textContent);

        createDirectory(filePath);
        uploadContent(filePath + "/" + fileName, requestBody);
    }

    private static void createDirectory(String path) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("type", "directory");
        uploadContent(path, requestBody);
    }

    private static void uploadContent(String path, JSONObject body) {
        int statusCode = RestAssured
                .given()
                        .baseUri("http://selenoidshare.d.exportcenter.ru/")
                        .basePath("/api/contents/work")
                        .param("token", tokenNum)
                        .body(body)
                .when()
                        .put(path)
                        .getStatusCode();

        Assert.assertTrue(statusCode == 200 || statusCode == 201);
    }

//    public static ByteArrayInputStream toByteArray(String content) {
//        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
//    }

//    public static Properties parseProperties(String wayProperties) {
//        Properties properties;
//        String content = JupyterLabIntegration.getFileContent(wayProperties);
//        properties = PropertiesHandler.parsePropertiesFromString(content);
//        return properties;
//    }

}
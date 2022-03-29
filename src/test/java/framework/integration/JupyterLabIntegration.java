package framework.integration;

import functions.file.PropertiesHandler;
import io.restassured.RestAssured;

import java.util.Properties;

public class JupyterLabIntegration {

    public static String getFileContent(String filePath) {

        String content = RestAssured
                .given()
                        .baseUri("http://selenoidshare.d.exportcenter.ru/")
                        .basePath("/api/contents/work")
                        .param("token", "0a6a3f42bb6533339a7862e37f56c8cdbcee67a4baecf988")
                .when()
                        .get(filePath)

                .then()
                        .assertThat().statusCode(200)
                .extract().response().jsonPath().getString("content");

        return content;
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
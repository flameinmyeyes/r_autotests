package functions.file;

import framework.integration.JupyterLabIntegration;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesHandler {

    public static Properties parsePropertiesFromString(String content) {
        Properties properties = new Properties();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        try {
            properties.loadFromXML(byteArrayInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static Properties parseProperties(String wayProperties) {
        Properties properties;
        String content = JupyterLabIntegration.getFileContent(wayProperties);
        properties = PropertiesHandler.parsePropertiesFromString(content);
        return properties;
    }
}
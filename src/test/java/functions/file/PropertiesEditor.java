package functions.file;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesEditor {

    public static String readProperties(String filePath, String key) {

        String value = "";

        Properties properties = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "ANSI-1251");

            if(filePath.endsWith(".xml")) {
                properties.loadFromXML(fileInputStream);
            } else {
                properties.load(inputStreamReader);
            }

        } catch (Exception e) {
            System.out.println("Файл " + filePath + " не найден!");
        }

        value = properties.getProperty(key);

        return value;
    }

}

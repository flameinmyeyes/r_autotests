package framework;

import com.google.common.collect.ImmutableMap;
import functions.api.RESTFunctions;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class Environment {

    String wayToConfig = "src/test/resources/environment.properties"; //"src/test/resources/environment.xml";
    String browserName;
    String browserVersion;
    String standURL;
    String standVersion;
    String standCoreVersion;

//    public void setAllureEnvironment(String standURL, String standVersionURL) {
//        //получение и сохранение данных окружения
//        Environment env = new Environment();
//        Map<String,String> parameters = new HashMap<>();
//        parameters.put("browserName", env.getBrowserName());
//        parameters.put("browserVersion", env.getBrowserVersion());
//        parameters.put("standURL", env.getStandURL(standURL));
//        parameters.put("standVersion", env.getStandVersion(standVersionURL));
//        parameters.put("standCoreVersion", env.getStandCoreVersion(standVersionURL));
//        env.writeDataToConfig(parameters);
//
//        //во вложение
////        Allure.addAttachment("Версия приклада", "text/plain", standVersion);
////        Allure.addAttachment("Версия ядра", "text/plain", standCoreVersion);
////        Allure.addAttachment("Версия приклада", standVersion);
////        Allure.addAttachment("Версия ядра", standCoreVersion);
//    }

    //автоматически создаёт environment.xml в папке /target/allure-results/
    public static void setAllureEnvironmentXML(String standURL, String standVersionURL) {
        try {
            //получение и сохранение данных окружения
            Environment env = new Environment();
            allureEnvironmentWriter(
                    ImmutableMap.<String, String>builder()
                            .put("browserName", env.getBrowserName())
                            .put("browserVersion", env.getBrowserVersion())
                            .put("standURL", env.getStandURL(standURL))
                            .put("standVersion", env.getStandVersion(standVersionURL))
                            .put("standCoreVersion", env.getStandCoreVersion(standVersionURL))
                            .build());
        } catch (Exception e) {
            System.out.println("Не удалось записать версию стенда!");
        }

    }

    private String getBrowserName() {
        //в разработке
        browserName = "Chrome";
//        System.out.println("Браузер: " + browserName);
        System.out.println((char) 27 + "[40m" + "Браузер: " + browserName + (char) 27 + "[0m");
        return browserName;
    }

    private String getBrowserVersion() {
        //в разработке
        browserVersion = "85";
//        System.out.println("Версия браузера: " + browserVersion);
        System.out.println((char) 27 + "[40m" + "Версия браузера: " + browserVersion + (char) 27 + "[0m");
        return browserVersion;
    }

    private String getStandURL(String standURL) {
        this.standURL = standURL;
//        System.out.println("Стенд: " + standURL);
        System.out.println((char) 27 + "[40m" + "Стенд: " + standURL + (char) 27 + "[0m");
        return this.standURL;
    }

    private String getStandVersion(String standVersionURL) {
        try {
            //с помощью REST запроса получаем html с версиями ядра и приклада
            String content = RESTFunctions.sendRESTRequest("GET", standVersionURL);

            //правим String
            String[] contentMod = content
                    .replace("&nbsp;", "")
                    .replace("html", "")
                    .replace("p align='left'", "")
                    .replace("pre", "")
                    .replace("br", "")
                    .replace("b", "")
                    .replace("/", "")
                    .replace("Сборка от:", "build: ")
                    .split("<>");

            //достаем версию приклада
            standVersion = contentMod[10];
//            System.out.println("Версия стенда: " + standVersion);
            System.out.println((char) 27 + "[40m" + "Версия стенда: " + standVersion + (char) 27 + "[0m");

        } catch (Exception e) {
            System.out.println("Не удалось получить версию стенда!");
        }
        return standVersion;
    }

    private String getStandCoreVersion(String standVersionURL) {
        try {
            //с помощью REST запроса получаем html с версиями ядра и приклада
            String content = RESTFunctions.sendRESTRequest("GET", standVersionURL);

            //правим String
            String[] contentMod = content
                    .replace("&nbsp;", "")
                    .replace("html", "")
                    .replace("p align='left'", "")
                    .replace("pre", "")
                    .replace("br", "")
                    .replace("b", "")
                    .replace("/", "")
                    .replace("Сборка от:", "build: ")
                    .split("<>");

            //достаем версию ядра
            standCoreVersion = contentMod[5];
//            System.out.println("Версия ядра стенда: " + standCoreVersion);
            System.out.println((char) 27 + "[40m" + "Версия ядра стенда: " + standCoreVersion + (char) 27 + "[0m");

        } catch (Exception e) {
            System.out.println("Не удалось получить версию стенда!");
        }
        return standCoreVersion;
    }

    private void writeDataToConfig(Map<String, String> parameters) {
        //записываем данные в файл с конфигурацией
        String configType = null;
        if (wayToConfig.endsWith(".properties")) {
            configType = "properties";
        } else if (wayToConfig.endsWith(".xml")) {
            configType = "xml";
        }

        switch (configType) {
            case("properties"):
                Properties properties = new Properties();
                try {
                    properties.load(new InputStreamReader(new FileInputStream(wayToConfig), "UTF-8"));
                    for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                        properties.setProperty(parameter.getKey(), parameter.getValue());
                    }
                    properties.store(new OutputStreamWriter(new FileOutputStream(wayToConfig), "UTF-8"), null);
                } catch (Exception e) {
                    System.out.println("Файл конфигурации не найден!");
                    e.printStackTrace();
                }
                break;

            case("xml"):
                //в разработке
                break;
        }

    }



}

package framework;

import com.codeborne.selenide.WebDriverRunner;
import functions.common.CommonFunctions;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Browsers.CHROME;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.FileDownloadMode.PROXY;

public class DriverInit {

    private RemoteWebDriver remoteWebDriver;
    private final int TIMEOUT = 60000;
    private final String DOWNLOADS_FOLDER = Ways.DOWNLOADS.getWay();

    public void driverConfiguration(String runMode, List<File> pluginsList, String testName) {

        ChromeOptions chromeOptions = new ChromeOptions();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        System.out.println((char) 27 + "[40m" + "Режим запуска: " + runMode + (char) 27 + "[0m");
        System.out.println((char) 27 + "[40m" + "Имя теста: " + testName + (char) 27 + "[0m");

        switch (runMode) {
            case ("local"):
                //плагины
                chromeOptions.addExtensions(pluginsList);
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

                //настройки браузера
                browser = CHROME;
                browserVersion = "91";
                startMaximized = true;
                headless = false;
                downloadsFolder = DOWNLOADS_FOLDER;
                screenshots = true;
                proxyEnabled = true;
                fileDownload = PROXY;
                pageLoadTimeout = TIMEOUT;
                timeout = TIMEOUT;
                break;

            case ("remote"):
                //плагины
                chromeOptions.addExtensions(pluginsList);

                //папка для скачивания
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.default_directory", DOWNLOADS_FOLDER);
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.directory_upgrade", true);
                chromeOptions.setExperimentalOption("prefs", prefs);
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

                //настройки браузера
                desiredCapabilities.merge(chromeOptions);
                desiredCapabilities.setCapability("browserName", "chrome");
                desiredCapabilities.setCapability("browserVersion", "85_cryptopro_csp_new_v2");
                desiredCapabilities.setCapability("enableVNC",true);
//                desiredCapabilities.setCapability("enableVideo",true);
                desiredCapabilities.setCapability("name", testName); //передача имени теста в селеноид
                desiredCapabilities.setCapability("takesScreenshot",true);
                desiredCapabilities.setCapability("sessionTimeout", "30m");
                browserCapabilities = desiredCapabilities;
                pageLoadTimeout = TIMEOUT;
                timeout = TIMEOUT;

//              создание удаленного браузера, костыль
                System.out.println("src/test/java/framework/DriverInit.java создание remoteWebDriver");
                for (int i = 1; i < 11; i++) {
                    try {
                        remoteWebDriver = new RemoteWebDriver(URI.create("http://selenoid.d.exportcenter.ru/wd/hub/").toURL(), desiredCapabilities);
                    } catch (Exception e) {
                        System.out.println("Alarm: remoteWebDriver НЕ создан, (" + i + "я попытка из 10)");
                    }
                    if (remoteWebDriver != null){
                        System.out.println("Bingo: remoteWebDriver remoteWebDriver создан, (" + i + "я попытка из 10)");
                        break;
                    }
                    CommonFunctions.wait(1);
                }

                WebDriverRunner.setWebDriver(remoteWebDriver);
                remoteWebDriver.manage().window().maximize();
                break;
        }

    }

}

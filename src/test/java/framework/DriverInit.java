package framework;

import com.codeborne.selenide.WebDriverRunner;
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

    public void driverConfiguration(String runMode, List<File> pluginsList, String testName) {

        ChromeOptions chromeOptions = new ChromeOptions();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        System.out.println((char) 27 + "[40m" + "Режим запуска: " + runMode + (char) 27 + "[0m");
        System.out.println((char) 27 + "[40m" + "Имя теста: " + testName + (char) 27 + "[0m");

        switch (runMode) {
            case ("local"):
                //плагины
//                chromeOptions.addExtensions(pluginsList);
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

                //настройки браузера
                browser = CHROME;
//                browserVersion = "85";
                startMaximized = true;
//                downloadsFolder = "Z:\\files_for_tests\\downloads";
                headless = false;
                screenshots = true;
                proxyEnabled = true;
                fileDownload = PROXY;

                break;

            case ("docker"):
                //плагины
//                chromeOptions.addExtensions(pluginsList);

                //папка для скачивания
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.default_directory", "/share/files_for_tests/downloads");
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.directory_upgrade", true);
                chromeOptions.setExperimentalOption("prefs", prefs);
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

                //настройки браузера
                desiredCapabilities.merge(chromeOptions);
                desiredCapabilities.setCapability("browserName", "chrome");
                desiredCapabilities.setCapability("browserVersion", "85_cryptopro_csp");
//                desiredCapabilities.setCapability("browserVersion", "93.0");

//                desiredCapabilities.setCapability("enableVNC",true); //было раньше
//                desiredCapabilities.setCapability("enableVideo",true); //было раньше
                Map<String, Object> selenoid_options = new HashMap<String, Object>();
                selenoid_options.put("enableVNC", true);
                selenoid_options.put("enableVideo", false);
                desiredCapabilities.setCapability("selenoid:chromeOptions", selenoid_options);

                desiredCapabilities.setCapability("name", testName); //передача имени теста в селеноид
                desiredCapabilities.setCapability("takesScreenshot",true);
                browserCapabilities = desiredCapabilities;

                //создание удаленного браузера
                try {
                    remoteWebDriver = new RemoteWebDriver(URI.create("http://eb-test-allure.otr.ru:4444/wd/hub/").toURL(), desiredCapabilities);
                } catch (Exception e) {}
                WebDriverRunner.setWebDriver(remoteWebDriver);
                remoteWebDriver.manage().window().maximize();
                break;
        }

    }

}

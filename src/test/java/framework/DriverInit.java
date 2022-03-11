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

        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capabilities = new DesiredCapabilities();

//        System.out.println("Режим запуска: " + runMode);
        System.out.println((char) 27 + "[40m" + "Режим запуска: " + runMode + (char) 27 + "[0m");
//        System.out.println("Имя теста: " + testName);
        System.out.println((char) 27 + "[40m" + "Имя теста: " + testName + (char) 27 + "[0m");

        switch (runMode) {
            case ("local"):
                //плагины
                options.addExtensions(pluginsList);
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

                //настройки браузера
                browser = CHROME;
//                browserVersion = "85";
                startMaximized = true;
                downloadsFolder = "Z:\\files_for_tests\\downloads";
                headless = false;
                screenshots = true;
                proxyEnabled = true;
                fileDownload = PROXY;

                break;

            case ("docker"):
                //плагины
                options.addExtensions(pluginsList);

                //папка для скачивания
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.default_directory", "/share/files_for_tests/downloads");
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.directory_upgrade", true);
                options.setExperimentalOption("prefs", prefs);
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

                //настройки браузера
                capabilities.merge(options);
                capabilities.setCapability("browserName", "chrome");
                capabilities.setCapability("browserVersion", "85_cryptopro_csp");
//                capabilities.setCapability("browserVersion", "93.0");

//                capabilities.setCapability("enableVNC",true); //было раньше
//                capabilities.setCapability("enableVideo",true); //было раньше
                Map<String, Object> selenoid_options = new HashMap<String, Object>();
                selenoid_options.put("enableVNC", true);
                selenoid_options.put("enableVideo", false);
                capabilities.setCapability("selenoid:options", selenoid_options);

                capabilities.setCapability("name", testName); //передача имени теста в селеноид
                capabilities.setCapability("takesScreenshot",true);
                browserCapabilities = capabilities;

                //создание удаленного браузера
                try {
                    remoteWebDriver = new RemoteWebDriver(URI.create("http://eb-test-allure.otr.ru:4444/wd/hub/").toURL(), capabilities);
//                    remoteWebDriver = new RemoteWebDriver(URI.create("http://172.17.45.184:4444/wd/hub/").toURL(), capabilities);
                } catch (Exception e) {}
                WebDriverRunner.setWebDriver(remoteWebDriver);
                remoteWebDriver.manage().window().maximize();
                break;
        }

    }

    @Deprecated
    public void driverConfiguration(String runMode) {

        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (runMode) {
            case ("local"):
                //плагины
                //options.addExtensions(new File("drivers\\JinnClient.crx"));
                options.addExtensions(new File("drivers\\CryptoPro.crx"));
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                //настройки браузера
                browser = CHROME;
                startMaximized = true;
                downloadsFolder = "Z:\\files_for_tests\\downloads";
                headless = false;
                screenshots = true;
                proxyEnabled = true;
                fileDownload = PROXY;
                break;

            case ("docker"):
                //плагины
                //options.addExtensions(new File("drivers/JinnClient.crx"));
                options.addExtensions(new File("drivers/CryptoPro.crx"));
                //папка для скачивания
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.default_directory", "/share/files_for_tests/downloads");
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.directory_upgrade", true);
                options.setExperimentalOption("prefs", prefs);
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                //настройки браузера
                capabilities.merge(options);
                capabilities.setCapability("browserName", "chrome");
                capabilities.setCapability("browserVersion", "85_cryptopro_csp");
                capabilities.setCapability("enableVNC",true);
                capabilities.setCapability("enableVideo",false);
                capabilities.setCapability("name","your.test.name"); //передача имени теста в селеноид
                capabilities.setCapability("takesScreenshot",true);
                browserCapabilities = capabilities;
                //удаленный браузер
                try {
                    remoteWebDriver = new RemoteWebDriver(URI.create("http://eb-test-allure.otr.ru:4444/wd/hub/").toURL(), capabilities);
                } catch (Exception e) {}
                WebDriverRunner.setWebDriver(remoteWebDriver);
                remoteWebDriver.manage().window().maximize();
                break;

            case ("docker_manual"):
                //плагины
                //options.addExtensions(new File("drivers\\JinnClient.crx"));
                options.addExtensions(new File("drivers\\CryptoPro.crx"));
                browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                //настройки браузера
                capabilities.merge(options);
                capabilities.setCapability("browserName", "chrome");
                capabilities.setCapability("browserVersion", "85_cryptopro_csp");
                capabilities.setCapability("enableVNC",true);
                capabilities.setCapability("enableVideo",true);
                capabilities.setCapability("takesScreenshot",true);
                browserCapabilities = capabilities;
                //удаленный браузер
                try {
                    remoteWebDriver = new RemoteWebDriver(URI.create("http://eb-test-allure.otr.ru:4444/wd/hub/").toURL(), capabilities);
                } catch (Exception e) {}
                WebDriverRunner.setWebDriver(remoteWebDriver);
                remoteWebDriver.manage().window().maximize();
                break;
        }

    }

}

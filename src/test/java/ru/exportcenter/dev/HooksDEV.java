package ru.exportcenter.dev;

import framework.DriverInit;
import framework.HooksInterface;
import io.qameta.allure.Step;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class HooksDEV implements HooksInterface {

    private static final String URL = "http://uidm.uidm-dev.d.exportcenter.ru/ru/login";
    public static String RUN_MODE;

    @Parameters({"runMode"})
    @BeforeClass
    public DriverInit setDriver(@Optional("local") String runModeFromSuite) {
        //определяем режим запуска (по тест-сьюту)
        RUN_MODE = runModeFromSuite;

        //плагины
        List<File> pluginsList = new ArrayList<>();
        pluginsList.add(new File(setWay("drivers\\CryptoPro.crx")));
//        pluginsList.add(new File(setWay("drivers\\ModHeader.crx")));

        //инициализируем драйвер
        DriverInit driverInit = new DriverInit();
        driverInit.driverConfiguration(RUN_MODE, pluginsList, this.getClass().asSubclass(this.getClass()).getSimpleName());

        //передаем данные окружения в Allure
//        Environment.setAllureEnvironmentXML(URL, "http://eb-tse-demo-ufos.otr.ru:18080/sufdversion");

        return driverInit;
    }

    @BeforeClass
    @Override
    @Step("Открытие веб-страницы")
    public void setUp() {
        clearBrowserCookies();
        open(URL);
    }

    @AfterClass
    @Override
    public void completeTest() {
        closeWebDriver();
    }

    /**
     * Преобразование пути к файлу под Linux (в случае запуска в докере)
     */
    public static String setWay(String way) {
        if (RUN_MODE.equals("docker") || RUN_MODE.equals("docker_manual")) {
            way = way.replace("Z:", "/share").replace("\\", "/");
            //System.out.println("Путь к файлам преобразован под Linux");
        } else {
            //System.out.println("Преобразование пути к файлам под Linux не требуется");
        }
        return way;
    }

}
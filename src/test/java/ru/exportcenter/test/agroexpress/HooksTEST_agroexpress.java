package ru.exportcenter.test.agroexpress;

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

public class HooksTEST_agroexpress implements HooksInterface {

    private static final String URL = "https://lk.t.exportcenter.ru/ru/promo-service?key=agroexpress&serviceId=199d1559-632f-435b-a482-a5bb849b30ce&next_query=true";
    public static String RUN_MODE;

    @Parameters({"runMode"})
    @BeforeClass
    public DriverInit setDriver(@Optional("local") String runModeFromSuite) {
        //определяем режим запуска (по тест-сьюту)
        RUN_MODE = runModeFromSuite;

        //плагины
        List<File> pluginsList = new ArrayList<>();
        pluginsList.add(new File("drivers/CryptoPro.crx"));
//        pluginsList.add(new File("drivers/ModHeader.crx"));

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

}
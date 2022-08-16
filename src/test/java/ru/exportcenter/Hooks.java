package ru.exportcenter;

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

public class Hooks implements HooksInterface {

    public static String RUN_MODE;

    @Parameters({"runMode"})
    @BeforeClass
    public DriverInit setDriver(@Optional("local") String runModeFromSuite) {
        //определяем режим запуска (по тест-сьюту)
        RUN_MODE = runModeFromSuite;

        //плагины
        List<File> pluginsList = new ArrayList<>();
        pluginsList.add(new File("drivers/CryptoPro.crx"));

        //инициализируем драйвер
        DriverInit driverInit = new DriverInit();
        driverInit.driverConfiguration(RUN_MODE, pluginsList, this.getClass().asSubclass(this.getClass()).getSimpleName());

        return driverInit;
    }

    @BeforeClass
    @Override
    @Step("Открытие веб-страницы")
    public void setUp() {
        clearBrowserCookies();
    }

    @AfterClass
    @Override
    public void completeTest() {
        closeWebDriver();
    }

}
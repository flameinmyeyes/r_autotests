package ru.exportcenter.dev.finplatforma;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_08_10_33 extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_17/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_17_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Теребкова Андрей")
    @Description("08 10 33 Просмотр списка сотрудников")
    @Link(name = "Test_08_10_33", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133427200")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        new GUIFunctions()
                .authorizationLib(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"));
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .waitForElementDisplayed("//*[text()='Сотрудники']")
                .clickButton("Сотрудники")
                .clickButton("Все");
    }

    @Step("Заполнение данных по новому сотруднику")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions()
            .clickButton("Романовская Ксения")
            .clickButton("//*[title()='Выход']");
    }
}
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

public class Test_08_10_23 extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_23/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_23_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Теребков Андрей")
    @Description("008 10 23 Профиль сотрудника. Блокировка/Разблокировка")
    @Link(name = "Test_08_10_23", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133418349")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
        step02();
        step03();
        step04();
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
//            .waitForElementDisplayed("//a[@href='/products']");
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
            .clickByLocator("//a[@href='/employees']")
            .clickButton("Сотрудники");
    }

    @Step("Блокировка")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions()
            .clickButton(PROPERTIES.getProperty("AutoTest_Body.ФИО"))
            .clickButton(PROPERTIES.getProperty("AutoTest_Body.Action1"))
            .clickButton(PROPERTIES.getProperty("AutoTest_Body.Action2"));
        CommonFunctions.wait(1);
    }

    @Step("Разблокировка")
    public void step04() {
        CommonFunctions.printStep();

        CommonFunctions.wait(1);

        new GUIFunctions()
            .clickButton(PROPERTIES.getProperty("AutoTest_Body.ФИО"))
            .clickButton(PROPERTIES.getProperty("AutoTest_Body.Action3"))
            .clickButton(PROPERTIES.getProperty("AutoTest_Body.Action4"));
    }
}
package ru.exportcenter.test.finplatforma;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_22 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_22/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_22_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("08 10 22 Сотрудники. Сортировка")
    @Link(name = "Test_08_10_22", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133416352")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль kromanovskaya+user2@roox.ru/Password1!
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Сотрудники']");
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();

        //Нажать на раздел «Сотрудники»
        new GUIFunctionsLKB().clickButton("Сотрудники");
    }

    @Step("Сортировка по полю «ФИО сотрудника»")
    public void step03() {
        CommonFunctions.printStep();

        //Сортировать поле «ФИО сотрудника»
        //Сортировать поле «ФИО сотрудника»
        //Сортировать поле «ФИО сотрудника»
        new GUIFunctionsLKB().clickButton("ФИО сотрудника")
                .waitForLoading()
                .clickButton("ФИО сотрудника")
                .waitForLoading()
                .clickButton("ФИО сотрудника")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Должность»")
    public void step04() {
        CommonFunctions.printStep();

        //Сортировать поле «Должность»
        //Сортировать поле «Должность»
        //Сортировать поле «Должность»
        new GUIFunctionsLKB().clickButton("Должность")
                .waitForLoading()
                .clickButton("Должность")
                .waitForLoading()
                .clickButton("Должность")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Дата регистрации»")
    public void step05() {
        CommonFunctions.printStep();

        //Сортировать поле «Дата регистрации»
        //Сортировать поле «Дата регистрации»
        //Сортировать поле «Дата регистрации»
        new GUIFunctionsLKB().clickButton("Дата регистрации")
                .waitForLoading()
                .clickButton("Дата регистрации")
                .waitForLoading()
                .clickButton("Дата регистрации")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Статус»")
    public void step06() {
        CommonFunctions.printStep();

        //Сортировать поле «Статус»
        //Сортировать поле «Статус»
        //Сортировать поле «Статус»
        new GUIFunctionsLKB().clickButton("Статус")
                .waitForLoading()
                .clickButton("Статус")
                .waitForLoading()
                .clickButton("Статус")
                .waitForLoading();
    }
}

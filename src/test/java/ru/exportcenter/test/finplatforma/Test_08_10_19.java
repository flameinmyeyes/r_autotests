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

public class Test_08_10_19  extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_19/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_19_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("08 10 19 Запросы. Сортировка")
    @Link(name = "Test_08_10_19", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133415849")
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

        //Ввести логин и пароль kromanovskaya+user4@roox.ru/Password1!
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .clickButton("Запросы")
                .waitForElementDisplayed("//tr[@class='ant-table-row ant-table-row-level-0']");
    }

    @Step("Сортировка по полю «Статус»")
    public void step02() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Статус»
        //Нажать на кнопку «Статус»
        //Нажать на кнопку «Статус»
        new GUIFunctionsLKB().clickByLocator("(//span[text()='Статус'])[2]")
                .waitForLoading()
                .clickByLocator("(//span[text()='Статус'])[2]")
                .waitForLoading()
                .clickByLocator("(//span[text()='Статус'])[2]")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Получатель»")
    public void step03() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Получатель»
        //Нажать на кнопку «Получатель»
        //Нажать на кнопку «Получатель»
        new GUIFunctionsLKB().clickByLocator("(//span[text()='Получатель'])[2]")
                .waitForLoading()
                .clickByLocator("(//span[text()='Получатель'])[2]")
                .waitForLoading()
                .clickByLocator("(//span[text()='Получатель'])[2]")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Наименование продукта»")
    public void step04() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Наименование продукта»
        //Нажать на кнопку «Наименование продукта»
        //Нажать на кнопку «Наименование продукта»
        new GUIFunctionsLKB().clickByLocator("(//span[text()='Наименование продукта'])[2]")
                .waitForLoading()
                .clickByLocator("(//span[text()='Наименование продукта'])[2]")
                .waitForLoading()
                .clickByLocator("(//span[text()='Наименование продукта'])[2]")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Дата получения»")
    public void step05() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Дата получения»
        //Нажать на кнопку «Дата получения»
        //Нажать на кнопку «Дата получения»
        new GUIFunctionsLKB().clickButton("Дата получения")
                .waitForLoading()
                .clickButton("Дата получения")
                .waitForLoading()
                .clickButton("Дата получения")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Номер запроса»")
    public void step06() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Номер запроса»
        //Нажать на кнопку «Номер запроса»
        //Нажать на кнопку «Номер запроса»
        new GUIFunctionsLKB().clickButton("Номер запроса")
                .waitForLoading()
                .clickButton("Номер запроса")
                .waitForLoading()
                .clickButton("Номер запроса")
                .waitForLoading();
    }
}

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

public class Test_08_10_15 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_15/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_15_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Петрищев Руслан")
    @Description("08 10 15 Форма Продукты. Сортировка")
    @Link(name="Test_08_10_15", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133413036")
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

        System.out.println(WAY_TO_PROPERTIES);
        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Продукты']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        //В области навигации нажать на раздел «Продукты»
        new GUIFunctionsLKB().clickButton("Продукты")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Наименование продукта»")
    public void step03() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Наименование продукта»
        //Нажать на кнопку «Наименование продукта»
        //Нажать на кнопку «Наименование продукта»
        new GUIFunctionsLKB().clickButton("Наименование продукта")
                .waitForLoading()
                .clickButton("Наименование продукта")
                .waitForLoading()
                .clickButton("Наименование продукта")
                .waitForLoading();
    }

    @Step("Сортировка по полю «Статус»")
    public void step04() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Наименование продукта»
        //Нажать на кнопку «Наименование продукта»
        //Нажать на кнопку «Наименование продукта»
        new GUIFunctionsLKB().clickByLocator("//div[@class='ant-table-column-sorters']//following::span[text()='Статус']")
                .waitForLoading()
                .clickByLocator("//div[@class='ant-table-column-sorters']//following::span[text()='Статус']")
                .waitForLoading()
                .clickByLocator("//div[@class='ant-table-column-sorters']//following::span[text()='Статус']")
                .waitForLoading();
    }
}

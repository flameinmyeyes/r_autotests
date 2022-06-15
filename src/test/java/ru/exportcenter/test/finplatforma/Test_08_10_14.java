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

public class Test_08_10_14 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_14/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_14_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Теребков Андрей")
    @Description("08 10 14 Форма Продукты. Фильтрация")
    @Link(name="Test_08_10_14", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133413032")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() throws InterruptedException {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввод логина и пароля
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//a[@href='/products']");
    }


    @Step("Наименование продукта")
    public void step02() throws InterruptedException {
        CommonFunctions.printStep();

        //В фильтр «Наименование продукта» ввести значение: «Аккредитив. Резервный аккредитив».
        //Нажать на кнопку «Сброс фильтрации».
        new GUIFunctionsLKB().inPlaceholder("Наименование продукта").inputValue(PROPERTIES.getProperty("Наименование продукта"))
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .waitForLoading();
    }

    @Step("Тип категории")
    public void step03() {
        CommonFunctions.printStep();

        //В фильтре «Тип категории» выбрать из выпадающего списка  значение: «Аккредитив».
        //Нажать на кнопку «Сброс фильтрации».
        new GUIFunctionsLKB().inPlaceholder("Тип категории").selectValue(PROPERTIES.getProperty("Тип категории"))
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .waitForLoading();
    }

    @Step("Категория продукта")
    public void step04() {
        CommonFunctions.printStep();

        //В фильтре «Категория продукта» выбрать из выпадающего списка  значение: «Экспортный аккредитив».
        //Нажать на кнопку «Сброс фильтрации».
        new GUIFunctionsLKB().inPlaceholder("Категория продукта").selectValue(PROPERTIES.getProperty("Категория продукта"))
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .waitForLoading();
    }

    @Step("Статус")
    public void step05() {
        CommonFunctions.printStep();

        //В фильтре «Статус» выбрать из выпадающего списка  значение: «Опубликован».
        //Нажать на кнопку «Сброс фильтрации».
        new GUIFunctionsLKB().inPlaceholder("Статус").selectValue(PROPERTIES.getProperty("Статус"))
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .waitForLoading();
    }
}
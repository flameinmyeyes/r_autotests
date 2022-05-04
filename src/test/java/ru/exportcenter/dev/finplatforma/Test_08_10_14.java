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

import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_14 extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_14/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_14_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    public String newProductName;

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
//        step06();
//        step07();
        step08();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    public void setValue(String field, String value){
        CommonFunctions.wait(2);
        $x("//*[@id='rc_select_"+field+"']").click();
        CommonFunctions.wait(2);
        $x("//*[@id='rc_select_"+field+"']//following::*[text()='"+value+"']").click();
        CommonFunctions.wait(2);
        new GUIFunctions().clickButton("Сбросить фильтры");
    }

    @Step("Авторизация")
    public void step01() throws InterruptedException {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        new GUIFunctions()
                .authorizationLib(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//a[@href='/products']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();
        new GUIFunctions().clickButton("Продукты");
        CommonFunctions.wait(2);
        new GUIFunctions().clickButton("Черновики");
        CommonFunctions.wait(2);
        new GUIFunctions().clickButton("Сбросить фильтры");
    }

    @Step("Наименование продукта")
    public void step03() throws InterruptedException {
        CommonFunctions.printStep();
        CommonFunctions.wait(2);
        new GUIFunctions().setValueInPlaceholder("Аккредитив. Резервный аккредитив","Наименование продукта");
        CommonFunctions.wait(2);
        new GUIFunctions().clickButton("Сбросить фильтры");
    }

    @Step("Условия предоставления")
    public void step04() {
        CommonFunctions.printStep();
        setValue("6", "Аккредитив");
    }

    @Step("Категория продукта")
    public void step05() {
        CommonFunctions.printStep();
        setValue("7", "Экспортный аккредитив");
    }

//    @Step("Получатель")
//    public void step06() {
//        CommonFunctions.printStep();
//        setValue("//*[@id='8']", "Российский банк");
//    }
//
//    @Step("Валюта")
//    public void step07() {
//        CommonFunctions.printStep();
//        setValue("//*[@id='9']", "Лоти, LST");
//    }

    @Step("Статус")
    public void step08() {
        CommonFunctions.printStep();
        setValue("10", "Опубликован");
    }
}
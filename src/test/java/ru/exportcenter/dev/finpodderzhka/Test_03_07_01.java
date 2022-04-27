package ru.exportcenter.dev.finpodderzhka;

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
import static com.codeborne.selenide.Selenide.*;
import ru.exportcenter.Hooks;

import java.util.Properties;

public class Test_03_07_01  extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finpodderzhka/Test_03_07_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_01_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Ворожко Александр")
    @Description("03 07 04 Сценарий 4")
    @Link(name="Test_03_07_04", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902512")
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
    public void step01() throws InterruptedException {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password http://arm-lkb.arm-services-dev.d.exportcenter.ru/
        new GUIFunctions()
                .authorizationLib(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//a[@href='/products']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        //Переход на вкладку продукты
        new GUIFunctions().clickButton("Продукты");

        //Создать новый продукт
        new GUIFunctions().clickButton("Создать новый продукт");
    }

    @Step("Сведения о продукте")
    public void step03() {
        CommonFunctions.printStep();

        //Тип продукта - "Финансирование"
        //Категория продукта - "Инвестиционное финансирование"
        setValueInFieldFromSelect("Финансирование", "Тип продукта");
        setValueInFieldFromSelect("Инвестиционное финансирование", "Категория продукта");
        new GUIFunctions().waitForElementDisplayed("//*[text()='Краткое описание продукта']");
        setValueInField("Целевое назначение", "Целевое назначение");
        setValueInField("Краткое описание продукта", "Краткое описание продукта");

        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Условия предоставления")
    public void step04() {
        CommonFunctions.printStep();

        setCheckboxON("Иностранный покупатель");

        setCheckboxONValueInFieldFromSelect("Любая ОПФ", "ОПФ российского получателя");
//        setCheckboxONValueInFieldFromSelect("", "");
        setCheckboxONValueInFieldFromSelect("Любой субъект РФ", "Регион регистрации российского получателя");
        setCheckboxONValueInFieldFromSelect("Все страны", "Страна регистрации иностранного покупателя");
        setValueInField("12","Минимальный срок");
        setValueInField("24","Максимальный срок");
        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Финансовые параметры")
    public void step05() {
        CommonFunctions.printStep();

    }

    @Step("Особенности погашения")
    public void step06() {
        CommonFunctions.printStep();

    }

    public void setValueInFieldFromSelect(String value, String field){
        $x("//span[text()='" + field + "']/following::input").click();
        $x("//*[text()='" + field + "']//following::*[text()='" + value + "']").click();
    }

    public void setValueInField(String value, String field){
        $x("//span[text()='" + field + "']/following::textarea").setValue(value);
    }

    public void setCheckboxON(String field){
        $x("//*[contains(text(), '" + field + "')]//preceding::span[@class=\"ant-checkbox\"]").click();
    }

    public void setCheckboxONValueInFieldFromSelect(String value, String field){
        $x("//span[text()='" + field + "']/following::input").click();
        $x("//*[text()='" + field + "']//following::*[text()='" + value + "']//child::span[@class=\"ant-checkbox\"]").click();
        $x("//span[text()='" + field + "']/following::input").click();
    }
}

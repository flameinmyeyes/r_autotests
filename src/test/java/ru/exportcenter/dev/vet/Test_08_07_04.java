package ru.exportcenter.dev.vet;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_08_07_04 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_04/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_04/" + "Test_08_07_04_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.04 Внести изменение в действующее разрешение в заявку")
    @Link(name = "Test_08_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175275595")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();

    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void precondition() {
        //Предусловие: выполнить шаги 1-4 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_08_07_01 test_08_07_01 = new Test_08_07_01();
        test_08_07_01.WAY_TEST = this.WAY_TEST;
        test_08_07_01.steps();

    }

    @Step("Переход на карточку \"Тип услуги\" ")
    public void step01() {
        CommonFunctions.printStep();

        //В поле Выберите тип услуги выбрать Оформить новое разрешение  Нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Тип услуги")
                .inField("Выберите тип услуги").clickByLocator("//span[text()='Внести изменения в действующее разрешение']")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 2 из 9']");

    }

    @Step("Заполнить поля")
    public void step02() {
        CommonFunctions.printStep();


        new GUIFunctions()
                .inContainer("Поиск действующего разрешения")
                .inField("Страна импортер").selectValue(P.getProperty("Страна импортер"))
                .inField("Тип продукции").selectValue(P.getProperty("Тип продукции"))
                .inField("Продукция").selectValue(P.getProperty("Продукция"))
                .inField("Вид продукции").selectValue(P.getProperty("Вид продукции"))
                .inField("Укажите предприятие (места хранения/отгрузки)").selectValue("RU033")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//button[text()='Скоректировать']")
                .clickButton("Скоректировать")
                .waitForElementDisplayed("//button[text()='Продолжить']");      // дальше этого шага неправильный ТК , отправили на переделку отв. ТЕ Паша. плюс бага с выпадающим списком 2


        //Скорректировать
        new GUIFunctions()
                .inContainer("Поиск действующего разрешения")
                .inField("Страна импортер").selectValue(P.getProperty("Страна импортер"))
                .inField("Тип продукции").selectValue(P.getProperty("Тип продукции"))
                .inField("Продукция").selectValue(P.getProperty("Продукция"))
                .inField("Вид продукции").selectValue(P.getProperty("Вид продукции"))
                .inField("Укажите предприятие (места хранения/отгрузки)").selectValue("RU033")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//button[text()='Скоректировать']")
                .clickButton("Скоректировать")
                .waitForElementDisplayed("//button[text()='Продолжить']");
        //Нажать кнопку "продолжить"

    }
}
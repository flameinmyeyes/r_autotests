package ru.exportcenter.test.pavilion;

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
import java.awt.*;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_02 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_02/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_02_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Андрей Теребков")
    @Description("04 07 02 Внесение Клиентом изменений в состав сведений соглашения об объемах и номенклатуре продукции")
    @Link(name = "Test_04_07_02", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302518")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
//        requestNumber = "S/2022/303225";
        precondition();
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

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_04_07_01 test_04_07_01 = new Test_04_07_01();
        test_04_07_01.steps();
        requestNumber = test_04_07_01.requestNumber;
    }

    @Step("Блок \"Номенклатура и объемы продукции\"")
    public void step01() {
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/ru/main");
        switchTo().alert().accept();

        new GUIFunctions()
                .waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .refreshTab("Подписание Соглашения", 120)
                .refreshTab("Продолжить", 15)
                .clickButton("Продолжить");

        //Нажать на кнопку "..."
        //Нажать кнопку "Изменить"
        //В поле "Количество ед. продукции" заменить значение "12" на "10"
        //Нажать кнопку "Добавить"
        $x("//*[text()='Номенклатура и объемы продукции']").scrollTo();
        new GUIFunctions()
                .clickByLocator("(//button[@class='dropdown-icon'])[2]")
                .waitForElementDisplayed("//*[text()='Изменить']")
                .waitForElementDisplayed("//*[text()='Удалить']")
                .clickButton("Изменить")
                .clickByLocator("//*[text()='Количество ед. продукции']/ancestor::div[@class='BigInputLabel_labelWrapper__30aum']//following::input")
                .clickByLocator("//*[text()='Количество ед. продукции']/ancestor::div[@class='BigInputLabel_labelWrapper__30aum']//following::button[@name='close']")
                .inField("Количество ед. продукции").inputValue(P.getProperty("Номенклатура.Количество ед. продукции")).assertValue()
                .clickButton("Добавить");
    }

    @Step("Блок \"Контактное лицо\"")
    public void step02() throws AWTException {
        CommonFunctions.printStep();

        //Активировать чек-бокс "Контактное лицо"
        //В поле "ФИО" выбрать значение из выпадающего списка Выбираем "ГУСЕВ ДМИТРИЙ МИХАЙЛОВИЧ"
        new GUIFunctions()
                .inField("Контактное лицо").setCheckboxON().assertCheckboxON()
                .inField("ФИО").inputValue(P.getProperty("Контактное лицо.Имя"))
                .waitForElementDisplayed("//*[contains(text(), '"+P.getProperty("Контактное лицо.Имя")+"')]")
                .clickByLocator("//*[contains(text(), '"+P.getProperty("Контактное лицо.Имя")+"')]");
    }
    @Step("Блок \"Фактический адрес\"")
    public void step03() {
        CommonFunctions.printStep();

        //Активировать чек-бокс "Фактический адрес изменился"
        //В поле "Индекс" ввести значение "123456"
        //В поле "Регион" ввести значение "Регион"
        //В поле "Район" ввести значение "Район"
        //В поле "Город" ввести значение "Город"
        //В поле "Населенный пункт" ввести значение "Населенный пункт"
        //В поле "Улица" ввести значение "Улица"
        //В поле "Дом" ввести значение "12"
        //В поле "Строение" ввести значение "3"
        //В поле "Офис" ввести значение "4"
        new GUIFunctions()
                .scrollTo("Фактический адрес изменился")
                .inField("Фактический адрес изменился").setCheckboxON().assertCheckboxON()
                .inField("Индекс").inputValue(P.getProperty("Фактический адрес.Индекс")).assertValue()
                .inField("Регион").inputValue(P.getProperty("Фактический адрес.Регион")).assertValue()
                .inField("Район").inputValue(P.getProperty("Фактический адрес.Район")).assertValue()
                .inField("Город").inputValue(P.getProperty("Фактический адрес.Город")).assertValue()
                .inField("Населенный пункт").inputValue(P.getProperty("Фактический адрес.Населенный пункт")).assertValue()
                .inField("Улица").inputValue(P.getProperty("Фактический адрес.Улица")).assertValue()
                .inField("Дом").inputValue(P.getProperty("Фактический адрес.Дом")).assertValue()
                .inField("Строение").inputValue(P.getProperty("Фактический адрес.Строение")).assertValue()
                .inField("Офис").inputValue(P.getProperty("Фактический адрес.Офис")).assertValue();

        //Нажать кнопку "Далее"
        new GUIFunctions()
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Подписать электронной подписью']");
    }

    @Step("Подписание и направление соглашения")
    public void step04() {
        CommonFunctions.printStep();

        //Вернуться на страницу с заявкой и нажать "Подписать электронной подписью"
        //Выбрать электронный сертификат
        //Нажать кнопку "Подписать"
        //Нажать кнопку "Далее"
        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue(P.getProperty("Подписание.Выберите сертификат")).assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForLoading();
    }

    @Step("Проверка изменений")
    public void step05() {
        CommonFunctions.printStep();

        //Раскрыть аккордеон "О компании"
        //Перейти к аккордеону "Номенклатура и объёмы продукции"
        //Нажать кнопку "Далее"
        new GUIFunctions().clickButton("О компании")
                .scrollTo("Далее")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[contains(text(),'Проект Акта приёмки продукции направлен оператору')]");
    }
}

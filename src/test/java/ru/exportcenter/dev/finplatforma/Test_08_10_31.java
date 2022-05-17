package ru.exportcenter.dev.finplatforma;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.fin.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_31 extends Hooks {

    private final String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_31/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_31_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;

    @Owner(value="Петрищев Руслан")
    @Description("08 10 31 Отправка Черновика на модерацию/публикацию")
    @Link(name="Test_08_10_31", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133424147")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        precondition();
//        step01();
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

    @Step("Предусловия")
    public void precondition() throws InterruptedException {
        CommonFunctions.printStep();

        Test_08_10_02 test_08_10_02 = new Test_08_10_02();
        test_08_10_02.steps();
        newProductName = test_08_10_02.newProductName;
//        closeWebDriver();
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Черновики']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        //Нажать на переключатель «Черновики»
        //В поле фильтра к атрибуту "Наименование продукта" ввести значение "Кредитование. Прямой кредит российскому банку"
        //Нажать на кнопку "Редактировать"
        new GUIFunctions().clickButton("Черновики")
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .inPlaceholder("Наименование продукта").inputValue(newProductName)
                .waitForLoading()
                .clickButton(newProductName)
                .waitForLoading()
                .clickButton("Редактировать")
                .waitForLoading();
    }

    @Step("Сведения о продукте")
    public void step03() {
        CommonFunctions.printStep();

        //Выбрать значение "Финансирование" в поле атрибута «Тип продукта»
        //Выбрать значение "Текущее финансирование" в поле атрибута «Категория продукта»
        //В поле атрибута «Целевое назначение» ввести значение "Кредит на расчеты по экспортной сделке"
        //В поле атрибута «Краткое описание продукта» ввести значение "Кредитование. Прямой кредит российскому экспортеру"
        //Нажать вкладку Блока 2 "Условия предоставления"
        new GUIFunctions().inField("Тип продукта").selectValue(PROPERTIES.getProperty("Сведения о продукте.Тип продукта"))
                .inField("Категория продукта").selectValue(PROPERTIES.getProperty("Сведения о продукте.Категория продукта"))
                .inField("Целевое назначение").inputText(PROPERTIES.getProperty("Сведения о продукте.Целевое назначение"))
                .inField("Краткое описание продукта").inputText(PROPERTIES.getProperty("Сведения о продукте.Краткое описание продукта"))
                .clickButton("Условия предоставления")
                .waitForElementDisplayed("//*[text()='ЮЛ/ИП резидент']");
    }

    @Step("Условия предоставления")
    public void step04() {
        CommonFunctions.printStep();

        //В чек-боксе атрибута «Получатель» поставить флаг в пункте «ЮЛ/ИП резидент»
        //Выбрать значение "Любая ОПФ" в поле атрибута «ОПФ российского получателя»
        //Выбрать значение "от 2 лет" в поле атрибута "Срок регистрации российского получателя"
        //Нажать на вкладку "Финансовые параметры"
        new GUIFunctions().inField("ЮЛ/ИП резидент").setCheckboxON()
                .inField("ОПФ российского получателя").selectValue(PROPERTIES.getProperty("Условия предоставления.ОПФ российского получателя"))
                .inField("Срок регистрации российского получателя").selectValue(PROPERTIES.getProperty("Условия предоставления.Срок регистрации российского получателя"))
                .clickButton("Финансовые параметры")
                .waitForLoading();
    }

    @Step("Финансовые параметры")
    public void step05() {
        CommonFunctions.printStep();

        //Выбрать значение "Доллар сша, USD" в поле атрибута "Валюта"
        //В поле атрибута "Минимальная сумма" ввести значение "500 000"
        //В поле атрибута "Максимальная сумма" ввести значение "1 000 000"
        //В радио-баттоне атрибута "Применение НПА" выбрать значение "На стандартных условиях"
        //нажать на вкладку "Особенности погашения"
        $x("//span[text()='Валюта']/following::input").setValue("Доллар сша");
        $x("//*[text()='Валюта']//following::*[text()='Доллар сша, USD']").click();
        new GUIFunctions().inField("Минимальная сумма").inputValue(PROPERTIES.getProperty("Финансовые параметры.Минимальная сумма"))
                .inField("Максимальная сумма").inputValue(PROPERTIES.getProperty("Финансовые параметры.Максимальная сумма"))
                .inField("На стандартных условиях").setRadiobuttonON()
                .clickButton("Особенности погашения")
                .waitForLoading();
    }

    @Step("Отправка на публикацию")
    public void step06() {
        CommonFunctions.printStep();

        ////Нажать кнопку «...» в карточке черновика
        //Нажать на кнопку "Отправить на публикацию"
        new GUIFunctions().clickByLocator("(//span[@aria-label='ellipsis'])[1]")
                .clickButton("Отправить на публикацию")
                .waitForElementDisplayed("//*[text()='Черновик отправлен на публикацию']");
    }
}

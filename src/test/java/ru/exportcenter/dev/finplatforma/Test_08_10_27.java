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
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_27  extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_27/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_27_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Петрищев Руслан")
    @Description("08 10 27 Создание нового Продукта")
    @Link(name="Test_08_10_27", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133423028")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Создать новый продукт']");
    }

    @Step("Навигация")
    public void step02(){
        CommonFunctions.printStep();

        //Нажать на кнопку «Создать новый продукт»
        new GUIFunctions().clickButton("Создать новый продукт");
    }

    @Step("Блок «Сведения о продукте»")
    public void step03(){
        CommonFunctions.printStep();

        //В поле атрибута «Тип продукта» выбрать значение "Финансирование"
        //В поле атрибута «Категория продукта» выбрать значение "Текущее финансирование"
        //В поле "Наименование продукта" ввести значение "Кредитование. Прямой кредит российскому экспортеру "Урал Лес"
        //В поле атрибута «Целевое назначение» ввести значение "Кредит на расчеты по экспортной сделке"
        //В поле атрибута «Краткое описание продукта» ввести значение "Кредитование. Прямой кредит российскому экспортеру"
        //В экранной форме заполнения атрибутов Продукта Блока 1 "Сведения о продукте" нажать кнопку "Продолжить"
        new GUIFunctions().inField("Тип продукта").selectValue(PROPERTIES.getProperty("Сведения о продукте.Тип продукта"))
                .inField("Категория продукта").selectValue(PROPERTIES.getProperty("Сведения о продукте.Категория продукта"))
                .inField("Наименование продукта").inputText(PROPERTIES.getProperty("Сведения о продукте.Наименование продукта"))
                .inField("Целевое назначение").inputText(PROPERTIES.getProperty("Сведения о продукте.Целевое назначение"))
                .inField("Краткое описание продукта").inputText(PROPERTIES.getProperty("Сведения о продукте.Краткое описание продукта"))
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='ЮЛ/ИП резидент']");
    }

    @Step("Условия предоставления")
    public void step04(){
        CommonFunctions.printStep();

        //В чек-боксе атрибута «Получатель» поставить флаг в пункте «ЮЛ/ИП резидент»
        //Выбрать значение "Любая ОПФ" в поле атрибута «ОПФ российского получателя»
        //Выбрать значение "от 2 лет" В поле атрибута "Срок регистрации российского получателя"
        //В экранной форме заполнения атрибутов Продукта Блока 2 «Условия предоставления» нажать на кнопку "Продолжить"
        new GUIFunctions().inField("ЮЛ/ИП резидент").setCheckboxON()
                .inField("ОПФ российского получателя").selectValue(PROPERTIES.getProperty("Условия предоставления.ОПФ российского получателя"))
                .inField("Срок регистрации российского получателя").selectValue(PROPERTIES.getProperty("Условия предоставления.Срок регистрации российского получателя"))
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Финансовые параметры")
    public void step05(){
        CommonFunctions.printStep();

        //Выбрать значение "Доллар сша, USD" в поле атрибута "Валюта"
        //В поле атрибута "Минимальная сумма" ввести значение "500 000"
        //В поле атрибута "Максимальная сумма" ввести значение "1 000 000"
        //В радио-баттоне атрибута "Применение НПА" выбрать значение "На стандартных условиях"
        //В экранной форме заполнения атрибутов Продукта Блока 3 «Финансовые параметры» нажать на кнопку "Продолжить"
        $x("//span[text()='Валюта']/following::input").setValue("Доллар сша");
        $x("//*[text()='Валюта']//following::*[text()='Доллар сша, USD']").click();
        new GUIFunctions().inField("Минимальная сумма").inputValue(PROPERTIES.getProperty("Финансовые параметры.Минимальная сумма"))
                .inField("Максимальная сумма").inputValue(PROPERTIES.getProperty("Финансовые параметры.Максимальная сумма"))
                .inField("На стандартных условиях").setRadiobuttonON()
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Отправка на публикацию")
    public void step06() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Отправить на публикацию"
        new GUIFunctions().clickButton("Отправить на публикацию")
                .waitForLoading();
    }
}

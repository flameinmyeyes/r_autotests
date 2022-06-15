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

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_03 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_03/";
    public String WAY_TEST_FIRST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_03_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;


    @Owner(value="Теребков Андрей")
    @Description("08 10 03 Отправка Черновика на публикацию. Валидация не пройдена")
    @Link(name="Test_08_10_03", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133412585")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        precondition();
//      step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
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
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password http://arm-lkb.arm-services-dev.d.exportcenter.ru/
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//a[@href='/products']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        //В области навигации нажать на раздел «Продукты»
        //Нажать на кнопку «Создать новый продукт»
        new GUIFunctionsLKB().clickButton("Продукты")
                .waitForElementDisplayed("//*[text()='Создать новый продукт']")
                .clickButton("Черновики")
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .inPlaceholder("Наименование продукта").inputValue(newProductName)
                .waitForLoading()
                .clickButton(newProductName)
                .waitForLoading()
                .clickButton("Редактировать")
                .waitForLoading();
    }

    @Step("Заполнение полей обязательных атрибутов Блока 1 «Сведения о продукте»")
    public void step03(){
        CommonFunctions.printStep();

        //В поле атрибута «Наименование продукта» ввести значение «Кредитование. Прямой кредит иностранному покупателю"
        //В поле атрибута «Тип продукта» выбрать значение "Финансирование"
        //В поле атрибута «Категория продукта» выбрать значение "Текущее финансирование"
        //В поле атрибута «Целевое назначение» ввести значение "Текущее финансирование российского банка"
        //В поле атрибута «Краткое описание продукта» ввести значение "Кредит на расчеты в случае если условия в стране нахождения менее привлекательны для заемщика"
        //Нажать на вкладку «Условия предоставления»
        new GUIFunctionsLKB().inField("Тип продукта").selectValue(PROPERTIES.getProperty("Сведения о продукте.Тип продукта"));
        CommonFunctions.wait(1);
        new GUIFunctionsLKB()
                .inField("Категория продукта").selectValue(PROPERTIES.getProperty("Сведения о продукте.Категория продукта"))
                .inField("Целевое назначение").inputText(PROPERTIES.getProperty("Сведения о продукте.Целевое назначение"))
                .inField("Краткое описание продукта").inputText(PROPERTIES.getProperty("Сведения о продукте.Краткое описание продукта"))
                .clickButton("Условия предоставления")
                .waitForElementDisplayed("//*[text()='Банк резидент']");
    }

    @Step("Заполнение полей обязательных атрибутов Блока 2  «Условия предоставления»")
    public void step04() {
        CommonFunctions.printStep();

        //В чек-боксе атрибута «Получатель» поставить флаг в пункте «Банк резидент»
        //В поле атрибута «ОПФ российского получателя» выбрать значение "Любая ОПФ"
        //В поле атрибута "Срок регистрации российского получателя" выбрать значение "от 6 месяцев"
        //Нажать на вкладку «Финансовые параметры»
        new GUIFunctionsLKB().inField("Банк резидент").setCheckboxON()
                .inField("ОПФ российского получателя").selectValue(PROPERTIES.getProperty("Условия предоставления.ОПФ российского получателя"))
                .inField("Срок регистрации российского получателя").selectValue(PROPERTIES.getProperty("Условия предоставления.Срок регистрации российского получателя"))
//                .inField("Страна регистрации иностранного покупателя").selectValue(PROPERTIES.getProperty("Условия предоставления.Страна регистрации иностранного покупателя"))
                .clickButton("Финансовые параметры")
                .waitForLoading();
    }

    @Step("Заполнение полей обязательных атрибутов Блока 3 «Финансовые параметры»")
    public void step05() {
        CommonFunctions.printStep();

        //В поле атрибута "Валюта" выбрать значение "Евро, EUR"
        //В поле атрибута "Минимальная сумма" ввести значение "500 000 000"
        //В поле атрибута "Максимальная сумма" ввести значение "1 000 000 000"
        //В радио-баттоне атрибута "Применение НПА" выбрать значение "На стандартных условиях"
        //Нажать на вкладку "Особенности погашения"
        $x("//span[text()='Валюта']/following::input").setValue("Евро");
        $x("//*[text()='Валюта']//following::*[text()='Евро, EUR']").click();
        new GUIFunctionsLKB().inField("Минимальная сумма").inputValue(PROPERTIES.getProperty("Финансовые параметры.Минимальная сумма"))
                .inField("Максимальная сумма").inputValue(PROPERTIES.getProperty("Финансовые параметры.Максимальная сумма"))
                .inField("На стандартных условиях").setRadiobuttonON()
                .clickButton("Особенности погашения");
    }

    @Step("Отправка на публикацию")
    public void step06() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Отправить на публикацию"
        new GUIFunctionsLKB().clickByLocator("(//span[@aria-label='ellipsis'])[1]")
                .clickButton("Отправить на публикацию")
                .waitForElementDisplayed("//*[text()='Заполните обязательные поля']");
    }

    @Step("Отображение созданной записи продукта в списке продуктов со статусом «На модерации»")
    public void step07() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Назад»
        new GUIFunctionsLKB().clickByLocator("//img[@alt='Назад']");
    }
}

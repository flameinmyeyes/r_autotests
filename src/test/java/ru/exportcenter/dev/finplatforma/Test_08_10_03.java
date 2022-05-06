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
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_08_10_03 extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_03_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;


    @Owner(value="Петрищев Руслан")
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
        CommonFunctions.wait(1);
//        closeWebDriver();
    }

    @Step("Авторизация")
    public void step01(){
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

        //В области навигации нажать на раздел «Продукты»
        //Нажать на переключатель «Черновики»
        //Нажать на кнопку «Редактировать»
        new GUIFunctions().clickButton("Продукты")
                .waitForElementDisplayed("//*[text()='Создать новый продукт']")
                .clickButton("Черновики")
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .setValueInPlaceholder(newProductName,"Наименование продукта")
                .waitForLoading()
                .clickButton(newProductName)
                .waitForLoading()
                .clickButton("Редактировать");
    }

    @Step("Заполнение полей обязательных атрибутов Блока 1 «Сведения о продукте»")
    public void step03(){
        CommonFunctions.printStep();

        //В поле атрибута «Наименование продукта» ввести значение «Кредитование. Прямой кредит иностранному покупателю"
        //В поле атрибута «Тип продукта» выбрать значение "Финансирование"
        //В поле атрибута «Категория продукта» выбрать значение "Текущее финансирование"
        //В поле атрибута «Целевое назначение» ввести значение "Текущее финансирование российского банка"
        //В поле атрибута «Краткое описание продукта» ввести значение "Кредит на расчеты в случае если условия в стране нахождения менее привлекательны для заемщика"
        //Нажать на вкладку Блока 2 "Условия предоставления"
        new GUIFunctions().waitForLoading()
                .setValueInFieldFromSelect(PROPERTIES.getProperty("Сведения о продукте.Тип продукта"),"Тип продукта")
                .setValueInFieldFromSelect(PROPERTIES.getProperty("Сведения о продукте.Категория продукта"),"Категория продукта")
                .setTextInField(PROPERTIES.getProperty("Сведения о продукте.Целевое назначение"),"Целевое назначение")
                .setTextInField(PROPERTIES.getProperty("Сведения о продукте.Краткое описание продукта"),"Краткое описание продукта")
                .clickButton("Условия предоставления")
                .waitForLoading();
    }

    @Step("Заполнение полей обязательных атрибутов Блока 2  «Условия предоставления»")
    public void step04() {
        CommonFunctions.printStep();

        //В чек-боксе атрибута «Получатель» поставить флаг в пункте «Банк резидент»
        //В поле атрибута «ОПФ российского получателя» выбрать значение "Любая ОПФ"
        //Нажать на вкладку "Финансовые параметры"
        new GUIFunctions().setCheckboxOnInField("Банк резидент")
                .setCheckboxONValueInFieldFromSelect(PROPERTIES.getProperty("Условия предоставления.ОПФ российского получателя"),"ОПФ российского получателя")
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
        new GUIFunctions().setValueInField(PROPERTIES.getProperty("Финансовые параметры.Минимальная сумма"), "Минимальная сумма")
                .setValueInField(PROPERTIES.getProperty("Финансовые параметры.Максимальная сумма"), "Максимальная сумма")
                .setRadioButtonInField("На стандартных условиях")
                .clickButton("Особенности погашения");
    }

    @Step("Отправка на публикацию")
    public void step06() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Отправить на публикацию"
        new GUIFunctions().clickButton("Отправить на публикацию")
                .waitForLoading();
    }
}

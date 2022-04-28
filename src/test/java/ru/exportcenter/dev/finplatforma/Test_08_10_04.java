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

import java.util.Properties;
import java.util.Random;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class Test_08_10_04 extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_04/";
    public String WAY_TEST_FIRST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_04_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;


    @Owner(value="Петрищев Руслан")
    @Description("08 10 04 Отправка Черновика на публикацию. Валидация пройдена")
    @Link(name="Test_08_10_04", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133412728")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        precondition();
        step01();
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
        CommonFunctions.wait(1);
        closeWebDriver();
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        System.out.println(newProductName);

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
        //Нажать на кнопку «Создать новый продукт»
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

        newProductName = "Кредитование. Прямой кредит российскому банку " + new Random().nextInt(999999999);

        //В поле атрибута «Наименование продукта» ввести значение «Кредитование. Прямой кредит иностранному покупателю"
        //В поле атрибута «Тип продукта» выбрать значение "Финансирование"
        //В поле атрибута «Категория продукта» выбрать значение "Инвестиционное финансирование"
        //В поле атрибута «Целевое назначение» ввести значение "Финансирование / рефинансирование расчетов по экспортному контракту"
        //В поле атрибута «Краткое описание продукта» ввести значение "Кредит на расчеты в случае если условия в стране нахождения менее привлекательны для заемщика"
        new GUIFunctions().waitForLoading()
                .setValueInField(newProductName, "Наименование продукта")
                .setValueInFieldFromSelect("Финансирование","Тип продукта")
                .setValueInFieldFromSelect("Инвестиционное финансирование","Категория продукта")
                .setValueInField("Финансирование / рефинансирование расчетов по экспортному контракту","Целевое назначение")
                .setValueInField("Кредит на расчеты в случае если условия в стране нахождения менее привлекательны для заемщика","Краткое описание продукта");

        //Нажать на кнопку «Продолжить»
        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Заполнение полей обязательных атрибутов Блока 2  «Условия предоставления»")
    public void step04() {
        CommonFunctions.printStep();

        //В чек-боксе атрибута «Получатель» поставить флаг в пункте «Иностранный покупатель»
        new GUIFunctions().setCheckboxOnInField("Иностранный покупатель")
                .setCheckboxONValueInFieldFromSelect("Любая ОПФ","ОПФ российского получателя")
                .setValueInFieldFromSelect("От 1 года","Срок регистрации российского получателя");

        //Нажать на кнопку «Продолжить»
        new GUIFunctions().clickButton("Продолжить");

    }

    @Step("Заполнение полей обязательных атрибутов Блока 3 «Финансовые параметры»")
    public void step05() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Сохранить как черновик»
        new GUIFunctions().setValueInFieldFromSelect("Евро,EUR", "Валюта")
                .setValueInField("500 000 000", "Минимальная сумма")
                .setValueInField("1 000 000 000", "Максимальная сумма")
                .setRadiobuttonByDescription("На стандартных условиях");

        //Нажать на кнопку «Продолжить»
        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Отправка на публикацию")
    public void step06() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Отправить на публикацию"
        new GUIFunctions().clickButton("Отправить на публикацию");
    }

    @Step("Отображение созданной записи продукта в списке продуктов со статусом «На модерации»")
    public void step07() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Отправить на публикацию"
        new GUIFunctions().clickByLocator("//*[text()='Назад']");
        CommonFunctions.wait(10);
    }
}

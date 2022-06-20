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
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_32 extends Hooks {

    private final String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_32/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_32_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;

    @Owner(value="Петрищев Руслан")
    @Description("08 10 32 Изменение статуса продукта")
    @Link(name="Test_08_10_32", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133425343")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        precondition();
//        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() throws InterruptedException {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password http://arm-lkb.arm-services-dev.d.exportcenter.ru/
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//a[@href='/products']");

        //В области навигации нажать на раздел «Продукты»
        //Нажать на кнопку «Создать новый продукт»
        new GUIFunctionsLKB().clickButton("Создать новый продукт")
                .waitForLoading();

        //Тип продукта - "Финансирование"
        CommonFunctions.wait(2);
//        newProductName = "Кредитование. Прямой кредит российскому банку " + new Random().nextInt(999999999);
        newProductName = String.valueOf(new Random().nextInt(999999999));
        new GUIFunctionsLKB().inField("Тип продукта").selectValue("Финансирование")
                .inField("Категория продукта").selectValue("Текущее финансирование")
                .inField("Наименование продукта").inputText(newProductName)
                .inField("Целевое назначение").inputText("Целевое назначение")
                .inField("Краткое описание продукта").inputText("Краткое описание продукта")
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='ЮЛ/ИП резидент']");

        new GUIFunctionsLKB().inField("ЮЛ/ИП резидент").setCheckboxON()
                .inField("ОПФ российского получателя").selectValue("Общественные учреждения")
                .inField("Срок регистрации российского получателя").selectValue("от 2 лет")
                .inField("Регион регистрации российского получателя").selectValue("Г.Санкт-Петербург")
                .scrollToElement("//*[text()='Страна регистрации иностранного покупателя']")
                .inField("Страна регистрации иностранного покупателя").selectValue("Республика Албания")
                .clickButton("Продолжить")
                .waitForLoading();

        new GUIFunctionsLKB().inField("На стандартных условиях").setRadiobuttonON()
                .clickButton("Продолжить")
                .waitForLoading();

        //Нажать на кнопку «Сохранить как черновик»
        new functions.gui.GUIFunctions().clickButton("Сохранить как черновик");

        //Нажать на кнопку «Назад» (стрелкой)
        new functions.gui.GUIFunctions().clickByLocator("//img[@alt='Назад']")
                .waitForElementDisplayed("//*[text()='Создать новый продукт']")
                .clickByLocator("//div[text()='Продукты']");
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Черновики']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        //Нажать на переключатель «Черновики»
        //В поле фильтра к атрибуту "Наименование продукта" ввести значение "Кредитование. Прямой кредит российскому банку"
        //Нажать на кнопку "Редактировать"
        new GUIFunctionsLKB().waitForElementDisplayed("//*[text()='Черновики']")
                .clickButton("Черновики")
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .inPlaceholder("Наименование продукта").inputValue(newProductName)
                .waitForLoading()
                .clickButton(newProductName)
                .waitForLoading();
        CommonFunctions.wait(1);
    }

    @Step("Изменение статуса продукта")
    public void step03() {
        CommonFunctions.printStep();

        //Нажать кнопку «...» в карточке черновика
        //Нажать на кнопку "Отправить на публикацию"
        new GUIFunctionsLKB().clickByLocator("(//span[@aria-label='ellipsis'])[1]")
                .clickButton("Отправить на публикацию")
                .waitForElementDisplayed("//*[text()='Черновик отправлен на публикацию']");
    }
}

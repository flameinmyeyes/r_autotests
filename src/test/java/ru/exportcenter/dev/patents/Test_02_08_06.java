package ru.exportcenter.dev.patents;

import framework.RunTestAgain;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.dev.HooksDEV;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.codeborne.selenide.Selenide.*;

public class Test_02_08_06 extends HooksDEV {

    @Description("ТК 02 08 06")
    @Owner(value="Петрищев Руслан, Теребков Андрей")
    @Link(name="Test_02_08_06", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123868584")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
        step02();
        step03();
    }

    @Step("Авторизация")
    private void step01(){
        CommonFunctions.printStep();

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти")
                .waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    private void step02(){
        CommonFunctions.printStep();

        //Перейти во вкладку «Сервисы»
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business");

        //Выбрать сервис «Компенсация части затрат на регистрацию ОИС за рубежом»
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом")
                .clickByLocator("//div[text()='Государственные']");
    }

    @Step("Получение информации о сервисе")
    private void step03() throws AWTException {
        CommonFunctions.printStep();

        //Нажать кнопку «Подробнее»
        new GUIFunctions().openSearchResult("Компенсация части затрат на регистрацию ОИС", "Подробнее");

        //Проверка корректного отображения заголовков «Компенсация части затрат на регистрацию ОИС за рубежом», разделы «Что получает экспортер?» и «Как получить».
        new GUIFunctions().waitForElementDisplayed("//h1[contains(text(),'Компенсация части затрат на регистрацию ОИС за рубежом')]");
        new GUIFunctions().waitForElementDisplayed("//*[text()='Что получает экспортер?']");
        new GUIFunctions().waitForElementDisplayed("//h2[text()='Как получить']");

        //В боковом меню нажать  «Как получить»
        new GUIFunctions().inContainer("Содержимое страницы")
                .clickButton("Как получить");

        //Проверка отображения раздела «Как получить».
        new GUIFunctions().waitForElementDisplayed("//div[@class='timeline']");

        //В боковом меню нажать  «Описание»
        new GUIFunctions().inContainer("Содержимое страницы")
                        .clickButton("Описание");

        //Проверка отображения заловока и раздела «Что получает экспортер?»
        new GUIFunctions().waitForElementDisplayed("//*[text()='Что получает экспортер?']");
    }
}



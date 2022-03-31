package ru.exportcenter.test.patents;

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
import ru.exportcenter.test.HooksTEST;
import java.util.Properties;

public class Test_02_08_02_1 extends HooksTEST {

    private String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_1/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_02_1_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Description("ТК 02 08 02 1")
    @Owner(value="Петрищев Руслан, Теребков Андрей")
    @Link(name="Test_02_08_02_1", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123868584")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps(){
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    private void step01(){
        CommonFunctions.printStep();

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue(PROPERTIES.getProperty("Авторизация.Email"))
                .inField("Пароль").inputValue(PROPERTIES.getProperty("Авторизация.Пароль"))
                .clickButton("Войти")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    private void step02(){
        CommonFunctions.printStep();

        //Перейти во вкладку «Сервисы»
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business");

        //Выбрать сервис «Компенсация части затрат на регистрацию ОИС за рубежом»
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом")
                .clickByLocator("//div[text()='Государственные']");
    }

    @Step("Получение информации о сервисе")
    private void step03() {
        CommonFunctions.printStep();

        new GUIFunctions().closeAllPopupWindows();

        //Нажать кнопку «Подробнее»
        new GUIFunctions().openSearchResult("Компенсация части затрат на регистрацию ОИС", "Подробнее");

        //Проверка корректного отображения заголовков «Компенсация части затрат на регистрацию ОИС за рубежом», разделы «Что получает экспортер?» и «Как получить».
        new GUIFunctions().waitForElementDisplayed("//h1[contains(text(),'Компенсация части затрат на регистрацию ОИС за рубежом')]");
        new GUIFunctions().waitForElementDisplayed("//*[text()='Что получает экспортер?']");
        new GUIFunctions().waitForElementDisplayed("//h2[text()='Как получить']");

        new GUIFunctions().closeAllPopupWindows();

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



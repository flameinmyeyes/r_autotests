package ru.exportcenter.test.agroexpress;

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

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_11  extends HooksTEST {

    private String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_11/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_11_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("ТК 03 07 02.4")
    @Link(name = "Test_03_07_02.4", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123878662")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
//      step04(); //Недоделан функционал
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль test-otr@yandex.ru/Password1!
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("test-otr@yandex.ru")
                .inField("Пароль").inputValue("Password1!")
                .clickButton("Войти");

        //Ввести код
        $x("//input[@data-id='0']").sendKeys("1");
        $x("//input[@data-id='1']").sendKeys("2");
        $x("//input[@data-id='2']").sendKeys("3");
        $x("//input[@data-id='3']").sendKeys("4");
        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Просмотр данных заявки из перечня ")
    public void step02() {
        CommonFunctions.printStep();

        //Выбрать любую заявку из реестра и нажать на нее
        new GUIFunctions().clickByLocator("(//div[@class='ServiceCard_cardWrapper__1GgdB'])[1]")
                .waitForLoading();
    }

    @Step("Создание заявки")
    public void step03() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Сервисы"
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business");

        //Выбрать сервис «Компенсация части затрат на регистрацию ОИС за рубежом»
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить");

        //Перезагрузить страницу
        switchTo().window(1);
        new GUIFunctions().waitForElementDisplayed("//div[@class='Steps_stepsWrapper__2dJpS']");
        refreshTab("//*[text()='Продолжить']", 5);

        //Нажать кнопку «Продолжить»
        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//div[@class='FormOpenPanel_panelBody__2UbuF']");
    }

    @Step("Ознакомление с функциональными возможностями сервиса (Onboarding)")
    public void step04() {
        CommonFunctions.printStep();

    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(3);
        }
    }
}

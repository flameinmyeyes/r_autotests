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

public class Test_03_07_02_4 extends HooksTEST {

    private String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_4/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_4_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("03 07 02.4 Просмотр перечня Заявок по сервису, созданных организацией. Создание Заявки. Ознакомление с функциональными возможностями сервиса (Onboarding)")
    @Link(name = "Test_03_07_02_4", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123878662")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль test-otr@yandex.ru/Password1!
        //Ввести код
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), "1234")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Просмотр данных заявки из перечня ")
    public void step02() {
        CommonFunctions.printStep();

        //Выбрать любую заявку из реестра и нажать на нее
        new GUIFunctions().clickByLocator("(//div[@class='ServiceCard_cardWrapper__1GgdB'])[1]")
                .waitForElementDisplayed("//*[text()='Завершено']");
    }

    @Step("Создание заявки")
    public void step03() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Сервисы"
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/");

        //Выбрать сервис «Логистика. Доставка продукции "Агроэкспрессом"»
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=agroexpress&serviceId=199d1559-632f-435b-a482-a5bb849b30ce&next_query=true");
        new GUIFunctions().waitForLoading();

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

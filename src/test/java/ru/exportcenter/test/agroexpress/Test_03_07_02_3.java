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

public class Test_03_07_02_3 extends HooksTEST {

    /*
     * http://selenoidshare.d.exportcenter.ru/lab/tree/work/files_for_tests/test/agroexpress/Test_03_07_02_3
     * https://gitlab.exportcenter.ru/sub-service/autotests/rec_autotests/-/blob/master/src/test/java/ru/exportcenter/test/agroexpress/Test_03_07_02_3.java
     */

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_3/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_03_07_02_3.xml");

    @Owner(value = "Диана Максимова")
    @Description("03 07 02.3 Авторизация экспортера в ФГАИС \"Одно окно\". Выбор Сервиса. Ознакомление с описанием Сервиса")
    @Link(name = "Test_03_07_02_3", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123878353")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
        step03();
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        new GUIFunctions().authorization(P.getProperty("Логин"), P.getProperty("Пароль")/*, P.getProperty("Код подтвержения")*/)
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация через Поиск")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"");
    }

    @Step("Получение информации о сервисе")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions().closeAllPopupWindows()
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Подробнее")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business" +
                        "/Prodvizhenie_na_vneshnie_rynki/Poisk_pokupatelya,_soprovozhdenie_peregovorov/" +
                        "logistics_agroexpress")
                .closeAllPopupWindows()
                .waitForElementDisplayed("//h1[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']")
                .waitForElementDisplayed(byText("Что получает экспортер?"))
                .waitForElementDisplayed("//h2[text()='Как получить']");

        for (int step = 1; step <= 7; step++) { // Как получить
            new GUIFunctions().waitForElementDisplayed(byText(P.getProperty("Шаг " + step)));
        }

        for (int service = 1; service <= 5; service++) { // Что получает экспортер?
            new GUIFunctions().waitForElementDisplayed(byText(P.getProperty("Услуга " + service)));
        }

        new GUIFunctions()
                .clickButton("Оферта АО «РЖД Логистика» на оказание услуг транспортной экспедиции")
                .switchPageTo(1)
                .waitForURL(P.getProperty("Оферта.URL"));
    }

    private String byText(final String text) {
        return "//*[contains(text(), '" + text + "')]";
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }
}


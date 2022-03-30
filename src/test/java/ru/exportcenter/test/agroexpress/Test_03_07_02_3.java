package ru.exportcenter.test.agroexpress;

import framework.RunTestAgain;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;

import static com.codeborne.selenide.Selenide.$x;

public class Test_03_07_02_3 extends HooksTEST {

    @Owner(value = "Максимова Диана")
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
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти");

        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация через Поиск")
    public void step02() {
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"");
    }

    @Step("Получение информации о сервисе")
    public void step03() {
        new GUIFunctions()
                .closeAllPopupWindows()
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Подробнее")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business" +
                        "/Prodvizhenie_na_vneshnie_rynki/Poisk_pokupatelya,_soprovozhdenie_peregovorov/" +
                        "logistics_agroexpress")
                .closeAllPopupWindows()
                .waitForElementDisplayed("//h1[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']")
                .waitForElementDisplayed(byText("Что получает экспортер?"))
                .waitForElementDisplayed("//h2[text()='Как получить']");

        new GUIFunctions() // Как получить
                .waitForElementDisplayed(byText("Зарегистрируйтесь или авторизуйтесь в ЕЛК на платформе «Мой экспорт»"))
                .waitForElementDisplayed(byText("Выберите услугу и направьте заявку"))
                .waitForElementDisplayed(byText("Получите счет на оплату услуг перевозки"))
                .waitForElementDisplayed(byText("Передайте груз в АО «РЖД Логистика» для выполнения перевозки"))
                .waitForElementDisplayed(byText("Отслеживайте статус перевозки груза в ЕЛК на платформе «Мой экспорт»"))
                .waitForElementDisplayed(byText("Согласуйте результат оказания услуги и получите закрывающие документы"))
                .waitForElementDisplayed(byText("Оцените качество предоставленной услуги"));

        new GUIFunctions() // Что получает экспортер?
                .waitForElementDisplayed(byText("Подача заявки на организацию перевозки на экспорт непосредственно в АО «РЖД Логистика»;"))
                .waitForElementDisplayed(byText("Возможность заказа дополнительных услуг таможенного оформления, а также получения ветеринарных и фитосанитарных сертификатов;"))
                .waitForElementDisplayed(byText("Организация железнодорожной или мультимодальной перевозки с возможностью отправки от одного контейнера;"))
                .waitForElementDisplayed(byText("Отслеживание транспортировки груза;"))
                .waitForElementDisplayed(byText("Информация о документах, необходимых для получения компенсации затрат на транспортировку сельскохозяйственной и продовольственной продукции."))
                .clickButton("Оферта АО «РЖД Логистика» на оказание услуг транспортной экспедиции")
                .switchPageTo(1)
                .waitForURL("https://www.rzdlog.ru/?utm_source=master-portal.t.exportcenter.ru&utm_medium=referral&utm_campaign=master-portal.t.exportcenter.ru&utm_referrer=master-portal.t.exportcenter.ru");
    }

    private String byText(final String text) {
        return "//*[contains(text(), '" + text + "')]";
    }
}


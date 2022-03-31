package ru.exportcenter.test.agroexpress;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
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

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_03_07_01 extends HooksTEST {

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_01/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_03_07_01.xml");

    @Owner(value = "Максимова Диана")
    @Description("03 07 01 Заполнение Заявки на получение услуги (сценарий 1)")
    @Link(name = "Test_03_07_01", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902466")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
    }

    @Step("Авторизация")
    public void step01() {
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue(P.getProperty("Логин"))
                .inField("Пароль").inputValue(P.getProperty("Пароль"))
                .clickButton("Войти");

        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .closeAllPopupWindows()
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить")
                .switchPageTo(1);

        refreshTab(15);

        new GUIFunctions().clickButton("Продолжить");
    }

    private void refreshTab(int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if ($x("//*[contains(text(), 'Продолжить')]").isDisplayed()) {
                break;
            }
            refresh();
        }
    }

    @Step("Заполнить область «Информация о компании»")
    public void step03() {
        new GUIFunctions().inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue(P.getProperty("Почтовый адрес")).assertNoControl();
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step04() {
        new GUIFunctions().inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertNoControl()
                .inField("ФИО").inputValue(P.getProperty("Заявитель.ФИО")).assertNoControl()
                .inField("Телефон").inputValue(P.getProperty("Заявитель.Телефон")).assertNoControl()
                .inField("Должность").inputValue(P.getProperty("Заявитель.Должность")).assertNoControl()
                .inField("Email").inputValue(P.getProperty("Заявитель.Email")).assertNoControl();
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step05() {
        String departureDate = P.getProperty("Дата отправления");
        if (departureDate.equals("")) {
            departureDate = DateFunctions.dateShift("dd.MM.yyyy", 14);
        }

        new GUIFunctions().inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue(P.getProperty("Город отправления")).assertNoControl()
                .inField("Город назначения").selectValue(P.getProperty("Город назначения")).assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Адрес").inputValue(P.getProperty("Адрес")).assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue(departureDate).assertNoControl();
    }

    @Step("Заполнить область «Информация о грузе»")
    public void step06() {
        new GUIFunctions().inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .inField("Наименование продукции").selectValue(P.getProperty("Наименование продукции")).assertNoControl()
                .inField("Код ТН ВЭД").assertValue(P.getProperty("Код ТН ВЭД")).assertUneditable().assertNoControl()
                .inField("Вес продукции, кг").inputValue(P.getProperty("Вес продукции")).assertNoControl()
                .inField("Упаковка").selectValue(P.getProperty("Упаковка")).assertNoControl()
                .inField("Количество контейнеров").inputValue(P.getProperty("Количество контейнеров")).assertNoControl()
                .inField("Тип контейнера").selectValue(P.getProperty("Тип контейнера")).assertNoControl()
                .clickButton("Сохранить");
    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step07() {
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue(P.getProperty("Наименование грузополучателя")).assertNoControl()
                .inField("Страна").inputValue(P.getProperty("Страна")).assertNoControl()
                .inField("Город").inputValue(P.getProperty("Город")).assertNoControl()
                .inField("Дом").inputValue(P.getProperty("Дом")).assertNoControl()
                .inField("Регион").inputValue(P.getProperty("Регион")).assertNoControl()
                .inField("Улица").inputValue(P.getProperty("Улица")).assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue(P.getProperty("Регистрационный номер грузополучателя")).assertNoControl()
                .inField("Телефон").inputValue(P.getProperty("Телефон")).assertNoControl()
                .inField("Представитель грузополучателя").inputValue(P.getProperty("Представитель грузополучателя")).assertNoControl()
                .inField("Email").inputValue(P.getProperty("Email")).assertNoControl();
    }

    @Step("Заполнить область «Дополнительные услуги»")
    public void step08() {
        new GUIFunctions().inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Адрес").assertValue(P.getProperty("Адрес")).assertEditable().assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления")
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов")
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов");

        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Заявка отправлена на рассмотрение. Срок рассмотрения до 3 рабочих дней']");
    }


    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }
}


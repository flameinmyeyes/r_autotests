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

import static ru.exportcenter.test.agroexpress.Test_03_07_03_1.Test_03_07_03_1_10.errorCompensation;

@Deprecated
public class Test_03_07_01 extends HooksTEST {

    /**
     * Тестовый сценарий находится в орхиве
     * http://selenoidshare.d.exportcenter.ru/lab/tree/work/files_for_tests/test/agroexpress/Test_03_07_01
     * https://gitlab.exportcenter.ru/sub-service/autotests/rec_autotests/-/blob/master/src/test/java/ru/exportcenter/test/agroexpress/Test_03_07_01.java
     */

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_01/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_03_07_01.xml");

    @Owner(value = "Диана Максимова")
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
        CommonFunctions.printStep();

        new GUIFunctions().authorization(P.getProperty("Логин"), P.getProperty("Пароль"))
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .closeAllPopupWindows()
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить")
                .switchPageTo(1);

        errorCompensation();

        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Заполнить область «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions().inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue(P.getProperty("Почтовый адрес")).assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions().inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertNoControl()
                .inField("ФИО").inputValue(P.getProperty("Заявитель.ФИО")).assertValue().assertNoControl()
                .inField("Телефон").inputValue(P.getProperty("Заявитель.Телефон")).assertValue().assertNoControl()
                .inField("Должность").inputValue(P.getProperty("Заявитель.Должность")).assertValue().assertNoControl()
                .inField("Email").inputValue(P.getProperty("Заявитель.Email")).assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();

        String departureDate = P.getProperty("Дата отправления");
        if (departureDate.equals("")) {
            departureDate = DateFunctions.dateShift("dd.MM.yyyy", 14);
        }

        new GUIFunctions().inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue(P.getProperty("Город отправления")).assertValue().assertNoControl()
                .inField("Город назначения").selectValue(P.getProperty("Город назначения")).assertValue().assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Адрес").inputValue(P.getProperty("Адрес")).assertValue().assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue(departureDate).assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация о грузе»")
    public void step06() {
        CommonFunctions.printStep();

        new GUIFunctions().inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .inField("Наименование продукции").selectValue(P.getProperty("Наименование продукции")).assertValue().assertNoControl()
                .inField("Код ТН ВЭД").assertValue(P.getProperty("Код ТН ВЭД")).assertUneditable().assertNoControl()
                .inField("Вес продукции, кг").inputValue(P.getProperty("Вес продукции")).assertValue().assertNoControl()
                .inField("Упаковка").selectValue(P.getProperty("Упаковка")).assertValue().assertNoControl()
                .inField("Количество контейнеров").inputValue(P.getProperty("Количество контейнеров")).assertValue().assertNoControl()
                .inField("Тип контейнера").selectValue(P.getProperty("Тип контейнера")).assertValue().assertNoControl()
                .clickButton("Сохранить");
    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step07() {
        CommonFunctions.printStep();

        new GUIFunctions().inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue(P.getProperty("Наименование грузополучателя")).assertValue().assertNoControl()
                .inField("Страна").inputValue(P.getProperty("Страна")).assertValue().assertNoControl()
                .inField("Город").inputValue(P.getProperty("Город")).assertValue().assertNoControl()
                .inField("Дом").inputValue(P.getProperty("Дом")).assertValue().assertNoControl()
                .inField("Регион").inputValue(P.getProperty("Регион")).assertValue().assertNoControl()
                .inField("Улица").inputValue(P.getProperty("Улица")).assertValue().assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue(P.getProperty("Регистрационный номер грузополучателя")).assertValue().assertNoControl()
                .inField("Телефон").inputValue(P.getProperty("Телефон")).assertValue().assertNoControl()
                .inField("Представитель грузополучателя").inputValue(P.getProperty("Представитель грузополучателя")).assertValue().assertNoControl()
                .inField("Email").inputValue(P.getProperty("Email")).assertValue().assertNoControl();
    }

    @Step("Заполнить область «Дополнительные услуги»")
    public void step08() {
        CommonFunctions.printStep();

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


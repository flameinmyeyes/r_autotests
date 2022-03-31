package ru.exportcenter.test.agroexpress;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
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

public class Test_03_07_02_2_1 extends HooksTEST {

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_2_1/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_03_07_02_2_1.xml");

    @Owner(value = "Максимова Диана")
    @Description("03 07 02.2.1 Ввод и редактирование данных Заявки (Полный контейнер). Отправка Заявки на рассмотрение")
    @Link(name = "Test_03_07_02_2_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123878854")

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
        step09();
    }

    @Step("Авторизация")
    public void step01() {
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("test-otr@yandex.ru")
                .inField("Пароль").inputValue("Password1!")
                .clickButton("Войти");

        setCode("1234");

        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    private void setCode(String code) {
        int n = 0;
        while (n < code.length()) {
            $x("//input[@data-id=" + n + "]").sendKeys(code.substring(n, n + 1));
            n++;
        }
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
        if ($x("//*[contains(text(), 'Продолжить')]").isDisplayed()) {
            new GUIFunctions().clickButton("Продолжить");
        }

    }

    private void refreshTab(int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if ($x("//*[contains(text(), 'Продолжить')]").isDisplayed()
                    || $x("//*[contains(text(), 'Информация о заявителе')]").isDisplayed()) {
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
        if (departureDate.equals("")){
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

        String removedName = P.getProperty("1.Наименование продукции");

        new GUIFunctions().inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .inField("Наименование продукции").selectValue(removedName).assertNoControl()
                .inField("Код ТН ВЭД").assertValue(P.getProperty("1.Код ТН ВЭД")).assertUneditable().assertNoControl()
                .inField("Вес продукции, кг").inputValue(P.getProperty("1.Вес продукции")).assertNoControl()
                .inField("Упаковка").selectValue(P.getProperty("1.Упаковка")).assertNoControl()
                .inField("Температурный режим (от -30°C до 0°C или от 0°C до +30°C) ").setCheckboxON().assertNoControl()
                .inField("От").inputValue(P.getProperty("1.От")).assertNoControl()
                .inField("До").inputValue(P.getProperty("1.До")).assertNoControl()
                .inField("Количество контейнеров").inputValue(P.getProperty("1.Количество контейнеров")).assertNoControl()
                .inField("Тип контейнера").selectValue(P.getProperty("1.Тип контейнера")).assertNoControl()
                .clickButton("Сохранить");

        new GUIFunctions().inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .clickButton("Добавить новую")
                .inField("Наименование продукции").inputValue(P.getProperty("2.Наименование продукции")).assertNoControl()
                .inField("Код ТН ВЭД").selectValue(P.getProperty("2.Код ТН ВЭД")).assertNoControl()
                .inField("Вес продукции, кг").inputValue(P.getProperty("2.Вес продукции")).assertNoControl()
                .inField("Упаковка").selectValue(P.getProperty("2.Упаковка")).assertNoControl()
                .inField("Количество контейнеров").inputValue(P.getProperty("2.Количество контейнеров")).assertNoControl()
                .inField("Тип контейнера").selectValue(P.getProperty("2.Тип контейнера")).assertNoControl()
                .clickButton("Сохранить");

        $x("//td[text()='" + removedName + "']/ancestor::tr[1]//button").click();
        $x("//td[text()='" + removedName + "']/ancestor::tr[1]//span[contains(text(), 'Удалить')]").click();
        new GUIFunctions().waitForElementDisappeared("//td[text()='" + removedName + "']/ancestor::tr[1]");
    }

    @Step("Заполнить область «Информация о грузополучаеле»")
    public void step07() {
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue(P.getProperty("Наименование грузополучателя")).assertNoControl()
                .inField("Страна").inputValue(P.getProperty("Страна")).assertNoControl()
                .inField("Город").inputValue(P.getProperty("Город")).assertNoControl()
                .inField("Дом").inputValue(P.getProperty("Дом")).assertNoControl()
                .inField("Регион").inputValue(P.getProperty("Регион")).assertNoControl()
                .inField("Населенный пункт").inputValue(P.getProperty("Населенный пункт")).assertNoControl()
                .inField("Район").inputValue(P.getProperty("Район")).assertNoControl()
                .inField("Улица").inputValue(P.getProperty("Улица")).assertNoControl()
                .inField("Офис").inputValue(P.getProperty("Офис")).assertNoControl()
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
    }

    @Step("Отправка Заявки на рассмотрение ")
    public void step09() {
        String docNum = $x("//div[contains (@class, 'FormHeader_title' )]//span[contains (@class, 'Typography_body' )]").getText().split("№")[1];

        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Заявка отправлена на рассмотрение. Срок рассмотрения до 3 рабочих дней']");

        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }
}


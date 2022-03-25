package ru.exportcenter.dev.agroexpress;

import functions.gui.GUIFunctions;
import framework.RunTestAgain;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.dev.HooksDEV;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_03_07_01 extends HooksDEV {

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
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти");

        new GUIFunctions().waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
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
                .inField("Почтовый адрес").inputValue("Корнилаева 2").assertNoControl();
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step04() {
        new GUIFunctions().inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertNoControl()
                .inField("ФИО").inputValue("Иванов Иван Иванович").assertNoControl()
                .inField("Телефон").inputValue("+7(999)999-99-99").assertNoControl()
                .inField("Должность").inputValue("Менеджер").assertNoControl()
                .inField("Email").inputValue("word@mail.ru").assertNoControl();
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step05() {
        new GUIFunctions().inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue("Ярославль").assertNoControl()
                .inField("Город назначения").selectValue("Шанхай").assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Адрес").inputValue("Молодежная улица").assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue("22.10.2022").assertNoControl();
    }

    @Step("Заполнить область «Информация о грузе»")
    public void step06() {
        new GUIFunctions().inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .inField("Наименование продукции").selectValue("Новая продукция").assertNoControl()
                .inField("Код ТН ВЭД").assertValue("  Кофе").assertNoControl()
                .inField("Вес продукции, кг").inputValue("532,000").assertNoControl()
                .inField("Упаковка").selectValue("Фляги").assertNoControl()
                .inField("Количество контейнеров").inputValue("156").assertNoControl()
                .inField("Тип контейнера").selectValue("Универсальный").assertNoControl()
                .clickButton("Сохранить");
    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step07() {
        new GUIFunctions().inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue("Ss-password").assertNoControl()
                .inField("Страна").inputValue("USA").assertNoControl()
                .inField("Город").inputValue("Moscow").assertNoControl()
                .inField("Дом").inputValue("Ff").assertNoControl()
                .inField("Регион").inputValue("Raion").assertNoControl()
                .inField("Улица").inputValue("Lenina street").assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue("223 22 44 2").assertNoControl()
                .inField("Телефон").inputValue("+79999999999").assertNoControl()
                .inField("Представитель грузополучателя").inputValue("Moscow disco rule").assertNoControl()
                .inField("Email").inputValue("www@mail.ru").assertNoControl();
    }

    @Step("Заполнить область «Дополнительные услуги»")
    public void step08() {
        new GUIFunctions().inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Адрес").assertValue("Молодежная улица").assertEditable().assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления")
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов")
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов");

        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Заявка отправлена на рассмотрение. Срок рассмотрения до 3 рабочих дней']");
    }
}


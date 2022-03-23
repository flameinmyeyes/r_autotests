package ru.exportcenter.uidm_dev.agroexpress;

import framework.GUI.func.GUI;
import framework.RunTestAgain;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.uidm_dev.Hooks_UIDM_DEV;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_03_07_01 extends Hooks_UIDM_DEV {

    @Owner(value = "Максимова Диана")
    @Description("03 07 01 Сценарий 1")
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
    }

    @Step("Перейти к оформлению сервиса «Логистика. Доставка продукции «Агроэкспрессом»»")
    public void step01() {
        new GUI().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти");

        new GUI().waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");

        new GUI().selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить")
                .switchPageTo(1);

        refreshTab("//*[contains(text(), 'Продолжить')]", 15);

        new GUI().clickButton("Продолжить");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new GUI().waitForLoading();
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
        }
    }

    @Step("Заполнить область «Информация о компании»")
    public void step02() {
        new GUI().inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue("Корнилаева 2").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step03() {
        new GUI().inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("ФИО").inputValue("Иванов Иван Иванович").assertValue().assertNoControl()
                .inField("Телефон").inputValue("+7(999)999-99-99").assertValue().assertNoControl()
                .inField("Должность").inputValue("Менеджер").assertValue().assertNoControl()
                .inField("Email").inputValue("word@mail.ru").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step04() {
        new GUI().inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue("Ярославль").assertValue().assertNoControl()
                .inField("Город назначения").selectValue("Шанхай").assertValue().assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Адрес").inputValue("Молодежная улица").assertValue().assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue("22.10.2022").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация о грузе»")
    public void step05() {
        new GUI().inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .inField("Наименование продукции").selectValue("кофе").assertValue().assertNoControl()
                .inField("Вес продукции, кг").inputValue("532,000").assertValue().assertNoControl()
                .inField("Упаковка").selectValue("Фляги").assertValue().assertNoControl()
                .inField("Количество контейнеров").inputValue("156").assertValue().assertNoControl()
                .inField("Тип контейнера").selectValue("Универсальный").assertValue().assertNoControl()
                .clickButton("Сохранить");
    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step06() {
        new GUI().inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue("Ss-password").assertValue().assertNoControl()
                .inField("Страна").inputValue("USA").assertValue().assertNoControl()
                .inField("Город").inputValue("Moscow").assertValue().assertNoControl()
                .inField("Дом").inputValue("Ff").assertValue().assertNoControl()
                .inField("Регион").inputValue("Raion").assertValue().assertNoControl()
                .inField("Улица").inputValue("Lenina street").assertValue().assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue("223 22 44 2").assertValue().assertNoControl()
                .inField("Телефон").inputValue("+79999999999").assertValue().assertNoControl()
                .inField("Представитель грузополучателя").inputValue("Moscow disco rule").assertValue().assertNoControl()
                .inField("Email").inputValue("www@mail.ru").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Дополнительные услуги»")
    public void step07() {
        new GUI().inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Адрес").assertValueContains(" ")
                .inField("Таможенное оформление").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления").assertRadiobuttonONByDescription()
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов").assertRadiobuttonONByDescription()
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов").assertRadiobuttonONByDescription();

        new GUI().clickButton("Далее");
    }
}


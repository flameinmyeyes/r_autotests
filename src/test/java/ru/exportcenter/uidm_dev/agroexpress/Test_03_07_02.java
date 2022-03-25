package ru.exportcenter.uidm_dev.agroexpress;

import functions.gui.GUIFunctions;
import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.uidm_dev.Hooks_UIDM_DEV;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02 extends Hooks_UIDM_DEV {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_1\\";

    @Owner(value="Камаев Евгений")
    @Description("03 07 02 Сценарий 2")
    @Link(name="03 07 02", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902502")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot( WAY_TEST + "screen.png");
    }

    @Step("Шаг 1 Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти");

        new GUIFunctions().waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Шаг 2 Навигация")
    public void step02() {
        CommonFunctions.printStep();
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Подробнее");

        new GUIFunctions().clickButton("Получить услугу").switchPageTo(1);
        refreshTab("//*[contains(text(), 'Продолжить')]", 15);
        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Шаг 3 Блок «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions().inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue("Корнилаева 2").assertValue().assertNoControl();
    }

    @Step("Шаг 4 Блок «Информация о заявителе»" )
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions().inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("ФИО").inputValue("Иванов Иван Иванович").assertValue().assertNoControl()
                .inField("Телефон").inputValue("+7(999)999-99-99").assertValue().assertNoControl()
                .inField("Должность").inputValue("Менеджер").assertValue().assertNoControl()
                .inField("Email").inputValue("word@mail.ru").assertValue().assertNoControl();
    }

    @Step("Шаг 5 Блок  «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions().inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue("Ярославль").assertValue().assertNoControl()
                .inField("Город назначения").selectValue("Шанхай").assertValue().assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Адрес").inputValue("Молодежная улица").assertValue().assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue("22.10.2022").assertValue().assertNoControl();
    }

    @Step("Шаг 6 Блок «Информация о грузе»")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions().inContainer("Информация о грузе")
                .clickButton("Сборный груз")
                .clickButton("Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ")
                .inField("От").inputValue("-25")
                .inField("До").inputValue("+2")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .inField("Наименование продукции").selectValue("Новая продукция").assertNoControl()
                .inField("Код ТН ВЭД").assertValue("  Кофе").assertNoControl()
                .inField("Вес продукции, кг").inputValue("4,000").assertValue().assertNoControl()
                .inField("Упаковка").selectValue("Фляги").assertValue().assertNoControl()
                .inField("Длина, см").inputValue("15").assertValue().assertNoControl()
                .inField("Ширина, см").inputValue("2345").assertValue().assertNoControl()
                .inField("Высота, см").inputValue("113").assertValue().assertNoControl()
                .inField("Количество грузовых мест, шт").inputValue("2222").assertValue().assertNoControl()
                .clickButton("Сохранить");
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Заявка отправлена на рассмотрение. Срок рассмотрения до 3 рабочих дней']");

    }

    @Step("Шаг 7 Блок  «Информация о грузополучателе»")
    public void step07() {
        new GUIFunctions().inContainer("Информация о грузополучателе")
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

    @Step("Шаг 8 Блок «Дополнительные услуги»")
    public void step08() {
        new GUIFunctions().inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Адрес").assertValue("Молодежная улица").assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления").assertRadiobuttonONByDescription()
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов").assertRadiobuttonONByDescription()
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов").assertRadiobuttonONByDescription();
        new GUIFunctions().clickButton("Далее");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            CommonFunctions.wait(1);
        }

    }

}

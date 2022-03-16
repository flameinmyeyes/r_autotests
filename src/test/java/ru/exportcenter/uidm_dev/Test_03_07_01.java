package ru.exportcenter.uidm_dev;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import functional.CommonFunctions;
import functional.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_01 extends Hooks_UIDM_DEV {

    @Description("03 07 01 Сценарий 1")
    @Owner(value = "Максимова Диана")
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
    private void step01() {
        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions().authorization("demo_exporter", "password");

        //Перейти во вкладку «Сервисы»
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business");

        //Выбрать сервис «Логистика. Доставка продукции «Агроэкспрессом»» и нажать кнопку «Оформить»
        new GUIFunctions().waitForElementDisplayed("//*[contains(@class, 'preloader')]", 60)
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить");

        //Нажать на кнопку «Продолжить»
        switchTo().window(1);
        refreshTab("//*[contains(text(), 'Продолжить')]", 15);
        new GUIFunctions().clickWebElement("//*[contains(text(), 'Продолжить')]");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            $x("//*[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']").shouldBe(Condition.visible, Duration.ofSeconds(30));
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            CommonFunctions.wait(1);
        }
    }

    @Step("Заполнить область «Информация о компании»")
    private void step02() {
        new GUIFunctions().inputValueInArea("Информация о компании", "Почтовый адрес", "Корнилаева 2");
    }

    @Step("Заполнить область «Информация о заявителе»")
    private void step03() {
        new GUIFunctions().setCheckboxInArea("Информация о заявителе", "Дополнительный контакт", true)
                .inputValueInArea("Информация о заявителе", "ФИО", "Иванов Иван Иванович")
                .inputValueInArea("Информация о заявителе", "Телефон", "+7(999)999-99-99")
                .inputValueInArea("Информация о заявителе", "Должность", "Менеджер")
                .inputValueInArea("Информация о заявителе", "Email", "word@mail.ru");
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    private void step04() {
        new GUIFunctions().selectValueFromDropdownInArea("Информация для оказания услуги", "Город отправления", "Ярославль")
                .selectValueFromDropdownInArea("Информация для оказания услуги", "Город назначения", "Шанхай")
                .setCheckboxInArea("Информация для оказания услуги", "Вывоз груза с адреса («Первая миля»)", true)
                .inputValueInArea("Информация для оказания услуги", "Адрес", "Молодежная улица")
                .inputValueInArea("Информация для оказания услуги", "Предполагаемая дата отправления груза", "22.10.2022");
    }

    @Step("Заполнить область «Информация о грузе»")
    private void step05() {
        new GUIFunctions().clickButtonInArea("Информация о грузе", "Добавить +")
                .selectValueFromDropdownInArea("Сведения о продукции", "Наименование продукции", "кофе");
        Assert.assertEquals($x("//*[@placeholder='Выберите код ТН ВЭД']").getValue(), "  Кофе");

        new GUIFunctions().inputValueInArea("Сведения о продукции", "Вес продукции, кг", "532,000")
                .selectValueFromDropdownInArea("Сведения о продукции", "Упаковка", "Фляги")
                .inputValueInArea("Сведения о продукции", "Количество контейнеров", "156")
                .selectValueFromDropdownInArea("Сведения о продукции", "Тип контейнера", "Универсальный")
                .clickButtonInArea("Сведения о продукции", "Сохранить");
    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions().inputValueInArea("Информация о грузополучателе", "Наименование грузополучателя", "Ss-password")
                .inputValueInArea("Информация о грузополучателе", "Страна", "USA")
                .inputValueInArea("Информация о грузополучателе", "Город", "Moscow")
                .inputValueInArea("Информация о грузополучателе", "Дом", "Ff")
                .inputValueInArea("Информация о грузополучателе", "Регион", "St-Peterburg")
                .inputValueInArea("Информация о грузополучателе", "Район", "Raion")
                .inputValueInArea("Информация о грузополучателе", "Улица", "Lenina street")
                .inputValueInArea("Информация о грузополучателе", "Регистрационный номер грузополучателя", "223 22 44 2")
                .inputValueInArea("Информация о грузополучателе", "Телефон", "+79999999999")
                .inputValueInArea("Информация о грузополучателе", "Представитель грузополучателя", "Moscow disco rule")
                .inputValueInArea("Информация о грузополучателе", "Email", "www@mail.ru");
    }

    @Step("Заполнить область «Дополнительные услуги»")
    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions().setCheckboxInArea("Дополнительные услуги", "Вывоз груза с адреса («Первая миля»)", true)
                .setCheckboxInArea("Дополнительные услуги", "Таможенное оформление", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Таможенное оформление", "РЭЦ")
                .setCheckboxInArea("Дополнительные услуги", "Оформление ветеринарного сертификата", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Оформление ветеринарного сертификата", "РЭЦ")
                .setCheckboxInArea("Дополнительные услуги", "Оформление фитосанитарного сертификата", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Оформление фитосанитарного сертификата", "РЭЦ")
                .clickButton("Далее");
    }
}


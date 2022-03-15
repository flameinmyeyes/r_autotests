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

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_04 extends Hooks_UIDM_DEV {

//    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_1\\";

//    private GUIFunctions guiFunctions = new GUIFunctions();

    @Owner(value="Ворожко Александр")
    @Description("03 07 04 Сценарий 4")
    @Link(name="Test_03_07_04", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902512")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
//        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .authorization("demo_exporter", "password")
                .selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
                .waitForElementDisplayed("//*[contains(@class, 'preloader')]", 60)
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить");

        switchTo().window(1);
        new GUIFunctions().waitForLoading(120);

        if (!$x("//*[contains(text(), 'Продолжить')]").isDisplayed()) {
            refreshTab("//*[contains(text(), 'Продолжить')]", 20);
        }
        new GUIFunctions()
                .clickWebElement("//*[contains(text(), 'Продолжить')]");
    }

    @Step("Заполнить область «Информация о компании»")
    public void step02() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inputValueInArea("Информация о компании", "Почтовый адрес", "Корнилаева 2");
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .setCheckboxInArea("Информация о заявителе", "Дополнительный контакт", true)
                .inputValueInArea("Информация о заявителе", "ФИО", "Иванов Иван Иванович")
                .inputValueInArea("Информация о заявителе", "Телефон", "+7(999)999-99-99")
                .inputValueInArea("Информация о заявителе", "Должность", "Менеджер")
                .inputValueInArea("Информация о заявителе", "Email", "word@mail.ru");
        checkAreaForErrors("Информация о заявителе");
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .selectValueFromDropdownInArea("Информация для оказания услуги", "Город отправления", "Ульяновск")
                .selectValueFromDropdownInArea("Информация для оказания услуги", "Город назначения", "Харбин")
                .setCheckboxInArea("Информация для оказания услуги", "Вывоз груза с адреса («Первая миля»)", true)
                .inputValueInArea("Информация для оказания услуги", "Адрес", "Молодежная улица")
                .inputValueInArea("Информация для оказания услуги", "Предполагаемая дата отправления груза", "22.10.2022");
        checkAreaForErrors("Информация для оказания услуги");
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .clickButtonInArea("Информация о грузе", "Сборный груз")
                .setCheckboxInArea("Информация о грузе", "Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ", true)
                .inputValueInArea("Информация о грузе", "От", "-2")
                .inputValueInArea("Информация о грузе", "До", "+30")
                .clickButtonInArea("Информация о грузе", "Добавить +");
        checkAreaForErrors("Информация о грузе");

        //Модальный контейнер
        new GUIFunctions()
                .clickButtonInArea("Сведения о продукции", "Добавить новую")
                .inputValueInArea("Сведения о продукции", "Наименование продукции", "Продукция")
                .selectValueFromDropdownInArea("Сведения о продукции", "Код ТН ВЭД", "Сыры и творог")
                .inputValueInArea("Сведения о продукции", "Вес продукции, кг", "15,000")
                .selectValueFromDropdownInArea("Сведения о продукции", "Упаковка", "Ящики 1-5кг")
                .inputValueInArea("Сведения о продукции", "Длина, см", "224")
                .inputValueInArea("Сведения о продукции", "Ширина, см", "45")
                .inputValueInArea("Сведения о продукции", "Высота, см", "122")
                .inputValueInArea("Сведения о продукции", "Количество грузовых мест, шт", "226")
                .clickButtonInArea("Сведения о продукции", "Сохранить");
        checkAreaForErrors("Сведения о продукции");
    }

    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
            .inputValueInArea("Информация о грузополучателе", "Наименование грузополучателя", "Ss-password")
            .inputValueInArea("Информация о грузополучателе", "Страна", "USA")
            .inputValueInArea("Информация о грузополучателе", "Город", "Moscow")
            .inputValueInArea("Информация о грузополучателе", "Дом", "Ff")
            .inputValueInArea("Информация о грузополучателе", "Регион", "St-Peterburg")
            .inputValueInArea("Информация о грузополучателе", "Район", "Raion")
            .inputValueInArea("Информация о грузополучателе", "Регистрационный номер грузополучателя", "223 22 44 2")
            .inputValueInArea("Информация о грузополучателе", "Телефон", "+79999999999")
            .inputValueInArea("Информация о грузополучателе", "Представитель грузополучателя", "Moscow disco rule")
            .inputValueInArea("Информация о грузополучателе", "Email", "www@mail.ru");
        checkAreaForErrors("Информация о грузополучателе");
    }

    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .setCheckboxInArea("Дополнительные услуги", "Вывоз груза с адреса («Первая миля»)", true)
                .setCheckboxInArea("Дополнительные услуги", "Таможенное оформление", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Таможенное оформление", "РЖД Логистика")
                .setCheckboxInArea("Дополнительные услуги", "Оформление ветеринарного сертификата", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Оформление ветеринарного сертификата", "РЖД Логистика")
                .setCheckboxInArea("Дополнительные услуги", "Оформление фитосанитарного сертификата", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Оформление фитосанитарного сертификата", "РЖД Логистика")
                .clickButton("Далее");
        checkAreaForErrors("Дополнительные услуги");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }

    public void checkAreaForErrors(String area) {
        String xpathToArea = "//*[text() = '" + area + "']/ancestor::*[contains(@class, 'container')][1]//*[contains(@class, 'error')]//*[contains(@class, 'error')]";
        $x(xpathToArea).shouldNot(Condition.exist);
    }
}

//*[text() = 'Оформление ветеринарного сертификата']/ancestor::div[contains(@class, 'WithExpansionPanel')][1]/div[descendant::*[text() = 'Оформление ветеринарного сертификата']]/following-sibling::div[descendant::*[contains(text(), 'РЖД Логистика')]][1]//input[@type = 'radio']
//*[text() = 'Дополнительные услуги']/ancestor::div[contains(@class, 'WithExpansionPanel')]//div[contains(@class, 'WithExpansionPanel')]/div[descendant::*[text() = 'Таможенное оформление']]/following-sibling::div[descendant::*[contains(text(), 'РЖД Логистика')]][1]//input[@type = 'radio']
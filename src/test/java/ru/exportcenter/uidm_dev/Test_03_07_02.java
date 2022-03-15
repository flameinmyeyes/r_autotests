package ru.exportcenter.uidm_dev;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
import functional.GUIFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

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
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot( WAY_TEST + "screen.png");
    }

    @Step("Авторизация и навигация")
    public void step01() {
        CommonFunctions.printStep();
        // Ввести логин и пароль demo_exporter/password
        new GUIFunctions().authorization("demo_exporter", "password");

        //Перейти во вкладку «Сервисы»
        new GUIFunctions()
                .selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business");

        //Выбрать сервис «Логистика. Доставка продукции «Агроэкспрессом»» и нажать кнопку «Оформить»
        new GUIFunctions()
                .waitForElementDisplayed("//*[contains(@class, 'preloader')]", 60)
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Подробнее");
        $x("//a[text()='Получить услугу']").click();
        switchTo().window(1);
        while (!$x("//button//span[text()='Продолжить']").isDisplayed()) {
            CommonFunctions.wait(3);
            refresh();
            $x("//*[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']").shouldBe(Condition.visible, Duration.ofSeconds(60));
        }
        $x("//button//span[text()='Продолжить']").click();
        $x("//span[text()='Заявка на услугу']").shouldBe(Condition.visible, Duration.ofSeconds(60));
    }

    @Step("«Информация о компании»")
    public void step02() {
        CommonFunctions.printStep();
        //Заполнить поле адрес
        new GUIFunctions().inputValueInArea("Информация о компании", "Почтовый адрес", "Корнилаева 2");
        //Проверить отсуствие срабатывания контроля
        $x("//span[contains (@class, 'KrInput_message')]").shouldNotBe(Condition.visible, Duration.ofSeconds(3));
        //Проверить что блок заполнен
        $x("//div[text() = 'Заполнено']/../../../div[text()= 'Информация о компании']").
                shouldBe(Condition.visible, Duration.ofSeconds(3));
    }

    @Step("  «Информация о заявителе»"
            )
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .setCheckboxInArea("Информация о заявителе", "Дополнительный контакт", true)
                .inputValueInArea("Информация о заявителе", "ФИО", "Иванов Иван Иванович")
                .inputValueInArea("Информация о заявителе", "Телефон", "+7(999)999-99-99")
                .inputValueInArea("Информация о заявителе", "Должность", "Менеджер")
                .inputValueInArea("Информация о заявителе", "Email", "word@mail.ru");
        $x("//span[contains (@class, 'KrInput_message')]").shouldNotBe(Condition.visible, Duration.ofSeconds(3));
        $x("//div[text() = 'Заполнено']/../../../div[text()= 'Информация о заявителе']").
                shouldBe(Condition.visible, Duration.ofSeconds(3));
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .selectValueFromDropdownInArea("Информация для оказания услуги", "Город отправления", "Уфа")
                .selectValueFromDropdownInArea("Информация для оказания услуги", "Город назначения", "Харбин")
                .setCheckboxInArea("Информация для оказания услуги", "Вывоз груза с адреса («Первая миля»)", true)
                .inputValueInArea("Информация для оказания услуги", "Адрес", "Молодежная улица")
                .inputValueInArea("Информация для оказания услуги", "Предполагаемая дата отправления груза", "22.10.2022");
    }

    @Step("Заполнить область «Информация о грузе»")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButtonInArea("Информация о грузе", "Сборный груз")
                .clickButtonInArea("Информация о грузе", "Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ")
                .inputValueInArea("Информация о грузе", "От", "-25")
                .inputValueInArea("Информация о грузе", "До", "+2")
                .clickButtonInArea("Информация о грузе", "Добавить +")
                //.clickButtonInArea("Сведения о продукции", "Выбрать из каталога")
                .selectValueFromDropdownInArea("Сведения о продукции", "Наименование продукции", "Чудо шоколадное")
                //.clickButtonInArea("Сведения о продукции", "Добавить новую")
                .waitForElementDisplayed("//input[@placeholder = 'Выберите код ТН ВЭД']" +
                        "[@value = '97030000 ПОДЛИННИКИ СКУЛЬПТУР И СТАТУЭТОК ИЗ ЛЮБЫХ МАТЕРИАЛОВ'][@disabled]", 5)
                .inputValueInArea("Сведения о продукции", "Вес продукции, кг", "4,000")
                .selectValueFromDropdownInArea("Сведения о продукции", "Упаковка", "Фляги") /*Бидоны*/
                .inputValueInArea("Сведения о продукции", "Длина, см", "15")
                .inputValueInArea("Сведения о продукции", "Ширина, см", "2345")
                .inputValueInArea("Сведения о продукции", "Высота, см", "113")
                .inputValueInArea("Сведения о продукции", "Количество грузовых мест, шт", "2222")
                .clickButtonInArea("Сведения о продукции", "Сохранить");

    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step06() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inputValueInArea("Информация о грузополучателе", "Наименование грузополучателя", "Ss-password")
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
        new GUIFunctions()
                .setCheckboxInArea("Дополнительные услуги", "Вывоз груза с адреса («Первая миля»)", true)
                .setCheckboxInArea("Дополнительные услуги", "Таможенное оформление", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Таможенное оформление", "РЭЦ")
                .setCheckboxInArea("Дополнительные услуги", "Оформление ветеринарного сертификата", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Оформление ветеринарного сертификата", "РЭЦ")
                .setCheckboxInArea("Дополнительные услуги", "Оформление фитосанитарного сертификата", true)
                .setRadiobuttonUnderСheckboxInArea("Дополнительные услуги", "Оформление фитосанитарного сертификата", "РЭЦ")
                .clickButton("Далее");

    }

}

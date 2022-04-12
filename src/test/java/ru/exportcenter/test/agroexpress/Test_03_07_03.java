package ru.exportcenter.test.agroexpress;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
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

//АРХИВ

@Deprecated
public class Test_03_07_03 extends HooksTEST {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_03_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Балашов Илья")
    @Description("03 07 03 Заполнение Заявки на получение услуги. Полный контейнер. Новая продукция")
    @Link(name="Test_03_07_03", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902506")

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

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForLoading()
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        new GUIFunctions()
                .selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить")
                .switchPageTo(1);

        refreshTab("//*[contains(text(), 'Продолжить')]", 60);

        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Блок «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue(PROPERTIES.getProperty("Информация о компании.Почтовый адрес")).assertNoControl();
    }

    @Step("Блок «Информация о заявителе»")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertNoControl()
                .inField("ФИО").inputValue(PROPERTIES.getProperty("Информация о заявителе.ФИО")).assertNoControl()
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Информация о заявителе.Телефон")).assertNoControl()
                .inField("Должность").inputValue(PROPERTIES.getProperty("Информация о заявителе.Должность")).assertNoControl()
                .inField("Email").inputValue(PROPERTIES.getProperty("Информация о заявителе.Email")).assertNoControl();
    }

    @Step("Блок  «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue(PROPERTIES.getProperty("Информация для оказания услуги.Город отправления")).assertNoControl()
                .inField("Город назначения").selectValue(PROPERTIES.getProperty("Информация для оказания услуги.Город назначения")).assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON()
                .inField("Адрес").inputValue(PROPERTIES.getProperty("Информация для оказания услуги.Адрес")).assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue(PROPERTIES.getProperty("Информация для оказания услуги.Предполагаемая дата отправления груза")).assertNoControl();
    }

    @Step("Блок «Информация о грузе»")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .clickButton("Добавить новую")
                .inField("Наименование продукции").inputValue(PROPERTIES.getProperty("Информация о грузе.Наименование продукции")).assertNoControl()
                .inField("Код ТН ВЭД").selectValue(PROPERTIES.getProperty("Информация о грузе.Код ТН ВЭД")).assertNoControl()
                .inField("Вес продукции, кг").inputValue(PROPERTIES.getProperty("Информация о грузе.Вес продукции, кг")).assertNoControl()
                .inField("Упаковка").selectValue(PROPERTIES.getProperty("Информация о грузе.Упаковка")).assertNoControl()
                .inField("Температурный режим (от -30°C до 0°C или от 0°C до +30°C) ").setCheckboxON().assertNoControl()
                .inField("От").inputValue(PROPERTIES.getProperty("Информация о грузе.От")).assertNoControl()
                .inField("До").inputValue(PROPERTIES.getProperty("Информация о грузе.До")).assertNoControl()
                .inField("Количество контейнеров").inputValue(PROPERTIES.getProperty("Информация о грузе.Количество контейнеров")).assertNoControl()
                .inField("Тип контейнера").selectValue(PROPERTIES.getProperty("Информация о грузе.Тип контейнера")).assertNoControl()
                .clickButton("Сохранить");
    }

    @Step("Блок  «Информация о грузополучателе»")
    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Наименование грузополучателя")).assertValue().assertNoControl()
                .inField("Страна").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Страна")).assertValue().assertNoControl()
                .inField("Город").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Город")).assertValue().assertNoControl()
                .inField("Дом").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Дом")).assertValue().assertNoControl()
                .inField("Регион").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Регион")).assertValue().assertNoControl()
                .inField("Район").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Район")).assertValue().assertNoControl()
                .inField("Улица").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Улица")).assertValue().assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Регистрационный номер грузополучателя")).assertValue().assertNoControl()
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Телефон")).assertValue().assertNoControl()
                .inField("Представитель грузополучателя").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Представитель грузополучателя")).assertValue().assertNoControl()
                .inField("Email").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Email")).assertValue().assertNoControl();
    }

    @Step("Блок «Дополнительные услуги»")
    public void step08() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления").assertRadiobuttonONByDescription()
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов").assertRadiobuttonONByDescription()
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов").assertRadiobuttonONByDescription();

        new GUIFunctions().clickButton("Далее");
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            CommonFunctions.wait(1);
        }
    }

}

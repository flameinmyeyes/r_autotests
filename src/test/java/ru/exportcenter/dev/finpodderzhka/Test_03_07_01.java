package ru.exportcenter.dev.finpodderzhka;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import ru.exportcenter.Hooks;

import java.util.Properties;

public class Test_03_07_01  extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_3/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_02_3_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Ворожко Александр")
    @Description("03 07 04 Сценарий 4")
    @Link(name="Test_03_07_04", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902512")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
//        step02();
//        step03();
//        step04();
//        step05();
//        step06();
//        step07();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();


        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");

//
//        new GUIFunctions()
//                .inContainer("Вход в личный кабинет")
//                .inField("Email").inputValue("demo_exporter")
//                .inField("Пароль").inputValue("password")
//                .clickButton("Войти")
//                .waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main")
//                .selectTab("Сервисы")
//                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
//                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
//                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить")
//                .switchPageTo(1)
//                .waitForLoading();

        refreshTab("//*[contains(text(), 'Продолжить')]", 20);
        new GUIFunctions()
                .clickButton("Продолжить");
    }

    @Step("Заполнить область «Информация о компании»")
    public void step02() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue("Корнилаева 2").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON()
                .inField("ФИО").inputValue("Иванов Иван Иванович").assertValue().assertNoControl()
                .inField("Телефон").inputValue("+7(999)999-99-99").assertValue().assertNoControl()
                .inField("Должность").inputValue("Менеджер").assertValue().assertNoControl()
                .inField("Email").inputValue("word@mail.ru").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue("Ульяновск").assertValue().assertNoControl()
                .inField("Город назначения").selectValue("Харбин").assertValue().assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON()
                .inField("Адрес").inputValue("Молодежная улица").assertValue().assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue("22.10.2022").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузе")
                .clickButton("Сборный груз")
                .inField("Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ").setCheckboxON()
                .inField("От").inputValue("-2").assertValue().assertNoControl()
                .inField("До").inputValue("+30").assertValue().assertNoControl()
                .clickButton("Добавить +")

                //Модальный контейнер
                .inContainer("Сведения о продукции")
                .clickButton("Добавить новую")
                .inField("Наименование продукции").inputValue("Продукция").assertValue().assertNoControl()
                .inField("Код ТН ВЭД").selectValue("Сыры и творог").assertValue().assertNoControl()
                .inField("Вес продукции, кг").inputValue("15,000").assertValue().assertNoControl()
                .inField("Упаковка").selectValue("Поддоны").assertValue().assertNoControl()
                .inField("Длина, см").inputValue("224").assertValue().assertNoControl()
                .inField("Ширина, см").inputValue("45").assertValue().assertNoControl()
                .inField("Высота, см").inputValue("122").assertValue().assertNoControl()
                .inField("Количество грузовых мест, шт").inputValue("226").assertValue().assertNoControl()
                .clickButton("Сохранить");
    }

    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue("Ss-password").assertValue().assertNoControl()
                .inField("Страна").inputValue("USA").assertValue().assertNoControl()
                .inField("Город").inputValue("Moscow").assertValue().assertNoControl()
                .inField("Дом").inputValue("Ff").assertValue().assertNoControl()
                .inField("Регион").inputValue("St-Peterburg").assertValue().assertNoControl()
                .inField("Район").inputValue("Raion").assertValue().assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue("223 22 44 2").assertValue().assertNoControl()
                .inField("Телефон").inputValue("+79999999999").assertValue().assertNoControl()
                .inField("Представитель грузополучателя").inputValue("Moscow disco rule").assertValue().assertNoControl()
                .inField("Email").inputValue("www@mail.ru").assertValue().assertNoControl();
    }

    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON()
                .inField("Адрес").assertValue("Молодежная улица")
                .inField("Таможенное оформление").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления").assertRadiobuttonONByDescription()
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов").assertRadiobuttonONByDescription()
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов").assertRadiobuttonONByDescription();

        new GUIFunctions()
                .clickButton("Далее");
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
}

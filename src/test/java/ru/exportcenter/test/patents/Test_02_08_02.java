package ru.exportcenter.test.patents;

import framework.RunTestAgain;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_02_08_02 extends HooksTEST {

    @Owner(value = "Ворожко Александр")
    @Description("ТК 02 08 02")
    @Link(name = "Test_02_08_02", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123868178")

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
        new GUIFunctions()
                .inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти")
                .waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом")
                .inContainer("Каталог сервисов")
                        .clickButton("Государственные")
                .openSearchResult("Компенсация части затрат на регистрацию ОИС за рубежом", "Оформить")
                .switchPageTo(1)
                .waitForLoading();

        refreshTab(15);

        new GUIFunctions()
                .clickButton("Продолжить");
    }

    @Step("Заполнить область «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Сведения о заявителе")
                        .inField("Регион").selectValue("Республика Коми").assertValue().assertNoControl()
                .inContainer("Контакты заявителя")
                        .inField("ИНН").inputValue("12345676").assertValue().assertNoControl()
                        .inField("СНИЛС").inputValue("12345676").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Контакты заявителя")
                        .inField("Доверенное лицо").setCheckboxON().assertCheckboxON().assertNoControl();

//        new GUIFunctions()
//                .uploadFileInArea("Загрузите документ", "Загрузить доверенность", "C:\\test\\0-1.pdf");

//        CommonFunctions.wait(60);

//        new GUI()
//                .inContainer("Контакты заявителя")
//                        .inField("Номер доверенности").inputValue("1234554321").assertValue().assertNoControl()
//                        .inField("Дата выдачи").inputValue("02.02.2022").assertValue().assertNoControl()
//                        .inField("Срок действия выдачи").inputValue("02.02.2022").assertValue().assertNoControl()
//                        .inField("Контактное лицо").setCheckboxON().assertCheckboxON().assertNoControl()
//                        .inField("ФИО").selectValue("Иванов Иван Иванович").assertValue().assertNoControl();
//
//        new GUI()
//                .clickButton("Далее")
//                .waitForLoading();
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация для оказания услуги")
                        .inField("Город отправления").selectValue("Ярославль").assertValue().assertNoControl()
                        .inField("Город назначения").selectValue("Шанхай").assertValue().assertNoControl()
                        .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertCheckboxON().assertNoControl()
                        .inField("Адрес").inputValue("Молодежная улица").assertValue().assertNoControl()
                        .inField("Предполагаемая дата отправления груза").inputValue("22.10.2022").assertValue().assertNoControl();
    }

    @Step("Заполнить область «Информация о грузе»")
    public void step06() {
        new GUIFunctions()
                .inContainer("Информация о грузе")
                        .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                        .inField("Наименование продукции").selectValue("Новая продукция").assertValue().assertNoControl()
                        .inField("Код ТН ВЭД").assertValue("  Кофе").assertNoControl()
                        .inField("Вес продукции, кг").inputValue("532,000").assertValue().assertNoControl()
                        .inField("Упаковка").selectValue("Фляги").assertValue().assertNoControl()
                        .inField("Количество контейнеров").inputValue("156").assertValue().assertNoControl()
                        .inField("Тип контейнера").selectValue("Универсальный").assertValue().assertNoControl()
                        .clickButton("Сохранить");
    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step07() {
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
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
    public void step08() {
        new GUIFunctions()
                .inContainer("Дополнительные услуги")
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

    private void refreshTab(int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if ($x("//*[contains(text(), 'Продолжить')]").isDisplayed()) {
                break;
            }
            refresh();
        }
    }
}

//*[text() = 'Сведения о заявителе']/ancestor::div[contains(@class, 'container')][1]//*[text() = 'Доверенное лицо']/ancestor::div[contains(@class,'Column_col') or contains(@class, 'inputWrapper') or contains(@class, 'Input_fullWidth')][1]//div[contains(@class,'checkMark')]
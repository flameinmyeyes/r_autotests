package ru.exportcenter.uidm_dev.agroexpress;

import framework.GUI.func.GUI;
import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
import functional.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.uidm_dev.Hooks_UIDM_DEV;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_03 extends Hooks_UIDM_DEV {

//    private String WAY_TEST = Ways.UIDM_DEV.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_1\\";

    @Owner(value="Балашов Илья")
    @Description("03 07 03 Заполнение Заявки на получение услуги (сценарий 3)")
    @Link(name="Test_03_07_03", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902506")

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
        step08();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Авторизация")
    public void step01() {
        //В браузере перейти по ссылке http://uidm.uidm-dev.d.exportcenter.ru/ru/login
        //Ввести логин и пароль demo_exporter/password, нажать «Войти»
        new GUI()
                .inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти");

        new GUI().waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        //Перейти во вкладку «Сервисы»
        //Найти сервис «Логистика. Доставка продукции "Агроэкспрессом"» и нажать «Оформить»
        //Перезагрузить страницу
        //Нажать кнопку «Продолжить»
        new GUI()
                .selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить")
                .switchPageTo(1);

        refreshTab("//*[contains(text(), 'Продолжить')]", 60);

        new GUI().clickButton("Продолжить");
    }

    @Step("Блок «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();
        //В поле «Почтовый адрес» вводим значение «Корнилаева 2»
        new GUI()
                .inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue("Корнилаева 2").assertNoControl();
    }

    @Step("Блок «Информация о заявителе»")
    public void step04() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Дополнительный контакт»
        //В поле «ФИО» вводим значение «Иванов Иван Иванович»
        //В поле «Телефон» вводим значение «+7(999)999-99-99»
        //В поле «Должность» вводим значение «Менеджер»
        //В поле «Email» вводим значение «word@mail.ru»
        new GUI()
                .inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertNoControl()
                .inField("ФИО").inputValue("Иванов Иван Иванович").assertNoControl()
                .inField("Телефон").inputValue("+7(999)999-99-99").assertNoControl()
                .inField("Должность").inputValue("Менеджер").assertNoControl()
                .inField("Email").inputValue("word@mail.ru").assertNoControl();
    }

    @Step("Блок  «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();

        //В поле «Город отправителя» выбрать значение из выпадающего списка.        //Выбираем «Тула»
        //В поле «Город назначения» выбрать значение из выпадающего списка.        //Выбираем «Шанхай»
        //Нажать на кнопку «Вывоз груза с адреса («Первая миля»)»
        //В поле «Адрес» вводим значение «Молодежная улица»
        //В поле «Предполагаемая дата отправления груза» вводим значение «22.10.2022»
        new GUI()
                .inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue("Тула").assertNoControl()
                .inField("Город назначения").selectValue("Шанхай").assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON()
                .inField("Адрес").inputValue("Молодежная улица").assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue("22.10.2022").assertNoControl();
    }

    @Step("Блок «Информация о грузе»")
    public void step06() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Полный контейнер»
        //Нажать на кнопку «Добавить +»
        //Нажать на кнопку «Добавить новую»
        //В поле «Наименование продукции» вводим значение «Новая Продукция»
        //В поле «Код ТН ВЭД» выбрать значение из выпадающего списка.        //Выбираем значение «Кофе»
        //В поле «Вес продукции, кг» вводим значение «16,000»
        //В поле «Упаковка» выбрать значение из выпадающего списка.        //Выбираем «Барабаны»
        //Нажать на кнопку «Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C)»
        //В поле «От» ввести значение «-15»
        //В поле «До» ввести значение «+27»
        //В поле «Количество контейнеров» вводим значение «16»
        //В поле «Тип контейнера» выбрать значение из выпадающего списка.        //Выбираем «Специализированный»
        //Нажать на кнопку «Сохранить»
        new GUI()
                .inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .clickButton("Добавить новую")
                .inField("Наименование продукции").inputValue("Новая Продукция").assertNoControl()
                .inField("Код ТН ВЭД").selectValue("Кофе").assertNoControl()
                .inField("Вес продукции, кг").inputValue("16,000").assertNoControl()
                .inField("Упаковка").selectValue("Ящики 1-5кг").assertNoControl()
                .inField("Температурный режим (от -30°C до 0°C или от 0°C до +30°C) ").setCheckboxON().assertNoControl()
                .inField("От").inputValue("-15").assertNoControl()
                .inField("До").inputValue("+27").assertNoControl()
                .inField("Количество контейнеров").inputValue("16").assertNoControl()
                .inField("Тип контейнера").selectValue("Специализированный").assertNoControl()
                .clickButton("Сохранить");
    }

    @Step("Блок  «Информация о грузополучателе»")
    public void step07() {
        CommonFunctions.printStep();

        //В поле «Наименование грузополучателя» вводим значение Ss-password
        //В поле «Страна» вводим значение «USA»
        //В поле «Город» вводим значение «Moscow»
        //В поле «Дом» вводим значение «Ff»
        //В поле «Регион» вводим значение «St-Peterburg»
        //В поле «Район» вводим значение «Raion»
        //В поле «Улица» вводим значение «Lenina street»
        //В поле «Регистрационный номер грузополучателя» вводим значение «223 22 44 2»
        //В поле «Телефон» вводим значение «+79999999999»
        //В поле «Представитель грузополучателя» вводим значение «Moscow disco rule»
        //В поле «Email» вводим значение «www@mail.ru»
        new GUI()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue("Ss-password").assertNoControl()
                .inField("Страна").inputValue("USA").assertNoControl()
                .inField("Город").inputValue("Moscow").assertNoControl()
                .inField("Дом").inputValue("Ff").assertNoControl()
                .inField("Регион").inputValue("St-Peterburg").assertNoControl()
                .inField("Район").inputValue("Raion").assertNoControl()
                .inField("Улица").inputValue("Lenina street").assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue("223 22 44 2").assertNoControl()
                .inField("Телефон").inputValue("+79999999999").assertNoControl()
                .inField("Представитель грузополучателя").inputValue("Moscow disco rule").assertNoControl()
                .inField("Email").inputValue("www@mail.ru").assertNoControl();
    }

    @Step("Блок «Дополнительные услуги»")
    public void step08() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Вывоз груза с адреса («Первая миля»)»
        //Нажать на кнопку «Таможенное оформление»
        //Выбрать одно из значений «РЭЦ» или «РЖД Логистика».        //Выбираем «РЭЦ»
        //Нажать на кнопку «Оформление ветеринарного сертификата»
        //Выбрать одно из значений «РЭЦ» или «РЖД Логистика».        //Выбираем «РЭЦ»
        //Нажать на кнопку «Оформление фитосанитарного сертификата»
        //Выбрать одно из значений «РЭЦ» или «РЖД Логистика».        //Выбираем «РЭЦ»
        //Нажать на кнопку «Далее», для перехода на следующий шаг
        new GUI()
                .inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления")
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов")
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов");

        new GUI().clickButton("Далее");
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new GUI().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            CommonFunctions.wait(1);
        }
    }

}

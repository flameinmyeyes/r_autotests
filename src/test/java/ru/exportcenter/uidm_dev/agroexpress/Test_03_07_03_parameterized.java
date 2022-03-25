package ru.exportcenter.uidm_dev.agroexpress;

import functions.gui.GUIFunctions;
import framework.RunTestAgain;
import functions.common.CommonFunctions;
import functions.file.FileFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.uidm_dev.Hooks_UIDM_DEV;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_03_07_03_parameterized extends Hooks_UIDM_DEV {

//    private final String WAY_TEST = Ways.UIDM_DEV.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_1\\";

    public static String WAY_TO_PROPERTIES = "src/test/java/ru/exportcenter/uidm_dev/agroexpress/Test_03_07_03_properties.xml";

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

    public static String getData(String key) {
        String data = FileFunctions.readProperties(WAY_TO_PROPERTIES, key);
        return data;
    }

    @Step("Авторизация")
    public void step01() {
        //В браузере перейти по ссылке http://uidm.uidm-dev.d.exportcenter.ru/ru/login
        //Ввести логин и пароль demo_exporter/password, нажать «Войти»
        new GUIFunctions()
                .inContainer("Вход в личный кабинет")
                .inField("Email").inputValue(getData("Авторизация.Email"))
                .inField("Пароль").inputValue(getData("Авторизация.Пароль"))
                .clickButton("Войти");

        new GUIFunctions().waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        //Перейти во вкладку «Сервисы»
        //Найти сервис «Логистика. Доставка продукции "Агроэкспрессом"» и нажать «Оформить»
        //Перезагрузить страницу
        //Нажать кнопку «Продолжить»
        new GUIFunctions()
                .selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить")
                .switchPageTo(1);

        refreshTab("//*[contains(text(), 'Продолжить')]", 60);

        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Блок «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();
        //В поле «Почтовый адрес» вводим значение «Корнилаева 2»
        new GUIFunctions()
                .inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue(getData("Информация о компании.Почтовый адрес")).assertNoControl();
    }

    @Step("Блок «Информация о заявителе»")
    public void step04() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Дополнительный контакт»
        //В поле «ФИО» вводим значение «Иванов Иван Иванович»
        //В поле «Телефон» вводим значение «+7(999)999-99-99»
        //В поле «Должность» вводим значение «Менеджер»
        //В поле «Email» вводим значение «word@mail.ru»
        new GUIFunctions()
                .inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertNoControl()
                .inField("ФИО").inputValue(getData("Информация о заявителе.ФИО")).assertNoControl()
                .inField("Телефон").inputValue(getData("Информация о заявителе.Телефон")).assertNoControl()
                .inField("Должность").inputValue(getData("Информация о заявителе.Должность")).assertNoControl()
                .inField("Email").inputValue(getData("Информация о заявителе.Email")).assertNoControl();
    }

    @Step("Блок  «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();

        //В поле «Город отправителя» выбрать значение из выпадающего списка.        //Выбираем «Тула»
        //В поле «Город назначения» выбрать значение из выпадающего списка.        //Выбираем «Шанхай»
        //Нажать на кнопку «Вывоз груза с адреса («Первая миля»)»
        //В поле «Адрес» вводим значение «Молодежная улица»
        //В поле «Предполагаемая дата отправления груза» вводим значение «22.10.2022»
        new GUIFunctions()
                .inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue(getData("Информация для оказания услуги.Город отправления")).assertNoControl()
                .inField("Город назначения").selectValue(getData("Информация для оказания услуги.Город назначения")).assertNoControl()
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON()
                .inField("Адрес").inputValue(getData("Информация для оказания услуги.Адрес")).assertNoControl()
                .inField("Предполагаемая дата отправления груза").inputValue(getData("Информация для оказания услуги.Предполагаемая дата отправления груза")).assertNoControl();
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
        new GUIFunctions()
                .inContainer("Информация о грузе")
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .clickButton("Добавить новую")
                .inField("Наименование продукции").inputValue(getData("Информация о грузе.Наименование продукции")).assertNoControl()
                .inField("Код ТН ВЭД").selectValue(getData("Информация о грузе.Код ТН ВЭД")).assertNoControl()
                .inField("Вес продукции, кг").inputValue(getData("Информация о грузе.Вес продукции, кг")).assertNoControl()
                .inField("Упаковка").selectValue(getData("Информация о грузе.Упаковка")).assertNoControl()
                .inField("Температурный режим (от -30°C до 0°C или от 0°C до +30°C) ").setCheckboxON().assertNoControl()
                .inField("От").inputValue(getData("Информация о грузе.От")).assertNoControl()
                .inField("До").inputValue(getData("Информация о грузе.До")).assertNoControl()
                .inField("Количество контейнеров").inputValue(getData("Информация о грузе.Количество контейнеров")).assertNoControl()
                .inField("Тип контейнера").selectValue(getData("Информация о грузе.Тип контейнера")).assertNoControl()
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
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue(getData("Информация о грузополучателе.Наименование грузополучателя")).assertNoControl()
                .inField("Страна").inputValue(getData("Информация о грузополучателе.Страна")).assertNoControl()
                .inField("Город").inputValue(getData("Информация о грузополучателе.Город")).assertNoControl()
                .inField("Дом").inputValue(getData("Информация о грузополучателе.Дом")).assertNoControl()
                .inField("Регион").inputValue(getData("Информация о грузополучателе.Регион")).assertNoControl()
                .inField("Район").inputValue(getData("Информация о грузополучателе.Район")).assertNoControl()
                .inField("Улица").inputValue(getData("Информация о грузополучателе.Улица")).assertNoControl()
                .inField("Регистрационный номер грузополучателя").inputValue(getData("Информация о грузополучателе.Регистрационный номер грузополучателя")).assertNoControl()
                .inField("Телефон").inputValue(getData("Информация о грузополучателе.Телефон")).assertNoControl()
                .inField("Представитель грузополучателя").inputValue(getData("Информация о грузополучателе.Представитель грузополучателя")).assertNoControl()
                .inField("Email").inputValue(getData("Информация о грузополучателе.Email")).assertNoControl();
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
        new GUIFunctions()
                .inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления")
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов")
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов");

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

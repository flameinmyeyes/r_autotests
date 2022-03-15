package ru.exportcenter.uidm_dev;

import framework.RunTestAgain;
import functional.CommonFunctions;
import functional.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_03 extends Hooks_UIDM_DEV {

//    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_1\\";

//    private GUIFunctions guiFunctions = new GUIFunctions();

    @Owner(value="Балашов Илья")
    @Description("NSI_016. ПЗ п. 2.7.2. ТК №1 Проверка создания нового документа")
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
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions().authorization("demo_exporter", "password");

        //Перейти во вкладку «Сервисы»
        new GUIFunctions()
                .selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business");

        //Выбрать сервис «Логистика. Доставка продукции «Агроэкспрессом»» и нажать кнопку «Оформить»
        new GUIFunctions()
                .waitForElementDisplayed("//*[contains(@class, 'preloader')]", 60)
                .inputInSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"")
                .openSearchResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить");

        //Нажать на кнопку «Продолжить»
        switchTo().window(1);

        new GUIFunctions().waitForLoading(60);
        refreshTab("//*[contains(text(), 'Продолжить')]", 20);

        new GUIFunctions().clickWebElement("//*[contains(text(), 'Продолжить')]");
    }

    @Step("Заполнить область «Информация о компании»")
    public void step02() {
        CommonFunctions.printStep();

        //В поле «Почтовый адрес» вводим значение «Корнилаева 2»
        new GUIFunctions().inputValueInArea("Информация о компании", "Почтовый адрес", "Корнилаева 2");
    }

    @Step("Заполнить область «Информация о заявителе»")
    public void step03() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Дополнительный контакт»
        //В поле «ФИО» вводим значение «Иванов Иван Иванович»
        //В поле «Телефон» вводим значение «+7(999)999-99-99»
        //В поле «Должность» вводим значение «Менеджер»
        //В поле «Email» вводим значение «word@mail.ru»
        new GUIFunctions()
                .setCheckboxInArea("Информация о заявителе", "Дополнительный контакт", true)
                .inputValueInArea("Информация о заявителе", "ФИО", "Иванов Иван Иванович")
                .inputValueInArea("Информация о заявителе", "Телефон", "+7(999)999-99-99")
                .inputValueInArea("Информация о заявителе", "Должность", "Менеджер")
                .inputValueInArea("Информация о заявителе", "Email", "word@mail.ru");
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    public void step04() {
        CommonFunctions.printStep();

        //В поле «Город отправителя» выбрать значение из выпадающего списка.        //Выбираем «Подольск»
        //В поле «Город назначения» выбрать значение из выпадающего списка.        //Выбираем «Харбин»
        //Нажать на кнопку «Вывоз груза с адреса («Первая миля»)»
        //В поле «Адрес» вводим значение «Молодежная улица»
        //В поле «Предполагаемая дата отправления груза» вводим значение «22.10.2022»
        new GUIFunctions()
                .selectValueFromDropdownInArea("Информация для оказания услуги", "Город отправления", "Тула") //!!!
                .selectValueFromDropdownInArea("Информация для оказания услуги", "Город назначения", "Харбин")
                .setCheckboxInArea("Информация для оказания услуги", "Вывоз груза с адреса («Первая миля»)", true)
                .inputValueInArea("Информация для оказания услуги", "Адрес", "Молодежная улица")
                .inputValueInArea("Информация для оказания услуги", "Предполагаемая дата отправления груза", "22.10.2022");
    }

    @Step("Заполнить область «Информация о грузе»")
    public void step05() {
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
//                .clickButtonInArea("Информация о грузе", "Полный контейнер")
                .clickButtonInArea("Информация о грузе", "Добавить +")

                .clickButtonInArea("Сведения о продукции", "Добавить новую")
                .inputValueInArea("Сведения о продукции", "Наименование продукции", "Новая Продукция")
                .selectValueFromDropdownInArea("Сведения о продукции", "Код ТН ВЭД", "Кофе")
                .inputValueInArea("Сведения о продукции", "Вес продукции, кг", "16,000")
                .selectValueFromDropdownInArea("Сведения о продукции", "Упаковка", "Флекси танки") //!!!
                .setCheckboxInArea("Сведения о продукции", "Температурный режим (от -30°C до 0°C или от 0°C до +30°C) ", true)
                .inputValueInArea("Сведения о продукции", "От", "-15")
                .inputValueInArea("Сведения о продукции", "До", "+27")
                .inputValueInArea("Сведения о продукции", "Количество контейнеров", "16")
                .selectValueFromDropdownInArea("Сведения о продукции", "Тип контейнера", "Специализированный")
                .clickButtonInArea("Сведения о продукции", "Сохранить");
    }

    @Step("Заполнить область «Информация о грузополучателе»")
    public void step06() {
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

        //Нажать на кнопку «Вывоз груза с адреса («Первая миля»)»
        //Нажать на кнопку «Таможенное оформление»
        //Выбрать одно из значений «РЭЦ» или «РЖД Логистика».        //Выбираем «РЭЦ»
        //Нажать на кнопку «Оформление ветеринарного сертификата»
        //Выбрать одно из значений «РЭЦ» или «РЖД Логистика».        //Выбираем «РЭЦ»
        //Нажать на кнопку «Оформление фитосанитарного сертификата»
        //Выбрать одно из значений «РЭЦ» или «РЖД Логистика».        //Выбираем «РЭЦ»
        //Нажать на кнопку «Далее», для перехода на следующий шаг

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

    private void refreshTab(String expectedXpath, int times) {
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

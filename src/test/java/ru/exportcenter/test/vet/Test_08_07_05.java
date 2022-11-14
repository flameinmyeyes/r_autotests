package ru.exportcenter.test.vet;

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
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class Test_08_07_05 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_05/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_05/" + "Test_08_07_05_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.05 Новая заявка. Тип продукции - рыба и морепродукты")
    @Link(name = "Test_08_07_05", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170230743")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
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
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void precondition() {
        //Предусловие: выполнить шаги 1-4 из ТК 08.07.02 Авторизация для ИП
        Test_08_07_02 test_08_07_02 = new Test_08_07_02();
        test_08_07_02.WAY_TEST = this.WAY_TEST;
        test_08_07_02.steps();

    }

    @Step("Ввод данных в карточку Тип услуги")
    public void step01() {
        CommonFunctions.printStep();

        //В поле Выберите тип услуги выбрать Оформить новое разрешение  Нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Тип услуги")
                .inField("Выберите тип услуги").clickByLocator("//span[text()='Оформить новое разрешение']")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 2 из 9']");

    }

    @Step("Ввод данных в карточку \"Информация о продукции\" ")
    public void step02() {
        CommonFunctions.printStep();

        //В поле Тип продукции выбрать Рыба и морепродукты
        new GUIFunctions()
                .inContainer("Информация о продукции")
                .inField("Тип продукции").selectValue("Рыба и морепродукты").assertNoControl().assertValue()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 3 из 9']");


        //Нажать кнопку "Добавить" и заполнение полей
        new GUIFunctions()
                .clickButton("Добавить +")
                .inContainer("Добавление продукции")
                .inField("Продукция").selectValue(P.getProperty("Продукция")) // выпадающий список - глючит, значения с NBSP
                .inField("Вид продукции").selectValue(P.getProperty("Вид продукции"))
                .clickByLocator("//span[text()='С выгрузкой на берегу']")
                .waitForElementDisplayed("//*[text() = 'Добавление продукции']/ancestor::div[contains(@class, 'container')][1]//*[text() = 'Район вылова водных биологических ресурсов (ВБР)']")
                .inField("Район вылова водных биологических ресурсов (ВБР)").inputValue(P.getProperty("Район вылова водных биологических ресурсов (ВБР)"))
                .inField("Единица измерения").selectValue(P.getProperty("Единица измерения"))
                .inField("Количество в выбранных единицах").inputValue(P.getProperty("Количество в выбранных единицах"))
                .inField("Выберите одного или несколько производителей").selectValue("RU-065/BT02750")
                .clickButton("Добавить")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 4 из 9']");
    }

    @Step("Предзаполненные данные в карточке \"Информация о поставке\" 4 шаг")
    public void step03() {
        CommonFunctions.printStep();

        $x("//*[text() = 'Информация о поставке']/ancestor::div[contains(@class, 'container')][1]//span[text()='Страна происхождения продукции']/following-sibling::span[1]").shouldHave(text("РОССИЯ"));
        $x("//*[text() = 'Информация о поставке']/ancestor::div[contains(@class, 'container')][1]//span[text()='Тип продукции ']").shouldHave(text("  Рыба и морепродукты"));

    }

    @Step("Ввод Информации об условиях поставки")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о поставке")
                .inField("Субъект Российской Федерации, в котором расположены поднадзорные объекты").selectValue(P.getProperty("Субъект Российской Федерации, в котором расположены поднадзорные объекты")).assertNoControl().assertValue()
                .inField("Цель вывоза").selectValue(P.getProperty("Цель вывоза"))
                .inField("Укажите одно или несколько предприятий (места хранения/отгрузки)").selectValue("RU035");

        //Нажать кнопку "продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 5 из 9']");

    }

    @Step("Блок  \"Условия поставки\"")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о транспортировке")
                .inField("Наименование организации-перевозчика").inputValue(P.getProperty("Наименование организации-перевозчика")).assertNoControl().assertValue()
                .inField("Вид транспорта, используемый для перевозки до пункта пропуска на границе Таможенного союза (выберите один или несколько)").selectValue(P.getProperty("Вид транспорта, используемый для перевозки до пункта пропуска на границе Таможенного союза (выберите один или несколько)"))
                .inField("Маршрут следования").inputValue("Москва-Китай")
                .inField("Регион проведения таможенного оформления").selectValue(P.getProperty("Регион проведения таможенного оформления"))
                .inField("Таможенный пункт").selectValue(P.getProperty("Таможенный пункт"));
        $x("//*[text() = 'Информация о транспортировке']/ancestor::div[contains(@class, 'container')][1]//textarea[text()='124460 МОСКВА, Г. ЗЕЛЕНОГРАД, 2-Й ЗАПАДНЫЙ ПР-Д, Д.3, СТР.1']").shouldBe(exist);

        //Нажать кнопку "продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 6 из 9']");
    }

    @Step("Ввод Информации в карточке \"Информация об импортере \"")
    public void step06() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inContainer("Информация об импортере")
                .inField("Наименование").inputValue(P.getProperty("Наименование"))
                .inField("Страна").selectValue(P.getProperty("Страна"))
                .inField("Введите адрес на русском или английском языке").inputValue("Room 1203,  Guangming Office Building No. 42 Liangmaqiao Road, Chaoyang District, Beijing, 100125, P.R. China");
        //Нажать кнопку "продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 7 из 9']");
    }

    @Step("Ввод Информации в карточке \"Информация о поставке\"")
    public void step07() {
        CommonFunctions.printStep();
        $x("//textarea[@name='$.createItem.additionInfo']").setValue("Договор поставки от 10 мая №10997");
        //Нажать кнопку "продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 8 из 9']");
    }

    @Step("Предзаполненные данные проверка")
    public void step08() {
        CommonFunctions.printStep();

        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование организации']/b").shouldHave(text("ИП Федоров Ф.Ф."));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='ИНН ']/b").shouldHave(text("318396766019"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='ОГРН ']/b").shouldHave(text("302920179182270"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Контактный телефон ']/b").shouldHave(text("79085452618"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Email ']/b").shouldHave(text("vet1_exporter@otr.ru"));
        //Блок Инфо об импортере
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование ']/b").shouldHave(text("Импортер"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Страна ']/b").shouldHave(text("Китайская Народная Республика"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Адрес ']/b[text()='Room 1203,  Guangming Office Building No. 42 Liangmaqiao Road, Chaoyang District, Beijing, 100125, P.R. China']").should(exist);
        //Блок Инфо о поставке
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Страна происхождения продукции ']/b").shouldHave(text("РОССИЯ"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Субъект Российской Федерации, в котором расположены поднадзорные объекты ']/b").shouldHave(text("Вологодская"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Цель вывоза ']/b").shouldHave(text("реализация в пищу людям"));

        //Блок Инфо о транспортировке
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование организации-перевозчика ']/b").shouldHave(text("ООО \"АРГО\""));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Вид транспорта, используемый для перевозки до пункта пропуска на границе Таможенного союза']/b").shouldHave(text("Автомобильный"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование пункта пропуска']/b").shouldHave(text("Выбраны все пункты пропуска, соответствующие видам транспорта"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Маршрут следования ']/b").shouldHave(text("Москва-Китай"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Регион проведения таможенного оформления ']/b").shouldHave(text("Московская область"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Таможенный пункт ']/b").shouldHave(text("Пикинский таможенный пост"));

        new GUIFunctions()   //проверка окна по кнопке "Просмотреть"
                .inContainer("Информация о продукции")
                .clickByLocator("//button[@class='dropdown-icon']")
                .clickByLocator("//span[text() ='Просмотреть']")
                .waitForElementDisplayed("//span[text() ='Закрыть']");
    }

}

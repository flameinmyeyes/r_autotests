package ru.exportcenter.dev.vet;

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
import static com.codeborne.selenide.Selenide.refresh;

public class Test_08_07_04 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_04/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_04/" + "Test_08_07_04_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.04 Внести изменение в действующее разрешение в заявку")
    @Link(name = "Test_08_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175275595")
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
        //Предусловие: выполнить шаги 1-4 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_08_07_01 test_08_07_01 = new Test_08_07_01();
        test_08_07_01.WAY_TEST = this.WAY_TEST;
        test_08_07_01.steps();

    }

    @Step("Переход на карточку \"Тип услуги\" ")
    public void step01() {
        CommonFunctions.printStep();

        //В поле Выберите тип услуги выбрать Оформить новое разрешение  Нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Тип услуги")
                .inField("Выберите тип услуги").clickByLocator("//span[text()='Внести изменения в действующее разрешение']")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 2 из 9']");

    }

    @Step("Заполнить поля")
    public void step02() {
        CommonFunctions.printStep();


        new GUIFunctions()
                .inContainer("Поиск действующего разрешения")
                .inField("Страна импортер").selectValue(P.getProperty("Страна импортер")).assertNoControl().assertValue()
                .inField("Тип продукции").selectValue(P.getProperty("Тип продукции")).assertNoControl().assertValue()
                .inField("Продукция").selectValue(P.getProperty("Продукция")).assertNoControl().assertValue()
                .inField("Вид продукции").selectValue(P.getProperty("Вид продукции")).assertNoControl().assertValue()
                .inField("Укажите предприятие (места хранения/отгрузки)").selectValue("7718162032")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//button[text()='Скоректировать']")
                .clickButton("Скоректировать");


        //Нажать кнопку "Добавить"
        new GUIFunctions()
                .clickButton("Добавить +")
                .inContainer("Добавление продукции")
                .inField("Продукция").selectValue(P.getProperty("Продукция")).assertNoControl().assertValue()
                .inField("Вид продукции").selectValue(P.getProperty("Вид продукции")).assertNoControl().assertValue()
                .inField("Номер документа СИТЕС").inputValue(P.getProperty("Номер документа СИТЕС")).assertNoControl().assertValue()
                .inField("Единица измерения").selectValue(P.getProperty("Единица измерения")) //ассерт глючит на этом поле
                .inField("Количество в выбранных единицах").inputValue(P.getProperty("Количество в выбранных единицах")).assertNoControl().assertValue()
                .inField("Выберите одного или несколько производителей").selectValue("RU-033/VH03882 123 Российская Федерация, Владимирская обл., г. Владимир")
                .inField("Укажите информацию о продукции, которую считаете необходимо сообщить дополнительно").inputValue(P.getProperty("Укажите информацию о продукции, которую считаете необходимо сообщить дополнительно")).assertNoControl().assertValue()
                .clickButton("Сохранить");
        //Нажать кнопку "продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 4 из 9']");
    }

    @Step("Предзаполненные данные в карточке \"Информация о поставке\" ")
    public void step03() {
        CommonFunctions.printStep();

        $x("//*[text() = 'Информация о поставке']/ancestor::div[contains(@class, 'container')][1]//span[text()='Страна происхождения продукции']/following-sibling::span[1]").shouldHave(text("РОССИЯ"));
        $x("//*[text() = 'Информация о поставке']/ancestor::div[contains(@class, 'container')][1]//span[text()='Тип продукции ']").shouldHave(text("  Живые животные"));

    }

    @Step("Ввод Информации об условиях поставки")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о поставке")
                .inField("Субъект Российской Федерации, в котором расположены поднадзорные объекты").selectValue(P.getProperty("Субъект Российской Федерации, в котором расположены поднадзорные объекты")).assertNoControl().assertValue()
                .inField("Цель вывоза").selectValue(P.getProperty("Цель вывоза"))
                .clickButton("Добавить +")
                .inContainer("Информация о поставке")
                .inField("Укажите предприятие (места хранения/отгрузки)").selectValue("RU033")
                .inField("Выберите один или несколько видов размещения").selectValue(P.getProperty("Выберите один или несколько видов размещения"))
                .clickButton("Сохранить");
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
                .inField("Маршрут следования").inputValue("Россия - Вена")
                .inField("Регион проведения таможенного оформления").selectValue(P.getProperty("Регион проведения таможенного оформления"))
                .inField("Таможенный пункт").selectValue(P.getProperty("Таможенный пункт"));
        $x("//*[text() = 'Информация о транспортировке']/ancestor::div[contains(@class, 'container')][1]//textarea[text()='140185, МОСКОВСКАЯ ОБЛ., Г.ЖУКОВСКИЙ, УЛ. НАРКОМВОД, Д. 3']").shouldBe(exist);

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
                .inField("Наименование").inputValue(P.getProperty("Наименование")).assertNoControl().assertValue()
                .inField("Страна").selectValue(P.getProperty("Страна"))
                .inField("Введите адрес на русском или английском языке").inputValue("Вена, ул.Стефана 2");
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

        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование организации']/following-sibling::span[1]").shouldHave(text("ООО «ОТР 2000»"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='ИНН']/following-sibling::span[1]").shouldHave(text("7718162032"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='ОГРН']/following-sibling::span[1]").shouldHave(text("1027700269530"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Контактный телефон']/following-sibling::span[1]").shouldHave(text("9999999999"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Email']/following-sibling::span[1]").shouldHave(text("rychagova.uliia@otr.ru"));
        //Блок Инфо об импортере
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование']/following-sibling::span[1]").shouldHave(text("Swan"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Страна']/following-sibling::span[1]").shouldHave(text("Австрийская Республика"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Вена, ул.Стефана 2']").should(exist);
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Страна происхождения продукции ']").shouldHave(text("РОССИЯ"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Субъект Российской Федерации, в котором расположены поднадзорные объекты ']").shouldHave(text("Владимирская"));
        //Блок Инфо о транспортировке
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование организации-перевозчика ']").shouldHave(text("ООО \"АРГО\""));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Вид транспорта, используемый для перевозки до пункта пропуска на границе Таможенного союза']").shouldHave(text("Автомобильный"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Маршрут следования ']").shouldHave(text("Россия - Вена"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Регион проведения таможенного оформления ']").shouldHave(text("Московская область"));
        $x("//*[text() = 'Проект заявления']/ancestor::div[contains(@class, 'container')][1]//span[text()='Таможенный пункт ']").shouldHave(text("таможенный пост Аэродром Раменское"));


    }


    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }
}
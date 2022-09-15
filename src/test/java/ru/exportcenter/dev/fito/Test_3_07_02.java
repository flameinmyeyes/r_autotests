package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import functions.api.SQLFunctions;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import functions.file.FileFunctions;
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

import static com.codeborne.selenide.Selenide.*;

public class Test_3_07_02 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_02/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_3_07_02_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String docNum;
    private String processID;

    @Owner(value = "Балашов Илья")
    @Description("3.07.02 (Р) Сценарий с отсутствием типа документа в списке \"Тип документа о происхождении груза\"")
    @Link(name = "Test_3_07_02", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308873")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step05();
        step06();
        step07();
        step08();
        step09();
        step10();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие выполнить шаги 1-4 из
        //https://confluence.exportcenter.ru/pages/resumedraft.action?draftId=163308622&draftShareId=786c5e3a-edfc-4a1d-8fb1-ae287b286103&
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
//        test_3_07_01.WAY_TEST = WAY_TEST;
//        test_3_07_01.WAY_TO_PROPERTIES = WAY_TO_PROPERTIES;
//        test_3_07_01.P = P;
        test_3_07_01.step01();
        test_3_07_01.step02();
        test_3_07_01.step03();
        test_3_07_01.step04();
    }

    @Step("Блок  \"Условия поставки\"")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 2 из 9']")

                .inContainer("Условия поставки")
                    .inField("Вид транспорта").selectValue(P.getProperty("Условия поставки.Вид транспорта")).assertNoControl().assertValue()
                    .inField("Страна назначения").selectValue(P.getProperty("Условия поставки.Страна назначения")).assertNoControl().assertValue()
                    .inField("Пункт ввоза в стране назначения").inputValue(P.getProperty("Условия поставки.Пункт ввоза в стране назначения")).assertNoControl().assertValue()

                    .inField("Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»").selectValue(P.getProperty("Условия поставки.Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»")).assertNoControl().assertValue()
                    .inField("Наименование документа о происхождении груза").selectValue(P.getProperty("Условия поставки.Наименование документа о происхождении груза")).assertNoControl().assertValue()
                    .inField("Номер документа на груз").inputValue(P.getProperty("Условия поставки.Номер документа на груз")).assertNoControl().assertValue()
                    .inField("Дата").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Блок \"Добавление продукции\"")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 3 из 9']")

                .inContainer("Добавление продукции")
//                    .inField("Каталог продукции").selectValue(" --- кофе").assertNoControl().assertValue()
                    .inField("Каталог продукции").clickByLocator("//input[@placeholder='Введите наименование продукции или код ТН ВЭД']").inputValue("кофе").clickByLocator("//*[contains(text(), ' --- кофе')]")
                    .inField("Тип продукции").selectValue(P.getProperty("Добавление продукции.Тип продукции")).assertNoControl().assertValue()
                    .inField("Дополнительная информация о продукции. Например, страна производства (произрастания) продукции, сорт продукции и т.д.").inputValue(P.getProperty("Добавление продукции.Дополнительная информация о продукции")).assertNoControl().assertValue()
                    .inField("Вес груза (нетто), кг").inputValue(P.getProperty("Добавление продукции.Вес груза (нетто)")).assertNoControl().assertValue()
                    .inField("Особые единицы измерения").selectValue(P.getProperty("Добавление продукции.Особые единицы измерения")).assertNoControl().assertValue()
                    .inField("Количество в особых единицах измерения").inputValue(P.getProperty("Добавление продукции.Количество в особых единицах измерения")).assertNoControl().assertValue()
                    .inField("Описание упаковки").selectValue(P.getProperty("Добавление продукции.Описание упаковки")).assertNoControl().assertValue()
                    .inField("Размещение продукции").clickByLocator("//ancestor::div//span[contains(text(),'Навалом (наливом)')][last()]")
                    .inField("Наличие отличительных знаков (маркировки). Например, номера партий, серийные номера или названия торговых марок. ").setCheckboxON().assertCheckboxON()
                    .inField("Страна").selectValue(P.getProperty("Добавление продукции.Страна")).assertNoControl().assertValue()
                    .inField("Регион").selectValue(P.getProperty("Добавление продукции.Регион")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Блок \"Договор на установление карантинного фитосанитарного состояния\"")
    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 4 из 9']")

                .inContainer("Добавление продукции")
                    .inField("Договор").selectValue(P.getProperty("Договор.Договор")).assertNoControl().assertValue()
                    .inField("Орган инспекции").assertNoControl().assertValue(P.getProperty("Договор.Орган инспекции"))
                    .inField("Номер").assertNoControl().assertValue(P.getProperty("Договор.Номер"))
                    .inField("Дата").assertNoControl().assertValue(P.getProperty("Договор.Дата"))
                    .inField("Срок действия").assertNoControl().assertValue(P.getProperty("Договор.Срок действия"))

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Блок \"Запрос отбора проб\"")
    public void step08() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 5 из 9']")

                .inContainer("Запрос отбора проб")
                    .inField("Территориальное управление Россельхознадзора").selectValue(P.getProperty("Запрос отбора проб.Территориальное управление Россельхознадзора")).assertNoControl().assertValue()
                    .inField("Планируемая дата отбора проб").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue()
                    .inField("Планируемое время отбора проб").inputValue(P.getProperty("Запрос отбора проб.Планируемое время отбора проб")).assertNoControl().assertValue()
                    .inField("Адрес места отбора проб").inputValue(P.getProperty("Запрос отбора проб.Адрес места отбора проб")).assertNoControl().assertValue()
                    .inField("Дополнительные требования к исследованиям").inputValue(P.getProperty("Запрос отбора проб.Дополнительные требования к исследованиям")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Направить на проверку")
                    .waitForLoading();
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Форма заключения\"")
    public void step09() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 7 из 9']")

                .inContainer("Форма заключения")
                    .inField("Выберите требуемую форму заключения о карантинном фитосанитарном состоянии:").clickByLocator("//ancestor::div//span[contains(text(),'В электронной форме')][last()]");
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Уполномоченное лицо для получения заключения\"")
    public void step10() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 7 из 9']")

                .inContainer("Уполномоченное лицо для получения заключения")
                    .inField("ФИО").selectValue(P.getProperty("Форма заключения.ФИО"))
                    .inField("Телефон").assertNoControl().assertValue(P.getProperty("Форма заключения.Телефон"))
                    .inField("Email").assertNoControl().assertValue(P.getProperty("Форма заключения.Email"))

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Уполномоченное лицо для получения заключения\"")
    public void step11() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 8 из 9']")

                .inContainer("Договор с органом инспекции")
                   .inField("Я ознакомлен и согласен с условиями проекта договора").setCheckboxON().assertCheckboxON()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Подписать и отправить")
                    .waitForLoading();

//        docNum = $x("//div[contains (@class, 'FormHeader_title' )]//span[contains (@class, 'Typography_body' )]").getText().split("№")[1];
//        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");
//        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");
//
//        CommonFunctions.wait(20);
//        String status = RESTFunctions.getOrderStatus(processID);
//        System.out.println(status);
//        Assert.assertEquals(status, "Проводится проверка");
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

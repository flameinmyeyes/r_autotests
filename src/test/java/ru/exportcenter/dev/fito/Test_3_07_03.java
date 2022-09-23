package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
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

public class Test_3_07_03 extends Hooks {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/Test_3_07_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_3_07_03_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value = "Балашов Илья")
    @Description("3.07.03 (Р) Сценарий с добавлением новой продукции")
    @Link(name = "Test_3_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175253524")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие выполнить шаги 1-5 из
        //https://confluence.exportcenter.ru/pages/resumedraft.action?draftId=163308622&draftShareId=786c5e3a-edfc-4a1d-8fb1-ae287b286103&
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = WAY_TEST;
        test_3_07_01.WAY_TO_PROPERTIES = WAY_TO_PROPERTIES;
        test_3_07_01.P = P;
        test_3_07_01.step01();
        test_3_07_01.step02();
        test_3_07_01.step03();
        test_3_07_01.step04();
        test_3_07_01.step05();
    }

    @Step("Блок \"Добавление продукции\"")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Добавление продукции")
                    .clickButton("Добавить новый")
//                    .clickByLocator("//div[text()='Добавить новый']")
//                    .inField("Каталог продукции").selectValue(" --- Кофе").assertNoControl().assertValue()
                    .inField("Код ТН ВЭД").clickByLocator("//input[@placeholder='Введите или выберите из списка ' and @name='tnvedCode']").inputValue("кофе").clickByLocator("//*[contains(text(), ' --- Кофе')]")
                    .inField("Тип продукции").selectValue(P.getProperty("Добавление продукции.Тип продукции")).assertNoControl().assertValue()
                    .inField("Ботаническое наименование продукции").assertValue(P.getProperty("Добавление продукции.Ботаническое наименование продукции")).assertNoControl()
                    .inField("Наименование продукции").inputValue(P.getProperty("Добавление продукции.Наименование продукции")).assertNoControl().assertValue()
                    .inField("Производитель").clickByLocator("//ancestor::div//span[contains(text(),'Российский')][last()]")
                    .inField("Наименование производителя").selectValue(P.getProperty("Добавление продукции.Наименование производителя")).assertNoControl().assertValue()
                    .inField("Дополнительная информация о продукции. Например, страна производства (произрастания) продукции, сорт продукции и т.д.").inputValue(P.getProperty("Добавление продукции.Дополнительная информация о продукции")).assertNoControl().assertValue()
                    .inField("Вес груза (нетто), кг").inputValue(P.getProperty("Добавление продукции.Вес груза (нетто)")).assertNoControl().assertValue()
                    .inField("Особые единицы измерения").selectValue(P.getProperty("Добавление продукции.Особые единицы измерения")).assertNoControl().assertValue()
                    .inField("Количество в особых единицах измерения").inputValue(P.getProperty("Добавление продукции.Количество в особых единицах измерения")).assertNoControl().assertValue()
                    .inField("Описание упаковки").selectValue(P.getProperty("Добавление продукции.Описание упаковки")).assertNoControl().assertValue()
                    .inField("Размещение продукции").clickByLocator("//ancestor::div//span[contains(text(),'Навалом (наливом)')][last()]")
                    .inField("Наличие отличительных знаков (маркировки). Например, номера партий, серийные номера или названия торговых марок. ").setCheckboxON().assertCheckboxON()
                    //Место происхождения( произрастания) продукции
                    .inField("Страна").selectValue(P.getProperty("Добавление продукции.Страна")).assertNoControl().assertValue()
                    .inField("Регион").selectValue(P.getProperty("Добавление продукции.Регион")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
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

package ru.exportcenter.test.fito;

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

import static com.codeborne.selenide.Selenide.$x;

public class Test_3_07_03 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_03/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/fito/Test_3_07_03/" + "Test_3_07_03_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Балашов Илья")
    @Description("3.07.03 (Тест контур) Сценарий с добавлением новой продукции")
    @Link(name = "Test_3_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=196777489")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step06();
        step07();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие: выполнить шаги 1-5 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188852997
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
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
                    .inField("Код ТН ВЭД").selectValue(P.getProperty("Добавление продукции.Код ТН ВЭД").split(" ")[0]).assertNoControl().assertValue(P.getProperty("Добавление продукции.Код ТН ВЭД"))
                    .inField("Тип продукции").selectValue(P.getProperty("Добавление продукции.Тип продукции")).assertNoControl().assertValue()
                    .inField("Наименование продукции").inputValue(P.getProperty("Добавление продукции.Наименование продукции")).assertNoControl().assertValue()
                    .inField("Производитель").clickByLocator("//ancestor::div//span[contains(text(),'Российский')][last()]")
                    .inField("Наименование производителя").selectValue(P.getProperty("Добавление продукции.Наименование производителя").split(" ")[0]).assertNoControl().assertValue(P.getProperty("Добавление продукции.Наименование производителя"))
                    .inField("Дополнительная информация о продукции. Например, страна производства (произрастания) продукции, сорт продукции и т.д.").inputValue(P.getProperty("Добавление продукции.Дополнительная информация о продукции")).assertNoControl().assertValue()
                    .inField("Вес груза (нетто), кг").inputValue(P.getProperty("Добавление продукции.Вес груза (нетто)")).assertNoControl().assertValue()
                    .inField("Особые единицы измерения").selectValue(P.getProperty("Добавление продукции.Особые единицы измерения")).assertNoControl().assertValue()
                    .inField("Количество в особых единицах измерения").inputValue(P.getProperty("Добавление продукции.Количество в особых единицах измерения")).assertNoControl().assertValue()
                    .inField("Описание упаковки").selectValue(P.getProperty("Добавление продукции.Описание упаковки")).assertNoControl().assertValue()
                    .inField("Размещение продукции").clickByLocator("//ancestor::div//span[contains(text(),'Навалом (наливом)')][last()]")
                    .inField("Наличие отличительных знаков (маркировки). Например, номера партий, серийные номера или названия торговых марок. ").setCheckboxON().assertCheckboxON()
                    .inField("Номер партии зерна (продуктов переработки зерна)").inputValue(P.getProperty("Добавление продукции.Номер партии зерна")).assertNoControl().assertValue()
                    //Место происхождения( произрастания) продукции
                    .inField("Страна").selectValue(P.getProperty("Добавление продукции.Страна")).assertNoControl().assertValue()
                    .inField("Регион").selectValue(P.getProperty("Добавление продукции.Регион")).assertNoControl().assertValue();
    }

    @Step("Шаг 7. Отмена заявки пользователем")
    public void step07() {
        CommonFunctions.printStep();

        //Нажать кнопку <...>
        new GUIFunctions()
                .scrollTo("Запрос заключения о карантинном фитосанитарном состоянии")
                .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']" +
                        "/parent::div/following-sibling::div/button[@class='dropdown-icon']")
                .waitForLoading();
        clickUntilCancelButtonIsDisplayed(60);

        //Нажать "Отменить заявку"
        new GUIFunctions()
                .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']/parent::div/following-sibling::div//li[text()='Отменить заявку']")
                .waitForLoading();

        //pop-up уведомление о подтверждении удаления
        if($x("//div//button[@title='Да']").isDisplayed()) {
            new GUIFunctions().clickByLocator("//div//button[@title='Да']");
            CommonFunctions.wait(1);
        }

        new GUIFunctions().waitForURL(new Test_3_07_01().P.getProperty("Авторизация.URL") + "/ru/main");
    }

    /**
     * Костыль: кликать, пока не появится нужное всплывающее меню
     */
    private static void clickUntilCancelButtonIsDisplayed(int times) {
        for(int i = 0; i < times; i++) {
            if(!$x("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']" +
                    "/parent::div/following-sibling::div//li[text()='Отменить заявку']").isDisplayed()) {
                new GUIFunctions()
                        .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']" +
                                "/parent::div/following-sibling::div/button[@class='dropdown-icon']")
                        .waitForLoading();
                CommonFunctions.wait(1);
            }
        }
    }

}

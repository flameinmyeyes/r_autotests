package ru.exportcenter.dev.fito;

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
import static com.codeborne.selenide.Selenide.refresh;

public class Test_3_07_04 extends Hooks {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/Test_3_07_04/";
    public String WAY_TO_PROPERTIES = Ways.DEV.getWay() + "/fito/Test_3_07_04/" + "Test_3_07_04_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Балашов Илья")
    @Description("3.07.04 (Р) Сценарий с заключением нового договора на установление КФС")
    @Link(name = "Test_3_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175255752")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step07();
        postcondition();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие: выполнить шаги 1-6 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
        test_3_07_01.step01();
        test_3_07_01.step02();
        test_3_07_01.step03();
        test_3_07_01.step04();
        test_3_07_01.step05();
        test_3_07_01.step06();
    }

    @Step("Шаг 7. Блок \"Договор на установление карантинного фитосанитарного состояния\"\n")
    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Договор на установление карантинного  фитосанитарного состояния")
                .inField("Договор").selectValue(P.getProperty("Договор.Договор")).assertNoControl().assertValue()
                .inField("Орган инспекции").assertValue(P.getProperty("Договор.Орган инспекции")).assertNoControl()
                .inField("Номер").assertValue(P.getProperty("Договор.Номер")).assertNoControl()
                .inField("Дата").assertValue(P.getProperty("Договор.Дата")).assertNoControl()
                .inField("Срок действия").assertValue(P.getProperty("Договор.Срок действия")).assertNoControl()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 5 из 9']");
    }

    public void postcondition() {
        //Выполнить шаги 8-12 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
        test_3_07_01.step08();
        test_3_07_01.step09();
        test_3_07_01.step10();
        test_3_07_01.step11();
        test_3_07_01.step12();
    }

}

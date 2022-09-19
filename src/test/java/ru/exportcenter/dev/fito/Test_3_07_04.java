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

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_04/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_3_07_04_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value = "Балашов Илья")
    @Description("3.07.04 (Р) Сценарий с заключением нового договора на установление КФС")
    @Link(name = "Test_3_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175255752")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step07();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие выполнить шаги 1-6 из
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
        test_3_07_01.step06();
    }

    @Step("Шаг 7. Блок \"Договор на установление карантинного фитосанитарного состояния\"\n")
    public void step07() {
        CommonFunctions.printStep();

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

package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
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

public class Test_3_07_05 extends Hooks {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/Test_3_07_05/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_3_07_05_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value = "Балашов Илья")
    @Description("3.07.05 (Р) Форматно-логический контроль")
    @Link(name = "Test_3_07_05", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170247548")
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
        //
    }

    @Step("")
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

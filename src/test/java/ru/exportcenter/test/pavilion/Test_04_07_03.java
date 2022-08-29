package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_03  extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_03/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_03_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 03 Подписание Акта")
    @Link(name = "Test_04_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170242302")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
//        step02();
//        step03();
//        step04();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("«»")
    public void step01() {
        CommonFunctions.printStep();

        requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/");
        new GUIFunctions().authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div");

        refreshTab("//*[text()='Продолжить']", 10);



    }

    @Step("«»")
    public void step02() {
        CommonFunctions.printStep();

        open("http://arm-pavilion.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization("pavilion_operator_nr@otr.ru","Password1!");

        new GUIFunctionsLKB().clickButton("Согласовать");
    }

    @Step("«»")
    public void step03() {
        CommonFunctions.printStep();


    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh(" + expectedXpath + ")");
            CommonFunctions.wait(1);
        }
    }

}

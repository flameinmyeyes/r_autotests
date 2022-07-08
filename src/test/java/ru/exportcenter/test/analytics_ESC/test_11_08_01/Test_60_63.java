package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;

import static com.codeborne.selenide.Selenide.*;

public class Test_60_63 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/analytics_ESC/Test_03_07_01_1/";
    public String requestNumber;

    @Owner(value="Петрищев Руслан")
    @Description("***")
    @Link(name="Test_60_63", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException, AWTException {
        precondition();
        step01();
    }

    @Step("Предусловия")
    public void precondition() throws InterruptedException, AWTException {
        CommonFunctions.printStep();

        Test_41_59 test_41_59 = new Test_41_59();
        test_41_59.steps();
    }

    @Step("Авторизация")
    public void step01() {

        requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");

        System.out.println("Шаг 60");
        open("https://lk.t.exportcenter.ru/");

        new GUIFunctions().authorization("demo_exporter", "password")
                .waitForElementDisplayed("//*[contains(text(),'Показать все')]");

        System.out.println("Шаг 61");
        new GUIFunctions().clickByLocator("//*[contains(text(),'Показать все')]")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/ancestor::div[contains(@class,'CardFooter_wrapperText')]//following::*[text()='Продолжить']")
                .waitForElementDisplayed("//*[text()='Завершить']");

        System.out.println("Шаг 62");
        new GUIFunctions().clickButton("Завершить")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");

        System.out.println("Шаг 62");
        closeWebDriver();
    }
}
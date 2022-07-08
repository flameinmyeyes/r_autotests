package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import static com.codeborne.selenide.Selenide.*;

public class Test_37_40 extends Hooks {

    public String requestNumber = JupyterLabIntegration.getFileContent("/analytics_ESC/Test_03_07_01_1/requestNumber.txt");

    @Owner(value="***")
    @Description("***")
    @Link(name="Test_37_40", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
    }

    @Step("Авторизация")
    public void step01() {

        System.out.println("Шаг 37");
        open("https://lk.t.exportcenter.ru/");
        new GUIFunctions().authorization("demo_exporter", "password")
                .waitForElementDisplayed("//*[contains(text(),'Показать все')]");


        System.out.println("Шаг 38");
        new GUIFunctions().clickByLocator("//*[contains(text(),'Показать все')]")
                        .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/ancestor::div[contains(@class,'CardFooter_wrapperText')]//following::*[text()='Продолжить']")
                .waitForElementDisplayed("//*[text()='Завершить']");

        System.out.println("Шаг 39");
        new GUIFunctions().clickButton("Завершить")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");

        System.out.println("Шаг 40");
        closeWebDriver();
    }
}

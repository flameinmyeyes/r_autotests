package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_60_63 extends Hooks {

    public String requestNumber;

    @Owner(value="***")
    @Description("***")
    @Link(name="Test_60_63", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
    }

    @Step("Авторизация")
    public void step01() {

        System.out.println("Шаг 60");

        open("https://lk.t.exportcenter.ru/");

        new GUIFunctions().authorization("demo_exporter", "password")
                .waitForElementDisplayed("//*[contains(text(),'Показать все')]")
                .clickByLocator("//*[contains(text(),'Показать все')]");

        requestNumber = new Test_1_17().requestNumber;
        $x("//*[contains(text(),'" + requestNumber + "')]").scrollTo();

        System.out.println("Шаг 61");

        new GUIFunctions().clickButton("Заявка №" + requestNumber + " отправлена")
                .clickButton("Продолжить");

        System.out.println("Шаг 62");

        new GUIFunctions().clickButton("Завершить");

    }
}
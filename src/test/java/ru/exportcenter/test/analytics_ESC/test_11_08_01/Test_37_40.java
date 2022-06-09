package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
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
        new GUIFunctions().authorization("mosolov@exportcenter.ru", "password")
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");

        System.out.println("Шаг 38");
        $x("//*[@class='anticon anticon-number']").click();

        System.out.println("Шаг 39");
        new GUIFunctions().clickButton("Завершить");

        System.out.println("Шаг 40");
        closeWebDriver();

    }
}

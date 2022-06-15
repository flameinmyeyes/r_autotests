package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import static com.codeborne.selenide.Selenide.*;

public class Test_41_59 extends Hooks {

    @Owner(value="***")
    @Description("***")
    @Link(name="Test_41_59", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
    }

    @Step("Авторизация")
    public void step01() {

        System.out.println("Шаг 41");
        open("https://tasks.t.exportcenter.ru/");

        System.out.println("Шаг 42");
        new GUIFunctionsLKB().authorization("mosolov@exportcenter.ru", "password");

        System.out.println("Шаг 43");
        $x("(//*[text()='Список задач']/following::*[contains(@class,'ant-radio-group')])[1]//following::input").click();
        new GUIFunctionsLKB().inPlaceholder("Выберите номер заявки").inputValue("");
        //
        new GUIFunctionsLKB().clickButton("К выполнению");

        System.out.println("Шаг 44");

        System.out.println("Шаг 45");
        refresh();
        new GUIFunctionsLKB().waitForElementDisplayed("");




    }

}

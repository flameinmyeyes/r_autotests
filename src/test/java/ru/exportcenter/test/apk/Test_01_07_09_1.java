package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;
import static com.codeborne.selenide.Selenide.*;

public class Test_01_07_09_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_09_1/";

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 09.1 Ознакомление с возможностями Сервиса (onboarding)")
    @Link(name = "Test_01_07_09_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170236337")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_01_07_05_2 test_01_07_05_2 = new Test_01_07_05_2();
        test_01_07_05_2.steps();
    }

    @Step("Переход к заявке")
    public void step01() {
        CommonFunctions.printStep();

        //Перезагрузить страницу
        //Нажать кнопку «Продолжить»
        new GUIFunctions().refreshTab("Продолжить", 10)
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//*[contains(text(),'Сертификация продукции АПК')]", 240);
    }

    @Step("Отказ от прохождения onboarding")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Позже");

        refresh();
        switchTo().alert().accept();

        new GUIFunctions().waitForElementDisplayed("//*[text()='Хочу, расскажите']")
                .clickButton("Хочу, расскажите")
                .waitForElementDisplayed("//*[text()='Возможности сервиса']")
                .clickButton("Пропустить")
                .waitForElementDisappeared("//*[text()='Господдержка. Сертификация продукции АПК']");

        refresh();
        switchTo().alert().accept();

        new GUIFunctions().waitForElementDisplayed("//*[text()='Господдержка. Сертификация продукции АПК']");
    }

    @Step("Ознакомление с возможностями Сервиса (onboarding)")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton("Хочу, расскажите")
                .waitForElementDisplayed("//*[text()='Возможности сервиса']")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Требования к экспортеру']")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Проверка организации и заявления']")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Помощь и подсказки']")
                .clickButton("Завершить ознакомление")
                .waitForElementDisappeared("//*[text()='Господдержка. Сертификация продукции АПК']");

        refresh();
        switchTo().alert().accept();

        if ($x("//*[text()='Господдержка. Сертификация продукции АПК']").isDisplayed()){
            Assert.fail("Модальное окно всеравно отображается");
        }
    }
}

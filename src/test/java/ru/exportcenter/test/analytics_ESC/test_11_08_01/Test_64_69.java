package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;

import static com.codeborne.selenide.Selenide.*;

public class Test_64_69 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/analytics_ESC/Test_03_07_01_1/";
    public String requestNumber;

    @Owner(value="Петрищев Руслан")
    @Description("***")
    @Link(name="Test_64_69", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException, AWTException {
        precondition();
        step01();
    }

    @Step("Предусловия")
    public void precondition() throws InterruptedException, AWTException {
        CommonFunctions.printStep();

        Test_60_63 test_60_63 = new Test_60_63();
        test_60_63.steps();
    }

    @Step("Авторизация")
    public void step01() {

        requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");

        System.out.println("Шаг 65");

        open("https://tasks.t.exportcenter.ru/");

        new GUIFunctionsLKB().authorization("OPS", "password")
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");

        System.out.println("Шаг 66");
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Новые")
                .clickByLocator("//*[text()='" + requestNumber + " Шаг 14: Постановка на учет']")
                .clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 67");
        new GUIFunctions().clickByLocator("//*[text()='Завершить выполнение']/parent::button");

        System.out.println("Шаг 68");
        new GUIFunctions().clickByLocator("//*[text()='Назначить задачу на себя и завершить']/parent::button");

        System.out.println("Шаг 69");
        new GUIFunctions().clickByLocator("//*[text()='Поставить на учет']/parent::button")
                .waitForElementDisappeared("//*[text()='Поставить на учет']/parent::button");
        closeWebDriver();

    }
}

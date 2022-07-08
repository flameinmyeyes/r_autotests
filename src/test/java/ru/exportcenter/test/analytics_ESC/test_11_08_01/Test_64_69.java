package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import framework.integration.JupyterLabIntegration;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_64_69 extends Hooks {

    public String requestNumber = JupyterLabIntegration.getFileContent("/analytics_ESC/Test_03_07_01_1/requestNumber.txt");

    @Owner(value="***")
    @Description("***")
    @Link(name="Test_64_69", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
    }

    @Step("Авторизация")
    public void step01() {

        System.out.println("Шаг 65");

        open("https://lk.t.exportcenter.ru/");

        new GUIFunctions().authorization("OPS", "password")
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
        new GUIFunctions().clickByLocator("//*[text()='Поставить на учет']/parent::button");

    }

}

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
import java.awt.event.KeyEvent;

import static com.codeborne.selenide.Selenide.*;

public class Test_41_59 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/analytics_ESC/Test_03_07_01_1/";
    public String requestNumber;

    @Owner(value="Петрищев Руслан")
    @Description("***")
    @Link(name="Test_41_59", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException, AWTException {
        precondition();
        step01();
    }

    @Step("Предусловия")
    public void precondition() throws InterruptedException, AWTException {
        CommonFunctions.printStep();

        Test_37_40 test_37_40 = new Test_37_40();
        test_37_40.steps();
    }

    @Step("Авторизация")
    public void step01() throws AWTException {

        requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");

        System.out.println("Шаг 41");
        open("https://tasks.t.exportcenter.ru/");

        System.out.println("Шаг 42");
        new GUIFunctionsLKB().authorization("mosolov@exportcenter.ru", "password")
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");

        System.out.println("Шаг 43");
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Исполняются")
                .waitForElementDisplayed("//*[@class='ant-collapse-header']")
                .clickByLocator("//*[@class='ant-collapse-header']")
                .clickByLocator("//*[text()='" + requestNumber + " Шаг 10: Коммуникация с клиентом']")
                .clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 44");
        switchTo().frame("formApp");
        $x("//*[@data-icon='edit']").scrollTo();
        new GUIFunctionsLKB().clickByLocator("//*[@data-icon='edit']");

        System.out.println("Шаг 45");
        new GUIFunctionsLKB().clickByLocator("//span[text()='Обновить']");

        System.out.println("Шаг 46");
        switchTo().defaultContent();
        new GUIFunctionsLKB().clickByLocator("//*[text()='Завершить выполнение']/parent::button");

        System.out.println("Шаг 47");
        new GUIFunctionsLKB().clickByLocator("//*[text()='Перейти на следующий этап']/parent::button");

        System.out.println("Шаг 48");
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Исполняются")
                .waitForElementDisplayed("//*[@class='ant-collapse-header']");

        System.out.println("Шаг 49");
        new GUIFunctions().clickByLocator("//*[@class='ant-collapse-header']")
                .clickByLocator("//*[text()='" + requestNumber + " Шаг 11: Мониторинг взаимодействия экспортера и импортера']")
                .clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 50");
        //Выбрать значение «Да» в поле «Информация о заключенном контракте»

        System.out.println("Шаг 51");
        switchTo().frame("formApp");
        $x("//div[text()='Информация о заключенном контракте']").scrollTo();
        new GUIFunctionsLKB().clickByLocator("//div[text()='Информация о заключенном контракте']");
        new GUIFunctionsLKB().inField("Номер контракта").inputValue("11111")
                .inField("Сумма контракта").inputValue("100000");
        $x("//*[text()='Страна контракта']/following::input").setValue("Республика Азербайджан");
        $x("//div[text()='Республика Азербайджан']").click();
        $x("//*[text()='Валюта контракта']/following::input").setValue("Австралийский доллар");
        $x("//div[text()='AUD - Австралийский доллар']").click();
        $x("//label[text()='Дата начала контракта']/following::input").sendKeys("26.05.2022");
        $x("//label[text()='Дата окончания контракта']/following::input").sendKeys("26.05.2023");
        $x("//label[text()='Предмет контракта']/following::textarea").setValue("1");

        new GUIFunctionsLKB().clickButton("Добавить");
        $x("//label[text()='Код ТНВЭД']/following::input").setValue("5512110000");
        new GUIFunctionsLKB().waitForElementDisplayed("//*[contains(text(),'5512110000 - М2-ткани неотбеленные')]");
        $x("//*[contains(text(),'5512110000 - М2-ткани неотбеленные')]").click();
        $x("//label[text()='Доля от общего размера экспортного контракта']/following::input").setValue("100");
        new GUIFunctionsLKB().clickByLocator("(//span[text()='Добавить'])[3]");

//        $x("//*[text()='Добавить']/ancestor::span[@class='ant-upload']/input").sendKeys("C:\\auto-tests\\Контракт.docx");
        $x("//*[text()='Добавить']/ancestor::span[@class='ant-upload']/input").sendKeys("/share/" + WAY_TEST + "Контракт.docx");
        CommonFunctions.wait(2);
        new GUIFunctionsLKB().clickByLocator("//div[text()='Общая информация']");

        System.out.println("Шаг 52");
        $x("//label[text()='Результат']/following::textarea").setValue("1");

        System.out.println("Шаг 53");
        switchTo().defaultContent();
        new GUIFunctionsLKB().clickByLocator("//*[text()='Завершить выполнение']/parent::button");

        System.out.println("Шаг 54");
        new GUIFunctionsLKB().clickByLocator("//*[text()='Перейти на следующий этап']/parent::button");

        System.out.println("Шаг 55");
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Исполняются");

        for (int i = 0; i < 180; i++) {
            if (!$x("//*[@class='ant-collapse-header']").isDisplayed()){
                CommonFunctions.wait(1);
            }else {
                break;
            }
        }

        new GUIFunctionsLKB().clickByLocator("//*[@class='ant-collapse-header']")
                .clickByLocator("//*[text()='" + requestNumber + " Шаг 12: Проверка итогового отчета']");

        System.out.println("Шаг 56");
        new GUIFunctionsLKB().clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 57");
        new GUIFunctionsLKB().clickByLocator("//*[text()='Завершить выполнение']/parent::button");

        System.out.println("Шаг 58");
        new GUIFunctionsLKB().clickByLocator("//*[text()='Перейти на следующий этап']/parent::button")
                        .waitForElementDisappeared("//*[text()='Перейти на следующий этап']/parent::button");

        System.out.println("Шаг 59");
        closeWebDriver();

    }

}

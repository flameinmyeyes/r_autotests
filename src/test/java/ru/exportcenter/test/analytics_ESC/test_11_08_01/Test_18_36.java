package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.codeborne.selenide.Selenide.*;

public class Test_18_36 extends Hooks {

    //    public String requestNumber = new Test_1_17().requestNumber;
    public String requestNumber = "S/2022/185920";

    @Owner(value = "***")
    @Description("***")
    @Link(name = "Test_37_40", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException, AWTException {
        step01();
    }

    @Step("Авторизация")
    public void step01() throws AWTException {

        System.out.println("Шаг 18, 19");
        open("https://tasks.t.exportcenter.ru/");
//        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/");
        new functions.gui.fin.GUIFunctions().authorization("mosolov@exportcenter.ru", "password")
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");

        System.out.println("Шаг 20");

        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Исполняются");
        new GUIFunctions().clickByLocator("//*[@class='ant-collapse-header']");
//        $x("//div[@class='ant-col ant-col-15']//div[1]").click();
        new GUIFunctions().clickByLocator("//*[text()='" + requestNumber + " Шаг 6: Заполнение скоринга Клиента']");
        new GUIFunctions().clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 21");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);

        switchTo().frame("formApp");
        $x("//*[text()='Наличие российского представительства в стране']").scrollTo();

        selectValueInField("Наличие российского представительства в стране", "ТП/Агенты");
        selectValueInField("Наличие субсидиарных программ", "Нет");
        selectValueInField("Наличие специальных программ поддержки", "Нет");

        System.out.println("Шаг 22");
        $x("//*[text()='Завершить выполнение']/parent::button").scrollTo();
        new GUIFunctions().clickByLocator("//*[text()='Завершить выполнение']/parent::button");


        System.out.println("Шаг 23");
        new GUIFunctions().clickByLocator("//*[text()='Далее']/parent::button");

        System.out.println("Шаг 24");
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Исполняются")
                .waitForElementDisplayed("//*[@class='ant-collapse-header']");
        new GUIFunctions().clickByLocator("//*[@class='ant-collapse-header']");
        new GUIFunctions().clickByLocator("//*[text()='" + requestNumber + " Шаг 7: Проверка анкеты и документов']");
        new GUIFunctions().clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 25");
        new GUIFunctions().clickByLocator("//*[text()='Завершить выполнение']/parent::button");

        System.out.println("Шаг 26");
        new GUIFunctions().clickByLocator("//*[text()='Далее']/parent::button");

        System.out.println("Шаг 27");
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Распределить");

        System.out.println("Шаг 28, 29");
        $x("//*[text()='" + requestNumber + " Шаг 8: Сформировать список покупателей']").dragAndDropTo($x("//*[text()='10']/parent::div//following::div[@class='DateCell_dropZone__2m5N4']"));
        CommonFunctions.wait(1);
        CommonFunctions.wait(1);

        System.out.println("Шаг 30");
        new GUIFunctions().clickByLocator("//*[text()='" + requestNumber + " Шаг 8: Сформировать список покупателей']");
        new GUIFunctions().clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 31");
        CommonFunctions.wait(1);
        CommonFunctions.wait(1);


        System.out.println("Шаг 40");
        closeWebDriver();

    }

    public void selectValueInField(String field, String value) {
        $x("(//*[text()='" + field + "']/following::span[@class='ant-select-selection-item'])[1]").click();
        $x("//*[text()='" + field + "']//following::*[text()='" + value + "']").click();
    }
}

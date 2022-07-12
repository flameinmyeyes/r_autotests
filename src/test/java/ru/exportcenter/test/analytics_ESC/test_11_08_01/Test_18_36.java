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

public class Test_18_36 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/analytics_ESC/Test_03_07_01_1/";
    public String requestNumber;

    @Owner(value="Петрищев Руслан")
    @Description("***")
    @Link(name = "Test_18_36", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException, AWTException {
        precondition();
        step01();
    }

    @Step("Предусловия")
    public void precondition() throws InterruptedException, AWTException {
        CommonFunctions.printStep();

        Test_1_17 test_1_17 = new Test_1_17();
        test_1_17.steps();
    }

    @Step("Авторизация")
    public void step01() throws AWTException {

        requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");
        Robot robot = new Robot();

        System.out.println("Шаг 18, 19");
        open("https://tasks.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization("mosolov@exportcenter.ru", "password")
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");

        System.out.println("Шаг 20");
        System.out.println(requestNumber);
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Исполняются");

        for (int i = 0; i < 240; i++) {
            if (!$x("//*[@class='ant-collapse-header']").isDisplayed()){
                CommonFunctions.wait(1);
            }else {
                break;
            }
        }

        new GUIFunctions().clickByLocator("//*[@class='ant-collapse-header']")
                .clickByLocator("//*[text()='" + requestNumber + " Шаг 6: Заполнение скоринга Клиента']")
                .clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 21");


        robot.keyPress(KeyEvent.VK_PAGE_DOWN);

        switchTo().frame("formApp");
        $x("//*[text()='Наличие российского представительства в стране']").scrollTo();

        selectValueInField("Наличие российского представительства в стране", "ТП/Агенты");
        selectValueInField("Наличие субсидиарных программ", "Нет");

        CommonFunctions.wait(1);
        $x("//*[text()='Наличие специальных программ поддержки']/following::input").click();
        $x("(//*[text()='Наличие специальных программ поддержки']//following::div[text()='Нет'])[2]").click();

        switchTo().defaultContent();

        System.out.println("Шаг 22");
        robot.keyPress(KeyEvent.VK_PAGE_UP);
        new GUIFunctions().clickByLocator("//*[text()='Завершить выполнение']/parent::button");


        System.out.println("Шаг 23");
        new GUIFunctions().clickByLocator("//*[text()='Далее']/parent::button")
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");

        System.out.println("Шаг 24");
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Исполняются")
                .waitForElementDisplayed("//*[@class='ant-collapse-header']")
                .clickByLocator("//*[@class='ant-collapse-header']")
                .clickByLocator("//*[text()='" + requestNumber + " Шаг 7: Проверка анкеты и документов']")
                .clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 25");
        new GUIFunctions().clickByLocator("//*[text()='Завершить выполнение']/parent::button");

        System.out.println("Шаг 26");
        new GUIFunctions().clickByLocator("//*[text()='Далее']/parent::button")
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");

        System.out.println("Шаг 27");
        refresh();
        new GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickButton("Распределить");

        System.out.println("Шаг 28, 29");
        for (int i = 0; i < 240; i++) {
            if (!$x("//*[text()='" + requestNumber + " Шаг 8: Сформировать список покупателей']").isDisplayed()){
                CommonFunctions.wait(1);
            }else {
                break;
            }
        }

        System.out.println("Шаг 30");
        new GUIFunctions().clickByLocator("//*[text()='" + requestNumber + " Шаг 8: Сформировать список покупателей']")
                .clickByLocator("//*[text()='К выполнению']/parent::button");

        System.out.println("Шаг 31");
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        switchTo().frame("formApp");
        $x("//*[text()='Выбрать компанию']").scrollTo();
        $x("//*[contains(text(),'Выбрать компанию')]/parent::*/descendant::input").setValue("4027064200");
        $x("//*[contains(text(),'4027064200 - -')]").click();
        new GUIFunctionsLKB().clickByLocator("//*[text()='Добавить']/parent::button");

        System.out.println("Шаг 32");
        new GUIFunctionsLKB().clickByLocator("//*[@data-icon='edit']");

        System.out.println("Шаг 33");
        $x("//label[@title='Готовность компании предоставить свои контактные данные третьим лицам']").scrollTo();

        inputTextInField("Деятельность", "1");
        inputTextInField("Формат проведения переговоров", "2");
        inputTextInField("Рекомендации экспортеру по развитию дальнейших коммуникаций с контрагентом", "3");
        $x("(//*[text()='Оценка интереса']/following::span[@class='ant-select-selection-item'])[1]").click();
        $x("//div[@label='Заинтересован']//div[1]").click();
        inputTextInField("Содержание переговоров", "4");
        $x("//label[text()='Готовность компании предоставить свои контактные данные третьим лицам']/following::input").click();
        $x("//div[@label='Да']//div[1]").click();
        new GUIFunctionsLKB().clickByLocator("//*[text()='Обновить']/parent::button");
        switchTo().defaultContent();
        robot.keyPress(KeyEvent.VK_PAGE_UP);

        new GUIFunctionsLKB().clickByLocator("//button[contains(@class,'ant-btn TaskTimeline_escalation')]");
        $x("//form[@id='escalate_form']//textarea[1]").setValue("123");
        new GUIFunctionsLKB().clickByLocator("//*[text()='OK']/parent::button");

        System.out.println("Шаг 34");
        new GUIFunctionsLKB().clickByLocator("//*[text()='Завершить выполнение']/parent::button")
                .waitForElementDisplayed("//*[text()='Назначить задачу на себя и завершить']/parent::button");

        System.out.println("Шаг 35");
        new GUIFunctionsLKB().clickByLocator("//*[text()='Назначить задачу на себя и завершить']/parent::button")
                .waitForElementDisplayed("//*[text()='Далее']/parent::button")
                .clickByLocator("//*[text()='Далее']/parent::button")
                .waitForElementDisappeared("//*[text()='Далее']/parent::button");


        System.out.println("Шаг 36");
        closeWebDriver();
    }

    public void inputTextInField(String field, String text) {
        $x("//*[text()='" + field + "']/ancestor::div[@class='ant-row ant-form-item']//following::textarea").setValue(text);
    }

    public void selectValueInField(String field, String value) {
        $x("//*[text()='" + field + "']/following::input").click();
        $x("(//*[text()='" + field + "']//following::div[text()='" + value + "'])[1]").click();
    }

    public void selectValueInField(String field, String value, Integer index) {
        $x("//*[text()='" + field + "']/following::input").click();
        $x("//div[@label='" + value + "']//div[" + index + "]").click();
    }
}

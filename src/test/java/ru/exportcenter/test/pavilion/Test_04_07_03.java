package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_03  extends Hooks {
    // qqq
    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_01/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_03_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 03 Подписание Акта")
    @Link(name = "Test_04_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170242302")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
        step02();
        step03();

    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_04_07_02 test_04_07_02 = new Test_04_07_02();
        test_04_07_02.steps();
        requestNumber = test_04_07_02.requestNumber;
    }

    @Step("«»")
    public void step01() {
        CommonFunctions.printStep();
//        requestNumber = "S/2022/262278";

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/");
        new GUIFunctions().authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='Продолжить']")
                .clickButton("Продолжить");

//        refreshTab("//*[text()='Продолжить']", 10);

//        $x("//span[text()='Далее']").scrollTo();

        new GUIFunctions().clickButton("Далее")
                .waitForLoading();
        closeWebDriver();
    }

    @Step("«»")
    public void step02() {
        CommonFunctions.printStep();

        open("http://arm-pavilion.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization("pavilion_operator_nr@otr.ru","Password1!");

        new GUIFunctionsLKB().clickByLocator("//span[@title='Все задачи']")
                .clickByLocator("//div[@title='Проверить сведения о продукции']")
                .clickByLocator("(//li[text()='Проверить сведения о продукции'])[1]")
                .waitForElementDisplayed("//span[text()='Согласовать']")
                .clickByLocator("//span[text()='Согласовать']")
                .clickByLocator("//*[text()='OK']");

        new GUIFunctionsLKB().clickByLocator("//span[@title='Все задачи']")
                .clickByLocator("//div[@title='Подписать Акт приёмки продукции']")
                .clickByLocator("(//li[text()='Подписать Акт приёмки продукции'])[1]")
                .clickByLocator("//span[text()='Редактировать']");

//        $x("(//input[@placeholder='Выберите дату'])[2]").setValue("29.08.2022").pressEnter();
        $x("(//input[@placeholder='Выберите дату'])[2]").sendKeys("29.08.2022");
        $x("(//input[@placeholder='Выберите дату'])[2]").pressEnter();

        new GUIFunctionsLKB().clickByLocator("//span[text()='Сохранить']");
        $x("//span[text()='Далее']").scrollTo();

        new GUIFunctionsLKB().clickByLocator("//span[text()='Далее']");
        closeWebDriver();
    }

    @Step("«»")
    public void step03() {
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/");
        new GUIFunctions().authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='Продолжить']")
                .clickButton("Продолжить");

//        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForLoading();

//                .waitForElementDisplayed("(//*[contains(text(), 'Господдержка. Демонстрационно-дегустационные павильоны АПК')])[1]")
//                .clickByLocator("(//*[contains(text(), 'Господдержка. Демонстрационно-дегустационные павильоны АПК')])[1]");


        String url = webdriver().driver().getWebDriver().getCurrentUrl();
        System.out.println(url);
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh(" + expectedXpath + ")");
            CommonFunctions.wait(1);
        }
    }

}

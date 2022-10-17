package ru.exportcenter.test.apk.Test_02_07_00;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.apk.Test_01_07_05_2;

import java.awt.*;

import static com.codeborne.selenide.Selenide.*;

public class Test_02_07_00_3 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_02_07_00/Test_02_07_00_3/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_01_1_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
//    public String requestNumber = null;
    public String requestNumber = "S/2022/304460";
    public String identifier;

    @Owner(value = "Петрищев Руслан")
    @Description("02 07 00.3 Получение идентификатора заявки")
    @Link(name = "Test_02_07_00_3", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183188767")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_02_07_00_2 test_02_07_00_2 = new Test_02_07_00_2();
        test_02_07_00_2.steps();
    }

    @Step("Получение id заявки")
    public void step01() {
        CommonFunctions.printStep();

        new GUIFunctionsLKB()
                .clickByLocator("//div[text()='camunda-exp-search']")
                .clickByLocator("//div[text()='camunda-exp-search']/following::div[text()='Cockpit']");

        switchTo().frame($x("//iframe[contains(@src,'/camunda/')]"));

        new GUIFunctionsLKB()
                .waitForElementDisplayed("//a[text()='Процессы']")
                .clickByLocator("//a[text()='Процессы']")
                .waitForLoading();

        new GUIFunctions()
                .clickByLocator("//a[contains(text(),'apkNaVr')]")
                .waitForLoading();

        //скопировать в буфер
//        $x("//span[contains(text(),'" + requestNumber + "')]/ancestor::tr/td[@class='instance-id ng-isolate-scope']/a[contains(@tooltip,'Нажмите, чтобы скопировать')]").click();

        identifier = $x("//span[contains(text(),'" + requestNumber + "')]/ancestor::tr/td[@class='instance-id ng-isolate-scope']/span/a").getText();
        System.out.println(identifier);
    }
}

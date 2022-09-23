package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;

import static com.codeborne.selenide.Selenide.open;

public class Test_01_07_10_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_10_1/";
    private String requestNumber;

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 10.1 Просмотр перечня Заявок по сервису, созданных организацией")
    @Link(name = "Test_01_07_10_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170236223")
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

        Test_01_07_05_2 test_01_07_05_2 = new Test_01_07_05_2();
        test_01_07_05_2.steps();
        requestNumber = test_01_07_05_2.requestNumber;
    }

    @Step("Навигация и создание заявки")
    public void step01() {
        CommonFunctions.printStep();

        System.out.println(requestNumber);
        open("https://lk.t.exportcenter.ru/ru/main");
        new GUIFunctions().clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='" + requestNumber + "']");

    }
}

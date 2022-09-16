package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_01_07_05_2 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_05_2/";
    public String requestNumber;

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 05.2 Быстрое создание новой Заявки")
    @Link(name = "Test_01_07_05_2", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163299314")
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

        Test_01_07_01_1 test_01_07_01_1 = new Test_01_07_01_1();
        test_01_07_01_1.steps();
    }

    @Step("Быстрое создание")
    public void step01() {
        CommonFunctions.printStep();

        //Перейти на https://lk.t.exportcenter.ru/ru/promo-service?key=apkNaVr&serviceId=b4ac4be3-224e-4277-8178-7eafd954725f&next_query=true
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=apkNaVr&serviceId=b4ac4be3-224e-4277-8178-7eafd954725f&next_query=true");
        new GUIFunctions().waitForElementDisplayed("//div[text()='Номер заявки']/following-sibling::div");
        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
    }
}

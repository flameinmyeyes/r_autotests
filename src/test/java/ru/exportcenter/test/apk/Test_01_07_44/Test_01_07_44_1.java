package ru.exportcenter.test.apk.Test_01_07_44;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.apk.Test_01_07_05_2;
import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;

public class Test_01_07_44_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_44/Test_01_07_44_1/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_44_1_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 44.1 Проверка на обязательность заполнения поля")
    @Link(name = "Test_01_07_44_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170236438")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
        step02();
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

        new GUIFunctions()
                .refreshTab("Продолжить", 10)
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[contains(text(),'Господдержка. Сертификация продукции')]");
    }

    @Step("Заполнение блока \"Информация о Заявителе\"")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .closeAllPopupWindows()
                .inField("Доверенное лицо").setCheckboxON().assertCheckboxON()
                .inField("ФИО").selectValue(P.getProperty("Информация о Заявителе.ФИО1") + " " +
                                            P.getProperty("Информация о Заявителе.ФИО2") + " " +
                                            P.getProperty("Информация о Заявителе.ФИО3"))
                               .assertValue(P.getProperty("Информация о Заявителе.ФИО1") + " " +
                                            P.getProperty("Информация о Заявителе.ФИО2") + " " +
                                            P.getProperty("Информация о Заявителе.ФИО3"));

        $x("//*[text()='ФИО']/following::input").click();
        $x("//*[text()='ФИО']/following::input").sendKeys(Keys.LEFT_CONTROL + "a");
        $x("//*[text()='ФИО']/following::input").sendKeys(Keys.BACK_SPACE);

        new GUIFunctions()
                .waitForLoading()
                .clickByLocator("//*[text()='ФИО']")
                .waitForElementDisplayed("//input[@placeholder='Введите ФИО']//following::*[text()='Выберите значение из выпадающего списка']");
    }
}

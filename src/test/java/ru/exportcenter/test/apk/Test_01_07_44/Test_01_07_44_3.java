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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.apk.Test_01_07_05_2;
import java.awt.*;
import java.util.Properties;

public class Test_01_07_44_3 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_44/Test_01_07_44_3/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_44_3_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 44.3 Проверка типа \"буквы (кириллица), пробел и тире\"")
    @Link(name = "Test_01_07_44_3", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170236525")
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
                .waitForElementDisplayed("//*[contains(text(),'Господдержка. Сертификация продукции')]", 240);
    }

    @Step("Заполнение блока \"Информация о Заявителе\"")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .closeAllPopupWindows()
                .inField("Дополнительный контакт").setCheckboxON().assertCheckboxON()
                .inField("Новый дополнительный контакт").setCheckboxON().assertCheckboxON()
                .inField("Имя").inputValue("Усть-Пос адский").assertValue()
                .clickByLocator("//*[text()='Имя']/following::button[@name='close']")
                .inField("Имя").inputValue("Усть-Посадский D2").assertValue()
                .waitForElementDisplayed("//*[text()='Имя']//following::*[text()='Только буквы (кириллица), пробел и тире']");

    }
}

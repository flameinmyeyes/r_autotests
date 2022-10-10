package ru.exportcenter.test.apk.Test_01_07_44;

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
import ru.exportcenter.test.apk.Test_01_07_05_2;
import java.awt.*;

public class Test_01_07_44_2 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_44/Test_01_07_44_2/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_44_2_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 44.2 Проверка на ограничение количества символов")
    @Link(name = "Test_01_07_44_2", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170236499")
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
                .inField("Дополнительный контакт").setCheckboxON().assertCheckboxON()
                .inField("Новый дополнительный контакт").setCheckboxON().assertCheckboxON();

        StringBuilder expectedSurname = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            expectedSurname.append("a");
        }
        System.out.println(expectedSurname);
        new GUIFunctions()
                .inField("Фамилия").inputValue(String.valueOf(expectedSurname)).assertValue()
                .clickByLocator("//*[text()='Фамилия']/following::button[@name='close']");

        expectedSurname.append("a");

        new GUIFunctions()
                .inField("Фамилия").inputValue(String.valueOf(expectedSurname)).assertValue()
                .waitForElementDisplayed("//*[text()='Фамилия']//following::*[text()='Максимальное количество символов — 200']");
    }
}

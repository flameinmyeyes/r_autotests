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

public class Test_01_07_44_4 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_44_4/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_44_4_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 44.4 Проверка типа \"дата\"")
    @Link(name = "Test_01_07_44_4", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170236569")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
        step02();
        step03();
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

        new GUIFunctions().refreshTab("Продолжить", 10)
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Далее']");
    }

    @Step("Заполнение блока \"Информация о Заявителе\"")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Добавить +']");
    }

    @Step("Заполнение блока \"Сведения о затратах\"")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("//*[text()='Добавить +']")
                .inField("Вид затраты, связанной с сертификацией продукции").selectValue("11\u00a0Услуги по хранению образцов\u00a0").assertValue()
                .inField("Основание понесенных затрат").selectValue("Требование контракта")
                .uploadFile("Загрузить шаблон", "C:\\auto-tests\\Документ к Затрате 11.xlsx");
    }

    @Step("Заполнение блока \"Загрузка подтверждающих документов\"")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions().inContainer("Загрузка подтверждающих документов")
                .uploadFile("Подтверждающие документы", "C:\\auto-tests\\capsule_616x353.zip");
    }
}

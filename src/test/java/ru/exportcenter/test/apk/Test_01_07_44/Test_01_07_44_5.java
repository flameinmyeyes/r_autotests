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

public class Test_01_07_44_5 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_44/Test_01_07_44_5/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_44_4_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 44.5 Проверка типа \"маска\"")
    @Link(name = "Test_01_07_44_5", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170237336")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
//        step02();
//        step03();
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
                .waitForElementDisplayed("//*[text()='Сведения о затратах']");
    }

    @Step("Заполнение блока \"Сведения о затратах\"")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("//*[text()='Добавить +']")
                .inField("Вид затраты, связанной с сертификацией продукции").selectValue("11\u00a0Услуги по хранению образцов\u00a0").assertValue()
                .waitForElementDisplayed("//*[text()='Шаблон к Затрате 11.xlsm']")
                .inField("Основание понесенных затрат").selectValue("Требование контракта")
                .uploadFile("Загрузить шаблон", "C:\\auto-tests\\Документ к Затрате 11.xlsx")
                .waitForElementDisplayed("//*[text()='Документ к Затрате 11.xlsm']");
    }

    @Step("Заполнение блока \"Загрузка подтверждающих документов\"")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions().uploadFile("Подтверждающие документы", "C:\\auto-tests\\capsule_616x353.zip")
                .uploadFile("Платежное поручение", "C:\\auto-tests\\capsule_616x353.zip")
                .clickButton("Далее");
    }

    @Step("Заполнение блока \"Подтверждение сведений заявителем\"")
    public void step05() {
        CommonFunctions.printStep();



    }

}

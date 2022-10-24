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

import static com.codeborne.selenide.Selenide.$x;

public class Test_01_07_44_5 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_44/Test_01_07_44_5/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_44_4_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 44.5 Проверка типа \"маска\"")
    @Link(name = "Test_01_07_44_5", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170237336")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
        step02();
        step03();
        step04();
        step05();
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
                .clickButton("Далее")
                .waitForLoading()
                .waitForLoading()
                .closeAllPopupWindows()
                .waitForElementDisplayed("//*[text()='Добавить +']");
    }

    @Step("Заполнение блока \"Сведения о затратах\"")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton("Добавить +")
                .inField("Вид затраты, связанной с сертификацией продукции").selectValue(P.getProperty("Сведения о затратах.Вид затраты1") + " " +
                                                                                         P.getProperty("Сведения о затратах.Вид затраты2"))
                                                                            .assertValue(P.getProperty("Сведения о затратах.Вид затраты1") + " " +
                                                                                         P.getProperty("Сведения о затратах.Вид затраты2"))
                .inField("Основание понесенных затрат").selectValue(P.getProperty("Сведения о затратах.Основание понесенных затрат"))
                .uploadFile("Загрузить шаблон", "/share/" + WAY_TEST + "Шаблон 1 - фаст (1).xlsm");
//                .uploadFile("Загрузить шаблон", "C:\\auto-tests\\Шаблон 1 - фаст (1).xlsm");
    }

    @Step("Заполнение блока \"Загрузка подтверждающих документов\"")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inContainer("Загрузка подтверждающих документов")
                .scrollTo("Подтверждающие документы")
                .uploadFile("Подтверждающие документы", "/share/" + WAY_TEST + "rec.zip")
//                .uploadFile("Подтверждающие документы", "C:\\auto-tests\\rec.zip")
                .uploadFile("Платежное поручение", "/share/" + WAY_TEST + "payment 228.zip");
//                .uploadFile("Платежное поручение", "C:\\auto-tests\\payment 228.zip");

        new GUIFunctions()
                .scrollTo("Далее")
                .clickButton("Далее")
                .waitForElementDisplayed("//span[contains(text(),'Господдержка. Сертификация продукции')]");
    }

    @Step("Заполнение блока \"Подтверждение сведений заявителем\"")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .scrollTo($x("//*[text()='Контактные данные лица, ответственного за работу в ГИИС \"Электронный бюджет\"']"))
                .inContainer("Контактные данные лица, ответственного за работу в ГИИС \"Электронный бюджет\"")
                .inField("E-mail").inputValue(P.getProperty("Подтверждение сведений заявителем.E-mail1")).assertValue()
                .clickByLocator("//*[text()='E-mail']/following::button[@name='close']")
                .inField("E-mail").inputValue(P.getProperty("Подтверждение сведений заявителем.E-mail2")).assertValue()
                .waitForElementDisplayed("//*[text()='E-mail']/following::*[text()='Укажите e-mail']");
    }
}

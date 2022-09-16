package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;

import static com.codeborne.selenide.Selenide.*;

public class Test_01_07_05_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_04_07_01/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_01_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 05.1 Создание новой Заявки")
    @Link(name = "Test_01_07_05_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163299306")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
    }

    //    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_01_07_01_1 test_04_07_01_1 = new Test_01_07_01_1();
        test_04_07_01_1.steps();
    }

    @Step("Навигация и создание заявки")
    public void step01() {
        CommonFunctions.printStep();

        //Перейти на https://master-portal.t.exportcenter.ru/services/
        open("https://master-portal.t.exportcenter.ru/services/");

        //В строке поиска ввести «Господдержка. Сертификация продукции АПК» и нажать на икноку "Лупа"
        $x("//input[@placeholder='Поиск по разделу']").setValue("Господдержка. Сертификация продукции АПК").pressEnter();
        new GUIFunctions().waitForElementDisplayed("//*[text()='По вашему запросу ']");


//        new GUIFunctions().inputInSearchField("Поиск по разделу", "Господдержка. Сертификация продукции АПК")
//                .waitForElementDisplayed("//*[text()='По вашему запросу ']");

        String infoText = $x("//div[@class='col-12 pb-2']").getText();
        System.out.println(infoText);

        //Выбрать вкладку «Государственные»
        new GUIFunctions().clickButton("Государственные")
                .waitForElementDisplayed("//div[text()='Господдержка. Сертификация продукции АПК']");

        //Нажать кнопу "Оформить"
        new GUIFunctions().clickButton("Оформить");
        switchTo().window(1);
        new GUIFunctions().waitForElementDisplayed("//*[text()='Господдержка. Сертификация продукции АПК']//following::div[text()='Черновик']");

    }

}

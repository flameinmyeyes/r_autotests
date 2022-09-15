package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.pavilion.Test_04_07_01;

import java.awt.*;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_01_07_02_1 extends Hooks {

//    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_01/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_01_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 02.1 Выбор Сервиса. Ознакомление с описанием Сервиса")
    @Link(name = "Test_01_07_02_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163299184")
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

    @Step("Навигация через Поиск")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://master-portal.t.exportcenter.ru/services/");
        new GUIFunctions().inputInSearchField("Поиск по разделу", "Господдержка. Сертификация продукции АПК")
                .waitForElementDisplayed("//*[text()='По вашему запросу ']");

        String infoText = $x("//div[@class='col-12 pb-2']").getText();
        System.out.println(infoText);

        new GUIFunctions().clickButton("Государственные")
                .waitForElementDisplayed("//div[text()='Господдержка. Сертификация продукции АПК']");

    }

    @Step("Получение информации о сервисе")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Подробнее");

    }
}

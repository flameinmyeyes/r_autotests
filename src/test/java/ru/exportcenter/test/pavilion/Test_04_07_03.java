package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class Test_04_07_03  extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_03_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 03 Подписание Акта")
    @Link(name = "Test_04_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170242302")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
//        step02();
//        step03();
//        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("«»")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=pavilion&serviceId=a546931c-0eb9-4545-853a-8a683c0924f7&next_query=true");
        new GUIFunctions().authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

    }

    @Step("«»")
    public void step02() {
        CommonFunctions.printStep();

        open("http://arm-pavilion.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization("pavilion_operator_nr@otr.ru","Password1!");

        new GUIFunctionsLKB().clickButton("Согласовать");
    }

    @Step("«»")
    public void step03() {
        CommonFunctions.printStep();


    }

}

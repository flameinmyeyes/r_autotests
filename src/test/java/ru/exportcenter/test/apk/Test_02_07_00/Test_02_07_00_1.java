package ru.exportcenter.test.apk.Test_02_07_00;

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

public class Test_02_07_00_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_02_07_00/Test_02_07_00_1/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_01_1_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("02 07 00.1 Авторизация в МДМ")
    @Link(name = "Test_02_07_00_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175248692")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль, нажать «Войти»
        open("https://mdm.t.exportcenter.ru/");
        new GUIFunctionsLKB()
                .authorization("mdm_admin", "password")
                .waitForURL("https://mdm.t.exportcenter.ru/catalog");
    }
}

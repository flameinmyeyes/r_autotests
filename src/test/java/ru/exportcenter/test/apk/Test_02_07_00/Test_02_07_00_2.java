package ru.exportcenter.test.apk.Test_02_07_00;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
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

import static com.codeborne.selenide.Selenide.*;

public class Test_02_07_00_2 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_02_07_00/Test_02_07_00_2/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_01_1_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("02 07 00.2 Авторизация в bpms")
    @Link(name = "Test_02_07_00_2", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183188555")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация в МДМ")
    public void step01() {
        CommonFunctions.printStep();

        open("https://bpms.t.exportcenter.ru/main");
        new GUIFunctionsLKB().authorization("bpmn_admin", "password")
                .waitForURL("https://bpms.t.exportcenter.ru/main");
    }
}

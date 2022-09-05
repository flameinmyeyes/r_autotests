package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import functions.api.RESTFunctions;
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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_04 extends Hooks {

//    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_04/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_04_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Теребков Андрей")
    @Description("04 07 04 Годовой отчет экспортера")
    @Link(name = "Test_04_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170242979")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
//        step02();
//        step03();
//        step04();
    }

    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Блок «Сведения о демонстрационно-дегустационном павильоне»")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/bpmn/swagger-ui/");
        String token = RESTFunctions.getAccessToken("bpmn_admin");

        System.out.println(token);

        new GUIFunctions()
                .clickByLocator("//span[text()='Authorize']");
        $x("//span[text()='Authorize']/following::input").setValue(token);
        new GUIFunctions()
                .clickByLocator("//button[text()='Authorize']")
                .clickByLocator("//button[text()='Close']")
                .clickByLocator("//*[@id='operations-tag-bpmn-process-rest-api']/a/span")
                .clickByLocator("//span[text()='POST']")
                .clickByLocator("//button[text()='Try it out ']");


        new GUIFunctions().waitForURL("");
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh()");
            CommonFunctions.wait(1);
        }
    }
}
package ru.exportcenter.test.SPT;

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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_07_07_01 extends Hooks {

    //    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_01/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_01_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.01 Авторизация, создание заявки, отмена заявки клиентом")
    @Link(name = "Test_07_07_01", url = " https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175264454")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
        step02();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        String url = "http://uidm.uidm-dev.d.exportcenter.ru/promo-service?key=service-spt&serviceId=27ddd0b2-5ed9-4100-9c93-2b0e07a2d599&next_query=true";
        open(url);
        new GUIFunctions()
                .authorization("demo_exporter", "password")
                .waitForLoading();

        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
//        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
        System.out.println("requestNumber = " + requestNumber);
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForLoading();

        CommonFunctions.wait(10);


        $("//*[@id='form-open-panel']/div[1]/span/div/div[2]/div[1]/div[2]/button").click();

//      $("//*[@id=эform-open-panelэ]/div[1]/span/div/div[2]/div[1]/div[2]/ul/li").click();

        new GUIFunctions()
                .waitForURL("");
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

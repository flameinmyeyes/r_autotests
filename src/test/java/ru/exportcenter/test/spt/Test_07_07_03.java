package ru.exportcenter.test.spt;

import framework.RunTestAgain;
import framework.integration.JupyterLabIntegration;
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
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_07_07_03 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_03_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.03 Нажатие кнопки «К перечню заявлений»")
    @Link(name = "Test_07_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175264808")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
//        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        String url = "http://uidm.uidm-dev.d.exportcenter.ru/promo-service?key=service-spt&serviceId=27ddd0b2-5ed9-4100-9c93-2b0e07a2d599&next_query=true";
        open(url);
        new GUIFunctions()
                .waitForLoading()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForLoading();
        CommonFunctions.wait(15);
//      requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        System.out.println("class = " + $x("//*[@id='form-open-panel']/div[1]/span/div/div[2]/div[2]/div").getAttribute("class"));
        requestNumber = $x("//body").getAttribute("id");
        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
//      requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");
        System.out.println("requestNumber = " + requestNumber);
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Нажатие кнопки «К перечню заявлений»")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton("К перечню заявлений")
                .waitForLoading();
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

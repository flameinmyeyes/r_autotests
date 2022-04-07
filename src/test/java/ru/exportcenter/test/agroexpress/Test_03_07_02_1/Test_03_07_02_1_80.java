package ru.exportcenter.test.agroexpress.Test_03_07_02_1;

import com.google.gson.JsonObject;
import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.file.JSONHandler;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;
import ru.exportcenter.test.agroexpress.HooksTEST_agroexpress;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02_1_80 extends HooksTEST {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_80/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_70/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_80_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String token;
    private String docUUID;
    private String docNum;

    @Owner(value="Теребкова Андрей")
    @Description("03 07 02.1.80 Отправка сведений ПД / ЭПД (загрузка ЭПД)")
    @Link(name="Test_03_07_02_1_80", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123879970")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        preconditions();
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Предусловия")
    public void preconditions() {
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

        if(!status.equals("Оплата счёта")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_02_1_70 test_03_07_02_1_70 = new Test_03_07_02_1_70();
            test_03_07_02_1_70.steps();
            CommonFunctions.wait(10);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        }
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        new GUIFunctions()
            .authorization(
                PROPERTIES.getProperty("Логин")
                ,PROPERTIES.getProperty("Пароль")
                ,PROPERTIES.getProperty("Код")
            )
            .waitForLoading();
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();
        docNum = JupyterLabIntegration.getFileContent("/files_for_tests/test/agroexpress/Test_03_07_02_1/Test_03_07_02_1_10/docNum.txt");

        System.out.println(docNum);

        new GUIFunctions()
            .clickByLocator("//*[@class='Button_text__3lYJC']");

        new GUIFunctions()
            .clickByLocator("//*[text()='№" + docNum + "']")
            .waitForElementDisplayed("//div[text()='Статус']/following-sibling::div[text()='Подтверждение выбранных услуг']");

        refreshTab("//*[contains(text(), 'Продолжить')]", 60);

        new GUIFunctions()
            .closeAllPopupWindows()
            .clickButton("Продолжить")
            .waitForElementDisplayed("//*[text()='Подтверждение оплаты счета']");
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh()");
            CommonFunctions.wait(1);
        }
    }
}
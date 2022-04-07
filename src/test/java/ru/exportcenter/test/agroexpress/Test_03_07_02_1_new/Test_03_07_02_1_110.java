package ru.exportcenter.test.agroexpress.Test_03_07_02_1_new;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.agroexpress.HooksTEST_agroexpress;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02_1_110 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_110/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_100/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_110_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String token;
    private String docUUID;

    @Owner(value="Ворожко Александр")
    @Description("03 07 02.1.110 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_110", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123882163")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Предусловия")
    public void precondition() {
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        docUUID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "docUUID.txt");
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

        if(!status.equals("Оказание услуги")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_02_1_100 test_03_07_02_1_100 = new Test_03_07_02_1_100();
            test_03_07_02_1_100.steps();
            CommonFunctions.wait(20);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        }
    }

    @Step("Авторизация в ЕЛК")
    public void step01() {
        CommonFunctions.printStep();
        open("https://lk.t.exportcenter.ru/ru/services/drafts/info/" + processID);
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), PROPERTIES.getProperty("Авторизация.Код"))
                .waitForLoading()
                .closeAllPopupWindows();
        refreshTab("//*[contains(text(), 'Продолжить')]", 60);
    }

    @Step("Навигация в ЕЛК")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .closeAllPopupWindows()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text() = 'Осуществляется перевозка']");
    }

    @Step("Проверка изменения статуса заявки")
    public void step03() {
        CommonFunctions.printStep();

        String status = null;
        for (int i = 1; i < 20; i++) {
            status = RESTFunctions.getOrderStatus(processID);
            if (status.equals("Формирование закрывающих документов")) {
                break;
            } else {
                CommonFunctions.wait(60);
            }
        }

        Assert.assertEquals(status, "Формирование закрывающих документов");

        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }

}

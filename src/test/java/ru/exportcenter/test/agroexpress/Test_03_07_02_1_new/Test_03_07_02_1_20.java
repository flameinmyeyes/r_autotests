package ru.exportcenter.test.agroexpress.Test_03_07_02_1_new;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.agroexpress.HooksTEST_agroexpress;

import java.util.Properties;

public class Test_03_07_02_1_20 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_20/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_10/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_20_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value="Ворожко Александр")
    @Description("03 07 02.1.20 Получение результатов верификации от АО \"РЖД Логистика\"")
    @Link(name="Test_03_07_02_1_20", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123870742")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Предусловия")
    public void precondition() {
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

        if(!status.equals("Проводится проверка")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_02_1_10 test_03_07_02_1_10 = new Test_03_07_02_1_10();
            test_03_07_02_1_10.steps();
            CommonFunctions.wait(20);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        }
    }

    @Step("Проверка изменения статуса заявки")
    public void step01() {
        CommonFunctions.printStep();

        String status = null;
        for (int i = 1; i < 20; i++) {
            System.out.println("Идёт ожидание изменения статуса заявки...");
            status = RESTFunctions.getOrderStatus(processID);
            if (status.equals("Расчёт стоимости")) {
                break;
            } else {
                CommonFunctions.wait(60);
            }
        }

        Assert.assertEquals(status, "Расчёт стоимости");

        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

}

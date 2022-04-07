package ru.exportcenter.test.agroexpress.Test_03_07_02_1_new;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.agroexpress.HooksTEST_agroexpress;

public class Test_03_07_02_1_100 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_100/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_90/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_100_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value="Ворожко Александр")
    @Description("03 07 02.1.100 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_100", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127895135")

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

        if(!status.equals("Передача груза")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_02_1_90 test_03_07_02_1_90 = new Test_03_07_02_1_90();
            test_03_07_02_1_90.steps();
            CommonFunctions.wait(20);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        }
    }

    @Step("Проверка изменения статуса заявки")
    public void step01() {
        CommonFunctions.printStep();

        String status = null;
        for (int i = 1; i < 20; i++) {
            status = RESTFunctions.getOrderStatus(processID);
            if (status.equals("Оказание услуги")) {
                break;
            } else {
                CommonFunctions.wait(60);
            }
        }

        Assert.assertEquals(status, "Оказание услуги");

        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

}

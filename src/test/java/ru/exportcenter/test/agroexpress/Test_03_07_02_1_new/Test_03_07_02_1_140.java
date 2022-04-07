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
import ru.exportcenter.test.HooksTEST;
import ru.exportcenter.test.agroexpress.Test_03_07_02_1.Test_03_07_02_1_130;

import java.util.Properties;

public class Test_03_07_02_1_140 extends HooksTEST {

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_140/";
    private final String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_130/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_140_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value="Балашов Илья")
    @Description("03 07 02.1.140 (Р) Ответ от РЖДЛ с подтверждением о передаче закрывающих документов")
    @Link(name="Test_03_07_02_1_140", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127896179")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Выполнение предусловий")
    public void precondition() {
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

        if (!status.equals("")) {
            System.out.println("Перепрогон предыдущего теста");

            ru.exportcenter.test.agroexpress.Test_03_07_02_1.Test_03_07_02_1_130 test_03_07_02_1_130 = new Test_03_07_02_1_130();
            test_03_07_02_1_130.steps();
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
            if (status.equals("Услуга оказана")) {
                break;
            } else {
                CommonFunctions.wait(60);
            }
        }

        Assert.assertEquals(status, "Услуга оказана");

        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

}

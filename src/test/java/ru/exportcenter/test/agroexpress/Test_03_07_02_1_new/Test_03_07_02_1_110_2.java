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

public class Test_03_07_02_1_110_2 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_110_2/";
//    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_100/";
    public String WAY_TEST_FIRST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_10/";
    private String processID;

    @Owner(value="Балашов Илья")
    @Description("03 07 02.1.110_2 Получение скорректированной заявки с расчетом (интеграция)")
    @Link(name="Test_03_07_02_1_110_2", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123882163")

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
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_FIRST + "processID.txt");
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

//        if(!status.equals("Оказание услуги")) {
//            System.out.println("Перепрогон предыдущего теста");
//
//            Test_03_07_02_1_100 test_03_07_02_1_100 = new Test_03_07_02_1_100();
//            test_03_07_02_1_100.steps();
//            CommonFunctions.wait(20);
//            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
//        }
    }

    @Step("Проверка изменения статуса заявки")
    public void step01() {
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
    }

}

package ru.exportcenter.test.agroexpress.Test_03_07_02_1;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02_1_50 extends HooksTEST {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_50/";
    public String WAY_TEST_PREVIOUS = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_40/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_50_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String docNum;

    @Owner(value = "Петрищев Руслан")
    @Description("03 07 02.1.50")
    @Link(name = "Test_03_07_02_1_50", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123878515")

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
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);

        if (!status.equals("Проводится проверка")) {
            System.out.println("Перепрогон предыдущего теста");

            Test_03_07_02_1_40 test_03_07_02_1_40 = new Test_03_07_02_1_40();
            test_03_07_02_1_40.steps();
            CommonFunctions.wait(20);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PREVIOUS + "processID.txt");
        }
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        new GUIFunctions().authorization(PROPERTIES.getProperty("Логин"), PROPERTIES.getProperty("Пароль"), PROPERTIES.getProperty("Код подтвержения")).waitForLoading().closeAllPopupWindows();
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();
        docNum = JupyterLabIntegration.getFileContent("/files_for_tests/test/agroexpress/Test_03_07_02_1/Test_03_07_02_1_10/docNum.txt");

        System.out.println(docNum);

        new GUIFunctions().clickByLocator("//*[@class='Button_text__3lYJC']");

        new GUIFunctions().clickByLocator("//*[text()='№" + docNum + "']").waitForElementDisplayed("//div[text()='Статус']/following-sibling::div[text()='Подтверждение выбранных услуг']");

        new GUIFunctions().closeAllPopupWindows().clickButton("Продолжить").waitForElementDisplayed("//*[text()='Расчет стоимости услуги']");
    }

    @Step("Ознакомление с изменениями и подтверждение выбранных услуг")
    public void step03() {

        //Нажать «Далее»
        new GUIFunctions().clickButton("Далее").waitForElementDisplayed("//*[text()='Расчет стоимости услуги']");

        //Нажать «Далее»
        new GUIFunctions().clickButton("Далее").waitForElementDisplayed("//*[text()='Подтверждение отправлено в АО \"РЖД Логистика\"']");

        //Нажать на ссылку "Логистика. Доставка продукции "Агроэкспрессом". Заявка №(Номер заявки)"
        new GUIFunctions().clickByLocator("//button[text()='Логистика. Доставка продукции \"Агроэкспрессом\". Заявка №" + docNum + "']").waitForElementDisplayed("//*[text()='Формирование счёта']");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if ($x(expectedXpath).isDisplayed()) {
                new GUIFunctions().closeAllPopupWindows();
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }
}

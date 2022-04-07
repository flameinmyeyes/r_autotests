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

import static com.codeborne.selenide.Selenide.$x;

public class Test_03_07_02_1_130 extends HooksTEST {

    /*
     * http://selenoidshare.d.exportcenter.ru/lab/tree/work/files_for_tests/test/agroexpress/Test_03_07_02_1/Test_03_07_02_1_130
     * https://gitlab.exportcenter.ru/sub-service/autotests/rec_autotests/-/blob/master/src/test/java/ru/exportcenter/test/agroexpress/Test_03_07_02_1/Test_03_07_02_1_130.java
     */

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_130/";
    private final String WAY_TEST_PRECONDITION = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1/Test_03_07_02_1_10/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_03_07_02_1_130.xml");
    private final String DOC_STATUS = "Выбор вида предоставления закрывающих документов";
    private String processID;
    private String docNum;


    @Owner(value = "Диана Максимова")
    @Description("03 07 02.1.130 Выбор вида предоставления Закрывающих документов")
    @Link(name = "Test_03_07_02_1_130", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127893558")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();
        step03();
        step04();
    }

    @Step("Выполнение предусловий")
    public void precondition() {
        processID = JupyterLabIntegration.getFileContent(WAY_TEST_PRECONDITION + "processID.txt");
        docNum = JupyterLabIntegration.getFileContent(WAY_TEST_PRECONDITION + "docNum.txt");

        if (!RESTFunctions.getOrderStatus(processID).equals(DOC_STATUS)) {
            System.out.println("Перепрогон предыдущего теста");
            new Test_03_07_02_1_120().steps();
            CommonFunctions.wait(20);
            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PRECONDITION + "processID.txt");
        }
    }

    @Step("Авторизация в ЕЛК")
    public void step01() {
        new GUIFunctions().authorization("test-otr@yandex.ru", "Password1!", "1234")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация в ЕЛК")
    public void step02() {
        // Открыть нужную заявку
        new GUIFunctions().inContainer("Мои услуги")
                .clickByLocator("//*[contains(text(),'Показать все (')]")
                .clickByLocator("//*[contains(text(),'" + docNum + "')]");

        new GUIFunctions().inContainer("Логистика. Доставка продукции \"Агроэкспрессом\"")
                .inField("Статус").assertValue(DOC_STATUS)
                .closeAllPopupWindows()
                .clickButton("Продолжить");
    }

    @Step("Блок, содержащий управляющие элементы")
    public void step03() {
        // Проверка сохранения, печати и просмотра документа (проверка кнопок)
        String downloadXPath = "//button[contains(@class,'downloadButton')][not(@disabled)][*[@viewBox='0 0 16 16']]";
        String printXPath = "//button[contains(@class,'printButton')][not(@disabled)][*[@viewBox='0 0 22 22']]";
        String viewXPath = "//button[contains(@class,'printButton')][not(@disabled)][*[@viewBox='0 0 16 10']]";

        new GUIFunctions().waitForElementDisplayed(downloadXPath)
                .waitForElementDisplayed(printXPath)
                .waitForElementDisplayed(viewXPath);
    }

    @Step("Блок Вид предоставления УПД")
    public void step04() {
        new GUIFunctions().inContainer("Вид предоставления УПД")
                .inField("Электронный вид").setRadiobuttonByDescription("передается в электронном виде")
                .inField("Оператор системы ЭДО").selectValue("ООО ЭТП ГПБ").assertValue().assertNoControl()
                .inField("Идентификатор участника ЭДО").inputValue("2BM-170B038D96B34EODA1b2BB86B09585BA").assertValue().assertNoControl();

        new GUIFunctions().clickButton("Далее");
    }


    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");
    }
}

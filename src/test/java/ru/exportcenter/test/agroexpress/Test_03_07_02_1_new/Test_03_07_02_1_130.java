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
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02_1_130 extends HooksTEST {

    /*
     * http://selenoidshare.d.exportcenter.ru/lab/tree/work/files_for_tests/test/agroexpress/Test_03_07_02_1/Test_03_07_02_1_130
     * https://gitlab.exportcenter.ru/sub-service/autotests/rec_autotests/-/blob/master/src/test/java/ru/exportcenter/test/agroexpress/Test_03_07_02_1/Test_03_07_02_1_130.java
     */

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_130/";
    private final String WAY_TEST_PRECONDITION = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_120/";
    private final String WAY_TEST_DOC_NUM = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_10/";
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
        docNum = JupyterLabIntegration.getFileContent(WAY_TEST_DOC_NUM + "docNum.txt");

//        if (!RESTFunctions.getOrderStatus(processID).equals(DOC_STATUS)) {
//            System.out.println("Перепрогон предыдущего теста");
//            new Test_03_07_02_1_120().steps();
//            CommonFunctions.wait(20);
//            processID = JupyterLabIntegration.getFileContent(WAY_TEST_PRECONDITION + "processID.txt");
//        }
    }

    @Step("Авторизация в ЕЛК")
    public void step01() {
        CommonFunctions.printStep();
//        new GUIFunctions().authorization(P.getProperty("Логин"), P.getProperty("Пароль"), P.getProperty("Код подтвержения"))
        new GUIFunctions().authorization(P.getProperty("Логин"), P.getProperty("Пароль"))
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");

        open("https://lk.t.exportcenter.ru/ru/services/drafts/info/" + processID);
        new GUIFunctions()
                .waitForLoading()
                .closeAllPopupWindows();
    }

    @Step("Навигация в ЕЛК")
    public void step02() {
        CommonFunctions.printStep();

        refreshTab("//*[contains(text(), 'Продолжить')]", 20);

        new GUIFunctions()
                .closeAllPopupWindows()
                .clickButton("Продолжить");
        // Открыть нужную заявку
//        new GUIFunctions().inContainer("Мои услуги")
//                .clickByLocator("//*[contains(text(),'Показать все (')]")
//                .clickByLocator("//*[contains(text(),'" + docNum + "')]");
//
//        new GUIFunctions().inContainer("Логистика. Доставка продукции \"Агроэкспрессом\"")
//                .inField("Статус").assertValue(DOC_STATUS)
//                .closeAllPopupWindows()
//                .clickButton("Продолжить");
    }

    @Step("Блок, содержащий управляющие элементы")
    public void step03() {
        CommonFunctions.printStep();
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
        CommonFunctions.printStep();
        new GUIFunctions().inContainer("Вид предоставления УПД")
                .inField("Электронный вид").setRadiobuttonByDescription("передается в электронном виде")
                .inField("Оператор системы ЭДО").selectValue("ООО ЭТП ГПБ").assertValue().assertNoControl()
                .inField("Идентификатор участника ЭДО").inputValue("2BM-170B038D96B34EODA1b2BB86B09585BA").assertValue().assertNoControl();

        for (int i = 0; i<5; i++) {
            if($x("//*[text() = 'Далее']").isDisplayed()) {
                new GUIFunctions().clickButton("Далее");
                CommonFunctions.wait(5);
            } else {
                break;
            }
        }

        System.out.println(RESTFunctions.getOrderStatus(processID));

        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");
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

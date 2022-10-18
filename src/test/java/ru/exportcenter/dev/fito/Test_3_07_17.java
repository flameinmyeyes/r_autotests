package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import static com.codeborne.selenide.Selenide.*;

public class Test_3_07_17 extends Hooks {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/Test_3_07_17/";
//    public String WAY_TO_PROPERTIES = Ways.DEV.getWay() + "/fito/Test_3_07_17/" + "Test_3_07_17_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    private String processID;

    private String docNum;

    @Owner(value = "Балашов Илья")
    @Description("3.07.17 Формирование отчетности по сервису")
    @Link(name = "Test_3_07_17", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183203153")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие: выполнить шаги 1-2 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
        test_3_07_01.step01();
        test_3_07_01.step02();
    }

    @Step("Шаг 3. Формирование отчета")
    public void step03() {
        CommonFunctions.printStep();

        //если страница с номером заявки проскакивает
//        clickRequestLinkIfRequestNumberPageIsSkipped("Запрос заключения о карантинном фитосанитарном состоянии");

        //сохранить processID в файл
        processID = CommonFunctions.getProcessIDFromURL();
        System.out.println("processID: " + processID);
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");

        //сохранить номер документа в файл
        docNum = $x("//div[text()='Номер заявки']/parent::div/div[contains(@class, 'description')]").getText();
        System.out.println("docNum: " + docNum);
        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");

        new GUIFunctions()
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии подкарантинной продукции")
                    //ждем, пока загрузится кнопка и статусы
                    .waitForElementDisplayed("//a[@href][text()='Сформировать отчет']", 120)
                    //Нажать "Сформировать отчет"
                    .clickByLocator("//a[@href][text()='Сформировать отчет']")

                .inContainer("Формирование отчета по получению услуги «Запрос заключения о карантинном фитосанитарном состоянии»")
                    .waitForElementDisplayed("//div[text()='Шаг 1 из 2']")
                .inContainer("Выбор отчета")
                    .inField("Вид").clickByLocator("//ancestor::div//span[contains(text(),'Общий')][last()]")
                    .inField("Вид").clickByLocator("//ancestor::div//span[contains(text(),'Страновой')][last()]")
                    .inField("Вид").clickByLocator("//ancestor::div//span[contains(text(),'Аналитический')][last()]")
                .inContainer("Общий отчет")
                    .inField("Тип отчета").clickByLocator("//ancestor::div//span[contains(text(),'Полный')][last()]");

        //нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Формирование отчета по получению услуги «Запрос заключения о карантинном фитосанитарном состоянии»")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 2 из 2']");
    }

    /**
     * Костыль: обновлять страницу, пока не появится кнопка "Продолжить"
     */
    private static void refreshTabUntilElementIsDisplayed(String expectedXpath, int times) {
        if (!$x(expectedXpath).isDisplayed()) {
            System.out.println("Refreshing...");
        }
        for (int i = 0; i < times; i++) {
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            new GUIFunctions().waitForLoading();
            CommonFunctions.wait(1);
        }
    }

    /**
     * Костыль: если страница с номером заявки проскакивается - кликаем по ссылке с необходимым типом заявки
     */
    private static void clickRequestLinkIfRequestNumberPageIsSkipped(String requestName) {
        if ($x("//button[contains(text(),'" + requestName + "')]").isDisplayed()) {
            new GUIFunctions().clickByLocator("//button[contains(text(),'" + requestName + "')]");
        }
    }

}

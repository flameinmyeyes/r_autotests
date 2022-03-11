package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.refresh;

public class FUN_02_BP_006_NSI_024_PZ_2_8_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_8_1\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_024_PZ_KP_2_2 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_KP_2_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.8.1. Макет краткой формы справочника")
    @Link(name="TSE-T3221", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3221")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_FUN_02_BP_006_NSI_024_PZ_KP_2_2 = setWay(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_KP_2_2);
        preconditions();
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    private void preconditions() {
        CommonFunctions.printStep();
        String docGuid = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_KP_2_2 + "docGuid.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

        if(!docStatus.equals("Новая")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_024_PZ_2_2_1 fun_02_bp_006_nsi_024_pz_2_2_1 = new FUN_02_BP_006_NSI_024_PZ_2_2_1();
            fun_02_bp_006_nsi_024_pz_2_2_1.steps();
            refresh();
            CommonFunctions.wait(3);
        } else {
            System.out.println((char) 27 + "[42m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[42m" + "Выполнение предварительных условий не требуется" + (char) 27 + "[0m");
        }

    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Выбрать запись и проверить заполнение")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1);

        String quickViewPanelLocator = "//div[@class='doc-quick-view-panel z-south']";
        new MainPage().clickWebElement(quickViewPanelLocator + "//a[@class='expand z-toolbarbutton z-toolbarbutton-checked']");

        //Поля заполнено в соответствии с правилом отображения реквизитов на краткой форме справочника
        //Наименование реквизита        //Отображаемое на краткой форме наименование        //Примечание
        //Получатели уведомлений        //Получатели уведомлений        //Группирующий атрибут-заголовок
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + "//span[text()='Получатели уведомлений']"), 120);
        //Наименование документа        //Наименование документа        //–
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + "//span[text()='Наименование документа']"), 120);
        //Код документа        //Код документа        //–
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + "//span[text()='Код документа']"), 120);
        //Адресаты        //-        //Таблица
        String tableLocator = "//div[@class='overflow-auto z-listbox'][@title='Почта получателей']";
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + tableLocator), 120);
        //№ п/п        //№ п/п        //–
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + tableLocator + "//div[text()='№ п/п']"), 120);
        //ФИО получателя        //ФИО получателя        //–
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + tableLocator + "//div[text()='ФИО получателя']"), 120);
        //Логин пользователя        //Логин пользователя        //–
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + tableLocator + "//div[text()='Логин пользователя']"), 120);
        //e-mail        //e-mail        //–
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + tableLocator + "//div[text()='e-mail']"), 120);
        //В копию        //В копию        //–
        CommonFunctions.waitForElementDisplayed(By.xpath(quickViewPanelLocator + tableLocator + "//div[text()='В копию']"), 120);
    }

}

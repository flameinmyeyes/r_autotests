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

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_021_PZ_1_7 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_7\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_1 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_1\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_2\\";

    //после FUN_02_BP_006_NSI_021_PZ_1_3_1, FUN_02_BP_006_NSI_021_PZ_1_3_2

    @Owner(value="Балашов Илья")
    @Description("NSI_021. ПЗ п. 1.7. Требования к статусной модели справочника (отправление в архив)")
    @Link(name="TSE-T4787", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4787")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        preconditions();
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    private void preconditions() throws Exception {
        CommonFunctions.printStep();

        //FUN_02_BP_006_NSI_021_PZ_1_3_1
        String docGuid_1 = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_1 + "docGuid.txt");
        String request_1 = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid_1 + "'";
        String docStatus_1 = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request_1);

        if(!docStatus_1.equals("Актуальная")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus_1 + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_021_PZ_1_3_1 fun_02_bp_006_nsi_021_pz_1_3_1 = new FUN_02_BP_006_NSI_021_PZ_1_3_1();
            fun_02_bp_006_nsi_021_pz_1_3_1.steps();
            refresh();
            CommonFunctions.wait(3);
        } else {
            System.out.println((char) 27 + "[42m" + "Статус: " + docStatus_1 + (char) 27 + "[0m");
            System.out.println((char) 27 + "[42m" + "Выполнение предварительных условий не требуется" + (char) 27 + "[0m");
        }

        //FUN_02_BP_006_NSI_021_PZ_1_3_2
        String docGuid_2 = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "docGuid.txt");
        String request_2 = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid_2 + "'";
        String docStatus_2 = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request_2);

        if(!docStatus_2.equals("Актуальная")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus_2 + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_021_PZ_1_3_2 fun_02_bp_006_nsi_021_pz_1_3_2 = new FUN_02_BP_006_NSI_021_PZ_1_3_2();
            fun_02_bp_006_nsi_021_pz_1_3_2.steps();
            refresh();
            CommonFunctions.wait(3);
        } else {
            System.out.println((char) 27 + "[42m" + "Статус: " + docStatus_2 + (char) 27 + "[0m");
            System.out.println((char) 27 + "[42m" + "Выполнение предварительных условий не требуется" + (char) 27 + "[0m");
        }

    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Группы причин отказов».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Группы причин отказов");
    }

    @Step("Выделить на СФ запись со статусом «Актуальная»")
    private void step03() {
        CommonFunctions.printStep();
        //выбрать запись актуализированный в прогоне
        //https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3189
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Детализированный код", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_1 + "detailCode.txt"))
                .filterColumnInList("Код", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_1 + "code.txt"))
                .filterColumnInList("Причина отказа", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_1 + "reasonRefusal.txt"))
                .filterColumnInList("Дата начала действия записи", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_1 + "startDate.txt"))
                .clickRowInList(FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_1 + "detailCode.txt"));
    }

    @Step("Нажать кнопку \"Отправить  в архив\"")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Отправить в архив")
                .clickButtonInBlock("Подтвердите действие", "Подтвердить")
                .clickButtonInBlock("Подтвердите действие", "ОК");

        //Колонка "Статус" заполнено значением Архивная
        //Колонка "Дата окончания действия записи" заполнено значением текущей датой
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Архивная']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='" + CommonFunctions.dateToday("dd.MM.yyyy") + "']"), 60);
//
//        new MainPage()
//                .assertValueInColumn("Статус","Архивная")
//                .assertValueInColumn("Дата окончания действия записи", CommonFunctions.dateToday("dd.MM.yyyy"));
    }

    @Step("Выделить на СФ запись со статусом «Актуальная»")
    private void step05() {
        CommonFunctions.printStep();
        //выбрать запись актуализированный в прогоне
        //https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4789
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Код причины отказа ", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "reasonFailureCode.txt") + "%")
                .filterColumnInList("Код", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "code.txt"))
                .filterColumnInList("Причина отказа", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "reasonRefusal.txt"))
                .filterColumnInList("Дата начала действия записи", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "startDate.txt"))
                .clickRowsInList(1);
    }

    @Step("Нажать кнопку \"Отправить  в архив\"")
    private void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Отправить в архив")
                .clickButtonInBlock("Подтвердите действие", "Подтвердить")
                .clickButtonInBlock("Подтвердите действие", "ОК");

        //Колонка "Статус" заполнено значением Архивная
        //Колонка "Дата окончания действия записи" заполнено значением текущей датой
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Архивная']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='" + CommonFunctions.dateToday("dd.MM.yyyy") + "']"), 60);

//        new MainPage()
//                .assertValueInColumn("Статус","Архивная")
//                .assertValueInColumn("Дата окончания действия записи",CommonFunctions.dateToday("dd.MM.yyyy"));
    }

}

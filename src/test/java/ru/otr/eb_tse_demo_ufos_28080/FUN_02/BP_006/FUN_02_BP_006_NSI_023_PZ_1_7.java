package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;


public class FUN_02_BP_006_NSI_023_PZ_1_7 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_7\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_1 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_023. ПЗ п. 1.7. Требования к СФ. Требования к действиям из списковой форм")
    @Link(name="TSE-T3205", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3205")

    //после FUN_02_BP_006_NSI_023_PZ_1_1

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        preconditions();
        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    private void preconditions() throws Exception {
        CommonFunctions.printStep();

        String docGuid = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_1 + "docGuid.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

        if(!docStatus.equals("Новая")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_023_PZ_1_1 fun_02_bp_006_nsi_023_pz_1_1= new FUN_02_BP_006_NSI_023_PZ_1_1();
            fun_02_bp_006_nsi_023_pz_1_1.steps();
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
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов");
    }

    @Step("Выделить на СФ запись со статусом «Новая»")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Виды причин отказа", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_1 + "reject_reason.txt"))
                .clickRowInList(FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_1 + "reject_reason.txt"));
    }

    @Step("Нажать кнопку \"Удаление\"")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Удалить документ (Alt+R)");
    }

    @Step("Нажать \"Да\" и нажать кнопку \"Обновить\"")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage().clickButtonInBlock("Удалить строку справочника", "Да");

        new MainPage().clickButtonTitle("Обновить список документов (Alt+F5)");

        //документ удалился и нет в справочнике
        //Обновился СФ
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[text()='Нет элементов, удовлетворяющих Вашему запросу']"), 60);
    }

}

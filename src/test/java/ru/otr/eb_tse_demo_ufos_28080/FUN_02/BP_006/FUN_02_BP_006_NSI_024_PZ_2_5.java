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

public class FUN_02_BP_006_NSI_024_PZ_2_5 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_5\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_2 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_2_2\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_1 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_2_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.5. Требования к статусной модели справочника")
    @Link(name="TSE-T3214", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3214")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_2 = setWay(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_2);
        WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_1 = setWay(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_1);
        preconditions();
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
        step09();
        step10();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    private void preconditions() {
        CommonFunctions.printStep();

        //FUN_02_BP_006_NSI_024_PZ_2_2_2
        String docGuid = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_2 + "docGuid.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

        if(!docStatus.equals("Новая")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_024_PZ_2_2_2 fun_02_bp_006_nsi_024_pz_2_2_2 = new FUN_02_BP_006_NSI_024_PZ_2_2_2();
            fun_02_bp_006_nsi_024_pz_2_2_2.steps();
            refresh();
            CommonFunctions.wait(3);
        } else {
            System.out.println((char) 27 + "[42m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[42m" + "Выполнение предварительных условий не требуется" + (char) 27 + "[0m");
        }

        //FUN_02_BP_006_NSI_024_PZ_2_2_1
        docGuid = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_1 + "docGuid.txt");
        request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

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

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Выбрать запись, где \"Статус \" заполнена значением «Новая» при прогоне  TSE-T3212")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Дата начала действия записи", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_2 + "startDate.txt"))
                .filterColumnInList("Статус", "Новая")
                .clickRowsInList(1);
    }

    @Step("Нажать на кнопку \"Актуализировать\"")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Актуализировать");

        //Колонка "Статус " заполнена значением «Актуальная».
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][9][@title='Актуальная']"), 60);
    }

    @Step("Нажать на кнопку \"Отправить в архив\"")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Отправить в архив");

        //Колонка "Статус " заполнена значением «Архивная ».
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][9][@title='Архивная']"), 60);
        //Колонка "Дата окончания действия записи " заполнена значением тек.даты
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][8][@title='" + CommonFunctions.dateToday("dd.MM.yyyy") +"']"), 60);
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step06() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step07() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Выбрать запись ,где \"Статус \" заполнена значением «Новая» при прогоне   TSE-T3211")
    public void step08() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Дата начала действия записи", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_024_PZ_2_2_1 + "startDate.txt"))
                .filterColumnInList("Статус", "Новая")
                .clickRowsInList(1);
    }

    @Step("Нажать на кнопку \"Актуализировать\"")
    public void step09() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Актуализировать");

        //Колонка "Статус " заполнена значением «Актуальная».
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][9][@title='Актуальная']"), 60);
    }

    @Step("Нажать на кнопку \"Отправить в архив\"")
    public void step10() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Отправить в архив");

        //Колонка "Статус " заполнена значением «Архивная ».
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][9][@title='Архивная']"), 60);
        //Колонка "Дата окончания действия записи " заполнена значением тек.даты
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][8][@title='" + CommonFunctions.dateToday("dd.MM.yyyy") +"']"), 60);
    }

}

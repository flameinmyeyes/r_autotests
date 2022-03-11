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

public class FUN_02_BP_006_NSI_021_PZ_1_3_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_3_2\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_021. ПЗ п. 1.3(2). Требования к статусной модели справочника (Актуализация записи справочника) (признак контроля 1)")
    @Link(name="TSE-T4789", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4789")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        preconditions();
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    private void preconditions() throws Exception {
        CommonFunctions.printStep();
        String docGuid = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "docGuid.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

        if(!docStatus.equals("Новая")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_021_PZ_1_2 fun_02_bp_006_nsi_021_pz_1_2 = new FUN_02_BP_006_NSI_021_PZ_1_2();
            fun_02_bp_006_nsi_021_pz_1_2.steps();
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

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Группы причин отказов».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Группы причин отказов");
    }

    @Step("на СФ выбрать запись на статусе \"Новая\"")
    private void step03() {
        //выбрать запись созданный в прогоне
        //https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4785
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Код причины отказа ", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "reasonFailureCode.txt"))
                .filterColumnInList("Код", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "code.txt"))
                .filterColumnInList("Причина отказа", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "reasonRefusal.txt"))
                .filterColumnInList("Дата начала действия записи", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "startDate.txt"))
                .clickRowInList(FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_021_PZ_1_2 + "reasonFailureCode.txt"));

        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Новая']"), 60);
    }

    @Step("Нажать кнопку \"Актуализировать\"\n" +
            "Проверить колонку \"Код причины отказа\"")
    public void step04() {
        CommonFunctions.printStep();
        //1.XXX.YY.ZZZZ, где:
        //XXX – тип документа (Пример, 1-СБО, 2-СДО и т.д.);
        //YY – Код (Пример, 01-контроль на соответствие НСИ, 02-контроль обязательности реквизита и т.д.);
        //ZZZZ – порядковый номер самого контроля (например, 0001; в нумерации будут применены и буквы латинского и русского алфавита)
        new MainPage()
                .clickButtonTitle("Актуализировать");

        //Колонка "Статус" заполнено значением "Актуальная"
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Актуальная']"), 60);
        //Колонка "Код причины отказа" заполнена значением = 1.D02.40.000N
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][contains(@title, '1.D02.40.000')]"), 60);
    }

}

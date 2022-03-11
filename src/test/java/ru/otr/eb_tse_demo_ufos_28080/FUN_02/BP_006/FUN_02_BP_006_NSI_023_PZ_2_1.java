package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_023_PZ_2_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_2_1\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_2\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_1 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_1\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Балашов Илья")
    @Description("NSI_023. ПЗ п. 2.1. Требования к печатной форме спр-ка")
    @Link(name="TSE-T3209", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3209")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        WAY_DOWNLOADS = setWay(WAY_DOWNLOADS);
        preconditions();
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    private void preconditions() throws Exception {
        CommonFunctions.printStep();

        String docGuid = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 + "docGuid.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

        if(!docStatus.equals("Актуальная")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_023_PZ_1_2 fun_02_bp_006_nsi_023_pz_1_2= new FUN_02_BP_006_NSI_023_PZ_1_2();
            fun_02_bp_006_nsi_023_pz_1_2.steps();
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

    @Step("Выделить на СФ запись со статусом «Актуальная»\n" +
            "Нажать кнопку \"Печать списка\" , выбрав шаблон Excel (Старый формат *.xls)")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Виды причин отказа", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 + "reject_reason.txt"))
                .clickRowInList(FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 + "reject_reason.txt"))
                .clickButtonTitle(" Печать списка");

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        if(!$x(window + "//span[text()='Excel (Старый формат *.xls)']/parent::td").getAttribute("style").contains("background")) {
            $x(window + "//span[text()='Excel (Старый формат *.xls)']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Сформирована печатная форма выбранных записей в Exсel файле
        //PrintScroller_21-12-2021 12-11.xls
        String containsName = "PrintScroller_" + printTime + ".xls";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        FileFunctions.waitForFileDownload(wayToSourceFile, 400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xls",2);
    }

    @Step("Проверить формирование наименования Exсel-файла")
    private void step04() {
        CommonFunctions.printStep();
        //Наименование, дата и время соответствуют значениям

        //PrintScroller_21-12-2021 12-11.xls
    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: тип документа")
    private void step05() {
        CommonFunctions.printStep();
        //В поле "Тип документа" указано значение "Причины отклонения документов"
        Assert.assertEquals(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 1, 1),
                "Тип документа: Типы контролей");

    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: дата формирования")
    private void step06() {
        CommonFunctions.printStep();
        //В поле "Дата формирования" указано значение даты формирования печатной формы по формату
        Assert.assertTrue(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 2, 1)
                        .startsWith("Дата формирования: " + CommonFunctions.dateToday("dd-MM-yyy")));

    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: реквизиты справочника")
    private void step07() {
        CommonFunctions.printStep();

        //Поля со значениями реквизитов справочника соответствуют значениям из справочника
        Assert.assertEquals(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 5, 1),
                "1");

        Assert.assertEquals(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 5, 2),
                "40");

        String rejectReason = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 + "reject_reason.txt");
        Assert.assertEquals(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 5, 3),
                rejectReason);

        String startDate = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_1 + "startDate.txt");
        Assert.assertEquals(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 5, 4),
                startDate);

        String endDate = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_1 + "endDate.txt");
        Assert.assertEquals(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 5, 5),
                endDate);

        Assert.assertEquals(FileFunctions.getXLSCellValue(WAY_TEST + "print.xls", "Scroller", 5, 6),
                "Актуальная");
    }


}

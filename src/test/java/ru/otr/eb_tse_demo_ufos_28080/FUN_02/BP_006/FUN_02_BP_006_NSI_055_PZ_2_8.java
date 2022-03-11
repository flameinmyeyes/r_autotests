package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
import functional.FileFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_055_PZ_2_8 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_055_PZ_2_8\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Балашов Илья")
    @Description("NSI_055. ПЗ п. 2.8. Требования к печатной форме спр-ка")
    @Link(name="TSE-T3248", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3248")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        WAY_DOWNLOADS = setWay(WAY_DOWNLOADS);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Реестр приостанавливаемых операций ».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Реестр приостанавливаемых операций");
    }

    @Step("Выбрать записи справочника  «Реестр приостанавливаемых операций»")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1,2);

        //Выбраны записи и показано что выделено n (Количество выделенных записей) записей
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (*.xlsx)  и нажать кнопку \"ОК\"")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle(" Печать списка");

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (*.xlsx)";
        if(!($x(windowLocator + "//span[text()='" + printFormat + "']/parent::td")).getAttribute("style").contains("background")) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xlsx
        //Правило заполнения полей
        //Тип документа:	Реестр приостанавливаемых операций
        //Дата формирования:	текущая дата
        //PrintScroller_18-01-2022 12-39.xlsx
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xlsx";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xlsx";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,300);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_scroller.xlsx");

        FileFunctions.isNotEmptyFile(WAY_TEST + "print_scroller.xlsx",1);

        FileFunctions.compareXLS(WAY_TEST + "print_scroller.xlsx", WAY_TEST + "print_sample.xlsx", 0, 1,3,4);
    }

    @Step("Выбрать записи справочника  «Реестр приостанавливаемых операций»")
    private void step05() {
        CommonFunctions.printStep();

        //Выбраны записи и показано что выделено n (Количество выделенных записей) записей
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    private void step06() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle(" Печать списка");

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!($x(windowLocator + "//span[text()='" + printFormat + "']/parent::td")).getAttribute("style").contains("background")) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xls
        //Правило заполнения полей
        //Тип документа:	Реестр приостанавливаемых операций
        //Дата формирования:	текущая дата
        //PrintScroller_18-01-2022 12-39.xls
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xls";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xls";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,300);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_scroller.xls");

        FileFunctions.isNotEmptyFile(WAY_TEST + "print_scroller.xls",1);

        FileFunctions.compareXLS(WAY_TEST + "print_scroller.xls", WAY_TEST + "print_sample.xls", 0, 1,3,4);
    }

    @Step("Выбрать записи справочника  «Реестр приостанавливаемых операций»")
    private void step07() {
        CommonFunctions.printStep();

        //Выбраны записи и показано что выделено n (Количество выделенных записей) записей
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    private void step08() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle(" Печать списка");

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "OpenOffice (*.ods)";
        if(!($x(windowLocator + "//span[text()='" + printFormat + "']/parent::td")).getAttribute("style").contains("background")) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.ods
        //Правило заполнения полей
        //Тип документа:	Реестр приостанавливаемых операций
        //Дата формирования:	текущая дата
        //PrintScroller_18-01-2022 12-39.ods
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".ods";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".ods";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,300);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_scroller.ods");

        FileFunctions.isNotEmptyFile(WAY_TEST + "print_scroller.ods",1);

        FileFunctions.compareODS(WAY_TEST + "print_scroller.ods", WAY_TEST + "print_sample.ods", 0, 1,3,4);
    }

}

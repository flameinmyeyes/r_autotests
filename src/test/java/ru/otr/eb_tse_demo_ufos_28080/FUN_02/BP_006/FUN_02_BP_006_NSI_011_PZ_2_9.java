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

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_011_PZ_2_9 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_011_PZ_2_9\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Власовец Кирилл")
    @Description("NSI_011. ПЗ п. 2.9.\tПечать списка справочника")
    @Link(name="TSE-T4968", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4968")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vlasovets_kirill", "FUN_02", "BP_006"})
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
    public void screenShot(){
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Шаблон листа согласования».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Шаблон листа согласования");
    }

    @Step("Выбрать записи справочника  «Шаблон листа согласования »")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1);

        //Выбраны записи и показано что выделено n (Количество выделенных записей) записей
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (*.xlsx)  и нажать кнопку \"ОК\"")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка");

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        if(!$x(window + "//span[text()='Excel (*.xlsx)']/parent::td").getAttribute("style").contains("background")) {
            $x(window + "//span[text()='Excel (*.xlsx)']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xlsx
        //Заполнено поля в соответствии с СФ
        //Тип документа:	Шаблон листа согласования
        //Дата формирования:	тек.дата
        //№ п/п	Номер шаблона	Обрабатываемый документ. Наименование	Дата начала действия записи	Дата окончания действия записи	Шаблон по умолчанию	Статус	Код СВР/НУБП	Наименование
        //PrintScroller_21-01-2022 12-36.xlsx
        String containsName = "PrintScroller_" + printTime + ".xlsx";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        FileFunctions.waitForFileDownload(wayToSourceFile, 400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xlsx");

        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xlsx",2);

        FileFunctions.compareXLS(WAY_TEST + "print.xlsx", WAY_TEST + "print_sample.xlsx", 0, 1,3,4,5);
    }

    @Step("Выбрать записи справочника  «Шаблон листа согласования »")
    private void step05() {
        CommonFunctions.printStep();
        //Выбраны записи и показано что выделено n (Количество выделенных записей) записей

    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    private void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка");

        $(By.xpath("//td[@class='z-cell']//span[text()='Excel (*.xlsx)']")).click();
        new MainPage().waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        if(!$x(window + "//span[text()='Excel (Старый формат *.xls)']/parent::td").getAttribute("style").contains("background")) {
            $x(window + "//span[text()='Excel (Старый формат *.xls)']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xls
        //Заполнено поля в соответствии с СФ
        //Тип документа: Шаблон листа согласования
        //Дата формирования: тек.датой
        //№ п/п	Номер шаблона	Обрабатываемый документ. Наименование	Дата начала действия записи	Дата окончания действия записи	Шаблон по умолчанию	Статус	Код СВР/НУБП	Наименование
        String containsName = "PrintScroller_" + printTime + ".xls";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        FileFunctions.waitForFileDownload(wayToSourceFile, 400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xls");

        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xls",2);

        FileFunctions.compareXLS(WAY_TEST + "print.xls", WAY_TEST + "print_sample.xls", 0, 1,3,4,5);
    }

    @Step("Выбрать записи справочника  «Шаблон листа согласования »")
    private void step07() {
        CommonFunctions.printStep();
        //Выбраны записи и показано что выделено n (Количество выделенных записей) записей
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    private void step08() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка");

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        if(!$x(window + "//span[text()='OpenOffice (*.ods)']/parent::td").getAttribute("style").contains("background")) {
            $x(window + "//span[text()='OpenOffice (*.ods)']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.ods
        //Заполнено поля в соответствии с СФ
        //Тип документа:	Шаблон листа согласования
        //Дата формирования:	тек.датой
        //№ п/п	Номер шаблона	Обрабатываемый документ. Наименование	Дата начала действия записи	Дата окончания действия записи	Шаблон по умолчанию	Статус	Код СВР/НУБП	Наименование
        String containsName = "PrintScroller_" + printTime + ".ods";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        FileFunctions.waitForFileDownload(wayToSourceFile, 400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.ods");

        FileFunctions.isNotEmptyFile(WAY_TEST + "print.ods",2);

        FileFunctions.compareODS(WAY_TEST + "print.ods", WAY_TEST + "print_sample.ods", 0, 1,3,4,5);
    }

}
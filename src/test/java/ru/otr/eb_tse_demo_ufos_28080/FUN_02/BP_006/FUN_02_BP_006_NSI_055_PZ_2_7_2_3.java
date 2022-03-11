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

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_055_PZ_2_7_2_3 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_055_PZ_2_7_2_3\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Балашов Илья")
    @Description("NSI_055. ПЗ п. 2.7.2(3).Требования к действиям из списковой формы (ЦС)")
    @Link(name="TSE-T5954", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T5954")
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
        step09();
        step10();
        step11();
        step12();
        step13();
        step14();
        step15();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
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

    @Step("Нажать кнопку \"Обновить \"")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Обновить список документов (Alt+F5)");

        //СФ обновилась
    }

    @Step("Выбрать несколько записей справочника и нажать на кнопку \"Печать списка\" и выбрать старый формат печати")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickRowsInList(1,2)
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!($x(windowLocator + "//span[text()='" + printFormat + "']/parent::td").getAttribute("style")).contains("background")) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new DocPage().waitForLoading(60);
        }
        new DocPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Сформировалась печатная форма документа
        //PrintScroller_22-02-2022 16-47.xls
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
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xls",3);
    }

    @Step("Выделить документ на СФ.\n" +
            "Нажать кнопку «Просмотреть» на панели инструментов.")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");

        //Выделен документ на СФ.
        //Открыта ВФ формуляра на просмотр.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]"), 120);
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step06() {
        CommonFunctions.printStep();
        new LoginPage().authorization("da9df7b3-cc44-439a-a347-45e2a0214785");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Реестр приостанавливаемых операций ».")
    private void step07() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Реестр приостанавливаемых операций");
    }

    @Step("Нажать кнопку \"Обновить \"")
    private void step08() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Обновить список документов (Alt+F5)");

        //СФ обновилась
    }

    @Step("Выбрать несколько записей справочника и нажать на кнопку \"Печать списка\" и выбрать старый формат печати")
    private void step09() {
        CommonFunctions.printStep();
        new MainPage().clickRowsInList(1,2);

        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!($x(windowLocator + "//span[text()='" + printFormat + "']/parent::td").getAttribute("style")).contains("background")) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new DocPage().waitForLoading(60);
        }
        new DocPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Сформировалась печатная форма документа
        //PrintScroller_22-02-2022 16-47.xls
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
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_2.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_2.xls",3);
    }

    @Step("Выделить документ на СФ.\n" +
            "Нажать кнопку «Просмотреть» на панели инструментов.")
    private void step10() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");

        //Выделен документ на СФ.
        //Открыта ВФ формуляра на просмотр.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]"), 120);
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step11() {
        CommonFunctions.printStep();
        new LoginPage().authorization("73faa96c-44d0-411f-9d16-85115f03f958");
    }

    @Step("Проверить что справочник «Реестр приостанавливаемых операций»  не доступен")
    private void step12() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Реестр приостанавливаемых операций");
    }

    @Step("Нажать кнопку \"Обновить \"")
    private void step13() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Обновить список документов (Alt+F5)");

        //СФ обновилась
    }

    @Step("Выбрать несколько записей справочника и нажать на кнопку \"Печать списка\" и выбрать старый формат печати")
    private void step14() {
        CommonFunctions.printStep();
        new MainPage().clickRowsInList(1,2);

        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!($x(windowLocator + "//span[text()='" + printFormat + "']/parent::td").getAttribute("style")).contains("background")) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new DocPage().waitForLoading(60);
        }
        new DocPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Сформировалась печатная форма документа
        //PrintScroller_22-02-2022 16-47.xls
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
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_3.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_3.xls",3);
    }

    @Step("Выделить документ на СФ.\n" +
            "Нажать кнопку «Просмотреть» на панели инструментов.")
    private void step15() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");

        //Выделен документ на СФ.
        //Открыта ВФ формуляра на просмотр.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]"), 120);
    }

}

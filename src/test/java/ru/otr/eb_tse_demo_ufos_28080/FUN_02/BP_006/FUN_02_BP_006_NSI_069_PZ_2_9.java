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
import static ru.otr.eb_tse_demo_ufos_28080.FUN_01.BP_001.ColumnSorting.column;

public class FUN_02_BP_006_NSI_069_PZ_2_9 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_069_PZ_2_9\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Балашов Илья")
    @Description("NSI_069. ПЗ п. 2.9 Проверка печати списка \"Перечень КБК, допущенных к проведению операций по кассовым выплатам\"")
    @Link(name="TSE-T4956", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4956")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        WAY_DOWNLOADS = setWay(WAY_DOWNLOADS);
        step01();
        step02();
        step03();
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

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Перечень КБК, допущенных к проведению операций по кассовым выплатам».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Перечень КБК, допущенных к проведению операций по кассовым выплатам в период приостановления операций на лицевом счете");
    }

    @Step("Выбрать несколько записей .\n" +
            "Нажать на кнопку \"Печать списка\" и выбрать шаблон Excel (Старый формат *.xls)  и печать список")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1,2)
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!$x(windowLocator + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Сформирован документ для печати списка актуальных записей
        //PrintScroller_28-10-2021 12-26.xls
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

        FileFunctions.waitForFileDownload(wayToSourceFile,180);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xls",2);
    }

}

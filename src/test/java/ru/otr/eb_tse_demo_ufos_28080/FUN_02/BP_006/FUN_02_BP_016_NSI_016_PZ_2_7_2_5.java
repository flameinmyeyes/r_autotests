package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.Ways;
import functional.CommonFunctions;
import functional.FileFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_016_NSI_016_PZ_2_7_2_5 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_5\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Балашов Илья")
    @Description("NSI_016. ПЗ п. 2.7.2. ТК №5 Проверка печати списка")
    @Link(name="TSE-T2285", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2285")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_016"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        WAY_DOWNLOADS = setWay(WAY_DOWNLOADS);
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot( WAY_TEST + "screen.png");
    }

    @Step("Зайти на стенд. Авторизироваться в ЛК Администратора локальных НСИ ПУР КС")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть в навигации папку \"Справочники\"")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники");
    }

    @Step("Перейти на вкладку \"Виды средств\"")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Виды средств");
    }

    @Step("Выделить несколько записей и Нажать на кнопку \"Печать списка\"")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Код", "АТ%")
                .filterColumnInList("Наименование", "АТ")
                .clickRowsInList(1, 2)
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!$x(windowLocator + "//tr[contains(@style, 'background')]//span[text()='" + printFormat +"']").isDisplayed()) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //PrintScroller_28-10-2021 11-38.xls
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
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "printScroller.xls");

        //Сформирован документ для печати списка выделеных записей
        Assert.assertTrue(new File(WAY_TEST + "printScroller.xls").exists());
    }

}

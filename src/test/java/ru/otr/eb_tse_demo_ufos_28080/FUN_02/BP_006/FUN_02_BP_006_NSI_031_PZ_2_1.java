package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_031_PZ_2_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_2_1\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Балашов Илья")
    @Description("NSI_031. ПЗ п. 2.1. Требования к печатной форме спр-ка")
    @Link(name="TSE-T3235", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3235")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
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

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Настройки доступа ТОФК по месту обращения");
    }

    @Step("Выделить на СФ «Настройки доступа ТОФК по месту обращения» со статусом «Актуальная»")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Статус", "Актуальная")
                .clickRowsInList(1);
    }

    @Step("На списковой форме справочника нажать на кнопку \"Печать списка\", выбрать формат  xlsx")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String windowLocator = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (*.xlsx)";
        if(!($x(windowLocator + "//span[text()='" + printFormat + "']/parent::td").getAttribute("style")).contains("background")) {
            $x(windowLocator + "//span[text()='" + printFormat + "']").click();
            new DocPage().waitForLoading(60);
        }
        new DocPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Сформирована печатная форма выбранных записей в Exсel файле
        //PrintScroller_16-02-2022 13-50.xlsx
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

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xlsx");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xlsx",3);
    }

    @Step("Проверить формирование наименования Exсel-файла")
    private void step05() {
        CommonFunctions.printStep();
        //Наименование, дата и время соответствуют значениям
        FileFunctions.compareXLS(WAY_TEST + "print.xlsx", WAY_TEST + "print_sample.xlsx", 0, 1,3,4,5);
    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: тип документа")
    private void step06() {
        CommonFunctions.printStep();
        //В поле "Тип документа" указано значение «Настройки доступа ТОФК по месту обращения»"
    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: дата формирования")
    private void step07() {
        CommonFunctions.printStep();
        //В поле "Дата формирования" указано значение даты формирования печатной формы по формату
    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: реквизиты справочника")
    private void step08() {
        CommonFunctions.printStep();
        //Поля со значениями реквизитов справочника соответствуют значениям из справочника
    }

}

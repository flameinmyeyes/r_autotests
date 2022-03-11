package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static ru.otr.eb_tse_demo_ufos_28080.FUN_01.BP_001.ColumnSorting.column;

public class FUN_02_BP_006_NSI_055_PZ_2_7_4 extends HooksTSE_DEMO_28080 {
    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_055_PZ_2_7_4\\";

    @Owner(value = "Максимова Диана")
    @Description("NSI_055. ПЗ п. 2.7.4. Требования к СФ. Требования к сортировке спр-ка")
    @Link(name = "TSE-T3246", url = "https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3246")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "maksimova_diana", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
        step09();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); // Рябова Анна Викторовна
    }

    @Step("Переход по дереву навигации к справочнику «Реестр приостанавливаемых операций»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().resetUserSettings().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Реестр приостанавливаемых операций");
    }

    @Step("В списковой форме нажать на реквизит «Документ основание ».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Документ основание ».")
    private void step03() {
        CommonFunctions.printStep();
        column("Документ основание ").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Номер документа основания».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Номер документа основания».")
    private void step04() {
        CommonFunctions.printStep();
        column("Номер документа основания").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Номер лицевого счета».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Номер лицевого счета».")
    private void step05() {
        CommonFunctions.printStep();
        column("Номер лицевого счета").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «КБК ».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «КБК ».")
    private void step06() {
        CommonFunctions.printStep();
        column("КБК ").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Дата с».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата с».")
    private void step07() {
        CommonFunctions.printStep();
        column("Дата с").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Дата по».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата по».")
    private void step08() {
        CommonFunctions.printStep();
        column("Дата по").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Сумма реорганизации на второй год планового периода».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Сумма реорганизации на второй год планового периода».")
    private void step09() {
        CommonFunctions.printStep();
        column("Сумма реорганизации на второй год планового периода").sortByClick()
                .setDataTypes("Number")
                .assertOrderIsASC();
    }
}

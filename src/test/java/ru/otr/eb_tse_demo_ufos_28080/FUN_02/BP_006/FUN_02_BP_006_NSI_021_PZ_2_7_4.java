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

public class FUN_02_BP_006_NSI_021_PZ_2_7_4 extends HooksTSE_DEMO_28080 {
    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_2_7_4\\";

    @Owner(value = "Максимова Диана")
    @Description("NSI_021. ПЗ п. 2.7.4. Требования к СФ. Требования к сортировке спр-ка")
    @Link(name = "TSE-T3195", url = "https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3195")
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
        step10();
        step11();
        step12();
        step13();
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

    @Step("Переход по дереву навигации к справочнику «Группы причин отказов»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().resetUserSettings().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Группы причин отказов");
    }

    @Step("Сбросить все пользовательские настройки\n" +
            "Записи отсортированы по реквизиту  «Код», «Детализированный код», в порядке возрастания")
    private void step03() {
        CommonFunctions.printStep();
        column("Код", "Детализированный код").assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Признак контроля».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Признак контроля».")
    private void step04() {
        CommonFunctions.printStep();
        column("Признак контроля").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Код причины отказа ».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Код причины отказа ».")
    private void step05() {
        CommonFunctions.printStep();
        column("Код причины отказа ").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Детализированный код».\n" +
            "Записи отсортированы в порядке возростания по реквизиту «Детализированный код».")
    private void step06() {
        CommonFunctions.printStep();
        column("Детализированный код").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Код».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Код».")
    private void step07() {
        CommonFunctions.printStep();
        column("Код").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Виды причин отказа».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Виды причин отказа».")
    private void step08() {
        CommonFunctions.printStep();
        column("Виды причин отказа").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Причина отказа».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Причина отказа».")
    private void step09() {
        CommonFunctions.printStep();
        column("Причина отказа").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Тип документа ».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Тип документа ».")
    private void step10() {
        CommonFunctions.printStep();
        column("Тип документа ").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Наименование документаа».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Наименование документа».")
    private void step11() {
        CommonFunctions.printStep();
        column("Наименование документа").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Дата начала действия записи».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата начала действия записи».")
    private void step12() {
        CommonFunctions.printStep();
        column("Дата начала действия записи").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Дата окончания действия записи».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата окончания действия записи».")
    private void step13() {
        CommonFunctions.printStep();
        column("Дата окончания действия записи").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }
}

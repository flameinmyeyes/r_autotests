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

public class FUN_02_BP_006_NSI_014_PZ_2_7_4 extends HooksTSE_DEMO_28080 {
    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_014_PZ_2_7_4\\";

    @Owner(value = "Максимова Диана")
    @Description("NSI_014. ПЗ п. 2.7.4. Требования к СФ. Требования к сортировке спр-ка")
    @Link(name = "TSE-T2446", url = "https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2446")
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
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); // Рябова Анна Викторовна
    }

    @Step("Переход по дереву навигации к справочнику «Проекты»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().resetUserSettings().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты");
    }

    @Step("Сбросить все пользовательские настройки\n" +
            "Записи отсортированы по реквизиту «Наименование», в порядке возрастания")
    private void step03() {
        CommonFunctions.printStep();
        column("Наименование проекта").assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Наименование ».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Наименование».")
    private void step04() {
        CommonFunctions.printStep();
        column("Наименование проекта").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Федеральный».\n" +
            "Записи отсортированы в порядке убывания по реквизиту «Федеральный».")
    private void step05() {
        CommonFunctions.printStep();
        column("Федеральный").sortByClick()
                .assertOrderIsDESC();
    }

    @Step("В списковой форме нажать на реквизит «Бюджет субъекта».\n" +
            "Записи отсортированы в порядке убывания по реквизиту «Бюджет субъекта».")
    private void step06() {
        CommonFunctions.printStep();
        column("Бюджет субъекта").sortByClick()
                .assertOrderIsDESC();
    }

    @Step("В списковой форме нажать на реквизит «Муниципальный».\n" +
            "Записи отсортированы в порядке убывания по реквизиту «Муниципальный».")
    private void step07() {
        CommonFunctions.printStep();
        column("Муниципальный").sortByClick()
                .assertOrderIsDESC();
    }

    @Step("В списковой форме нажать на реквизит «Дата начала действия записи».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата начала действия записи».")
    private void step08() {
        CommonFunctions.printStep();
        column("Дата начала действия записи").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Дата окончания действия записи».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата окончания действия записи».")
    private void step09() {
        CommonFunctions.printStep();
        column("Дата окончания действия записи").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }


    @Step("В списковой форме нажать на реквизит «Статус».\n" +
            "Записи отсортированы в порядке убывания по реквизиту «Статус».")
    private void step10() {
        CommonFunctions.printStep();
        column("Статус").sortByClick()
                .assertOrderIsDESC();
    }
}

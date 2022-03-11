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

public class FUN_02_BP_006_NSI_109_PZ_2_8 extends HooksTSE_DEMO_28080 {
    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_069_PZ_2_7_4\\";

    @Owner(value = "Ворожко Александр")
    @Description("NSI_109. ПЗ п. 2.8. Требования к печатной форме спр-ка")
    @Link(name="TSE-T4996", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4996")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
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

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» " +
            "- «Справочники» " +
            "- «Перечень КБК, допущенных к проведению операций по кассовым выплатам " +
            "в период приостановления операций на лицевом счете».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().resetUserSettings().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Перечень КБК, допущенных к проведению операций по кассовым выплатам" +
                " в период приостановления операций на лицевом счете");
    }

    @Step("Сбросить все пользовательские настройки\n" +
            "Записи отсортированы по реквизиту «Дата действия с», в порядке убывания")
    private void step03() {
        CommonFunctions.printStep();
        column("Дата действия с")
                .setDataTypes("Date")
                .assertOrderIsDESC();
    }

    @Step("ВВ списковой форме нажать на реквизит «Номер л/с».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Номер л/с».")
    private void step04() {
        CommonFunctions.printStep();
        column("Номер л/с").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Шаблон КБК».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Шаблон КБК».")
    private void step05() {
        CommonFunctions.printStep();
        column("Шаблон КБК").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Код ТОФК (где введен)».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Код ТОФК (где введен)».")
    private void step06() {
        CommonFunctions.printStep();
        column("Код ТОФК (где введен)").sortByClick()
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Дата действия с».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата действия с».")
    private void step07() {
        CommonFunctions.printStep();
        column("Дата действия с").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Дата действия по».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Дата действия по».")
    private void step08() {
        CommonFunctions.printStep();
        column("Дата действия по").sortByClick()
                .setDataTypes("Date")
                .assertOrderIsASC();
    }

    @Step("В списковой форме нажать на реквизит «Статус записи».\n" +
            "Записи отсортированы в порядке возрастания по реквизиту «Статус записи».")
    private void step09() {
        CommonFunctions.printStep();
        column("Статус записи").sortByClick()
                .assertOrderIsASC();
    }
}

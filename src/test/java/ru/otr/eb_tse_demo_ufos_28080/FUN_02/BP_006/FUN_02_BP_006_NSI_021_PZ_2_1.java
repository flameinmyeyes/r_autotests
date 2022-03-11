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
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;

public class FUN_02_BP_006_NSI_021_PZ_2_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_2_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_021. ПЗ п. 2.1. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T3194", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3194")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
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
        step14();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Группы причин отказов».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Группы причин отказов");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Признак контроля\"  тестовое значение.")
    private void step03() {
        CommonFunctions.printStep();
        //Признак контроля = 0
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Признак контроля", "0");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Код причины отказа\"  тестовое значение.")
    private void step04() {
        CommonFunctions.printStep();
        //1.D02.40.0002
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Код причины отказа ", "1.D02.40.0002");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Детализированный код\"  тестовое значение.")
    private void step05() {
        CommonFunctions.printStep();
        //40001
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Детализированный код", "40001");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Код\"  тестовое значение.")
    private void step06() {
        CommonFunctions.printStep();
        //40
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Код", "40");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Виды причин отказа\"  тестовое значение.")
    private void step07() {
        CommonFunctions.printStep();
        //тест
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Код причины отказа ", "тест");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Виды причин отказа\"  тестовое значение.")
    private void step08() {
        CommonFunctions.printStep();
        //тестовый сценарий
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Виды причин отказа", "тестовый сценарий");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Причин отказа\"  тестовое значение.")
    private void step09() {
        CommonFunctions.printStep();
        //тест
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Причина отказа", "тест");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Тип документа\"  тестовое значение.")
    private void step10() {
        CommonFunctions.printStep();
        //D02
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Тип документа ", "D02");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Наименование документа\"  тестовое значение.")
    private void step11() {
        CommonFunctions.printStep();
        //Документ-основание
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Наименование документа", "Документ-основание");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Статус\"  тестовое значение.")
    private void step12() {
        CommonFunctions.printStep();
        //Актуальная
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Статус", "Актуальная");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Дата начала действия записи\"  тестовое значение.")
    private void step13() {
        CommonFunctions.printStep();
        //21.04.2021
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Дата начала действия записи", "21.04.2021");
    }

    @Step("Нажать на кнопку фильтрации\n" +
            "Ввести в колонку фильтрации \"Дата окончания действия записи\"  тестовое значение.")
    private void step14() {
        CommonFunctions.printStep();
        //21.04.2021
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Дата окончания действия записи", "21.04.2021");
    }

}

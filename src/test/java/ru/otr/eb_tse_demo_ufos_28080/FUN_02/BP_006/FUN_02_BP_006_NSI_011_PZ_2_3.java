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

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_011_PZ_2_3 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_011_PZ_2_3\\";

    @Owner(value="Власовец Кирилл")
    @Description("NSI_011. ПЗ п. 2. 3. Реквизитный состав справочника в соответствии" +
            " с справочником \"Тип документов\"")
    @Link(name="TSE-T4970", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4970")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vlasovets_kirill", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step02();
        step03();
        step09();
        step10();
        step11();
        step02();
        step03();
        step14();
    }

    @AfterMethod
    public void screenShot(){
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul\n" +
            "Роль: 601_01_01 ПУР КС. Ввод документов по ЛС ЮЛ")
    private void step01() {
        CommonFunctions.printStep();
        // Пользователь: Пирогов Владимир Константинович
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)»\n" +
            "- «Справочники» - «Шаблон листа согласования».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Шаблон листа согласования");
        new MainPage().resetUserSettings();
    }

    @Step("Нажать кнопку «Создать» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Выбрать поле \"Обрабатываемый документ\", в окне \"Тип документов\"\n" +
            "Проверить заполнение раздела Данные об организации.")
    private void step04() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@name='btn_subDocAction.applyOrShowSubDoc-cc67']");
        new DocPage().clickRowInDictionary("Документ-основание");
        // Проверяем поле "Обрабатываемый документ"
        $x("//input[@name='tf_dict_doctypename']").click();
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_doctypename'][@title='Документ-основание']"), 60);
        // Проверяем поле "Код СВР/НУБП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_uniccode'][@value='450Э7888']"), 60);
        // Проверяем поле "Наименование"
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='tf_dict_namegrbs']" +
                "[@value='АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО " +
                "ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\"']"), 60);
        // Проверяем секцию "Исполнители"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Исполнители']"), 60);
        // Проверяем секцию "Согласующие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Согласующие']"), 60);
        // Проверяем секцию "Утверждающие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Утверждающие']"), 60);
        // Проверяем секцию "Главный бухгалтер"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Главный бухгалтер']"), 60);
        // Проверяем секцию "Руководитель"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Руководитель']"), 60);
    }

    @Step("Выбрать поле \"Обрабатываемый документ\" в открывшемся окне (Сведения об операциях с ЦС (ф. 0501213)).")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@name='btn_subDocAction.applyOrShowSubDoc-cc67']");
        new DocPage().filterColumnInDictionary("Наименование",
                "Сведения об операциях с ЦС (ф. 0501213)");
        new DocPage().clickRowInDictionary("Сведения об операциях с ЦС (ф. 0501213)");
        // Проверяем секцию "Исполнители"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Исполнители']"), 60);
        // Проверяем секцию "Согласующие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Согласующие']"), 60);
        // Проверяем секцию "Утверждающие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Утверждающие']"), 60);
        // Проверяем секцию "Руководитель ФЭС"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Руководитель ФЭС']"), 60);
        // Проверяем секцию "Руководитель"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Руководитель']"), 60);
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul\n" +
            "Роль: 603_01_02 ПУР КС. Исполнитель ЦС ФК по обработке документов по ЛС ЮЛ")
    private void step06() {
        CommonFunctions.printStep();
        // Пользователь: Колесова Елена Петровна
        new LoginPage().authorization("8ef203db-9712-4dd1-a528-5e953cdc590d");
    }

    @Step("Выбрать поле \"Обрабатываемый документ\" в открывшемся окне (Документ-основание).")
    private void step09() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@name='btn_subDocAction.applyOrShowSubDoc-cc67']");
        new DocPage().clickRowInDictionary("Документ-основание");
        // Проверяем поле "Обрабатываемый документ"
        $x("//input[@name='tf_dict_doctypename']").click();
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_doctypename'][@title='Документ-основание']"), 60);
        // Проверяем поле "Код СВР/НУБП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_uniccode'][@value='00118773']"), 60);
        // Проверяем поле "Наименование"
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='tf_dict_namegrbs']" +
                "[@value='УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО САМАРСКОЙ ОБЛАСТИ']"), 60);
        // Проверяем секцию "Исполнители"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Исполнители']"), 60);
        // Проверяем секцию "Согласующие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Согласующие']"), 60);
        // Проверяем секцию "Утверждающие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Утверждающие']"), 60);
        // Проверяем секцию "Руководитель"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Руководитель']"), 60);
    }

    @Step("Выбрать поле \"Обрабатываемый документ\" в открывшемся окне " +
            "(Акт приемки-передачи показателей лицевого счета).")
    private void step10() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@name='btn_subDocAction.applyOrShowSubDoc-cc67']");
        new DocPage().filterColumnInDictionary("Наименование",
                "Акт приемки-передачи показателей лицевого счета");
        new DocPage().clickRowInDictionary("Акт приемки-передачи показателей лицевого счета");
        // Проверяем секцию "Исполнители"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Исполнители']"), 60);
        // Проверяем секцию "Согласующие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Согласующие']"), 60);
        // Проверяем секцию "Утверждающие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Утверждающие']"), 60);
        // Проверяем секцию "Главный бухгалтер"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Главный бухгалтер']"), 60);
        // Проверяем секцию "Руководитель"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Руководитель']"), 60);
    }

    @Step("Авторизоваться в ЛК ТОФК Обращения ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul\n" +
            "Роль: PFC_ПУР КС. Исполнитель ТОФК обращения")
    private void step11() {
        CommonFunctions.printStep();
        // Пользователь: Балабанов Александр Сергеевич
        new LoginPage().authorization("8e1fdacb-ac18-41bf-a6f7-9c420a5d80a5");
    }

    @Step("Выбрать поле \"Обрабатываемый документ\" в открывшемся окне (Документ-основание).")
    private void step14() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@name='btn_subDocAction.applyOrShowSubDoc-cc67']");
        new DocPage().clickRowInDictionary("Документ-основание");
        // Проверяем поле "Обрабатываемый документ"
        $x("//input[@name='tf_dict_doctypename']").click();
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_doctypename'][@title='Документ-основание']"), 60);
        // Проверяем поле "Код СВР/НУБП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_uniccode'][@value='00114436']"), 60);
        // Проверяем поле "Наименование"
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='tf_dict_namegrbs']" +
                "[@value='УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО Г. САНКТ-ПЕТЕРБУРГУ']"), 60);
        // Проверяем секцию "Исполнители"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Исполнители']"), 60);
        // Проверяем секцию "Согласующие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Согласующие']"), 60);
        // Проверяем секцию "Утверждающие"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Утверждающие']"), 60);
        // Проверяем секцию "Руководитель"
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Руководитель']"), 60);
    }
}
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

public class FUN_02_BP_006_NSI_011_PZ_2_4_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_011_PZ_2_4_1\\";
    public String WAY_TEST_4 = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_KP_4\\";

    @Owner(value="Власовец Кирилл")
    @Description("NSI_011. ПЗ п. 2.4.1.Контроль на уникальность актуальной записи по ключу")
    @Link(name="TSE-T4969", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4969")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vlasovets_kirill", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_4 = setWay(WAY_TEST_4);
        preconditions();
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

    private void preconditions() {
        String docGuid = FileFunctions.readValueFile(WAY_TEST_4 + "GUID.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid " +
                "where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest(
                "src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);
        if (!docStatus.equals("Актуальная")) {
            FUN_02_BP_006_MT_8_KP_4 fun_02_bp_006_mt_8_kp_4 = new FUN_02_BP_006_MT_8_KP_4();
            fun_02_bp_006_mt_8_kp_4.steps();
        }
    }

    @AfterMethod
    public void screenShot(){
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul\n" +
            "Роль: 603_01_02 ПУР КС. Исполнитель ЦС ФК по обработке документов по ЛС ЮЛ")
    private void step01() {
        CommonFunctions.printStep();
        // Пользователь: Рябова Анна Викторовна
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
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

    @Step("Выбрать обрабатываемый документ.")
    private void step04() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@name='btn_subDocAction.applyOrShowSubDoc-cc67']");
    }

    @Step("Выбрать документ \"Документ-основание\".")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage().clickRowInDictionary("Документ-основание");
        // Проверяем поле "Обрабатываемый документ"
        $x("//input[@name='tf_dict_doctypename']").click();
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_doctypename'][@title='Документ-основание']"), 60);
        // Проверяем поле "Код СВР/НУБП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//input[@name='tf_dict_uniccode'][@value='00132269']"), 60);
        // Проверяем поле "Наименование"
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='tf_dict_namegrbs']" +
                "[@value='УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО Г. МОСКВЕ']"), 60);
    }

    @Step("Выбрать исполнителя.")
    private void step06() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//span[text()='Исполнители']/ancestor:: " +
                "div[@_name='panelExec']//button[@title='Добавить новую строку (Ins)']");
        new DocPage().clickWebElement("//button[@name='btn_ossActions.showAllOrIncludedToGroupUsers-CS']");
        $x("//span[text()='Царькова Ирина Степановна']").click();
        new DocPage().clickButtonText("OK");
        new DocPage().clickButtonText("Ok");
    }

    @Step("Выбрать согласующего.")
    private void step07() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//span[text()='Согласующие']/ancestor:: " +
                "div[@_name='panelSogl']//button[@title='Добавить новую строку (Ins)']");
        new DocPage().clickWebElement("//button[@name='btn_ossActions.showAllOrIncludedToGroupUsers-CS_Sogl']");
        $x("//span[text()='Царькова Ирина Степановна']").click();
        new DocPage().clickButtonText("OK");
        $x("//div[@class='subdoc-edit-dialog tbl_fs_agi_agrpers_list z-window " +
                "z-window-highlighted z-window-shadow']//button[text()='Ok']").click();
    }

    @Step("Выбрать утверждающего.")
    private void step08() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//span[text()='Руководитель']/ancestor:: " +
                "div[@_name='panelBoss']//button[@title='Добавить новую строку (Ins)']");
        new DocPage().clickWebElement("//button[@name='btn_ossActions.showAllOrIncludedToGroupUsers-CS_Boss']");
        $x("//span[text()='Царькова Ирина Степановна']").click();
        new DocPage().clickButtonText("OK");
        $x("//div[@class='subdoc-edit-dialog tbl_fs_ei_step_agreer_list_copy z-window " +
                "z-window-highlighted z-window-shadow']//button[text()='Ok']").click();
    }

    @Step("Нажать кнопку \"Актуализировать\". В окне нажать кнопку \"Ок\".")
    private void step09() {
        CommonFunctions.printStep();
        new DocPage().clickButtonTitle("Проверить документ (Alt+Q)");
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()=' В системе уже есть запись с " +
                "указанными данными в полях «Обрабатываемый документ», «Код СВР/НУБП» раздела " +
                "«Данные об организации», «Код СВР/ИП и КФХ» раздела «Обслуживаемые организации», " +
                "«ФИО» раздела «Исполнитель», «ФИО» раздела «Согласующие», «ФИО» раздела «Главный бухгалтер», " +
                "«ФИО» раздела «Руководитель ФЭС», «ФИО» раздела «Руководитель», «Шаблон по умолчанию» " +
                "на статусе «Актуальная» в заданном периоде.']"), 60);
    }
}
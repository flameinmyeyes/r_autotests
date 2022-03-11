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

public class FUN_02_BP_006_MT_8_KP_4 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_KP_4\\";
    private String documentNumber;

    @Owner(value="Власовец Кирилл")
    @Description("МТ 8. КП 4. Формирование и актуализация записи в справочнике «Шаблон листа согласования»")
    @Link(name="TSE-T3647", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3647")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vlasovets_kirill", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        preconditions();
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
    }
    private void preconditions() {
        String docGuid = FileFunctions.readValueFile(WAY_TEST + "GUID.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid " +
                "where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest(
                "src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);
        if (docStatus.equals("Актуальная")) {
            FUN_02_BP_006_MT_8_KP_4_1 fun_02_bp_006_mt_8_kp_4_1 = new FUN_02_BP_006_MT_8_KP_4_1();
            fun_02_bp_006_mt_8_kp_4_1.steps();
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

    @Step("Выбрать поле \"Обрабатываемый документ\", в окне \"Тип документов\"\n" +
            "Проверить заполнение раздела Данные об организации.")
    private void step04() {
        CommonFunctions.printStep();
        documentNumber = $x("//input[@name='tf_dict_codedict']").getAttribute("value");
        FileFunctions.writeValueFile(WAY_TEST + "DocumentNumber.txt", documentNumber);
        new DocPage().clickWebElement("//button[@name='btn_subDocAction.applyOrShowSubDoc-cc67']");
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

    @Step("Добавить строку в разделе \"Исполнители\" и выбрать ФИО. Нажать \"ОК\".")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//span[text()='Исполнители']/ancestor:: " +
                "div[@_name='panelExec']//button[@title='Добавить новую строку (Ins)']");
        new DocPage().clickWebElement("//button[@name='btn_ossActions.showAllOrIncludedToGroupUsers-CS']");
        $x("//span[text()='Царькова Ирина Степановна']").click();
        new DocPage().clickButtonText("OK");
        new DocPage().clickButtonText("Ok");
    }

    @Step("Добавить строку в разделе \"Согласующие\" и выбрать ФИО. Нажать \"ОК\".")
    private void step06() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//span[text()='Согласующие']/ancestor:: " +
                "div[@_name='panelSogl']//button[@title='Добавить новую строку (Ins)']");
        new DocPage().clickWebElement("//button[@name='btn_ossActions.showAllOrIncludedToGroupUsers-CS_Sogl']");
        $x("//span[text()='Царькова Ирина Степановна']").click();
        new DocPage().clickButtonText("OK");
        $x("//div[@class='subdoc-edit-dialog tbl_fs_agi_agrpers_list z-window " +
                "z-window-highlighted z-window-shadow']//button[text()='Ok']").click();
    }

    @Step("Добавить строку в разделе \"Утверждающий. Руководитель\" и выбрать ФИО. Нажать \"ОК\".")
    private void step07() {
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
    private void step08() {
        CommonFunctions.printStep();
        $x("//div[@class='doc-edit-frame z-vlayout']//button[@title='Актуализировать']").click();
        new DocPage().clickButtonText("Да");
        new DocPage().clickButtonText("OK");
        new DocPage().clickButtonTitle("Закрыть окно");
        // Находим шаблон на СФ и проверяем колонку "Статус"
        new MainPage().filterColumnInList("Номер шаблона", documentNumber)
                .filterColumnInList("Обрабатываемый документ. Наименование", "Документ-основание");
        CommonFunctions.waitForElementDisplayed(By.xpath("//tr/td[@title='Актуальная'][@col='" +
                $x("//tr[1]/th[@title='Статус']").getAttribute("toidx") + "']"), 60);
        new MainPage().clickRowInList(documentNumber);
        // Сохраним ГУИД документа
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()=' Атрибуты']"), 60);
        $x("//span[text()=' Атрибуты']").click();
        new MainPage().waitForLoading(60);
        FileFunctions.writeValueFile(WAY_TEST + "GUID.txt",
                $x("//tr[@class='attr-view z-row'][3]/td[@class='attr-value z-cell']/span").getText());
    }
}
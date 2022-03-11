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

public class FUN_02_BP_006_MT_8_KP_4_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_KP_4_1\\";
    public String WAY_TEST_4 = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_KP_4\\";
    private String documentNumber;

    @Owner(value="Власовец Кирилл")
    @Description("МТ 8. КП 4.(1) Отправления в архив записи в справочнике «Шаблон листа согласования»")
    @Link(name="TSE-T5001", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T5001")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vlasovets_kirill", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        preconditions();
        step01();
        step02();
        step03();
        step04();
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
        documentNumber = FileFunctions.readValueFile(WAY_TEST_4 + "DocumentNumber.txt");
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

    @Step("Выбрать запись справочника, где статус равен \"Актуальная\".")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().filterColumnInList("Номер шаблона", documentNumber)
                .filterColumnInList("Обрабатываемый документ. Наименование", "Документ-основание");
        new MainPage().clickRowInList(documentNumber);
    }

    @Step("Нажать кнопку \"Отправить в архив\".")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Отправить в архив");
        CommonFunctions.waitForElementDisplayed(By.xpath("//tr/td[@title='Архивная'][@col='" +
                $x("//tr[1]/th[@title='Статус']").getAttribute("toidx") + "']"), 60);
        new MainPage().addUserColumns("Дата окончания действия записи");
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//tr/td[@title='" + CommonFunctions.dateToday("dd.MM.yyyy") + "'][@col='" +
                        $x("//tr[1]/th[@title='Дата окончания действия записи']")
                                .getAttribute("toidx") + "']"), 60);
    }
}
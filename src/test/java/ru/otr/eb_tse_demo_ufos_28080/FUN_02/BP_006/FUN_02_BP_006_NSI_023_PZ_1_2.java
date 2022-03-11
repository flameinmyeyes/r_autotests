package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Instant;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static ru.otr.eb_tse_demo_ufos_28080.FUN_05.BP_009.FUN_05_BP_009_MT_3_KP_1_3_1.FUN_05_BP_009_MT_3_KP_1_3_8.changeDateFormat;

////AC
public class FUN_02_BP_006_NSI_023_PZ_1_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_2\\";
    public String WAY_TEST_PREVIOUS = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_1\\";
    public String REJECT_REASON;
    public String docGuid;

    @Owner(value = "Ворожко Александр")
    @Description("NSI_023. ПЗ п. 1.2. Актуализация записи справочника")
    @Link(name="TSE-T3199", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3199")
    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_PREVIOUS = setWay(WAY_TEST_PREVIOUS);

        precondition();
        step01();
        step02();
        step03();
        step04();
    }

    @Step("Проверить предусловия")
    public void precondition() throws Exception {
        CommonFunctions.printStep();
        REJECT_REASON = FileFunctions.readValueFile(WAY_TEST_PREVIOUS + "reject_reason.txt");
        docGuid = FileFunctions.readValueFile(WAY_TEST_PREVIOUS + "docGuid.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);
        System.out.println("Статус: " + docStatus);
        if (!docStatus.equals("Новая")) {
            System.out.println("Перезапуск предыдущего теста");
            FUN_02_BP_006_NSI_023_PZ_1_1 fun_02_bp_006_nsi_023_pz_1_1 = new FUN_02_BP_006_NSI_023_PZ_1_1();
            fun_02_bp_006_nsi_023_pz_1_1.steps();
            REJECT_REASON = FileFunctions.readValueFile(WAY_TEST_PREVIOUS + "reject_reason.txt");
            docGuid = FileFunctions.readValueFile(WAY_TEST_PREVIOUS + "docGuid.txt");
        } else {
            System.out.println("Перезапуск предыдущего теста не требуется");
        }
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов")
                .resetUserSettings();
    }

    @Step("Выделить на СФ запись на статусе \"Новая\"")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Виды причин отказа", REJECT_REASON)
                .filterColumnInList("Дата действия с", CommonFunctions.dateToday("dd.MM.yyyy"))
                .filterColumnInList("Статус", "Новая");
        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldNotBe(Condition.visible);
        $(By.xpath("//div[@class='z-listcell-content']")).click();
        CommonFunctions.waitForElementDisplayed(By.xpath("//tr[@title = 'Выделено: 1']"), 30);
    }

    @Step("Нажать кнопку \"Актуализировать\"")
    public void step04() {
        new MainPage().clickButtonTitle("Актуализировать");
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()= 'Операция \"Актуализировать\"']/..//p[contains(text(), 'Успешно завершена')]"), 60);

        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

        Assert.assertEquals(docStatus, "Актуальная");

        FileFunctions.writeValueFile(WAY_TEST + "reject_reason.txt", REJECT_REASON);
        FileFunctions.writeValueFile(WAY_TEST + "docGuid.txt", docGuid);
    }
}

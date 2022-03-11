package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
import functional.DocPage;
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
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$x;

////AC
public class FUN_02_BP_006_NSI_032_MT_8_KP_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_032_MT_8_KP_2\\";

    @Owner(value = "Ворожко Александр")
    @Description("МТ8 КП1 (1). Формирование записи в справочнике \"Критерии приостановления операций по ЛС\" (Расход)")
    @Link(name="TSE-T4293", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4293")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        precondition();
        step01();
        step02();
        step03();
    }

    @Step("Проверить предусловия")
    public void precondition() throws Exception {
        CommonFunctions.printStep();
        step01();
        step02();
        new MainPage()
                .filterColumnInList("Наименование критерия приостановления операций ", "Уплата налогов и сборов%приказа Минфина России от 08.12.2017 № 221н")
                .filterColumnInList("Статус", "Новая");
        if ($x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").isDisplayed()) {
            FUN_02_BP_006_NSI_032_MT_8_KP_1 previousTest = new FUN_02_BP_006_NSI_032_MT_8_KP_1();
            previousTest.steps();
        }
    }

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Критерии приостановления операций по ЛС».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Критерии приостановления операций по ЛС")
                .resetUserSettings();
    }

    @Step("Нажать кнопку «Создать» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Наименование критерия приостановления операций ", "Уплата налогов и сборов%приказа Минфина России от 08.12.2017 № 221н")
                .filterColumnInList("Статус", "Новая");
        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldNotBe(Condition.visible);

        new MainPage()
                .clickRowsInList(1)
                .clickButtonTitle("Удаление документа из БД");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Успешно завершена')]"), 120, true);

        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Наименование критерия приостановления операций ", "Уплата налогов и сборов%приказа Минфина России от 08.12.2017 № 221н")
                .filterColumnInList("Статус", "Новая");
        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldBe(Condition.visible);
    }
}

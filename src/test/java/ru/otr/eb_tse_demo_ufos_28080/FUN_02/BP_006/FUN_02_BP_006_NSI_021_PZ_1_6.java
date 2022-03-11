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

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_021_PZ_1_6 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_5\\";

    @Owner(value="Ворожко Александр")
    @Description("NSI_021. ПЗ п. 1.6. Требования к ВФ. Требования к действиям на визуальной форме спр-ка")
    @Link(name="TSE-T3191", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3191")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
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
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Группы причин отказов")
                .resetUserSettings();
    }

    @Step("Выделить документ на СФ. Нажать кнопку «Просмотреть» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(@class, 'doc-edit-frame')][descendant::*[contains(text(), 'Причина отказа')]]"), 60, true);
        $x("//*[@class='z-listhead']").shouldNotBe(Condition.not(Condition.visible));
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step04() {
        CommonFunctions.printStep();
        $x("//button[@title='Закрыть окно']").click();
        new MainPage().waitForLoading(60);
        $x("//*[contains(@class, 'doc-edit-frame')][descendant::*[contains(text(), 'Причина отказа')]]").shouldNotBe(Condition.visible);
        $x("//*[@class='z-listhead']").shouldBe(Condition.visible);
    }

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Администратор (Пятница) ")
    private void step05() {
        CommonFunctions.printStep();
        new LoginPage().authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Группы причин отказов».")
    public void step06() {
        CommonFunctions.printStep();
        step02();
    }

    @Step("Выделить документ на СФ. Нажать кнопку «Просмотреть» на панели инструментов.")
    public void step07() {
        CommonFunctions.printStep();
        step03();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step08() {
        CommonFunctions.printStep();
        step04();
    }

    @Step("Авторизация в ЛК Клиента с ролью 603_06_02 ПУР КС.Администратор ЦС ФК ")
    public void step09() {
        CommonFunctions.printStep();
        new LoginPage().authorization("8d220b3b-bc53-49c6-b1e0-6244c4afe716");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Группы причин отказов».")
    public void step10() {
        CommonFunctions.printStep();
        step02();
    }

    @Step("Выделить документ на СФ. Нажать кнопку «Просмотреть» на панели инструментов.")
    public void step11() {
        CommonFunctions.printStep();
        step03();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step12() {
        CommonFunctions.printStep();
        step04();
    }
}

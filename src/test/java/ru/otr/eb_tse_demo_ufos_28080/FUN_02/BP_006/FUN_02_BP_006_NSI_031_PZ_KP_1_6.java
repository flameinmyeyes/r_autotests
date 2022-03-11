package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

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
import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Instant;

import static com.codeborne.selenide.Selenide.$;


////AC
public class FUN_02_BP_006_NSI_031_PZ_KP_1_6 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3229
     * NSI_031. ПЗ п. 1.6.Требования к ВФ. Требования к действиям на визуальной форме спр-ка
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_KP_1_6\\";

    @Owner(value = "Якубов Алексей")
    @Description("NSI_031. ПЗ п. 1.6.Требования к ВФ. Требования к действиям на визуальной форме спр-ка")
    @Link(name="TSE-T3229", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3229")
    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        step01("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        step02();
        step03();
        step04();

        step05("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
        step06();
        step07();
        step08();

        step09("8d220b3b-bc53-49c6-b1e0-6244c4afe716"); //Царькова Ирина Степановна
        step10();
        step11();
        step12();
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01(String login) {
        CommonFunctions.printStep();
        loginTo(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step02() {
        CommonFunctions.printStep();
        navigate();
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    private void step03() {
        CommonFunctions.printStep();
        selectRowAndView();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step04() {
        CommonFunctions.printStep();
        closeView();
    }



    @Step("TSE-T3819 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Администратор (Пятница)")
    public void step05(String login) {
        CommonFunctions.printStep();
        loginTo(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step06() {
        CommonFunctions.printStep();
        navigate();
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    private void step07() {
        CommonFunctions.printStep();
        selectRowAndView();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step08() {
        CommonFunctions.printStep();
        closeView();
    }



    @Step("TSE-T4233 (1.0) Авторизация в ЛК Клиента с ролью 603_06_02 ПУР КС.Администратор ЦС ФК")
    public void step09(String login) {
        CommonFunctions.printStep();
        loginTo(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step10() {
        CommonFunctions.printStep();
        navigate();
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    private void step11() {
        CommonFunctions.printStep();
        selectRowAndView();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step12() {
        CommonFunctions.printStep();
        closeView();
    }


    private void loginTo(String login) {
        new LoginPage().authorization(login);
    }

    private void navigate() {
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Настройки доступа ТОФК по месту обращения");
    }

    private void selectRowAndView() {
        new MainPage().clickRowsInList(1).waitForLoading(30);
        new MainPage().clickWebElement("//button[@title='Открыть документ на просмотр'][not(@disabled)]").waitForLoading(30);
    }

    private void closeView() {
        CommonFunctions.waitForElementDisplayed(By.xpath("//table[contains(@class,'docInfoDiv')]//span[text()='Настройка доступа ТОФК по месту обращения']"),30);
        new MainPage().clickWebElement("//button[@title='Закрыть окно'][not(@disabled)]").waitForLoading(30);
        Assert.assertFalse($(By.xpath("//table[contains(@class,'docInfoDiv')]//span[text()='Настройка доступа ТОФК по месту обращения']")).isDisplayed());
    }
}

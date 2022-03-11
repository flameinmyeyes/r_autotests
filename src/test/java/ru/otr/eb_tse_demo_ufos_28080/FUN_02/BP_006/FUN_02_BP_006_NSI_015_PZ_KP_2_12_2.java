package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.Ways;
import functional.CommonFunctions;
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

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_015_PZ_KP_2_12_2 extends HooksTSE_DEMO_28080 {

    /**
     * NSI_015. ПЗ п. 2.12.2.Доступ к Справочнику предоставляется ролям пользователей (справочник не доступен клиенту и ЦС)
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3176
     */

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_PZ_KP_2_12_2\\";


    @Owner(value = "Якубов Алексей")
    @Description("NSI_015. ПЗ п. 2.12.2.Доступ к Справочнику предоставляется ролям пользователей (справочник не доступен клиенту и ЦС)")
    @Link(name="TSE-T3176", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3176")
    @Test(groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    //@Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "osterov_eduard", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);

        step01("7806990d-3489-4496-9d20-03f31969a64e"); //Пирогов Владимир Константинович
        step02();

        step03("f5571d67-e5d4-4e3b-b490-78d62cb3a5f5"); //Бекренева Наталья Вячеславовна
        step04();

        step05("f16d2b47-36e2-4e90-b32b-1ecbfaed54cd"); //Кузьминых Богдан Олегович
        step06();

        step07("472fbfd5-a05e-4507-990d-759a42f99ba5"); //Мечников Иван Ильич
        step08();

        step09("8ef203db-9712-4dd1-a528-5e953cdc590d"); //Колесова Елена Петровна
        step10();

        step11("1320e682-4d8b-4c5a-9c10-032dbab70d34"); //Беспалова Елена Викторовна
        step12();

        step13("8953f7d6-5909-425f-b1ce-1cf4e60e7a27"); //Киченко Наталья Викторовна
        step14();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("TSE-T3692 (1.0) Авторизация в ЛК Клиента. Исполнитель (Пирогов)")
    private void step01(String login) {
        CommonFunctions.printStep();
        login(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step02() {
        CommonFunctions.printStep();
        dontSeeMenuItem();
    }

    @Step("TSE-T3694 (1.0) Авторизация в ЛК Клиента. Согласующий")
    private void step03(String login) {
        CommonFunctions.printStep();
        login(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step04() {
        CommonFunctions.printStep();
        dontSeeMenuItem();
    }

    @Step("TSE-T3693 (1.0) Авторизация в ЛК Клиента. Утверждающий")
    private void step05(String login) {
        CommonFunctions.printStep();
        login(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step06() {
        CommonFunctions.printStep();
        dontSeeMenuItem();
    }

    @Step("TSE-T3770 (1.0) Авторизация в ЛК Клиента. Утверждающий ( Главный бухгалтер)С")
    private void step07(String login) {
        CommonFunctions.printStep();
        login(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step08() {
        CommonFunctions.printStep();
        dontSeeMenuItem();
    }

    @Step("TSE-T4144 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС (Колесова)")
    private void step09(String login) {
        CommonFunctions.printStep();
        login(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    public void step10() {
        CommonFunctions.printStep();
        dontSeeMenuItem();
    }

    @Step("TSE-T4157 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Согласующий ЦС (Беспалова)")
    private void step11(String login) {
        CommonFunctions.printStep();
        login(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step12() {
        CommonFunctions.printStep();
        dontSeeMenuItem();
    }

    @Step("TSE-T4158 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Утверждающий ЦС (Киченко)")
    public void step13(String login) {
        CommonFunctions.printStep();
        login(login);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    public void step14() {
        CommonFunctions.printStep();
        dontSeeMenuItem();
    }

    private void login(String login) {
        new LoginPage().authorization(login);
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    private void dontSeeMenuItem() {
        //Нет справочника  «ИГК».
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники").waitForLoading(30);
        if ($(By.xpath("//div[@class='tree-holder z-center']//span[text()=' ИГК']")).exists()) {
            System.out.println("Есть справочник ИГК");
            Assert.fail();
        }else{
            System.out.println("Нет справочника ИГК");
        }
    }


}

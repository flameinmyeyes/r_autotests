package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
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
import static com.codeborne.selenide.Selenide.refresh;

public class FUN_02_BP_006_NSI_055_PZ_2_7_2_2 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_055_PZ_2_7_2_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_055. ПЗ п. 2.7.2(2).Требования к действиям из списковой формы (Клиентом)")
    @Link(name="TSE-T3241", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3241")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() {
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

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Реестр приостанавливаемых операций ».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники");
    }

    @Step("Проверить что справочник «Реестр приостанавливаемых операций»  не доступен")
    private void step03() {
        CommonFunctions.printStep();
        //Справочник не доступен
        Assert.assertTrue(!$x("//span[text()=' Справочники']/ancestor::tr/following::span[text()=' Реестр приостанавливаемых операций'][1]").isDisplayed());
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step04() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(10);
        new LoginPage().authorization("f5571d67-e5d4-4e3b-b490-78d62cb3a5f5");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Реестр приостанавливаемых операций ».")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники");
    }

    @Step("Проверить что справочник «Реестр приостанавливаемых операций»  не доступен")
    private void step06() {
        CommonFunctions.printStep();
        //Справочник не доступен
        Assert.assertTrue(!$x("//span[text()=' Справочники']/ancestor::tr/following::span[text()=' Реестр приостанавливаемых операций'][1]").isDisplayed());
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step07() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(10);
        new LoginPage().authorization("f16d2b47-36e2-4e90-b32b-1ecbfaed54cd");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Реестр приостанавливаемых операций ».")
    private void step08() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники");
    }

    @Step("Проверить что справочник «Реестр приостанавливаемых операций»  не доступен")
    private void step09() {
        CommonFunctions.printStep();
        //Справочник не доступен
        Assert.assertTrue(!$x("//span[text()=' Справочники']/ancestor::tr/following::span[text()=' Реестр приостанавливаемых операций'][1]").isDisplayed());
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step10() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(10);
        new LoginPage().authorization("472fbfd5-a05e-4507-990d-759a42f99ba5");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Реестр приостанавливаемых операций ».")
    private void step11() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники");
    }

    @Step("Проверить что справочник «Реестр приостанавливаемых операций»  не доступен")
    private void step12() {
        CommonFunctions.printStep();
        //Справочник не доступен
        Assert.assertTrue(!$x("//span[text()=' Справочники']/ancestor::tr/following::span[text()=' Реестр приостанавливаемых операций'][1]").isDisplayed());
    }

}

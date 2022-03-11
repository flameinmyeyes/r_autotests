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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.util.HashMap;
import java.util.Map;

public class FUN_02_BP_006_NSI_055_PZ_2_7_2_1 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_055_PZ_2_7_2_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_055. ПЗ п. 2.7.2(1). Требования к СФ. Требования к действиям из списковой форм")
    @Link(name="TSE-T3244", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3244")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Переход по дереву навигации к справочнику «Реестр приостанавливаемых операций»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Реестр приостанавливаемых операций");
    }

    @Step("Нажать кнопу \"Обновить\"")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Обновить список документов (Alt+F5)");

        //обновлена СФ справочника  «Реестр приостанавливаемых операций»
    }

    @Step("Выбрать запись справочника  «Реестр приостанавливаемых операций»")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage().clickRowsInList(1);
    }

    @Step("Нажать кнопу \"Просмотреть\"")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Открыть документ на просмотр");

        //Открыта ВФ справочника  «Реестр приостанавливаемых операций»
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]"), 120);
    }

}

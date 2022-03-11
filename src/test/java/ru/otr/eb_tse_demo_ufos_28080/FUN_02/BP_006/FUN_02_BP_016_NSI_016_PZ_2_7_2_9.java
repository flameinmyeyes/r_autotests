package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.Ways;
import functional.CommonFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_016_NSI_016_PZ_2_7_2_9 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_9\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_016. ПЗ п. 2.7.2. ТК №9 Проверка возможности редактирования записи")
    @Link(name="TSE-T2484", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2484")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_016"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot( WAY_TEST + "screen.png");
    }

    @Step("Зайти на стенд. Авторизироваться в ЛК Администратора локальных НСИ ПУР КС")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть в навигации папку \"Справочники\"")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)> Справочники");
    }

    @Step("Открыть \"Виды средств\"")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Виды средств");
    }

    @Step("Открыть запись на статусе \"Новая\" и нажать на кнопку \"Редактировать\"")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Статус", "Новая")
                .clickRowInList("Новая")
                .clickButtonTitle("Открыть документ на редактирование");

        //Открыта форма редактирования записи
        Assert.assertFalse($x("//input[@name = 'tf_dict_code'][@overridetooltip='true'][@readonly = 'readonly']").exists());
        Assert.assertTrue($x("//input[@name = 'tf_dict_code']").exists());
    }

}

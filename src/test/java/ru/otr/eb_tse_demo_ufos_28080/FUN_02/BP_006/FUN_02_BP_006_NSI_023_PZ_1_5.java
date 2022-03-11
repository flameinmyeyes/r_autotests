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

import static com.codeborne.selenide.Selenide.refresh;


public class FUN_02_BP_006_NSI_023_PZ_1_5 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_5\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_023. ПЗ п. 1.5. Требования к ВФ. Требования к действиям на визуальной форме спр-ка")
    @Link(name="TSE-T3203", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3203")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
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

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов");
    }

    @Step("Выделить документ на СФ.\n" +
            "Нажать кнопку «Просмотреть» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");
    }

    @Step("Нажать на кнопку \"Закрыть\"")
    public void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Закрыть окно");

        CommonFunctions.waitForElementDisappeared(By.xpath("//div[contains(@class,'dialog-edit')]"), 60);
    }

}

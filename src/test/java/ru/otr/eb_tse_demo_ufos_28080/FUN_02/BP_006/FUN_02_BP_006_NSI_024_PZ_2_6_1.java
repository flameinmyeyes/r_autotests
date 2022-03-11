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
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_024_PZ_2_6_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_6_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.6.1. Требования к ВФ. Требования к действиям на визуальной форме спр-ка")
    @Link(name="TSE-T3215", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3215")

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
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку «Создать» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Проверить наличие отображение кнопок \"Актуализировать\", \"Сохранить\", Сохранить и закрыть\", \"Закрыть\"")
    private void step04() {
        CommonFunctions.printStep();

        //На ВФ присутствуют кнопки "Актуализировать", "Сохранить", Сохранить и закрыть", "Закрыть"
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]//button[@title='Актуализировать']"), 120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]//button[@title='Сохранить изменения и закрыть окно (Alt+S)']"), 120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]//button[@title='Сохранить изменения (Alt+A)']"), 120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]//button[@title='Закрыть окно']"), 120);
    }

}

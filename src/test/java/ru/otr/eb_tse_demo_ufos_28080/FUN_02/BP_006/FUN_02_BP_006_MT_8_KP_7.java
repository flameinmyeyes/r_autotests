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

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_MT_8_KP_7 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4760
     * МТ 8 КП7 Проверка открытия записи в режиме просмотра "Критерии приостановления операций по ЛС"
     * задача:
     * Проверка открытия записи в режиме просмотра "Критерии приостановления операций по ЛС"
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_KP_7\\";
    private String documentNumber;

    @Owner(value = "Якубов Алексей")
    @Description("МТ 8 КП7 Проверка открытия записи в режиме просмотра \"Критерии приостановления операций по ЛС\"")
    @Link(name="TSE-T4760", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4760")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})

    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
    }


    @AfterMethod
    public void screenShot(){
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul\n" +
            "Роль: 603_01_02 ПУР КС. Исполнитель ЦС ФК по обработке документов по ЛС ЮЛ")
    public void step01() {
        CommonFunctions.printStep();
        // Пользователь: Рябова Анна Викторовна
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)»\n" +
            "- «Справочники» - «Шаблон листа согласования».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Критерии приостановления операций по ЛС");
        new MainPage().resetUserSettings();
    }

    @Step("Нажать кнопку «Создать» на панели инструментов.")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage().filterColumnInList("Статус", "Актуальная");
        new MainPage().clickRowsInList(1).waitForLoading(90);
        new MainPage().clickWebElement("//button[@title='Открыть документ на просмотр'][not(@disabled)]");
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Критерии приостановления операций по ЛС']"), 90);
        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@title='Закрыть окно'][not(@disabled)]"), 90);
    }


}
package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
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

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_015_PZ_KP_2_12_2_1 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_PZ_KP_2_12_2_1\\";


    @Owner(value = "Ворожко Александр")
    @Description("NSI_015. ПЗ п. 2.12.2(1).Справочник доступен пользователям")
    @Link(name="TSE-T3177", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3177")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);

        step01(); //Пирогов Владимир Константинович
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

    @Step("Авторизация в ЛК Клиента с ролью 603_06_02 ПУР КС.Администратор ЦС ФК")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("8d220b3b-bc53-49c6-b1e0-6244c4afe716"); //Царькова Ирина Степановна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " ИГК");
    }

    @Step("Проверить что СФ открыта")
    private void step03() {
        CommonFunctions.printStep();
        $x("//*[contains(@class, 'doc-browse-pane-center')][descendant::*[contains(text(), 'ИГК')]]").shouldBe(Condition.visible);
    }

    @Step("Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    private void step04() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step05() {
        CommonFunctions.printStep();
        step02();
    }

    @Step("Проверить что СФ открыта")
    private void step06() {
        CommonFunctions.printStep();
        step03();
    }

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Администратор (Пятница)")
    private void step07() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step08() {
        CommonFunctions.printStep();
        step02();
    }

    @Step("Проверить что СФ открыта")
    private void step09() {
        CommonFunctions.printStep();
        step03();
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС")
    private void step10() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step11() {
        CommonFunctions.printStep();
        step02();
    }

    @Step("Проверить что СФ открыта")
    private void step12() {
        CommonFunctions.printStep();
        step03();
    }
}

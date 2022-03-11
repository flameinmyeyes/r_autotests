package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
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
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_MT_1_KP_2_4 extends HooksTSE_DEMO_28080 {


    /**
     * МТ 1. КП 2. Актуализация записи в справочнике  "Проекты" (СФ )
     */

    private  String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_1_KP_2_4\\";
    private  String WAY_TEST_number = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_1_KP_2_1\\";


    @Description("МТ 1. КП 2. Актуализация записи в справочнике  \"Проекты\" (СФ )")
    @Owner(value = "Каверина Марина")
    @Link(name="TSE-T3826", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3826")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "kaverina_marina", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_number = setWay(WAY_TEST_number);
        FUN_02_BP_006_MT_1_KP_2_1.step01();
        FUN_02_BP_006_MT_1_KP_2_1.step02();
        FUN_02_BP_006_MT_1_KP_2_1.step03();
        FUN_02_BP_006_MT_1_KP_2_1.step04();
        FUN_02_BP_006_MT_1_KP_2_1.step05();

        refresh();
        step01();
        step02();
        step03();
        step04();


    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Шаг 1.Авторизоваться в ЛК ЦС Обслуживания ПУР КС")
    private void step01() {
        //Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:8889/sufdclient/index.zul
        CommonFunctions.printStep();
        new LoginPage().authorization("73faa96c-44d0-411f-9d16-85115f03f958");
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='tree-holder z-center']"), 60);
    }

    @Step("Шаг 2. Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Проекты»")
    private void step02() {
        // Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Проекты»
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)> Справочники> Проекты")
                .waitForLoading(30);

        new MainPage()
                .resetUserSettings();
    }

    @Step("Шаг 3. Выделить на СФ \"Проект\" со статусом «Новая»")
    private void step03() {
        CommonFunctions.printStep();
        String Date = CommonFunctions.dateToday("dd.MM.yyyy");

        new MainPage().setUserColumns("Наименование", "Дата начала действия записи", "Статус");

        new MainPage().filterColumnInList("Наименование проекта", FileFunctions.readValueFile( WAY_TEST_number + "docNum.txt"))
                .waitForLoading(30);

        $x("//span[@filter-for='SND_DATEDOCSTART']//input")
                .setValue(Date)
                .pressEnter();

        new MainPage().waitForLoading(10);
        $x("//span[contains(@title,'Для задания точного совпадения добавьте знак = перед фильтром (например: =Утвержден)')]//input[@readonly]")
                .should(Condition.visible, Duration.ofSeconds(60))
                .click();

        $x("//div[contains(text(), 'Новая')]/span")
                .should(Condition.visible, Duration.ofSeconds(60))
                .click();

        new MainPage().waitForLoading(10);
        $(By.xpath("//td/button[text()='ОК']")).click();


    }

    @Step("Шаг 4. Нажать кнопку \"Актуализировать\"")
    private void step04() {
        CommonFunctions.printStep();

        $x("//div[@class='advcheckbox-default']")
                .should(Condition.visible, Duration.ofSeconds(80))
                .click();

        $x("//button[@title='Актуализировать']")
                .should(Condition.visible, Duration.ofSeconds(80))
                .click();


        ElementsCollection row = $$x("//*[contains(@title,'Выделено:')]//td[8]");
        CommonFunctions.wait(20);

        for (SelenideElement s : row) {

            Assert.assertEquals(s.getAttribute("title"), "Актуальная");



        }


    }

}

package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.Ways;
import functional.CommonFunctions;
import functional.FileFunctions;
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
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_MT_1_KP_2_5 extends HooksTSE_DEMO_28080 {
    long wait = 100000;
    long waitInterval = 50;
    String docDate;

    /**
     * Актуализация записи в справочнике "Проекты" (ВФ )
     */

    private  String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_1_KP_2_5\\";
    private  String WAY_TEST_number1 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_1_KP_2_2\\";
    private  String WAY_TEST_number2 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_1_KP_2_3\\";

    @Description("МТ 1. КП 2. Актуализация записи в справочнике  \"Проекты\" (ВФ )")
    @Owner(value = "Каверина Марина")
    @Link(name="TSE-T3834", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3834")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "kaverina_marina", "FUN_02", "BP_006"})
    public void steps() throws Exception {

        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_number1 = setWay(WAY_TEST_number1);
        WAY_TEST_number2 = setWay(WAY_TEST_number2);
        FUN_02_BP_006_MT_1_KP_2_2.step01();
        FUN_02_BP_006_MT_1_KP_2_2.step02();
        FUN_02_BP_006_MT_1_KP_2_2.step03();
        FUN_02_BP_006_MT_1_KP_2_2.step04();
        FUN_02_BP_006_MT_1_KP_2_2.step05();
        refresh();
        FUN_02_BP_006_MT_1_KP_2_3.step01();
        FUN_02_BP_006_MT_1_KP_2_3.step02();
        FUN_02_BP_006_MT_1_KP_2_3.step03();
        FUN_02_BP_006_MT_1_KP_2_3.step04();
        FUN_02_BP_006_MT_1_KP_2_3.step05();
        refresh();
        step01();
        step02();
        step03(WAY_TEST_number1);
        step03(WAY_TEST_number2);

    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Шаг 1.Авторизоваться в ЛК ЦС Обслуживания ПУР КС")
    private void step01() {
        //Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul
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
    private void step03(String WAY_TEST_number) {
        //Просмотр формуляра
        CommonFunctions.printStep();
        new MainPage().setUserColumns("Наименование", "Дата начала действия записи", "Статус");






            new MainPage().filterColumnInList("Наименование проекта", FileFunctions.readValueFile( WAY_TEST_number + "docNum.txt"))
                    .clickRowInList(FileFunctions.readValueFile( WAY_TEST_number + "docNum.txt"))
                    .waitForLoading(30);


            $x("//button[@title='Открыть документ на просмотр']")
                    .should(Condition.visible, Duration.ofSeconds(80))
                    .click();

            new MainPage().waitForLoading(40);

            step04();

            $x("//button[text()='OK']").should(Condition.visible, Duration.ofSeconds(60)).click();

            $x("//button[@title='Закрыть окно']")
                    .should(Condition.visible, Duration.ofSeconds(80))
                    .click();

            Assert.assertEquals($x("//*[contains(@title,'Выделено:')]//td[8]").getAttribute("title"), "Актуальная");



    }

    @Step("Шаг 4. Нажать кнопку \"Актуализировать\"")
    private void step04() {
        //Актуализировать запись и проверить статус
        CommonFunctions.printStep();

        $x("//*[@class='dialog-edit maximized z-window z-window-noborder z-window-modal']//button[@title='Актуализировать']")
                .should(Condition.visible, Duration.ofSeconds(80))
                .click();

        new MainPage().waitForLoading(60);


    }

}

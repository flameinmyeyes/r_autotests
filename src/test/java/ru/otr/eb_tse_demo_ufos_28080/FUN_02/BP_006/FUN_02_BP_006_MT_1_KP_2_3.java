package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

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

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_MT_1_KP_2_3 extends HooksTSE_DEMO_28080 {



    /**
     * МТ 1. КП 2(3). Формирование записи в справочнике  "Проекты" утверждающим ЦС ФК
     */

    private static  String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_1_KP_2_3\\";

    @Description("МТ 1. КП 2(3). Формирование записи в справочнике  \"Проекты\" утверждающим ЦС ФК")
    @Owner(value = "Каверина Марина")
    @Link(name="TSE-T3831", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3831")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "kaverina_marina", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();


    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot( WAY_TEST + "screen.png");
    }

    @Step("Шаг 1.Авторизоваться в ЛК ЦС Обслуживания ПУР КС")
    public static void step01() {
        //Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul
        CommonFunctions.printStep();
        new LoginPage().authorization("73faa96c-44d0-411f-9d16-85115f03f958 ");
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='tree-holder z-center']"), 60);
    }

    @Step("Шаг 2. Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Проекты»")
    public static void step02() {
        // Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Проекты»
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)> Справочники> Проекты")
                .waitForLoading(30);

        new MainPage()
                .resetUserSettings();
    }

    @Step("Шаг 3. Создание нового документа")
    public static void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Создать новый документ (Alt+N)")
                .waitForLoading(30);
    }

    @Step("Шаг 4. Заполнить поля")
    public static void step04() {
        CommonFunctions.printStep();
        String docDate;
        String rand = "ЕДПОСТАВЩИК_АТ_" + CommonFunctions.randomNumber(1, 99999);
        System.out.println(rand);

        new DocPage()
                .inputValueInArea("Наименование проекта:", rand, "Основная информация")
                .waitForLoading(30);

        new DocPage()
                .inputValueInArea("Примечание:", "Проекты казначейского сопровождения", "Основная информация")
                .waitForLoading(30);

        new DocPage()
                .clickCheckbox("Муниципальный")
                .waitForLoading(30);

        docDate = CommonFunctions.dateToday("dd.MM.yyyy");

        //поле "Дата начала действия записи " заполнено текущей датой

        Assert.assertEquals($x("//input[@name='dtf_dict_snd_datedocstart']").getAttribute("value"), docDate);

        //поле "Статус" заполнено значением Новая и не доступно для редактирования ,
        Assert.assertEquals($x("//input[@name='tf_dict_snd_status']").getAttribute("value"), "Новая");
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='tf_dict_snd_status'][@readonly]"), 30);

        //поле "Дата окончания действия записи " не доступно для редактирования
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='dtf_dict_snd_datedocend'][@readonly]"), 30);

        Assert.assertEquals($x("//input[@name='dtf_dict_snd_datedocstart']").getAttribute("autocomplete"), "off");

        FileFunctions.writeValueFile(WAY_TEST + "docDate.txt", docDate);
        FileFunctions.writeValueFile(WAY_TEST + "docNum.txt", rand);

    }

    @Step("Шаг 5. Сохранить изменения и закрыть окно")
    public static void step05() {
        CommonFunctions.printStep();
        String rand=  FileFunctions.readValueFile(WAY_TEST + "docNum.txt");
        new MainPage()
                .clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)")
                .waitForLoading(30);

        //Колонка "Наименование" заполнена значением ЕДПОСТАВЩИК
        Assert.assertEquals($x("//*[contains(@class,\"addedDocument\")]//td[2]").getAttribute("title"), rand);

        //D разделе колонок у "Уровень бюджета" у колонка "Федеральный" заполнено значением Нет, колонка "Бюджет субъекта " заполнено значением Нет, колонка "Муниципальный " заполнено значением Да

        Assert.assertEquals($x("//*[contains(@class,\"addedDocument\")]//td[3]").getAttribute("title"), "Нет");
        Assert.assertEquals($x("//*[contains(@class,\"addedDocument\")]//td[4]").getAttribute("title"), "Нет");
        Assert.assertEquals($x("//*[contains(@class,\"addedDocument\")]//td[5]").getAttribute("title"), "Да");

        //Колонка "Статус " заполнена значением «Новая».
        Assert.assertEquals($x("//*[contains(@class,\"addedDocument\")]//td[8]").getAttribute("title"), "Новая");


    }


}


package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

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
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_MT_8_KP_1_2 extends HooksTSE_DEMO_28080 {



    /**
     * МТ 8. КП 1. Преобразование справочника "ФАИП" на «Объекты капитального вложения (ОКВ)»
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_KP_1_2\\";

    @Owner(value = "Якубов Алексей")
    @Description("МТ 8. КП 1. Преобразование справочника 'ФАИП' на «Объекты капитального вложения (ОКВ)»")
    @Link(name="TSE-T4530", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4530")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    //@Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    @Test(groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @Step("TSE-T3689 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС")
    public void step01() {
        CommonFunctions.printStep();
        //new LoginPage().authorization("Loboda.KI", "Oracle33");
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='tree-holder z-center']"), 60);
    }

    @Step("Проверить в навигации «Управление расходами (казначейское сопровождение)» - «Справочники», есть справочник с наименованием «Объекты капитального вложения (ОКВ)»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Объекты капитального вложения (ОКВ)").waitForLoading(30);
        new MainPage().resetUserSettings();
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Объекты капитального вложения (ОКВ)» ")
    public void step03() {
        CommonFunctions.printStep();
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[contains(@class,'z-menuitem-text')][contains(.,'Объекты капитального вложения (ОКВ)')]"), 30);
    }

    @Step("Проверить наименование колонок:\n" +
            "Колонка 3,\n" +
            "Колонка 4,\n" +
            "Колонка 5,\n" +
            "Колонка 6,\n" +
            "Колонка 7")
    public void step04() {
        CommonFunctions.printStep();
        //Статус записи
        Assert.assertEquals($x("//*[contains(@class,'z-listheader z-listheader-sort')][2]").getAttribute("title"), "Код ОКВ");
        Assert.assertEquals($x("//*[contains(@class,'z-listheader z-listheader-sort')][3]").getAttribute("title"), "Наименование ОКВ");
        Assert.assertEquals($x("//*[contains(@class,'z-listheader z-listheader-sort')][4]/div").getText(), "Дата начала периода действия");
        Assert.assertEquals($x("//*[contains(@class,'z-listheader z-listheader-sort')][5]/div").getText(), "Дата окончания периода действия");
        Assert.assertEquals($x("//*[contains(@class,'z-listheader z-listheader-sort')][6]/div").getText(), "Код вышестоящего объекта ОКВ");
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step05() {
        CommonFunctions.printStep();
        $(By.xpath("//tr[contains(@class,'z-listitem')]/td[@col='1']")).click();
        CommonFunctions.waitForElementDisplayed(By.xpath("//tr[contains(@class,'z-listitem')][contains(@title,'Выделено: 1')]"), 60);
        //Кликаем "Просмотреть"
        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@title='Открыть документ на просмотр']"), 60);
        new MainPage().clickButtonTitle("Открыть документ на просмотр");
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[contains(@class,'docInfoLabel')][contains(.,'ОКВ')]"), 60);
    }

    @Step("Проверить наименование строк:\n" +
            "строка 1,\n" +
            "строка 2,\n" +
            "строка 3,\n" +
            "строка 5")
    public void step06() {
        CommonFunctions.printStep();
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-content-view')]//span[@name='lbl_dict_mi_code'][text()='Код ОКВ']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-content-view')]//span[@name='lbl_dict_mi_name'][text()='Наименование ОКВ']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-content-view')]//span[@name='lbl_dict_mi_namepfprint'][text()='Наименование ОКВ (для печати)']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-content-view')]//span[text()='Вышестоящий объект ОКВ']"), 60);
    }

}

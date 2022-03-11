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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_011_PZ_2_7_1 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4967
     * NSI_011. ПЗ п. 2.7.1.Требования к отображению реквизитов в СФ справочника
     * задача:
     * Проверить отображение реквизитов в СФ
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() +
            "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_011_PZ_2_7_1\\";

    @Owner(value = "Якубов Алексей")
    @Description("NSI_011. ПЗ п. 2.7.1.Требования к отображению реквизитов в СФ справочника")
    @Link(name="TSE-T4967", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4967")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})

    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @AfterMethod
    public void screenShot(){
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("TSE-T3692 (1.0) Авторизация в ЛК Клиента. Исполнитель (Пирогов)")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e"); // Пользователь: Пирогов Владимир Константинович
    }

    @Step("TSE-T4804 (1.0) Переход по дереву навигации к справочнику «Шаблон листа согласования»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Шаблон листа согласования");

    }

    @Step("В меню «Настройки» выбрать «Сбросить пользовательские настройки».\n" +
            "Установить чекбокс в появившемся диалоговом окне на пункте «Все» и нажать кнопку «Применить».")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().resetUserSettings();
    }

    @Step("Выбрать колонки СФ, через кнопку \"Выбор колонки\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage().contextClickWebElement("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']");
        new MainPage().clickWebElement("//div[@class='z-menupopup z-menupopup-shadow z-menupopup-open']//span[@class='z-menuitem-text'][text()='Выбор колонок ...']");
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[@class='z-label'][text()='Название колонки']/parent::*/span[1]"), 30);

        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Номер шаблона')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Обрабатываемый документ. Наименование')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);

        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem')][not(contains(@class,'z-listitem-selected'))]//div[contains(@class,'z-listcell-content')][contains(.,'Дата начала действия записи')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem')][not(contains(@class,'z-listitem-selected'))]//div[contains(@class,'z-listcell-content')][contains(.,'Дата окончания действия записи')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);

        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Шаблон по умолчанию')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Код СВР/НУБП')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Наименование')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Статус')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 10);
    }

    @Step("Выбрать чек-бокс Дата начала действия, Дата окончания действия и нажать кнопку  \"ОК\"")
    private void step05() {
        CommonFunctions.printStep();

        new MainPage().clickWebElement("//div[contains(@class,'z-listbox-body')]//tr[not(contains(@class,'z-listitem-selected'))]//div[contains(@class,'z-listcell-content')][contains(.,'Дата начала действия записи')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]");
        new MainPage().clickWebElement("//div[contains(@class,'z-listbox-body')]//tr[not(contains(@class,'z-listitem-selected'))]//div[contains(@class,'z-listcell-content')][contains(.,'Дата окончания действия записи')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]");

        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Дата начала действия записи')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 20);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listbox-body')]//tr[contains(@class,'z-listitem-selected')]//div[contains(@class,'z-listcell-content')][contains(.,'Дата окончания действия записи')]//i[contains(@class,'z-icon-check')]/ancestor::span[1]"), 20);

        new MainPage().clickWebElement("//div[contains(@class,'z-south')]//button[text()='OK']").waitForLoading(10);

        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='doc-table-holder z-center']//th[@title='Дата начала действия записи']"), 20);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='doc-table-holder z-center']//th[@title='Дата окончания действия записи']"), 20);
    }


}
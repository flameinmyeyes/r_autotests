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
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.FUN_08.BP_021.FUN_08_BP_021_MT_14_KP_1_1;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;


public class FUN_02_BP_006_NSI_029_PZ_KP_2_7_3 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4982
     * NSI_029. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_029_PZ_KP_2_7_3\\";


    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_029. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T4982", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4982")
    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4984 (1.0) Переход по дереву навигации к справочнику «Символы кассовой отчетности»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Символы кассовой отчетности").waitForLoading(30);
        new MainPage().resetUserSettings().waitForLoading(30);
    }

    @Step("Нажать на кнопку фильтрации. Ввести в колонку фильтрации \"Код символа\"  тестовое значение.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().filterColumnInList("Код символа", "75").waitForLoading(30);
        checkCol(2, "75"); //Код символа
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Наименование символа\" тестовое значение.")
    public void step04() {
        CommonFunctions.printStep();
        resetFilter();
        new MainPage().filterColumnInList("Наименование символа", "Поступления наличных денег в кассу кредитной организации из другой кредитной организации, из территориального управления инкассации - филиала Российского объединения инкассации (РОСИНКАС)").waitForLoading(30);
        checkCol(3, "Поступления наличных денег в кассу кредитной организации из другой кредитной организации, из территориального управления инкассации - филиала Российского объединения инкассации (РОСИНКАС)"); //Наименование символа
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата начала действия\" тестовое значение.")
    public void step05() {
        CommonFunctions.printStep();
        resetFilter();
        new MainPage().filterColumnInList("Дата начала действия", "20.05.2019").waitForLoading(30);
        checkCol(5, "20.05.2019"); //Дата начала действия
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Статус\" тестовое значение.")
    public void step06() {
        CommonFunctions.printStep();
        resetFilter();
        new MainPage().filterColumnInList("Статус", "Актуальная").waitForLoading(30);
        checkCol(4, "Актуальная"); //Статус
    }


    private void resetFilter() {
        $(By.xpath("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']")).contextClick();
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-menupopup')][contains(@class,'z-menupopup-open')]"), 30);
        CommonFunctions.wait(1);
        $(By.xpath("//body")).sendKeys(Keys.ESCAPE);
        CommonFunctions.wait(1);

        new MainPage().clickWebElement("//div[contains(@class,'z-menupopup-open')]" +
                "//ul[contains(@class,'z-menupopup-content')]/li[contains(.,'Колонки по умолчанию')]", 20).waitForLoading(20);
    }

    private void checkCol(String title, String value) {
        int colNumber = hlp.colNumberByTitle(title);
        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "][normalize-space(.)='" + value + "']]")).size();
        System.out.println("Ячеек в колонке '" + title + "' (№" + colNumber + ") всего " + totalRows + " из них " + filtredRows + " содержат значение '" + value + "'.");
    }

    private void checkCol(int colNumber, String value) {
        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "][normalize-space(.)='" + value + "']]")).size();
        System.out.println("Ячеек в колонке №" + colNumber + " всего " + totalRows + " из них " + filtredRows + " содержат значение '" + value + "'.");
    }

}

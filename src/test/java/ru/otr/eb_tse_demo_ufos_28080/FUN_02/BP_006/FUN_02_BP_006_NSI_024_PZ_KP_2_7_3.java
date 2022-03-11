package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.FUN_08.BP_021.FUN_08_BP_021_MT_14_KP_1_1;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;


public class FUN_02_BP_006_NSI_024_PZ_KP_2_7_3 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3219
     * NSI_024. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_KP_2_7_3\\";


    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_024. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T3219", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3219")
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
        step07();
        step08();
        step09();
        step10();
    }

    @Step("TSE-T3689 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4800 (1.0) Переход по дереву навигации к справочнику «Почтовые уведомления»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления").waitForLoading(30);
        new MainPage().resetUserSettings().waitForLoading(30);
    }

    @Step("Нажать на кнопку фильтрации. Ввести в колонку фильтрации \"ИНН \"  тестовое значение.")
    private void step03() {
        CommonFunctions.printStep();
        //ИНН = 7722698789 - 2
        new MainPage().filterColumnInList("ИНН", "7722698789").waitForLoading(30);
        checkCol(2, "75"); //ИНН
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"КПП\" тестовое значение.")
    public void step04() {
        CommonFunctions.printStep();
        //КПП= 772201001 - 3
        resetFilter();
        new MainPage().filterColumnInList("КПП", "772201001").waitForLoading(30);
        checkCol(3, "772201001"); //КПП
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Код СВР/НУБП\" тестовое значение.")
    public void step05() {
        CommonFunctions.printStep();
        //Код СВР/НУБП= 450Э7888 - 4
        resetFilter();
        new MainPage().filterColumnInList("Код СВР/НУБП", "450Э7888").waitForLoading(30);
        checkCol(4, "450Э7888"); //Код СВР/НУБП
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Полное наименование\" тестовое значение.")
    public void step06() {
        CommonFunctions.printStep();
        //Полное наименование= %АКЦИОНЕРНОЕ ОБЩЕСТВО "РОССИЙСКАЯ КОРПОРАЦИЯ % - 5
        resetFilter();
        new MainPage().filterColumnInList("Полное наименование", "%АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ %").waitForLoading(30);
        checkColContains(5, "АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ"); //Полное наименование
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Наименование документа\" тестовое значение.")
    public void step07() {
        CommonFunctions.printStep();
        //Наименование документа= Платежное поручение (входящее) - 6
        resetFilter();
        new MainPage().filterColumnInList("Наименование документа", "Платежное поручение (входящее)").waitForLoading(30);
        checkCol(6, "Платежное поручение (входящее)"); //Наименование документа
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата начала действия записи\" тестовое значение.")
    public void step08() {
        CommonFunctions.printStep();
        //Дата начала действия записи= 22.03.2019 - 7
        resetFilter();
        new MainPage().filterColumnInList("Дата начала действия записи", "22.03.2019").waitForLoading(30);
        checkCol(7, "22.03.2019"); //Дата начала действия записи
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата окончания действия записи\" тестовое значение.")
    public void step09() {
        CommonFunctions.printStep();
        //Дата окончания действия записи= пусто - 8
        resetFilter();

        if ($(By.xpath("//img[contains(@class,'filterToggler z-image')]")).isDisplayed()) {
            $(By.xpath("//img[contains(@class,'filterToggler z-image')]")).click();
        }
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[@filter-for='RECENDDATE']"), 30);

        $(By.xpath("//span[@filter-for='RECENDDATE']")).contextClick();
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-menupopup')][contains(@class,'z-menupopup-open')]"), 30);
        CommonFunctions.wait(1);
        $(By.xpath("//body")).sendKeys(Keys.ESCAPE);
        CommonFunctions.wait(1);

        new MainPage().clickWebElement("//div[contains(@class,'z-menupopup-open')]" +
                "//ul[contains(@class,'z-menupopup-content')]/li[contains(.,'пусто')]", 20).waitForLoading(20);

        checkCol(8, "[NBSP]"); //Дата окончания действия записи
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Статус\" тестовое значение.")
    public void step10() {
        CommonFunctions.printStep();
        //Статус= Актуальная - 9
        resetFilter();
        new MainPage().filterColumnInList("Статус", "Актуальная").waitForLoading(30);
        checkCol(9, "Актуальная"); //Статус
    }


    /**
     * Сброс фильтра СФ
     */
    private void resetFilter() {
        $(By.xpath("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']")).contextClick();
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-menupopup')][contains(@class,'z-menupopup-open')]"), 30);
        CommonFunctions.wait(1);
        $(By.xpath("//body")).sendKeys(Keys.ESCAPE);
        CommonFunctions.wait(1);

        new MainPage().clickWebElement("//div[contains(@class,'z-menupopup-open')]" +
                "//ul[contains(@class,'z-menupopup-content')]/li[contains(.,'Колонки по умолчанию')]", 20).waitForLoading(20);
    }

    /**
     * Проверка все ли ячейки в колонке содержат указанное значение
     * @param colNumber
     * @param value
     */
    private void checkCol(int colNumber, String value) {
        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "][normalize-space(.)='" + value + "']]")).size();
        System.out.println("Ячеек в колонке №" + colNumber + " всего " + totalRows + " из них " + filtredRows + " содержат значение '" + value + "'.");
    }


    /**
     * Проверка все ли ячейки в колонке содержат указанную часть значения
     * @param colNumber
     * @param value
     */
    private void checkColContains(int colNumber, String value) {
        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "][contains(normalize-space(.),'" + value + "')]]")).size();
        System.out.println("Ячеек в колонке №" + colNumber + " всего " + totalRows + " из них " + filtredRows + " содержат значение '" + value + "'.");
    }

}

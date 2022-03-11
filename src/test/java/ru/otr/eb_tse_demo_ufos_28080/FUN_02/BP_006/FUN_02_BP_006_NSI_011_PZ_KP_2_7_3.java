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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.FUN_08.BP_021.FUN_08_BP_021_MT_14_KP_1_1;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;


public class FUN_02_BP_006_NSI_011_PZ_KP_2_7_3 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4806
     * NSI_011. ПЗ п. 2.7.3.Требования к СФ. Требования к фильтрам
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_011_PZ_KP_2_7_3\\";

    private String col2;
    private String col3;
    private String col4;
    private String col7;

    private int col2_num;
    private int col3_num;
    private int col4_num;
    private int col7_num;

    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_011. ПЗ п. 2.7.3.Требования к СФ. Требования к фильтрам")
    @Link(name="TSE-T4806", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4806")
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

    @Step("TSE-T3692 (1.0) Авторизация в ЛК Клиента. Исполнитель (Пирогов)")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e"); //Пирогов Владимир Константинович
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4800 (1.0) Переход по дереву навигации к справочнику «Почтовые уведомления»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Шаблон листа согласования").waitForLoading(30);
        new MainPage().resetUserSettings().waitForLoading(30);

        resetFilter();
        //Запоминаем первую строку и фильтруем по данным из неё
        new MainPage().clickRowsInList(1).waitForLoading(5);

        col2_num = hlp.colNumberByTitle("Номер шаблона");
        col3_num = hlp.colNumberByTitle("Обрабатываемый документ. Наименование");
        col4_num = hlp.colNumberByTitle("Шаблон по умолчанию");
        col7_num = hlp.colNumberByTitle("Статус");

        col2 = hlp.getSelectedRowTextByColTitle("Номер шаблона");
        col3 = hlp.getSelectedRowTextByColTitle("Обрабатываемый документ. Наименование");
        col4 = hlp.getSelectedRowTextByColTitle("Шаблон по умолчанию");
        col7 = hlp.getSelectedRowTextByColTitle("Статус");

        System.out.println(col2_num + " - " + col2);
        System.out.println(col3_num + " - " + col3);
        System.out.println(col4_num + " - " + col4);
        System.out.println(col7_num + " - " + col7);
    }

    @Step("Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку фильтрации «Номер шаблона»  тестовое значение.\n")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().filterColumnInList("Номер шаблона", col2).waitForLoading(30);
        checkCol(col2_num, col2);
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации «Обрабатываемый документ. Наименование». тестовое значение.")
    public void step04() {
        CommonFunctions.printStep();
        resetFilter();
        new MainPage().filterColumnInList("Обрабатываемый документ. Наименование", col3).waitForLoading(30);
        checkCol(col3_num, col3);
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации «Шаблон по умолчанию». тестовое значение.")
    public void step05() {
        CommonFunctions.printStep();
        resetFilter();
        new MainPage().filterColumnInList("Шаблон по умолчанию", col4).waitForLoading(30);
        checkCol(col4_num, col4);
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации «Статус». тестовое значение.")
    public void step06() {
        CommonFunctions.printStep();
        resetFilter();
        new MainPage().filterColumnInList("Статус", col7).waitForLoading(30);
        checkCol(col7_num, col7);
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
        Assert.assertEquals(filtredRows, totalRows);
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
        Assert.assertEquals(filtredRows, totalRows);
    }

}

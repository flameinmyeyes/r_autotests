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


public class FUN_02_BP_006_NSI_109_PZ_KP_2_7_3 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4995
     * NSI_109. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_109_PZ_KP_2_7_3\\";

    private String col2;
    private String col8;
    private String col4;
    private String col14;
    private String col15;
    private String col18;

    private int col2_colNum;
    private int col4_colNum;
    private int col8_colNum;
    private int col14_colNum;
    private int col15_colNum;
    private int col18_colNum;

    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_109. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T4995", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4995")
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
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4997 (1.0) Переход по дереву навигации к справочнику «Книга регистрации казначейских счетов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Книга регистрации казначейских счетов").waitForLoading(30);
        new MainPage().resetUserSettings().waitForLoading(30);

        new MainPage().clickRowsInList(1).waitForLoading(30);
        col2_colNum = hlp.colNumberByTitle("Наименование казначейского счета");
        col4_colNum = hlp.colNumberByTitle("Наименование клиента");
        col8_colNum = hlp.colNumberByTitle("Номер КС");
        col14_colNum = hlp.colNumberByTitle("Код типа КС");
        col15_colNum = hlp.colNumberByTitle("БИК Банка");
        col18_colNum = hlp.colNumberByTitle("Статус КС");
    }

    @Step("Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку фильтрации \"Наименование казначейского счета\"  тестовое значение.")
    private void step03() {
        CommonFunctions.printStep();
        if (countCol() > 0) {
            col2 = "единый счет бюджета";
            //filterByName("IGK", col2);
            new MainPage().filterColumnInList("Наименование казначейского счета", col2).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col2_colNum, col2);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                //filterByName("IGK", col2);
                new MainPage().filterColumnInList("Наименование казначейского счета", col2).waitForLoading(30);
                checkCol(col2_colNum, col2);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Наименование клиента\" тестовое значение.")
    public void step04() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col4 = "АДМИНИСТРАЦИЯ МУНИЦИПАЛЬНОГО ОКРУГА ХАМОВНИКИ";
            new MainPage().filterColumnInList("Наименование клиента", col4).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col4_colNum, col4);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Наименование клиента", col4).waitForLoading(30);
                checkCol(col4_colNum, col4);
            }
        }
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Номер КС\" тестовое значение.")
    public void step05() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col8 = "03211643453830007304";
            new MainPage().filterColumnInList("Номер КС", col8).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col8_colNum, col8);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Номер КС", col8).waitForLoading(30);
                checkCol(col8_colNum, col8);
            }
        }
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Код тип КС\" тестовое значение.")
    public void step06() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col14 = "08";
            new MainPage().filterColumnInList("Код типа КС", col14).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col14_colNum, col14);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Код типа КС", col14).waitForLoading(30);
                checkCol(col14_colNum, col14);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"БИК Банка\" тестовое значение.")
    public void step07() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col15 = "040702615";
            new MainPage().filterColumnInList("БИК Банка", col15).waitForLoading(30);
            if (countCol() > 0) {
                //checkColnonPusto(col15_colNum);
                checkCol(col15_colNum, col15);
            } else {
                resetFilter();
                nonPusto("AI_BANKBIK");
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("БИК Банка", col15).waitForLoading(30);
                //checkColnonPusto(col15_colNum);
                checkCol(col15_colNum, col15);
            }
        }


    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню.\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Статус КС\" тестовое значение.")
    public void step08() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col18 = "Открыт";
            new MainPage().filterColumnInList("Статус КС", col18).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col18_colNum, col18);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Статус КС", col18).waitForLoading(30);
                checkCol(col18_colNum, col18);
            }
        }
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
    }

    private int countCol() {
        return $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')]")).size();
    }

    private void getSelectedRowData() {
        //Запоминаем выделенную строку для фильтрации
        new MainPage().clickRowsInList(1).waitForLoading(25);
        col2 = hlp.getSelectedRowTextByColTitle("Наименование казначейского счета");
        col4 = hlp.getSelectedRowTextByColTitle("Наименование клиента");
        col8 = hlp.getSelectedRowTextByColTitle("Номер КС");
        col14 = hlp.getSelectedRowTextByColTitle("Код типа КС");
        col15 = hlp.getSelectedRowTextByColTitle("БИК Банка");
        col18 = hlp.getSelectedRowTextByColTitle("Статус КС");
    }

    private void filterByName(String filterfor, String col) {
        if ($(By.xpath("//img[contains(@class,'filterToggler z-image')]")).isDisplayed()) {
            $(By.xpath("//img[contains(@class,'filterToggler z-image')]")).click();
        }
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[@filter-for='" + filterfor + "']"), 30);
        $(By.xpath("//*[@filter-for='" + filterfor + "']")).setValue(col).sendKeys(Keys.ENTER);
        new MainPage().waitForLoading(30);

    }

    private void nonPusto(String filterfor) {
        if ($(By.xpath("//img[contains(@class,'filterToggler z-image')]")).isDisplayed()) {
            $(By.xpath("//img[contains(@class,'filterToggler z-image')]")).click();
        }
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[@filter-for='" + filterfor + "']"), 30);

        $(By.xpath("//*[@filter-for='" + filterfor + "']")).contextClick();
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-menupopup')][contains(@class,'z-menupopup-open')]"), 30);
        CommonFunctions.wait(1);
        $(By.xpath("//body")).sendKeys(Keys.ESCAPE);
        CommonFunctions.wait(1);

        new MainPage().clickWebElement("//div[contains(@class,'z-menupopup-open')]" +
                "//ul[contains(@class,'z-menupopup-content')]/li[contains(.,'не пусто')]", 20).waitForLoading(20);
    }

    private void checkColnonPusto(int colNumber) {
        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]/div[not(normalize-space(.)='')]]")).size();
        System.out.println("Ячеек в колонке №" + colNumber + " всего " + totalRows + " из них " + filtredRows + " не пустые.");
        Assert.assertEquals(filtredRows, totalRows);
    }

}

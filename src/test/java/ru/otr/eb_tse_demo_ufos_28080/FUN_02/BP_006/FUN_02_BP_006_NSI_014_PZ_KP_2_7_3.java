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


public class FUN_02_BP_006_NSI_014_PZ_KP_2_7_3 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2445
     * NSI_014. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_014_PZ_KP_2_7_3\\";

    private String col2;
    private String col8;
    private String col4;
    private String col12;
    private String col14;
    private String col15;
    private String col18;

    private String col2_colName = "Наименование проекта";

    private String group1_colName = "Уровень бюджета";
    private String col4_colName = "Федеральный";
    private String col8_colName = "Бюджет субъекта";
    private String col12_colName = "Муниципальный";

    private String col14_colName = "Дата начала действия записи";
    private String col15_colName = "Дата окончания действия записи";
    private String col18_colName = "Статус";

    private int col2_colNum;
    private int col8_colNum;
    private int col4_colNum;
    private int col12_colNum;
    private int col14_colNum;
    private int col15_colNum;
    private int col18_colNum;

    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_014. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T2445", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2445")
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
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(30);
        new MainPage().resetUserSettings().waitForLoading(30);
        new MainPage().clickRowsInList(1).waitForLoading(30);
        col2_colNum = hlp.colNumberByTitle(col2_colName);
        col4_colNum = hlp.colNumberByTitle(group1_colName, col4_colName);
        col8_colNum = hlp.colNumberByTitle(group1_colName, col8_colName);
        col12_colNum = hlp.colNumberByTitle(group1_colName, col12_colName);
        col14_colNum = hlp.colNumberByTitle(col14_colName);
        col15_colNum = hlp.colNumberByTitle(col15_colName);
        col18_colNum = hlp.colNumberByTitle(col18_colName);

    }

    @Step("Нажать на кнопку фильтрации. Ввести в колонку фильтрации \"Наименование\"  тестовое значение.")
    private void step03() {
        CommonFunctions.printStep();
        if (countCol() > 0) {
            col2 = "Проект";
            new MainPage().filterColumnInList(col2_colName, col2).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col2_colNum, col2);
            } else {
                resetFilter();
                getSelectedRowData();
                new MainPage().filterColumnInList(col2_colName, col2).waitForLoading(30);
                checkCol(col2_colNum, col2);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Федеральный\" тестовое значение.")
    public void step04() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col4 = "Да";
            new MainPage().filterColumnInList(col4_colName, col4).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col4_colNum, col4);
            } else {
                resetFilter();
                getSelectedRowData();
                new MainPage().filterColumnInList(col4_colName, col4).waitForLoading(30);
                checkCol(col4_colNum, col4);
            }
        }
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Бюджет субъекта\" тестовое значение.")
    public void step05() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col8 = "Да";
            new MainPage().filterColumnInList(col8_colName, col8).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col8_colNum, col8);
            } else {
                resetFilter();
                getSelectedRowData();
                new MainPage().filterColumnInList(col8_colName, col8).waitForLoading(30);
                checkCol(col8_colNum, col8);
            }
        }
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Муниципальный\" тестовое значение.")
    public void step06() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col12 = "Да";
            new MainPage().filterColumnInList(col12_colName, col12).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col12_colNum, col12);
            } else {
                resetFilter();
                getSelectedRowData();
                new MainPage().filterColumnInList(col12_colName, col12).waitForLoading(30);
                checkCol(col12_colNum, col12);
            }
        }
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата начала действия записи\" тестовое значение.")
    public void step07() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col14 = CommonFunctions.dateToday("dd.MM.yyyy");
            new MainPage().filterColumnInList(col14_colName, col14).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col14_colNum, col14);
            } else {
                resetFilter();
                getSelectedRowData();
                new MainPage().filterColumnInList(col14_colName, col14).waitForLoading(30);
                checkCol(col14_colNum, col14);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата окончания действия записи\" тестовое значение.")
    public void step08() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col15 = "";
            nonPusto("RECENDDATE");
            checkColnonPusto(col15_colNum);
        }


    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Статус\" тестовое значение.")
    public void step09() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col18 = "Актуальная";

            new MainPage().filterColumnInList(col18_colName, col18).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col18_colNum, col18);
            } else {
                resetFilter();
                getSelectedRowData();
                new MainPage().filterColumnInList(col18_colName, col18).waitForLoading(30);
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
     * Проверка все ли ячейки в колонке содержат указанное значение (без учёта регистра)
     * @param colNumber
     * @param value
     */
    private void checkCol(int colNumber, String value) {
        String tr1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        String tr2 = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        //translate(@BoundId, 'k', 'K')

        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "][normalize-space(translate(., '" + tr1 + "', '" + tr2 + "'))='" + value.toLowerCase() + "']]")).size();
        System.out.println("Ячеек в колонке №" + colNumber + " всего " + totalRows + " из них " + filtredRows + " содержат значение '" + value + "'.");
        Assert.assertEquals(filtredRows, totalRows);
    }



    /**
     * Проверка все ли ячейки в колонке содержат указанную часть значения
     * @param colNumber
     * @param value
     */
    private void checkColContains(int colNumber, String value) {

        String tr1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        String tr2 = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        //translate(@BoundId, 'k', 'K')

        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "]]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')][td[" + colNumber + "][contains(normalize-space(translate(., '" + tr1 + "', '" + tr2 + "')),'" + value.toLowerCase() + "')]]")).size();
        System.out.println("Ячеек в колонке №" + colNumber + " всего " + totalRows + " из них " + filtredRows + " содержат значение '" + value + "'.");
    }

    private int countCol() {
        return $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')]")).size();
    }

    private void getSelectedRowData() {
        //Запоминаем выделенную строку для фильтрации
        new MainPage().clickRowsInList(1).waitForLoading(25);

        col2 = hlp.getSelectedRowTextByColTitle(col2_colName);
        col4 = hlp.getSelectedRowTextByColTitle(group1_colName, col4_colName);
        col8 = hlp.getSelectedRowTextByColTitle(group1_colName, col8_colName);
        col12 = hlp.getSelectedRowTextByColTitle(group1_colName, col12_colName);
        col14 = hlp.getSelectedRowTextByColTitle(col14_colName);
        col15 = hlp.getSelectedRowTextByColTitle(col15_colName);
        col18 = hlp.getSelectedRowTextByColTitle(col18_colName);
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

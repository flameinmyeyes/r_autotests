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


public class FUN_02_BP_006_NSI_015_PZ_KP_2_7_3 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3182
     * NSI_015. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_PZ_KP_2_7_3\\";

    private String col2;
    private String col3;
    private String col4;
    private String col5;
    private String col6;
    private String col7;
    private String col9;
    private String col10;
    private String col11;
    private String col12;
    private String col13;
    private String col14;

    private int col2_colNum;
    private int col3_colNum;
    private int col4_colNum;
    private int col5_colNum;
    private int col6_colNum;
    private int col7_colNum;
    private int col9_colNum;
    private int col10_colNum;
    private int col11_colNum;
    private int col12_colNum;
    private int col13_colNum;
    private int col14_colNum;

    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_015. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T3182", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3182")
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
        step11();
        step12();
        step13();
        step14();
    }

    @Step("TSE-T3692 (1.0) Авторизация в ЛК Клиента.")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4800 (1.0) Переход по дереву навигации к справочнику «Почтовые уведомления»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(30);
        new MainPage().resetUserSettings().waitForLoading(30);

        new MainPage().clickRowsInList(1).waitForLoading(30);
        col2_colNum = hlp.colNumberByTitle("ИГК");
        col3_colNum = hlp.colNumberByTitle("Проект");
        col4_colNum = hlp.colNumberByTitle("Код вида средств");
        col5_colNum = hlp.colNumberByTitle("Наименование вида средств");
        col6_colNum = hlp.colNumberByTitle("Идентификационный код закупки");
        col7_colNum = hlp.colNumberByTitle("Код главы по БК");
        col9_colNum = hlp.colNumberByTitle("Дата начала действия записи");
        col10_colNum = hlp.colNumberByTitle("Дата окончания действия записи");
        col11_colNum = hlp.colNumberByTitle("Статус");
        col12_colNum = hlp.colNumberByTitle("Подлежит приостановлению");
        col13_colNum = hlp.colNumberByTitle("Расходная декларация");
        col14_colNum = hlp.colNumberByTitle("КОО");
    }

    @Step("Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку фильтрации «ИГК»  тестовое значение.\n")
    private void step03() {
        CommonFunctions.printStep();
        if (countCol() > 0) {
            col2 = "00000000000000000000";
            filterByName("IGK", col2);
            //new MainPage().filterColumnInList("ИГК", col2).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col2_colNum, col2);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                filterByName("IGK", col2);
                //new MainPage().filterColumnInList("ИГК", col2).waitForLoading(30);
                checkCol(col2_colNum, col2);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Проект \" тестовое значение.")
    public void step04() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col3 = "Кино";
            new MainPage().filterColumnInList("Проект", col3).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col3_colNum, col3);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Проект", col3).waitForLoading(30);
                checkCol(col3_colNum, col3);
            }
        }
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Код вида средств \" тестовое значение.")
    public void step05() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col4 = "071";
            new MainPage().filterColumnInList("Код вида средств", col4).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col4_colNum, col4);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Код вида средств", col4).waitForLoading(30);
                checkCol(col4_colNum, col4);
            }
        }
    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Наименование вида средств \" тестовое значение.")
    public void step06() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col5 = "3-Признак «Без права расходо-вания на начало го-да»";
            new MainPage().filterColumnInList("Наименование вида средств", col5).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col5_colNum, col5);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Наименование вида средств", col5).waitForLoading(30);
                checkCol(col5_colNum, col5);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Идентификационный код закупки  \" тестовое значение.")
    public void step07() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col6 = "730";
            nonPusto("PURCHASEID");
            //new MainPage().filterColumnInList("Идентификационный код закупки", col6).waitForLoading(30);
            if (countCol() > 0) {
                checkColnonPusto(col6_colNum);
                //checkCol(col6_colNum, col6);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                nonPusto("PURCHASEID");
                //new MainPage().filterColumnInList("Идентификационный код закупки", col6).waitForLoading(30);
                checkColnonPusto(col6_colNum);
                //checkCol(col6_colNum, col6);
            }
        }


    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Код главы по БК   \" тестовое значение.")
    public void step08() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col7 = "730";
            new MainPage().filterColumnInList("Код главы по БК", col7).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col7_colNum, col7);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Код главы по БК", col7).waitForLoading(30);
                checkCol(col7_colNum, col7);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \" Дата начала действия записи   \" тестовое значение.")
    public void step09() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col9 = CommonFunctions.dateShift(CommonFunctions.dateToday("dd.MM.yyyy"), "dd.MM.yyyy", -1);
            new MainPage().filterColumnInList("Дата начала действия записи", col9).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col9_colNum, col9);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Дата начала действия записи", col9).waitForLoading(30);
                checkCol(col9_colNum, col9);
            }
        }

    }


    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата окончания действия записи \" тестовое значение.")
    public void step10() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            nonPusto("PURCHASEID");
            //new MainPage().filterColumnInList("Идентификационный код закупки", col6).waitForLoading(30);
            if (countCol() > 0) {
                checkColnonPusto(col10_colNum);
                //checkCol(col6_colNum, col6);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                nonPusto("DATEFINISH");
                //new MainPage().filterColumnInList("Идентификационный код закупки", col6).waitForLoading(30);
                checkColnonPusto(col10_colNum);
                //checkCol(col6_colNum, col6);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Статус\" тестовое значение.")
    public void step11() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col11 = "Архивная";
            new MainPage().filterColumnInList("Статус", col11).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col11_colNum, col11);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Статус", col11).waitForLoading(30);
                checkCol(col11_colNum, col11);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Подлежит приостановлению \" тестовое значение.")
    public void step12() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col12 = "Да";
            new MainPage().filterColumnInList("Подлежит приостановлению", col12).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col12_colNum, col12);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Подлежит приостановлению", col12).waitForLoading(30);
                checkCol(col12_colNum, col12);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Расходная декларация  \" тестовое значение.")
    public void step13() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col13 = "Да";
            new MainPage().filterColumnInList("Расходная декларация", col13).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col13_colNum, col13);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("Расходная декларация", col13).waitForLoading(30);
                checkCol(col13_colNum, col13);
            }
        }
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"КОО \" тестовое значение.")
    public void step14() {
        CommonFunctions.printStep();
        resetFilter();
        if (countCol() > 0) {
            col14 = "Да";
            new MainPage().filterColumnInList("КОО", col14).waitForLoading(30);
            if (countCol() > 0) {
                checkCol(col14_colNum, col14);
            } else {
                resetFilter();
                new MainPage().clickRowsInList(1).waitForLoading(30);
                getSelectedRowData();
                new MainPage().filterColumnInList("КОО", col14).waitForLoading(30);
                checkCol(col14_colNum, col14);
            }
        }

//        System.out.println("Стою");
//        CommonFunctions.wait(5000);
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
        new MainPage().clickRowsInList(1).waitForLoading(5);
        col2 = hlp.getSelectedRowTextByColTitle("ИГК"); //2
        col3 = hlp.getSelectedRowTextByColTitle("Проект"); //3
        col4 = hlp.getSelectedRowTextByColTitle("Код вида средств"); //4
        col5 = hlp.getSelectedRowTextByColTitle("Наименование вида средств"); //5
        col6 = hlp.getSelectedRowTextByColTitle("Идентификационный код закупки"); //6
        col7 = hlp.getSelectedRowTextByColTitle("Код главы по БК"); //7
        col9 = hlp.getSelectedRowTextByColTitle("Дата начала действия записи"); //9
        col10 = hlp.getSelectedRowTextByColTitle("Дата окончания действия записи"); //10
        col11 = hlp.getSelectedRowTextByColTitle("Статус"); //11
        col12 = hlp.getSelectedRowTextByColTitle("Подлежит приостановлению"); //12
        col13 = hlp.getSelectedRowTextByColTitle("Расходная декларация"); //13
        col14 = hlp.getSelectedRowTextByColTitle("КОО"); //14

        hlp.colNumberByTitle("ИГК");
        System.out.println(col2 + " - " + "ИГК");

        hlp.colNumberByTitle("Проект");
        System.out.println(col3 + " - " + "Проект");

        hlp.colNumberByTitle("Код вида средств");
        System.out.println(col4 + " - " + "Код вида средств");

        hlp.colNumberByTitle("Наименование вида средств");
        System.out.println(col5 + " - " + "Наименование вида средств");

        hlp.colNumberByTitle("Идентификационный код закупки");
        System.out.println(col6 + " - " + "Идентификационный код закупки");

        hlp.colNumberByTitle("Код главы по БК");
        System.out.println(col7 + " - " + "Код главы по БК");

        hlp.colNumberByTitle("Дата начала действия записи");
        System.out.println(col9 + " - " + "Дата начала действия записи");

        hlp.colNumberByTitle("Дата окончания действия записи");
        System.out.println(col10 + " - " + "Дата окончания действия записи");

        hlp.colNumberByTitle("Статус");
        System.out.println(col11 + " - " + "Статус");

        hlp.colNumberByTitle("Подлежит приостановлению");
        System.out.println(col12 + " - " + "Подлежит приостановлению");

        hlp.colNumberByTitle("Расходная декларация");
        System.out.println(col13 + " - " + "Расходная декларация");

        hlp.colNumberByTitle("КОО");
        System.out.println(col14 + " - " + "КОО");
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

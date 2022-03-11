package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
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
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_069_PZ_2_7_3 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_069_PZ_2_7_2\\";
    public static String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    private static Date date;

    @Owner(value = "Ворожко Александр")
    @Description("NSI_069. ПЗ п. 2.7.3. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T3167", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3167")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        WAY_DOWNLOADS = setWay(WAY_DOWNLOADS);

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
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Перечень КБК, допущенных к проведению операций по кассовым выплатам».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Перечень КБК, допущенных к проведению операций по кассовым выплатам в период приостановления операций на лицевом счете")
                .resetUserSettings();
    }

    @Step("Нажать на кнопку фильтрации. Ввести в колонку фильтрации \"Номер Л/С\"  тестовое значение.")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Номер л/с", "03601863340");
        checkCol(2, "03601863340");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Шаблон КБК\" тестовое")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Шаблон КБК", "%111");
        checkCol(3, "%111");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Код ТОФК (где введен)\" тестовое")
    public void step05() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Код ТОФК (где введен)", "6000");
        checkCol(4, "6000");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Наименование ТОФК (где введен)\" тестовое")
    public void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Наименование ТОФК (где введен)", "Управление Федерального казначейства по Саратовской области");
        checkCol(5, "Управление Федерального казначейства по Саратовской области");
        resetFilter();
    }

    @Step("Выбрать запись справочника. Нажать кнопку \"Просмотреть\"")
    public void step07() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Дата действия с", "05.05.2021");
        checkCol(6, "05.05.2021");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Статус записи\" тестовое")
    public void step08() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Статус записи", "Актуальная");
        checkCol(8, "Актуальная");
    }

    private void checkCol(int colNumber, String value) {
        int totalRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')]/td[" + colNumber + "]")).size();
        int filtredRows = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')]/td[" + colNumber + "][descendant::*[contains(text(), '" + value + "')]]")).size();
        System.out.println("Ячеек в колонке №" + colNumber + " всего " + totalRows + " из них " + filtredRows + " содержат значение '" + value + "'.");
        Assert.assertEquals(filtredRows, totalRows);
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
}

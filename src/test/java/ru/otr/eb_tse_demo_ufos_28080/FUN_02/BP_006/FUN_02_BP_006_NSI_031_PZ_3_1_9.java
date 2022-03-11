package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
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
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


////AC
public class FUN_02_BP_006_NSI_031_PZ_3_1_9 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_3_1_9\\";

    @Owner(value = "Ворожко Александр")
    @Description("NSI_031. ПЗ п. 1.9. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T3232", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3232")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
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
    }

    @Step("Авторизация в ЛК ТОФК Обращения. Исполнитель ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Настройки доступа ТОФК по месту обращения")
                .resetUserSettings();
    }

    @Step("Нажать на кнопку фильтрации. Ввести в колонку фильтрации \"Код ТОФК\"  тестовое значение 7300.")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Код ТОФК", "7300");
        checkCol(2, "7300");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Код СВР ТОФК\" тестовое значение 00132269.")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Код СВР ТОФК", "00132269");
        checkCol(3, "00132269");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Наименование ТОФК\" тестовое значение УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО Г. МОСКВЕ.")
    public void step05() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Наименование ТОФК", "УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО Г. МОСКВЕ");
        checkCol(4, "УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО Г. МОСКВЕ");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"ИНН Клиента\" тестовое значение 7722698789.")
    public void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("ИНН Клиента", "7722698789");
        checkCol(5, "7722698789");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Код СВР / ИП и КФХ Клиента\" тестовое значение 001Э2344.")
    public void step07() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Код СВР / ИП и КФХ Клиента", "001Э2344");
        checkCol(6, "001Э2344");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Полное наименование клиента\" тестовое значение АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\".")
    public void step08() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Полное наименование Клиента", "АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\"");
        checkCol(7, "АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\"");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата начала действия записи\" тестовое значение 18.08.2020.")
    public void step09() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Дата начала действия записи", "18.08.2020");
        checkCol(8, "18.08.2020");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата окончания действия записи\" тестовое 24.08.2020")
    public void step10() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Дата окончания действия записи", "24.08.2020");
        checkCol(9, "24.08.2020");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Статус\" тестовое значение.")
    public void step11() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Статус", "Актуальная");
        checkCol(10, "Актуальная");
        resetFilter();
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

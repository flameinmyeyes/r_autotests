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
public class FUN_02_BP_006_NSI_031_PZ_1_8 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_1_5\\";

    @Owner(value = "Ворожко Александр")
    @Description("NSI_023. ПЗ п. 1.8. Требования к СФ. Требования к фильтрам спр-ка")
    @Link(name="TSE-T3206", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3206")

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
    }

    @Step("Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Причины отклонения документов")
                .resetUserSettings();
    }

    @Step("Ввести в колонку фильтрации \"Код\"  тестовое значение.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Код", "01");
        checkCol(2, "01");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Виды причин отказа\" тестовое значение.")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Виды причин отказа", "Некорректное оформление документов");
        checkCol(3, "Некорректное оформление документов");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата действия с\" тестовое значение.")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Дата действия с", "25.06.2020");
        checkCol(4, "25.06.2020");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Дата действия по\" тестовое значение.")
    private void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Дата действия по", "03.08.2020");
        checkCol(5, "03.08.2020");
        resetFilter();
    }

    @Step("Сбросить фильтрацию СФ, через кнопку \"Колонки по умолчанию\" в контекстном меню (появляется при нажатии ПКМ по полю фильтрации колонок).\n" +
            "Нажать на кнопку фильтрации.\n" +
            "Ввести в колонку  фильтрации \"Статус\" тестовое значение.")
    private void step07() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Статус", "Актуальная");
        checkCol(6, "Актуальная");
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

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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;


public class FUN_02_BP_006_NSI_023_PZ_1_6 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_6\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_023. ПЗ п. 1.6. Требования к СФ. Требования к отоб-нию реквизитов в списковой форме спр-ка")
    @Link(name="TSE-T3204", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3204")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов");
    }

    @Step("Проверить отображение колонок на СФ")
    private void step03() {
        CommonFunctions.printStep();
        //Есть колонки:
        //"Код"
        //"Виды причин отказа"
        //"Дата действия с"
        //"Дата действия по"
        //"Статус"

        new MainPage().resetUserSettings();

        SoftAssert a = new SoftAssert();
        onListForm(a, "Код", "Да");
        onListForm(a, "Виды причин отказа", "Да");
        onListForm(a, "Дата действия с", "Да");
        onListForm(a, "Дата действия по", "Да");
        onListForm(a, "Статус", "Да");

        a.assertAll();
    }

    /**
     * Проверить отображение колонки на СФ.
     *
     * @param a           softAssert
     * @param th          Название колонки
     * @param isDisplayed Должна ли отображаться колонка? ("Да"/"Нет")
     */
    private void onListForm(SoftAssert a, String th, String isDisplayed) {
        String thXPath = "//th/div[text() = '" + th + "']";
        a.assertTrue($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" существует - ");
        thXPath = thXPath.replace("//th", "//th[not(contains(@style, 'hidden'))]");
        if (isDisplayed.equals("Да")) {
            a.assertTrue($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" отображается -");
        } else if (isDisplayed.equals("Нет")) {
            a.assertFalse($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" не отображается -");
        } else {
            Assert.fail("Неверно выбрано условие. Значение exist должно быть равно 'Да' или 'Нет'");
        }
    }

    /**
     * Проверить отображение колонки на СФ c учетом идентификатора колонки.
     *
     * @param a           Экземпляр класса softAssert
     * @param fromidx     Номер колонки
     * @param th          Название колонки
     * @param isDisplayed Должна ли отображаться колонка? ("Да"/"Нет")
     */
    private void onListForm(SoftAssert a, String th, int fromidx, String isDisplayed) {
        String thXPath = "//th[@fromidx = '" + fromidx + "']/div[text() = '" + th + "']";
        a.assertTrue($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" существует - ");
        thXPath = thXPath.replace("//th", "//th[not(contains(@style, 'hidden'))]");
        if (isDisplayed.equals("Да")) {
            a.assertTrue($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" отображается -");
        } else if (isDisplayed.equals("Нет")) {
            a.assertFalse($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" не отображается -");
        } else {
            Assert.fail("Неверно выбрано условие. Значение exist должно быть равно 'Да' или 'Нет'");
        }
    }

}

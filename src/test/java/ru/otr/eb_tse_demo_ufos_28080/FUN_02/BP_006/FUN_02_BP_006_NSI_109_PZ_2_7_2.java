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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.FUN_08.BP_021.FUN_08_BP_021_MT_14_KP_1_1;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;


////AC
public class FUN_02_BP_006_NSI_109_PZ_2_7_2 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4994
     * NSI_109. ПЗ п. 2.7.2. Требования к СФ. Требования к действиям из списковой форм
     * задача:
     * Проверить кнопки "Открыть","Обновить"
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_109_PZ_2_7_2\\";
    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();
    private String selectedDocNum;
    private String selectedDocDate;

    @Owner(value = "Якубов Алексей")
    @Description("NSI_109. ПЗ п. 2.7.2. Требования к СФ. Требования к действиям из списковой форм")
    @Link(name="TSE-T4994", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4994")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})

    public void steps() {
        WAY_TEST = setWay(WAY_TEST);

        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4997 (1.0) Переход по дереву навигации к справочнику «Книга регистрации казначейских счетов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(60);
        new MainPage().resetUserSettings().waitForLoading(30);
    }

    @Step("Нажать кнопу \"Обновить\"")
    public void step03() {
        CommonFunctions.printStep();
        Assert.assertFalse($(By.xpath("//tr[contains(@class,'z-listitem-selected')]")).exists());
        new MainPage().clickRowsInList(1).waitForLoading(10);

        selectedDocNum = hlp.getSelectedRowTextByColTitle("Наименование проекта");
        selectedDocDate = hlp.getSelectedRowTextByColTitle("Дата начала действия записи");

        System.out.println("Выделен документ " + selectedDocNum + " от " + selectedDocDate);

        Assert.assertTrue($(By.xpath("//tr[contains(@class,'z-listitem-selected')][contains(.,'" + selectedDocNum + "')][contains(.,'" + selectedDocDate + "')]")).exists());

        new MainPage().clickWebElement("//button[@title='Обновить список документов (Alt+F5)'][not(disabled)]").waitForLoading(10);
        Assert.assertFalse($(By.xpath("//tr[contains(@class,'z-listitem-selected')]")).exists());
    }

    @Step("Выбрать запись справочника  «Книга регистрации казначейских счетов»")
    public void step04() {
        CommonFunctions.printStep();
        Assert.assertFalse($(By.xpath("//tr[contains(@class,'z-listitem-selected')]")).exists());
        new MainPage().clickRowsInList(1).waitForLoading(10);
        Assert.assertTrue($(By.xpath("//tr[contains(@class,'z-listitem-selected')][contains(.,'" + selectedDocNum + "')][contains(.,'" + selectedDocDate + "')]")).exists());
    }

    @Step("Нажать кнопу \"Просмотреть\"")
    public void step05() {
        CommonFunctions.printStep();
        new MainPage().clickWebElement("//button[@title='Открыть документ на просмотр'][not(disabled)]").waitForLoading(10);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-view-pane')]//*[@name='tf_dict_mi_projectname'][@value='" + selectedDocNum + "']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-view-pane')]//*[@name='ta_dict_mi_note']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-view-pane')]//*[@name='dtf_dict_snd_datedocstart'][@value='" + selectedDocDate + "']"), 60);
    }
}

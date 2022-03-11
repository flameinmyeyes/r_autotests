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
public class FUN_02_BP_006_NSI_015_PZ_2_8_1 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3670
     * NSI_015. ПЗ п. 2.8.1 Макет краткой формы справочника
     * задача:
     * Проверить КФ  записи справочника
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_PZ_2_8_1\\";
    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_015. ПЗ п. 2.8.1 Макет краткой формы справочника")
    @Link(name="TSE-T3670", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3670")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})

    public void steps() {
        WAY_TEST = setWay(WAY_TEST);

        step01();
        step02();
        step03();
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

    @Step("TSE-T4516 (1.0) Переход по дереву навигации к справочнику «ИГК»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(60);
        new MainPage().resetUserSettings().waitForLoading(30);
    }

    @Step("Выбрать запись на статусе \"Актуальная\"")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage().filterColumnInList("Статус", "Актуальная").waitForLoading(10);
        new MainPage().clickRowsInList(1).waitForLoading(30);
        CommonFunctions.wait(5);

        if ($(By.xpath("//a[contains(@class,'z-toolbarbutton')][contains(@class,'z-toolbarbutton-checked')]")).isDisplayed()) {
            $(By.xpath("//a[contains(@class,'z-toolbarbutton')][contains(@class,'z-toolbarbutton-checked')]")).click();
        }

        //Основная информация
        String tf_dict_igk = hlp.getSelectedRowTextByColTitle("ИГК"); //Идентификационный номер госконтракта
        hlp.checkField("Идентификационный номер госконтракта", "tf_dict_igk", tf_dict_igk);

        String tf_dict_project = hlp.getSelectedRowTextByColTitle("Проект"); //Проект
        hlp.checkField("Проект", "tf_dict_project", tf_dict_project);

        String tf_dict_typeresourcecode = hlp.getSelectedRowTextByColTitle("Код вида средств"); //Код вида средств
        hlp.checkField("Код вида средств", "tf_dict_typeresourcecode", tf_dict_typeresourcecode);

        String ta_dict_name = hlp.getSelectedRowTextByColTitle("Наименование вида средств"); //Наименование вида средств
        hlp.checkField("Наименование вида средств", "ta_dict_name", ta_dict_name);

        String tf_dict_purchaseid = hlp.getSelectedRowTextByColTitle("Идентификационный код закупки"); //Идентификационный код закупки
        hlp.checkField("Идентификационный код закупки", "tf_dict_purchaseid", tf_dict_purchaseid);

        String tf_dict_grbscode = hlp.getSelectedRowTextByColTitle("Код главы по БК"); //Код главы по БК
        hlp.checkField("Код главы по БК", "tf_dict_grbscode", tf_dict_grbscode);

        String chb_dict_muststop = hlp.getSelectedRowTextByColTitle("Подлежит приостановлению"); //Подлежит приостановлению (Чекбокс checked если Да, '' если Нет)
        if (chb_dict_muststop.equals("Да")) {
            CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='chb_dict_muststop'][@checked]/ancestor::span[1]"), 30);
        }
        if (chb_dict_muststop.equals("Нет")) {
            CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='chb_dict_muststop'][not(@checked)]/ancestor::span[1]"), 30);
        }

        String chb_dict_kassexpenses = hlp.getSelectedRowTextByColTitle("Расходная декларация"); //Расходная декларация (Чекбокс checked если Да, '' если Нет)
        if (chb_dict_kassexpenses.equals("Да")) {
            CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='chb_dict_kassexpenses'][@checked]/ancestor::span[1]"), 30);
        }
        if (chb_dict_kassexpenses.equals("Нет")) {
            CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='chb_dict_kassexpenses'][not(@checked)]/ancestor::span[1]"), 30);
        }

        //Статус и даты действия
        String dtf_dict_recstartdate = hlp.getSelectedRowTextByColTitle("Дата начала действия записи"); //Дата начала действия записи
        hlp.checkField("Дата начала действия записи", "dtf_dict_recstartdate", dtf_dict_recstartdate);

        String dtf_dict_recenddate = hlp.getSelectedRowTextByColTitle("Дата окончания действия записи"); //Дата окончания действия записи
        hlp.checkField("Дата окончания действия записи", "dtf_dict_recenddate", dtf_dict_recenddate);

        String tf_dict_status = hlp.getSelectedRowTextByColTitle("Статус"); //Статус
        hlp.checkField("Статус", "tf_dict_status", tf_dict_status);





//        System.out.println("Стою");
//        CommonFunctions.wait(5000);





//        System.out.println("Выделен документ " + selectedDocNum + " от " + selectedDocDate);
//
//        Assert.assertTrue($(By.xpath("//tr[contains(@class,'z-listitem-selected')][contains(.,'" + selectedDocNum + "')][contains(.,'" + selectedDocDate + "')]")).exists());
//
//        new MainPage().clickWebElement("//button[@title='Обновить список документов (Alt+F5)'][not(disabled)]").waitForLoading(10);
//        Assert.assertFalse($(By.xpath("//tr[contains(@class,'z-listitem-selected')]")).exists());
    }

}

package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;


public class FUN_02_BP_006_MT_8_KP_1 extends HooksTSE_DEMO_28080 {

    /**
     * НУЖЕН FUN_01_BP_003_MT_1_KP_5_1. Слишком сложный тест. Решено понизить приоритет
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3545
     * Проверка работоспособности справочника «ИГК» в ПУР КС
     * задача:
     * Контрольный пример должен продемонстрировать, что в ПУР КС корректно реализован ссылка на головной контракт в справочнике «ИГК».
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_KP_1\\";

    public String docNumber51;

    public String docNumber51_date2;
    public String docNumber51_docnum2;
    public String docNumber51_link2;


    public String igk;
    public String project;
    public String kodVidaSretstv;
    public String naimVidaSredstv;
    public String kodzakupki;
    public String kodGlavi;
    public String dateFrom;
    public String dateTo;
    public String status;


    public Helpers helper = new Helpers();

    @Owner(value = "Якубов Алексей")
    @Description("МТ 8 КП 1 Проверка работоспособности справочника «ИГК» в ПУР КС")
    //@Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    @Test(groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    @Link(name = "TSE-T3545", url = "https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3545")


    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        //System.out.println("Автотест отработал");
        //docNumber51 = FileFunctions.readValueFile(setWay(Ways.TSE_DEMO_28080.getWay() + "\\FUN_01\\BP_003\\FUN_01_BP_003_MT_1_KP_5_1\\MainDocumentNumber.txt"));

        docNumber51_date2 = FileFunctions.readValueFile(setWay(Ways.TSE_DEMO_28080.getWay() + "\\FUN_01\\BP_003\\FUN_01_BP_003_MT_1_KP_5_1\\startDate2.txt"));
        docNumber51_docnum2 = FileFunctions.readValueFile(setWay(Ways.TSE_DEMO_28080.getWay() + "\\FUN_01\\BP_003\\FUN_01_BP_003_MT_1_KP_5_1\\NumDoc2.txt"));

        docNumber51_link2 = "№ " + docNumber51_docnum2 + "  от " + CommonFunctions.dateReFormat(docNumber51_date2, "dd.MM.yyyy", "yyyy-MM-dd"); //№ AT5695 от 2021-07-02
        System.out.println("Номер документа основания  " + docNumber51_docnum2);
        System.out.println("Дата начала действия " + docNumber51_date2);
        System.out.println("Ссылка на головной контракт " + docNumber51_link2);


        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна //Loboda.KI
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4516 (1.0) Переход по дереву навигации к справочнику \"ИГК\"")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(30);
        new MainPage().resetUserSettings()
                .waitForLoading(30);
    }

    @Step("Проверить что в справочнике появилась запись")
    public void step03() {
        CommonFunctions.printStep();

        //new MainPage().filterColumnInList("Ссылка на головной контракт", "*" + docNumber51_date2 + "*");

        //new MainPage().filterColumnInList("Проект", "Проект");
        //new MainPage().filterColumnInList("Код вида средств", "071");
        //new MainPage().filterColumnInList("Код главы по БК", "730");

        new MainPage().filterColumnInList("Дата начала действия записи", docNumber51_date2);
        $(By.xpath("//input[@filter-for='TECH_GUIDPDO']")).sendKeys("*" + docNumber51_docnum2 + "*");
        $(By.xpath("//input[@filter-for='TECH_GUIDPDO']")).pressEnter();
        new MainPage().waitForLoading(10);

    }

    @Step("Проверить ссылку в колонке «Ссылка на головной контракт».")
    public void step04() {
        CommonFunctions.printStep();
        System.out.println("Выделяю строку со ссылкой " + docNumber51_link2);
        new MainPage().clickRowsInList(1);
        System.out.println("Кликаю по ссылке " + docNumber51_link2);
        $(By.xpath("//table//tr/td[contains(@class,'z-listcell')][contains(.,'" + docNumber51_link2 + "')]")).click();

        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='dtf_doc_bre_docdate'][@value='" + docNumber51_date2 + "']"), 20);
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='tf_doc_bad_docnum'][@value='" + docNumber51_docnum2 + "']"), 20);
        new MainPage().clickButtonTitle("Закрыть окно").waitForLoading(20);


    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step05() {
        CommonFunctions.printStep();
        igk = helper.getSelectedRowTextByColTitle("ИГК");
        project = helper.getSelectedRowTextByColTitle("Проект");
        kodVidaSretstv = helper.getSelectedRowTextByColTitle("Код вида средств");
        naimVidaSredstv = helper.getSelectedRowTextByColTitle("Наименование вида средств");
        kodzakupki = helper.getSelectedRowTextByColTitle("Идентификационный код закупки");
        kodGlavi = helper.getSelectedRowTextByColTitle("Код главы по БК");
        dateFrom = helper.getSelectedRowTextByColTitle("Дата начала действия записи");
        dateTo = helper.getSelectedRowTextByColTitle("Дата окончания действия записи");
        status = helper.getSelectedRowTextByColTitle("Статус");


        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@title='Открыть документ на просмотр'][not(@disabled)]"),20);
        $(By.xpath("//button[@title='Открыть документ на просмотр'][not(@disabled)]")).click();
    }

    @Step("Проверить заполнение полей \"Идентификационный номер госконтракта \", \"Проект\", \"Код вида средств\", \"Наименование вида средств\", \"Идентификационный код закупки\", \"Код главы по БК\", \"Дата начала действия записи\", \"Дата окончания действия записи\", \"Статус\", \"Подлежит приостановлению\", \"Расходная декларация\", \" Признак  КОО\"")
    public void step06() {
        CommonFunctions.printStep();
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]//*[@name='tf_dict_igk']"), 20);

        //new MainPage().clickButtonTitle("Открыть документ на просмотр").waitForLoading(60);

        String containerPath = "//div[contains(@class,'dialog-edit')]";

        //Тут падает.

        helper.checkFieldByName(containerPath, "tf_dict_igk", igk); //Идентификационный номер госконтракта:
        helper.checkFieldByName(containerPath, "tf_dict_project", project); //Проект:
        helper.checkFieldByName(containerPath, "tf_dict_typeresourcecode", kodVidaSretstv); //Код вида средств:
        helper.checkFieldByName(containerPath, "tf_dict_typeresourcename", naimVidaSredstv); //Наименование вида средств:
        helper.checkFieldByName(containerPath, "tf_dict_purchaseid", kodzakupki); //Идентификационный код закупки:
        helper.checkFieldByName(containerPath, "tf_dict_grbscode", kodGlavi); //Код главы по БК:

        helper.checkCheckboxByName(containerPath, "chb_dict_muststop", true);
        helper.checkCheckboxByName(containerPath, "chb_dict_kassexpenses", true);
        helper.checkCheckboxByName(containerPath, "chb_dict_signkoo", true);

        helper.checkFieldByName(containerPath, "dtf_dict_recstartdate", dateFrom); //Дата начала действия записи:
        //helper.checkFieldByName(containerPath, "dtf_dict_recenddate", dateTo); //Дата окончания действия записи:
        helper.checkFieldByName(containerPath, "tf_dict_status", status); //Статус

    }

    @Step("Проверить отображение «Ссылка на головной контракт».")
    public void step07() {
        CommonFunctions.printStep();
        String link2 = "№ " + docNumber51_docnum2 + " от " + docNumber51_date2; //№ AT5695 от 2021-07-02
        String containerPath = "//div[contains(@class,'doc-edit-frame')]";

        System.out.println(link2);
        System.out.println($(By.xpath(containerPath + "//span[contains(@class,'document-link')]")).getText());

        By link_x = By.xpath(containerPath + "//span[contains(@class,'document-link')][contains(.,'" + link2 + "')]");
        $(link_x).click();
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='dtf_doc_bre_docdate'][@value='" + docNumber51_date2 + "']"), 20);
        CommonFunctions.waitForElementDisplayed(By.xpath("//input[@name='tf_doc_bad_docnum'][@value='" + docNumber51_docnum2 + "']"), 20);
    }
}

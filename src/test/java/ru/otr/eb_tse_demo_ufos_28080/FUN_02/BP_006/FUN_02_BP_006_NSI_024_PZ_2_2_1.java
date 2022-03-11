package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.FUN_08.BP_021.FUN_08_BP_021_MT_14_KP_1_1;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Instant;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class FUN_02_BP_006_NSI_024_PZ_2_2_1 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3211
     * NSI_024. ПЗ п. 2.2. Способы формирования спр-ка пользователем  601_01_01 ПУР КС. Ввод документов по ЛС ЮЛ
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_2_1\\";
    int strCounter = 0;

    private FUN_08_BP_021_MT_14_KP_1_1 hlp = new FUN_08_BP_021_MT_14_KP_1_1();

    @Owner(value = "Якубов Алексей")
    @Description("NSI_024. ПЗ п. 2.2. Способы формирования спр-ка пользователем  601_01_01 ПУР КС. Ввод документов по ЛС ЮЛ")
    @Link(name="TSE-T3211", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3211")
    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    public void steps() {
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

    @Step("TSE-T3692 (1.0) Авторизация в ЛК Клиента. Исполнитель (Пирогов)")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e"); //Пирогов Владимир Константинович
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления").waitForLoading(30);
        new MainPage().resetUserSettings().waitForLoading(30);
    }

    @Step("TSE-T3702 (1.0) Создание формуляра")
    private void step03() {
        CommonFunctions.printStep();

        new MainPage().filterColumnInList("Дата начала действия записи", CommonFunctions.dateToday("dd.MM.yyyy")).waitForLoading(20);

        strCounter = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')]")).size();
        System.out.println("Вначале теста документов с текущей датой найдено: " + strCounter);

        new MainPage().clickWebElement("//button[@title='Создать новый документ (Alt+N)'][not(@disabled)]");
        CommonFunctions.waitForElementDisplayed(By.xpath("//table[contains(@class,'docInfoDiv')]//span[text()='Почтовые уведомления']"),30);
    }

    @Step("Проверить заполнение полей в  разделе \"Информация о клиенте\" и поле \"Дата начала действия записи\", поле \"Статус\"")
    public void step04() {
        CommonFunctions.printStep();
        hlp.checkField("Код СВР/НУБП", "tf_dict_svrcode", "450Э7888"); //Поле "Код СВР/НУБП" заполнено значением 450Э7888
        hlp.checkField("ИНН", "tf_dict_inn", "7722698789"); //Поле "ИНН" заполнено значением 7722698789
        hlp.checkField("КПП", "tf_dict_kpp", "772201001"); //Поле "КПП" заполнено значением 772201001
        hlp.checkField("Полное наименование", "ta_dict_lognaame", "АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\""); //Поле "Полное наименование" заполнено значением АКЦИОНЕРНОЕ ОБЩЕСТВО "РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ"
        hlp.checkField("Дата начала действия записи", "dtf_dict_datebegin", CommonFunctions.dateToday("dd.MM.yyyy")); //поле "Дата начала действия записи" заполнено значением Текущая дата
        hlp.checkField("Статус", "tf_docstate_name", "Новая"); //поле "Статус" заполнено значением Статус

    }


    @Step("Выбрать в поле \"Наименование документа\" в открывавшемся окне выбрать Тип документа")
    public void step05() {
        CommonFunctions.printStep();
        new MainPage().clickWebElement("//button[@name='btn_subDocAction.showSubDoc-f36e'][not(@disabled)]");
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'doc-dialog-window')][contains(.,'Типы документов')]"),30);
        new DocPage().filterColumnInDictionary("Наименование", "Документ-основание").waitForLoading(30);
        new DocPage().clickRowInDictionary("D02");

        hlp.checkField("Наименование документа", "tf_dict_docname", "Документ-основание"); //Поле "Наименование документа" заполнено значением Документ-основание
        hlp.checkField("Код документа", "tf_dict_doccode", "TSE_ApplInfrmRDO_D02"); //Поле "Код документа" заполнено значением TSE_ApplInfrmRDO_D02
    }

    @Step("Нажимаем добавить новую запись при разделе \"Адресаты\", заполнить поля Электронная почта, ФИО пользователя ,Логин пользователя . Нажать кнопку \"ОК\"")
    public void step06() {
        CommonFunctions.printStep();
        new MainPage().clickWebElement("//button[@title='Добавить новую строку (Ins)'][not(@disabled)]", 90);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'subdoc-edit-dialog')][contains(.,'Добавление записи')]"),30);

        hlp.fillField("//div[contains(@class,'subdoc-edit-dialog')]", "Электронная почта", "tf_mail", "grachenova.tatyana@mail.ru");
        hlp.fillField("//div[contains(@class,'subdoc-edit-dialog')]", "ФИО пользователя", "tf_fiorecipient", "grachenova.tatyana");
        hlp.fillField("//div[contains(@class,'subdoc-edit-dialog')]", "Логин пользователя", "tf_login", "grachenova.tatyana");
        new MainPage().clickWebElement("//button[text()='Ok'][not(@disabled)]");


        docAdresTh("ФИО получателя", 3);
        docAdresTd("grachenova.tatyana", 3);

        docAdresTh("Логин пользователя", 4);
        docAdresTd("grachenova.tatyana", 4);

        docAdresTh("e-mail", 5);
        docAdresTd("grachenova.tatyana@mail.ru", 5);

        docAdresTh("В копию", 6);
        docAdresTd("Нет", 6);

    }

    @Step("Нажимаем добавить новую запись при разделе \"Адресаты\", заполнить поля Электронная почта, ФИО пользователя ,Логин пользователя , В копию выбрать . Нажать кнопку \"ОК\"")
    private void step07() {
        CommonFunctions.printStep();
        new MainPage().clickWebElement("//button[@title='Добавить новую строку (Ins)'][not(@disabled)]", 90);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'subdoc-edit-dialog')][contains(.,'Добавление записи')]"),30);

        hlp.fillField("//div[contains(@class,'subdoc-edit-dialog')]", "Электронная почта", "tf_mail", "Grachenova.Tatyana@mail.ru");
        hlp.fillField("//div[contains(@class,'subdoc-edit-dialog')]", "ФИО пользователя", "tf_fiorecipient", "Grachenova.Tatyana");
        hlp.fillField("//div[contains(@class,'subdoc-edit-dialog')]", "Логин пользователя", "tf_login", "Grachenova.Tatyana");

        new MainPage().clickWebElement("//div[contains(@class,'subdoc-edit-dialog')]//div[contains(@class,'advcheckbox-default')]/ancestor::span[1]");
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'subdoc-edit-dialog')]//div[contains(@class,'advcheckbox-default')][contains(@class,'advcheckbox-checked')]"), 20);

        new MainPage().clickWebElement("//button[text()='Ok'][not(@disabled)]");

        docAdresTh("ФИО получателя", 3);
        docAdresTd("Grachenova.Tatyana", 3);

        docAdresTh("Логин пользователя", 4);
        docAdresTd("Grachenova.Tatyana", 4);

        docAdresTh("e-mail", 5);
        docAdresTd("Grachenova.Tatyana@mail.ru", 5);

        docAdresTh("В копию", 6);
        docAdresTd("Да", 6);

        //Вытащить и сохранить дату начала действия записи
        String startDate = $(By.xpath("//input[@name='dtf_dict_datebegin']")).getAttribute("value");
        FileFunctions.writeValueFile(WAY_TEST + "startDate.txt", startDate);

        //Сохранить гуид
        new DocPage()
                .clickButtonTitle("Информация о документе")
                .waitForLoading(60);
        String docGuid = $x("(//span[text()='Идентификатор']/../../..//span)[2]").getText();
        FileFunctions.writeValueFile(WAY_TEST + "docGuid.txt", docGuid);
        new DocPage().clickWebElement("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//div[@class='z-window-close']");
    }

    @Step("Нажать кнопку «Сохранить изменения и закрыть окно».")
    public void step08() {
        CommonFunctions.printStep();
        new MainPage().clickWebElement("//button[@title='Сохранить изменения и закрыть окно (Alt+S)'][not(@disabled)]").waitForLoading(30);

        int strCounterLast = $(By.xpath("//div[contains(@class,'doc-browse-pane')]")).findElements(By.xpath("//tr[contains(@class,'z-listitem')]")).size();
        System.out.println("В конце теста документов с текущей датой найдено: " + strCounterLast);

        Assert.assertEquals(strCounterLast, strCounter + 1);
    }


    private void docAdresTh(String colTitle, int colNumber) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//table[contains(@class,'docInfoDiv')]/ancestor::div[contains(@class,'z-window-content')][1]//div[contains(@class,'z-listbox-header')]//tr//th[" + colNumber + "][contains(.,'" + colTitle + "')]"),10);
    }

    private void docAdresTd(String colTitle, int colNumber) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//table[contains(@class,'docInfoDiv')]/ancestor::div[contains(@class,'z-window-content')][1]//tr[contains(@class,'z-listitem-selected')][contains(@title,'Выделено')]/td[" + colNumber + "][contains(.,'" + colTitle + "')]"),10);
    }
}

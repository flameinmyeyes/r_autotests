package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_024_PZ_2_2_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_2_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.2(2). Способы формирования спр-ка пользователем  603_01_02 ПУР КС. Исполнитель ЦС ФК по обработке документов по ЛС ЮЛ")
    @Link(name="TSE-T3212", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3212")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
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

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку «Создать» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Проверить заполнение полей в  разделе \"Информация о клиенте\" и поле \"Дата начала действия записи\", поле \"Статус\"")
    private void step04() {
        CommonFunctions.printStep();
        //Поле "Код СВР/НУБП" заполнено значением 00132269
        Assert.assertEquals($x("//input[@name='tf_dict_svrcode']").getAttribute("value"), "00132269");
        //Поле "ИНН" заполнено значением 7725074789
        Assert.assertEquals($x("//input[@name='tf_dict_inn']").getAttribute("value"), "7725074789");
        //Поле "КПП" заполнено значением 772501001
        Assert.assertEquals($x("//input[@name='tf_dict_kpp']").getAttribute("value"), "772501001");
        //Поле "Полное наименование" заполнено значением УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО Г. МОСКВЕ
        Assert.assertEquals($x("//textarea[@name='ta_dict_lognaame']").getAttribute("value"), "УПРАВЛЕНИЕ ФЕДЕРАЛЬНОГО КАЗНАЧЕЙСТВА ПО Г. МОСКВЕ");
        //поле "Дата начала действия записи" заполнено значением Текущая дата
        Assert.assertEquals($x("//input[@name='dtf_dict_datebegin']").getAttribute("value"), CommonFunctions.dateToday("dd.MM.yyyy"));
        //поле "Статус" заполнено значением Статус
        Assert.assertEquals($x("//input[@name='tf_docstate_name']").getAttribute("value"), "Новая");
    }

    @Step("Выбрать в поле \"Наименование документа\" в открывавшемся окне выбрать Тип документа")
    private void step05() {
        CommonFunctions.printStep();
        //Поле "Наименование документа" заполнено значением Протокол (ф. 0531805)
        //Поле "Код документа" заполнено значением TSE_0531805_D06
        new DocPage()
                .clickDictionaryNearField("Наименование документа")
                .filterColumnInDictionary("Наименование", "Протокол (ф. 0531805)")
                .filterColumnInDictionary("Системное имя формуляра", "TSE_0531805_D06")
                .clickRowInDictionary("Протокол (ф. 0531805)");
    }

    @Step("Нажимаем добавить новую запись при разделе \"Адресаты\", заполнить поля Электронная почта, ФИО пользователя ,Логин пользователя . Нажать кнопку \"ОК\"")
    private void step06() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@title='Добавить новую строку (Ins)']");

        //Новые адреса и пользователи
        //Электронная почта=grachenova.tatyana@mail.ru
        //ФИО пользователя=grachenova.tatyana
        //Логин пользователя=grachenova.tatyana
        String window = "//div[@class='subdoc-edit-dialog tbl_fs_tse_mailrecip_list z-window z-window-highlighted z-window-shadow']";
        $x(window + "//input[@name='tf_mail']").setValue("grachenova.tatyana@mail.ru");
        $x(window + "//input[@name='tf_fiorecipient']").setValue("grachenova.tatyana");
        $x(window + "//input[@name='tf_login']").setValue("grachenova.tatyana");

        new DocPage().clickButtonInBlock("Добавление записи", "Ok");

        //в разделе Адресаты добавилась запись, где  поле ФИО пользователя заполнено значением grachenova.tatyana,
        //поле Логин пользователя заполнено значением grachenova.tatyana, поле e-mail заполнено значением grachenova.tatyana@mail.ru,поле В копию заполнено значением Нет.
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='2']/div")
                .getText(), "grachenova.tatyana");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='3']/div")
                .getText(), "grachenova.tatyana");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='4']/div")
                .getText(), "grachenova.tatyana@mail.ru");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='5']/div")
                .getText(), "Нет");
    }

    @Step("Нажимаем добавить новую запись при разделе \"Адресаты\", заполнить поля Электронная почта, ФИО пользователя ,Логин пользователя , В копию выбрать . Нажать кнопку \"ОК\"")
    private void step07() {
        CommonFunctions.printStep();
        new DocPage().clickWebElement("//button[@title='Добавить новую строку (Ins)']");

        //Новые адреса и пользователи
        //Электронная почта=Grachenova.Tatyana@mail.ru
        //ФИО пользователя=Grachenova.Tatyana
        //Логин пользователя=Grachenova.Tatyana
        String window = "//div[@class='subdoc-edit-dialog tbl_fs_tse_mailrecip_list z-window z-window-highlighted z-window-shadow']";
        $x(window + "//input[@name='tf_mail']").setValue("Grachenova.Tatyana@mail.ru");
        $x(window + "//input[@name='tf_fiorecipient']").setValue("Grachenova.Tatyana");
        $x(window + "//input[@name='tf_login']").setValue("Grachenova.Tatyana");
        $x(window + "//input[@name='chb_incopy']/following-sibling::div").click();

        new DocPage().clickButtonInBlock("Добавление записи", "Ok");

        //в разделе Адресаты добавилась запись, где  поле ФИО пользователя заполнено значением Grachenova.Tatyana,
        //поле Логин пользователя заполнено значением Grachenova.Tatyana, поле e-mail заполнено значением Grachenova.Tatyana@mail.ru,поле В копию заполнено значением Да.
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='2']/div")
                .getText(), "Grachenova.Tatyana");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='3']/div")
                .getText(), "Grachenova.Tatyana");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='4']/div")
                .getText(), "Grachenova.Tatyana@mail.ru");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='5']/div")
                .getText(), "Да");
    }

    @Step("Нажать кнопку «Сохранить изменения и закрыть окно».")
    public void step08() {
        CommonFunctions.printStep();

        //Вытащить и сохранить дату начала действия записи
        String startDate = $(By.xpath("//input[@name='dtf_dict_datebegin']")).getAttribute("value");
        FileFunctions.writeValueFile(WAY_TEST + "startDate.txt", startDate);

        //Сохранить гуид документа в файл
        new DocPage()
                .clickButtonTitle("Информация о документе")
                .waitForLoading(60);
        String docGuid = $x("(//span[text() = 'Идентификатор']/../../..//span)[2]").getText();
        FileFunctions.writeValueFile(WAY_TEST + "docGuid.txt", docGuid);
        $x("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//div[@class='z-window-close']").click();

        new DocPage().clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");

        //Добавлена строка на СФ.
        //Колонка "Статус " заполнена значением «Новая».
        CommonFunctions.waitForElementDisplayed(By.xpath("//tr[contains(@class, 'addedDocument')]//td[@class='z-listcell'][@title='Новая']"), 60);
    }

}

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
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_024_PZ_2_4_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_4_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.4.1 Требования к контролям справочника (Проверка наличие адресата)")
    @Link(name="TSE-T4943", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4943")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
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

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e"); //Пирогов Владимир Константинович
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

    @Step("В разделе  \"Получатели уведомлений \" и выбрать тип документа")
    private void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .clickDictionaryNearFieldInArea("Наименование документа", "Получатели уведомлений")
                .filterColumnInDictionary("Системное имя формуляра", "TSE_ApplInfrmRDO_D02")
                .clickRowInDictionary("TSE_ApplInfrmRDO_D02");

        //Поле "Наименование документа" заполнено значением Документ-основание
        Assert.assertEquals($x("//input[@name='tf_dict_docname']").getAttribute("value"), "Документ-основание");
        //Поле "Код документа" заполнено значением TSE_ApplInfrmRDO_D02
        Assert.assertEquals($x("//input[@name='tf_dict_doccode']").getAttribute("value"), "TSE_ApplInfrmRDO_D02");
    }

    @Step("в разделе \"Адресаты \" добавить  и заполнить поля")
    public void step05() {
        CommonFunctions.printStep();

        new DocPage().clickWebElement("//button[@title='Добавить новую строку (Ins)']");

        String window = "//div[@class='subdoc-edit-dialog tbl_fs_tse_mailrecip_list z-window z-window-highlighted z-window-shadow']";
        $x(window + "//input[@name='tf_mail']").setValue("grachenova.tatyana@otr.ru");
        $x(window + "//input[@name='tf_fiorecipient']").setValue("grachenova.tatyana");
        $x(window + "//input[@name='tf_login']").setValue("grachenova.tatyana");
        $x(window + "//input[@name='chb_incopy']/following-sibling::div").click();

        new DocPage().clickButtonInBlock("Добавление записи", "Ok");

        //Поле "Электронная почта" заполнено значением grachenova.tatyana@otr.ru
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='4']/div")
                .getText(), "grachenova.tatyana@otr.ru");
        //Поле "ФИО пользователя" заполнено значением grachenova.tatyana
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='2']/div")
                .getText(), "grachenova.tatyana");
        //Поле "Логин пользователя" заполнено значением grachenova.tatyana
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='3']/div")
                .getText(), "grachenova.tatyana");
        //чек бокс "В копию" заполнено
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='5']/div")
                .getText(), "Да");
    }

    @Step("в разделе \"Адресаты \" добавить  и заполнить поля")
    public void step06() {
        CommonFunctions.printStep();

        new DocPage().clickWebElement("//button[@title='Добавить новую строку (Ins)']");

        String window = "//div[@class='subdoc-edit-dialog tbl_fs_tse_mailrecip_list z-window z-window-highlighted z-window-shadow']";
        $x(window + "//input[@name='tf_mail']").setValue("grachenova.tatyana@otr.ru");
        $x(window + "//input[@name='tf_fiorecipient']").setValue("grachenova.tatyana");
        $x(window + "//input[@name='tf_login']").setValue("grachenova.tatyana");
        $x(window + "//input[@name='chb_incopy']/following-sibling::div").click();

        new DocPage().clickButtonInBlock("Добавление записи", "Ok");

        //Поле "Электронная почта" заполнено значением grachenova.tatyana@otr.ru
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='4']/div")
                .getText(), "grachenova.tatyana@otr.ru");
        //Поле "ФИО пользователя" заполнено значением grachenova.tatyana
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='2']/div")
                .getText(), "grachenova.tatyana");
        //Поле "Логин пользователя" заполнено значением grachenova.tatyana
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='3']/div")
                .getText(), "grachenova.tatyana");
        //чек бокс "В копию" заполнено
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[2]/td[@col='5']/div")
                .getText(), "Да");
    }

    @Step("нажать кнопку \"Сохранить изменения\"")
    public void step07() {
        CommonFunctions.printStep();
        new DocPage().clickButtonTitle("Сохранить изменения (Alt+A)");

        //Выходит контроль:
        //В текущей записи в таблице «Адресаты» должна быть хотя бы одна строка, в которой реквизит «В копию» не содержит «галочки».
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//span[contains(text(), 'В текущей записи в таблице «Адресаты» должна быть хотя бы одна строка, в которой реквизит «В копию» не содержит «галочки».')]"), 60);
    }

}

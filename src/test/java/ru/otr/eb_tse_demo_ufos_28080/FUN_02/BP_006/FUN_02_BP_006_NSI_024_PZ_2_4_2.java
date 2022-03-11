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

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_024_PZ_2_4_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_4_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.4.2 Требования к контролям справочника (Проверка на корректность указанного e-mail)")
    @Link(name="TSE-T4801", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4801")

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

    @Step("Проверить заполнение полей в  разделе \"Информация о клиенте\" и поле \"Дата начала действия записи\", поле \"Статус\"")
    private void step04() {
        CommonFunctions.printStep();

        //Поле "Код СВР/НУБП" заполнено значением 450Э7888
        Assert.assertEquals($x("//input[@name='tf_dict_svrcode']").getAttribute("value"), "450Э7888");
        //Поле "ИНН" заполнено значением 7722698789
        Assert.assertEquals($x("//input[@name='tf_dict_inn']").getAttribute("value"), "7722698789");
        //Поле "КПП" заполнено значением 772201001
        Assert.assertEquals($x("//input[@name='tf_dict_kpp']").getAttribute("value"), "772201001");
        //Поле "Полное наименование" заполнено значением АКЦИОНЕРНОЕ ОБЩЕСТВО "РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ"
        Assert.assertEquals($x("//textarea[@name='ta_dict_lognaame']").getAttribute("value"), "АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\"");
        //поле "Дата начала действия записи" заполнено значением Текущая дата
        Assert.assertEquals($x("//input[@name='dtf_dict_datebegin']").getAttribute("value"), CommonFunctions.dateToday("dd.MM.yyyy"));
        //поле "Статус" заполнено значением Статус
        Assert.assertEquals($x("//input[@name='tf_docstate_name']").getAttribute("value"), "Новая");
    }

    @Step("Выбрать в поле \"Наименование документа\" в открывавшемся окне выбрать Тип документа")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickDictionaryNearField("Наименование документа")
                .filterColumnInDictionary("Наименование", "Документ-основание")
                .filterColumnInDictionary("Системное имя формуляра", "TSE_ApplInfrmRDO_D02")
                .clickRowInDictionary("Документ-основание");

        //Поле "Наименование документа" заполнено значением Документ-основание
        Assert.assertEquals($x("//input[@name='tf_dict_docname']").getAttribute("value"), "Документ-основание");
        //Поле "Код документа" заполнено значением TSE_ApplInfrmRDO_D02
        Assert.assertEquals($x("//input[@name='tf_dict_doccode']").getAttribute("value"), "TSE_ApplInfrmRDO_D02");
    }

    @Step("Нажимаем добавить новую запись при разделе \"Адресаты\", заполнить поля Электронная почта, ФИО пользователя ,Логин пользователя . Нажать кнопку \"ОК\"")
    private void step06() {
        CommonFunctions.printStep();

        new DocPage().clickWebElement("//button[@title='Добавить новую строку (Ins)']");

        String window = "//div[@class='subdoc-edit-dialog tbl_fs_tse_mailrecip_list z-window z-window-highlighted z-window-shadow']";
        $x(window + "//input[@name='tf_mail']").setValue("grachenova.tatyana@ma");
        $x(window + "//input[@name='tf_fiorecipient']").setValue("grachenova.tatyana");
        $x(window + "//input[@name='tf_login']").setValue("grachenova.tatyana");

        new DocPage().clickButtonInBlock("Добавление записи", "Ok");

        //в разделе Адресаты добавилась запись, где  поле ФИО пользователя заполнено значением grachenova.tatyana,
        //поле Логин пользователя заполнено значением grachenova.tatyana, поле e-mail заполнено значением grachenova.tatyana@ma,поле В копию заполнено значением Нет.
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='2']/div")
                .getText(), "grachenova.tatyana");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='3']/div")
                .getText(), "grachenova.tatyana");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='4']/div")
                .getText(), "grachenova.tatyana@ma");
        Assert.assertEquals($x("//span[text()='Адресаты']//ancestor::div[8]//table//tr[1]/td[@col='5']/div")
                .getText(), "Нет");
    }


    @Step("Нажать кнопку «Сохранить изменения и закрыть окно».")
    private void step07() {
        CommonFunctions.printStep();
        new DocPage().clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");

        //Выходит  сообщение с типом ошибка и сообщением " В поле  «e-mail» в строке [1]  указан некорректный адрес. "
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//span[contains(text(), ' В поле «e-mail» в строке [1] указан некорректный адрес.')]"), 30);
    }

}

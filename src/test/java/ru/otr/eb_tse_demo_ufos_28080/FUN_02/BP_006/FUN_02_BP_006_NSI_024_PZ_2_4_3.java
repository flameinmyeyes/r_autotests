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

public class FUN_02_BP_006_NSI_024_PZ_2_4_3 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_4_3\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.4.3 Требования к контролям справочника (проверка на уникальность)")
    @Link(name="TSE-T4944", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4944")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
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
        //Поле "КПП" заполнено значением 772201001
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

        //выбрать = ARP_FAH_0504833
        new DocPage()
                .clickDictionaryNearField("Наименование документа")
                .filterColumnInDictionary("Наименование", "Бухгалтерская справка (ф. 0504833)")
                .filterColumnInDictionary("Системное имя формуляра", "ARP_FAH_0504833")
                .filterColumnInDictionary("Статус", "Актуальная")
                .clickRowInDictionary("Бухгалтерская справка (ф. 0504833)");

        //Поле "Наименование документа" заполнено значением Бухгалтерская справка (ф. 0504833)
        Assert.assertEquals($x("//input[@name='tf_dict_docname']").getAttribute("value"), "Бухгалтерская справка (ф. 0504833)");
        //Поле "Код документа" заполнено значением ARP_FAH_0504833
        Assert.assertEquals($x("//input[@name='tf_dict_doccode']").getAttribute("value"), "ARP_FAH_0504833");
    }


    @Step("Нажать кнопку «Актуализировать».")
    private void step06() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Актуализировать")
                .waitForLoading(120);

        if ($x("//div[@class='z-window z-window-highlighted z-window-shadow']//button[text()='Да']").isDisplayed()) {
            $x("//div[@class='z-window z-window-highlighted z-window-shadow']//button[text()='Да']").click();
            new DocPage().waitForLoading(60);
        }

        new DocPage().clickWebElement("//div[@class='processResultDialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//div[text()='Документарный контроль выполнен с ошибками или предупреждениями']");

        new DocPage().clickWebElement("//div[@class='processResultDialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//span[text()='Док. контроль']");

        //В справочнике имеется запись по документу «Бухгалтерская справка (ф. 0504833)» с аналогичным периодом действия. Измените период или внесите изменения в существующую запись.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='processResultDialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//span[contains(text(), 'В справочнике имеется запись по документу «Бухгалтерская справка (ф. 0504833)» с аналогичным периодом действия, измените период или внесите изменения в существующую запись.')]"), 60);
    }

}

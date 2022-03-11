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

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_021_PZ_1_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_1\\";
    public String detailCode = "40" + CommonFunctions.randomNumber(100, 999);

    @Owner(value="Балашов Илья")
    @Description("NSI_021. ПЗ п. 1.1. Способы формирования спр-ка (Признак контроля 0)")
    @Link(name="TSE-T3186", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3186")

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

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Группы причин отказов».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Группы причин отказов");
    }

    @Step("Нажать на кнопку \"Создать новый документ\" на инструментальной панели списковой формы")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Заполнить поля:\n" +
            "\"Признак контроля\"\n" +
            "\"Код и виды причины отказа\"\n" +
            "\"Причина отказа\"\n" +
            "\"Детализированный код\"")
    public void step04() {
        CommonFunctions.printStep();
        //"Признак контроля" = 0
        //"Код и виды причины отказа" = 40
        //"Причина отказа" =ТЕСТ
        //"Детализированный код" = 40001
        new DocPage()
                .inputValueInField("Признак контроля", "0");

        new DocPage()
                .clickDictionaryNearField("Код и виды причин отказа")
                .filterColumnInDictionary("Код", "40")
                .clickRowInDictionary("40");

        new DocPage()
                .inputValueInField("Причина отказа", "ТЕСТ")
                .inputValueInField("Детализированный код", detailCode);

        //Заполнены поля:
        //"Признак контроля" = 0
        Assert.assertEquals($x("//input[@name='cmb_dict_symcontrol']").getAttribute("value"), "0");
        //"Код и виды причины отказа" = 40
        Assert.assertEquals($x("//input[@name='cmb_dict_typecontrol']").getAttribute("value"), "40");
        //"Причина отказа" = ТЕСТ
        Assert.assertEquals($x("//textarea[@name='tf_dict_reasonrefusal']").getAttribute("value"), "ТЕСТ");
        //"Детализированный код" = 40001 (уникальное число)
        Assert.assertEquals($x("//input[@name='tf_dict_detailcode']").getAttribute("value"), detailCode);

        //Сохранить в файл атрибуты:
        //детализированный код
        FileFunctions.writeValueFile(WAY_TEST + "detailCode.txt", detailCode);
        //код
        FileFunctions.writeValueFile(WAY_TEST + "code.txt", $x("//input[@name='cmb_dict_typecontrol']").getAttribute("value"));
        //причина отказа
        FileFunctions.writeValueFile(WAY_TEST + "reasonRefusal.txt", $x("//textarea[@name='tf_dict_reasonrefusal']").getAttribute("value"));
        //дата начала действия
        FileFunctions.writeValueFile(WAY_TEST + "startDate.txt", $x("//input[@name='dtf_dict_datestart']").getAttribute("value"));
        //Сохранить гуид
        new DocPage()
                .clickButtonTitle("Информация о документе")
                .waitForLoading(60);
        String docGuid = $x("(//span[text() = 'Идентификатор']/../../..//span)[2]").getText();
        FileFunctions.writeValueFile(WAY_TEST + "docGuid.txt", docGuid);
        $x("//div[@class='z-window-close']").click();
    }

    @Step("1. Нажать кнопку \"Проверить документ\"\n" +
            "2. Нажать \"Сохранить\"")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Проверить документ (Alt+Q)")
                .waitForLoading(120);

        new DocPage()
                .clickButtonInBlock("Результаты проверки", "Сохранить");
    }

    @Step("Нажать на кнопку \"Закрыть\"")
    private void step06() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Закрыть окно")
                .waitForLoading(120);

        new MainPage()
                .filterColumnInList("Детализированный код", FileFunctions.readValueFile(WAY_TEST + "detailCode.txt"))
                .filterColumnInList("Код", FileFunctions.readValueFile(WAY_TEST + "code.txt"))
                .filterColumnInList("Причина отказа", FileFunctions.readValueFile(WAY_TEST + "reasonRefusal.txt"))
                .filterColumnInList("Дата начала действия записи", FileFunctions.readValueFile(WAY_TEST + "startDate.txt"));

        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Новая']"), 60);
    }

}

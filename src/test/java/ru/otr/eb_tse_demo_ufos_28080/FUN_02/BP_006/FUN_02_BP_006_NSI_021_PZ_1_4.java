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

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_021_PZ_1_4 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_1_4\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_021. ПЗ п. 1.4. Требования к контролям справочника (контроль на обязательность заполнения реквизитов)")
    @Link(name="TSE-T3188", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3188")

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
        step08();
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

    @Step("1. Выбрать признак контроля\n" +
            "2. Удалить значение поля \"Дата начала действия\"")
    public void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .inputValueInField("Признак контроля", "0");

        $x("//input[@name='dtf_dict_datestart']").clear();

        //Признак контроля = 0
        Assert.assertEquals($x("//input[@name='cmb_dict_symcontrol']").getAttribute("value"), "0");
        //Значение удалено
        Assert.assertEquals($x("//input[@name='dtf_dict_datestart']").getAttribute("value"), "");
    }

    @Step("Нажать кнопку \"Сохранить\"")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Сохранить изменения (Alt+A)")
                .waitForLoading(120);

        //Выходят контроли на обязательное заполнение полей (тип ошибки блокирующий)
        //Поле 'Причина отказа' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Причина отказа' обязательно для заполнения\")]"), 60);
        //Поле 'Код' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Код' обязательно для заполнения\")]"), 60);
        //Поле 'Дата начала действия записи' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Дата начала действия записи' обязательно для заполнения\")]"), 60);
        //Поле 'Виды причин отказа' обязательно для заполнения.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Виды причин отказа' обязательно для заполнения.\")]"), 60);
        //Поле 'Детализированный код' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Детализированный код' обязательно для заполнения\")]"), 60);
        //Первые два символа реквизита 'Детализированный код' должны быть равны значению 'Код'
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Первые два символа реквизита 'Детализированный код' должны быть равны значению 'Код'\")]"), 60);
    }

    @Step("Нажать \"Вернуться к документу\"")
    private void step06() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonInBlock("Результаты проверки", "Закрыть окно результатов проверки и вернуться к документу")
                .waitForLoading(60);
    }

    @Step("1. Выбрать признак контроля")
    private void step07() {
        CommonFunctions.printStep();
        new DocPage()
                .inputValueInField("Признак контроля", "1");

        //Признак контроля =1
        Assert.assertEquals($x("//input[@name='cmb_dict_symcontrol']").getAttribute("value"), "1");
    }

    @Step("Нажать кнопку \"Сохранить\"")
    private void step08() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Сохранить изменения (Alt+A)")
                .waitForLoading(120);

        //Выходят контроли на обязательное заполнение полей (тип ошибки блокирующий)
        //Поле 'Наименование документа' обязательно для заполнения.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Наименование документа' обязательно для заполнения.\")]"), 60);
        //Поле 'Код  причины отказа' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Код  причины отказа' обязательно для заполнения\")]"), 60);
        //Поле 'Причина отказа' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Причина отказа' обязательно для заполнения\")]"), 60);
        //Поле 'Тип документа' обязательно для заполнения
        //Поле 'Код' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Код' обязательно для заполнения\")]"), 60);
        //Поле 'Дата начала действия записи' обязательно для заполнения
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Дата начала действия записи' обязательно для заполнения\")]"), 60);
        //Поле 'Виды причин отказа' обязательно для заполнения.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле 'Виды причин отказа' обязательно для заполнения.\")]"), 60);
    }

}

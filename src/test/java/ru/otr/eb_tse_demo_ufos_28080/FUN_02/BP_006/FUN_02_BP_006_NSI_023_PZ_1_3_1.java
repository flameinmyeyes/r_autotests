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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

public class FUN_02_BP_006_NSI_023_PZ_1_3_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_3_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_023. ПЗ п. 1.3.1. Требования к контролям справочника (контроль на обязательность заполнения реквизитов)")
    @Link(name="TSE-T3200", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3200")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
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

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов");
    }

    @Step("Нажать кнопку «Создать» на панели инструментов")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Ничего не заполняя нажать на кнопку \"Сохранить\"")
    public void step04() {
        new DocPage()
                .clickButtonTitle("Сохранить изменения (Alt+A)")
                .waitForLoading(300);

        //Выходят контроли на обязательное заполнение полей (тип ошибки блокирующий)
        //Поле 'Код' обязательно для заполнения.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле «Код» обязательно для заполнения.\")]"), 60);
        //Поле 'Дата начала действия обязательно для заполнения' обязательно для заполнения.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле «Дата начала действия записи» обязательно для заполнения.\")]"), 60);
        //Поле 'Виды причин отказа' обязательно для заполнения.
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \" Поле «Виды причин отказа» обязательно для заполнения.\")]"), 60);
    }
}

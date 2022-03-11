package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

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
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_016_NSI_016_PZ_2_7_2_2 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_016. ПЗ п. 2.7.2. ТК №2 Проверка актуализации записи справочника")
    @Link(name="TSE-T2264", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2264")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_016"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot( WAY_TEST + "screen.png");
    }

    @Step("Зайти на стенд. Авторизироваться в ЛК Администратора локальных НСИ ПУР КС")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть в навигации папку \"Справочники\"")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники");
    }

    @Step("Перейти на вкладку \"Виды средств\"")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Виды средств");
    }

    @Step("Открыть запись на статусе \"Новая\" и нажать на кнопку \"Актуализировать\"")
    public void step04() {
        CommonFunctions.printStep();

        //Создать запись на статусе "Новая"
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Создать новый документ (Alt+N)")
                .waitForLoading(60);
        String code = "АТ" + CommonFunctions.randomNumber(000, 999);
        new DocPage()
                .inputValueInField("Код", code)
                .inputValueInField("Наименование", "АТ")
                .clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)")
                .waitForLoading(60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Новая']"), 60);

        //Выбрать и актулизировать запись
        new MainPage()
                .filterColumnInList("Код", code)
                .filterColumnInList("Наименование", "АТ")
                .filterColumnInList("Дата начала действия записи", CommonFunctions.dateToday("dd.MM.yyyy"))
                .clickRowInList(code)
                .clickButtonTitle("Актуализировать")
                .waitForLoading(60);
        //Статус записи "Актуальная"
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Актуальная']"), 60);

        //Отправить запись в архив
        new MainPage()
                .clickButtonTitle("Отправить в архив")
                .clickButtonInBlock("Подтверждение", "Да")
                .waitForLoading(60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Архивная']"), 60);
    }

}

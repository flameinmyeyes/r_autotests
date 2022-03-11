package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
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

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;


////AC
public class FUN_02_BP_006_NSI_014_PZ_KP_2_7_1 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2443
     * NSI_014. ПЗ п. 2.7.1. Требования к СФ. Требования к отоб-нию реквизитов в списковой форме спр-ка
     * задача:
     * Проверить отображение реквизитов в СФ справочника
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_014_PZ_KP_2_7_1\\";

    @Owner(value = "Якубов Алексей")
    @Description("NSI_014. ПЗ п. 2.7.1. Требования к СФ. Требования к отоб-нию реквизитов в списковой форме спр-ка")
    @Link(name="TSE-T2443", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2443")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})

    public void steps() {
        WAY_TEST = setWay(WAY_TEST);

        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(60);
        new MainPage().resetUserSettings().waitForLoading(30);
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step03() {
        CommonFunctions.printStep();
        //CommonFunctions.wait(5000);
        SeeColumns();

    }

    /**
     * Поиск колонки
     * @param text Название колонки
     */
    private void seeColumn(String text) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class, 'z-listheader-content')][contains(.,'" + text + "')]"),20);
    }

    /**
     * Поиск типового набора колонок
     */
    public void SeeColumns(){
        //Есть колонки "Наименование", "Уровень бюджета", " Дата начала действия записи" , "Дата окончания действия записи", "Статус"

        seeColumn("Наименование");
        seeColumn("Уровень бюджета");
        seeColumn("Дата начала действия записи");
        seeColumn("Дата окончания действия записи");
        seeColumn("Статус");
    }

}

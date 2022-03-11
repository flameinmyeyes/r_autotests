package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
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

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_015 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_PZ_KP_2_12_2_1\\";


    @Owner(value = "Ворожко Александр")
    @Description("NSI_015. Переход по ссылке на документ ПДО")
    @Link(name="TSE-T3178", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3178")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})

    public void steps() {
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

    @Step("Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " ИГК")
                .resetUserSettings();
    }

    @Step("Перейти  по ссылке колонки \"Ссылка на головной контракт\", где у  ИГК в колонке \"Статус\" равен \"Актуальная\"")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Статус", "Актуальная");

        $x("//table//tr/td[contains(@class,'z-listcell')][contains(.,'№ ')]").shouldBe(Condition.visible);
        String link = $x("//table//tr/td[contains(@class,'z-listcell')][contains(., '№ ')][1]").getText();
        $x("//table//tr/td[contains(@class,'z-listcell')][contains(., '№ ')][1]").click();
        new MainPage()
                .waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='doc-edit-frame z-vlayout']//*[contains(text(), 'Государственный контракт №')]"), 60, true);
//        $x("//div[@class='doc-edit-frame z-vlayout']//*[contains(text(), 'Головной уровень')]/../*[@checked='checked']").shouldBe(Condition.visible);
    }

    @Step("Нажать кнопку \"Закрыть окно\"")
    private void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Закрыть окно");
        $x("//div[@class='doc-edit-frame z-vlayout']").should(Condition.not(Condition.visible), Duration.ofSeconds(60));
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «ИГК».")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");
        $x("//div[@class='doc-edit-frame z-vlayout']//*[contains(text(), '№ ')]").shouldBe(Condition.visible).click();
    }

    @Step("Проверить что СФ открыта")
    private void step06() {
        CommonFunctions.printStep();
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='doc-edit-frame z-vlayout']//*[contains(text(), 'Государственный контракт №')]"), 60, true);
//        $x("//div[@class='doc-edit-frame z-vlayout']//*[contains(text(), 'Головной уровень')]/../*[@checked='checked']").shouldBe(Condition.visible);
    }
}

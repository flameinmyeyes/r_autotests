package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
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

import java.time.Duration;
import java.time.Instant;

import static com.codeborne.selenide.Selenide.*;


////AC
public class FUN_02_BP_006_NSI_023_PZ_1_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_1\\";
    public String REJECT_REASON = "тестовый сценарий" + Instant.now().getEpochSecond();

    @Owner(value = "Ворожко Александр")
    @Description("NSI_023. ПЗ п. 1.1. Способы формирования спр-ка")
    @Link(name="TSE-T3198", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3198")
    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);

        precondition();
        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @Step("Проверить предусловия")
    public void precondition() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов")
                .resetUserSettings();
        new MainPage()
                .filterColumnInList("Код", "40")
                .filterColumnInList("Статус", "Актуальная");
        if (!$x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").isDisplayed()) {
            $(By.xpath("//div[@class='z-listcell-content']")).click();
            new MainPage().clickButtonTitle("Отправить в архив");
            $x("//button[contains(@class, 'yes')]").click();
        }
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов")
                .resetUserSettings();
    }

    @Step("Нажать кнопку «Создать» на панели инструментов")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Заполнить поля формы")
    public void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .inputValueInField("Код:", "40")
                .inputValueInField("Виды причин отказа:", REJECT_REASON)
                .inputValueInField("Дата действия с:", CommonFunctions.dateToday("dd.MM.yyyy"))
                .inputValueInField("Дата действия по:", CommonFunctions.dateShift(CommonFunctions.dateToday("dd.MM.yyyy"), "dd.MM.yyyy", +10));

        //сохранить дату начала
        String startDate = $x("//input[@name='dtf_dict_startdate']").getAttribute("value");
        FileFunctions.writeValueFile(WAY_TEST + "startDate.txt", startDate);
        //сохранить дату окончания
        String endDate = $x("//input[@name='dtf_dict_enddate']").getAttribute("value");
        FileFunctions.writeValueFile(WAY_TEST + "endDate.txt", endDate);
    }

    @Step("Нажать кнопку «Нажать кнопку \"Сохранить и закрыть\"")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage().clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");
        new MainPage()
                .filterColumnInList("Виды причин отказа", REJECT_REASON)
                .filterColumnInList("Дата действия с", CommonFunctions.dateToday("dd.MM.yyyy"))
                .filterColumnInList("Статус", "Новая");
        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldNotBe(Condition.visible);
        new MainPage().clickRowInList(REJECT_REASON);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()=' Атрибуты']"), 60);
        $x("//span[text()=' Атрибуты']").click();
        new MainPage().waitForLoading(60);

        String docGuid = $x("//span[text() = 'Идентификатор:']/ancestor::*[descendant::*[contains(@class, 'attr-value')]][1]//descendant::*[contains(@class, 'attr-value')]//span").getText();
        FileFunctions.writeValueFile(WAY_TEST + "reject_reason.txt", REJECT_REASON);
        FileFunctions.writeValueFile(WAY_TEST + "docGuid.txt", docGuid);
    }
}

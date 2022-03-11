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

import static com.codeborne.selenide.Selenide.$x;

////AC
public class FUN_02_BP_006_NSI_031_PZ_1_5 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_1_5\\";
    public String WAY_TEST_PREVIOUS = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_1_2\\";

    @Owner(value = "Ворожко Александр")
    @Description("NSI_031. ПЗ п. 1.5. Требования к статусной модели справочника (отправление в архив)")
    @Link(name="TSE-T3227", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3227")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_PREVIOUS = setWay(WAY_TEST_PREVIOUS);

        precondition();
        step01();
        step02();
        step03();
        step04();
    }

    @Step("Проверить предусловия")
    public void precondition() throws Exception {
        CommonFunctions.printStep();
        new LoginPage().authorization("8e1fdacb-ac18-41bf-a6f7-9c420a5d80a5"); //Балабанов Александр Сергеевич
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Настройки доступа ТОФК по месту обращения")
                .resetUserSettings();
        new MainPage()
                .filterColumnInList("ИНН Клиента", "4347024171")
                .filterColumnInList("Статус", "Актуальная");
        if ($x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").isDisplayed()) {
            System.out.println("Требуется выполнение предусловий");
            FUN_02_BP_006_NSI_031_PZ_1_2 preconditionTest = new FUN_02_BP_006_NSI_031_PZ_1_2();
            preconditionTest.steps();
        } else {
            System.out.println("Выполнение предусловий не требуется");
        }
    }

    @Step("Авторизация в ЛК ТОФК Обращения. Исполнитель ")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("8e1fdacb-ac18-41bf-a6f7-9c420a5d80a5"); //Балабанов Александр Сергеевич
    }

    @Step("Открыть в навигации папку \"Справочники\" - «Настройки доступа ТОФК по месту обращения»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Настройки доступа ТОФК по месту обращения")
                .resetUserSettings();
    }

    @Step("Выделить на СФ \"«Настройки доступа ТОФК по месту обращения»\" со статусом «Актуальная»")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("ИНН Клиента", "4347024171")
                .filterColumnInList("Статус", "Актуальная")
                .clickRowsInList(1);
    }

    @Step("Нажать кнопку \"Отправить  в архив\"")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Отправить в архив");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Успешно завершена')]"), 120, true);
        new MainPage()
                .filterColumnInList("ИНН Клиента", "4347024171")
                .filterColumnInList("Статус", "Актуальная");
        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldNotBe(Condition.visible);
        new MainPage()
                .clickButtonTitle("Отправить в архив");
        $x("//button[contains(@class, 'yesButton')]").click();
        new MainPage()
                .filterColumnInList("ИНН Клиента", "4347024171")
                .filterColumnInList("Статус", "Актуальная");
        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldBe(Condition.visible);
    }
}

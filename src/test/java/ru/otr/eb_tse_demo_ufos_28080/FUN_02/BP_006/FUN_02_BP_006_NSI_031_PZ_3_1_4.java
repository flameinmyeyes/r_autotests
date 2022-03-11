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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


////AC
public class FUN_02_BP_006_NSI_031_PZ_3_1_4 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_3_1_4\\";

    @Owner(value = "Ворожко Александр")
    @Description("NSI_031. ПЗ п. 1.4. Требования к контролям справочника ( контроль на обязательность заполнения реквизитов)")
    @Link(name="TSE-T3226", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3226")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @Step("Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
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

    @Step("Нажать кнопку «Создать» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Создать новый документ (Alt+N)");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(@class, 'doc-edit-frame')]//*[contains(text(), 'Настройка доступа ТОФК по месту обращения')]"), 60, true);
    }

    @Step("Удалить значение поля  \"Дата начала действия записи \"")
    public void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .inputValueInField("Дата начала действия записи", "");
    }

    @Step("Нажать на кнопку \"Сохранить изменение \"")
    public void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");

        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \"Поле 'Дата начала действия записи' обязательно для заполнения.\")]"), 60);
        $x("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \"Поле 'Полное наименование Клиента' обязательно для заполнения\")]").shouldBe(Condition.visible);
        $x("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \"Поле 'ИНН Клиента' обязательно для заполнения\")]").shouldBe(Condition.visible);
        $x("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \"Поле 'Код СВР / ИП и КФХ Клиента' обязательно для заполнения.\")]").shouldBe(Condition.visible);
    }
}

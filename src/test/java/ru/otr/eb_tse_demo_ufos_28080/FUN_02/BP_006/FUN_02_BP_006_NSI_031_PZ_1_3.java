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

import static com.codeborne.selenide.Selenide.$x;

////AC
public class FUN_02_BP_006_NSI_031_PZ_1_3 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_1_3\\";
    public String WAY_TEST_PREVIOUS = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_1_2\\";

    @Owner(value = "Ворожко Александр")
    @Description("NSI_031. ПЗ п. 1.3. Требования к контролям справочника (Проверка на уникальность записи справочника)")
    @Link(name="TSE-T3228", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3228")

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
        step01();
        step02();
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

    @Step("Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("8e1fdacb-ac18-41bf-a6f7-9c420a5d80a5"); //Рябова Анна Викторовна
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

    @Step("Выбрать \"ИНН\". Нажать кнопку \"Сохранить изменение\"")
    public void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .clickDictionaryNearField("ИНН Клиента")
                .filterColumnInDictionary("ИНН", "4347024171")
                .clickRowInDictionary("4347024171")
                .clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");

        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//span[contains(text(), \"Для данного ТОФК Обращения в системе уже имеется актуальная запись по данному Клиенту.\")]"), 60);
    }
}

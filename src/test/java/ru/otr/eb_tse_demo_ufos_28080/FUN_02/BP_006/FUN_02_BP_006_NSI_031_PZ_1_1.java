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
public class FUN_02_BP_006_NSI_031_PZ_1_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_1_1\\";

    @Owner(value = "Ворожко Александр")
    @Description("NSI_031. ПЗ п. 1.1. Формирования записи спр-ка")
    @Link(name="TSE-T3224", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3224")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        precondition();
        step01();
        step02();
        step03();
        step04();
        step05();
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
        if (!$x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").isDisplayed()) {
            $(By.xpath("//div[@class='z-listcell-content']")).click();
            new MainPage().clickButtonTitle("Отправить в архив");
            $x("//button[contains(@class, 'yesButton')]").click();
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

    @Step("Нажать на кнопку \"Создать новый документ\" на инструментальной панели списковой формы")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Создать новый документ (Alt+N)");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(@class, 'doc-edit-frame')]//*[contains(text(), 'Настройка доступа ТОФК по месту обращения')]"), 60, true);
    }

    @Step("Выбрать ИНН клиента. ИНН = 4347024171")
    public void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .clickDictionaryNearField("ИНН Клиента")
                .filterColumnInDictionary("ИНН", "4347024171")
                .clickRowInDictionary("4347024171");

        Assert.assertEquals($x("//*[@name='tf_doc_bre_clientinn']").getAttribute("value"), "4347024171");
        Assert.assertEquals($x("//*[@name='tf_doc_bre_clientcode']").getAttribute("value"), "33302683");
        Assert.assertEquals($x("//*[@name='ta_dict_bre_client']").getAttribute("value"), "МУНИЦИПАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ ДОПОЛНИТЕЛЬНОГО ОБРАЗОВАНИЯ \"ДЕТСКО-ЮНОШЕСКИЙ ЦЕНТР ОКТЯБРЬСКОГО РАЙОНА\" ГОРОДА КИРОВА");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");
        $x("//*[contains(@class, 'doc-edit-frame')]//*[contains(text(), 'Настройка доступа ТОФК по месту обращения')]").shouldNotBe(Condition.visible);
    }
}

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
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

////AC
public class FUN_02_BP_006_NSI_032_MT_8_KP_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_032_MT_8_KP_1\\";

    @Owner(value = "Ворожко Александр")
    @Description("МТ8 КП1 (1). Формирование записи в справочнике \"Критерии приостановления операций по ЛС\" (Расход)")
    @Link(name="TSE-T4293", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4293")

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

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Критерии приостановления операций по ЛС».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Критерии приостановления операций по ЛС")
                .resetUserSettings();
    }

    @Step("Нажать кнопку «Создать» на панели инструментов.")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Создать новый документ (Alt+N)");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(@class, 'doc-edit-frame')]//*[contains(text(), 'Критерии приостановления операций по ЛС')]"), 60, true);
    }

    @Step("Заполнить поля ВФ")
    public void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .clickDictionaryNearField("Наименование критерия приостановления операций")
                .filterColumnInDictionary("Причина отказа", "Уплата налогов и сборов%приказа Минфина России от 08.12.2017 № 221н")
                .clickRowInDictionary("52001")
                .inputValueInField("% суммы ГК", "1");

        $x("//div[contains(@class, 'doc-edit-frame')]//button[contains(@class, 'fillTab_INFCODECS')]").shouldBe(Condition.visible).click();

        new DocPage()
                .filterColumnInDictionary("Код направления расходования", "0200006")
                .clickRowInDictionary("0200006");

        assertValuefromField("Код бюджета", "99010001");
        assertValuefromField("Статус", "Новая");
        assertValuefromField("Дата начала действия записи", CommonFunctions.dateToday("dd.MM.yyyy"));
    }

    @Step("Нажать кнопку «Сохранить изменения и закрыть окно».")
    public void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");

        new MainPage()
                .filterColumnInList("Наименование критерия приостановления операций ", "Уплата налогов и сборов%приказа Минфина России от 08.12.2017 № 221н")
                .filterColumnInList("Дата начала действия записи", CommonFunctions.dateToday("dd.MM.yyyy"))
                .filterColumnInList("Статус", "Новая");
        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldNotBe(Condition.visible);
    }

    public void assertValuefromField(String title, String expected) {
        System.out.println("Проверка поля " + title);
        String xpath = "//div[contains(@class, 'doc-edit-frame')]//*[text()='" + title + "']" +
                "//ancestor::*[descendant::*[contains(@class, 'z-advtextarea') or contains(@class, 'z-textbox') or contains(@class, 'z-datebox-input')]][1]" +
                "//*[contains(@class, 'z-advtextarea') or contains(@class, 'z-textbox') or contains(@class, 'z-datebox-input')]";
        String value = $x(xpath).getAttribute("value");
        Assert.assertEquals(value, expected);
    }
}

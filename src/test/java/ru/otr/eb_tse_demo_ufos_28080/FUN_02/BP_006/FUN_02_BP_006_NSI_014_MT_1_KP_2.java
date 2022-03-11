package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_014_MT_1_KP_2 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_014_MT_1_KP_2\\";
    private String date;


    @Owner(value = "Ворожко Александр")
    @Description("МТ1 КП2. Формирование записи в справочнике \"Проекты\" (Администратор)")
    @Link(name="TSE-T3824", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3824")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        precondition();
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

    @Step("Проверить предусловия")
    public void precondition() throws Exception {
        CommonFunctions.printStep();
        step01();
        step02();
        new MainPage()
                .filterColumnInList("Наименование проекта", "ЕДПОСТАВЩИК")
                .filterColumnInList("Статус", "Актуальная");
        if (!$x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").isDisplayed()) {
            new MainPage()
                    .clickRowsInList(1)
                    .clickButtonTitle("Отправить в архив");
        }
    }

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Администратор (Пятница) ")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты")
                .resetUserSettings();
    }

    @Step("Создание формуляра")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Заполнить поля ВФ")
    private void step04() {
        CommonFunctions.printStep();
        date = CommonFunctions.dateToday("dd.MM.yyyy");
        new DocPage()
                .inputValueInField("Наименование проекта:", "ЕДПОСТАВЩИК")
                .inputValueInField("Примечание:", "Проекты казначейского сопровождения")
                .clickCheckbox("Бюджет субъекта")
                .inputValueInField("Дата начала действия записи:", date);

        Assert.assertEquals($x("//*[text()='Статус:']//ancestor::*[descendant::input][1]//input").getAttribute("value"), "Новая");
        $x("//*[text()='Дата окончания действия записи:']//ancestor::*[descendant::input][1]//input[@readonly='readonly']").shouldBe(Condition.visible);
    }

    @Step("Нажать кнопку «Сохранить изменения».")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Сохранить изменения и закрыть окно (Alt+S)");
    }

    @Step("Нажать кнопку \" Закрыть окно\"")
    private void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Наименование проекта", "ЕДПОСТАВЩИК")
                .filterColumnInList("Дата начала действия записи", date)
                .filterColumnInList("Статус", "Новая")
                .filterColumnInList("Федеральный", "Нет")
                .filterColumnInList("Бюджет субъекта", "Да")
                .filterColumnInList("Муниципальный", "Нет");

        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldNotBe(Condition.visible);
    }
}

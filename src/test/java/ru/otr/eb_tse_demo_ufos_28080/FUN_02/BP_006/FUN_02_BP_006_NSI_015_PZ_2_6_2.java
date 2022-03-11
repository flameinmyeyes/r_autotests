package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
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
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;


////AC
public class FUN_02_BP_006_NSI_015_PZ_2_6_2 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3179
     * Требования к ВФ. Требования к действиям на визуальной форме спр-ка
     * задача:
     * Проверить действия в ВФ справочника
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_PZ_2_6_2\\";

    @Owner(value = "Якубов Алексей")
    @Description("NSI_015. ПЗ п. 2.6.2. Требования к ВФ. Требования к действиям на визуальной форме спр-ка")
    @Link(name="TSE-T3179", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3179")
    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);

        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
        step09();
        step10();
        step11();
        step12();
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        //new LoginPage().authorization("Loboda.KI");
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T4516 (1.0) Переход по дереву навигации к справочнику \"ИГК\"")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(60);
        seePageTitle("ИГК");
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step03() {
        CommonFunctions.printStep();
        clickViewButton();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step04() {
        CommonFunctions.printStep();
        clickCloseButton();
        SeeColumns();
        refresh();
    }

    @Step("TSE-T3819 (1.0) Авторизация в ЛК ЦС Обслуживания. Администратор")
    public void step05() {
        CommonFunctions.printStep();
        //Semak.VF
        new LoginPage().authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна //Semak.VF
    }

    @Step("TSE-T4516 (1.0) Переход по дереву навигации к справочнику \"ИГК\"")
    public void step06() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(60);
        seePageTitle("ИГК");
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step07() {
        CommonFunctions.printStep();
        clickViewButton();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step08() {
        CommonFunctions.printStep();
        clickCloseButton();
        SeeColumns();
        refresh();
    }

    @Step("TSE-T4233 (1.0) Авторизация в ЛК Клиента с ролью 603_06_02 ПУР КС.Администратор ЦС ФК")
    public void step09() {
        CommonFunctions.printStep();
        new LoginPage().authorization("8d220b3b-bc53-49c6-b1e0-6244c4afe716"); //Belonogov.VV
    }

    @Step("TSE-T4516 (1.0) Переход по дереву навигации к справочнику \"ИГК\"")
    public void step10() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(60);
        seePageTitle("ИГК");
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step11() {
        CommonFunctions.printStep();
        clickViewButton();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public void step12() {
        CommonFunctions.printStep();
        clickCloseButton();
        SeeColumns();
    }

    /**
     * Убеждаюсь, что нахожусь в нужном разделе
     * @param name Название раздела
     */
    private void seePageTitle(String name) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[contains(@class,\"z-menuitem-text\")][contains(.,\"" + name + "\")]"), 30);
    }

    /**
     * Нажимаю кнопку "Закрыть" в формуляре
     */
    private void clickCloseButton() {
        $(By.xpath("//button[@title=\"Закрыть окно\"]")).click();
        $(By.xpath("//div[contains(@class,\"doc-edit-frame\")]")).should(Condition.hidden, Duration.ofSeconds(20));
        $(By.xpath("//button[@title=\"Закрыть окно\"]")).should(Condition.hidden, Duration.ofSeconds(20));
    }

    /**
     * Тестирую открытие формуляра кнопкой "Просмотреть" на панели инструментов.
     */
    private void clickViewButton() {
        //Убеждаемся, что последняя строка таблицы отображается
        String myRow = "//tr[contains(@class,\"z-listitem\")]";
        String myRowLast = "[last()]";
        //Кликаем по последней строке
        CommonFunctions.waitForElementDisplayed(By.xpath(myRow + myRowLast), 30);
        $(By.xpath(myRow + myRowLast + "/td[@col=\"1\"]")).click();
        CommonFunctions.waitForElementDisplayed(By.xpath(myRow + myRowLast + "[contains(@title,\"Выделено: 1\")]"), 20);

        //Кликаем "Просмотреть"
        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@title=\"Открыть документ на просмотр\"]"), 30);
        $(By.xpath("//button[@title=\"Открыть документ на просмотр\"]")).click();

        //Вижу обязательные элементы окна с документом
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[contains(@class,\"docInfoLabel\")][contains(.,\"ИГК\")]"), 140);
        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@title=\"Закрыть окно\"]"), 20);
    }

    /**
     * Поиск колонки
     * @param text Название колонки
     */
    private void seeColumn(String text) {
        if ($(By.xpath("//div[contains(@class, 'z-listheader-content')][contains(.,'" + text + "')]")).exists()) {
            $(By.xpath("//div[contains(@class, 'z-listheader-content')][contains(.,'" + text + "')]")).scrollTo();
            Assert.assertTrue($(By.xpath("//div[contains(@class, 'z-listheader-content')][contains(.,\"" + text + "\")]")).isDisplayed());
            //CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class, \"z-listheader-content\")][contains(.,\"" + text + "\")]"), 20);
        }
    }

    /**
     * Поиск типового набора колонок
     */
    public void SeeColumns(){
        seeColumn("ИГК");
        seeColumn("Проект");
        seeColumn("Код вида средств");
        seeColumn("Наименование вида средств");
        seeColumn("Идентификационный код закупки");
        seeColumn("Код главы по БК");
        seeColumn("Ссылка на головной контракт");
        seeColumn("Дата начала действия записи");
        seeColumn("Дата окончания действия записи");
        seeColumn("Статус");
        seeColumn("Подлежит приостановлению");
        seeColumn("Расходная декларация");
        //seeColumn("КОО"); //Не видит
    }

}

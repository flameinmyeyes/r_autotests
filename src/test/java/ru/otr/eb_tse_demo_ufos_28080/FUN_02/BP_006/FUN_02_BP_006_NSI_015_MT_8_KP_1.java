package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import framework.Ways;
import functional.CommonFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;


////AC
public class FUN_02_BP_006_NSI_015_MT_8_KP_1 extends HooksTSE_DEMO_28080 {

    /**
     * НУЖНО ДОДЕЛАТЬ!
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4550
     * Проверка работоспособности справочника «ИГК» в ПУР КС (Архивная и актуальная)
     * задача:
     * Контрольный пример должен продемонстрировать, что в ПУР КС корректно реализован ссылка на головной контракт в справочнике «ИГК».
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_MT_8_KP_1";

    @Owner(value = "Якубов Алексей")
    @Description("МТ 8 КП 1 Проверка работоспособности справочника «ИГК» в ПУР КС (Архивная и актуальная)")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "\\screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        System.out.println("https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4550");
        System.out.println("Проверка работоспособности справочника «ИГК» в ПУР КС (Архивная и актуальная)");

        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        login("Loboda.KI");
    }

    @Step("TSE-T4516 (1.0) Переход по дереву навигации к справочнику \"ИГК\"")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(30);
        seePageTitle("ИГК");
    }

    @Step("Проверить что в справочнике появилась запись ")
    public void step03() {
        CommonFunctions.printStep();
        clickViewButton();
    }

    @Step("Проверить ссылку в колонке «Ссылка на головной контракт».")
    public void step04() {
        CommonFunctions.printStep();
        clickCloseButton();
        SeeColumns();
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step05() {
        CommonFunctions.printStep();
        login("Semak.VF");
    }

    @Step("Проверить заполнение полей \"Идентификационный номер госконтракта \", \"Проект\", \"Код вида средств\", \"Наименование вида средств\", \"Идентификационный код закупки\", \"Код главы по БК\", \"Дата начала действия записи\", \"Дата окончания действия записи\", \"Статус\", \"Подлежит приостановлению\", \"Расходная декларация\", \" Признак  КОО\"")
    public void step06() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК").waitForLoading(30);
        seePageTitle("ИГК");
    }

    @Step("Проверить отображение «Ссылка на головной контракт».")
    public void step07() {
        CommonFunctions.printStep();
        clickViewButton();
    }

    @Step("Проверить старую запись документа TSE-T3545")
    public void step08() {
        CommonFunctions.printStep();
        clickCloseButton();
        SeeColumns();
    }



    /**
     * Авторизация
     * @param login Значение в поле
     */
    private void login(String login) {
        Selenide.refresh();
        new LoginPage().authorization(login);
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
        new MainPage().resetUserSettings().waitForLoading(30);
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
        CommonFunctions.waitForElementDisplayed(By.xpath(myRow + myRowLast), 20);
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
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class, \"z-listheader-content\")][contains(.,\"" + text + "\")]"),20);
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
        seeColumn("КОО");
    }

}

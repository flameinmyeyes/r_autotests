package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.Ways;
import functional.CommonFunctions;
import functional.FileFunctions;
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
import framework.RunTestAgain;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_MT_8_1 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_MT_8_1\\";
    public String WAY_TEST_PREVIOUS = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_01_BP_003_MT_1_KP_5_1\\";

    @Owner(value="Ворожко Александр")
    @Description("МТ8.1.Создание нового АКР справочника \"Разделы лицевого счета\"")
    @Link(name="TSE-T5350", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T5350")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        WAY_TEST_PREVIOUS = setWay(WAY_TEST_PREVIOUS);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @Step("TSE-T3689 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Переход по дереву навигации к справочнику «Разделы лицевого счета» ")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Разделы лицевого счета").waitForLoading(30);
        new MainPage().resetUserSettings();
    }

    @Step("Выбрать в СФ запись справочника")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .setUserColumns("Дата и время формирования документа", "Номер выписки", "Дата выписки за", "Статус")
                .clickRowInList(FileFunctions.readValueFile(WAY_TEST_PREVIOUS + "docNum.txt"));
    }

    @Step("Проверить СФ и заполнение")
    public void step04() {
        CommonFunctions.printStep();
        // Проверяем поле "ИНН"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_inn']" +
                        "[@value='7722698789']"), 60);
        // Проверяем поле "КПП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_kpp']" +
                        "[@value='772201001']"), 60);
        // Проверяем поле "Код СВР/НУБП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_codesvr']" +
                        "[@value='450Э7888']"), 60);
        // Проверяем поле "Номер лицевого счета"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_personalacc']" +
                        "[@value='711Э7888001']"), 60);
        // Проверяем поле "Наименование"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_name']" +
                        "[@value='АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО " +
                        "ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\"']"), 60);
        // Проверяем поле "Код ЦС"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_codecs']" +
                        "[@value='7305']"), 60);
        // Проверяем поле "Наименование Центра специализации"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_namecs']" +
                        "[@value='Отдел № 24 Управления Федерального казначейства по г. Москве']"), 60);
    }

    @Step("TSE-T3723 (1.0) Просмотр формуляра")
    public void step05() {
        CommonFunctions.printStep();
        $(By.xpath("//tr[contains(@class,'z-listitem')]/td[@col='1']")).click();
        CommonFunctions.waitForElementDisplayed(By.xpath("//tr[contains(@class,'z-listitem')][contains(@title,'Выделено: 1')]"), 60);
        //Кликаем "Просмотреть"
        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@title='Открыть документ на просмотр']"), 60);
        new MainPage().clickButtonTitle("Открыть документ на просмотр");
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[contains(@class,'docInfoLabel')][contains(.,'ОКВ')]"), 60);
    }

    @Step("Проверить заполнение  полей")
    public void step06() {
        CommonFunctions.printStep();
        CommonFunctions.printStep();
        // Проверяем поле "ИНН"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_inn']" +
                        "[@value='7722698789']"), 60);
        // Проверяем поле "КПП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_kpp']" +
                        "[@value='772201001']"), 60);
        // Проверяем поле "Код СВР/НУБП"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_codesvr']" +
                        "[@value='450Э7888']"), 60);
        // Проверяем поле "Номер лицевого счета"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_personalacc']" +
                        "[@value='711Э7888001']"), 60);
        // Проверяем поле "Наименование"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_name']" +
                        "[@value='АКЦИОНЕРНОЕ ОБЩЕСТВО \"РОССИЙСКАЯ КОРПОРАЦИЯ РАКЕТНО-КОСМИЧЕСКОГО " +
                        "ПРИБОРОСТРОЕНИЯ И ИНФОРМАЦИОННЫХ СИСТЕМ\"']"), 60);
        // Проверяем поле "Код ЦС"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_codecs']" +
                        "[@value='7305']"), 60);
        // Проверяем поле "Наименование Центра специализации"
        CommonFunctions.waitForElementDisplayed(
                By.xpath("//div[@class='doc-edit-frame-center z-div']//input[@name='tf_doc_vr_namecs']" +
                        "[@value='Отдел № 24 Управления Федерального казначейства по г. Москве']"), 60);
    }

}

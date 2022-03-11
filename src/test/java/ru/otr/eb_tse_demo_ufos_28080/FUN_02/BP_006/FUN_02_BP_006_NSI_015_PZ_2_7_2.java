package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
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
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static com.codeborne.selenide.Selenide.*;


////AC
public class FUN_02_BP_006_NSI_015_PZ_2_7_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_015_PZ_2_7_2\\";
    public static String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    private static Date date;

    @Owner(value = "Ворожко Александр")
    @Description("NSI_015. ПЗ п. 2.7.2. Требования к СФ. Требования к действиям из списковой форм")
    @Link(name="TSE-T3181", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3181")
    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        WAY_DOWNLOADS = setWay(WAY_DOWNLOADS);

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
        step13();
        step14();
    }

    @Step("Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().
                authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("TSE-T4516 (1.0) Переход по дереву навигации к справочнику \"ИГК\"")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " ИГК")
                .resetUserSettings()
                .clickRowsInList(1,2);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.xlsx)   и нажать кнопку \"ОК\"")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Выберите шаблон')]"), 60, true);
//        $x("//*[contains(text(), 'Excel (*.xlsx)')]").shouldBe(Condition.visible).click();
        new MainPage()
                .clickButtonInBlock("Выберите шаблон", "OK");
        date = new Date();

        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Успешно завершена')]"), 60, true);
        String nameTime = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(date);

        String containsName = "PrintScroller_" + nameTime + ".xlsx";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        Assert.assertTrue(wayToSourceFile.contains(containsName));

        FileFunctions.waitForFileDownload(wayToSourceFile, 300);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xlsx");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xlsx",4);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Выберите шаблон')]"), 60, true);
        new MainPage()
                .clickWebElement("//*[contains(text(), 'Excel (Старый формат')]")
                .clickButtonInBlock("Выберите шаблон", "OK");
        date = new Date();

        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Успешно завершена')]"), 60, true);
        String nameTime = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(date);

        String containsName = "PrintScroller_" + nameTime + ".xls";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        Assert.assertTrue(wayToSourceFile.contains(containsName));

        FileFunctions.waitForFileDownload(wayToSourceFile, 300);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print.xls",10);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    public void step05() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка");
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Выберите шаблон')]"), 60, true);
        new MainPage()
                .clickWebElement("//*[contains(text(), 'OpenOffice')]")
                .clickButtonInBlock("Выберите шаблон", "OK");
        date = new Date();

        CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(), 'Успешно завершена')]"), 60, true);
        String nameTime = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(date);

        String containsName = "PrintScroller_" + nameTime + ".ods";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        Assert.assertTrue(wayToSourceFile.contains(containsName));

        FileFunctions.waitForFileDownload(wayToSourceFile, 300);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.ods");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print.ods",6);
    }

    @Step("Нажать кнопку  \"Обновить\"")
    public void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Обновить список документов (Alt+F5)");
    }

    @Step("Выбрать запись справочника. Нажать кнопку \"Просмотреть\"")
    public void step07() {
        CommonFunctions.printStep();
        new MainPage()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='doc-edit-frame z-vlayout']//*[contains(text(), 'ИГК')]"), 60);
    }

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Администратор (Пятница)")
    public void step08() {
        CommonFunctions.printStep();
        new LoginPage().
                authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
    }

    @Step("Переход по дереву навигации к справочнику «ИГК» ")
    public void step09() {
        CommonFunctions.printStep();
        step02();
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (*.xlsx)  и нажать кнопку \"ОК\"")
    public void step10() {
        CommonFunctions.printStep();
        step03();
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    public void step11() {
        CommonFunctions.printStep();
        step04();
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    public void step12() {
        CommonFunctions.printStep();
        step05();
    }

    @Step("Нажать кнопку  \"Обновить\"")
    public void step13() {
        CommonFunctions.printStep();
        step06();
    }

    @Step("Выбрать запись справочника. Нажать кнопку \"Просмотреть\"")
    public void step14() {
        CommonFunctions.printStep();
        step07();
    }
}

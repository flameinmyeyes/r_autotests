package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_026_MT_8_2 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_026_MT_8_2\\";

    @Owner(value = "Ворожко Александр")
    @Description("МТ8.2. Доступ записей справочника \"Банковские карты\"")
    @Link(name="TSE-T5653", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T5653")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})

    public void steps() {
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
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
    }

    @Step("Переход по дереву навигации к справочнику «Банковские карты»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Банковские карты");
    }

    @Step("Выбрать запись документа и нажать кнопку \"Просмотреть\"")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .clickRowsInList(1)
                .clickButtonTitle("Открыть документ на просмотр");
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'dialog-edit')]"), 120);
    }

    @Step("Закрыть  запись")
    private void step04() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Закрыть");
    }

    @Step("Нажать кнопу \"Обновить\"")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Обновить список документов (Alt+F5)");
    }

    @Step("Нажать на кнопку \"Печать списка\" выбрать шаблон Excel (*.xlsx)")
    private void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка");
        $x("//td/span[text()='Excel (*.xlsx)']").click();
        $x("//button[@title='OK']").click();

        // Дождаться загрузки файла
        new MainPage().waitForLoading(30);
        String path = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(
                new File(Ways.DOWNLOADS.getWay()), "PrintScroller_"
                        + new SimpleDateFormat("dd-MM-yyyy HH-mm").format(new Date())));
        path = setWay(path);
        FileFunctions.waitForFileDownload(path, 60);

        // Удалить файл
        if (new File(path).delete()) {
            System.out.println("Файл " + path + " удален");
        } else System.out.println("Файла " + path + " не обнаружено");
    }

    @Step("Нажать на кнопку \"Печать списка\" выбрать шаблон  Excel (Старый формат *.xls)")
    public void step07() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка");
        $x("//td/span[text()='Excel (Старый формат *.xls)']").click();
        $x("//button[@title='OK']").click();

        // Дождаться загрузки файла
        new MainPage().waitForLoading(30);
        String path = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(
                new File(Ways.DOWNLOADS.getWay()), "PrintScroller_"
                        + new SimpleDateFormat("dd-MM-yyyy HH-mm").format(new Date())));
        path = setWay(path);
        FileFunctions.waitForFileDownload(path, 60);

        // Удалить файл
        if (new File(path).delete()) {
            System.out.println("Файл " + path + " удален");
        } else System.out.println("Файла " + path + " не обнаружено");
    }

    @Step("Нажать на кнопку \"Печать списка\" выбрать шаблон  OpenOffice (*.ods)")
    public void step08() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка");
        $x("//td/span[text()='OpenOffice (*.ods)']").click();
        $x("//button[@title='OK']").click();

        // Дождаться загрузки файла
        new MainPage().waitForLoading(30);
        String path = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(
                new File(Ways.DOWNLOADS.getWay()), "PrintScroller_"
                        + new SimpleDateFormat("dd-MM-yyyy HH-mm").format(new Date())));
        path = setWay(path);
        FileFunctions.waitForFileDownload(path, 60);

        // Удалить файл
        if (new File(path).delete()) {
            System.out.println("Файл " + path + " удален");
        } else System.out.println("Файла " + path + " не обнаружено");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step09() {
        CommonFunctions.printStep();
        new LoginPage()
                .authorization("1e02feed-b421-4ea2-89bc-3a8f4aebee70"); //Карасев Алексей Наумович
    }
}

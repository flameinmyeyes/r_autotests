package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
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
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$x;

////AC
public class FUN_02_BP_006_NSI_032_MT_8_KP_6 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_032_MT_8_KP_6\\";

    @Owner(value = "Ворожко Александр")
    @Description("МТ 8 КП6 Проверка печати списка \"Критерии приостановления операций по ЛС\"")
    @Link(name="TSE-T4758", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4758")

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

    @Step("Нажать на кнопку \"Печать списка\" выбрать шаблон Excel (*.xlsx)")
    private void step03() {
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
    public void step04() {
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
    public void step05() {
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
}

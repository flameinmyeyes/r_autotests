package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_021_PZ_2_2 extends HooksTSE_DEMO_28080 {


    public static  String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_021_PZ_2_2\\";
    public static String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();
    public static Workbook workbook;
    private static FileInputStream fileStream;
    private static String printTime;
    private static Date date;

    @Description("NSI_021. ПЗ п. 2.2. Требования к печатной форме спр-ка")
    @Owner(value = "Ворожко Александр")
    @Link(name="TSE-T3197", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3197")

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
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot( WAY_TEST + "screen.png");
    }


    @Step("Шаг 1.Авторизоваться в ЛК ЦС Обслуживания ПУР КС")
    public static void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Группы причин отказов».")
    public static void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)> Справочники> Группы причин отказов")
                .resetUserSettings();
    }

    @Step("Выделить на СФ запись на статусе \"Актуальная\"")
    public static void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Статус", "Актуальная")
                .clickRowsInList(1);
    }

    @Step("На списковой форме справочника нажать на кнопку \"Печать списка\"")
    public static void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle(" Печать списка");

        if(!$(By.xpath("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//tr[contains(@style, 'background')]//span[text()='Excel (*.xlsx)']")).isDisplayed()) {
            $(By.xpath("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//span[text()='Excel (*.xlsx)']")).click();
            new MainPage().waitForLoading(60);
        }

        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        date = new Date();
        printTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
    }

    @Step("Шаг 5. Проверить формирование наименования Exсel-файла")
    public static void step05() throws IOException {
        CommonFunctions.printStep();

        String nameTime = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(date);

        String containsName = "PrintScroller_" + nameTime + ".xlsx";
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
        Assert.assertTrue(wayToSourceFile.contains(containsName));

        FileFunctions.waitForFileDownload(wayToSourceFile, 300);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xlsx");

        fileStream = new FileInputStream(WAY_TEST + "print.xlsx");
        workbook = new XSSFWorkbook(fileStream);
    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: В поле \"Тип документа\" указано значение «Группы причин отказов»")
    public static void step06() {
        CommonFunctions.printStep();
        String docType = workbook.getSheetAt(0).getRow(0).getCell(3, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
        Assert.assertEquals(docType, "Группы причин отказов");
    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: В поле \"Дата формирования\" указано значение даты формирования печатной формы по формату")
    public static void step07() {
        CommonFunctions.printStep();
        String docDate = workbook.getSheetAt(0).getRow(1).getCell(3, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue();
        Assert.assertEquals(docDate, printTime);
    }

    @Step("Открыть файл и проверить заполнение полей печатной формы: Поля со значениями реквизитов справочника соответствуют значениям из справочника")
    public static void step08() throws IOException {
        CommonFunctions.printStep();

        StringBuilder dataFromDoc = new StringBuilder();
        StringBuilder dataFromForm = new StringBuilder();

        for (int i = 2; i < 13; i++) {
            String data = $x("(//*[contains(@class, 'z-listitem')]//div)[" + i + "]").getText();
            if (!data.isEmpty() && !data.equals(" "))
                dataFromForm.append(data);
            dataFromForm.append(";");
        }

        Iterator<Cell> row = workbook.getSheetAt(0).getRow(4).cellIterator();
        row.next();
        while (row.hasNext()) {
            Cell cell = row.next();
            if (cell.getCellType() == CellType.STRING)
                dataFromDoc.append(cell.getStringCellValue()).append(";");
            else if (cell.getCellType() == CellType.NUMERIC)
                dataFromDoc.append(new SimpleDateFormat("dd.MM.yyyy").format(cell.getDateCellValue())).append(";");
        }

        Assert.assertEquals(dataFromDoc.toString(), dataFromForm.toString());
    }
}

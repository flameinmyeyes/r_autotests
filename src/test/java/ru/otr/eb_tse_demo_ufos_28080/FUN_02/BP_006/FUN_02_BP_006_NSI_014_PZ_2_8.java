package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

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
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_014_PZ_2_8 extends HooksTSE_DEMO_28080 {

    /**
     * NSI_014. ПЗ п. 2.8. Требования к печатной форме спр-ка
     */

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_014_PZ_2_8\\";
    public int rowsInSF;

    public Helpers helper = new Helpers();


    @Owner(value = "Якубов Алексей")
    @Description("NSI_014. ПЗ п. 2.8. Требования к печатной форме спр-ка")
    @Link(name="TSE-T2448", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T2448")
    @Test(groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    //@Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "osterov_eduard", "FUN_02", "BP_006"})
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

        step10();
        step11();
        step12();

        step13();
        step14();
        step15();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    private void step01() {
        CommonFunctions.printStep();
        //new LoginPage().authorization("Loboda.KI", "Oracle33");
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(30);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (*.xlsx)  и нажать кнопку \"ОК\"")
    private void step03() {
        CommonFunctions.printStep();
        //selectSeveralDocs(7);
        rowsInSF = 7;
        System.out.println("Выбираю " + rowsInSF + " строк");
        selectSeveralDocs(rowsInSF);



        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

//        $(By.xpath("//td[@class='z-cell']//span[text()='Excel (*.xlsx)']")).click();  -- дефолтный выбор
        new MainPage().waitForLoading(30);

        Calendar calendar = new GregorianCalendar();
        String date1 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 1);
        String date2 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        String containsName1 = "PrintScroller_" + date1 + ".xlsx";
        String containsName2 = "PrintScroller_" + date2 + ".xlsx";

        new MainPage()
                .clickButtonInBlock("Выберите шаблон", "OK")
                .waitForLoading(120);
        waitPrintingList();
        //CommonFunctions.wait(80);
//        FileFunctions.waitForFileDownload(Ways.DOWNLOADS.getWay(), 60);

        //Сформирован и выгружен на локальный компьтер файл печатной формы в формате XLS.
        String containsName = searchFileNameInDirectories(containsName1, containsName2, 120);
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()), containsName));
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xlsx");
    }

    @Step("TSE-T3689 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Исполнитель ЦС")
    private void step04() {
        CommonFunctions.printStep();
        //new LoginPage().authorization("Loboda.KI");
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    private void step05() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(30);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    private void step06() {
        CommonFunctions.printStep();
        selectSeveralDocs(7);
        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        $(By.xpath("//td[@class='z-cell']//span[text()='Excel (Старый формат *.xls)']")).click();
        new MainPage().waitForLoading(30);

        Calendar calendar = new GregorianCalendar();
        String date1 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 1);
        String date2 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        String containsName1 = "PrintScroller_" + date1 + ".xls";
        String containsName2 = "PrintScroller_" + date2 + ".xls";

        new MainPage()
                .clickButtonInBlock("Выберите шаблон", "OK")
                .waitForLoading(120);
        waitPrintingList();
        //CommonFunctions.wait(5);
//        FileFunctions.waitForFileDownload(Ways.DOWNLOADS.getWay(), 60);

        //Сформирован и выгружен на локальный компьтер файл печатной формы в формате XLS.
        String containsName = searchFileNameInDirectories(containsName1, containsName2, 120);
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()), containsName));
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.xls");
    }

    @Step("TSE-T3690 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Согласующий ЦС")
    private void step07() {
        CommonFunctions.printStep();
        //new LoginPage().authorization("Govorun.IK");
        new LoginPage().authorization("da9df7b3-cc44-439a-a347-45e2a0214785"); //Силаева Елена Васильевна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    private void step08() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(30);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    private void step09() {
        CommonFunctions.printStep();
        selectSeveralDocs(7);
        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        $(By.xpath("//td[@class='z-cell']//span[text()='OpenOffice (*.ods)']")).click();
        new MainPage().waitForLoading(30);

        Calendar calendar = new GregorianCalendar();
        String date1 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 1);
        String date2 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        String containsName1 = "PrintScroller_" + date1 + ".ods";
        String containsName2 = "PrintScroller_" + date2 + ".ods";

        new MainPage()
                .clickButtonInBlock("Выберите шаблон", "OK")
                .waitForLoading(60);
        waitPrintingList();
        //CommonFunctions.wait(80);

        //Сформирован и выгружен на локальный компьтер файл печатной формы в формате XLS.
        String containsName = searchFileNameInDirectories(containsName1, containsName2, 120);
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()), containsName));
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.ods");
    }

    @Step("Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Утверждающий ЦС")
    public void step10() {
        CommonFunctions.printStep();
        refresh();
        //new LoginPage().authorization("Belonogov.VV");
        new LoginPage().authorization("73faa96c-44d0-411f-9d16-85115f03f958"); //Пахомова Наталья Игоревна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    private void step11() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(30);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    private void step12() {
        CommonFunctions.printStep();
        selectSeveralDocs(7);
        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        $(By.xpath("//td[@class='z-cell']//span[text()='OpenOffice (*.ods)']")).click();
        new MainPage().waitForLoading(30);

        Calendar calendar = new GregorianCalendar();
        String date1 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 1);
        String date2 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        String containsName1 = "PrintScroller_" + date1 + ".ods";
        String containsName2 = "PrintScroller_" + date2 + ".ods";

        new MainPage()
                .clickButtonInBlock("Выберите шаблон", "OK")
                .waitForLoading(60);
        //CommonFunctions.wait(80);
        waitPrintingList();

        //Сформирован и выгружен на локальный компьтер файл печатной формы в формате XLS.
        String containsName = searchFileNameInDirectories(containsName1, containsName2, 120);
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()), containsName));
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.ods");
    }

    @Step("TSE-T3819 (1.0) Авторизация в ЛК ЦС Обслуживания (ТОФК Обслуживания) ЛС. Администратор")
    public void step13() {
        CommonFunctions.printStep();
        refresh();
        //new LoginPage().authorization("Semak.VF", "Oracle33");
        new LoginPage().authorization("dc02ea88-c09c-429a-9329-adf35f1d3513"); //Пятница Анна Сергеевна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
    }

    @Step("TSE-T3827 (1.0) Переход по дереву навигации к справочнику «Проекты»")
    private void step14() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Проекты").waitForLoading(30);
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    private void step15() {
        CommonFunctions.printStep();
        selectSeveralDocs(7);
        new MainPage()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        $(By.xpath("//td[@class='z-cell']//span[text()='OpenOffice (*.ods)']")).click();
        new MainPage().waitForLoading(30);

        Calendar calendar = new GregorianCalendar();
        String date1 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 1);
        String date2 = new SimpleDateFormat("dd-MM-yyyy HH-mm").format(calendar.getTime());
        String containsName1 = "PrintScroller_" + date1 + ".ods";
        String containsName2 = "PrintScroller_" + date2 + ".ods";

        new MainPage()
                .clickButtonInBlock("Выберите шаблон", "OK")
                .waitForLoading(60);

        waitPrintingList();
        //CommonFunctions.wait(80);

        //Сформирован и выгружен на локальный компьтер файл печатной формы в формате XLS.
        String containsName = searchFileNameInDirectories(containsName1, containsName2, 120);
        String wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()), containsName));
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print.ods");
    }

    public static String searchFileNameInDirectories(String containsName1, String containsName2, int seconds) {
        int n = 0;
        String containsName = "";
        boolean search = true;
        System.out.println("Ожидание загрузки файла: " + containsName1 + " или " + containsName2);
        while (search && n < seconds) {
            if (FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()), containsName1).exists()) {
                containsName = containsName1;
                search = false;
                continue;
            }
            if (FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()), containsName2).exists()) {
                containsName = containsName2;
                search = false;
                continue;
            }
            CommonFunctions.wait(5);
            n ++;
        } return containsName;
    }

    /**
     * Ожидаем сообщение об успешной печати.
    */
    private void waitPrintingList() {
        //CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Операция \"Печать списка\"']/ancestor::div[1]/p[contains(., 'Успешно завершена')]"), 180);
        //CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Операция \"Печать списка\"']/ancestor::div[1]/p[contains(., 'для просмотра результатов')]"), 180);
        CommonFunctions.wait(80);
    }

    /**
     * Выбрать несколько записей
     */
    private void selectSeveralDocs(int selectDocs) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-listboxfakepaging-body')]//tr[contains(@class, 'z-listitem')]//span[contains(@class, 'checkbox')]"), 40);
        if($x("//div[contains(@class,'z-listboxfakepaging-body')]//tr[contains(@class, 'z-listitem')]//span[contains(@class, 'checkbox')]").exists()) {
            //поочередно ставим галочку в чекбокс каждого элемента из массива
            for (int selStr = 1; selStr <= selectDocs; selStr++) {
                $(By.xpath("//div[contains(@class,'z-listboxfakepaging-body')]//tr[contains(@class, 'z-listitem')][" + selStr + "]//span[contains(@class, 'checkbox')]")).click();
                new MainPage().waitForLoading(10);
                new MainPage().closeShortForm();
                new MainPage().waitForLoading(10);
            }
        } else { //если столбца с чекбоксом нет
            System.out.println("Выбрать несколько строк не удалось. Требуется доработка метода!");
        }
    }



}

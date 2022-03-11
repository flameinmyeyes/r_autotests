package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class FUN_02_BP_006_NSI_024_PZ_2_8_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_024_PZ_2_8_2\\";
    private String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();

    @Owner(value="Балашов Илья")
    @Description("NSI_024. ПЗ п. 2.8.2. Требования к печатной форме спр-ка")
    @Link(name="TSE-T3222", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3222")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
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
        step15();
        step16();
        step17();
        step18();
        step19();
        step20();
        step21();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (*.xlsx)  и нажать кнопку \"ОК\"")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (*.xlsx)";
        if(!$x(window + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(window + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xlsx
        //Правило заполнения полей
        //Тип документа:	Почтовые уведомления
        //Дата формирования:	текущая дата
        //Наименование поля печатной формы        //Наименование реквизита формуляра        //Правило заполнения
        //ИНН        //ИНН        //Автоматически значение реквизита записи настоящего справочника
        //КПП        //КПП        //Автоматически значение реквизита записи настоящего справочника
        //Код СВР/НУБП        //Код СВР/НУБП        //Автоматически значение реквизита записи настоящего справочника
        //Полное наименование        //Полное наименование        //Автоматически значение реквизита записи настоящего справочника
        //Наименование документа        //Наименование документа        //Автоматически значение реквизита записи настоящего справочника
        //Дата начала действия записи        //Дата начала действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Дата окончания действия записи        //Дата окончания действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Статус        //Статус        //Автоматически значение реквизита записи настоящего справочника

        //PrintScroller_27-01-2022 12-27.xlsx
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xlsx";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xlsx";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_1.xlsx");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_1.xlsx",3);

        FileFunctions.compareXLS(WAY_TEST + "print_1.xlsx", WAY_TEST + "print_sample.xlsx", 0, 1,3,4);
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step04() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(5);
        new LoginPage().authorization("7806990d-3489-4496-9d20-03f31969a64e");
        //Пирогов Владимир Константинович
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step05() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (*.xlsx)  и нажать кнопку \"ОК\"")
    public void step06() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (*.xlsx)";
        if(!$x(window + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(window + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xlsx
        //Правило заполнения полей
        //Тип документа:	Почтовые уведомления
        //Дата формирования:	текущая дата
        //Наименование поля печатной формы        //Наименование реквизита формуляра        //Правило заполнения
        //ИНН        //ИНН        //Автоматически значение реквизита записи настоящего справочника
        //КПП        //КПП        //Автоматически значение реквизита записи настоящего справочника
        //Код СВР/НУБП        //Код СВР/НУБП        //Автоматически значение реквизита записи настоящего справочника
        //Полное наименование        //Полное наименование        //Автоматически значение реквизита записи настоящего справочника
        //Наименование документа        //Наименование документа        //Автоматически значение реквизита записи настоящего справочника
        //Дата начала действия записи        //Дата начала действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Дата окончания действия записи        //Дата окончания действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Статус        //Статус        //Автоматически значение реквизита записи настоящего справочника

        //PrintScroller_27-01-2022 12-27.xlsx
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xlsx";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xlsx";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_2.xlsx");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_2.xlsx",3);

        FileFunctions.compareXLS(WAY_TEST + "print_2.xlsx", WAY_TEST + "print_sample.xlsx", 0, 1,3,4);
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step07() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(5);
        new LoginPage().authorization("f5571d67-e5d4-4e3b-b490-78d62cb3a5f5");
        //Бекренева Наталья Вячеславовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step08() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (*.xlsx)  и нажать кнопку \"ОК\"")
    public void step09() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (*.xlsx)";
        if(!$x(window + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(window + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xlsx
        //Правило заполнения полей
        //Тип документа:	Почтовые уведомления
        //Дата формирования:	текущая дата
        //Наименование поля печатной формы        //Наименование реквизита формуляра        //Правило заполнения
        //ИНН        //ИНН        //Автоматически значение реквизита записи настоящего справочника
        //КПП        //КПП        //Автоматически значение реквизита записи настоящего справочника
        //Код СВР/НУБП        //Код СВР/НУБП        //Автоматически значение реквизита записи настоящего справочника
        //Полное наименование        //Полное наименование        //Автоматически значение реквизита записи настоящего справочника
        //Наименование документа        //Наименование документа        //Автоматически значение реквизита записи настоящего справочника
        //Дата начала действия записи        //Дата начала действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Дата окончания действия записи        //Дата окончания действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Статус        //Статус        //Автоматически значение реквизита записи настоящего справочника

        //PrintScroller_27-01-2022 12-27.xlsx
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xlsx";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xlsx";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_3.xlsx");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_3.xlsx",3);

        FileFunctions.compareXLS(WAY_TEST + "print_3.xlsx", WAY_TEST + "print_sample.xlsx", 0, 1,3,4);
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step10() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(5);
        new LoginPage().authorization("f16d2b47-36e2-4e90-b32b-1ecbfaed54cd");
        //Кузьминых Богдан Олегович
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step11() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    public void step12() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!$x(window + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(window + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xls
        //Правило заполнения полей
        //Тип документа:	Почтовые уведомления
        //Дата формирования:	текущая дата
        //Наименование поля печатной формы        //Наименование реквизита формуляра        //Правило заполнения
        //ИНН        //ИНН        //Автоматически значение реквизита записи настоящего справочника
        //КПП        //КПП        //Автоматически значение реквизита записи настоящего справочника
        //Код СВР/НУБП        //Код СВР/НУБП        //Автоматически значение реквизита записи настоящего справочника
        //Полное наименование        //Полное наименование        //Автоматически значение реквизита записи настоящего справочника
        //Наименование документа        //Наименование документа        //Автоматически значение реквизита записи настоящего справочника
        //Дата начала действия записи        //Дата начала действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Дата окончания действия записи        //Дата окончания действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Статус        //Статус        //Автоматически значение реквизита записи настоящего справочника

        //PrintScroller_27-01-2022 12-27.xls
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xls";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xls";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_1.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_1.xls",3);

        FileFunctions.compareXLS(WAY_TEST + "print_1.xls", WAY_TEST + "print_sample.xls", 0, 1,3,4);
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step13() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(5);
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
        //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step14() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    public void step15() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!$x(window + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(window + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xls
        //Правило заполнения полей
        //Тип документа:	Почтовые уведомления
        //Дата формирования:	текущая дата
        //Наименование поля печатной формы        //Наименование реквизита формуляра        //Правило заполнения
        //ИНН        //ИНН        //Автоматически значение реквизита записи настоящего справочника
        //КПП        //КПП        //Автоматически значение реквизита записи настоящего справочника
        //Код СВР/НУБП        //Код СВР/НУБП        //Автоматически значение реквизита записи настоящего справочника
        //Полное наименование        //Полное наименование        //Автоматически значение реквизита записи настоящего справочника
        //Наименование документа        //Наименование документа        //Автоматически значение реквизита записи настоящего справочника
        //Дата начала действия записи        //Дата начала действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Дата окончания действия записи        //Дата окончания действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Статус        //Статус        //Автоматически значение реквизита записи настоящего справочника

        //PrintScroller_27-01-2022 12-27.xls
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xls";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xls";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_2.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_2.xls",3);

        FileFunctions.compareXLS(WAY_TEST + "print_2.xls", WAY_TEST + "print_sample.xls", 0, 1,3,4);
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step16() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(5);
        new LoginPage().authorization("da9df7b3-cc44-439a-a347-45e2a0214785");
        //Силаева Елена Васильевна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step17() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон Excel (Старый формат *.xls)   и нажать кнопку \"ОК\"")
    public void step18() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "Excel (Старый формат *.xls)";
        if(!$x(window + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(window + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.xls
        //Правило заполнения полей
        //Тип документа:	Почтовые уведомления
        //Дата формирования:	текущая дата
        //Наименование поля печатной формы        //Наименование реквизита формуляра        //Правило заполнения
        //ИНН        //ИНН        //Автоматически значение реквизита записи настоящего справочника
        //КПП        //КПП        //Автоматически значение реквизита записи настоящего справочника
        //Код СВР/НУБП        //Код СВР/НУБП        //Автоматически значение реквизита записи настоящего справочника
        //Полное наименование        //Полное наименование        //Автоматически значение реквизита записи настоящего справочника
        //Наименование документа        //Наименование документа        //Автоматически значение реквизита записи настоящего справочника
        //Дата начала действия записи        //Дата начала действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Дата окончания действия записи        //Дата окончания действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Статус        //Статус        //Автоматически значение реквизита записи настоящего справочника

        //PrintScroller_27-01-2022 12-27.xls
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".xls";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".xls";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_3.xls");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_3.xls",3);

        FileFunctions.compareXLS(WAY_TEST + "print_3.xls", WAY_TEST + "print_sample.xls", 0, 1,3,4);
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step19() {
        CommonFunctions.printStep();
        refresh();
        CommonFunctions.wait(5);
        new LoginPage().authorization("73faa96c-44d0-411f-9d16-85115f03f958");
        //Пахомова Наталья Игоревна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Почтовые уведомления».")
    public void step20() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Почтовые уведомления");
    }

    @Step("Нажать кнопку  \"Печать списка\" ,выбрать шаблон  OpenOffice (*.ods)   и нажать кнопку \"ОК\"")
    public void step21() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle(" Печать списка")
                .waitForLoading(60);

        String window = "//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']";
        String printFormat = "OpenOffice (*.ods)";
        if(!$x(window + "//tr[contains(@style, 'background')]//span[text()='" + printFormat + "']").isDisplayed()) {
            $x(window + "//span[text()='" + printFormat + "']").click();
            new MainPage().waitForLoading(60);
        }
        new MainPage().clickButtonInBlock("Выберите шаблон", "OK");
        String printTime = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");
        CommonFunctions.wait(30);
        String printTime_2 = CommonFunctions.dateToday("dd-MM-yyyy HH-mm");

        //Должна сформироваться печать списка формате *.ods
        //Правило заполнения полей
        //Тип документа:	Почтовые уведомления
        //Дата формирования:	текущая дата
        //Наименование поля печатной формы        //Наименование реквизита формуляра        //Правило заполнения
        //ИНН        //ИНН        //Автоматически значение реквизита записи настоящего справочника
        //КПП        //КПП        //Автоматически значение реквизита записи настоящего справочника
        //Код СВР/НУБП        //Код СВР/НУБП        //Автоматически значение реквизита записи настоящего справочника
        //Полное наименование        //Полное наименование        //Автоматически значение реквизита записи настоящего справочника
        //Наименование документа        //Наименование документа        //Автоматически значение реквизита записи настоящего справочника
        //Дата начала действия записи        //Дата начала действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Дата окончания действия записи        //Дата окончания действия записи        //Автоматически значение реквизита записи настоящего справочника
        //Статус        //Статус        //Автоматически значение реквизита записи настоящего справочника

        //PrintScroller_27-01-2022 12-27.ods
        String containsName = null;
        String wayToSourceFile = null;
        try {
            containsName = "PrintScroller_" + printTime + ".ods";
            wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            if (wayToSourceFile == null) {
                containsName = "PrintScroller_" + printTime_2 + ".ods";
                wayToSourceFile = String.valueOf(FileFunctions.searchFileInDirectoriesRecursive(new File(setWay(WAY_DOWNLOADS)), containsName));
            }
        } catch (Exception e) {}

        FileFunctions.waitForFileDownload(wayToSourceFile,400);
        FileFunctions.moveFileToFolder(wayToSourceFile,WAY_TEST + "print_1.ods");
        FileFunctions.isNotEmptyFile(WAY_TEST + "print_1.ods",3);

        FileFunctions.compareODS(WAY_TEST + "print_1.ods", WAY_TEST + "print_sample.ods", 0, 1,3,4);
    }

}

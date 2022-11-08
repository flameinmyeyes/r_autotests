package ru.exportcenter.test.fito;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import functions.file.FileFunctions;
import functions.file.PropertiesHandler;
import functions.file.XMLHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.io.File;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_3_07_04 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_04/";
    private final String WAY_FILES = Ways.TEST.getWay() + "/fito/Test_3_07_04/"; //переменная нужна, т.к. значение WAY_TEST будет меняться при вызове текущего теста из других тестов
    private final String WAY_TEMP_FILE = "src/test/java/ru/exportcenter/test/fito/";
    private final String FILE_NAME_BC_1 = "ResponseNotDogovorBC1.xml";
    private final String FILE_NAME_BC_2 = "2ResponseSuccessBC2.xml";
    private final String FILE_NAME_BC_3_1 = "1ResponseSuccessBC3_1.xml";
    private final String FILE_NAME_BC_3_2 = "1ResponseSuccessBC3_2.xml";
    private final String FILE_NAME_BC_3_3 = "1ResponseSuccessBC3_3.xml";
    private final String FILE_NAME_BC_3_4 = "1ResponseSuccessBC3_4.xml";

    private String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/fito/Test_3_07_04/" + "Test_3_07_04_properties.xml";
    private Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    private String token;

    private final String BASE_URI = "https://lk.t.exportcenter.ru";

    private String processID;

    private String docNum;
    private String guid;
    private String zayavlenieRegistrationNumber;
    private String aktNumber;
    private String zKFSNumber;

    @Owner(value = "Балашов Илья")
    @Description("3.07.04 (Тест контур) Сценарий с заключением нового договора на установление КФС")
    @Link(name = "Test_3_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175255752")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step06();
        step07();
        step08();
        step09();
        step10();
        step11();
        step12();
//        step13();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие: выполнить шаги 1-5 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188852997
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
        test_3_07_01.step01();
        test_3_07_01.step02();
        test_3_07_01.step03();
        test_3_07_01.step04();
        test_3_07_01.step05();
    }

    @Step("Шаг 6. Редактирование XML файла ответа при получении сведений из реестра договоров с аккредитованными организациями (ВС 1)")
    public void step06() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_1);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_1;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //обновляем XML файл
        guid = CommonFunctions.generateUUID();
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
    }

    @Step("Шаг 7. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 1)")
    public void step07() {
        CommonFunctions.printStep();
        token = RESTFunctions.getAccessToken(new Test_3_07_01().P.getProperty("Авторизация.URL"), "bpmn_admin");
        System.out.println("token: " + token);

        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_1;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "AccOrgContrRequestMessage";

        //читаем processID из файла
        processID = JupyterLabIntegration.getFileContent(WAY_TEST + "processID.txt");
        System.out.println("processID: " + processID);

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 8. Блок \"Добавление продукции\"")
    public void step08() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Добавление продукции")
                    .inField("Каталог продукции").selectValue(P.getProperty("Добавление продукции.Каталог продукции").split(" ")[0]).assertNoControl().assertValue(P.getProperty("Добавление продукции.Каталог продукции"))
                    .inField("Код ТН ВЭД").assertValue(P.getProperty("Добавление продукции.Код ТН ВЭД"))
                    .inField("Тип продукции").selectValue(P.getProperty("Добавление продукции.Тип продукции")).assertNoControl().assertValue()
                    .inField("Дополнительная информация о продукции. Например, страна производства (произрастания) продукции, сорт продукции и т.д.").inputValue(P.getProperty("Добавление продукции.Дополнительная информация о продукции")).assertNoControl().assertValue()
                    .inField("Вес груза (нетто), кг").inputValue(P.getProperty("Добавление продукции.Вес груза (нетто)")).assertNoControl().assertValue()
                    .inField("Особые единицы измерения").selectValue(P.getProperty("Добавление продукции.Особые единицы измерения")).assertNoControl().assertValue()
                    .inField("Количество в особых единицах измерения").inputValue(P.getProperty("Добавление продукции.Количество в особых единицах измерения")).assertNoControl().assertValue()
                    .inField("Описание упаковки").selectValue(P.getProperty("Добавление продукции.Описание упаковки")).assertNoControl().assertValue()
                    .inField("Размещение продукции").clickByLocator("//ancestor::div//span[contains(text(),'Навалом (наливом)')][last()]")
                    .inField("Наличие отличительных знаков (маркировки). Например, номера партий, серийные номера или названия торговых марок. ").setCheckboxON().assertCheckboxON()
                    .inField("Номер партии зерна (продуктов переработки зерна)").inputValue(P.getProperty("Добавление продукции.Номер партии зерна")).assertNoControl().assertValue()
                    //Место происхождения( произрастания) продукции
                    .inField("Страна").selectValue(P.getProperty("Добавление продукции.Страна")).assertNoControl().assertValue()
                    .inField("Регион").selectValue(P.getProperty("Добавление продукции.Регион")).assertNoControl().assertValue()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 4 из 9']");
    }

    @Step("Шаг 9. Блок \"Договор на установление карантинного фитосанитарного состояния\"")
    public void step09() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Договор на установление карантинного  фитосанитарного состояния")
                    .inField("Орган инспекции для заключения договора").selectValue(P.getProperty("Договор.Орган инспекции для заключения договора")).assertNoControl().assertValue()
                    .inField("ФИО подписанта договора").assertValue(P.getProperty("Договор.ФИО подписанта договора")).assertNoControl()
                    .inField("Должность").assertValue(P.getProperty("Договор.Должность")).assertNoControl()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 5 из 9']");
    }

    @Step("Шаг 10. Блок \"Запрос отбора проб\"")
    public void step10() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Запрос отбора проб")
                    .inField("Территориальное управление Россельхознадзора").selectValue(P.getProperty("Запрос отбора проб.Территориальное управление Россельхознадзора")).assertNoControl().assertValue()
                    .inField("Планируемая дата отбора проб").inputValue(DateFunctions.dateShift("dd.MM.yyyy", +1)).assertNoControl().assertValue()
                    .inField("Планируемое время отбора проб").inputValue(P.getProperty("Запрос отбора проб.Планируемое время отбора проб")).assertNoControl().assertValue()
                    .inField("Место отбора проб").inputValue(P.getProperty("Запрос отбора проб.Место отбора проб")).assertNoControl().assertValue()
                    .inField("Дополнительные требования к исследованиям ").inputValue(P.getProperty("Запрос отбора проб.Дополнительные требования к исследованиям")).assertNoControl().assertValue()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Направить на проверку")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 6 из 9']");
    }

    @Step("Шаг 11. Редактирование XML ответа проверки сведений из проекта заявления в Россельхознадзоре (ВС 2)")
    public void step11() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_2);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_2;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
        XMLHandler.updateXML(wayFile, "common:ProbeDate", DateFunctions.dateShift("yyyy-MM-dd", +1)); //2022-10-04
        XMLHandler.updateXML(wayFile, "common:ProbeTime", DateFunctions.dateToday("yyyy-MM-dd'T'") + P.getProperty("Запрос отбора проб.Планируемое время отбора проб") + ":00"); //2022-10-04T14:00:00
    }

    @Step("Шаг 12. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 2)")
    public void step12() {
        CommonFunctions.printStep();
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_2;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "CheckAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 13. ")
    public void step13() {
        CommonFunctions.printStep();

    }

    public void refreshTab(String expectedXpath, int times) {
        if (!$x(expectedXpath).isDisplayed()) {
            System.out.println("Refreshing...");
        }
        for (int i = 0; i < times; i++) {
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            new GUIFunctions().waitForLoading();
            CommonFunctions.wait(1);
        }
    }

    private static void deleteFileIfExists(File file) {
        if(file.exists()) {
            System.out.print("Временный файл обнаружен");
            file.delete();
            System.out.println(" и успешно удален");
        } else {
            System.out.println("Временный файл не обнаружен, удаление не требуется");
        }
    }

}

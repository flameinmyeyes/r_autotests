package ru.exportcenter.test.fito.Test_3_07_21;

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
import ru.exportcenter.test.fito.Test_3_07_01;

import java.io.File;
import java.util.Properties;

public class Test_3_07_21_1 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_1/";
    private final String WAY_FILES = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_1/"; //переменная нужна, т.к. значение WAY_TEST будет меняться при вызове текущего теста из других тестов
    private final String WAY_TEMP_FILE = "src/test/java/ru/exportcenter/test/fito/Test_3_07_21/";
    private final String FILE_NAME_BC_1 = "ResponseFailDataBC1.xml";

    private final String BASE_URI = "https://lk.t.exportcenter.ru";
    private String processID;

    private String docNum;
    private String guid;
    private String token;

    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_1/" + "Test_3_07_21_1_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("3.07.21.1 (Г) ВС 1")
    @Link(name = "Test_3_07_21_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=196796010")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step06();
        step07();
        step08();
        step09();
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
        this.processID = JupyterLabIntegration.getFileContent(test_3_07_01.WAY_TEST + "processID.txt");
        this.docNum = JupyterLabIntegration.getFileContent(test_3_07_01.WAY_TEST + "docNum.txt");
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

        token = RESTFunctions.getAccessToken(P.getProperty("Авторизация.URL"), "bpmn_admin");
        System.out.println("token: " + token);

        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_1;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "AccOrgContrRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл

    }

    @Step("Шаг 8. Блок \"Добавление продукции\"")
    public void step08() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inContainer("Добавление продукции")
                .clickByLocator("//*[text()='Добавить +']")
                .inField("Каталог продукции").selectValue(P.getProperty("Добавление продукции.Каталог продукции").split(" ")[0]).assertNoControl().assertValue(P.getProperty("Добавление продукции.Каталог продукции"))
                .inField("Код ТН ВЭД").assertValue(P.getProperty("Добавление продукции.Код ТН ВЭД"))
                .inField("Тип продукции").selectValue(P.getProperty("Добавление продукции.Тип продукции")).assertNoControl().assertValue()
                .inField("Дополнительная информация о продукции. Например, страна производства (произрастания) продукции, сорт продукции и т.д.").inputValue(P.getProperty("Добавление продукции.Дополнительная информация о продукции")).assertNoControl().assertValue()
                .inField("Вес груза (нетто), кг").inputValue(P.getProperty("Добавление продукции.Вес груза (нетто)")).assertNoControl()
                .inField("Особые единицы измерения").selectValue(P.getProperty("Добавление продукции.Особые единицы измерения")).assertNoControl().assertValue()
//                .inField("Количество в особых единицах измерения").inputValue(P.getProperty("Добавление продукции.Количество в особых единицах измерения")).assertNoControl().assertValue()
                .inField("Описание упаковки").selectValue(P.getProperty("Добавление продукции.Описание упаковки")).assertNoControl().assertValue()
                .inField("Размещение продукции").clickByLocator("//ancestor::div//span[contains(text(),'Навалом (наливом)')][last()]")
                .inField("Наличие отличительных знаков (маркировки). Например, номера партий, серийные номера или названия торговых марок. ").setCheckboxON().assertCheckboxON()
                .inField("Номер партии зерна (продуктов переработки зерна)").inputValue(P.getProperty("Добавление продукции.Номер партии зерна")).assertNoControl().assertValue()
                //Место происхождения( произрастания) продукции
                .inField("Страна").selectValue(P.getProperty("Добавление продукции.Страна")).assertNoControl().assertValue()
                .inField("Регион").selectValue(P.getProperty("Добавление продукции.Регион")).assertNoControl().assertValue()
                .clickButton("Сохранить")

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 4 из 9']");

    }

    @Step("Шаг 9. Проверка уведомления.")
    public void step09() {
        CommonFunctions.printStep();

        //нажать на список уведомлений
        new GUIFunctions().clickByLocator("//button[contains(@class,'NotificationBadge')]")
                        .waitForElementDisplayed("//*[contains(text(),'Получение ЗКФС. Технический сбой. Заявление № "+docNum+"')]");
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

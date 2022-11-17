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

import static com.codeborne.selenide.Selenide.switchTo;

public class Test_3_07_21_4 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_4/";
    private final String WAY_FILES = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_4/"; //переменная нужна, т.к. значение WAY_TEST будет меняться при вызове текущего теста из других тестов
    private final String WAY_TEMP_FILE = "src/test/java/ru/exportcenter/test/fito/Test_3_07_21_4/";
    private final String FILE_NAME_BC_3_2 = "ResponseFailDataBC3_2.xml";

    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_4/" + "Test_3_07_21_4_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    private final String BASE_URI = "https://lk.t.exportcenter.ru";
    private String processID;

    private String zayavlenieRegistrationNumber;
    private String aktNumber;
    private String docNum;
    private String guid;
    private String token;

    @Owner(value = "Петрищев Руслан")
    @Description("3.07.21.4 (Г) ВС 3 ответ 2")
    @Link(name = "Test_3_07_21_4", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=196796021")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step20();
        step21();
        step22();
        step23();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие: выполнить шаги 1-19 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188852997
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
        test_3_07_01.step01();
        test_3_07_01.step02();
        test_3_07_01.step03();
        test_3_07_01.step04();
        test_3_07_01.step05();
        test_3_07_01.step06();
        test_3_07_01.step07();
        test_3_07_01.step08();
        test_3_07_01.step09();
        test_3_07_01.step10();
        test_3_07_01.step11();
        test_3_07_01.step12();
        test_3_07_01.step13();
        test_3_07_01.step14();
        test_3_07_01.step15();
        test_3_07_01.step16();
        test_3_07_01.step17();
        test_3_07_01.step18();
        test_3_07_01.step19();
        this.guid = test_3_07_01.guid;
        this.zayavlenieRegistrationNumber = JupyterLabIntegration.getFileContent(test_3_07_01.WAY_TEST + "zayavlenieRegistrationNumber.txt");
        this.processID = JupyterLabIntegration.getFileContent(test_3_07_01.WAY_TEST + "processID.txt");
        this.docNum = JupyterLabIntegration.getFileContent(test_3_07_01.WAY_TEST + "docNum.txt");
    }

    @Step("Шаг 20. Редактирование XML ответа 2 для 3 ВС")
    public void step20() {
        CommonFunctions.printStep();

        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_3_2);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_2;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //генерируем и сохраняем номер заявления в файл
        aktNumber = CommonFunctions.randomNumber(100, 999) + "-" + CommonFunctions.randomNumber(100, 999); //100-100
        System.out.println("aktNumber: " + aktNumber);
        JupyterLabIntegration.uploadTextContent(aktNumber, WAY_TEST, "aktNumber.txt");

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-07T11:18:52
        XMLHandler.updateXML(wayFile, "common:AktDate", DateFunctions.dateToday("yyyy-MM-dd")); //2022-10-07
        XMLHandler.updateXML(wayFile, "common:AktNumber", guid); //100-100
    }

    @Step("Шаг 21. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 3)")
    public void step21() {
        CommonFunctions.printStep();

        token = RESTFunctions.getAccessToken(P.getProperty("Авторизация.URL"), "bpmn_admin");
        System.out.println("token: " + token);

        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_2;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "SendAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 22.  Ознакомление с информацией об отборе проб")
    public void step22() {
        CommonFunctions.printStep();

        //Нажать на ссылку с номером заявки
        new GUIFunctions().clickByLocator("//*[contains(text(),'"+zayavlenieRegistrationNumber+"')]");

        //Нажать "Уйти"
        switchTo().alert().accept();

        //Нажать "Продолжить"
        new GUIFunctions()
                .waitForElementDisplayed("//*[text()='Продолжить']", 30)
                .clickButton("Продолжить");
    }

    @Step("Шаг 23. Проверка уведомления в ЕЛК. ")
    public void step23() {
        CommonFunctions.printStep();

        //нажать на список уведомлений
        new GUIFunctions()
                .clickByLocator("//button[contains(@class,'NotificationBadge')]")
                .waitForElementDisplayed("//*[contains(text(),'Получение ЗКФС. Технический сбой. Заявление № "+zayavlenieRegistrationNumber+"')]");
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

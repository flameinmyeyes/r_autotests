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

public class Test_3_07_21_2 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_2/";
    private final String WAY_FILES = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_2/"; //переменная нужна, т.к. значение WAY_TEST будет меняться при вызове текущего теста из других тестов
    private final String WAY_TEMP_FILE = "src/test/java/ru/exportcenter/test/fito/Test_3_07_21_2/";
    private final String FILE_NAME_BC_2 = "ResponseFailDataBC2.xml";

    private final String BASE_URI = "https://lk.t.exportcenter.ru";
    private String processID;

    private String docNum;
    private String guid;
    private String token;

    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/fito/Test_3_07_21/Test_3_07_21_2/" + "Test_3_07_21_2_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("3.07.21.2 (Г) ВС 2")
    @Link(name = "Test_3_07_21_2", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=196796014")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step11();
        step12();
        step13();
        step14();
//        step15();
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
        test_3_07_01.step06();
        test_3_07_01.step07();
        test_3_07_01.step08();
        test_3_07_01.step09();
        test_3_07_01.step10();
        this.guid = test_3_07_01.guid;
        this.processID = JupyterLabIntegration.getFileContent(test_3_07_01.WAY_TEST + "processID.txt");
        this.docNum = JupyterLabIntegration.getFileContent(test_3_07_01.WAY_TEST + "docNum.txt");
        System.out.println("guid: " + guid);
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
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T13:22:27
        XMLHandler.updateXML(wayFile, "common:ProbeDate", DateFunctions.dateShift("yyyy-MM-dd", +1)); ; //2022-10-31
        XMLHandler.updateXML(wayFile, "common:ProbeTime", DateFunctions.dateToday("yyyy-MM-dd'T'") + P.getProperty("Запрос отбора проб.Планируемое время отбора проб") + ":00"); //2022-10-31T14:00:00
    }

    @Step("Шаг 12. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 2)")
    public void step12() {
        CommonFunctions.printStep();

        token = RESTFunctions.getAccessToken(P.getProperty("Авторизация.URL"), "bpmn_admin");
        System.out.println("token: " + token);

        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_2;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "CheckAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл

    }

    @Step("Шаг 13. Получение результата проверки сведений")
    public void step13() {
        CommonFunctions.printStep();

        //Нажать на ссылку с номером заявки
        new GUIFunctions().clickByLocator("//*[contains(text(),'"+docNum+"')]");

        //Нажать "Уйти"
        switchTo().alert().accept();

        //Нажать "Продолжить"
        new GUIFunctions()
                .waitForElementDisplayed("//*[text()='Продолжить']", 30)
                .clickButton("Продолжить");
    }

    @Step("Шаг 14. Проверка уведомления в ЕЛК. ")
    public void step14() {
        CommonFunctions.printStep();

        //нажать на список уведомлений
        new GUIFunctions()
                .clickByLocator("//button[contains(@class,'NotificationBadge')]")
                .waitForElementDisplayed("//*[contains(text(),'Получение ЗКФС. Технический сбой. Заявление № "+docNum+"')]");
    }

    @Step("Шаг 15. Проверка уведомления почта")
    public void step15() {
        CommonFunctions.printStep();

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

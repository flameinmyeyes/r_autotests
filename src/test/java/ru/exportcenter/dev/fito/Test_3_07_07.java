package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.mdm_functions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.io.File;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_3_07_07 extends Hooks {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/Test_3_07_07/";
    private final String WAY_FILES = Ways.DEV.getWay() + "/fito/Test_3_07_07/"; //переменная нужна, т.к. значение WAY_TEST будет меняться при вызове текущего теста из других тестов
    private final String WAY_TEMP_FILE = "src/test/java/ru/exportcenter/dev/fito/";
    private final String FILE_NAME_BC_1 = "ResponseSuccess1.xml";
    private final String FILE_NAME_BC_2 = "1ResponseSuccessBC2.xml";
    private final String FILE_NAME_BC_3_1 = "1ResponseSuccessBC3_1.xml";
    private final String FILE_NAME_BC_3_2 = "1ResponseSuccessBC3_2.xml";
    private final String FILE_NAME_BC_3_3 = "1ResponseSuccessBC3_3.xml";
    private final String FILE_NAME_BC_3_4 = "1ResponseSuccessBC3_4.xml";

//    public String WAY_TO_PROPERTIES = Ways.DEV.getWay() + "/fito/Test_3_07_07/" + "Test_3_07_01_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    private String processID;
    private String token;
    private String baseURI = "http://bpmn-api-service.bpms-dev.d.exportcenter.ru/";

    private String docNum;
    private String guid;
    private String zayavlenieRegistrationNumber;
    private String aktNumber;
    private String zKFSNumber;

    @Owner(value = "Балашов Илья")
    @Description("3.07.07 Завершение услуги по таймауту при подписании")
    @Link(name = "Test_3_07_07", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183194042")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
//        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
//        test_3_07_01.WAY_TEST = this.WAY_TEST;
//        test_3_07_01.step01();
//        test_3_07_01.step03();
//        test_3_07_01.step04();
//        test_3_07_01.step05();
//        test_3_07_01.step06();
//        test_3_07_01.step07();
//        test_3_07_01.step08();
//        test_3_07_01.step09();
//        test_3_07_01.step10();
//        test_3_07_01.step11();
//        test_3_07_01.step12();
//        test_3_07_01.step13();
//        step14();
//        step15();
//        step16();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Шаг 1.  Внесение изменений в значения справочника \"Настройка сервиса \"Запрос заключения о карантинном фитосанитарном состоянии\"")
    public void step01() {
        CommonFunctions.printStep();
        //В новой вкладке перейти по ссылке https://mdm.t.exportcenter.ru/catalog/d6f7a2b8-b91b-437c-8645-9744b2aece5c
        open("https://mdm.t.exportcenter.ru/catalog/d6f7a2b8-b91b-437c-8645-9744b2aece5c");

        //Необходимо внести логин/пароль: mdm_admin/password
        //Необходимо нажать "Войти"
        new mdm_functions().authorization("mdm_admin", "password");

        //В поле "Укажите поисковый запрос" укажите "Истечение срока ожиданий действий пользователя при подписании заявления (1.8)" и нажмите "Поиск"
        new mdm_functions().inputInSearchField("Истечение срока ожиданий действий пользователя при подписании заявления (1.8)");

        //Нажмите два раза на поле "Истечение срока ожиданий действий пользователя при подписании заявления (1.8)"
        new mdm_functions().openSearchResult("Истечение срока ожиданий действий пользователя при подписании заявления (1.8)");

        //Нажать "Изменить"
        new mdm_functions().clickButtonByText("Изменить");

        //Изменить значения "Тип параметра" - "Время в минутах", "Значение" - "3", "Тип значения" - "Время в минутах"
        new mdm_functions()
                .inputValueInField("Тип параметра", "Время в минутах")
                .inputValueInField("Значение", "3")
                .selectValueInField("Тип значения", "Время в минутах");

        //Нажать "Сохранить"
        new mdm_functions().clickButtonByText("Сохранить");

        CommonFunctions.wait(180);

        //Закрыть справочник
        closeWindow();
    }

    @Step("Шаг 14. Заявка аннулирована по причине отсутствия действий по его подписанию в течение установленного времени.")
    public void step14() {
        CommonFunctions.printStep();

        //Нажать на "Закрытие вкладки"

        //Подождать 3 минуты



    }

    @Step("Шаг 15. Проверка смены статуса заявки")
    public void step15() {
        CommonFunctions.printStep();

        //Открыть основную страницу личного кабинета


        //Нажать на "Показать все"

        //В списке найти заявку № (номер заявки) и нажать

    }

    @Step("Шаг 16. Внесение изменений в значения справочника \"Настройка сервиса \"Запрос заключения о карантинном фитосанитарном состоянии\"")
    public void step16() {
        CommonFunctions.printStep();

        //В новой вкладке перейти по ссылке https://mdm.t.exportcenter.ru/catalog/d6f7a2b8-b91b-437c-8645-9744b2aece5c


        //Найти значение "Истечение срока ожиданий действий пользователя при подписании заявления (1.8)"


        //Нажать "Изменить"


        //Изменить значения "Тип параметра" - "Количество рабочих дней", "Значение" - "2", "Тип значения" - "Количество рабочих дней"


        //Нажать "Сохранить"


        //Закрыть справочник

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

    /**
     * Костыль: обновлять страницу, пока не появится кнопка "Продолжить"
     */
    private static void refreshTabUntilElementIsDisplayed(String expectedXpath, int times) {
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

    /**
     * Костыль: если страница с номером заявки проскакивается - кликаем по ссылке с необходимым типом заявки
     */
    private static void clickRequestLinkIfRequestNumberPageIsSkipped(String requestName) {
        if ($x("//button[contains(text(),'" + requestName + "')]").isDisplayed()) {
            new GUIFunctions().clickByLocator("//button[contains(text(),'" + requestName + "')]");
        }
    }

}

package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_01_07_32_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_32_1/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_01_1_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 32.1 Настройки параметров сервиса")
    @Link(name = "Test_01_07_32_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170239163")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль, нажать «Войти»
        open("https://arm-apkvr.t.exportcenter.ru/service-settings");
        new GUIFunctionsLKB()
                .authorization("mdm_admin", "password")
                .waitForURL("https://arm-apkvr.t.exportcenter.ru/service-settings");
    }

    @Step("")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctionsLKB()
                .inField("Максимальная продолжительность периода ожидания подписания Заявки в статусе \"Черновик\"").inputValue("1").assertValue()
                .inField("Максимальная продолжительность отображения Заявки после завершении услуги").inputValue("1").assertValue()
                .inField("Период ожидания выплаты компенсации").inputValue("1").assertValue()
                .inField("Период отправления первичного уведомления о выплате").inputValue("1").assertValue()
                .inField("Срок выполнения задачи на подписание предварительного агентского отчета").inputValue("1").assertValue()
                .inField("Срок выполнения задачи на подписание агентского отчета").inputValue("1").assertValue()
                .inField("Срок выполнения задачи на подписание итогового отчета о выполненных показателях уполномоченным лицом").inputValue("1").assertValue()
                .inField("Срок выполнения задачи на подписание заключения по отчету о выполненных показателях").inputValue("1").assertValue()
                .inField("Срок выполнения задачи на подписание Соглашения уполномоченным лицом").inputValue("1").assertValue()
                .clickByLocator("//*[text()='Включение/отключение риск-ориентированной модели']/parent::*//input")
                .clickButton("Сохранить")
                .clickButton("OK");
    }

    @Step("")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctionsLKB().clickButton("Параметры отбора")
                .clickButton("Создать новую запись")
                .inField("Год отбора").inputValue("2024").pressEnter()
                .inField("Номер отбора").inputValue("1").pressEnter();

        $x("//input[@placeholder='Начальная дата']").click();
        $x("//input[@placeholder='Начальная дата']").setValue("2024-01-01 16:58:27").pressEnter();
        $x("//input[@placeholder='Конечная дата']").setValue("2024-03-31 16:58:27").pressEnter();

        new GUIFunctionsLKB()
                .inField("Информация об отборе").inputText("тест")
                .clickButton("Сохранить")
                .clickButton("OK");
    }

    @Step("")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctionsLKB().clickButton("Настройки")
                .inField("Максимальная продолжительность периода ожидания подписания Заявки в статусе \"Черновик\"").inputValue("7").assertValue()
                .inField("Максимальная продолжительность отображения Заявки после завершении услуги").inputValue("30").assertValue()
                .inField("Период ожидания выплаты компенсации").inputValue("10").assertValue()
                .inField("Период отправления первичного уведомления о выплате").inputValue("5").assertValue()
                .inField("Срок выполнения задачи на подписание предварительного агентского отчета").inputValue("5").assertValue()
                .inField("Срок выполнения задачи на подписание агентского отчета").inputValue("5").assertValue()
                .inField("Срок выполнения задачи на подписание итогового отчета о выполненных показателях уполномоченным лицом").inputValue("5").assertValue()
                .inField("Срок выполнения задачи на подписание заключения по отчету о выполненных показателях").inputValue("5").assertValue()
                .inField("Срок выполнения задачи на подписание Соглашения уполномоченным лицом").inputValue("2").assertValue()
                .clickByLocator("//*[text()='Включение/отключение риск-ориентированной модели']/parent::*//input")
                .clickButton("Сохранить")
                .clickButton("OK")
                .clickButton("Параметры отбора")
                .clickByLocator("//td[text()='01.01.2024 - 31.03.2024']/following-sibling::*//*[text()='Просмотреть']")
                .clickButton("Удалить")
                .clickButton("OK")
                .waitForElementDisplayed("//*[text()='Запись удалена.']")
                .clickButton("OK");
    }
}

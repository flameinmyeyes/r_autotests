package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
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
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_32_1_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

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
        open(P.getProperty("start_URL"));
        new GUIFunctionsLKB()
                .authorization(P.getProperty("Авторизация.Email"), P.getProperty("Авторизация.Пароль"))
                .waitForURL(P.getProperty("expected_URL"));
    }

    @Step("Настройки Сервиса")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctionsLKB()
                .inField("Максимальная продолжительность периода ожидания подписания Заявки в статусе \"Черновик\"").inputValue(P.getProperty("Настройки Сервиса.продолжительность периода")).assertValue()
                .inField("Максимальная продолжительность отображения Заявки после завершении услуги").inputValue(P.getProperty("Настройки Сервиса.продолжительность отображения")).assertValue()
                .inField("Период ожидания выплаты компенсации").inputValue(P.getProperty("Настройки Сервиса.Период ожидания выплаты компенсации")).assertValue()
                .inField("Период отправления первичного уведомления о выплате").inputValue(P.getProperty("Настройки Сервиса.Период отправления первичного уведомления")).assertValue()
                .inField("Срок выполнения задачи на подписание предварительного агентского отчета").inputValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание предварительного агентского отчета")).assertValue()
                .inField("Срок выполнения задачи на подписание агентского отчета").inputValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание агентского отчета")).assertValue()
                .inField("Срок выполнения задачи на подписание итогового отчета о выполненных показателях уполномоченным лицом").inputValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание итогового отчета")).assertValue()
                .inField("Срок выполнения задачи на подписание заключения по отчету о выполненных показателях").inputValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание заключения по отчету")).assertValue()
                .inField("Срок выполнения задачи на подписание Соглашения уполномоченным лицом").inputValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание Соглашения")).assertValue()
                .inField("Включение/отключение риск-ориентированной модели").setCheckboxON()
                .clickButton("Сохранить")
                .waitForElementDisplayed("//*[text()='Новые настройки сервиса успешно сохранены.']")
                .clickButton("OK");

        new GUIFunctionsLKB()
                .inField("Максимальная продолжительность периода ожидания подписания Заявки в статусе \"Черновик\"").assertValue(P.getProperty("Настройки Сервиса.продолжительность периода"))
                .inField("Максимальная продолжительность отображения Заявки после завершении услуги").assertValue(P.getProperty("Настройки Сервиса.продолжительность отображения"))
                .inField("Период ожидания выплаты компенсации").assertValue(P.getProperty("Настройки Сервиса.Период ожидания выплаты компенсации"))
                .inField("Период отправления первичного уведомления о выплате").assertValue(P.getProperty("Настройки Сервиса.Период отправления первичного уведомления"))
                .inField("Срок выполнения задачи на подписание предварительного агентского отчета").assertValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание предварительного агентского отчета"))
                .inField("Срок выполнения задачи на подписание агентского отчета").assertValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание агентского отчета"))
                .inField("Срок выполнения задачи на подписание итогового отчета о выполненных показателях уполномоченным лицом").assertValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание итогового отчета"))
                .inField("Срок выполнения задачи на подписание заключения по отчету о выполненных показателях").assertValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание заключения по отчету"))
                .inField("Срок выполнения задачи на подписание Соглашения уполномоченным лицом").assertValue(P.getProperty("Настройки Сервиса.Срок выполнения задачи на подписание Соглашения"))
                .inField("Включение/отключение риск-ориентированной модели").assertCheckboxON();
    }

    @Step("Настройки параметров отбора")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctionsLKB().clickButton("Параметры отбора")
                .clickButton("Создать новую запись")
                .inField("Год отбора").inputValue(P.getProperty("Настройки параметров отбора.Год отбора")).pressEnter()
                .inField("Номер отбора").inputValue(P.getProperty("Настройки параметров отбора.Номер отбора")).pressEnter();

        $x("//input[@placeholder='Начальная дата']").click();
        $x("//input[@placeholder='Начальная дата']").setValue(P.getProperty("Настройки параметров отбора.Начальная дата")).pressEnter();
        $x("//input[@placeholder='Конечная дата']").setValue(P.getProperty("Настройки параметров отбора.Конечная дата")).pressEnter();

        new GUIFunctionsLKB()
                .inField("Информация об отборе").inputText(P.getProperty("Настройки параметров отбора.Информация об отборе")).assertValue()
                .clickButton("Сохранить")
                .waitForElementDisplayed("//*[text()='Новая запись успешно создана.']")
                .clickButton("OK");
    }

    @Step("Возврат исходных настроек")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctionsLKB()
                .clickButton("Настройки")
                .inField("Максимальная продолжительность периода ожидания подписания Заявки в статусе \"Черновик\"").inputValue(P.getProperty("Возврат исходных настроек.продолжительность периода")).assertValue()
                .inField("Максимальная продолжительность отображения Заявки после завершении услуги").inputValue(P.getProperty("Возврат исходных настроек.продолжительность отображения")).assertValue()
                .inField("Период ожидания выплаты компенсации").inputValue(P.getProperty("Возврат исходных настроек.Период ожидания выплаты компенсации")).assertValue()
                .inField("Период отправления первичного уведомления о выплате").inputValue(P.getProperty("Возврат исходных настроек.Период отправления первичного уведомления")).assertValue()
                .inField("Срок выполнения задачи на подписание предварительного агентского отчета").inputValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание предварительного агентского отчета")).assertValue()
                .inField("Срок выполнения задачи на подписание агентского отчета").inputValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание агентского отчета")).assertValue()
                .inField("Срок выполнения задачи на подписание итогового отчета о выполненных показателях уполномоченным лицом").inputValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание итогового отчета")).assertValue()
                .inField("Срок выполнения задачи на подписание заключения по отчету о выполненных показателях").inputValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание заключения по отчету")).assertValue()
                .inField("Срок выполнения задачи на подписание Соглашения уполномоченным лицом").inputValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание Соглашения")).assertValue()
                .inField("Включение/отключение риск-ориентированной модели").setCheckboxOFF()
                .clickButton("Сохранить")
                .waitForElementDisplayed("//*[text()='Новые настройки сервиса успешно сохранены.']")
                .clickButton("OK");

        new GUIFunctionsLKB()
                .inField("Максимальная продолжительность периода ожидания подписания Заявки в статусе \"Черновик\"").assertValue(P.getProperty("Возврат исходных настроек.продолжительность периода"))
                .inField("Максимальная продолжительность отображения Заявки после завершении услуги").assertValue(P.getProperty("Возврат исходных настроек.продолжительность отображения"))
                .inField("Период ожидания выплаты компенсации").assertValue(P.getProperty("Возврат исходных настроек.Период ожидания выплаты компенсации"))
                .inField("Период отправления первичного уведомления о выплате").assertValue(P.getProperty("Возврат исходных настроек.Период отправления первичного уведомления"))
                .inField("Срок выполнения задачи на подписание предварительного агентского отчета").assertValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание предварительного агентского отчета"))
                .inField("Срок выполнения задачи на подписание агентского отчета").assertValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание агентского отчета"))
                .inField("Срок выполнения задачи на подписание итогового отчета о выполненных показателях уполномоченным лицом").assertValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание итогового отчета"))
                .inField("Срок выполнения задачи на подписание заключения по отчету о выполненных показателях").assertValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание заключения по отчету"))
                .inField("Срок выполнения задачи на подписание Соглашения уполномоченным лицом").assertValue(P.getProperty("Возврат исходных настроек.Срок выполнения задачи на подписание Соглашения"))
                .inField("Включение/отключение риск-ориентированной модели").assertCheckboxOFF();

        new GUIFunctionsLKB()
                .clickButton("Параметры отбора")
                .clickByLocator("//td[text()='01.01.2024 - 31.03.2024']/following-sibling::*//*[text()='Просмотреть']")
                .clickButton("Удалить")
                .waitForElementDisplayed("//*[text()='Удаление Записи!']")
                .clickButton("OK")
                .waitForElementDisplayed("//*[text()='Запись удалена.']")
                .clickButton("OK");
    }
}

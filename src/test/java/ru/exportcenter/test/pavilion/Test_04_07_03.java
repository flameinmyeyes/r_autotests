package ru.exportcenter.test.pavilion;

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

import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_03  extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_03_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;
    public String requestData;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 03 Подписание Акта")
    @Link(name = "Test_04_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170242302")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
//        requestNumber = "S/2022/303011";
        precondition();
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_04_07_02 test_04_07_02 = new Test_04_07_02();
        test_04_07_02.steps();
        requestNumber = test_04_07_02.requestNumber;
    }

    @Step("Шаг 1")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/");
        switchTo().alert().accept();

        //Открыть заявку
        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='Продолжить']");

        requestData = $x("//*[text()='Последнее изменение статуса']/following-sibling::div").getText();

//        String tmp = "2 сентября 2022, 10:27:25";
        requestData = requestData.substring(requestData.lastIndexOf(" "), requestData.length());
        requestData = requestData.substring(0, requestData.lastIndexOf(":"));
        requestData = requestData.replaceAll(" ", "");
        System.out.println(requestData);

        new GUIFunctions().clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Шаг 2")
    public void step02() {
        CommonFunctions.printStep();

        //Открыть портал
        //Авторизоваться под учетными данными
        open("http://arm-pavilion.t.exportcenter.ru/");
        switchTo().alert().accept();
        new GUIFunctionsLKB().authorization(P.getProperty("Блок2.Email"),P.getProperty("Блок2.Пароль"));

        CommonFunctions.wait(15);

        //Найти задачу Подписать Акт приемки продукции номеру Заявки, открыть
        //В аккордеоне Сведения о продукции нажать на кнопку Редактировать
        new GUIFunctionsLKB().clickByLocator("//span[@title='Все задачи']")
                .clickByLocator("//div[@title='Подписать Акт приёмки продукции']")
                .clickByLocator("(//li[text()='Подписать Акт приёмки продукции'])[1]")
                .clickByLocator("//span[text()='Редактировать']");

        //Выбрать дату Годен до - Сегодня
        $x("//input[@placeholder='Выберите дату']").sendKeys(P.getProperty("Блок2.Дата"));
        $x("//input[@placeholder='Выберите дату']").pressEnter();

        //Нажать кнопку Сохранить
        new GUIFunctionsLKB().clickByLocator("//span[text()='Сохранить']");

        //В конце страницы нажать кнопку Далее
        $x("//span[text()='Далее']").scrollTo();
        new GUIFunctionsLKB().clickByLocator("//span[text()='Далее']")
                .waitForLoading();

        //Нажать Подписать в верху экрана
        new GUIFunctionsLKB().clickByLocator("//span[text()='Подписать']");

        CommonFunctions.wait(2);

        //Выбрать сертификат
        //Нажать Подписать и отправить
        //Нажать Закрыть
        new GUIFunctionsLKB()
                .clickByLocator("//span[text()='Сертификат']/following::input")
                .clickByLocator("//div[@title='Ермухамбетова Балсикер Бисеньевна от 18.01.2022']")
                .clickByLocator("//span[text()='Подписать и отправить']")
                .waitForElementDisplayed("//*[text()='Вы успешно подписали Акт']")
                .clickByLocator("//span[text()='Закрыть']");
    }

    @Step("Шаг 3")
    public void step03() {
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/");

        //Открыть сводную информацию о заявке, обновить страницу
        //Нажать кнопку Продолжить
        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .refreshTab("Продолжить", 30)
                .clickButton("Продолжить");

        //Нажать кнопку "Подписать электронной подписью"
        //Выбрать электронный сертификат
        //Нажать кнопку "Подписать"
        //Нажать Далее
        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue(P.getProperty("Блок3.Сертификат")).assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForElementDisplayed("//button[contains(text(),'Господдержка. Демонстрационно-дегустационные павильоны АПК')]");

        //Перейти на сводную информацию о заявке по "хлебным крошкам"
        new GUIFunctions().clickByLocator("//button[contains(text(),'Господдержка. Демонстрационно-дегустационные павильоны АПК')]");
        switchTo().alert().accept();

        String url = webdriver().driver().getWebDriver().getCurrentUrl();
        System.out.println(url);
    }
}

package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
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

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 03 Подписание Акта")
    @Link(name = "Test_04_07_03", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=170242302")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
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

        CommonFunctions.wait(10);

        //Открыть заявку
        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='Продолжить']");
    }

    @Step("Шаг 2")
    public void step02() throws AWTException {
        CommonFunctions.printStep();

        //Открыть портал
        //Авторизоваться под учетными данными
        open("http://arm-pavilion.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization(P.getProperty("Блок2.Email"),P.getProperty("Блок2.Пароль"));

        //Найти задачу Подписать Акт приемки продукции номеру Заявки, открыть
        new GUIFunctionsLKB().clickByLocator("//span[@title='Все задачи']")
                .clickByLocator("//div[@title='Подписать Акт приёмки продукции']");

        CommonFunctions.wait(5);

        new GUIFunctionsLKB().clickByLocator("//*[text()='"+requestNumber+"']/ancestor::ol/li[text()='Подписать Акт приёмки продукции']");

        //Костыль
        $x("//span[@class='anticon anticon-arrow-left']").click();
        new GUIFunctionsLKB().clickByLocator("//*[text()='"+requestNumber+"']/ancestor::ol/li[text()='Подписать Акт приёмки продукции']");

        new GUIFunctions().waitForElementDisplayed("//span[text()='Редактировать']");

        if ($x("//*[text()='Ошибка']").isDisplayed()){
            $x("//*[text()='OK']").click();
        }

        CommonFunctions.wait(5);

        //В аккордеоне Сведения о продукции нажать на кнопку Редактировать
        new GUIFunctionsLKB()
                .clickByLocator("//span[text()='Редактировать']");

        String currentDate = DateFunctions.dateToday("dd.MM.yyyy");

        //Выбрать дату Годен до - Сегодня
        $x("//input[@placeholder='Выберите дату']").sendKeys(currentDate);
        $x("//input[@placeholder='Выберите дату']").pressEnter();

        //Нажать кнопку Сохранить
        new GUIFunctionsLKB().clickByLocator("//span[text()='Сохранить']");

        Assert.assertEquals($x("(//tr[@class='ant-table-row ant-table-row-level-0']/td)[6]").getText(), currentDate);

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
                .clickByLocator("//div[@title='"+ P.getProperty("Блок3.Сертификат") +"']")
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

//        //Перейти на сводную информацию о заявке по "хлебным крошкам"
//        new GUIFunctions().clickByLocator("//button[contains(text(),'Господдержка. Демонстрационно-дегустационные павильоны АПК')]");
//        switchTo().alert().accept();
//
//        String url = webdriver().driver().getWebDriver().getCurrentUrl();
//        System.out.println(url);
    }
}

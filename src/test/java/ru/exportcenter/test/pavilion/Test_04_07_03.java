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
//        requestNumber = "S/2022/289017";
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

    @Step("Блок1")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/");
        new GUIFunctions().authorization(P.getProperty("Блок1.Email"), P.getProperty("Блок1.Пароль"), P.getProperty("Блок1.Код"));

        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='Продолжить']");

        requestData = $x("//*[text()='Последнее изменение статуса']/following-sibling::div").getText();

//        String tmp = "2 сентября 2022, 10:27:25";
        requestData = requestData.substring(requestData.lastIndexOf(" "), requestData.length());
        requestData = requestData.substring(0, requestData.lastIndexOf(":"));
        requestData = requestData.replaceAll(" ", "");
        System.out.println(requestData);



        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//*[contains(text(),'Проект Акта приёмки продукции направлен оператору')]");
        closeWebDriver();
    }

    @Step("Блок2")
    public void step02() {
        CommonFunctions.printStep();

        open("http://arm-pavilion.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization(P.getProperty("Блок2.Email"),P.getProperty("Блок2.Пароль"));

        new GUIFunctionsLKB().clickByLocator("//span[@title='Все задачи']")
                .clickByLocator("//div[@title='Подписать Акт приёмки продукции']")
                .clickByLocator("(//*[contains(text(), '"+requestData+"')]/ancestor::ol/li[text()='Подписать Акт приёмки продукции'])[1]")
                .clickByLocator("//span[text()='Редактировать']");

        $x("//input[@placeholder='Выберите дату']").sendKeys(P.getProperty("Блок2.Дата"));
        $x("//input[@placeholder='Выберите дату']").pressEnter();

        new GUIFunctionsLKB().clickByLocator("//span[text()='Сохранить']");
        $x("//span[text()='Далее']").scrollTo();

        new GUIFunctionsLKB().clickByLocator("//span[text()='Далее']")
                .waitForLoading()
                .clickByLocator("//span[text()='Редактировать']");

        $x("//input[@placeholder='Выберите дату']").sendKeys(P.getProperty("Блок2.Дата"));
        $x("//input[@placeholder='Выберите дату']").pressEnter();

        new GUIFunctionsLKB().clickByLocator("//span[text()='Сохранить']");

        new GUIFunctionsLKB().clickByLocator("//span[text()='Подписать']");
        CommonFunctions.wait(3);
        new GUIFunctionsLKB()
                .clickByLocator("//span[text()='Сертификат']/following::input")
                .clickByLocator("//div[@title='Ермухамбетова Балсикер Бисеньевна от 18.01.2022']")
                .clickByLocator("//span[text()='Подписать и отправить']")
                .waitForElementDisplayed("//*[text()='Вы успешно подписали Акт']")
                .clickByLocator("//span[text()='Закрыть']");

        closeWebDriver();
    }

    @Step("Блок3")
    public void step03() {
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/");
        new GUIFunctions().authorization(P.getProperty("Блок3.Email"), P.getProperty("Блок3.Пароль"), P.getProperty("Блок3.Код"));

        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='Продолжить']")
                .clickButton("Продолжить");

        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue(P.getProperty("Блок3.Сертификат")).assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForLoading();

        String url = webdriver().driver().getWebDriver().getCurrentUrl();
        System.out.println(url);
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh(" + expectedXpath + ")");
            CommonFunctions.wait(1);
        }
    }
}

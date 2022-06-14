package ru.exportcenter.test.finplatforma;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.fin.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_06 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_06/";
    public String WAY_TEST_FIRST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_03/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_06_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;
    public String requestNumber = "S/2022/190506";

    @Owner(value="Петрищев Руслан")
    @Description("08 10 06 Отправка Черновика на публикацию. Валидация пройдена")
    @Link(name="Test_08_10_06", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133412735")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
//        precondition();
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() throws InterruptedException {
        CommonFunctions.printStep();

        Test_08_10_02 test_08_10_02 = new Test_08_10_02();
        test_08_10_02.steps();
        newProductName = test_08_10_02.newProductName;
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[@class='anticon anticon-number']");
    }

    @Step("Отправка на публикацию")
    public void step02() {
        CommonFunctions.printStep();

        new functions.gui.GUIFunctions().clickByLocator("//*[@class='anticon anticon-number']");
        $x("//input[@placeholder='Выберите номер заявки']").setValue(requestNumber).pressEnter();
        new GUIFunctions().clickByLocator("//*[text()='" + requestNumber + " Отправка на Публикацию']")
                .clickByLocator("//*[text()='К выполнению']/parent::button")
                .clickByLocator("//*[text()='Завершить выполнение']/parent::button")
                .clickByLocator("//*[text()='Назначить задачу на себя и завершить']/parent::button")
                .clickByLocator("//*[text()='Вернуть на доработку']/parent::button");
    }
}

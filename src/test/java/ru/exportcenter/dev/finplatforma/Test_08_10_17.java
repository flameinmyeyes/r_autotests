package ru.exportcenter.dev.finplatforma;

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

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_08_10_17 extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_17/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_17_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("08 10 17 Приглашение сотрудника")
    @Link(name = "Test_08_10_17", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133415540")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль kromanovskaya+user2@roox.ru/Password1!
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Отправить приглашение']");
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();

        //В области навигации нажать на раздел «Сотрудники».
        //Нажать на кнопку «Отправить приглашение».
        new GUIFunctionsLKB().clickByLocator("//a[@href='/employees']")
                .clickButton("Отправить приглашение");
    }

    @Step("Заполнение данных по новому сотруднику")
    public void step03() {
        CommonFunctions.printStep();

        //В поле «ФИО» ввести значение «Петров Алексей Михайлович».
        //В поле «Роль» выбрать из выпадающего списка  значение: «Подтвержденный пользователь».
        //В поле «Email» ввести  значение: «petralm@otr.ru».
        //В поле «Телефон» ввести  значение: «9999999999».
        //Нажать на кнопку «Отправить приглашение».
        new GUIFunctionsLKB().inField("Фамилия").inputValue(PROPERTIES.getProperty("Фамилия"))
                .inField("Имя").inputValue(PROPERTIES.getProperty("Имя"))
                .inField("Отчество").inputValue(PROPERTIES.getProperty("Отчество"))
                .inField("Роль").selectValue(PROPERTIES.getProperty("Роль"))
                .inField("Email").inputValue(PROPERTIES.getProperty("Email"))
                .inField("Мобильный телефон").inputValue(PROPERTIES.getProperty("Мобильный телефон"))
                .clickButton("Отправить приглашение");
    }
}
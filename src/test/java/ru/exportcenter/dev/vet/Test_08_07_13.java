package ru.exportcenter.dev.vet;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_07_13 extends Hooks {


    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_13/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_07_13_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value = "Селедцов Вадим")
    @Description("08.07.13 Ошибка авторизации")
    @Link(name = "Test_08_07_13", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183178005")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();


    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        open(P.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions()
                .authorization(P.getProperty("Авторизация.Email"), P.getProperty("Авторизация.Пароль"));
    }

    @Step(" Поле тип услуги оставить пустым ")
    public void step02() {
        CommonFunctions.printStep();

        //поймать контроль на незаполненность полей
        new GUIFunctions()
                .waitForElementDisplayed("//span[text()='Данные введены с ошибкой. Проверьте веденные данные.']");
    }

}

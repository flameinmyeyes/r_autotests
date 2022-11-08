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

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class Test_08_07_22 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_22/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_22/" + "Test_08_07_22_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.22 Проверка, что услуга недоступна для пользователя, у которого нет мест осуществления деятельности.")
    @Link(name = "Test_08_07_22", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188851166")
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
                .authorization(P.getProperty("Авторизация.Email"), P.getProperty("Авторизация.Пароль"), P.getProperty("Авторизация.Код"))
                .waitForElementDisplayed("//span[text()='Запрос разрешения на вывоз подконтрольной продукции']");
    }
    @Step("Проверка контроля")
    public void step02() {
        try {
            Thread.sleep(20000);  // после исправления работы стенда это ожидание убрать , костыль.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new GUIFunctions()
                .waitForElementDisplayed("//strong[text()='Результат автоматической проверки:']");
    }

}

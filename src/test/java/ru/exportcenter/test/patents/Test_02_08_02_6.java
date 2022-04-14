package ru.exportcenter.test.patents;

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
import ru.exportcenter.test.HooksTEST;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_02_08_02_6 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_6/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_02_6_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("ТК 02 08 02.6 Создание заявки на основании архивной Заявки или Черновика")
    @Link(name = "Test_02_08_02_6", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127902739")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps () {
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot () {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    private void step01 () {
        CommonFunctions.printStep();
        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Логин"), PROPERTIES.getProperty("Пароль"), PROPERTIES.getProperty("Код"))
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация ")
    private void step02 () {
        CommonFunctions.printStep();

        //Из перечня заявок выбрать заявку
        $x("//*[text()='Показать все (100)']").click();
        new GUIFunctions().clickByLocator("//*[contains(text(), \"Компенсация части затрат на регистрацию ОИС за рубежом\")]")
                .waitForElementDisplayed("//*[text()='Использовать как черновик']");

        //Нажать кнопку "Использовать как черновик"
        new GUIFunctions().clickButton("Использовать как черновик")
                .waitForLoading();
    }
}


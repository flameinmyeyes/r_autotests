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

import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class Test_02_08_02_9 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_9/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_02_9_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("02 08 02.9 Просмотр перечня Заявок по сервису, созданных организацией")
    @Link(name = "Test_02_08_02_9", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127905125")

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
    private void step01() {
        CommonFunctions.printStep();
        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Логин"), PROPERTIES.getProperty("Пароль"), "1234")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Просмотр данных заявки из перечня ")
    private void step02() {
        CommonFunctions.printStep();

        //Выбрать любую заявку из реестра и нажать на нее
        new GUIFunctions().clickByLocator("(//div[@class='CardBody_bodyContainer__2CPmT'])[1]")
                .waitForElementDisplayed("//*[@class='Info_rowInfoWrapper__2tqtS']");
    }
}





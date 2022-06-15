package ru.exportcenter.test.finplatforma;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_29  extends Hooks {

    private final String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_29/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_29_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Петрищев Руслан")
    @Description("08 10 29 Просмотр списка Черновиков")
    @Link(name="Test_08_10_29", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133424076")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps(){
        step01();
        step02();
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctionsLKB()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Продукты']")
                .clickButton("Продукты");
    }

    @Step("Просмотр списка продуктов")
    public void step02(){
        CommonFunctions.printStep();

        //Нажать на переключатель «Черновики»
        //Проскроллить вниз страницу
        new GUIFunctionsLKB().clickButton("Черновики")
                .waitForElementDisplayed("//tr[contains(@class, 'ant-table-row')]");
        $("body").sendKeys(Keys.PAGE_DOWN);
    }
}

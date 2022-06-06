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

import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_26 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/finplatforma/Test_08_10_26/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_26_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Петрищев Руслан")
    @Description("08 10 26 Просмотр информации о Продукте")
    @Link(name="Test_08_10_26", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133422928")
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
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Наименование продукта']");
    }

    @Step("Навигация")
    public void step02(){
        CommonFunctions.printStep();

        //В поле "Наименование продукта" ввести значение "Аккредитив. Резервный аккредитив"
//        new GUIFunctions().inPlaceholder("Наименование продукта").inputValue(PROPERTIES.getProperty("Навигация.Наименование продукта"))
//                .waitForLoading();

    }

    @Step("Просмотр информации о Продукте")
    public void step03(){
        CommonFunctions.printStep();

        //Кликнуть по записи продукта с наименованием  "Аккредитив. Резервный аккредитив"
        //Нажать на вкладку «Условия предоставления»
        //Нажать на вкладку «Финансовые параметры»
        //Нажать на вкладку «Особенности погашения»
        new GUIFunctions().clickByLocator("(//tr[contains(@class,'ant-table-row')])[1]")
                .waitForLoading();

//        new GUIFunctions().clickButton("Аккредитив. Резервный аккредитив")
//                .waitForLoading()
        new GUIFunctions().clickButton("Условия предоставления")
                .waitForElementDisplayed("//div[@class='ant-tabs-content ant-tabs-content-top']//following::*[contains(text(),'Условия предоставления')]")
                .clickButton("Финансовые параметры")
                .waitForElementDisplayed("//div[@class='ant-tabs-content ant-tabs-content-top']//following::*[contains(text(),'Финансовые параметры')]")
                .clickButton("Особенности погашения")
                .waitForElementDisplayed("//div[@class='ant-tabs-content ant-tabs-content-top']//following::*[contains(text(),'Особенности погашения')]");
    }
}

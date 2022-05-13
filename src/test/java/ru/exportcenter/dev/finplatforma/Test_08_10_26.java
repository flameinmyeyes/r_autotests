package ru.exportcenter.dev.finplatforma;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.fin.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_26 extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_26/";
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
        new GUIFunctions().inPlaceholder("Наименование продукта")
                .inputValue("Аккредитив. Резервный аккредитив")
                .waitForLoading();
    }

    @Step("Просмотр информации о Продукте")
    public void step03(){
        CommonFunctions.printStep();

        //Кликнуть по записи продукта с наименованием  "Аккредитив. Резервный аккредитив"
        //Нажать на вкладку «Условия предоставления»
        //Нажать на вкладку «Финансовые параметры»
        //Нажать на вкладку «Особенности погашения»
        new GUIFunctions().clickButton("Аккредитив. Резервный аккредитив")
                .waitForLoading()
                .clickButton("Условия предоставления")
                .waitForLoading()
                .clickButton("Финансовые параметры")
                .waitForLoading()
                .clickButton("Особенности погашения")
                .waitForLoading();
    }
}

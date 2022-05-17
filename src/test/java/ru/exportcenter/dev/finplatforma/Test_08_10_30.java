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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class Test_08_10_30 extends Hooks {

    private final String WAY_TEST = Ways.DEV.getWay() + "/finplatforma/Test_08_10_30/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_10_30_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;

    @Owner(value="Петрищев Руслан")
    @Description("08 10 30 Просмотр Черновика")
    @Link(name="Test_08_10_30", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=133424112")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        precondition();
//        step01();
        step02();
        step03();
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
//        closeWebDriver();
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//*[text()='Черновики']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        //нажать на переключатель «Черновики»
        //В поле фильтра к атрибуту "Наименование продукта" ввести значение "Кредитование. Прямой кредит российскому экспортеру "СтройСервис"
        //Кликнуть по записи продукта с наименованием  "Кредитование. Прямой кредит российскому банку"
        new GUIFunctions().clickButton("Черновики")
                .waitForLoading()
                .clickButton("Сбросить фильтры")
                .inPlaceholder("Наименование продукта").inputValue(newProductName)
                .waitForLoading()
                .clickButton(newProductName)
                .waitForLoading();
    }

    @Step("Просмотр Черновика")
    public void step03() {
        CommonFunctions.printStep();

        //Нажать на вкладку «Условия предоставления»
        //Нажать на вкладку «Финансовые параметры»
        //Нажать на вкладку «Особенности погашения»
        new GUIFunctions().clickButton("Условия предоставления")
                .clickButton("Финансовые параметры")
                .waitForLoading()
                .clickButton("Особенности погашения")
                .waitForLoading();
    }
}

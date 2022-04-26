package ru.exportcenter.dev.finpodderzhka;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import ru.exportcenter.Hooks;

import java.util.Properties;

public class Test_03_07_01  extends Hooks {

    private String WAY_TEST = Ways.DEV.getWay() + "/finpodderzhka/Test_03_07_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_01_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Ворожко Александр")
    @Description("03 07 04 Сценарий 4")
    @Link(name="Test_03_07_04", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902512")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() throws InterruptedException {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions()
                .authorizationLib(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForElementDisplayed("//a[@href='/products']");
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        //Переход на вкладку продукты
        $x("//a[@href='/products']").click();

        //Создать новый продукт
        $x("//span[text()='Создать новый продукт']").click();
    }

    @Step("Сведения о продукте")
    public void step03() {
        CommonFunctions.printStep();

            //Тип продукта - "Финансирование"
            //Категория продукта - "Инвестиционное финансирование"
            $x("//span[text()='Тип продукта']/following::input").click();
            CommonFunctions.wait(1);
            $x("//span[text()='Тип продукта']/following::input").setValue("Финансирование");
            CommonFunctions.wait(1);
            $x("//span[text()='Тип продукта']/following::input").sendKeys(Keys.ENTER);
            $x("//span[text()='Категория продукта']/following::input").click();
            CommonFunctions.wait(1);
            $x("//span[text()='Категория продукта']/following::input").setValue("Инвестиционное финансирование");
            $x("//span[text()='Категория продукта']/following::input").sendKeys(Keys.ENTER);
            new GUIFunctions().waitForElementDisplayed("//*[text()='Краткое описание продукта']");

            $x("//span[text()='Целевое назначение']/following::textarea").setValue("Целевое назначение");
            $x("//span[text()='Краткое описание продукта']/following::textarea").setValue("Краткое описание продукта");

            new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Условия предоставления")
    public void step04() {
        CommonFunctions.printStep();

    }

    @Step("Финансовые параметры")
    public void step05() {
        CommonFunctions.printStep();

    }

    @Step("Особенности погашения")
    public void step06() {
        CommonFunctions.printStep();

//        new GUIFunctions().waitForURL("https://www.ya.ru");
    }
}

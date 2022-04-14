package ru.exportcenter.test.agroexpress;

import com.codeborne.selenide.WebDriverRunner;
import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.HooksTEST;

import java.util.Properties;

import static com.codeborne.selenide.Configuration.browserCapabilities;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.*;

@Deprecated
public class Test_03_07_06_1 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_06_1/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_06_1_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Петрищев Руслан")
    @Description("ТК 03 07 06.1")
    @Link(name="Test_03_07_06_1", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127901546")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
//        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль mdm_admin/password, нажать «Войти»
        open("https://arm-agroexpress.t.exportcenter.ru");
        $x("//input[@placeholder='E-mail']").setValue("mdm_admin");
        $x("//input[@placeholder='Пароль']").setValue("password");
        CommonFunctions.wait(1);
        $x("//button[contains(@class,'ant-btn ant-btn-primary')]").click();
        new GUIFunctions().waitForURL("https://arm-agroexpress.t.exportcenter.ru/main-page");
    }

    @Step("Поиск справочника")
    public void step02() {
        CommonFunctions.printStep();

        //В новой вкладке  перейти по ссылке
//        switchTo().window(1);
        open("https://mdm.t.exportcenter.ru/");
        new GUIFunctions().waitForURL("https://mdm.t.exportcenter.ru/catalog");

        //В поле "Укажите поисковый запрос" ввести значение "Настройки сервиса "Логистика
        $x("//input[@placeholder='Укажите поисковый запрос']").setValue("Настройки сервиса \"Логистика. Доставка продукции \"Агроэкспрессом\"");
        $x("//*[text()='Поиск']").click();

        //Нажать на поле "Настройки сервиса "Логистика. Доставка продукции "Агроэкспрессом"""
        $x("//a[text()='Настройки сервиса \"Логистика. Доставка продукции \"Агроэкспрессом\"']").click();
    }

    @Step("Настройка сервиса")
    public void step03() {
        CommonFunctions.printStep();

//        switchTo().window(0);
    }
}

package ru.exportcenter.test.pavilion;

import com.codeborne.selenide.WebDriverRunner;
import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.checkerframework.checker.guieffect.qual.UI;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_05 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_05/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_05_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String UUID;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 05 Отчёт за квартал")
    @Link(name = "Test_04_07_05", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175268694")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();

    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://mdm.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization("mdm_admin","password");

        $x("//input[@placeholder='Укажите поисковый запрос']").setValue("pavilion_plan_registry");
        $x("//span[text()='Поиск']").click();

        new GUIFunctions().waitForElementDisplayed("//a[text()='Реестр плана работа оператора Павильона']")
                .clickByLocator("//a[text()='Реестр плана работа оператора Павильона']");

        int rowsCount = $x("//tr[contains(@class,'ant-table-row')]").findElements(By.xpath("//tr[contains(@class,'ant-table-row')]")).size();
//        int rowsCount = webdriver().driver().getWebDriver().findElements(By.xpath("//tr[contains(@class,'ant-table-row')]")).size();

        System.out.println(rowsCount);
        $x("(//tr[contains(@class,'ant-table-row')])["+rowsCount+"]/td/a[contains(text(),'S/2022/')]").click();
        switchTo().window(1);

        $x("//div[text()='Системная информация']").click();
        UUID = $x("//*[text()='UUID:']/parent::*").getOwnText();
        System.out.println(UUID);


        closeWindow();
        switchTo().window(0);
        $x("//button[text()='Выйти']").click();
    }

    @Step("")
    public void step02() {
        CommonFunctions.printStep();


    }

    @Step("")
    public void step03() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("http://arm-pavilion.t.exportcenter.ru/");
        new GUIFunctionsLKB().authorization("pavilion_operator@otr.ru","Password1!");

        CommonFunctions.wait(1);

        //Открыть задачу "Подготовить ежеквартальный отчет"
        $x("(//*[text()='Подготовить ежеквартальный отчёт'])[1]").click();

        //
        new GUIFunctions().clickButton("Сформировать");

        String url = webdriver().driver().getWebDriver().getCurrentUrl();
        $x("//button[text()='Выйти']").click();

        open(url);
        new GUIFunctionsLKB().authorization("pavilion_operator_top@otr.ru","Password1!");

        new GUIFunctionsLKB().clickByLocator("//span[text()='Подписать']");
        CommonFunctions.wait(2);
        new GUIFunctionsLKB()
                .clickByLocator("//span[text()='Сертификат']/following::input")
                .clickByLocator("//div[@title='Ермухамбетова Балсикер Бисеньевна от 18.01.2022']")
                .clickByLocator("//span[text()='Подписать и отправить']")
                .waitForElementDisplayed("//*[text()='Отчёт подписан']")
                .clickByLocator("//span[text()='Закрыть']");
    }
}


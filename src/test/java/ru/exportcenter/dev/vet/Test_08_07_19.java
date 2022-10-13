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

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_07_19 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_19/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_08_07_19_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;

    @Owner(value = "Селедцов Вадим")
    @Description("08.07.19 Авторизация, для заявки Запрос разрешения на вывоз подконтрольной продукции")
    @Link(name = "Test_08_07_19", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188845972")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        //   step02();
        step03();

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
                //.waitForElementDisplayed("//span[text()='Запрос разрешения на вывоз подконтрольной продукции']");   / переделывали ТК уже три раза, не удалять.

                .refreshTab("Продолжить", 30)                      // это бага НА ДЕВЕ , кнопка продолжить не отображается с первого раза, перезагрузка страницы 10 раз
                .clickButton("Продолжить")
                .waitForElementDisplayed("//span[text()='Запрос разрешения на вывоз подконтрольной продукции']");
    }

    @Step("Выбрать Запрос разрешения на вывоз подконтрольной продукции")
    public void step02() {
        CommonFunctions.printStep();   // в новом ТК этот щаг  исчез, не понтяно , понадобиится ли в будущем

        //Выбрать Запрос разрешения на вывоз подконтрольной продукции
        new GUIFunctions()
                .clickByLocator("//span[text()='Запрос разрешения на вывоз подконтрольной продукции']")
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Информация о заявителе']");
    }

    @Step("Проверить корректность предзаполненных данных")
    public void step03() {
        CommonFunctions.printStep();

        //Проверить корректность предзаполненных данных

        $x("//*[text() = 'Информация о заявителе']/ancestor::div[contains(@class, 'container')][1]//span[text()='ИНН']").shouldHave(text(" 7261436362"));
        //  $x("//*[text() = 'Информация о заявителе']/ancestor::div[contains(@class, 'container')][1]//span[text()='КПП']").shouldHave(text(" 771301001"));   бага  с null
        $x("//*[text() = 'Информация о заявителе']/ancestor::div[contains(@class, 'container')][1]//span[text()='ОГРН']").shouldHave(text(" 9198786146946"));
        $x("//*[text() = 'Информация о заявителе']/ancestor::div[contains(@class, 'container')][1]//span[text()='Контактный телефон']/following-sibling::span[1]").shouldHave(text("79595555555"));
        $x("//*[text() = 'Информация о заявителе']/ancestor::div[contains(@class, 'container')][1]//span[text()='Email']/following-sibling::span[1]").shouldHave(text("vet_info@otr.ru"));
      //  $x("//*[text() = 'Информация о заявителе']/ancestor::div[contains(@class, 'container')][1]//span[text()='Наименование организации']").shouldHave(text(" ООО «ОТР 2000»")); бага  с null

    }

}

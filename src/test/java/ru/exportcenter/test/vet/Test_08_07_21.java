package ru.exportcenter.test.vet;

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

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_08_07_21 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_21/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_21/" + "Test_08_07_21_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.21 Выбор услуги из Каталога")
    @Link(name = "Test_08_07_21", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188849242")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();
        step03();

    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void precondition() {
            CommonFunctions.printStep();
            open(P.getProperty("start_URL"));

            //Ввести логин и пароль demo_exporter/password
            new GUIFunctions()
                    .authorization(P.getProperty("Авторизация.Email"), P.getProperty("Авторизация.Пароль"), P.getProperty("Авторизация.Код"))
                    .waitForElementDisplayed("//span[text()='Заказать услугу']", 120)
                    .clickByLocator("//span[text()='Заказать услугу']")
                    .switchPageTo(1)
                    .waitForElementDisplayed("//h1[text()='Каталог сервисов                    ']");

        }

    @Step("В поисковой строке (ввести ключевые слова для поиска сервиса \"Запрос ветеринарного сертификата\", нажать Enter. ")
    public void step01() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inputInSearchField("Поиск по разделу", P.getProperty("Поиск по разделу"))
                .inContainer("Запрос ветеринарного сертификата")
                .clickButton("Оформить")
                .waitForLoading()
                .switchPageTo(2)
                .waitForLoading()
                .waitForElementDisplayed("//span[text()='Запрос ветеринарного сертификата']");

    }

    @Step("В поле Выберите тип услуги указать значение")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inContainer("Тип услуги")
                .clickByLocator("//span[text()='Формирование отчетности по разрешениям на вывоз подконтрольной продукции']")
                .inContainer("Запрос ветеринарного сертификата")
                .clickButton("Продолжить")
                .waitForLoading()
                .inContainer("Формирование отчетности по разрешениям на вывоз подконтрольной продукции")
                .waitForElementDisplayed("//div[text()='Подтвердите переход на выбранный тип услуги нажав кнопку \"Продолжить\"']")
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Последнее изменение статуса']");

    }

    @Step("Предзаполненные данные в карточке \"Формирование отчетности по разрешениям на вывоз подконтрольной продукции\" ")
    public void step03() {
        CommonFunctions.printStep();

        $x("//span[text()='Получатель услуги']/following-sibling::div[1]").shouldHave(text("Юлия Викторовна Рычагова"));
        $x("//div[text()='Статус']/following-sibling::div[1]").shouldHave(text("Формирование отчета"));
        $x("//div[text()='Дата создания заявки']").shouldBe(exist);
        $x("//div[text()='Последнее изменение статуса']").shouldBe(exist);
        $x("//div[text()='Номер заявки']").shouldBe(exist);
        //new GUIFunctions().waitForElementDisplayed("//span[text()='Продолжить']");
        new GUIFunctions().refreshTab("Продолжить", 30);   // Bug стенда, чтобы появилась кнопка, надо перегружать страницу
    }
}

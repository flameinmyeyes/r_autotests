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

public class Test_08_07_14 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_14/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_14/" + "Test_08_07_14_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.14 Ошибка формирования отчета")
    @Link(name = "Test_08_07_14", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175264997")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();

    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void precondition() {
        //Предусловие: выполнить шаги 1-4 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_08_07_15 test_08_07_15 = new Test_08_07_15();
        test_08_07_15.WAY_TEST = this.WAY_TEST;
        test_08_07_15.steps();

    }
    @Step("Ввод данных в карточку \"Тип услуги\" ")
    public void step01() {
        CommonFunctions.printStep();

        //В поле Выберите тип услуги выбрать Оформить новое разрешение  Нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickByLocator("//a[text()='Сформировать отчет']")
                .waitForLoading()
                .inContainer("Выбор отчёта")
                .waitForElementDisplayed("//span[text()='Общий']");

    }

    @Step("В поле Выбор отчёта, выбираем - Общий ")
    public void step02() {
        CommonFunctions.printStep();

//В поле Выбор отчёта, выбираем - Общий
        new GUIFunctions()
                .inContainer("Выбор отчёта")
                .clickByLocator("//span[text()='Общий']");
//Оставить поля пустыми
//Нажать кнопку "Далее"
        new GUIFunctions()
                .inContainer("Формирование отчетности по разрешениям на вывоз подконтрольной продукции")
                .clickButton("Далее")
                .waitForElementDisplayed("//div[text()='Заполните поле']", 60);

    }
}

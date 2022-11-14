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

public class Test_08_07_07 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_07/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_07/" + "Test_08_07_07_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.07 Новая заявка. Отмена заявки")
    @Link(name = "Test_08_07_07", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175264993")
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
        Test_08_07_02 test_08_07_02 = new Test_08_07_02();
        test_08_07_02.WAY_TEST = this.WAY_TEST;
        test_08_07_02.steps();

    }

    @Step("Ввод данных в карточку Тип услуги")
    public void step01() {
        CommonFunctions.printStep();

        //В поле Выберите тип услуги выбрать Оформить новое разрешение  Нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Тип услуги")
                .inField("Выберите тип услуги").clickByLocator("//span[text()='Оформить новое разрешение']")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Шаг 2 из 9']");

    }

    @Step("Ввод данных и отмена заявки ")
    public void step02() {
        CommonFunctions.printStep();

        //В поле Тип продукции выбрать Рыба и морепродукты
        new GUIFunctions()
                .inContainer("Информация о продукции")
                .inField("Тип продукции").selectValue("Рыба и морепродукты")
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickByLocator("//button[@class='dropdown-icon']")
                .clickByLocator("//li[text()='Отменить заявку']")
                .waitForLoading()
                .waitForElementDisplayed("//h4[text()='Мои услуги']");

    }


}

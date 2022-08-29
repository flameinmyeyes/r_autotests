package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
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
import ru.exportcenter.test.finplatforma.Test_08_10_02;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

// test-commit 02
public class Test_04_07_02 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_01/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_02_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Андрей Теребков")
    @Description("04 07 02 Внесение Клиентом изменений в состав сведений соглашения об объемах и номенклатуре продукции")
    @Link(name = "Test_04_07_02", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302518")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
        step02();
        step03();
        step04();
        step05();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_04_07_01 test_04_07_01 = new Test_04_07_01();
        test_04_07_01.steps();
    }

    @Step("Номенклатура и объемы продукции")
    public void step01() {
        CommonFunctions.printStep();

        requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");
        System.out.println("Test_04_07_02.requestNumber = " + requestNumber);
        open("https://lk.t.exportcenter.ru/ru/main");

        new GUIFunctions()
                .authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        new GUIFunctions().waitForElementDisplayed("//*[text()='Показать все (100)']")
                .clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div");

        refreshTab("//*[text()='Подписание Соглашения']", 10);
        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions()
                .clickButton("Продолжить");

        $x("//*[text()='Номенклатура и объемы продукции']").scrollTo();
        new GUIFunctions().clickByLocator("(//button[@class='dropdown-icon'])[2]")
                .clickButton("Изменить")
                .inField("Количество ед. продукции").inputValue("3")
                .clickButton("Добавить");
    }

    @Step("Контактное лицо")
    public void step02() throws AWTException {
        CommonFunctions.printStep();

        //        $x("//*[text()='Контактное лицо']").scrollTo();

        new GUIFunctions()
                .inField("Контактное лицо").setCheckboxON().assertCheckboxON()
                .inField("ФИО").inputValue("Умеров")
                .waitForElementDisplayed("//*[contains(text(), 'Мансур')]")
                .clickByLocator("//*[contains(text(), 'Мансур')]");
    }

    @Step("Фактический адрес")
    public void step03() {
        CommonFunctions.printStep();

//        $x("//*[text()='Фактический адрес']").scrollTo();

        new GUIFunctions()
                .inField("Фактический адрес изменился").setCheckboxON().assertCheckboxON()
                .inField("Индекс").inputValue("1234567")
                .inField("Регион").inputValue("Регион")
                .inField("Район").inputValue("Район")
                .inField("Город").inputValue("Город")
                .inField("Населенный пункт").inputValue("Населенный пункт")
                .inField("Улица").inputValue("Улица")
                .inField("Дом").inputValue("12")
                .inField("Строение").inputValue("3")
                .inField("Офис").inputValue("4")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Подписать электронной подписью']");
    }

    @Step("Подписание и направление соглашения")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForLoading();
        closeWebDriver();
    }

    @Step("Проверка изменений ")
    public void step05() {
        CommonFunctions.printStep();

        $x("//*[text()='О компании']").scrollTo();
        new GUIFunctions().clickButton("О компании")
                .clickButton("Далее");
    }


    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh(" + expectedXpath + ")");
            CommonFunctions.wait(1);
        }
    }
}

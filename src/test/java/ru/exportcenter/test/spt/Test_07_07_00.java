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
import java.awt.*;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class Test_07_07_00 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_00/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_00_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    public String url = "", login = "", password = "", forma = "";

    @Owner(value = "Андрей В. Теребков")
    @Description("07 07 00 Заполнение Заявки на получение услуги, подписание Заявки УКЭП и автоматическая передача Заявки на верификацию")
    @Link(name = "Test_07_07_00", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302431")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException{
        step01();
//      step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация»")
    public void step01() {
        CommonFunctions.printStep();

//      открыть страницу
        open(url);

//      Ввести логин и пароль
        new GUIFunctions()
                .waitForLoading()
                .authorization(login, password)
                .waitForLoading();

        CommonFunctions.wait(15);

        System.out.println("class = " + $x("//*[@id='form-open-panel']/div[1]/span/div/div[2]/div[2]/div").getAttribute("class"));
        requestNumber = $x("//body").getAttribute("id");
        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
        System.out.println("requestNumber = " + requestNumber);
    }

    @Step("Новигация")
    public void step02(){

        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
        System.out.println("requestNumber = " + requestNumber);

        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Оформление вида сертификата")
    public void step03() throws AWTException {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton(forma)
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Предзаполненные данные в карточке «Информация о заявителе» и в карточке «Информация об импортере»")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .waitForElementDisplayed("//span[text()='Наименование организации']/following-sibling::span");

        assertEquals(
                $x("//span[text()='Наименование организации']/following-sibling::span").getText()
                ,PROPERTIES.getProperty("Наименование организации")
        );
        assertEquals(
                $x("//span[text()='Юридический адрес']/following-sibling::span").getText()
                ,PROPERTIES.getProperty("Юридический адрес")
        );
        assertEquals(
                $x("//span[text()='ИНН']/following-sibling::span").getText(),
                PROPERTIES.getProperty("ИНН")
        );
        assertEquals(
                $x("//span[text()='КПП']/following-sibling::span").getText(),
                PROPERTIES.getProperty("КПП")
        );
        assertEquals(
                $x("//span[text()='ОГРН']/following-sibling::span").getText()
                ,PROPERTIES.getProperty("ОГРН"));
        assertEquals(
                $x("//input[contains(@class,'KrInput_input__xg4vc undefined')]").getValue()
                ,PROPERTIES.getProperty("Email"));
        assertEquals(
                $x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[2]").getValue()
                ,PROPERTIES.getProperty("Телефон")
        );
        assertEquals(
                "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[2]/div/div[2]/div/div/div[3]/div/div[2]/div/label/div/input").isSelected()
                , "true"
        );
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh()");
            CommonFunctions.wait(1);
        }
    }
}
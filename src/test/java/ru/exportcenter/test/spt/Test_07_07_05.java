package ru.exportcenter.test.spt;

import framework.RunTestAgain;
import framework.integration.JupyterLabIntegration;
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
import java.awt.*;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class Test_07_07_05 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_05/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_05_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.05 Выдача сертификата о происхождении товара сертификат СТ-2 (выдать на русском языке)")
    @Link(name = "Test_07_07_05", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175264547")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
  //      step02();
        step03();
        step04();
        step05();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        String url = "http://uidm.uidm-dev.d.exportcenter.ru/promo-service?key=service-spt&serviceId=27ddd0b2-5ed9-4100-9c93-2b0e07a2d599&next_query=true";
        open(url);
        new GUIFunctions()
                .waitForLoading()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForLoading();
        CommonFunctions.wait(15);

        System.out.println("class = " + $x("//*[@id='form-open-panel']/div[1]/span/div/div[2]/div[2]/div").getAttribute("class"));
        requestNumber = $x("//body").getAttribute("id");
        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
        System.out.println("requestNumber = " + requestNumber);
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions().waitForURL("")
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Нажатие кнопки «К перечню заявлений»")
    public void step03() {
        CommonFunctions.printStep();

        $x("(//div[@class='Radio_checkMark__18knp'])[3]").click();

        new GUIFunctions()
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

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере»")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"));

//        $x("(//button[contains(@class,'KrButton_container__3O7c1 KrButton_fitContent__2fm5o')])[2]").click();
        $x("//*[@id='form-open-panel']/div[2]/div/form/div[3]/div[2]/div/div[3]/div/button").click();
        new GUIFunctions()//.clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере»")
    public void step06() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"))
                .clickButton("Продолжить")
                .waitForLoading()
//              .waitForURL("")
        ;

        assertEquals(
                "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[3]/div/div[1]/div/label/div[1]/div").exists()
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

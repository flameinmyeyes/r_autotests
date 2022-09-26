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

public class Test_07_07_04 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_04/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_04_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.04 Выдача сертификата о происхождении товара сертификат СТ-1")
    @Link(name = "Test_07_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175264516")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
        step02();
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

        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
//      requestNumber = JupyterLabIntegration.getFileContent(WAY_TEST + "requestNumber.txt");
        System.out.println("requestNumber = " + requestNumber);
    }

    @Step("Новигация")
    public void step02() {
        CommonFunctions.printStep();

        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Выбор «Формы СТ-1»")
    public void step03() {
        CommonFunctions.printStep();

        $x("//div[@class='Radio_checkMark__18knp']").click();

        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Предзаполненные данные в карточке «Информация о заявителе» и в карточке «Информация об импортере»")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .waitForElementDisplayed("//span[text()='Наименование организации']/following-sibling::span");
/*
ООО "АЛСЕЗА"
392526, ТАМБОВСКАЯ ОБЛАСТЬ, ПОСЕЛОК СТРОИТЕЛЬ, УЛИЦА ПРОМЫШЛЕННАЯ, 84
7743300600
287545403
1157746363994
exp_test@mail.ru
+7(123)112-34-56
true
*/
        System.out.println($x("//span[text()='Наименование организации']/following-sibling::span").getText());
        System.out.println($x("//span[text()='Юридический адрес']/following-sibling::span").getText());
        System.out.println($x("//span[text()='ИНН']/following-sibling::span").getText());
        System.out.println($x("//span[text()='КПП']/following-sibling::span").getText());
        System.out.println($x("//span[text()='ОГРН']/following-sibling::span").getText());
        System.out.println($x("//input[contains(@class,'KrInput_input__xg4vc undefined')]").getValue());
        System.out.println($x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[2]").getValue());

        System.out.println($x("//*[@id='form-open-panel']/div[2]/div/form/div[2]/div/div[2]/div/div/div[3]/div/div[2]/div/label/div/input").isSelected());



    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере»")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inField("Страна").selectValue("Австрия")
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue("Вена, улица Стефана, 1")
                .clickButton("Продолжить")
                .waitForLoading();

        new GUIFunctions()
                .waitForURL("");
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

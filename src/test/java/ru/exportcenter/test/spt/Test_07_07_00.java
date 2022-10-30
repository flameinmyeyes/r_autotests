package ru.exportcenter.test.spt;

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

    public String url = "", login = "", password = "", code="", forma = "", CompanyType = "";

    public String NameOrganisaiton = "";    // Наименование организации
    public String JuricAddress = "";        // Юридический адрес
    public String INN = "";                 // ИНН
    public String KPP = "";                 // КПП PROPERTIES.getProperty("КПП")
    public String OGRN = "";                // ОГРН
    public String email = "";               // Email
    public String phone = "";               // Телефон

    public String FIO = "";               //Фамилия Имя Отчество
    public String Address = "";               //Адрес
    public String INN_IP = "";               //ИНН_ИП
    public String OGRNIP = "";               //ОГРНИП
    public String email_IP = "";               //Email_ИП
    public String phone_IP = "";               //Телефон_ИП


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
        new GUIFunctions().waitForLoading();

//        if("Jur".equals(CompanyType))
//            new GUIFunctions().authorization(login, password);
//        else
            new GUIFunctions().authorization(login, password, code);

        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Определение вида сертификата']");



//      System.out.println("class = " + $x("//*[@id='form-open-panel']/div[1]/span/div/div[2]/div[2]/div").getAttribute("class"));
        System.out.println("class = class");
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
        if("Jur".equals(CompanyType)) {
            new GUIFunctions()
                .waitForElementDisplayed("//span[text()='Наименование организации']/following-sibling::span");

//            assertEquals(
//                    $x("//span[text()='Наименование организации']/following-sibling::span").getText()
//                    ,NameOrganisaiton
//            );
//            assertEquals(
//                    $x("//span[text()='Юридический адрес']/following-sibling::span").getText()
//                    ,JuricAddress
//            );
//            assertEquals(
//                    $x("//span[text()='ИНН']/following-sibling::span").getText()
//                    ,INN
//            );
//            assertEquals(
//                    $x("//span[text()='КПП']/following-sibling::span").getText()
//                    ,KPP
//            );
//            assertEquals(
//                    $x("//span[text()='ОГРН']/following-sibling::span").getText()
//                    ,OGRN
//            );
//            assertEquals(
//                    $x("//input[contains(@class,'KrInput_input__xg4vc undefined')]").getValue()
//                    ,email
//            );
//            assertEquals(
//                    $x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[2]").getValue()
//                    ,phone
//            );
            assertEquals(
                    "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[2]/div/div[2]/div/div/div[3]/div/div[2]/div/label/div/input").isSelected()
                    , "true"
            );
        }

        if("IP".equals(CompanyType)) {
            new GUIFunctions()
                .waitForElementDisplayed("//span[text()='Фамилия Имя Отчество']/following-sibling::span");

            assertEquals(
                    $x("//span[text()='Фамилия Имя Отчество']/following-sibling::span").getText()
                    ,FIO
            );
            assertEquals(
                    $x("//span[text()='Адрес']/following-sibling::span").getText()
                    ,Address
            );
            assertEquals(
                    $x("//span[text()='ИНН']/following-sibling::span").getText()
                    ,INN_IP
            );
            assertEquals(
                    $x("//span[text()='ОГРНИП']/following-sibling::span").getText()
                    ,OGRNIP
            );
            assertEquals(
                    $x("//input[contains(@class,'KrInput_input__xg4vc undefined')]").getValue()
                    ,email_IP
            );
            assertEquals(
                    $x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[2]").getValue()
                    ,phone_IP
            );

        }
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
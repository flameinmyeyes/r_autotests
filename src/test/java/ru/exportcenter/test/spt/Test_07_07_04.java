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

public class Test_07_07_04 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_04/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_04_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    public boolean trueValue = true;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.04 Выдача сертификата о происхождении товара сертификат СТ-1")
    @Link(name = "Test_07_07_04", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=175264516")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        precondition();
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }


    @Step("Предусловия")
    public void precondition() throws AWTException {
        CommonFunctions.printStep();
        Test_07_07_00 test_07_07_00 = new Test_07_07_00();
        test_07_07_00.url = "https://lk.t.exportcenter.ru/promo-service?key=service-spt&serviceId=dc83e6b2-138f-4a39-99ca-117d8f8a27c8&next_query=true";
        test_07_07_00.login = PROPERTIES.getProperty("Авторизация.Email");
        test_07_07_00.password = PROPERTIES.getProperty("Авторизация.Пароль");
        test_07_07_00.code = PROPERTIES.getProperty("Авторизация.Код");
        test_07_07_00.forma = PROPERTIES.getProperty("Форма");

        test_07_07_00.CompanyType = PROPERTIES.getProperty("CompanyType");

        test_07_07_00.NameOrganisaiton = PROPERTIES.getProperty("Наименование организации");
        test_07_07_00.JuricAddress = PROPERTIES.getProperty("Юридический адрес");
        test_07_07_00.INN = PROPERTIES.getProperty("ИНН");
        test_07_07_00.KPP = PROPERTIES.getProperty("КПП");
        test_07_07_00.OGRN = PROPERTIES.getProperty("ОГРН");
        test_07_07_00.email = PROPERTIES.getProperty("Email");
        test_07_07_00.phone = PROPERTIES.getProperty("Телефон");

        test_07_07_00.FIO = PROPERTIES.getProperty("Фамилия Имя Отчество");
        test_07_07_00.Address = PROPERTIES.getProperty("Адрес");
        test_07_07_00.INN_IP =PROPERTIES.getProperty("ИНН_ИП");
        test_07_07_00.OGRNIP = PROPERTIES.getProperty("ОГРНИП");
        test_07_07_00.email_IP = PROPERTIES.getProperty("Email_ИП");
        test_07_07_00.phone_IP = PROPERTIES.getProperty("Телефон_ИП");

        test_07_07_00.steps();
        requestNumber = test_07_07_00.requestNumber;
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере»")
    public void step01() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"));

//      $x("(//button[contains(@class,'KrButton_container__3O7c1 KrButton_fitContent__2fm5o')])[2]").click();

        $x("//*[@id='form-open-panel']/div[2]/div/form/div[3]/div[2]/div/div[3]/div/button").click();

        if(("" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[3]/div/div[1]/div/label/div[1]/div").exists()).equals("null"))
            $x("//*[@id='form-open-panel']/div[2]/div/form/div[3]/div[2]/div/div[3]/div/button").click();

        new GUIFunctions()//.clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере»")
    public void step02() {
        CommonFunctions.printStep();


        new GUIFunctions() .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"));

//        new GUIFunctions()
//                .inputInSearchField("Страна", PROPERTIES.getProperty("Страна"));
//                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"));

        //      $("//input[@class='KrDropdown_input__1h8gb KrDropdown_inputPointer__CFf-G']").setValue(PROPERTIES.getProperty("Страна"));



        new GUIFunctions()
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"))
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForURL("")
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
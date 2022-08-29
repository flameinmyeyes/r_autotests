package ru.exportcenter.test.pavilion;

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
import ru.exportcenter.test.finplatforma.Test_08_10_02;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

// test-commit 02
public class Test_04_07_02 extends Hooks {
    //    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_021/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_02_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String newProductName;

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
        newProductName = test_04_07_01.newProductName;
    }

    @Step("Авторизация и навигация")
    public void step01() {
        CommonFunctions.printStep();

        System.out.println("Test_04_07_02.newProductName = " + newProductName);
        open("https://lk.t.exportcenter.ru/ru/main");

        new GUIFunctions()
                .authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        new GUIFunctions()
                .clickButton("Показать все (100)")
                .clickButton("Заявка №" +newProductName+ " отправлена");

        refreshTab("//*[text()='Подписание Соглашения']", 10);
        refreshTab("//*[text()='Продолжить']", 10);

        new GUIFunctions()
                .clickButton("Продолжить");
    }

    @Step("Номенклатура и объемы продукции")
    public void step02() throws AWTException {
        CommonFunctions.printStep();

        $x("//*[text()='Номенклатура и объемы продукции']").scrollTo();
        $x("//*[@id='form-open-panel']/div[2]/div/form/div[3]/div/div[2]/div/div/div[2]/div/div[1]/div/div/div/div/div/div/div/table/tbody/tr/td[6]/div/div").click();

        new GUIFunctions()
                .clickButton("Изменить")
                .setValueInField("3", "Количество ед. продукции")
                .clickButton("Добавить");

    }

    @Step("Контактное лицо")
    public void step03() {
        CommonFunctions.printStep();
        $x("//*[text()='Контактное лицо']").scrollTo();

        new GUIFunctions()
                .inField("Контактное лицо").setCheckboxON()
                .setValueInFieldFromSelect("Умеров Мансур Эскендерович оглы", "ФИО");

        System.out.println("end 2");

    }

    @Step("Фактический адрес")
    public void step04() {
        CommonFunctions.printStep();
        $x("//*[text()='Фактический адрес']").scrollTo();

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
            System.out.println("refresh(" + expectedXpath + ")");
            CommonFunctions.wait(1);
        }
    }
}

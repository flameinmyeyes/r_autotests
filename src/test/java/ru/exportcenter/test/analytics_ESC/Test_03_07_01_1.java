package ru.exportcenter.test.analytics_ESC;

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

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_01_1 extends Hooks {

    /*
     * http://selenoidshare.d.exportcenter.ru/lab/tree/work/files_for_tests/test/agroexpress/Test_03_07_01_1
     * https://gitlab.exportcenter.ru/sub-service/autotests/rec_autotests/-/blob/master/src/test/java/ru/exportcenter/test/agroexpress/Test_03_07_01_1.java
     */

    private final String WAY_TEST = Ways.TEST.getWay() + "/analytics_ESC/Test_03_07_01_1/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_03_07_01_1.xml");

    @Owner(value = "***")
    @Description("03 07 01.1 ")
    @Link(name = "Test_03_07_01_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127898840")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/ru/promo-service?key=country_report&serviceId=7479918f-2830-47b8-a7f1-572d847ad24d&next_query=true");

        new GUIFunctions()
                .authorization(P.getProperty("Логин"), P.getProperty("Пароль"))
                .waitForElementDisplayed("//*[text()='Формирование отчёта']");
    }

    @Step("")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions().inContainer("Формирование отчёта")
                .inField("Экспорт в страну").selectValue("Абхазия")
                .waitForLoading()
                .inField("Отчётный период, содержит данные до").selectValue("10.2021")
                .waitForLoading();

        $x("//*[text()='Получить отчёт']").scrollTo();
        new GUIFunctions().clickButton("Получить отчёт");

        for (int i = 0; i < 600; i++) {
            if (!$x("//*[text()='Вернуться в личный кабинет']").isDisplayed()){
                CommonFunctions.wait(1);
            }else {
                break;
            }
        }

        new GUIFunctions().clickButton("Вернуться в личный кабинет")
                .waitForElementDisplayed("//*[text()='Мои услуги']");
    }

}


package ru.exportcenter.test.agroexpress.Test_03_07_02_5.Test_03_07_02_5_1;

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

public class Test_03_07_02_5_1_1 extends Hooks {

    /*
     * http://selenoidshare.d.exportcenter.ru/lab/tree/work/files_for_tests/test/agroexpress/Test_03_07_02_5/Test_03_07_02_5_1/Test_03_07_02_5_1_1
     * https://gitlab.exportcenter.ru/sub-service/autotests/rec_autotests/-/blob/master/src/test/java/ru/exportcenter/test/agroexpress/Test_03_07_02_5/Test_03_07_02_5_1/Test_03_07_02_5_1_1.java
     */

    private final String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_5/Test_03_07_02_5_1/Test_03_07_02_5_1_1/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_03_07_02_5_1_1.xml");

    @Owner(value = "Диана Максимова")
    @Description("03 07 02.5.1.1 Отмена Заявки на шаге \"Заполнение заявки\" по инициативе Клиента")
    @Link(name = "Test_03_07_02_5_1_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127896596")

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
        open(P.getProperty("start_URL"));
        new GUIFunctions().authorization(P.getProperty("Логин"), P.getProperty("Пароль"), P.getProperty("Код подтвержения"))
                .waitForElementDisplayed("(//*[contains(text(),'Логистика. Доставка продукции \"Агроэкспрессом\"')])[1]")
                .closeAllPopupWindows();

        errorCompensation();

        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//div[div[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']]/button")
                .closeAllPopupWindows();
    }

    private static void errorCompensation() {
        if ($x("//*[contains(text(), 'Информация о заявителе')]").isDisplayed()) {
            $x("//button[contains(text(), 'Логистика. Доставка продукции \"Агроэкспрессом\". Заявка')]").click();
            switchTo().alert().accept();
        }

        for (int i = 0; i < 20; i++) {
            new GUIFunctions().waitForLoading().closeAllPopupWindows();
            if ($x("//*[contains(text(), 'Продолжить')]").isDisplayed()) {
                return;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }

    @Step("Авторизация")
    public void step02() {
        CommonFunctions.printStep();

        String titleLocator = "//span[contains(text(),'Логистика. Доставка продукции \"Агроэкспрессом\"')]";

        String docNum = $x(titleLocator).getText().split("№")[1];
        new GUIFunctions().closeAllPopupWindows()
                .clickByLocator(titleLocator + "/following::button[@aria-label='dropdown']")
                .closeAllPopupWindows()
                .clickButton("Отменить заявку")
                .inContainer("Отмена Заявки")
                .clickButton("Да");

        new GUIFunctions().inContainer("Мои услуги")
                .clickByLocator("//*[contains(text(),'Показать все (')]")
                .clickByLocator("//*[contains(text(),'" + docNum + "')]");

        new GUIFunctions().inContainer("Логистика. Доставка продукции \"Агроэкспрессом\"")
                .inField("Статус").assertValue("Отказ Клиента")
                .waitForElementDisplayed("//div[div[text()='3']]//*[text()='Завершено']")
                .closeAllPopupWindows()
                .clickButton("К результату");
        new GUIFunctions().waitForElementDisplayed("//*[text()='Отказ Клиента']");

    }
}


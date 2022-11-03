package ru.exportcenter.test.fito;

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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;

public class Test_3_07_20 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_20/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/fito/Test_3_07_20/" + "Test_3_07_20_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    private String docNum;
    private String docStatus;

    @Owner(value = "Балашов Илья")
    @Description("3.07.20 (Тест контур) Возможность отказаться от принятия условий проекта договора и отбора проб")
    @Link(name = "Test_3_07_20", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=196775106")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step14();
        step15();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие: выполнить шаги 1-13 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188852997
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
        test_3_07_01.step01();
        test_3_07_01.step02();
        test_3_07_01.step03();
        test_3_07_01.step04();
        test_3_07_01.step05();
        test_3_07_01.step06();
        test_3_07_01.step07();
        test_3_07_01.step08();
        test_3_07_01.step09();
        test_3_07_01.step10();
        test_3_07_01.step11();
        test_3_07_01.step12();
        test_3_07_01.step13();
    }

    @Step("Шаг 14. Отказ от принятия условий проекта договора и отбора проб")
    public void step14() {
        CommonFunctions.printStep();

        //Нажать кнопку <...>
        new GUIFunctions()
                .scrollTo("Запрос заключения о карантинном фитосанитарном состоянии")
                .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']" +
                        "/parent::div/following-sibling::div/button[@class='dropdown-icon']")
                .waitForLoading();
        clickUntilCancelButtonIsDisplayed(60);

        //Нажать "Отменить заявку"
        new GUIFunctions()
                .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']" +
                        "/parent::div/following-sibling::div//li[text()='Отменить заявку']")
                .waitForLoading();

        //pop-up уведомление о подтверждении удаления
        if($x("//div//button[@title='Да']").isDisplayed()) {
            new GUIFunctions().clickByLocator("//div//button[@title='Да']");
            CommonFunctions.wait(1);
        }

        new GUIFunctions().waitForURL(new Test_3_07_01().P.getProperty("Авторизация.URL") + "/ru/main");
    }

    @Step("Шаг 15. Проверка статуса заявки")
    public void step15() {
        CommonFunctions.printStep();

        //читаем docNum из файла
        docNum = JupyterLabIntegration.getFileContent(WAY_TEST + "docNum.txt");
        System.out.println("docNum: " + docNum);

        new GUIFunctions()
                .waitForElementDisplayed("//*[contains(text(),'Показать все')]")
                .clickByLocator("//*[contains(text(),'Показать все')]");

        CommonFunctions.wait(600);

        new GUIFunctions()
                .clickByLocator("//*[contains(text(),'" + docNum + "')]/parent::div/following-sibling::button/div[text()='Продолжить']")
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Номер заявки']/parent::div/div[text()='" + docNum + "']");

        docStatus = $x("//div[text()='Статус']/parent::div/div[contains(@class, 'description')]").getText();
        System.out.println("docStatus: " + docStatus);
        Assert.assertEquals(docStatus, "Завершено. Отказ клиента");
    }

    /**
     * Костыль: кликать, пока не появится нужное всплывающее меню
     */
    private static void clickUntilCancelButtonIsDisplayed(int times) {
        for(int i = 0; i < times; i++) {
            if(!$x("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']" +
                    "/parent::div/following-sibling::div//li[text()='Отменить заявку']").isDisplayed()) {
                new GUIFunctions()
                        .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']" +
                                "/parent::div/following-sibling::div/button[@class='dropdown-icon']")
                        .waitForLoading();
                CommonFunctions.wait(1);
            }
        }
    }

}

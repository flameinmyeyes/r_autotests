package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.pavilion.Test_04_07_01;

import java.awt.*;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_01_07_02_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_02_1/";

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 02.1 Выбор Сервиса. Ознакомление с описанием Сервиса")
    @Link(name = "Test_01_07_02_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163299184")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
        step02();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_01_07_01_1 test_01_07_01_1 = new Test_01_07_01_1();
        test_01_07_01_1.steps();
    }

    @Step("Навигация через Поиск")
    public void step01() {
        CommonFunctions.printStep();

        //Перейти на https://master-portal.t.exportcenter.ru/services/
        //В строке поиска ввести «Господдержка. Сертификация продукции АПК» и нажать на икноку "Лупа"
        open("https://master-portal.t.exportcenter.ru/services/");
        new GUIFunctions().inputInSearchField("Поиск по разделу", "Господдержка. Сертификация продукции АПК");

//        String infoText = $x("//div[@class='col-12 pb-2']").getText();
//        System.out.println(infoText);

        //Выбрать вкладку «Государственные»
        new GUIFunctions()
                .clickByLocator("//div[@data-history-code-translit='Государственные']")
                .inputInSearchField("Поиск по разделу", "Господдержка. Сертификация продукции АПК");
    }

    @Step("Получение информации о сервисе")
    public void step02() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Подробнее»
        new GUIFunctions()
                .openSearchResult("Господдержка. Сертификация продукции АПК", "Подробнее")
                .waitForLoading()
                .closeAllPopupWindows()

                .scrollTo($x("//a[@href='#description']"))
                .clickButton("Описание")
                .scrollTo($x("//a[@href='#howto']"))
                .clickButton("Как получить");
    }
}

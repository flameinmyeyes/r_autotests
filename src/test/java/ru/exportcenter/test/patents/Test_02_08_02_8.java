package ru.exportcenter.test.patents;

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
import ru.exportcenter.test.HooksTEST;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class Test_02_08_02_8 extends Hooks {

    /*
     * http://selenoidshare.d.exportcenter.ru/lab/tree/work/files_for_tests/test/agroexpress/Test_02_08_02_8
     * https://gitlab.exportcenter.ru/sub-service/autotests/rec_autotests/-/blob/master/src/test/java/ru/exportcenter/test/agroexpress/Test_02_08_02_8.java
     */

    private final String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_8/";
    private final Properties P = PropertiesHandler.parseProperties(WAY_TEST + "Test_02_08_02_8.xml");

    @Owner(value = "Диана Максимова")
    @Description("ТК 02 08 02.8 Создание новой Заявки")
    @Link(name = "Test_02_08_02_8", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127905057")

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

        new GUIFunctions()
                .authorization(P.getProperty("Логин"), P.getProperty("Пароль"))
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация через Поиск")
    public void step02() {
        CommonFunctions.printStep();

        //Перейти во вкладку «Сервисы»
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business");

        //Выбрать сервис «Компенсация части затрат на регистрацию ОИС за рубежом» и нажать кнопку «Оформить»
        String service = "Компенсация части затрат на регистрацию ОИС за рубежом";
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", service)
                .closeAllPopupWindows()
                .waitForElementDisplayed(getIsNotFoundMessageXPath(service))
                .clickButton("Государственные")
                .openSearchResult(service, "Оформить")
                .switchPageTo(1)
                .waitForElementDisplayed("//*[text() = '" + service + "']");
    }

    private String getIsNotFoundMessageXPath(final String service) {
        return "//div[text()='По вашему запросу ']" +
                "[span[text()='" + service + "']]" +
                "[text()=' ничего не найдено']";
    }

}

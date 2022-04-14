package ru.exportcenter.test.patents;

import framework.RunTestAgain;
import framework.Ways;
import functions.api.RESTFunctions;
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

import static com.codeborne.selenide.Selenide.*;

public class Test_02_08_02_5 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_5/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_02_5_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String token;
    private String docUUID;

    @Owner(value="Теребков Андрей")
    @Description("ТК 02 08 02.5 Ознакомление с возможностями Сервиса (onboarding)")
    @Link(name="Test_02_08_02_5", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=127897381")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        open(PROPERTIES.getProperty("start_URL"));

        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Логин"), PROPERTIES.getProperty("Пароль")/*, PROPERTIES.getProperty("Код")*/)
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();

        open(PROPERTIES.getProperty("direct_URL"));
        new GUIFunctions()
            .waitForURL("https://lk.t.exportcenter.ru/ru/promo-service?key=ProcessPatent&serviceId=d3d7a7b0-934b-4ca9-b660-9940e9d8b1f2&next_query=true");

        refreshTab("//*[contains(text(), 'Продолжить')]", 60);

        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Ознакомление с возможностями Сервиса (onboarding)")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions()
            .inContainer("Компенсация части затрат на регистрацию ОИС за рубежом")
            .clickButton("Хочу, расскажите")
            .clickButton("Далее")
            .clickButton("Далее")
            .clickButton("Далее")
            .clickButton("Далее")
            .clickButton("Завершить ознакомление");
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            CommonFunctions.wait(1);
        }
    }
}
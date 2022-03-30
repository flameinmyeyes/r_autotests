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
import ru.exportcenter.test.HooksTEST;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class Test_02_08_09 extends HooksTEST {

    public String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_09/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_09_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Балашов Илья")
    @Description("ТК 02 08 09 Отказ Клиента от услуги")
    @Link(name="Test_02_08_09", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123868246")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

//    public String PROPERTIES.getProperty(String key) {
//        String data = PropertiesHandler.getProperty(PROPERTIES, key);
//        return data;
//    }

    @Step("Авторизация")
    public void step01() {
        //В браузере перейти по ссылке http://uidm.uidm-dev.d.exportcenter.ru/ru/login
        //Ввести логин и пароль demo_exporter/password, нажать «Войти»
        new GUIFunctions()
                .inContainer("Вход в личный кабинет")
                .inField("Email").inputValue(PROPERTIES.getProperty("Авторизация.Email"))
                .inField("Пароль").inputValue(PROPERTIES.getProperty("Авторизация.Пароль"))
                .clickButton("Войти");

        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    public void step02() {
        //Перейти во вкладку «Сервисы»
        //В строке поиска ввести «Компенсация части затрат на регистрацию ОИС за рубежом»
        //Нажать иконку "Лупа"
        //Выбрать вкладку «Государственные»
        //Нажать кнопку «Оформить»
        //Перезагрузить страницу
        //Нажать кнопку «Продолжить»
        new GUIFunctions()
                .selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business")
                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом")
                .waitForElementDisplayed("//div[text()=' ничего не найдено']//span[text()='Компенсация части затрат на регистрацию ОИС за рубежом']")

                .inContainer("Каталог сервисов")
                .clickButton("Государственные")
//                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/state")
//                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом")
                .openSearchResult("Компенсация части затрат на регистрацию ОИС за рубежом", "Оформить")
                .switchPageTo(1)
                .waitForLoading();

        refreshTab("//*[contains(text(), 'Продолжить')]", 60);

        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Отказ от услуги")
    public void step03() {
        CommonFunctions.printStep();
        //Нажать на иконку "три точки" в верхнем правом углу
        //Нажать на "Отменить заявку"
        new GUIFunctions()
                .inContainer("Заявка «Субсидия по компенсации части затрат, связанных с регистрацией на внешних рынках объектов интеллектуальной собственности» S/2022/08554")
                .clickByLocator("//button[@class='dropdown-icon']").clickByLocator("//li[text()='Отменить заявку']")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
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
package ru.exportcenter.dev.patents;

import framework.RunTestAgain;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import functions.gui.ext.Asserts;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.exportcenter.dev.HooksDEV;

import static com.codeborne.selenide.Selenide.*;

public class Test_02_08_06 extends HooksDEV {

    @Description("ТК 02 08 06")
    @Owner(value="Петрищев Руслан, Теребков Андрей")
    @Link(name="Test_02_08_06", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123868584")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
    }

    @Step("Авторизация")
    private void step01(){
        CommonFunctions.printStep();

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти");

        new GUIFunctions().waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");

        //Перейти во вкладку «Сервисы»
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business");

        //Выбрать сервис «Компенсация части затрат на регистрацию ОИС за рубежом» и нажать кнопку «Подробнее»
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом").
                clickByLocator("//div[text()='Государственные']")
                .openSearchResult("Компенсация части затрат на регистрацию ОИС", "Подробнее");
    }

    @Step("Получение информации о сервисе «Компенсация части затрат на регистрацию ОИС за рубежом»")
    private void step02(){
        CommonFunctions.printStep();

        //Проверка корректного отображения заголовков «Компенсация части затрат на регистрацию ОИС за рубежом», разделы «Что получает экспортер?» и «Как получить».
        Assert.assertEquals($x("//h1[contains(text(),'Компенсация части затрат на регистрацию ОИС за рубежом')]").getText(), "Компенсация части затрат на регистрацию ОИС за рубежом");
        Assert.assertEquals($x("//*[text()='Что получает экспортер?']").getText(), "Что получает экспортер?");
        Assert.assertEquals($x("//h2[text()='Как получить']").getText(), "Как получить");

        //В боковом меню нажать  «Как получить»
        $x("//a[text()='Как получить']").click();

        //Проверка отображения раздела «Как получить».
        new GUIFunctions().waitForElementDisplayed("//div[@class='timeline']");

        //В боковом меню нажать  «Описание»
        new GUIFunctions().clickButton("Описание");

        //Проверка отображения заловока и раздела «Что получает экспортер?»
        Assert.assertEquals($x("//*[text()='Что получает экспортер?']").getText(), "Что получает экспортер?");
    }
}



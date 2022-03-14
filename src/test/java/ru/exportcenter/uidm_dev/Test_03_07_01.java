package ru.exportcenter.uidm_dev;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class Test_03_07_01 extends Hooks_UIDM_DEV {

    @Description("03 07 01 Сценарий 1")
    @Owner(value = "Максимова Диана")
    @Link(name = "Test_03_07_01", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902466")

    @Test(retryAnalyzer = RunTestAgain.class)
    public static void steps() {
        step01();
        step02();
        step03();
        step04();
    }

    @Step("Перейти к оформлению сервиса «Логистика. Доставка продукции «Агроэкспрессом»»")
    private static void step01() {
        // Открыть браузер и перейти по ссылке http://uidm.uidm-dev.d.exportcenter.ru/ru/login
        // Ввести логин и пароль demo_exporter/password
        $(By.name("username")).sendKeys("demo_exporter");
        $(By.name("password")).sendKeys("password");
        $x("//button//*[text()='Войти']").click();

        webdriver().shouldHave(url("http://uidm.uidm-dev.d.exportcenter.ru/ru/main"), Duration.ofSeconds(30));

        // Перейти во вкладку «Сервисы»
        $x("//a[@class='header-menu-list__link '][text()='Сервисы']").click();
        webdriver().shouldHave(url("http://master-portal-dev.d.exportcenter.ru/services/business"), Duration.ofSeconds(30));

        // Выбрать сервис «Логистика. Доставка продукции «Агроэкспрессом»» и нажать кнопку «Оформить»
        $x("//input[@placeholder='Поиск по разделу']").sendKeys("Логистика\n");
        $x("//div[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']" +
                "/ancestor::div[@class='support-card support-card--active p-3']//*[text()='Оформить']").click();

        // Нажать на кнопку «Продолжить»
        switchTo().window(1);
        $x("//*[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']").shouldBe(Condition.visible, Duration.ofSeconds(60));
        if (!$x("//button//*[text()='Продолжить']").exists()) {
            refresh();
            $x("//*[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']").shouldBe(Condition.visible, Duration.ofSeconds(60));
        }
        $x("//button//*[text()='Продолжить']").click();
    }

    @Step("Заполнить область «Информация о компании»")
    private static void step02() {
        // В поле «Почтовый адрес» вводим значение «Корнилаева 2»
        input("Почтовый адрес", "Корнилаева 2");
    }

    private static void input(final String field, final String value) {
        String inputBoxXPath = "//*[text()='" + field + "']/ancestor::div[not(@class)][1]";
        String inputXPath = "";
        int n = 1;

        while (n < 10) {
            inputXPath = inputBoxXPath + "//input[preceding::*[" + n + "][text()='" + field + "']]";
            if ($x(inputXPath).exists())
                break;
            n++;
        }

        executeJavaScript("arguments[0].scrollIntoView();", $x(inputXPath));
        $x(inputXPath).sendKeys(value);

        // В поле корректно отображается введенное значение
        Assert.assertEquals($x(inputXPath).getValue(), "Корнилаева 2");
        // Нет сообщений об ошибке
        Assert.assertFalse($x(inputBoxXPath + "//span[contains(@class, 'error')]").exists());
    }

    private static void setCheckboxOn(final String field) {
        String checkboxAreaXPath = "//*[text()='" + field + "']/ancestor::div[not(@class)][1]";
        String checkboxXPath = checkboxAreaXPath + "//div[contains(@class,'checkMark')]";

        executeJavaScript("arguments[0].scrollIntoView();", $x(checkboxXPath));
        if ($x(checkboxAreaXPath + "//div[contains(@class,'checked')]").exists())
            System.out.println("Параметр «" + field + "» уже был включен");
        else
            $x(checkboxXPath).click();

        // В поле корректно отображается введенное значение
        Assert.assertTrue($x(checkboxAreaXPath + "//div[contains(@class,'checked')]").exists());
        // Нет сообщений об ошибке
        Assert.assertFalse($x(checkboxAreaXPath + "//span[contains(@class, 'error')]").exists());
    }

    @Step("Заполнить область «Информация о заявителе»")
    private static void step03() {
        setCheckboxOn("Дополнительный контакт");
        input("ФИО", "Иванов Иван Иванович");
        input("Телефон", "+7(999)999-99-99");
        input("Должность", "Менеджер");
        input("Email", "word@mail.ru");
    }

    @Step("Заполнить область «Информация для оказания услуги»")
    private static void step04() {
    }
}


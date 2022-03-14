package functional;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

@Deprecated
public class LoginPage {

    /* Методы для работы со страницей авторизации */

    public LoginPage() {
    }

    private By user = By.xpath("//input[@id='user']");
    private By password = By.xpath("//input[@id='psw']");
    private By buttonOk = By.xpath("//input[@id='okButton']");
    private String modHeaderWindow = "//div[@class='mdc-drawer-app-content app-content']";

    /**
     * Авторизация
     *
     * @param login - логин
     * @param pswd  - пароль
     */
    public LoginPage authorization(String login, String pswd) {
        CommonFunctions.waitForElementDisplayed(user, 120, false);
        $(user).sendKeys(login);
        CommonFunctions.waitForElementDisplayed(password, 120, false);
        $(password).sendKeys(pswd);
        CommonFunctions.waitForElementDisplayed(buttonOk, 120, false);
        $(buttonOk).click();
        new MainPage().waitForLoading(120);
        return this;
    }

    public LoginPage authorization(String login) {
        CommonFunctions.wait(3);
        //ModHeader
        String url = url();
        if (!$x("//span[text()='Веб-клиент: Регистрация']").isDisplayed() & !$x("//input[@id='user']").isDisplayed()) {

            Configuration.baseUrl = "";
            open("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/popup.html");
            CommonFunctions.wait(3);

            CommonFunctions.waitForElementDisplayed(By.xpath(modHeaderWindow), 60, false);
            if ($x(modHeaderWindow + "//button[@title='Resume ModHeader']").isDisplayed()) {
                $x(modHeaderWindow + "//button[@title='Resume ModHeader']").click();
            }

            CommonFunctions.waitForElementDisplayed(By.xpath(modHeaderWindow + "//button[@title='Pause ModHeader']"), 60, false);
//            CommonFunctions.waitForElementDisplayed(By.xpath(modHeaderWindow + "//input[@placeholder='Name']"), 60, false);
            $x(modHeaderWindow + "//input[@placeholder='Name']").setValue("POIB-Remote-User");

//            CommonFunctions.waitForElementDisplayed(By.xpath(modHeaderWindow + "//input[@placeholder='Value']"), 60, false);
            $x(modHeaderWindow + "//input[@placeholder='Value']").setValue(login);
            CommonFunctions.wait(3);

            open(url);
            new MainPage().waitForLoading(120);
        } else {
            CommonFunctions.waitForElementDisplayed(user, 60, false);
            $(user).sendKeys(login);
            CommonFunctions.waitForElementDisplayed(password, 60, false);
            $(password).sendKeys("Oracle33");
            CommonFunctions.waitForElementDisplayed(buttonOk, 60, false);
            $(buttonOk).click();
            new MainPage().waitForLoading(120);
        }
        return this;
    }

}

//    Если не сработает, тогда будем ловить так:
//    String nameFiled = modHeaderWindow + "//input[@placeholder='Name']";
//    String valueFiled = modHeaderWindow + "//input[@placeholder='Value']";
//
//            if ($x(nameFiled).isDisplayed() && $x(valueFiled).isDisplayed()) {
//        $x(nameFiled).setValue("POIB-Remote-User");
//        $x(valueFiled).setValue(login);
//    } else {
//        FileFunctions.writeValueFile("Z:\\files_for_tests\\eb_tse_demo_28080\\LoginHTML.html", new WebDriverRunner().source());
//        Assert.fail("Не удалось заполнить поля при авторизации. HTML страницы доступна по пути:\n" +
//                "Z:\\files_for_tests\\eb_tse_demo_28080\\LoginHTML.html");
//    }
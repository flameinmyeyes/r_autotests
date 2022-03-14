package functional;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class GUIFunctions {

    /* Методы для работы с главной страницей */

//    public static GUIFunctions GUIFunctions() {
////        new GUIFunctions();
////        super();
//        return new GUIFunctions();
//    }

    public int defaultWaitingTime = 60;

    /**
     * Авторизация
     * @param login - логин
     * @param password  - пароль
     */
    public GUIFunctions authorization(String login, String password) {
        String loginLocator = "//input[@name='username']";
        String passwordLocator = "//input[@name='password']";
        String buttonOk = "//button[@id='submit']";

        waitForElementDisplayed(loginLocator, defaultWaitingTime, false);
        $x(loginLocator).sendKeys(login);

        waitForElementDisplayed(passwordLocator, defaultWaitingTime, false);
        $x(passwordLocator).sendKeys(password);

        waitForElementDisplayed(buttonOk, defaultWaitingTime, false);
        $x(buttonOk).click();

        waitForLoading(defaultWaitingTime);
        return this;
    }

    /**
     * Ожидание отображения элемента
     * @param xpath   - локатор
     * @param seconds   - время ожидания в секундах
     */
    public GUIFunctions waitForElementDisplayed(String xpath, int seconds) {
        $x(xpath).shouldBe(Condition.visible, Duration.ofSeconds(seconds));
        return this;
    }

    /**
     * Ожидание отображения элемента (с опциональностью подсветки элемента)
     * @param xpath   - локатор
     * @param seconds   - время ожидания в секундах
     * @param highlight - необходимость подсветки элемента
     */
    public GUIFunctions waitForElementDisplayed(String xpath, int seconds, boolean highlight) {
        $x(xpath).shouldBe(Condition.visible, Duration.ofSeconds(seconds));
        if (highlight) {
            highlightSelenideElement(xpath);
        }
        return this;
    }

    /**
     * Ожидание исчезновения элемента
     * @param xpath - локатор
     * @param seconds - время ожидания в секундах
     */
    public GUIFunctions waitForElementDisappeared(String xpath, int seconds) {
        $x(xpath).shouldBe(Condition.disappear, Duration.ofSeconds(seconds));
        return this;
    }

    /**
     * Подсветка элемента (выделение границ элемента)
     * @param locator - локатор
     */
    private void highlightSelenideElement(String locator) {
        SelenideElement element = $x(locator);
        for (int i = 0; i < 1; i++) {
            executeJavaScript("arguments[0].style.border='2px solid red'", element);
            CommonFunctions.wait(0.15);
            executeJavaScript("arguments[0].style.border='none'", element);
        }
    }

    /**
     * Ожидание окончания Обработки запроса
     * @param maxWaitSeconds - максимальное время ожидания
     * */
    public GUIFunctions waitForLoading(int maxWaitSeconds) {
        while ($x("//*[contains(@class, 'preloader') or contains(@class,'spinner')]").isDisplayed()) {
            System.out.println("Ожидание обработки запроса...");

            waitForElementDisappeared("//*[contains(@class, 'preloader') or contains(@class,'spinner')]", defaultWaitingTime);
        }
        return this;
    }

    public GUIFunctions waitForURL(String URL) {
        webdriver().shouldHave(url(URL), Duration.ofSeconds(defaultWaitingTime));
        return this;
    }

    public GUIFunctions selectTab(String tabName) {
        String tabXpath = "//li[@data-name = '" + tabName + "']";

        waitForElementDisplayed(tabXpath, defaultWaitingTime);

        waitForLoading(defaultWaitingTime);
        return this;
    }

    public GUIFunctions inputSearchField(String placeHolder, String searchText) {
        String searchFieldXpath = "//input[contains(@placeholder, '" + placeHolder + "')]" +
                "[following-sibling::*[descendant::*[name()='use' and contains(@*, '#search')]]]";

        waitForElementDisplayed(searchFieldXpath, defaultWaitingTime);
        $x(searchFieldXpath).sendKeys(searchText);

        $x(searchFieldXpath).pressEnter();

        waitForLoading(defaultWaitingTime);
        return this;
    }

    public GUIFunctions openSearchResult(String searchResultName, String buttonName) {
        String searchResultXpath = "//*[contains(text(), '" + searchResultName + "')]" +
                "//following-sibling::div//descendant::*[contains(text(), '" + buttonName + "')]";

        waitForElementDisplayed(searchResultXpath, defaultWaitingTime);

        clickWebElement(searchResultXpath);

        switchTo().window(1);
        waitForLoading(defaultWaitingTime);
        return this;
    }

    /**
     * Ожидание отображения элемента и клик по нему
     * @param locator - локатор элемента
     */
    public GUIFunctions clickWebElement(String locator) {
        $x(locator).shouldBe(Condition.exist, Duration.ofSeconds(defaultWaitingTime));
        executeJavaScript("arguments[0].scrollIntoView();", $x(locator));
        $x(locator).click();

        waitForLoading(defaultWaitingTime);
        return this;
    }


}

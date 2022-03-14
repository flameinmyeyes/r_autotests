package functional;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.Assert;

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

        waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");

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

        clickWebElement(tabXpath);

//        waitForLoading(defaultWaitingTime);
        return this;
    }

    public GUIFunctions inputInSearchField(String searchFieldPlaceholder, String value) {
        String searchFieldXpath = "//input[contains(@placeholder, '" + searchFieldPlaceholder + "')]" +
                "[following-sibling::*[descendant::*[name()='use' and contains(@*, '#search')]]]";

        waitForElementDisplayed(searchFieldXpath, defaultWaitingTime);
        $x(searchFieldXpath).sendKeys(value);
        $x(searchFieldXpath).pressEnter();

//        waitForLoading(defaultWaitingTime);
        return this;
    }

    public GUIFunctions openSearchResult(String searchResultName, String buttonName) {
        String searchResultXpath = "//*[contains(text(), '" + searchResultName + "')]" +
                "//following-sibling::div//descendant::*[contains(text(), '" + buttonName + "')]";

        clickWebElement(searchResultXpath);

        waitForLoading(defaultWaitingTime);
        return this;
    }

    /**
     * Ожидание отображения элемента и клик по нему
     * @param xpath - локатор элемента
     */
    public GUIFunctions clickWebElement(String xpath) {
        $x(xpath).shouldBe(Condition.exist, Duration.ofSeconds(defaultWaitingTime));
        executeJavaScript("arguments[0].scrollIntoView();", $x(xpath));
        $x(xpath).click();

//        waitForLoading(defaultWaitingTime);
        return this;
    }

    /**
     * Ввести значение в поле области
     * @param area - область
     * @param field - поле
     * @param value - значение
     */
    public GUIFunctions inputValueInArea(String area, String field, String value) {
        String inputAreaXPath = "//*[text()='" + area + "']/ancestor::div[not(@class)][1]//*[text()='" + field + "']/ancestor::div[not(@class)][1]";
        String inputXPath = "";
        int n = 1;

        while (n < 10) {
            inputXPath = inputAreaXPath + "//input[preceding::*[" + n + "][text()='" + field + "']]";
            CommonFunctions.wait(1);
            if ($x(inputXPath).exists())
                break;
            if (n == 0)
                Assert.fail("Не найден элемент {By.xpath: " + inputXPath + "}");
            n++;
        }

        $x(inputXPath).scrollIntoView(false);
        $x(inputXPath).sendKeys(value);

        // В поле корректно отображается введенное значение
        Assert.assertEquals($x(inputXPath).getValue(), value);
        // Нет сообщений об ошибке
        Assert.assertFalse($x(inputAreaXPath + "//span[contains(@class, 'error')]").exists());

        return this;
    }

    /**
     * Установить чекбокс в поле области
     * @param area - область
     * @param field - поле
     */
    public GUIFunctions setCheckbox(String area, String field, boolean isCheckboxOn) {
        String checkboxAreaXpath = "//*[text()='" + area + "']/ancestor::div[not(@class)][1]//*[text()='" + field + "']/ancestor::div[not(@class)][1]";
        String checkboxXpath = checkboxAreaXpath + "//div[contains(@class,'checkMark')]";

        if(isCheckboxOn) {
            if ($x(checkboxAreaXpath + "//div[contains(@class,'checked')]/div").exists()) {
                System.out.println("Параметр «" + field + "» уже был включен");
            } else {
                $x(checkboxXpath).scrollIntoView(false);
                $x(checkboxXpath).click();
            }
            // В поле корректно отображается введенное значение
            Assert.assertTrue($x(checkboxAreaXpath + "//div[contains(@class,'checked')]").exists());
        } else {
            if ($x(checkboxAreaXpath + "//div[contains(@class,'checked')]/div").exists()) {
                $x(checkboxXpath).scrollIntoView(false);
                $x(checkboxXpath).click();
            } else {
                System.out.println("Параметр «" + field + "» уже был выключен");
            }
            // В поле корректно отображается введенное значение
            Assert.assertTrue($x(checkboxAreaXpath + "//div[count(contains(@class,'checked'))=0]").exists());
        }
        // Нет сообщений об ошибке
        Assert.assertFalse($x(checkboxAreaXpath + "//span[contains(@class, 'error')]").exists());

        return this;
    }

}

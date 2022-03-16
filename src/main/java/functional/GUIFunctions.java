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
        String loginXpath = "//input[@name='username']";
        String passwordXpath = "//input[@name='password']";
        String submitButtonXpath = "//button[@id='submit']";

        waitForElementDisplayed(loginXpath, defaultWaitingTime, false);
        $x(loginXpath).sendKeys(login);

        waitForElementDisplayed(passwordXpath, defaultWaitingTime, false);
        $x(passwordXpath).sendKeys(password);

        clickWebElement(submitButtonXpath);

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
     * Проверить значение поля в области
     * @param area - область
     * @param field - родительский чекбокс
     * @param value - радиокнопка
     */
    public GUIFunctions assertValueInFieldInArea(String area, String field, String value) {
        String inputXpath = "//*[text() = '" + area + "']/ancestor::div[contains(@class, 'container')][1]" +
                "/descendant::div[descendant::*[text() = '" + field + "'] and descendant::input][last()]//input";

        Assert.assertEquals($x(inputXpath).getValue(), value);

        return this;
    }

    /**
     * Проверить НЕотображение контроля в поле области
     * @param area - область
     * @param field - поле
     */
    public GUIFunctions assertNoControlInFieldInArea(String area, String field) {
        String controlFieldXpath = "//*[text() = '" + area + "']/ancestor::div[contains(@class, 'container')][1]" +
                "/descendant::div[descendant::*[text() = '" + field + "'] and descendant::input][last()]//span[contains(@class, 'error')]";

        Assert.assertFalse($x(controlFieldXpath).isDisplayed());

        return this;
    }

    /**
     * Подсветка элемента (выделение границ элемента)
     * @param xpath - локатор
     */
    private void highlightSelenideElement(String xpath) {
        SelenideElement element = $x(xpath);
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
        return this;
    }

    public GUIFunctions inputInSearchField(String searchFieldPlaceholder, String value) {
        String searchFieldXpath = "//input[contains(@placeholder, '" + searchFieldPlaceholder + "')]" +
                "[following-sibling::*[descendant::*[name()='use' and contains(@*, '#search')]]]";

        waitForElementDisplayed(searchFieldXpath, defaultWaitingTime);
        $x(searchFieldXpath).sendKeys(value);
        $x(searchFieldXpath).pressEnter();

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
        $x(xpath).click();

        return this;
    }

    /**
     * Кликнуть по кнопке (по тексту)
     * @param buttonText - текст
     */
    public GUIFunctions clickButton(String buttonText) {
        clickWebElement("//*[text()='" + buttonText + "']");
        return this;
    }

    /**
     * Кликнуть по кнопке в области (по тексту)
     * @param area - область
     * @param buttonText - текст
     */
    public GUIFunctions clickButtonInArea(String area, String buttonText) {
        clickWebElement("//*[text()='" + area + "']" +
                "/ancestor::*[contains(@class, 'container')][1]//*[text()='" + buttonText + "']");
        return this;
    }

    /**
     * Ввести значение в поле области
     * @param area - область
     * @param field - поле
     * @param value - значение
     */
    public GUIFunctions inputValueInArea(String area, String field, String value) {
        String inputXpath = "//*[text() = '" + area + "']/ancestor::div[contains(@class, 'container')][1]" +
                "/descendant::div[descendant::*[text() = '" + field + "'] and descendant::input][last()]//input";
        $x(inputXpath).sendKeys(value);

        //В поле корректно отображается введенное значение
        assertValueInFieldInArea(area, field, value);
        //Нет сообщений об ошибке
        assertNoControlInFieldInArea(area, field);

        return this;
    }

    /**
     * Выбрать значение из выпадающего списка области
     * @param area - область
     * @param field - поле
     * @param value - значение
     */
    public GUIFunctions selectValueFromDropdownInArea(String area, String field, String value) {
        String fieldXpath = "//*[text() = '" + area + "']" +
                "/ancestor::div[(contains(@class, 'WithExpansionPanel') or contains(@class, 'Modal_wrapper')) and descendant::*[text() = '" + field + "']]" +
                "/descendant::div[descendant::*[text() = '" + field + "'] and descendant::input][last()]//input";
        String valueXpath = "//*[text() = '" + area + "']" +
                "/ancestor::div[(contains(@class, 'WithExpansionPanel') or contains(@class, 'Modal_wrapper')) and descendant::*[text() = '" + field + "']]" +
                "/descendant::div[descendant::*[text() = '" + field + "'] and descendant::input][last()]//*[contains(text(), '" + value + "')]";

        clickWebElement(fieldXpath);
        clickWebElement(valueXpath);

        //В поле корректно отображается введенное значение
        assertValueInFieldInArea(area, field, value);
        //Нет сообщений об ошибке
        assertNoControlInFieldInArea(area, field);

        return this;
    }

    /**
     * Установить чекбокс в поле области
     * @param area - область
     * @param field - поле
     * @param isCheckboxOn - значение чекбокса
     */
    public GUIFunctions setCheckboxInArea(String area, String field, boolean isCheckboxOn) {
        String checkboxAreaXpath = "//*[text()='" + area + "']" +
                "/ancestor::*[contains(@class, 'container')][1]//*[text()='" + field + "']/ancestor::div[not(@class)][1]";
        String checkboxXpath = checkboxAreaXpath + "//div[contains(@class,'checkMark')]";

        if(isCheckboxOn) {
            if ($x(checkboxAreaXpath + "//div[contains(@class,'checked')]/div").exists()) {
                System.out.println("Параметр «" + field + "» уже был включен");
            } else {
                $x(checkboxXpath).click();
            }
            // В поле корректно отображается введенное значение
//            Assert.assertTrue($x(checkboxAreaXpath + "//div[contains(@class,'checked')]").exists());
        } else {
            if ($x(checkboxAreaXpath + "//div[contains(@class,'checked')]/div").exists()) {
                $x(checkboxXpath).click();
            } else {
                System.out.println("Параметр «" + field + "» уже был выключен");
            }
            // В поле корректно отображается введенное значение
//            Assert.assertTrue($x(checkboxAreaXpath + "//div[count(contains(@class,'checked'))=0]").exists());
        }

        //Нет сообщений об ошибке
//        Assert.assertFalse($x(checkboxAreaXpath + "//span[contains(@class, 'error')]").exists());

        return this;
    }

    /**
     * Установить чекбокс под чекбоксом в поле области
     * @param area - область
     * @param parentCheckbox - родительский чекбокс
     * @param Radiobutton - радиокнопка
=     */
    public GUIFunctions setRadiobuttonUnderСheckboxInArea(String area, String parentCheckbox, String Radiobutton) {
        String radiobuttonXpath = "//*[text()='" + area + "']/ancestor::div[contains(@class, 'WithExpansionPanel')]" +
                "//div[contains(@class, 'WithExpansionPanel')]/div[descendant::*[text()='" + parentCheckbox +"']]" +
                "/following-sibling::div[descendant::*[contains(text(), '" + Radiobutton + "')]][1]//div[contains(@class,'checkMark')]";
        $x(radiobuttonXpath).click();

        return this;
    }

    /**
     * Установить радиокнопку в поле области
     * @param area - область
     * @param field - поле
     */
    public GUIFunctions setRadiobuttonInArea(String area, String field, boolean isCheckboxOn) {
        String checkboxAreaXpath = "//*[text()='" + area + "']" +
                "/ancestor::*[contains(@class, 'container')][1]//*[text()='" + field + "']/ancestor::div[not(@class)][1]";
        String checkboxXpath = checkboxAreaXpath + "//div[contains(@class,'checkMark')]";

        if(isCheckboxOn) {
            if ($x(checkboxAreaXpath + "//div[contains(@class,'checked')]/div").exists()) {
                System.out.println("Параметр «" + field + "» уже был включен");
            } else {
                $x(checkboxXpath).click();
            }
            // В поле корректно отображается введенное значение
//            Assert.assertTrue($x(checkboxAreaXpath + "//div[contains(@class,'checked')]").exists());
        } else {
            if ($x(checkboxAreaXpath + "//div[contains(@class,'checked')]/div").exists()) {
                $x(checkboxXpath).click();
            } else {
                System.out.println("Параметр «" + field + "» уже был выключен");
            }
            // В поле корректно отображается введенное значение
//            Assert.assertTrue($x(checkboxAreaXpath + "//div[count(contains(@class,'checked'))=0]").exists());
        }

        //Нет сообщений об ошибке
//        Assert.assertFalse($x(checkboxAreaXpath + "//span[contains(@class, 'error')]").exists());

        return this;
    }


}

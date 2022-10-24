package functions.gui;

import com.codeborne.selenide.Condition;
import functions.common.CommonFunctions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class mdm_functions {

    public mdm_functions() {
    }

    protected final int DEFAULT_WAITING_TIME = 60;

    /**
     * Ожидание отображения элемента
     * @param locator - локатор
     * @return
     */
    public mdm_functions waitForElementDisplayed(String locator) {
        $x(locator).shouldBe(Condition.visible, Duration.ofSeconds(DEFAULT_WAITING_TIME));

        return this;
    }

    /**
     * Ожидание отображения элемента
     * @param locator - локатор
     * @param seconds - время ожидания в секундах
     * @return
     */
    public mdm_functions waitForElementDisplayed(String locator, int seconds) {
        $x(locator).shouldBe(Condition.visible, Duration.ofSeconds(seconds));

        return this;
    }

    /**
     * Ожидание исчезновения элемента
     * @param locator - локатор
     * @return
     */
    public mdm_functions waitForElementDisappeared(String locator) {
        $x(locator).should(Condition.disappear, Duration.ofSeconds(DEFAULT_WAITING_TIME));

        return this;
    }

    /**
     * Ожидание исчезновения элемента
     * @param locator - локатор
     * @param seconds - время ожидания в секундах
     * @return
     */
    public mdm_functions waitForElementDisappeared(String locator, int seconds) {
        $x(locator).should(Condition.disappear, Duration.ofSeconds(seconds));

        return this;
    }

    /**
     * Ожидание окончания Обработки запроса
     * @param maxWaitSeconds - максимальное время ожидания
     * @return*/
    @Deprecated
    public mdm_functions waitForLoading(int maxWaitSeconds) {
        CommonFunctions.wait(2);
        if ($x("//div[@class='z-loading-indicator']").isDisplayed()) {
            System.out.println("Ожидание обработки запроса...");
            $x("//div[@class='z-loading-indicator']").shouldBe(Condition.not(Condition.visible), Duration.ofSeconds(maxWaitSeconds));
        }
        return this;
    }

    /**
     * Ожидание отображения элемента и клик по нему
     * @param locator - локатор элемента
     * @return
     */
    public mdm_functions clickWebElement(String locator) {
        $x(locator).shouldBe(Condition.exist, Duration.ofSeconds(DEFAULT_WAITING_TIME));
        executeJavaScript("arguments[0].scrollIntoView();", $x(locator));

        waitForElementDisplayed(locator);
        $x(locator).click();

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }

    /**
     * Нажать кнопку
     * @param buttonText - значение
     * @return
     */
    public mdm_functions clickButtonByText(String buttonText) {

        String buttonXPath = "//button//span[text()='" + buttonText + "']";

        clickWebElement(buttonXPath);

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }

    /**
     * Ожидание отображения элемента и клик по нему
     * @param locator - локатор элемента
     * @return
     */
    public mdm_functions inputValueInWebElement(String locator, String value) {

        waitForElementDisplayed(locator);
        clickWebElement(locator); //кликнуть, на всякий случай

        $x(locator).sendKeys(value);

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }

    /**
     * Авторизация
     * @param login - логин
     * @param password  - пароль
     * @return
     */
    public mdm_functions authorization(String login, String password) {

        String loginXPath = "//input[@name='userName']";
        String passwordXPath = "//input[@name='password']";

        inputValueInWebElement(loginXPath, login);
        inputValueInWebElement(passwordXPath, password);

        clickButtonByText("Войти");

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }

    /**
     * Указать поисковый запрос
     * @param value - значение
     * @return
     */
    public mdm_functions inputInSearchField(String value) {

        String searchFieldXPath = "//input[@name='requestString']";

        inputValueInWebElement(searchFieldXPath, value);

        clickButtonByText("Поиск");

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }

    /**
     * Открыть результат поискового запроса
     * @param value - значение
     * @return
     */
    public mdm_functions openSearchResult(String value) {

        String searchResultFieldXPath = "//tr//td[text()='" + value + "']";

        $x(searchResultFieldXPath).doubleClick();

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }

    /**
     * Ввести значение в поле
     * @param field - поле
     * @param value - значение
     * @return
     */
    public mdm_functions inputValueInField(String field, String value) {

        String inputFieldXPath = "//strong[text()='" + field + "']/ancestor::div[1]/following-sibling::div//input";

        inputValueInWebElement(inputFieldXPath, value);

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }

    /**
     * Выбрать значение в поле
     * @param field - поле
     * @param value - значение
     * @return
     */
    public mdm_functions selectValueInField(String field, String value) {

        String inputFieldXPath = "//strong[text()='" + field + "']/ancestor::div[1]/following-sibling::div//input";
        String elementXPath = "//div[contains(@class, 'ant-select-dropdown ant-select-dropdown-placement-bottomLeft')]" +
                "[not(contains(@class, 'ant-select-dropdown-hidden'))]//div[text()='" + value + "']";

        clickWebElement(inputFieldXPath);
        clickWebElement(elementXPath);

        waitForLoading(DEFAULT_WAITING_TIME);
        return this;
    }


}

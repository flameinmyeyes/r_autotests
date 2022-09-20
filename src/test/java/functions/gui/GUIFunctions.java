package functions.gui;

import com.codeborne.selenide.SelenideElement;
import functions.common.CommonFunctions;
import functions.gui.ext.*;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class GUIFunctions extends ElementData {

    public GUIFunctions() {
    }

    /**
     * setters
     */

    public GUIFunctions inContainer(String container) {
        this.container = container;
        return this;
    }

    public GUIFunctions inField(String field) {
        this.field = field;
        return this;
    }

    /**
     * switch page
     */

    public GUIFunctions switchPageTo(int page) {
        switchTo().window(page);
        return this;
    }

    /**
     * scroll page
     */

    public GUIFunctions scrollTo(SelenideElement element) {
        element.scrollTo();
        return this;
    }

    public GUIFunctions scrollTo(String button) {
        $x("//*[text() = '" + button + "']").scrollTo();
        return this;
    }

    /**
     * authorization
     */

    public GUIFunctions authorization(String login, String password) {
        this.inContainer("Вход в личный кабинет")
            .inField("Email").inputValue(login)
            .inField("Пароль").inputValue(password)
            .clickButton("Войти");
        return new GUIFunctions();
    }

    public GUIFunctions authorization(String login, String password, String code) {
        this.inContainer("Вход в личный кабинет")
                .inField("Email").inputValue(login)
                .inField("Пароль").inputValue(password)
                .clickButton("Войти");

        int n = 0;
        while (n < code.length()) {
            $x(new XPath(this).getContainerXPath() + "//input[@data-id=" + n + "]")
                    .sendKeys(code.substring(n, n + 1));
            n++;
        }
        return new GUIFunctions();
    }

    /**
     * logout
     */

    public GUIFunctions logout() {
        new Click(this).clickByLocator("//div[@class='header-right']//div[contains(@class, 'exit')]");
//        waitForElementDisplayed("//*[text()='Вход в личный кабинет']/ancestor::div[contains(@class, 'container')][1]");
        return this;
    }


    /**
     * search in page
     */

    public GUIFunctions inputInSearchField(String searchFieldPlaceholder, String value) {
        new Field(this).inputInSearchField(searchFieldPlaceholder, value);
        return this;
    }

    public GUIFunctions openSearchResult(String searchResultName, String buttonName) {
        new Click(this).openSearchResult(searchResultName, buttonName);
        return this;
    }

    /**
     * input ext
     */

    public GUIFunctions inputValue(String value) {
        this.value = value;
        new Field(this).inputValue(value);
        return this;
    }

    public GUIFunctions selectValue(String value) {
        this.value = value;
        new Field(this).selectValue(value);
        return this;
    }

    public GUIFunctions assertValue() {
        new Asserts(this).assertValue();
        return this;
    }

    public GUIFunctions assertValue(String expectedValue) {
        new Asserts(this).assertValue(expectedValue);
        return this;
    }

    @Deprecated
    public GUIFunctions assertValueContains(String subValue) {
        new Asserts(this).assertValueContains(subValue);
        return this;
    }

    public GUIFunctions assertEditable() {
        new Asserts(this).assertEditable();
        return this;
    }

    public GUIFunctions assertUneditable() {
        new Asserts(this).assertUneditable();
        return this;
    }

    public GUIFunctions assertNoControl() {
        new Asserts(this).assertNoControl();
        return this;
    }


    /**
     * checkbox ext
     */

    public GUIFunctions setCheckboxON() {
        cbCondition = true;
        new Field(this).setCheckboxON();
        return this;
    }

    public GUIFunctions assertCheckboxON() {
        new Asserts(this).assertCheckboxON();
        return this;
    }


    public GUIFunctions setCheckboxOFF() {
        cbCondition = false;
        new Field(this).setCheckboxOFF();
        return this;
    }

    public GUIFunctions assertCheckboxOFF() {
        new Asserts(this).assertCheckboxOFF();
        return this;
    }


    public GUIFunctions setRadiobuttonByDescription(String description) {
        rbCondition = true;
        this.description = description;
        new Field(this).setRadiobuttonByDescription();
        return this;
    }

    public GUIFunctions assertRadiobuttonONByDescription() {
        new Asserts(this).assertRadiobuttonONByDescription();
        return this;
    }

    /**
     * click
     */

    public GUIFunctions clickButton(String button) {
        this.button = button;
        new Click(this).clickButton();
        return this;
    }

    public GUIFunctions clickByLocator(String xPath) {
        this.xPath = xPath;
        new Click(this).clickByLocator(xPath);
        return this;
    }

    public GUIFunctions selectTab(String tabName) {
        button = tabName;
        new Click(this).selectTab(tabName);
        return this;
    }

    public GUIFunctions closeAllPopupWindows() {
        new Click(this).closeAllPopupWindows();
        return this;
    }

    /**
     * upload
     */

    public GUIFunctions uploadFile(String upload, String wayToFile) {
        new Upload(this).uploadFile(upload, wayToFile);
        return this;
    }

    /**
     * wait
     */

    public GUIFunctions waitForURL(String URL) {
        new Wait(this).waitForURL(URL);
        return this;
    }

    public GUIFunctions waitForLoading() {
        new Wait(this).waitForLoading();
        return this;
    }

    public GUIFunctions waitForElementDisplayed(String xPath) {
        new Wait(this).waitForElementDisplayed(xPath);
        return this;
    }

    public GUIFunctions waitForElementDisappeared(String xPath) {
        new Wait(this).waitForElementDisappeared(xPath);
        return this;
    }

    /**
     * refresh
     */

    public GUIFunctions refreshTab(SelenideElement element, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if(element.isDisplayed()) {
                break;
            }
            refresh();
            CommonFunctions.wait(1);
        }
        return this;
    }

    public GUIFunctions refreshTab(String button, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x("//*[text() = '" + button + "']").isDisplayed()) {
                break;
            }
            refresh();
            CommonFunctions.wait(1);
        }
        return this;
    }

    /**
     * finplatforma 28.04.2022
     */

    public GUIFunctions authorizationLib(String login, String password) {
        $x("//input[@placeholder='E-mail']").setValue(login);
        $x("//input[@placeholder='Пароль']").setValue(password);
        CommonFunctions.wait(1);
        $x("//button[@type='button']").click();
        return new GUIFunctions();
    }

    public GUIFunctions setValueInFieldFromSelect(String value, String field){
        if ($x("//span[text()='" + field + "']/following::span[@class='ant-select-selection-item']").isDisplayed()){
            $x("//span[text()='" + field + "']/following::span[@class='ant-select-selection-item']").click();
        } else {
            $x("//span[text()='" + field + "']/following::div[@class='ant-form-item-control-input-content']").click();
        }
        $x("//*[text()='" + field + "']//following::*[text()='" + value + "']").click();
        return this;
    }

    public GUIFunctions setValueInField(String value, String field){
        $x("//*[text()='" + field + "']/following::input").setValue(value);
        return this;
    }

    public GUIFunctions setTextInField(String value, String field){
        $x("//span[text()='" + field + "']/following::textarea").setValue(value);
        return this;
    }

    public GUIFunctions setValueInPlaceholder(String value, String placeholder){
        $x("//input[@placeholder='" + placeholder + "']").setValue(value);
        return this;
    }

    public GUIFunctions setCheckboxOnInField(String field){
        $x("//*[contains(text(), '" + field + "')]/parent::label/span").click();

        return this;
    }

    public GUIFunctions setRadioButtonInField(String field){
        $x("//*[text()='" + field + "']//ancestor::label//child::input").click();
        return this;
    }

    public GUIFunctions setCheckboxONValueInFieldFromSelect(String value, String field){
        $x("//span[text()='" + field + "']/following::input").click();
        $x("//*[text()='" + field + "']//following::*[text()='" + value + "']//child::span[@class=\"ant-checkbox\"]").click();
        $x("//span[text()='" + field + "']/following::input").sendKeys(Keys.ESCAPE);
        return this;
    }
}

package functions.gui.fin;

import functions.common.CommonFunctions;

import functions.gui.fin.components.*;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

public class GUIFunctions extends ElementData {

    public GUIFunctions() {
    }

    /**
     * setters
     */

//    public GUIFunctions inContainer(String container) {
//        this.container = container;
//        return this;
//    }

    public GUIFunctions inField(String field) {
        this.field = field;
        return this;
    }

    public GUIFunctions inPlaceholder(String placeholder) {
        this.placeholder = placeholder;
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
     * authorization
     */

    public GUIFunctions authorization(String login, String password) {
        $x("//input[@placeholder='E-mail']").setValue(login);
        $x("//input[@placeholder='Пароль']").setValue(password);
        CommonFunctions.wait(1);
        $x("//button[@type='button']").click();
        return new GUIFunctions();
    }

    /**
     * input ext
     */

    public GUIFunctions inputValue(String value) {
        this.value = value;
        new Field(this).inputValue(value);
        return this;
    }

    public GUIFunctions inputText(String value) {
        this.value = value;
        new Field(this).inputText(value);
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
     * checkbox fin
     */

    public GUIFunctions setCheckboxON() {
        cbCondition = true;
//        new Field(this).setCheckboxON();
        $x(new XPath(this).getFieldXPath() + "/parent::label/span").click();
        return this;
    }

    public GUIFunctions setCheckboxONInValue(String value) {
        cbCondition = true;
        $x(new XPath(this).getFieldXPath() + "/following::input").click();
        $x(new XPath(this).getFieldXPath() + "//following::*[text()='" + value + "']//child::span[@class=\"ant-checkbox\"]").click();
        $x(new XPath(this).getFieldXPath() + "/following::input").sendKeys(Keys.ESCAPE);
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

    public GUIFunctions setRadiobuttonON() {
        rbCondition = true;
        $x(new XPath(this).getFieldXPath() + "//ancestor::label//child::input").click();
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
}

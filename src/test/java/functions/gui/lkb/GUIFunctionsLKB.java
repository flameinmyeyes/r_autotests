package functions.gui.lkb;

import functions.common.CommonFunctions;
import functions.gui.lkb.components.*;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

public class GUIFunctionsLKB extends ElementData {

    public GUIFunctionsLKB() {
    }

    /**
     * setters
     */

//    public GUIFunctions inContainer(String container) {
//        this.container = container;
//        return this;
//    }

    public GUIFunctionsLKB inField(String field) {
        this.field = field;
        return this;
    }

    public GUIFunctionsLKB inPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    /**
     * switch page
     */

    public GUIFunctionsLKB switchPageTo(int page) {
        switchTo().window(page);
        return this;
    }

    /**
     * authorization
     */

    public GUIFunctionsLKB authorization(String login, String password) {
        this.inPlaceholder("E-mail").inputValue(login)
                        .inPlaceholder("Пароль").inputValue(password);
        CommonFunctions.wait(1);
        this.clickByLocator("//button[@type='button']");
        return new GUIFunctionsLKB();
    }

    /**
     * input lkb
     */

    public GUIFunctionsLKB inputValue(String value) {
        this.value = value;
        new Field(this).inputValue(value);
        return this;
    }

    public GUIFunctionsLKB inputText(String value) {
        this.value = value;
        new Field(this).inputText(value);
        return this;
    }

    public GUIFunctionsLKB selectValue(String value) {
        this.value = value;
        new Field(this).selectValue(value);
        return this;
    }

    public GUIFunctionsLKB assertValue() {
        new Asserts(this).assertValue();
        return this;
    }

    public GUIFunctionsLKB assertValue(String expectedValue) {
        new Asserts(this).assertValue(expectedValue);
        return this;
    }

    public GUIFunctionsLKB assertCheckboxON() {
        new Asserts(this).assertCheckboxON();
        return this;
    }

    public GUIFunctionsLKB assertCheckboxOFF() {
        new Asserts(this).assertCheckboxOFF();
        return this;
    }

    public GUIFunctionsLKB assertRadiobuttonON() {
        new Asserts(this).assertRadiobuttonON();
        return this;
    }

    @Deprecated
    public GUIFunctionsLKB assertValueContains(String subValue) {
        new Asserts(this).assertValueContains(subValue);
        return this;
    }

    public GUIFunctionsLKB assertEditable() {
        new Asserts(this).assertEditable();
        return this;
    }

    public GUIFunctionsLKB assertUneditable() {
        new Asserts(this).assertUneditable();
        return this;
    }

    public GUIFunctionsLKB assertNoControl() {
        new Asserts(this).assertNoControl();
        return this;
    }

    /**
     * checkbox lkb
     */

    public GUIFunctionsLKB setCheckboxON() {
        cbCondition = true;
        new Field(this).setCheckboxON();
        return this;
    }

    public GUIFunctionsLKB setCheckboxOFF() {
        cbCondition = false;
        new Field(this).setCheckboxOFF();
        return this;
    }

    @Deprecated
    public GUIFunctionsLKB setCheckboxONInValue(String value) {
        cbCondition = true;
        this.value = value;
        new Field(this).setCheckboxONInValue();
        return this;
    }

    public GUIFunctionsLKB setRadiobuttonON() {
        rbCondition = true;
        $x(new XPath(this).getFieldXPath() + "//ancestor::label//child::input").click();
        return this;
    }

    /**
     * click
     */

    public GUIFunctionsLKB clickButton(String button) {
        this.button = button;
        new Click(this).clickButton();
        return this;
    }

    public GUIFunctionsLKB clickByLocator(String xPath) {
        this.xPath = xPath;
        new Click(this).clickByLocator(xPath);
        return this;
    }

    /**
     * wait
     */

    public GUIFunctionsLKB waitForURL(String URL) {
        new Wait(this).waitForURL(URL);
        return this;
    }

    public GUIFunctionsLKB waitForLoading() {
        new Wait(this).waitForLoading();
        return this;
    }

    public GUIFunctionsLKB waitForElementDisplayed(String xPath) {
        new Wait(this).waitForElementDisplayed(xPath);
        return this;
    }

    public GUIFunctionsLKB waitForElementDisappeared(String xPath) {
        new Wait(this).waitForElementDisappeared(xPath);
        return this;
    }

    /**
     * scroll
     */

    public GUIFunctionsLKB scrollToElement(String xPath) {
        $x(xPath).scrollTo();
        return this;
    }
}

package functions.gui;

import functions.gui.ext.*;

import static com.codeborne.selenide.Selenide.switchTo;

public class AllFunctions extends ElementData {

    protected AllFunctions(ElementData data) {
        super(data);
    }

    /**
     * setters
     */

    public AllFunctions inContainer(String container) {
        this.container = container;
        return this;
    }

    public InField inField(String field) {
        this.field = field;
        return new InField(this);
    }

    /**
     * switch page
     */

    public AllFunctions switchPageTo(int page) {
        switchTo().window(page);
        return this;
    }

    /**
     * search in page
     */

    public AllFunctions inputInSearchField(String searchFieldPlaceholder, String value) {
        new Field(this).inputInSearchField(searchFieldPlaceholder, value);
        return this;
    }

    public AllFunctions openSearchResult(String searchResultName, String buttonName) {
        new Click(this).openSearchResult(searchResultName, buttonName);
        return this;
    }

    /**
     * input ext
     */

    public AllFunctions inputValue(String value) {
        this.value = value;
        new Field(this).inputValue(value);
        return this;
    }

    public AllFunctions selectValue(String value) {
        this.value = value;
        new Field(this).selectValue(value);
        return this;
    }

    public AllFunctions assertValue() {
        new Asserts(this).assertValue();
        return this;
    }

    public AllFunctions assertValue(String expectedValue) {
        new Asserts(this).assertValue(expectedValue);
        return this;
    }

    @Deprecated
    public AllFunctions assertValueContains(String subValue) {
        new Asserts(this).assertValueContains(subValue);
        return this;
    }

    public AllFunctions assertEditable() {
        new Asserts(this).assertEditable();
        return this;
    }

    public AllFunctions assertNoControl() {
        new Asserts(this).assertNoControl();
        return this;
    }


    /**
     * checkbox ext
     */

    public AllFunctions setCheckboxON() {
        cbCondition = true;
        new Field(this).setCheckboxON();
        return this;
    }

    public AllFunctions setCheckboxOFF() {
        cbCondition = false;
        new Field(this).setCheckboxOFF();
        return this;
    }

    public AllFunctions assertCheckboxON() {
        new Asserts(this).assertCheckboxON();
        return this;
    }

    public AllFunctions assertCheckboxOFF() {
        new Asserts(this).assertCheckboxOFF();
        return this;
    }

    public AllFunctions setRadiobuttonByDescription(String description) {
        rbCondition = true;
        this.description = description;
        new Field(this).setRadiobuttonByDescription(description);
        return this;
    }

    public AllFunctions assertRadiobuttonONByDescription() {
        new Asserts(this).assertRadiobuttonONByDescription();
        return this;
    }

    /**
     * click ext
     */

    public AllFunctions clickButton(String button) {
        this.button = button;
        new Click(this).clickButton(button);
        return this;
    }

    public AllFunctions clickByLocator(String xPath) {
        this.xPath = xPath;
        new Click(this).clickByLocator(xPath);
        return this;
    }

    public AllFunctions selectTab(String tabName) {
        button = tabName;
        new Click(this).selectTab(tabName);
        return this;
    }

    /**
     * upload
     */

    public AllFunctions uploadFile(String upload, String wayToFile) {
        new Upload(this).uploadFile(upload, wayToFile);
        return this;
    }

    /**
     * wait
     */

    public AllFunctions waitForURL(String URL) {
        new Wait(this).waitForURL(URL);
        return this;
    }

    public AllFunctions waitForLoading() {
        new Wait(this).waitForLoading();
        return this;
    }

    public AllFunctions waitForElementDisplayed(String xPath) {
        new Wait(this).waitForElementDisplayed(xPath);
        return this;
    }

    public AllFunctions waitForElementDisappeared(String xPath) {
        new Wait(this).waitForElementDisappeared(xPath);
        return this;
    }

}

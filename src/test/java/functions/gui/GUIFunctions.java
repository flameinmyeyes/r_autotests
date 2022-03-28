package functions.gui;

import functions.gui.ext.Click;
import functions.gui.ext.Field;
import functions.gui.ext.Upload;
import functions.gui.ext.Wait;

import static com.codeborne.selenide.Selenide.switchTo;

public class GUIFunctions extends ElementData {

    public GUIFunctions() {
    }

    /**
     * setters
     */

    public AllFunctions inContainer(String container) {
        this.container = container;
        return new AllFunctions(this);
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
        return new AllFunctions(this);
    }

    /**
     * search in page
     */

    public AllFunctions inputInSearchField(String searchFieldPlaceholder, String value) {
        new Field(this).inputInSearchField(searchFieldPlaceholder, value);
        return new AllFunctions(this);
    }

    public AllFunctions openSearchResult(String searchResultName, String buttonName) {
        new Click(this).openSearchResult(searchResultName, buttonName);
        return new AllFunctions(this);
    }

    /**
     * click ext
     */

    public AllFunctions clickButton(String button) {
        this.button = button;
        new Click(this).clickButton();
        return new AllFunctions(this);
    }

    public AllFunctions clickByLocator(String xPath) {
        this.xPath = xPath;
        new Click(this).clickByLocator(xPath);
        return new AllFunctions(this);
    }

    public AllFunctions selectTab(String tabName) {
        new Click(this).selectTab(tabName);
        return new AllFunctions(this);
    }

    /**
     * upload
     */

    public AllFunctions uploadFile(String upload, String wayToFile) {
        new Upload(this).uploadFile(upload, wayToFile);
        return new AllFunctions(this);
    }

    /**
     * wait
     */

    public AllFunctions waitForURL(String URL) {
        new Wait(this).waitForURL(URL);
        return new AllFunctions(this);
    }

    public AllFunctions waitForLoading() {
        new Wait(this).waitForLoading();
        return new AllFunctions(this);
    }

    public AllFunctions waitForElementDisplayed(String xPath) {
        new Wait(this).waitForElementDisplayed(xPath);
        return new AllFunctions(this);
    }

    public AllFunctions waitForElementDisappeared(String xPath) {
        new Wait(this).waitForElementDisappeared(xPath);
        return new AllFunctions(this);
    }

}

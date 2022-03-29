package functions.gui.ext;

import functions.gui.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Click extends ElementData {

    public Click(ElementData data) {
        super(data);
    }

    public void clickButton() {
        $x(new XPath(this).getButtonXPath()).click();
    }

    public void clickByLocator(String xPath) {
        $x(xPath).click();
    }

    public void selectTab(String tabName) {
        $x("//li[@data-name = '" + tabName + "']").click();
    }

    public void openSearchResult(String searchResultName, String buttonName) {
        String searchResultXpath = "//*[contains(text(), '" + searchResultName + "')]" +
                "//following-sibling::div//descendant::*[contains(text(), '" + buttonName + "')]";
        $x(searchResultXpath).click();
        new Wait().waitForLoading();
    }

    public void closeAllPopupWindows() {
        if ($x("//*[contains(text(), 'Принять')]").isDisplayed())
            $x("//*[contains(text(), 'Принять')]").click();

        if ($x("//div[@class='info-line__close-btn']").isDisplayed())
            $x("//div[@class='info-line__close-btn']").click();

        if ($x("//*[text()='Да']").isDisplayed())
            $x("//*[text()='Да']").click();
    }

}

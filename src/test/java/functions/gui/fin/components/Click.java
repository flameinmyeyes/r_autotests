package functions.gui.fin.components;

import functions.gui.fin.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Click extends ElementData {

    public Click(ElementData data) {
        super(data);
    }

    public void clickButton() {
        $x(new XPath(this).getButtonXPath()).click();
    }

    public void clickByLocator(String xPath) {
        $x(new XPath(this).getContainerXPath() + xPath).click();
    }

    public void openSearchResult(String searchResultName, String buttonName) {
        String searchResultXpath = "//*[contains(text(), '" + searchResultName + "')]" +
                "//following-sibling::div//descendant::*[contains(text(), '" + buttonName + "')]";
        $x(searchResultXpath).click();
        new Wait().waitForLoading();
    }

}

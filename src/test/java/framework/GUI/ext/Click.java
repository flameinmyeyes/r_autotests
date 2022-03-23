package framework.GUI.ext;

import framework.GUI.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Click extends ElementData {

    public Click(ElementData data) {
        super(data);
    }

    public void clickButton(String button) {
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

}

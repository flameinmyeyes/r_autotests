package functions.gui.ext;

import functions.gui.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Field extends ElementData {

    public Field(ElementData data) {
        super(data);
    }

    public void inputValue(String value) {
        $x(new XPath(this).getInputXPath()).setValue(value);
        $x(new XPath(this).getInputXPath()).click();

        new Wait().waitForLoading();
    }

    public void selectValue(String value) {
        String inputField = new XPath(this).getInputXPath();
        String item = new XPath(this).getSearchedValueXPath(value);
        $x(inputField).click();
        $x(inputField).setValue(value);
        $x(item).click();

        new Wait().waitForLoading();
    }

    public void inputInSearchField(String searchFieldPlaceholder, String value) {
        String searchFieldXpath = "//input[contains(@placeholder, '" + searchFieldPlaceholder + "')]" +
                "[following-sibling::*[descendant::*[name()='use' and contains(@*, '#search')]]]";

        //новый локатор
        if (!$x(searchFieldXpath).isDisplayed()) {
            searchFieldXpath = "//div[@class='js-tabs__block open']//input[@placeholder='" + searchFieldPlaceholder + "']";
        }

        $x(searchFieldXpath).setValue(value);
        $x(searchFieldXpath).pressEnter();
    }

    public void setCheckboxON() {
        if (!$x(new XPath(this).getCheckedCheckboxXPath()).exists()) {
            $x(new XPath(this).getCheckboxXPath()).click();
        }
        new Asserts(this).assertCheckboxON();
    }

    public void setCheckboxOFF() {
        if ($x(new XPath(this).getCheckedCheckboxXPath()).exists()) {
            $x(new XPath(this).getCheckboxXPath()).click();
        }
        new Asserts(this).assertCheckboxOFF();
    }

    public void setRadiobuttonByDescription() {
        $x(new XPath(this).getRadiobuttonByDescriptionXPath()).click();
        new Asserts(this).assertRadiobuttonONByDescription();
    }

}

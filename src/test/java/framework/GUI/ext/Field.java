package framework.GUI.ext;

import framework.GUI.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Field extends ElementData {

    public Field(ElementData data) {
        super(data);
    }

    public void inputValue(String value) {
        $x(new XPath(this).getInputXPath()).sendKeys(value);
    }

    public void selectValue(String value) {
        $x(new XPath(this).getInputXPath()).click();
        $x(new XPath(this).getItemXPath(value)).click();
    }

    public void inputInSearchField(String searchFieldPlaceholder, String value) {
        String searchFieldXpath = "//input[contains(@placeholder, '" + searchFieldPlaceholder + "')]" +
                "[following-sibling::*[descendant::*[name()='use' and contains(@*, '#search')]]]";

        $x(searchFieldXpath).sendKeys(value);
        $x(searchFieldXpath).pressEnter();
    }

    public void setCheckboxON() {
        if (!$x(new XPath(this).getCheckedCheckboxXPath()).exists()) {
            $x(new XPath(this).getCheckboxXPath()).click();
        }
    }

    public void setCheckboxOFF() {
        if ($x(new XPath(this).getCheckedCheckboxXPath()).exists()) {
            $x(new XPath(this).getCheckboxXPath()).click();
        }
    }

    public void setRadiobuttonByDescription(String description) {
        $x(new XPath(this).getRadiobuttonByDescriptionXPath()).click();
    }

}

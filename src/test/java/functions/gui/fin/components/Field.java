package functions.gui.fin.components;

import functions.gui.fin.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Field extends ElementData {

    public Field(ElementData data) {
        super(data);
    }

    public void inputValue(String value) {
        if (isFieldDefined()) {
            $x(new XPath(this).getInputValueXPath()).setValue(value);
        }
        if (isPlaceholderDefined()) {
            $x(new XPath(this).getPlaceholderXPath()).setValue(value);
        }
    }

    public void inputText(String value) {
        $x(new XPath(this).getInputTextXPath()).setValue(value);
    }

    public void selectValue(String value) {
        String item = new XPath(this).getSearchedValueXPath(value);
        $x(item).click();
    }

    public void setCheckboxON() {
//        if (!$x(new XPath(this).getCheckedCheckboxXPath()).exists()) {
//            $x(new XPath(this).getCheckboxXPath()).click();
//        }
        $x(new XPath(this).getCheckboxXPath()).click();
        new Asserts(this).assertCheckboxON();
    }

    public void setCheckboxOFF() {
        if ($x(new XPath(this).getCheckedCheckboxXPath()).exists()) {
            $x(new XPath(this).getCheckboxXPath()).click();
        }
        new Asserts(this).assertCheckboxOFF();
    }

//    public void setCheckboxONInValue() {
//        if (!$x(new XPath(this).getCheckedCheckboxXPath()).exists()) {
//            $x(new XPath(this).getCheckboxXPath()).click();
//        }
//        new Asserts(this).assertCheckboxON();
//    }

    public void setRadiobuttonByDescription() {
        $x(new XPath(this).getRadiobuttonByDescriptionXPath()).click();
        new Asserts(this).assertRadiobuttonONByDescription();
    }

}

package functions.gui.lkb.components;

import functions.common.CommonFunctions;
import functions.gui.lkb.ElementData;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$x;

public class Field extends ElementData {

    public Field(ElementData data) {
        super(data);
    }

    public void inputValue(String value) {
        $x(new XPath(this).getInputValueXPath()).click();
        CommonFunctions.wait(0.5);
        $x(new XPath(this).getInputValueXPath()).sendKeys(Keys.LEFT_CONTROL + "a");
        $x(new XPath(this).getInputValueXPath()).sendKeys(Keys.BACK_SPACE);
        $x(new XPath(this).getInputValueXPath()).setValue(value);
    }

    public void inputText(String value) {
        $x(new XPath(this).getInputTextXPath()).setValue(value);
    }

    public void selectValue(String value) {
        String item = new XPath(this).getSearchedValueXPath(value);
        CommonFunctions.wait(1);
        $x(item).click();
        if ($x(new XPath(this).getCheckedCheckboxXPath()).isDisplayed()){
            $x(new XPath(this).getFieldXPath() + "/following::input").sendKeys(Keys.ESCAPE);
        }
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

    public void setCheckboxONInValue() {
        $x(new XPath(this).getFieldXPath() + "/following::input").click();
        $x(new XPath(this).getFieldXPath() + "//following::*[text()='" + value + "']//child::span[@class='ant-checkbox']").click();
        $x(new XPath(this).getFieldXPath() + "/following::input").sendKeys(Keys.ESCAPE);
//        new Asserts(this).assertCheckboxON();
    }
}

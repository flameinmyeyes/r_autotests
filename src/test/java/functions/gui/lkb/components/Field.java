package functions.gui.lkb.components;

import functions.gui.lkb.ElementData;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$x;

public class Field extends ElementData {

    public Field(ElementData data) {
        super(data);
    }

    public void inputValue(String value) {
        $x(new XPath(this).getInputValueXPath()).setValue(value);
//        String text = $x(new XPath(this).getInputValueXPath()).getValue();
//        assertEquals(text, this.value);
    }

    public void inputText(String value) {
        $x(new XPath(this).getInputTextXPath()).setValue(value);
//        if (!$x(new XPath(this).getInputTextXPath()).getText().equals(value)){
//            fail("Текст после ввода в поле не соответствует первоначальному");
//        }
    }

    public void selectValue(String value) {
        String item = new XPath(this).getSearchedValueXPath(value);
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

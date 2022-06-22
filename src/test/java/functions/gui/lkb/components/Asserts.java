package functions.gui.lkb.components;

import functions.gui.lkb.ElementData;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.*;

public class Asserts extends ElementData {

    public Asserts(ElementData data) {
        super(data);
    }

    public void assertValue() {
        assertEquals(getValue(), value);
    }

    public void assertValue(String expectedValue) {
        assertEquals(getValue(), expectedValue);
    }

    private String getValue() {
        String actualValue = null;

        if ($x(new XPath(this).getSelectCheckboxValueXPath()).exists()) {
            actualValue = $x(new XPath(this).getSelectCheckboxValueXPath()).getText();
        } else if ($x(new XPath(this).getSelectValueXPath()).exists()) {
            actualValue = $x(new XPath(this).getSelectValueXPath()).getText();
        } else if ($x(new XPath(this).getInputTextXPath()).exists()) {
            actualValue = $x(new XPath(this).getInputTextXPath()).getText();
        } else if ($x(new XPath(this).getInputValueXPath()).exists()) {
            actualValue = $x(new XPath(this).getInputValueXPath()).getValue();
        }

        return actualValue.replaceAll("\\u00a0", " ");
    }

    @Deprecated
    public void assertValueContains(String subValue) {
        String actualValue = getValue();
        assertTrue(actualValue.contains(subValue));
    }

    public void assertEditable() {
        assertFalse($x(new XPath(this).getUneditableInputXPath()).exists());
    }

    public void assertUneditable() {
        assertTrue($x(new XPath(this).getUneditableInputXPath()).exists());
    }

    public void assertNoControl() {
        assertFalse($x(new XPath(this).getErrorXPath()).exists());
    }

    public void assertCheckboxON() {
        assertTrue($x(new XPath(this).getCheckedCheckboxXPath()).exists());
    }

    public void assertCheckboxOFF() {
        assertFalse($x(new XPath(this).getCheckedCheckboxXPath()).exists());
    }

    public void assertRadiobuttonON() {
        assertTrue($x(new XPath(this).getRadiobuttonXPath()).exists());
    }
}

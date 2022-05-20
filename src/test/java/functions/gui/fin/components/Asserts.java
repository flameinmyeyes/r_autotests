package functions.gui.fin.components;

import functions.gui.fin.ElementData;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.*;

public class Asserts extends ElementData {

    public Asserts(ElementData data) {
        super(data);
    }

    public void assertValue() {
        String actualValue = getValue();
        assertEquals(actualValue, value);
    }

    private String getValue() {
        String actualValue;
        String inputXPath = new XPath(this).getInputValueXPath();
        if ($x(inputXPath).exists()) {
            actualValue = $x(new XPath(this).getInputValueXPath()).getValue();
        } else {
            actualValue = $x(new XPath(this).getUneditableInputXPath()).getText();
        }
        return actualValue;
    }

    public void assertValue(String expectedValue) {
        String actualValue = getValue();
        assertEquals(actualValue, expectedValue);
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

    public void assertRadiobuttonONByDescription() {
        if (rbCondition) {
            assertTrue($x(new XPath(this).getCheckedRadiobuttonByDescriptionXPath()).exists());
        } else {
            fail("Не установлено ожидаемое состояние радиокнопки для его проверки\n" +
                    "Пожалуйста, перед использованием метода assertRadiobuttonONByDescription() убедитеть, что " +
                    "в рамках текущего объекта уже был применен один из методов setRadiobuttonByDescription()");
        }
    }

}
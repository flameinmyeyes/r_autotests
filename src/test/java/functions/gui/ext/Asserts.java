package functions.gui.ext;

import functions.gui.ElementData;

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
        String inputXPath = new XPath(this).getInputXPath();
//        System.out.println("inputXPath: " + inputXPath);

        //старая версия
//        if ($x(inputXPath).exists()) {
//            actualValue = $x(new XPath(this).getInputXPath()).getValue();
//            System.out.println("inputXPath exists");
//        } else {
//            actualValue = $x(new XPath(this).getUneditableInputXPath()).getText();
//            System.out.println("inputXPath !exists");
//        }

        if (inputXPath != null && $x(inputXPath).exists()) {
//            System.out.println("getInputXPath");
            actualValue = $x(new XPath(this).getInputXPath()).getValue();

            //костыль
            if (actualValue == null && $x(new XPath(this).getUneditableInputXPath()).getText() != null) {
                actualValue = $x(new XPath(this).getUneditableInputXPath()).getText();
            }
        } else {
//            System.out.println("getUneditableInputXPath");
            actualValue = $x(new XPath(this).getUneditableInputXPath()).getText();
        }

        //убираем NBSP (beta)
        actualValue = actualValue.replace(" ", " ");

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

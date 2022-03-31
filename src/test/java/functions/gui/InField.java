package functions.gui;

import functions.gui.ext.Asserts;
import functions.gui.ext.Field;

public class InField extends ElementData {

    protected InField(ElementData data) {
        super(data);
    }

    public AllFunctions inputValue(String value) {
        this.value = value;
        new Field(this).inputValue(value);
        return new AllFunctions(this);
    }

    public AllFunctions selectValue(String value) {
        this.value = value;
        new Field(this).selectValue(value);
        return new AllFunctions(this);
    }

    public AllFunctions assertValue() {
        new Asserts(this).assertValue();
        return new AllFunctions(this);
    }

    public AllFunctions assertValue(String expectedValue) {
        new Asserts(this).assertValue(expectedValue);
        return new AllFunctions(this);
    }

    public AllFunctions assertEditable() {
        new Asserts(this).assertEditable();
        return new AllFunctions(this);
    }

    public AllFunctions assertUneditable() {
        new Asserts(this).assertUneditable();
        return new AllFunctions(this);
    }

    @Deprecated
    public AllFunctions assertValueContains(String subValue) {
        new Asserts(this).assertValueContains(subValue);
        return new AllFunctions(this);
    }

    public AllFunctions assertThereIsNoControl() {
        new Asserts(this).assertNoControl();
        return new AllFunctions(this);
    }


    /**
     * checkbox ext
     */

    public AllFunctions setCheckboxON() {
        cbCondition = true;
        new Field(this).setCheckboxON();
        return new AllFunctions(this);
    }

    public AllFunctions setCheckboxOFF() {
        cbCondition = false;
        new Field(this).setCheckboxOFF();
        return new AllFunctions(this);
    }

    public AllFunctions assertCheckboxON() {
        new Asserts(this).assertCheckboxON();
        return new AllFunctions(this);
    }

    public AllFunctions assertCheckboxOFF() {
        new Asserts(this).assertCheckboxOFF();
        return new AllFunctions(this);
    }

    public AllFunctions setRadiobuttonByDescription(String description) {
        rbCondition = true;
        this.description = description;
        new Field(this).setRadiobuttonByDescription();
        return new AllFunctions(this);
    }

    public AllFunctions assertRadiobuttonONByDescription() {
        new Asserts(this).assertRadiobuttonONByDescription();
        return new AllFunctions(this);
    }

}

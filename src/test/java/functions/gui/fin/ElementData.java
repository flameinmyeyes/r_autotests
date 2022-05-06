package functions.gui.fin;

public class ElementData {

    protected String container = "";
    protected String field = "";
    protected String placeholder = "";
    protected String value = "";
    protected String button = "";
    protected String description = "";
    protected boolean cbCondition = false;
    protected boolean rbCondition = false;

    protected String xPath = "";

    protected ElementData(ElementData data) {
        container = data.container;
        field = data.field;
        placeholder = data.placeholder;
        button = data.button;
        value = data.value;
        description = data.description;
        cbCondition = data.cbCondition;
        rbCondition = data.rbCondition;
        xPath = data.xPath;
    }

    protected ElementData() {
    }

    protected boolean isContainerDefined() {
        return !container.equals("");
    }

    protected boolean isFieldDefined() {
        return !field.equals("");
    }
    protected boolean isPlaceholderDefined() {
        return !placeholder.equals("");
    }

    protected boolean isButtonDefined() {
        return !button.equals("");
    }

}

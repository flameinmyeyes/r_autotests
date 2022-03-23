package framework.GUI.ext;

import framework.GUI.ElementData;
import org.testng.Assert;

public class XPath extends ElementData {

    public XPath(ElementData data) {
        super(data);
    }

    private String getContainerXPath() {
        if (isContainerDefined()) {
            return "//*[text() = '" + container + "']/ancestor::div[contains(@class, 'container')][1]";
        }
        return "";
    }

    private String getFieldXPath() {
        if (isFieldDefined()) {
            return "//*[text() = '" + field + "']/ancestor::div[contains(@class,'Column_col') " +
                    "or contains(@class, 'inputWrapper') " +
                    "or contains(@class, 'Input_fullWidth')][1]";
        }
        Assert.fail("Не удалось создать xPath. Поле ввода не было задано\n" +
                "Пожалуйста, ипользуте метод inField(String field), чтобы задать поле ввода");
        return "";
    }


    /**
     * input/checkbox ext
     */

    public String getInputXPath() {
        return getContainerXPath() + getFieldXPath() + "//input";
    }

    public String getItemXPath(String item) {
        return getContainerXPath() + getFieldXPath() + "//*[contains(text(), '" + item + "')]";
    }

    public String getErrorXPath() {
        return getContainerXPath() + getFieldXPath() + "//span[contains(@class, 'error') and . !='']";
    }

    public String getCheckboxXPath() {
        return getContainerXPath() + getFieldXPath() + "//div[contains(@class,'checkMark')]";
    }

    public String getCheckedCheckboxXPath() {
        return getCheckboxXPath() + "//div[contains(@class,'checked')]";
    }

    public String getRadiobuttonByDescriptionXPath() {
        return getContainerXPath() + getFieldXPath() + "/descendant::*[contains(@class,'checkMark')]" +
                "[following::*[contains(text(),'" + description + "')]][last()]";
    }

    public String getCheckedRadiobuttonByDescriptionXPath() {
        return getRadiobuttonByDescriptionXPath() + "//div[contains(@class,'checked')]";
    }

    /**
     * button ext
     */

    public String getButtonXPath() {
        if (isButtonDefined()) {
            return getContainerXPath() + "//*[text() = '" + button + "']";
        }
        Assert.fail("Не удалось создать xPath. Название кнопки не было задано\n" +
                "Пожалуйста, ипользуте метод clickButton(String button), чтобы задать поле ввода");
        return "";
    }

}

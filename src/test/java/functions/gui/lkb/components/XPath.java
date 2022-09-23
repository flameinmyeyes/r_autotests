package functions.gui.lkb.components;

import functions.gui.lkb.ElementData;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$x;

public class XPath extends ElementData {

    public XPath(ElementData data) {
        super(data);
    }

    public String getContainerXPath() {
        if (isContainerDefined()) {
            return "//*[text() = '" + container + "']/ancestor::div[contains(@class, 'container')][1]";
        }
        return "";
    }

    public String getFieldXPath() {
        if (isFieldDefined()) {
            return "//*[contains(text(),'" + field + "')]";
        }
        Assert.fail("Не удалось создать xPath. Поле ввода не было задано\n" +
                "Пожалуйста, ипользуте метод inField(String field), чтобы задать поле ввода");
        return "";
    }

    public String getValueXPath() {
        if (isFieldDefined()) {
            return "//*[text()='" + value + "']";
        }
        return "";
    }

    public String getPlaceholderXPath() {
        if (isPlaceholderDefined()) {
            return "//input[@placeholder='" + placeholder + "']";
        }
        Assert.fail("Не удалось создать xPath. Поле ввода не было задано\n" +
                "Пожалуйста, ипользуте метод inPlaceholder(String field), чтобы задать поле ввода");
        return "";
    }

    /**
     * input/checkbox ext
     */

    public String getSelectCheckboxValueXPath(){
        return getContainerXPath() + getFieldXPath() + "/parent::*/descendant::span[contains(@class,'ant-tag Select_tag')]/child::*[contains(text(),'" + value + "')]";
    }

    public String getSelectValueXPath(){
        return getContainerXPath() + getFieldXPath() + "/parent::*/descendant::span[@class='ant-select-selection-item']";
    }

    public String getInputTextXPath() {
        return getContainerXPath() + getFieldXPath() + "/ancestor::*/descendant::textarea";
    }

    public String getInputValueXPath() {
        if (isFieldDefined()) {

//            return getContainerXPath() + getFieldXPath() + "/parent::*/descendant::input";
            return getContainerXPath() + getFieldXPath() + "//ancestor::*/following::input";
        } else if (isPlaceholderDefined()){
            return getContainerXPath() + getPlaceholderXPath();
        }
        return "";
    }

    public String getUneditableInputXPath() {
        return getContainerXPath() + getFieldXPath() + "//*[(name()='input' and @disabled) " +
                "or (preceding-sibling::*[text()='" + field + "'] and text()!='" + field + "')][not(@style)]";
    }

    public String getSearchedValueXPath(String value) {
        if (isFieldDefined()){
            //Если placeholder уже заполнен значением
            if ($x(getContainerXPath() + getFieldXPath() + "/following::span[@class='ant-select-selection-item']").isDisplayed()){
                $x(getContainerXPath() + getFieldXPath() + "/parent::*/descendant::input").setValue(value);
            } else {
                $x(getContainerXPath() + getFieldXPath() + "/following::div[@class='ant-form-item-control-input-content']").click();
                $x(getContainerXPath() + getFieldXPath() + "/parent::*/descendant::input").setValue(value);
            }
            return getContainerXPath() + getFieldXPath() + "/following::div[contains(@class,'ant-select-item')]/child::span[contains(text(),'" + value + "')]";
        } else if (isPlaceholderDefined()){
            $x("//*[text()='" + placeholder + "']//parent::*/span/input").setValue(value);
            return "//*[text()='" + placeholder + "']//following::div/div[text()='" + value + "']";
        }
        return null;
    }

    public String getErrorXPath() {
        return getContainerXPath() + getFieldXPath() + "//span[contains(@class, 'message') and . !='']";
    }

    public String getCheckboxXPath() {
        if (value.equals("")) {
            return getContainerXPath() + getFieldXPath() + "/parent::label/span";
        } else {
            return getValueXPath() + "//parent::label/span";
        }
    }

    public String getCheckedCheckboxXPath() {
        return getCheckboxXPath() + "[contains(@class,'ant-checkbox')]";
    }

    public String getRadiobuttonXPath() {
        return "//*[text()='" + field + "']/parent::*[contains(@class,'ant-radio-wrapper-checked')]";
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

    /**
     * upload
     */

    public String getUploadXPath(String upload) {
        return getContainerXPath() + "//*[text() = '" + upload + "']/ancestor::*[contains(@class, 'FileInput_labelWrapper')]" +
                "/following-sibling::*[contains(@class, 'FileInput_container')]//input[@type = 'file']";
    }

    public String getUploadedFileNameXPath(String upload, String fileName) {
        return getContainerXPath() + "//*[text() = '" + upload + "']/ancestor::*[contains(@class, 'FileInput_labelWrapper')]" +
                "/following-sibling::*[contains(@class, 'FileInput_container')]//*[contains(@class, 'FileInput_fileName')][text() = '" + fileName + "']";
    }
}

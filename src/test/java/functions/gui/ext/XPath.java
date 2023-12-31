package functions.gui.ext;

import functions.gui.ElementData;
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
            return "//*[text() = '" + field + "']/ancestor::div[contains(@class,'Column_col') " +
                    "or contains(@class, 'Info_definition') " +
                    "or contains(@class, 'CertificateSelector') " +
                    "or contains(@class, 'Input_fullWidth')][1]";
        }
        Assert.fail("Не удалось создать xPath. Поле ввода не было задано\n" +
                "Пожалуйста, ипользуте метод inField(String field), чтобы задать поле ввода");
        return "";
    }


    /**
     * input/checkbox ext
     */


//    public String getInputXPath() {
//        return getContainerXPath() + getFieldXPath() + "//input";
//    }

    public String getInputXPath() {
        if ($x((getContainerXPath() + getFieldXPath() + "//input")).exists()){
            return getContainerXPath() + getFieldXPath() + "//input";
        } else if ($x((getContainerXPath() + getFieldXPath() + "//textarea")).exists()) {
            return getContainerXPath() + getFieldXPath() + "//textarea";
        } else if ($x((getContainerXPath() + getFieldXPath() + "//span[contains(@class,'Typography')]")).exists()){
            return getContainerXPath() + getFieldXPath() + "//span[contains(@class,'Typography')]";
        }
          return null;
    }

    public String getUneditableInputXPath() {
        return getContainerXPath() + getFieldXPath() + "//*[(name()='input' and @disabled) " +
                "or (preceding-sibling::*[text()='" + field + "'] and text()!='" + field + "')][not(@style)]";
    }

    public String getSearchedValueXPath(String value) {
        return getContainerXPath() + getFieldXPath() + "//span[contains(text(), '" + value + "')]";
    }

    public String getErrorXPath() {
        return getContainerXPath() + getFieldXPath() + "//span[contains(@class, 'message') and . !='']";
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

package functions.gui.ext;

import functions.gui.ElementData;
import functions.gui.GUIFunctions;

import java.io.File;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.*;

public class Upload extends ElementData {

    public Upload(ElementData data) {
        super(data);
    }

    public void uploadFile (String upload, String wayToFile) {
//        File file = new File(wayToFile);
//        String fileName = file.getName();
        $x(new XPath(this).getUploadXPath(upload)).sendKeys(wayToFile);
        new GUIFunctions().waitForElementDisplayed("//*[text()='"+upload+"']/ancestor::*[contains(@class,'FileInput')]/following::button[contains(@class,'delete')]");
//        new GUIFunctions().waitForElementDisplayed(new XPath(this).getUploadedFileNameXPath(upload, fileName));
    }
}

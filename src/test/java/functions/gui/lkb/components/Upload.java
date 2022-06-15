package functions.gui.lkb.components;

import functions.gui.lkb.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Upload extends ElementData {

    public Upload(ElementData data) {
        super(data);
    }

    public void uploadFile (String upload, String wayToFile) {
//        File file = new File(wayToFile);
//        String fileName = file.getName();
        $x(new XPath(this).getUploadXPath(upload)).sendKeys(wayToFile);
//        new GUIFunctions().waitForElementDisplayed(new XPath(this).getUploadedFileNameXPath(upload, fileName));
    }
}

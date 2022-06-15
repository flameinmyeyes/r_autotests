package functions.gui.lkb.components;

import com.codeborne.selenide.Condition;
import functions.gui.lkb.ElementData;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

public class Wait extends ElementData {

    public Wait(ElementData data) {
        super(data);
    }

    public Wait() {
        super();
    }

    public void waitForURL(String URL) {
        webdriver().shouldHave(url(URL));
    }

    public void waitForLoading() {
        String spinnerLocator = "//*[contains(@class, 'preloader') or contains(@class,'spinner') or contains(@class,'ant-spin-dot')]";
        if ($x(spinnerLocator).isDisplayed()) {
            waitForElementDisappeared(spinnerLocator);
        }
    }

    public void waitForElementDisplayed(String xPath) {
        $x(new XPath(this).getContainerXPath() + xPath).shouldBe(Condition.visible);
    }

    public void waitForElementDisappeared(String xPath) {
        $x(new XPath(this).getContainerXPath() + xPath).shouldBe(Condition.disappear);
    }

}

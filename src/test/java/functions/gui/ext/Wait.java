package functions.gui.ext;

import com.codeborne.selenide.Condition;
import functions.gui.ElementData;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
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
        String spinnerLocator = "//*[contains(@class, 'preloader') or contains(@class,'spinner') or contains(@class,'Loader_item') or contains(@class,'animate-spin')]";
        if ($x(spinnerLocator).isDisplayed()) {
//          Протез "увеличение тайм-оута"
            waitForElementDisappeared(spinnerLocator);
//            waitForElementDisappeared(spinnerLocator, 180);
        }
    }

    public void waitForElementDisplayed(String xPath) {
        $x(new XPath(this).getContainerXPath() + xPath).shouldBe(Condition.visible);
    }

    public void waitForElementDisplayed(String xPath, int seconds) {
        $x(new XPath(this).getContainerXPath() + xPath).shouldBe(Condition.visible, Duration.ofSeconds(seconds));
    }

    public void waitForElementDisappeared(String xPath) {
        $x(new XPath(this).getContainerXPath() + xPath).shouldBe(Condition.disappear);
    }

    public void waitForElementDisappeared(String xPath, int seconds) {
        $x(new XPath(this).getContainerXPath() + xPath).shouldBe(Condition.disappear, Duration.ofSeconds(seconds));
    }
}

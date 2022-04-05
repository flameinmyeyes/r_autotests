package functions.gui.ext;

import functions.gui.ElementData;

import static com.codeborne.selenide.Selenide.$x;

public class Click extends ElementData {

    public Click(ElementData data) {
        super(data);
    }

    public void clickButton() {
        $x(new XPath(this).getButtonXPath()).click();
    }

    public void clickByLocator(String xPath) {
        $x(new XPath(this).getContainerXPath() + xPath).click();
    }

    public void selectTab(String tabName) {
        $x("//li[@data-name = '" + tabName + "']").click();
    }

    public void openSearchResult(String searchResultName, String buttonName) {
        String searchResultXpath = "//*[contains(text(), '" + searchResultName + "')]" +
                "//following-sibling::div//descendant::*[contains(text(), '" + buttonName + "')]";
        $x(searchResultXpath).click();
        new Wait().waitForLoading();
    }

    public void closeAllPopupWindows() {
        String AcceptCookies = "//*[contains(text(), 'Принять')]";
        if ($x(AcceptCookies).isDisplayed()) {
            $x(AcceptCookies).click();
        }

        String closeOfferToUseSupportService = "//div[@class='info-line__close-btn']";
        if ($x(closeOfferToUseSupportService).isDisplayed()) {
            $x(closeOfferToUseSupportService).click();
        }

        String closeOfferToUseSupportServiceV2 = "//div[@class='BannerBetaVersion_closeIcon__1Ngmg']";
        if ($x(closeOfferToUseSupportServiceV2).isDisplayed()) {
            $x(closeOfferToUseSupportServiceV2).click();
        }

        String AcceptCity = "//div[@class = 'city-select__popup-wrap']//*[text()='Да']";
        if ($x(AcceptCity).isDisplayed()) {
            $x(AcceptCity).click();
        }

        String closeMyExport = "//div[@class='block-survey block-survey--show']" +
                "[@style='visibility: visible;'][//div[contains(text(),'«Мой экспорт»')]]" +
                "//button[contains(@class, 'close')]";
        if ($x(closeMyExport).isDisplayed()) {
            $x(closeMyExport).click();
        }

        String closeServicesInfo = "//div[div[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']]/button";
        if ($x(closeServicesInfo).isDisplayed()) {
            $x(closeServicesInfo).click();
        }
    }

}

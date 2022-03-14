package functional;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

@Deprecated
public class DocPage extends Page {

    public DocPage() {
        super();
    }

    private String docPageWindow =  "//div[contains(@class,'dialog-edit')]";
    private String actionsWindow = "//div[@class='doc-dialog-content z-center']";
    public final int defaultWaitForLoadingTime = 60;

    /**
     * Нажать по веб-элементу span с переданным текстом
     * @param textTab - текст внутри span
     * */
    @Override
    public Page goToTab(String textTab) {
        By tab = By.xpath(docPageWindow + "//span[text()='" + textTab + "']");
        CommonFunctions.waitForElementDisplayed(tab, 60, false);
        $(tab).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Нажать кнопку по title
     * @param title - title кнопки
     * */
    @Override
    public Page clickButtonTitle(String title) {
        CommonFunctions.waitForElementDisplayed(By.xpath(docPageWindow + "//button[@title='" + title + "']"), 60);
        $$(By.xpath(docPageWindow + "//button"))
                .stream()
                .filter(el -> !el.getAttribute("style").contains("display") && el.getAttribute("title").equals(title))
                .findFirst()
                .get()
                .click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Ввести значения в нескольких текстовых полях
     */
    public DocPage inputValuesInFields(Map<String, String> array) {
        for (Map.Entry<String, String> entry : array.entrySet()) {
            inputValueInField(entry.getKey(), entry.getValue());
        }
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Заполнение поля, находящегося в области (например в области Плательщик)
     */
    public DocPage inputValueInArea(String fieldName, String value, String areaName) {
        String area = "//span[contains(text(),'"+ areaName +"')]//ancestor::div[@class='z-vlayout-inner'][1]";
        String field = area + "//span[text()='" + fieldName + "']/ancestor::div[1]//following::input[1] |"+ area +" //span[text()='" + fieldName + "']/ancestor::div[1]//following::textarea[1]";

        String val = FileFunctions.compareValues(value);

        $(By.xpath(field)).click();
        CommonFunctions.wait(0.3);
        SelenideElement element = $(By.xpath(field));
        executeJavaScript("arguments[0].value='"+ val +"'", element);
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Заполнение нескольких полей, находящихся в области (например в области Плательщик)
     */
    public DocPage inputValuesInArea(Map<String, String> array, String areaName) {
        for (Map.Entry<String, String> entry : array.entrySet()) {
            inputValueInArea(entry.getKey(), entry.getValue(), areaName);
        }
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Нажать на справочник рядом с текстовым полем
     */
    public DocPage clickDictionaryNearField(String fieldName) {
        By field = By.xpath("//span[text()='" + fieldName + "']/ancestor::div[1]//following::button[1]");
        $(field).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Нажать на справочник, находящийся в области (например в области Плательщик)
     */
    public DocPage clickDictionaryNearFieldInArea(String fieldName, String areaName) {
        String area = "//span[text() = '" + areaName + "']/ancestor::div[1]";
        By field = By.xpath(area +"/following::span[contains(text(), '" + fieldName + "')][1]/ancestor::div[1]//following::button[not(contains(@style, 'display'))][1]");
        $(field).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Нажать кнопку, находящуюся в области (например в области Плательщик), по title
     */
    public DocPage clickButtonTitleInArea(String title, String areaName) {
        String area = "//span[text()='"+ areaName +"']//ancestor::div[@class='z-vlayout-inner'][1]";
        By button = By.xpath(area +"//button[@title='"+ title +"']");
        CommonFunctions.waitForElementDisplayed( button,60);
        $(button).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    public DocPage clickButtonTitleInArea(String title, String fieldName, String areaName) {
        String area = "//span[text()='" + areaName + "']//ancestor::div[@class='z-vlayout-inner'][1]";
        String field = "//following::span[contains(text(), '" + fieldName + "')][1]//ancestor::div[@class='z-hlayout-inner'][1]";
        By button = By.xpath(area + field + "//button[@title='"+ title +"']");
        CommonFunctions.waitForElementDisplayed( button,60);
        $(button).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Кликнуть по строке таблицы, находящейся в области (например в области Плательщик), по title
     */
    public DocPage clickRowInArea(String rowTitle, String areaName) {
        String area = "//span[text()='"+ areaName +"']//ancestor::div[@class='z-vlayout-inner'][1]";
        By row = By.xpath(area +"//td[@title='" + rowTitle +"']/preceding-sibling::td//span");
        CommonFunctions.waitForElementDisplayed(row,60);
        $(row).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Применить фильтр и отдельная функция выбрать
     */
    public DocPage filterColumnInDictionary(String columnTitle, String value) {
        executeJavaScript("arguments[0].scrollIntoView();", $(By.xpath(actionsWindow + "//img[@class='filterToggler z-image']"))); //пролистать
        filterOn(actionsWindow);
        String val = FileFunctions.compareValues(value);

        //рассчитываем число столбцов
        ElementsCollection elements = $$(By.xpath(actionsWindow + "//th[contains(@class, 'z-listheader z-listheader-sort')][not(contains(@style, 'hidden'))]"));
//        System.out.println("Число столбцов: " + elements.size());

        int num = -1;

        for (SelenideElement element : elements){
            num++;
            if (element.getAttribute("title").contains(columnTitle)) {
                num +=2;
                break;
            }
        }
        if (num == -1){
            throw new RuntimeException("Элемент с наименованием " + columnTitle + " не найден!");
        }

        if ($(By.xpath(actionsWindow + "//th[@class='attachColumnHeader z-listheader']")).exists()) {
            num ++;
        }

        By fieldLocator = By.xpath(actionsWindow + "//th[contains(@class,'z-auxheader')][not(contains(@style, 'hidden'))][" + num + "]//input");
        SelenideElement field = $(fieldLocator);
        new DocPage().scrollListForm(fieldLocator);

        //тест

        //3. Фильтруем, в зависимости от типа поля фильтрации
        if (field.getAttribute("class").contains("combobox")) {
            //для полей с типом "комбобокс"
            if (field.$x(".//parent::span").getAttribute("class").contains("readonly")) {
                //комбобокс readonly
                field.click();
                clickWebElement("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//div[contains(text(), '" + val + "')]", defaultWaitForLoadingTime);
                CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//button[text()='ОК'][not(contains(@disabled, 'disabled'))]"), defaultWaitForLoadingTime);
                $(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//button[text()='ОК']")).click();
            } else {
                //комбобокс не readonly
                field.$x(".//following-sibling::a/i").click();
                CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-combobox-popup  z-combobox-open z-combobox-shadow']"), defaultWaitForLoadingTime);
                $(By.xpath("//div[@class='z-combobox-popup  z-combobox-open z-combobox-shadow']//li/span[text()='" + val + "']")).click();
                actions().sendKeys(Keys.RETURN).perform();
                waitForLoading(defaultWaitForLoadingTime);
            }

        } else {
            //для полей других типов
            $(field).clear();
            $(field).sendKeys(val);
            $(field).pressEnter();
            CommonFunctions.wait(1);
        }


//        field.clear();
//        field.sendKeys(val);
//        field.pressEnter();

        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    @Deprecated
    public DocPage filterColumnInDictionary_new(String columnTitle, String value) {
        filterOn(actionsWindow);
        scrollListForm(By.xpath(actionsWindow + "//th[contains(@class,'z-listheader-sort')][@title='" + columnTitle + "']"));
        String val = FileFunctions.compareValues(value);

        SelenideElement field = null;
        int num;

        //1. Рассчитываем номер столбца
        ElementsCollection elements = $$x(actionsWindow + "//th[contains(@class,'z-listheader-sort')][not(contains(@style, 'hidden'))]");
        num = elements.indexOf($x("//*[@title='" + columnTitle + "']")) + 1; //так надо
        //если есть поле с вложением, прибавляем +1
        if ($(By.xpath(actionsWindow + "//th[@class='attachColumnHeader z-listheader']")).exists()) {
            num++;
        }
        //если есть поле с воронкой, прибавляем +1
        if ($(By.xpath(actionsWindow + "//th[contains(@class,'z-auxheader')][not(contains(@style, 'hidden'))]//span[@filter-for='nullColumn']")).exists()) {
            num++;
        }

        //2. Получаем нужное поле фильтрации по полученному номеру
        field = $x(actionsWindow + "//th[contains(@class,'z-auxheader')][not(contains(@style, 'hidden'))][" + num + "]").$x(".//input");

        //3. Фильтруем, в зависимости от типа поля фильтрации
        if (field.getAttribute("class").contains("combobox")) {
            //для полей с типом "комбобокс"
            if (field.$x(".//parent::span").getAttribute("class").contains("readonly")) {
                //комбобокс readonly
                field.click();
                clickWebElement("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//div[contains(text(), '" + val + "')]", defaultWaitForLoadingTime);
                CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//button[text()='ОК'][not(contains(@disabled, 'disabled'))]"), defaultWaitForLoadingTime);
                $(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//button[text()='ОК']")).click();
            } else {
                //комбобокс не readonly
                field.$x(".//following-sibling::a/i").click();
                CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-combobox-popup  z-combobox-open z-combobox-shadow']"), defaultWaitForLoadingTime);
                $(By.xpath("//div[@class='z-combobox-popup  z-combobox-open z-combobox-shadow']//li/span[text()='" + val + "']")).click();
                actions().sendKeys(Keys.RETURN).perform();
                waitForLoading(defaultWaitForLoadingTime);
            }

        } else {
            //для полей других типов
            $(field).clear();
            $(field).sendKeys(val);
            $(field).pressEnter();
            CommonFunctions.wait(1);
        }

        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Выбрать строку в справочнике
     */
    //нажатие "Ок" вшито
    public DocPage clickRowInDictionary(String value) {
        String val = FileFunctions.compareValues(value);

        CommonFunctions.wait(1);
        $(By.xpath(actionsWindow +"//td[@title='"+ val +"']/preceding-sibling::td//span")).click();
        if (!$(By.xpath("//button[@class='acceptButton z-button']")).isEnabled()){
            CommonFunctions.waitForElementDisplayed(
                    By.xpath("//button[@class='acceptButton z-button'][not (contains (@disabled, 'disabled'))]"), 120);
        }
        $(By.xpath("//button[@class='acceptButton z-button']")).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Прочитать значение поля и сохранить в файл
     */
    @Deprecated
    public DocPage saveValueField(String fieldName, String way) {
        By field = By.xpath("//span[text()='" + fieldName + "']/ancestor::div[1]//following::input[1] | //span[text()='" + fieldName + "']/ancestor::div[1]//following::textarea[1]");
        String value = $(field).getAttribute("value");
        FileFunctions.writeValueFile(new File(way).getAbsolutePath(), value);
        return this;
    }

    /**
     * Загрузить файл (взаимодействие идет после появления окна с загрузкой)
     */
    public DocPage uploadFile(String way) {
        By downloadButton = By.xpath("//input[@type='file']");
//        CommonFunctions.waitForElement( downloadButton, 5);
        $(downloadButton).shouldBe(Condition.exist, Duration.ofSeconds(5));
        try {
            $(downloadButton).sendKeys(new File(way).getCanonicalPath());
            CommonFunctions.wait(0.5);
        } catch (IOException e) {
            throw new RuntimeException("File not found " + e);
        }
        CommonFunctions.waitForElementDisplayed( By.xpath("//div[@class='tableWrapper']/table/tbody/tr/td/span"), 10);
//        $(By.xpath("//div[@class='tableWrapper']/table/tbody/tr/td/span")).waitUntil(Condition.exist,10);
        $(By.xpath("//button[@class='acceptButton z-button']")).click();
        waitForLoading(100);
        return this;
    }

    /**
     * Проверить документ на правильность заполнения
     */
    @Deprecated
    public DocPage checkDocument() {
        String window = "//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']";
        if($$(By.xpath(window+"//img[contains(@src,'error.png')]")).size()<1){
            $(By.xpath(window+"//button[@title='Сохранить']")).click();
            waitForLoading(12);
        }
        return this;
    }

    @Deprecated
    public DocPage checkDocument(String control, String typeControl) {
        String window = "//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']";
        final char dm = (char) 34;
        String tr = "//span[contains(text()," + dm + control + dm + ")]//ancestor::tr[1]//img[contains(@src,'" + typeControl + ".png')]";
        if($$(By.xpath(window + tr)).isEmpty()) throw new RuntimeException("Контроль: " + control + " c типом " + typeControl + " не найден!");
        return this;
    }

    /**
     * Проверка открытия ВФ документа
     */
    @Deprecated
    public DocPage checkDocumentSpan(String textSpan) {
        String window = "//div[@class='doc-edit-frame z-vlayout']//span[contains(text(), '" + textSpan + "')]";
        $(By.xpath(window)).isEnabled();
        System.out.println("Заголовок " + textSpan + " найден.");
        return this;
    }

    /**
     * Ввести значение в текстовое поле
     * @param fieldName - text поля
     * @param value - значение
     */

    @Override
    public Page inputValueInField_old2(String fieldName, String value) {
        By[] possibleLocatorsList = {
                By.xpath(docPageWindow + "//span[text()='" + fieldName + "']/ancestor::div[2]//following-sibling::div[1]/input"),
                By.xpath(docPageWindow + "//span[text()='" + fieldName + "']/ancestor::div//following-sibling::div[1]/textarea"),
                By.xpath(docPageWindow + "//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//input"),
                By.xpath(docPageWindow + "//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//textarea"),
                By.xpath(docPageWindow + "//span[text()='" + fieldName + "']/ancestor::div[1]//following::input[1]"),
                By.xpath(docPageWindow + "//span[text()='" + fieldName + "']/ancestor::div[1]//following::textarea[1]")
        };

        By locator = null;
        for (By possibleLocator : possibleLocatorsList) {
            if ($(possibleLocator).isDisplayed()) {
                locator = possibleLocator;
            }
        }

//        CommonFunctions.waitForElementDisplayed(locator, 60, false);
        SelenideElement input = $(locator);

        //определяем тип поля
        String fieldClass = $(input).getAttribute("class");

        if (fieldClass.contains("combobox")) {
            //для поля с типом "комбобокс"
            ElementsCollection elementList = $$x(docPageWindow +
                    "//span[text()='" + fieldName + "']/ancestor::div[2]/following-sibling::div[1]//input/following-sibling::a[1] | " +
                    "//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//input/following-sibling::a[1]");
            SelenideElement element = elementList.get(0);
            element.click();
            waitForLoading(defaultWaitForLoadingTime);
            CommonFunctions.wait(2);
            //пункт комбобокса
            By comboboxItem = By.xpath(docPageWindow + "//li/span[contains(text(), '" + value + "')] | " +
                    "//li[contains(@title, '" + value + "')]/span[text()]");
            CommonFunctions.waitForElementDisplayed(comboboxItem, 60, false);
            $(comboboxItem).click();
        }

        else {
            //для полей других типов
            $(locator).click();
            waitForLoading(defaultWaitForLoadingTime);
            CommonFunctions.wait(2);
            SelenideElement element = $(locator);
            executeJavaScript("arguments[0].value='" + value + "'", element);
        }

        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    @Override
    public Page inputValueInField(String fieldName, String value) {
        final String VIEW_FORM_XPATH = ".//div[@class = 'doc-edit-frame z-vlayout']";
        String totalXPath;
        String fieldBlockXPath = "//*[text() = '" + fieldName + "'][not(@class = 'z-label')]" +
                "[count(ancestor::*[contains(@style, 'none')]) = 0]";
        String inputXPath = "/following::*[1]//*[not(descendant-or-self::*[@head_ref])][contains(@class, 'z-datebox-input') " +
                "or contains(@class, 'z-textbox') " +
                "or contains(@class, 'z-combobox-input') " +
                "or contains(@class, 'z-advtextarea') " +
                "or contains(@class, 'z-maskinput') " +
                "or contains(@class, 'z-advdecimalfield')]";

        totalXPath = VIEW_FORM_XPATH + fieldBlockXPath + inputXPath;

        executeJavaScript("arguments[0].scrollIntoView();", $x(totalXPath));

        String fieldClass = $x(totalXPath).getAttribute("class");
        if (fieldClass.contains("combobox")) {
            String choseButton = totalXPath + "/following-sibling::a";
            $x(choseButton).click();
            By comboboxItem = By.xpath("//li/span[contains(text(), '" + value + "')] | //li[contains(@title, '" + value + "')]/span[text()]");
            $(comboboxItem).click();
        } else {
            $x(totalXPath).click();
            executeJavaScript("arguments[0].value='" + value + "'", $x(totalXPath));
        }
        new MainPage().waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

}

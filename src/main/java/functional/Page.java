package functional;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import static com.codeborne.selenide.Selenide.*;


public abstract class Page {

    public Page() {
    }

    protected String windowApprovalList = "//div[@class='z-window z-window-modal z-window-shadow']";
    protected String windowSignature = "//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']";
    protected String windowActions = "//div[@class='doc-dialog-content z-center']";
    protected int defaultWaitForLoadingTime = 60;
    
    /**
     * Нажать по веб-элементу span с переданным текстом
     * @param textTab - текст внутри span
     * */
    public Page goToTab(String textTab) {
        By tab = By.xpath("//span[text()='" + textTab + "']");
        CommonFunctions.waitForElementDisplayed(tab, 60);
        $(tab).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Ожидание отображения элемента и клик по нему
     * @param locator - локатор элемента
     */
    public Page clickWebElement(String locator) {
        $x(locator).shouldBe(Condition.exist, Duration.ofSeconds(defaultWaitForLoadingTime));
        executeJavaScript("arguments[0].scrollIntoView();", $x(locator));
        $x(locator).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * @param seconds - время ожидания
     */
    public Page clickWebElement(String locator, int seconds) {
        $x(locator).shouldBe(Condition.exist, Duration.ofSeconds(seconds));
        executeJavaScript("arguments[0].scrollIntoView();", $x(locator));
        $x(locator).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Ожидание отображения элемента и клик ПКМ по нему
     * @param locator - локатор элемента
     */
    public Page contextClickWebElement(String locator) {
        $x(locator).shouldBe(Condition.exist, Duration.ofSeconds(defaultWaitForLoadingTime));
        executeJavaScript("arguments[0].scrollIntoView();", $x(locator));
        $x(locator).contextClick();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * @param seconds - время ожидания
     */
    public Page contextClickWebElement(String locator, int seconds) {
        $x(locator).shouldBe(Condition.exist, Duration.ofSeconds(seconds));
        executeJavaScript("arguments[0].scrollIntoView();", $x(locator));
        $x(locator).contextClick();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Нажать кнопку по title
     * @param title - title кнопки
     */
    public Page clickButtonTitle(String title) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@title='" + title + "'][not(@disabled)]"), 60);
        $$(By.cssSelector("button"))
                .stream()
                .filter(el -> !el.getAttribute("style").contains("display") && el.getAttribute("title").equals(title))
                .findFirst()
                .get()
                .click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Нажать кнопку по тексту
     * @param text - текст внутри кнопки
     */
    public Page clickButtonText(String text) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//button[text()='" + text + "'][not(@disabled)]"), 60);
        $$(By.cssSelector("button"))
                .stream()
                .filter(el -> !el.getAttribute("style").contains("display") && el.getText().trim().equals(text))
                .findFirst()
                .get()
                .click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Нажать кнопку по class
     * @param buttonClass - class кнопки
     * */
    public Page clickButtonClass(String buttonClass) {
        By button = By.xpath("//button[contains(@class, '" + buttonClass + "')][not(@disabled)]");
        CommonFunctions.waitForElementDisplayed(button, 60);
        $(button).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Пролистать панель управления, пока не появится нужна кнопка, и нажать на неё по title
     * @param title - title кнопки
     */
    @Deprecated
    public Page scrollAndClickButtonTitle(String title) {
        SelenideElement button = $(By.xpath("//button[@title='" + title + "'][not(@disabled)]"));
        //пролистываем влево до упора
        for (int i = 1; i < 100; i++) {
            $(By.xpath("//div[@class='actionbar-left-scroll']")).click();
            CommonFunctions.wait(0.01);
        }
        //пролистываем вправо, пока не появится кнопка
        while (!button.isDisplayed()) {
                $(By.xpath("//div[@class='actionbar-right-scroll']")).click();
                CommonFunctions.wait(0.01);
        }
        button.click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Пролистать списковую форму, пока не появится нужный элемент
     * @param locator - локатор элемента
     */
    public Page scrollListForm(By locator) {
        executeJavaScript("arguments[0].scrollIntoView();", $(locator));
        return this;
    }

    /**
     * Нажать пункт меню из выпадающего списка кнопки
     * @param buttonTitle - тайтл основной кнопки
     * @param buttonMenuText - текст меню из выпадающего списка кнопки
     */
    public Page clickButtonMenu(String buttonTitle, String buttonMenuText) {
        By parentButton = By.xpath("//button[@title='" + buttonTitle + "'][not(@disabled)]/parent::td/following-sibling::td/button[@class='toolbarButtonArrow z-button']");
        CommonFunctions.waitForElementDisplayed(parentButton, 60);
        $(parentButton).click();
        CommonFunctions.wait(0.5);
        goToTab(buttonMenuText);
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Ожидание окончания Обработки запроса
     * @param maxWaitSeconds - максимальное время ожидания
     * */
    public Page waitForLoading(int maxWaitSeconds) {
        CommonFunctions.wait(1);
        if ($(By.xpath("//div[@class='z-loading-indicator']")).isDisplayed()) {
            System.out.println("Ожидание обработки запроса...");
            $(By.xpath("//div[@class='z-loading-indicator']")).shouldBe(Condition.not(Condition.visible), Duration.ofSeconds(maxWaitSeconds));
        }
//        CommonFunctions.wait(3);
//        int time = 0;
//            while ($(By.xpath("//div[@class='z-loading-indicator']")).exists() && time<maxWaitSeconds) {
//                CommonFunctions.wait(1);
////                if (time<1) System.out.println("Found loading");
//                if (time<1) System.out.println("Ожидание обработки запроса...");
//                time+=1;
//            }
//        CommonFunctions.wait(2);
        return this;
    }

    /**
     * Ожидание обработки (исчезновения?) окна с текстом
     * @param maxWaitSeconds - максимальное время ожидания
     * @param nameBlock - текст в ожидаемом окне
     * */
    @Deprecated
    public Page waitForLoading(int maxWaitSeconds, String nameBlock) {
        CommonFunctions.wait(3);
        int time = 0;
            while ($(By.xpath("//*[text() = '" + nameBlock + "']")).exists() && time<maxWaitSeconds) {
                if (time == 0) System.out.println("Found window: " + nameBlock);
                CommonFunctions.wait(1);
                time+=1;
            }
        CommonFunctions.wait(2);
        return this;
    }

    /**
     * Проверить открыт ли фильтр, если нет то открыть
     * @param window - окно
     * */
    public Page filterOn(String window){
        CommonFunctions.wait(2);
        SelenideElement filter = $(By.xpath(window + "//img[@class='filterToggler z-image']"));
        if (filter.getAttribute("src").contains("filter_on")) {
            filter.click();
            CommonFunctions.wait(3);
        }
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Ввести значение в текстовое поле
     * @param fieldName - text поля
     * @param value - значение
     */
    public Page inputValueInField_old(String fieldName, String value) {
        By field = By.xpath("//span[text()='" + fieldName + "']/ancestor::div[1]//following::input[1] | " +
                "//span[text()='" + fieldName + "']/ancestor::div[1]//following::textarea[1]");
        CommonFunctions.waitForElementDisplayed(field, 60, false);
        String val = FileFunctions.compareValues(value);
        SelenideElement input = $(field);

        if ($(input).getAttribute("class").contains("combobox")) {
            //для поля с типом "комбобокс"
            ElementsCollection elementList = $$(By.xpath("//span[text()='" + fieldName + "']/ancestor::div[2]/following-sibling::div[1]//input/following-sibling::a[1]"));
            SelenideElement element = elementList.get(0);
            element.click();
            waitForLoading(defaultWaitForLoadingTime);
            CommonFunctions.wait(2);
            //пункт комбобокса
            By comboboxItem = By.xpath("//li/span[contains(text(), '" + value + "')] | //li[contains(@title, '" + value + "')]/span[text()]");
            CommonFunctions.waitForElementDisplayed(comboboxItem, 60, false);
            $(comboboxItem).click();
        } else {
            //для остальных типов полей (input, textarea)
            $(field).click();
            waitForLoading(defaultWaitForLoadingTime);
            CommonFunctions.wait(2);
            SelenideElement element = $(field);
            executeJavaScript("arguments[0].value='" + val + "'", element);
        }
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Ввести значение в текстовое поле
     * @param fieldName - text поля
     * @param value - значение
     */
    public Page inputValueInField_old2(String fieldName, String value) {
        By[] possibleLocatorsList = {
                By.xpath("//span[text()='" + fieldName + "']/ancestor::div[2]//following-sibling::div[1]/input"),
                By.xpath("//span[text()='" + fieldName + "']/ancestor::div//following-sibling::div[1]/textarea"),
                By.xpath("//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//input"),
                By.xpath("//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//textarea"),
                By.xpath("//span[text()='" + fieldName + "']/ancestor::div[1]//following::input[1]"),
                By.xpath("//span[text()='" + fieldName + "']/ancestor::div[1]//following::textarea[1]")
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
            ElementsCollection elementList = $$x("//span[text()='" + fieldName + "']/ancestor::div[2]/following-sibling::div[1]//input/following-sibling::a[1] |" +
                    " //span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//input/following-sibling::a[1]");
            SelenideElement element = elementList.get(0);
            element.click();
            waitForLoading(defaultWaitForLoadingTime);
            CommonFunctions.wait(2);
            //пункт комбобокса
            By comboboxItem = By.xpath("//li/span[contains(text(), '" + value + "')] | //li[contains(@title, '" + value + "')]/span[text()]");
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
    public Page inputValueInField(String fieldName, String value) {
        String totalXPath;
        String fieldBlockXPath = "//*[text() = '" + fieldName + "'][not(@class = 'z-label')]" +
                "[count(ancestor::*[contains(@style, 'none')]) = 0]";
        String inputXPath = "/following::*[1]//*[not(descendant-or-self::*[@head_ref])][contains(@class, 'z-datebox-input') " +
                "or contains(@class, 'z-textbox') " +
                "or contains(@class, 'z-combobox-input') " +
                "or contains(@class, 'z-advtextarea') " +
                "or contains(@class, 'z-maskinput') " +
                "or contains(@class, 'z-advdecimalfield')]";

        totalXPath = fieldBlockXPath + inputXPath;

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
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Кликнуть по чекбоксу рядом с полем (есть дубль метода в DocPage, доработать!)
     * @param fieldName - text поля рядом с чекбоксом
     */
    public Page clickCheckbox(String fieldName) {
        By checkbox = By.xpath("//label[text()='" + fieldName + "']/preceding-sibling::div[contains(@class, 'checkbox')][1] | //span[text()='" + fieldName + "']//ancestor::div[3]//div[contains(@class, 'checkbox')][1]");
        CommonFunctions.waitForElementDisplayed(checkbox, 60);
        $(checkbox).click();
        CommonFunctions.wait(1);
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Выбрать документ из списка (без фильтра)
     */
    @Deprecated
    public Page selectListDoc(String textDoc) {
        $(By.xpath(windowActions+"//td[@title='"+ textDoc +"']/preceding-sibling::td//span")).click();
        if (!$(By.xpath("//button[@class='acceptButton z-button']")).isEnabled()){
            CommonFunctions.wait(2);
        }
        $(By.xpath("//button[@class='acceptButton z-button']")).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }



    /**
     * Формирование листа согласования (для двоих лиц и для трех, перегружен метод)
     */

    /**
     * Выбор утверждающего
     * @param columnName Название колонки ФИО в таблице утверждающих
     * @param fio        Фамилия утверждающего
     */
    public Page selectApprover(String columnName, String fio) {
        if (!$(By.xpath("//div[contains(@class,'z-column-content')][text()='" + columnName + "']/ancestor::div[@class='z-grid']//input[contains(@value,'" + fio + "')]")).exists()) {
            $x("//div[contains(@class,'z-column-content')][text()='" + columnName + "']/ancestor::div[@class='z-grid']//th" +
                    "//img[contains(@src,'book_open2')]/..").click();
            new MainPage().waitForLoading(20);
            // Выбрать и отметить согласующего исполнителя.
            //Проставлен флажок на записи.
            $x("//div[contains(@class,\"z-window-modal\")]" +
                    "//tr[contains(@class,\"z-listitem\")]/td/div[contains(.,'" + fio + "')]").click();
            // Нажать кнопку «Готово».
            //Заполнен раздел согласующие значением ФИО на диалоговом окно "Сформировать/редактировать лист согласования».
            new MainPage().clickButtonText("Готово");
            CommonFunctions.waitForElementDisplayed(By.xpath("//div[contains(@class,'z-column-content')][text()='" + columnName + "']/ancestor::div[@class='z-grid']//input[contains(@value,'" + fio + "')]"), 30);
        }
        return this;
    }


    @Deprecated
    //требуется доработка
    public Page createApprovalList(String approval, String affirmation){
        try {
            while ($(By.xpath(windowApprovalList + "//button[text()='X']")).isDisplayed()){
                $(By.xpath(windowApprovalList + "//button[text()='X']")).click();
                System.out.println("Found X");
                CommonFunctions.wait(0.1);
            }
        } catch (Exception ex){
            System.out.println("Not found X");
        }
        if (approval.equals("Отсутвует") || approval.equals("Missing")){
            $(By.xpath("//label[text()='Пропустить этап согласования']//preceding-sibling::div")).click();
        } else {
            $(By.xpath(windowApprovalList+"//div[text()='ФИО']/ancestor::div[2]//button")).click();
            CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(),'"+approval+"')]"), 5);
            $(By.xpath("//*[contains(text(),'"+approval+"')]/span")).click();
            $(By.xpath("//button[text()='Готово']")).click();
            CommonFunctions.wait(1);
        }

        $(By.xpath(windowApprovalList+"//div[text()=' ФИО руководитель']/ancestor::div[2]//button")).click();
        CommonFunctions.wait(2);
        $(By.xpath("//*[contains(text(),'"+affirmation+"')]/span")).click();
        $(By.xpath("//button[text()='Готово']")).click();
        CommonFunctions.wait(1);
        $(By.xpath(windowApprovalList+"//button[text()='Ok']")).click();
        try {
            if ($(By.xpath("//div[text()='На согласование']")).isDisplayed()) $(By.xpath("//button[@class='acceptButton z-button']")).click();
        } catch (Exception ex){
            System.out.println("Not found window accept");
        }
        return this;
    }

    public Page createApprovalList(String approval, String affirmationOne, String affirmationTwo){
        try {
            while ($(By.xpath(windowApprovalList + "//button[text()='X']")).isDisplayed()) {
                $(By.xpath(windowApprovalList + "//button[text()='X']")).click();
                System.out.println("Found X");
                CommonFunctions.wait(0.1);
            }
        } catch (Exception ex) {
            System.out.println("Not found X");
        }
        if (approval.equals("Отсутствует") || approval.equals("Missing")) {
            $(By.xpath("//label[text()='Пропустить этап согласования']//preceding-sibling::div")).click();
        } else {
            $(By.xpath(windowApprovalList+"//div[text()='ФИО']/ancestor::div[2]//button")).click();
            CommonFunctions.waitForElementDisplayed(By.xpath("//*[contains(text(),'"+approval+"')]"), 5);
            $(By.xpath("//*[contains(text(),'"+approval+"')]/span")).click();
            $(By.xpath("//button[text()='Готово']")).click();
            CommonFunctions.wait(1);
        }

        $(By.xpath(windowApprovalList+"//div[text()=' ФИО главный бухгалтер']/ancestor::div[2]//button")).click();
        CommonFunctions.wait(2);
        $(By.xpath("//*[contains(text(),'"+affirmationOne+"')]/span")).click();
        $(By.xpath("//button[text()='Готово']")).click();
        CommonFunctions.wait(1);

        $(By.xpath(windowApprovalList+"//div[text()=' ФИО руководитель']/ancestor::div[2]//button")).click();
        CommonFunctions.wait(2);
        $(By.xpath("//*[contains(text(),'"+affirmationTwo+"')]/span")).click();
        $(By.xpath("//button[text()='Готово']")).click();
        CommonFunctions.wait(1);
        $(By.xpath(windowApprovalList+"//button[text()='Ok']")).click();
        if ($(By.xpath("//div[text()='На согласование']")).exists()) $(By.xpath("//button[@class='acceptButton z-button']")).click();

        return this;
    }

    /**
     * Очистить лист согласования
     */
    public Page clearApprovalList() {
            while($(By.xpath(windowApprovalList + "//button[text()='X']")).exists()) {
                $(By.xpath(windowApprovalList + "//button[text()='X']")).click();
                CommonFunctions.wait(0.3);
                System.out.println("Found X");
            }
        return this;
    }

    //требуется доработка
    public Page setApproval(String type, String parameter, String value) {
//        int numTable = -1;
//        if (type.equals("Согласующие")) numTable = 0;
//        if (type.equals("Утверждающие")) numTable = 2;
//
//        List<SelenideElement> tables = $$(By.xpath(windowApprovalList + "//table"));
//
//        tables.get(numTable).$(By.xpath(".//*[text()='" + parameter + "']/ancestor::div[2]//button")).click();

        $(By.xpath(windowApprovalList+ "//*[text()='" + parameter + "']/ancestor::div[2]//button")).click();
        CommonFunctions.wait(2);
        $(By.xpath("//*[contains(text(),'" + value + "')]/span")).click();
        $(By.xpath("//button[text()='Готово']")).click();
        CommonFunctions.wait(1);
        return this;
    }

    /**
     * Нажать кнопку "Ок" в листе согласования
     */
    public Page okApproval() {
        $(By.xpath(windowApprovalList+"//button[text()='Ok']")).click();
        if ($(By.xpath("//div[text()='На согласование']")).exists()) {
            System.out.println("Окно на согласование");
            $(By.xpath("//button[@class='acceptButton z-button']")).click();
        }
        if ($(By.xpath("//div[text()='На согласование ФК']")).exists()) {
            System.out.println("Окно на согласование ФК");
            $(By.xpath("//button[@class='acceptButton z-button']")).click();
        }
        return this;
    }

    /**
     * Обработать всплывающее окно после операции
     */
    public Page clickButtonInBlock(String nameBlock, String nameButton) {
        String element = "//*[text() = '" + nameBlock + "']/ancestor::div[1]//following::button" +
                "[contains(normalize-space(), '" + nameButton + "') or contains(@title, '" + nameButton + "')]";
        CommonFunctions.waitForElementDisplayed(By.xpath(element),60, false);
        $(By.xpath(element)).waitUntil(Condition.exist,120*1000);
        $(By.xpath(element)).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Печать документа (робот не работает!)
     */
    @Deprecated
    public void printDocumentRobot(String typePrint, String wayToFile) {
        //проверяем, есть ли старая версия файла с таким именем, если есть - удаляем
        File file = new File(wayToFile);
        try {
            wayToFile = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(file.delete()){
            System.out.println("файл удален");
        } else {
            System.out.println("Файла не обнаружено");
        }

        CommonFunctions.wait(3);

        if (!typePrint.isEmpty()) {
            String window = "//div[@class='z-window-content']";
            $(By.xpath(window+"//span[text()='"+ typePrint +"']")).click();
            $(By.xpath(window +"//button[@title='OK']")).click();}
        CommonFunctions.wait(10);

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Робот не создан " + e);
        }
        //заносим путь к файлу в буфер обмена
        StringSelection stringSelection = new StringSelection(wayToFile);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        //вставляем путь к файлу из буфера
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        CommonFunctions.wait(3);
        //жмём Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        CommonFunctions.wait(3);
    }

    /**
     * Экспорт документа (робот не работает!)
     */
    @Deprecated
    public void exportDocumentRobot(String wayToFile) {
        //проверяем, есть ли старая версия файла с таким именем, если есть - удаляем
        File file = new File(wayToFile);
        try {
            wayToFile = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            file.delete();
            System.out.println("Старая версия файла удалена");
        } catch (Exception e){
            System.out.println("Старая версия файла не найдена");
        }
        CommonFunctions.wait(5);

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Робот не создан " + e);
        }
        //заносим путь к файлу в буфер обмена
        StringSelection stringSelection = new StringSelection(wayToFile);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        //вставляем путь к файлу из буфера
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        CommonFunctions.wait(3);
        //жмём Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        CommonFunctions.wait(3);
    }

    /**
     * Подпись
     */
    @Deprecated
    public Page signClient() {
        new MainPage().signCryptoPro("Ермухамбетова");
        return this;
    }

    /**
     * Подпись JinnClient
     */
    public Page signJinnClient() {
        long start = System.nanoTime();
        try {
            Process p = Runtime.getRuntime().exec(".\\AutoITScripts\\JinnSign.exe");
            System.out.println("Запустился скрипт подписи Jinn-Client");
            p.waitFor();
        } catch (Exception e){
            throw new RuntimeException("Ошибка запуска скрипта подписи Jinn-Client!" + e);
        }
        long end = System.nanoTime();
        double timeTransaction  = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS)/1000.0;
        if (timeTransaction >= 120) {
            throw new RuntimeException("Ошибка выполнения скрипта подписи!");
        } else {
            System.out.println("Скрипт успешно завершился за " + timeTransaction + " c.");
        }
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Подпись CryptoPro (без параметров по умолчанию сертификат "Ермухамбетова"
     */
    public Page signCryptoPro() {
//        CommonFunctions.wait(20);
        clickWebElement(windowSignature + "//span[@class='z-combobox z-combobox-readonly']", 60);
//        CommonFunctions.wait(1);
        clickWebElement("//div//span[contains(text(), 'Ермухамбетова')]", 60);

        if (!$(By.xpath(windowSignature + "//button[text()=' Подписать']")).isEnabled()) {
            CommonFunctions.wait(3);
        }
        clickWebElement(windowSignature + "//button[text()=' Подписать']", 60);

        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * @param certificate - часть имени сертификата
     */
    public Page signCryptoPro(String certificate) {
//        CommonFunctions.wait(20);
        clickWebElement(windowSignature + "//span[@class='z-combobox z-combobox-readonly']", 60);

//        CommonFunctions.wait(1);
        clickWebElement("//div//span[contains(text(), '" + certificate + "')]", 60);

        if (!$(By.xpath(windowSignature + "//button[text()=' Подписать']")).isEnabled()) {
            CommonFunctions.wait(3);
        }
        clickWebElement(windowSignature + "//button[text()=' Подписать']", 60);

        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    @Deprecated
    public Page signJinnClient(boolean docView) {
        long start = System.nanoTime();
        try {
            if (docView) {
                Process d = Runtime.getRuntime().exec(".\\AutoITScripts\\JinnSignView.exe");
                System.out.println("Запустился скрипт ожидания окна Просмотр документа перед формированием подписи");
                d.waitFor();
            }
            Process p = Runtime.getRuntime().exec(".\\AutoITScripts\\JinnSign.exe");
            System.out.println("Запустился скрипт подписи Jinn-Client");
            p.waitFor();
        } catch (Exception e){
            throw new RuntimeException("Ошибка запуска скрипта подписи Jinn-Client!" + e);
        }
        long end = System.nanoTime();
        double timeTransaction  = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS)/1000.0;
        if (timeTransaction >= 120) {
            throw new RuntimeException("Ошибка выполнения скрипта подписи!");
        } else {
            System.out.println("Скрипт успешно завершился за " + timeTransaction + " c.");
        }
        return this;
    }


}

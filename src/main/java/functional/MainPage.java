package functional;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

@Deprecated
public class MainPage extends Page {

    /* Методы для работы с главной страницей */

    public MainPage() {
        super();
    }

    public String mainPageWindow = "//div[@class='docbrowse-center z-center']";
    public String windowDirectory = "//div[@class='doc-dialog-content z-center']";
    public int defaultWaitForLoadingTime = 60;

    /**
     * Открыть пункт навигации
     */
    public MainPage openNavigation(String way) {
//        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class = 'tab-panel z-tabpanel'][not(contains(@style, 'display: none'))]"), 60);
        CommonFunctions.wait(1);
        String[] tabs = way.split(">");
        String previousTab = "";
        String elem = "";
        for (String tab : tabs) {
            if (previousTab.isEmpty()) {
                elem = "//div[@class='tree-holder z-center']//span[text()='" + tab + "']";
            } else {
                elem = "//span[text()='" + previousTab + "']/ancestor::tr/following::span[text()='" + tab + "'][1]";
            }
//            CommonFunctions.waitForElementDisplayed(By.xpath(elem), 30);
            $(By.xpath(elem)).shouldBe(Condition.visible, Duration.ofSeconds(30 * 1000));
            CommonFunctions.wait(0.5);
            $(By.xpath(elem)).click();
            previousTab = tab;
            waitForLoading(defaultWaitForLoadingTime);
        }
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Перейти в меню
     */
    public MainPage goToMenu(String menuName) {
        $$(By.cssSelector(".z-menubar-horizontal li"))
                .stream()
                .filter(el -> el.getAttribute("title").equals(menuName))
                .findFirst()
                .ifPresent(SelenideElement::click);
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Выбрать подпункт меню
     */
    public MainPage switchToMenu(String menuText) {
        SelenideElement menuContent = $(By.cssSelector(".z-menupopup ul"));
        menuContent.$$(By.cssSelector(".z-menuitem"))
                .stream()
                .filter(menuItems -> menuItems.getText().equals(menuText))
                .findFirst()
                .ifPresent(SelenideElement::click);
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Выбрать Все настройки и сбросить
     */
    @Deprecated
    public MainPage selectMenuItemResetUserSettings() { //бывший resetUserSettings
        By checkBox = By.cssSelector(".resetSettingsDialog .z-listheader span");
        By apply = By.xpath("//button[@class='resetOk z-button']");
        $(checkBox).click();
        $(apply).click();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Сбросить колонки на СФ по умолчанию
     */
    public MainPage resetUserSettings() { //новый
//        CommonFunctions.waitForElementDisplayed(By.cssSelector(".z-menubar-horizontal li[title='Настройки']"), 60, false);
//        goToMenu("Настройки");
//        CommonFunctions.waitForElementDisplayed(By.cssSelector(".z-menupopup ul"), 60, false);
//        switchToMenu("Сбросить пользовательские настройки");
//        waitForLoading(defaultWaitForLoadingTime);
//        selectMenuItemResetUserSettings();
//        CommonFunctions.waitForElementDisplayed(By.cssSelector(".resetSettingsDialog .z-listheader span"), 60, false);
//        $(By.cssSelector(".resetSettingsDialog .z-listheader span")).click();
//        CommonFunctions.waitForElementDisplayed(By.xpath("//button[@class='resetOk z-button']"), 60, false);
//        $x("//button[@class='resetOk z-button']").click();
//        waitForLoading(defaultWaitForLoadingTime);

        clickWebElement("//div[@class='z-menubar z-menubar-horizontal']//li[@title='Настройки']", defaultWaitForLoadingTime);
        clickWebElement("//div[@class='z-menupopup z-menupopup-shadow z-menupopup-open']//span[text()='Сбросить пользовательские настройки']", defaultWaitForLoadingTime);
        clickWebElement("//div[@class='resetSettingsDialog z-window z-window-highlighted z-window-shadow']//span[@class='z-listheader-checkable']", defaultWaitForLoadingTime);
        clickWebElement("//button[@class='resetOk z-button']", defaultWaitForLoadingTime);

        return this;
    }

    /**
     * Установить пользовательские колонки на СФ (убрав колонки по умолчанию)
     */
    public MainPage setUserColumns(String... columns) {
        //нажать ПКМ
        contextClickWebElement("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']", defaultWaitForLoadingTime);

        //в контекстном меню нажать "Выбор колонок"
        clickWebElement("//div[@class='z-menupopup z-menupopup-shadow z-menupopup-open']//span[@class='z-menuitem-text'][text()='Выбор колонок ...']", defaultWaitForLoadingTime);

        String columnsListWindow = "//div[@class='z-window z-window-highlighted z-window-shadow']";
        //во всплывающем окне нажать на чекбокс дважды, если он не выбран (чтобы убрать колонки по умолчанию)
        CommonFunctions.waitForElementDisplayed(By.xpath(columnsListWindow + "//span[@class='z-label'][text()='Название колонки']/parent::*/span[1]"), 30);
        if (!$(By.xpath(columnsListWindow + "//span[@class='z-label'][text()='Название колонки']/parent::*/span[@class='z-listheader-checkable z-listheader-checked']")).exists()) {
            $(By.xpath(columnsListWindow + "//span[@class='z-label'][text()='Название колонки']/parent::*/span[1]")).click();
            waitForLoading(defaultWaitForLoadingTime);
            CommonFunctions.wait(1);
        }
        clickWebElement(columnsListWindow + "//span[@class='z-label'][text()='Название колонки']/parent::*/span[1]", defaultWaitForLoadingTime);
        CommonFunctions.waitForElementDisappeared(By.xpath(columnsListWindow + "//tr[contains(@class, 'z-listitem-selected')]"), 60);
//        CommonFunctions.wait(1);

        //вбить поочередно в поиске каждое из нужных полей и нажать Enter
        for (String column : columns) {
            $(By.xpath(columnsListWindow + "//input[@class='z-textbox']")).click();
            waitForLoading(defaultWaitForLoadingTime);
//            CommonFunctions.wait(1);

            $(By.xpath(columnsListWindow + "//input[@class='z-textbox']")).sendKeys(column);
            waitForLoading(defaultWaitForLoadingTime);
//            CommonFunctions.wait(2);

            $(By.xpath(columnsListWindow + "//input[@class='z-textbox']")).pressEnter();
            waitForLoading(defaultWaitForLoadingTime);
//            CommonFunctions.wait(2);

//            $(By.xpath(columnsListWindow + "//button[@class='z-button']/img")).click();
            $(By.xpath(columnsListWindow + "//input[@class='z-textbox']")).clear();
            waitForLoading(defaultWaitForLoadingTime);
//            CommonFunctions.wait(2);
        }

        //нажать "ОК"
        clickButtonText("OK");
        waitForLoading(120);

        return this;
    }

    /**
     * Добавить пользовательские колонки на СФ (НЕ убирая колонки по умолчанию)
     */
    public MainPage addUserColumns(String... columns) {
        //нажать ПКМ
        contextClickWebElement("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']", defaultWaitForLoadingTime);

//        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']"), 60);
//        $(By.xpath("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']")).contextClick();

        //в контекстном меню нажать "Выбор колонок"
        clickWebElement("//div[@class='z-menupopup z-menupopup-shadow z-menupopup-open']//span[@class='z-menuitem-text'][text()='Выбор колонок ...']", defaultWaitForLoadingTime);

        //вбить поочередно в поиске каждое из нужных полей и нажать Enter
        String columnsListWindow = "//div[@class='z-window z-window-highlighted z-window-shadow']";
        CommonFunctions.waitForElementDisplayed(By.xpath(columnsListWindow + "//span[@class='z-label'][text()='Название колонки']/parent::*/span[1]"), 30);
        for (String column : columns) {
            $(By.xpath(columnsListWindow + "//input[@class='z-textbox']")).click();
            CommonFunctions.wait(2);
            $(By.xpath(columnsListWindow + "//input[@class='z-textbox']")).sendKeys(column);
            CommonFunctions.wait(2);
            $(By.xpath(columnsListWindow + "//input[@class='z-textbox']")).pressEnter();
            CommonFunctions.wait(3);
            $(By.xpath(columnsListWindow + "//button[@class='z-button']/img")).click();
            CommonFunctions.wait(3);
        }

        //нажать "ОК"
        new MainPage()
                .clickButtonText("OK")
                .waitForLoading(120);
//        CommonFunctions.wait(3);

        return this;
    }

    /**
     * Добавить пользовательские колонки на СФ (НЕ убирая колонки по умолчанию)
     */
    public MainPage addAllUserColumns() {
        //нажать ПКМ
        contextClickWebElement("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']", defaultWaitForLoadingTime);

        //в контекстном меню нажать "Выбор колонок"
        clickWebElement("//div[@class='z-menupopup z-menupopup-shadow z-menupopup-open']//span[@class='z-menuitem-text'][text()='Выбор колонок ...']", defaultWaitForLoadingTime);

        //во всплывающем окне нажать на чекбокс, чтобы выбрать все колонки
        String columnsListWindow = "//div[@class='z-window z-window-highlighted z-window-shadow']";
        String checkboxLocator = "//span[contains(@class,'z-listheader-checkable')]";
        CommonFunctions.waitForElementDisplayed(By.xpath(columnsListWindow + checkboxLocator), defaultWaitForLoadingTime);
        if (!$x(columnsListWindow + checkboxLocator).getAttribute("class").contains("checked")) {
            clickWebElement(columnsListWindow + checkboxLocator, defaultWaitForLoadingTime);
        }
        CommonFunctions.wait(2);

        //нажать "ОК"
        new MainPage()
                .clickButtonText("OK");
//        CommonFunctions.wait(3);

        CommonFunctions.waitForElementDisappeared(By.xpath(columnsListWindow), defaultWaitForLoadingTime);

        return this;
    }

    /**
     * Очистка фильтров списка
     */
    public MainPage clearListFilter() {
        while ($(By.xpath(windowApprovalList + "//button[@class()='filter-button filter-plank-cancel-button z-button']")).exists()) {
            $(By.xpath(windowApprovalList + "//button[@class()='filter-button filter-plank-cancel-button z-button']")).click();
            System.out.println("Found filter list");
            CommonFunctions.wait(0.1);
        }
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Применить фильтр и отдельная функция выбрать (Поля фильтра указывать параметр title)
     *
     * @param columnTitle - title столбца
     * @param value       - значение
     */
    public MainPage filterColumnInList(String columnTitle, String value) {
        filterOn(mainPageWindow);
        scrollListForm(By.xpath(mainPageWindow + "//th[contains(@class,'z-listheader-sort')][@title='" + columnTitle + "']"));
        String val = FileFunctions.compareValues(value);

        SelenideElement field = null;
        int num;

        SelenideElement element = $x(mainPageWindow + "//th[contains(@class,'z-listheader-sort')][@title='" + columnTitle + "'][@fromidx]");
        if (element.exists()) {
            //если есть fromidx/toidx, рассчитываем номер столбца по нему
            //1. Рассчитываем номер столбца
            num = Integer.parseInt(element.getAttribute("fromidx"));

            //2. Рассчитываем номер поля фильтрации (с учетом скрытых)
            //загоняем все поля фильтрации в массив, и по номеру столбца находим нужное поле фильтрации
            ElementsCollection fields = $$x(mainPageWindow + "//th[contains(@class,'z-auxheader')]");
            field = fields.get(num).$x(".//input");

        } else {
            //если нет fromidx/toidx, загоняем все столбцы в массив, и рассчитываем номер столбца по title

            //1. Рассчитываем номер столбца
            ElementsCollection elements = $$x(mainPageWindow + "//th[contains(@class,'z-listheader-sort')][not(contains(@style, 'hidden'))]");
            num = elements.indexOf($x("//*[@title='" + columnTitle + "']")) + 1;
            //если есть поле с вложением, прибавляем +1
            if ($x(mainPageWindow + "//th[@class='attachColumnHeader z-listheader']").exists()) {
                num++;
            }
            //если есть поле с воронкой, прибавляем +1
            if ($x(mainPageWindow + "//th[contains(@class,'z-auxheader')][not(contains(@style, 'hidden'))]//span[@filter-for='nullColumn']").exists() ||
                    $x(mainPageWindow + "//th[contains(@class,'null-column')]").exists()) {
                num++;
            }

            /*
            //2. Рассчитываем номер поля фильтрации (без учета скрытых)
            //загоняем все поля фильтрации в массив, и по номеру столбца находим нужное поле фильтрации
            ElementsCollection fields = $$(By.xpath(windowListDocuments + "//th[contains(@class,'z-auxheader')][not(contains(@style, 'hidden'))]"));
            field = fields.get(num).$(By.xpath(".//input"));
            */

            //2. Получаем нужное поле фильтрации по полученному номеру
            field = $x(mainPageWindow + "//th[contains(@class,'z-auxheader')][not(contains(@style, 'hidden'))][" + num + "]").$x(".//input");
        }

        //3. Фильтруем, в зависимости от типа поля фильтрации
        if (field.getAttribute("class").contains("combobox")) {
            //для полей с типом "комбобокс"
            if (field.$(By.xpath(".//parent::span")).getAttribute("class").contains("readonly")) {
                //комбобокс readonly
                $(field).click();
                CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']"), 60);
                $(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//div[contains(text(), '" + val + "')]")).click();
                CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//button[text()='ОК'][not(contains(@disabled, 'disabled'))]"), 60);
                $(By.xpath("//div[@class='z-combobox-popup z-combobox-open z-combobox-shadow']//button[text()='ОК']")).click();
            } else {
                //комбобокс не readonly
                field.$(By.xpath(".//following-sibling::a/i")).click();
                CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-combobox-popup  z-combobox-open z-combobox-shadow']"), 60);
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
     * Выделить элемент на СФ
     */
    public MainPage clickRowInList(String value) {
        String val = FileFunctions.compareValues(value);

        waitForLoading(100);
        scrollListForm(By.xpath(mainPageWindow + "//td[@title='" + val + "']"));
        $(By.xpath(mainPageWindow + "//td[@title='" + val + "']"))
                .click();
        waitForLoading(100);
        //свернуть панель краткой формы,если она откроется
        closeShortForm();
        waitForLoading(defaultWaitForLoadingTime);
        return this;
    }

    /**
     * Выделить несколько элементов на СФ
     */
    public MainPage clickRowsInList(String... values) {

        //если у строки есть поле с чекбоксом
        if($x("//div[@class='z-listboxfakepaging-body z-word-nowrap']//tr[contains(@class, 'z-listitem')]//span[contains(@class, 'checkbox')]").exists()) {
            //поочередно ставим галочку в чекбокс каждого элемента из массива
            for (String value : values) {
                $(By.xpath(mainPageWindow + "//div[@class='z-listboxfakepaging-body z-word-nowrap']//tr[contains(@class, 'z-listitem')]//td[@title='" + value + "']/preceding-sibling::td//span[contains(@class, 'checkbox')]")).click();
                waitForLoading(defaultWaitForLoadingTime);
                closeShortForm();
                waitForLoading(defaultWaitForLoadingTime);
            }
        } else { //если столбца с чекбоксом нет
            System.out.println("Выбрать несколько строк не удалось. Требуется доработка метода!");
            //через Robot (требуется доработка)
//            Robot robot = null;
//            try {
//                robot = new Robot();
//
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//
//                //зажимаем клавишу Ctrl с помощью робота
//                robot.keyPress(KeyEvent.VK_CONTROL);
//
//                //поочередно кликаем по каждому элементу из массива
//                for (int i = 0; i < values.length; i++) {
//                    $(By.xpath(windowListDocuments + "//td[@title='" + values[i] + "']/preceding-sibling::td//span")).click();
//                    waitForLoading(defaultWaitForLoadingTime);
//                    //свернуть панель краткой формы,если она откроется
//                    closeShortForm();
//                    waitForLoading(defaultWaitForLoadingTime);
//                }
//
//                //отпускаем клавишу Ctrl
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//
//            } catch (AWTException e) {
//                throw new RuntimeException("Робот не создан " + e);
//            } finally {
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//            }
        }

        return this;
    }

    /**
     * Выделить несколько элементов на СФ (по номеру строки)
     */
    public MainPage clickRowsInList(int... rowNumbers) {
        //если у строки есть столбец с чекбоксом
        if($x("//div[@class='z-listboxfakepaging-body z-word-nowrap']//tr[contains(@class, 'z-listitem')]//span[contains(@class, 'checkbox')]").exists()) {
            //поочередно ставим галочку в чекбокс каждого элемента из массива
            for (int i : rowNumbers) {
                $(By.xpath(mainPageWindow + "//div[@class='z-listboxfakepaging-body z-word-nowrap']//tr[contains(@class, 'z-listitem')][" + i + "]//span[contains(@class, 'checkbox')]")).click();
                waitForLoading(defaultWaitForLoadingTime);
                closeShortForm();
                waitForLoading(defaultWaitForLoadingTime);
            }
        } else { //если столбца с чекбоксом нет
            System.out.println("Выбрать несколько строк не удалось. Требуется доработка метода!");
            //через Robot (требуется доработка)
//            Robot robot = null;
//            try {
//                robot = new Robot();
//
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//
//                //зажимаем клавишу Ctrl с помощью робота
//                robot.keyPress(KeyEvent.VK_CONTROL);
//
//                //поочередно кликаем по каждому элементу из массива
//                for (int i : rowNumbers) {
//                    $(By.xpath(windowListDocuments + "//div[@class='z-listboxfakepaging-body z-word-nowrap']//tr[contains(@class, 'z-listitem')][" + i + "]")).click();
//                    waitForLoading(defaultWaitForLoadingTime);
//                    closeShortForm();
//                    waitForLoading(defaultWaitForLoadingTime);
//                }
//
//                //отпускаем клавишу Ctrl
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//
//            } catch (AWTException e) {
//                throw new RuntimeException("Робот не создан " + e);
//            } finally {
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//            }
        }

        return this;
    }

    /**
     * Скопировать столбец списковой формы
     */
    public ArrayList<String> getListFromColumn(String columnTitle) {
        new MainPage().waitForLoading(defaultWaitForLoadingTime);
        String th = "//tr[@class = 'z-listhead'][not(contains(@style, 'display: none;'))]" +
                "//th[contains(@class, 'z-listheader')][not(contains(@class, 'null-column'))]" +
                "[not(contains(@style, 'hidden'))][@title = '" + columnTitle + "']";
        executeJavaScript("arguments[0].scrollIntoView();", $x(th));
        String columnNumber = $(By.xpath(th)).getAttribute("toidx");
        if (columnNumber == null) {
            By thLocator = By.xpath("//tr[@class = 'z-listhead'][not(contains(@style, 'display: none;'))]" +
                    "//th[contains(@class, 'z-listheader')][not(@class = 'z-listheader')]" +
                    "[not(contains(@class, 'null-column'))]");
            ElementsCollection elements = $$(thLocator);
            columnNumber = String.valueOf(elements.indexOf($(By.xpath("//*[@title='" + columnTitle + "']"))) + 1);
        }
        ArrayList<String> columnList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String row = String.valueOf(i + 1);
            String path = "(//td[@class = 'z-listcell'][@col = '" + columnNumber + "'])[" + row + "]//div";
            if (!$(By.xpath(path)).exists()) break;
            String str = $(By.xpath(path)).getText();
            if (str.equals("") | str.equals(" ")| str.equals(" ")) str = " ";
            columnList.add(str);
            String message = "«" + str + "»";
            if (i == 0) {
                System.out.print("Колонка «" + columnTitle + "» содержит: " + message);
            } else {
                System.out.print(", " + message);
            }
        }
        System.out.println();
        return columnList;
    }

    /**
     * Проверить статус документа
     */
    // НЕ АКТУАЛЬНО , ИСПОЛЬЗОВАТЬ checkDataOnFields
    @Deprecated
    public MainPage checkStatusDocument(String numberDoc, String status) {
        String number = numberDoc;
        if (new File(numberDoc).exists()) {
            number = FileFunctions.readValueFile(numberDoc);
        }
        $(By.xpath(mainPageWindow + "//td[@title='" + number + "']//following-sibling::td[@title='" + status + "']")).isDisplayed();
        return this;
    }

    /**
     * Проверить статус документа
     */
    public MainPage checkDataOnFields(String nameField, String value) {

        //Ищем поле с наименованием nameField
        SelenideElement[] columns = $$(By.xpath("//tbody[contains(@id, 'headrows')]/tr[1]/th")).toArray(new SelenideElement[0]);
        int number = 0;
        for (int i = 1; i < columns.length; i++) {
            columns = $$(By.xpath("//tbody[contains(@id, 'headrows')]/tr[1]/th")).toArray(new SelenideElement[0]);
            if (columns[i].getText().trim().equals(nameField)) {
                number = i;
                break;
            }
        }
        if (number == 0) {
            throw new RuntimeException("Поле с наименованием: " + nameField + " не найдено!");
        }
        /////////////////////////////////////
        //Обновляем СФ пока не изменился статус документа, если время ожидания превышено то кидаем Exception
        waitForLoading(300);
        double maxTime = 120.0;
        long startTransaction = CommonFunctions.StartTransaction();
        while (true) {
            try {
                SelenideElement[] lines = $$(By.xpath("//tr[contains(@title, 'Выделено')][1]/td")).toArray(new SelenideElement[0]);
                String valueField;
                try {
                    valueField = lines[number].getAttribute("title");
                } catch (Exception e) {
                    lines = $$(By.xpath("//tr[contains(@title, 'Выделено')][1]/td")).toArray(new SelenideElement[0]);
                    valueField = lines[number].getAttribute("title");
                }
                if (valueField.equals("")) {
                    valueField = lines[number].getText();
                }

                if (!valueField.trim().equals(value)) {
                    throw new RuntimeException("У найденной записи значение поля '" + nameField + "' равно '" + valueField + "'");
                }
                break;
            } catch (Exception e) {
                double time = CommonFunctions.TimeTransaction(startTransaction);
                if (time > maxTime) throw new RuntimeException("" + e);
            }
            //Ждем кликабельности кнопки Обновить и кликаем
            int i = 0;
            while (true) {
                try {
                    $(By.xpath("//button/img[contains(@src, 'refresh.png')]")).click();
//                            new MainPage().clickButtonText("Обновить");
                    break;
                } catch (Exception e) {
                    System.out.println("Ждем открытия кнопки: " + i + "c.");
                    if (i == 119) throw new RuntimeException("" + e);
                    CommonFunctions.wait(1);
                    i++;
                }
            }
            waitForLoading(defaultWaitForLoadingTime);
        }
        ///////////////////////////////////////////////////
        return this;
    }

    /**
     * Проверка проводок в документе (по строкам)
     */
    public MainPage checkAccRecords(int numLine, String nameField, String value) {
        String winCheck = "//div[text()='Просмотр записей учета']//ancestor::div";

        //Ищем поле с наименованием nameField
        SelenideElement[] columns = $$(By.xpath(winCheck + "//tbody[contains(@id, 'headrows')]/tr[1]/th")).toArray(new SelenideElement[0]);
        int number = 0;
        for (int i = 1; i < columns.length; i++) {
            columns = $$(By.xpath(winCheck + "//tbody[contains(@id, 'headrows')]/tr[1]/th")).toArray(new SelenideElement[0]);
            if (columns[i].getText().trim().equals(nameField)) {
                number = i;
                break;
            }
        }
        if (number == 0) {
            throw new RuntimeException("Поле с наименованием: " + nameField + " не найдено!");
        }

        //Проверяем значение полей
        SelenideElement[] lines = $$(By.xpath(winCheck + "//tr[contains(@class, 'z-listitem')][" + numLine + "]/td")).toArray(new SelenideElement[0]);
        String valueField;
        try {
            valueField = lines[number].getAttribute("title");
        } catch (Exception e) {
            lines = $$(By.xpath(winCheck + "//tr[contains(@class, 'z-listitem')][" + numLine + "]/td")).toArray(new SelenideElement[0]);
            valueField = lines[number].getAttribute("title");
        }
        if (valueField.equals("")) {
            valueField = lines[number].getText();
        }

        if (!valueField.trim().equals(value)) {
            throw new RuntimeException("У найденной записи значение поля '" + nameField + "' равно '" + valueField + "'");
        }
        return this;
    }

    /**
     * Свернуть панель краткой формы, если она откроется
     */
    public MainPage closeShortForm() {
        By closeShortFormButton = By.xpath("//a[@class='collapse z-toolbarbutton']/span[@class = 'z-toolbarbutton-content']");
        By shortFormPanel = By.xpath("//div[@class='doc-quick-view-panel z-south']//following-sibling::div[@class='z-south-splitter'][contains(@style, 'block')]");
        // Вычислить площадь, занятую КФ
        int visibleListFormHeight = Integer.parseInt($(shortFormPanel).getAttribute("style").split("; ")[2].replaceAll("[^0-9]", ""));
        int allListFormHeight = Integer.parseInt($(By.xpath("//div[@class = 'docbrowse-center z-center']"))
                .getAttribute("style").split("; ")[3].replaceAll("[^0-9]", ""));
        int visiblePercent = visibleListFormHeight * 100 / allListFormHeight;
        System.out.print("Доступно " + visiblePercent + "% СФ");
        // Скрыть КФ при необходимости
        if (visiblePercent < 25) {
            if (!$(closeShortFormButton).isDisplayed()) {
                // Перетащить КФ вниз на 50 px
                actions().dragAndDropBy($(shortFormPanel), 0, -100).build().perform();
                CommonFunctions.wait(1);
                if ($(closeShortFormButton).isDisplayed()) {
                    $(closeShortFormButton).click();
                    System.out.println(". КФ смещена вниз и скрыта");
                }
            } else {
                $(closeShortFormButton).click();
                System.out.println(". КФ скрыта");
            }
        } else if (visiblePercent < 75) {
            $(closeShortFormButton).click();
            System.out.println(". КФ скрыта");
        } else {
            System.out.println();
        }
        return this;
    }

    /**
     * Выйти из учетной записи
     */
    public MainPage logout() {
//        CommonFunctions.waitForElementDisplayed(By.cssSelector(".z-menubar-horizontal li[title='Настройки']"), 60, false);
//        goToMenu("Настройки");
//        waitForLoading(defaultWaitForLoadingTime);
//        CommonFunctions.waitForElementDisplayed(By.cssSelector(".z-menupopup ul"), 60, false);
//        switchToMenu("Выйти");
//        waitForLoading(defaultWaitForLoadingTime);
//        CommonFunctions.wait(3);

        clickWebElement("//div[@class='z-menubar z-menubar-horizontal']//li[@title='Настройки']", defaultWaitForLoadingTime);
        clickWebElement("//div[@class='z-menupopup z-menupopup-shadow z-menupopup-open']//span[text()='Выйти']", defaultWaitForLoadingTime);
        CommonFunctions.wait(3);

        return this;
    }

    /**
     * Проверить значение в столбце СФ (должна быть 1 отфильтрованная запись)
     * @param columnTitle - title столбца
     * @param value       - значение
     */
    public MainPage assertValueInColumn(String columnTitle, String value) {
        scrollListForm(By.xpath(mainPageWindow + "//th[contains(@class,'z-listheader-sort')][@title='" + columnTitle + "']"));
        String val = FileFunctions.compareValues(value);

        SelenideElement field = null;
        int num;

        //1. Рассчитываем номер столбца
        //если есть fromidx/toidx, рассчитываем номер столбца по нему
        /*SelenideElement element = $x(windowListDocuments + "//th[contains(@class,'z-listheader-sort')][@title='" + columnTitle + "'][@fromidx]");
        if (element.exists()) {
            num = Integer.parseInt(element.getAttribute("fromidx"));
        } else {*/
            //если нет fromidx/toidx, загоняем все столбцы в массив, и рассчитываем номер столбца по title
            ElementsCollection elements = $$x(mainPageWindow + "//th[contains(@class,'z-listheader-sort')][not(contains(@style, 'hidden'))]");
            num = elements.indexOf($x("//*[@title='" + columnTitle + "']")) + 1;
        //}
        //если есть поле с вложением, прибавляем +1
        if ($x(mainPageWindow + "//th[@class='attachColumnHeader z-listheader']").exists()) {
            num++;
        }
        //если есть поле с воронкой, прибавляем +1
        if ($x(mainPageWindow + "//th[contains(@class,'z-auxheader')][not(contains(@style, 'hidden'))]//span[@filter-for='nullColumn']").exists() || $x(mainPageWindow + "//th[contains(@class,'null-column')][@fromidx]").exists()) {
            num++;
        }

//        System.out.println("num: " + num);

        //2. Получаем нужную ячейку по полученному номеру
        field = $x(mainPageWindow + "//tr//td[contains(@class,'z-listcell')][not(contains(@style, 'hidden'))][" + num + "]").$x(".//div");
//        System.out.println("Проверяемая ячейка: " + field);
//        System.out.println("Значение проверяемой ячейки равно: " + field.getText());

        //3. Проверяем значение
        Assert.assertEquals(field.getText(), val);

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
                By.xpath(mainPageWindow + "//span[text()='" + fieldName + "']/ancestor::div[2]//following-sibling::div[1]/input"),
                By.xpath(mainPageWindow + "//span[text()='" + fieldName + "']/ancestor::div//following-sibling::div[1]/textarea"),
                By.xpath(mainPageWindow + "//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//input"),
                By.xpath(mainPageWindow + "//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//textarea"),
                By.xpath(mainPageWindow + "//span[text()='" + fieldName + "']/ancestor::div[1]//following::input[1]"),
                By.xpath(mainPageWindow + "//span[text()='" + fieldName + "']/ancestor::div[1]//following::textarea[1]")
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
            ElementsCollection elementList = $$x(mainPageWindow +
                    "//span[text()='" + fieldName + "']/ancestor::div[2]/following-sibling::div[1]//input/following-sibling::a[1] | " +
                    "//span[text()='" + fieldName + "']/ancestor::td[1]/following-sibling::td[1]//input/following-sibling::a[1]");
            SelenideElement element = elementList.get(0);
            element.click();
            waitForLoading(defaultWaitForLoadingTime);
            CommonFunctions.wait(2);
            //пункт комбобокса
            By comboboxItem = By.xpath(mainPageWindow + "//li/span[contains(text(), '" + value + "')] | " +
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

        totalXPath = "." + mainPageWindow + fieldBlockXPath + inputXPath;

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
     * Проверить видимости колонки на СФ
     * @param map - <Название колонки, Видимость по умолчанию(Да/Нет)>
     */
    public void checkColumnVisibility(Map<String, Boolean> map) {
        SoftAssert a = new SoftAssert();

        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            String th = entry.getKey();
            String thXPath = "//th/div[text() = '" + th + "']";
            a.assertTrue($x(thXPath).exists(), "Колонка \"" + th + "\" существует - ");
            thXPath = thXPath.replace("//th", "//th[not(contains(@style, 'hidden'))]");
            boolean isDisplayed = entry.getValue();
            if (isDisplayed) {
                a.assertTrue($x(thXPath).exists(), "Колонка \"" + th + "\" отображается -");
            } else {
                a.assertFalse($x(thXPath).exists(), "Колонка \"" + th + "\" не отображается -");
            }
        }
        a.assertAll();
    }

    /*
    public void checkColumnVisibility(Map<String, Map.Entry<Integer, Boolean>> map) {
        SoftAssert a = new SoftAssert();

        for (Map.Entry<String, Map.Entry<Integer, Boolean>> entry : map.entrySet()) {
            String th = entry.getKey();

            String thXPath = "//th[@fromidx = '" + fromidx + "']/div[text() = '" + th + "']";
            a.assertTrue($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" существует - ");
            thXPath = thXPath.replace("//th", "//th[not(contains(@style, 'hidden'))]");
            if (isDisplayed.equals("Да")) {
                a.assertTrue($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" отображается -");
            } else if (isDisplayed.equals("Нет")) {
                a.assertFalse($(By.xpath(thXPath)).exists(), "Колонка \"" + th + "\" не отображается -");
            } else {
                Assert.fail("Неверно выбрано условие. Значение exist должно быть равно 'Да' или 'Нет'");
            }
        }
        a.assertAll();

        //        Map<String, Map.Entry<Integer, Boolean>> map = new HashMap<>();
        //        map.put("Этап утверждения ФК", new SimpleEntry(1, true));

    }
     */

}

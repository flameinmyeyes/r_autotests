package functional;


import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;


public class PortalPage extends Page {

    public PortalPage() {
        super();
    }

    public String windowPortal = "//img[@title='Электронный бюджет']//ancestor::html";

    private By user = By.xpath("//input[@id='login']");
    private By password = By.xpath("//input[@id='password']");
    private By buttonOk = By.xpath("//input[@value='ВОЙТИ']");
    private By exitButton = By.xpath("//button[@class='af_commandButton']//span[text()='Выйти']");

    public void inputUserName (String name) {
        $(user).sendKeys(name);
    }

    public void inputPassword (String psw) {
        $(password).sendKeys(psw);
    }

    /**
     * Авторизация на портале
     * */
    public PortalPage authorization(String name, String psw) {

        boolean exit = true;
        refresh();
        CommonFunctions.wait(1);

        //проверка, что портал не в 404
        try {
            while ((!$(user).isDisplayed()) && (!$(exitButton).isDisplayed())) {
                refresh();
                System.out.println("Выполнена перезагрузка страницы");
                CommonFunctions.wait(5);
            }
        } catch (Exception e) {}

        //проверка, доступно ли окно авторизации
        try {
            if ($(user).isDisplayed()) {
                exit = false;
                inputUserName(name);
                inputPassword(psw);
                $(buttonOk).click();
//                System.out.println("Выполнена авторизация");
                CommonFunctions.wait(5);
            }
        } catch (Exception e) {}

        //проверка, не открылась ли сразу СФ
        try {
            if (($(exitButton).isDisplayed()) && (exit)) {
                $(exitButton).click();
                System.out.println("Выполнен выход из предыдущей сессии");
                CommonFunctions.wait(10);
                //
                CommonFunctions.waitForElementDisplayed(user, 60);
                inputUserName(name);
                inputPassword(psw);
                $(buttonOk).click();
//                System.out.println("Выполнена авторизация");
                CommonFunctions.wait(5);
            }
        } catch (Exception e) {}

        return this;
    }


    /**
     * Открыть пункт навигации в главном меню Портала
     * */
    public PortalPage openNavigation(String way) {
        String[] tabs = way.split(">");
        String previousTab = "";
        String elem = "";
        for (String tab:tabs) {
            if (previousTab.isEmpty()) {
                elem = windowPortal + "//div[@class='af_table_data-body']//span[text()='" + tab + "']";
            } else {
                elem = windowPortal + "//span[text()='" + previousTab + "']/ancestor::tr/following::span[text()='" + tab + "'][1]";
            }
            CommonFunctions.waitForElementDisplayed(By.xpath(elem), 10);
            CommonFunctions.wait(1);

            //клик ПКМ
            //actions.contextClick($(By.xpath(elem))).perform();
            $(By.xpath(elem)).contextClick();
            CommonFunctions.wait(1);

            //проверка контекстного меню
            try {
                if ($(By.xpath("//span[contains(.,'Свернуть')]")).isDisplayed()) {
                    System.out.println("Содержит \"Свернуть\"");
//                    actions().sendKeys(Keys.ESCAPE).perform();
                    $(By.xpath("//span[contains(.,'Свернуть')]")).pressEscape();
                    CommonFunctions.wait(1);
                    previousTab = tab;
                    continue;
                }
            } catch (Exception e) {}

            try {
                if ($(By.xpath("//span[contains(.,'Развернуть')]")).isDisplayed()) {
                    System.out.println("Содержит \"Развернуть\"");
                    new functional.MainPage().goToTab("Развернуть");
                    previousTab = tab;
                }
            } catch (Exception e) {}

            previousTab = tab;
            System.out.println("Не содержит \"Свернуть\"/\"Развернуть\"");
            $(By.xpath(elem)).click();
        }
        return this;
    }

    /**
     * Открыть пункт в дереве навигации (отчеты, АИ и т.д.) В РАЗРАБОТКЕ
     * */
    public PortalPage openNavigation_2(String window, String way) {
        //window = "//div[@class='z-center-body']";

        String[] tabs = way.split(">");
        String previousTab = "";
        String elem = "";
        for (String tab:tabs) {
            if (previousTab.isEmpty()) {
                elem = window + "//span[text()='" + tab + "']//preceding::i[1]";
            } else {
                elem = window + "//span[text()='" + previousTab + "']/ancestor::tr/following::span[text()='" + tab + "'][1]";
            }
            CommonFunctions.waitForElementDisplayed(By.xpath(elem), 10);
            CommonFunctions.wait(1);

            try {
                if ($(By.xpath(window + "//span[text()='ПБС']//preceding::i[1][contains(@class, 'close')]")).isDisplayed()) {
                    $(By.xpath(elem)).click();
                    System.out.println("Содержит \"Развернуть\"");
                    CommonFunctions.wait(1);
                    previousTab = tab;
                    continue;
                }
            } catch (Exception e) {}

            try {
                if ($(By.xpath(window + "//span[text()='ПБС']//preceding::i[1][contains(@class, 'open')]")).isDisplayed()) {
                    System.out.println("Содержит \"Свернуть\"");
                    previousTab = tab;
                }
            } catch (Exception e) {}

            previousTab = tab;
            System.out.println("Не содержит \"Свернуть\"/\"Развернуть\"");
            $(By.xpath(window + "//span[text()='" + tab + "']")).click();
        }
        return this;
    }

    /**
     * Закрыть все вкладки в меню Портала
     */
    public PortalPage closePortalMenuTabs(){
        SelenideElement closeTabIcon = $(By.xpath(windowPortal + "//button[@class='dynTab af_commandButton']//span[text()]//following::a[1]"));
        try {
            while (closeTabIcon.isDisplayed()) {
                closeTabIcon.click();
                System.out.println("Обнаружена открытая вкладка");
                CommonFunctions.wait(0.1);
            }
        } catch (Exception ex){
            System.out.println("Все вкладки закрыты");
        }
        return this;
    }

    /**
     * Проверить, открыто ли нужное левое меню Портала, если нет то открыть
     * */
    public PortalPage openPortalLeftMenu(String menuName) {
        SelenideElement menuIcon = $(By.xpath(windowPortal + "//div[@class='nomargin af_panelGroupLayout']//span[@class='af_commandImageLink_text'][text()='" + menuName + "']//preceding::img[1]"));
        if (menuIcon.getAttribute("src").contains("report_off")) {
            menuIcon.click();
            CommonFunctions.wait(3);
        }
        return this;
    }

    /**
     * Открыть правое меню Портала
     * */
    public PortalPage openPortalRightMenu(String menuName) {
        SelenideElement menu = $(By.xpath(windowPortal + "//div[@class='af_panelTabbed_tab-content']/a[text()='" + menuName + "']"));
        //CommonFunctions.waitForElementDisplayed(By.xpath(windowPortal + "//div[@class='af_panelTabbed_tab-content']/a[text()='" + menuName + "']"), 10);
        if (menu.isDisplayed()) {
            menu.click();
            CommonFunctions.wait(3);
        }
        return this;
    }

    /**
     * Сменить фрейм
     * */
    public PortalPage changeFrame(By locator) {
        switchTo().frame($(locator));
        System.out.println("Фрейм успешно изменен");
        return this;
    }

    /**
     * Вернуться к стандартному фрейму
     * */
    public PortalPage changeFrameToDefault() {
        switchTo().defaultContent();
        System.out.println("Выполнен возврат к стандартному фрейму");
        return this;
    }

    ///////удалить

    /**
     * Импорт документа
     */
    @Deprecated
    public PortalPage importDocumentRobot(String typeImport, String wayToFile) {
        if (!typeImport.isEmpty()){
            $(By.xpath("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//span[text()='" + typeImport + "']")).click();
            CommonFunctions.wait(2);
            new MainPage().waitForLoading(10);
            $(By.xpath("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//button[text()='ОК']")).click();
            CommonFunctions.wait(2);
            new MainPage().waitForLoading(10);
            $(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//div[@class='fileUpload btn']")).click();
            new MainPage().waitForLoading(10);
        }
        CommonFunctions.wait(10);

        File file = new File(wayToFile);
        try {
            wayToFile = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Робот не создан " + e);
        }

        //заносим путь к файлу в буфер обмена
        StringSelection stringSelection = new StringSelection(wayToFile);
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        //вставляем путь к файлу из буфера с помощью робота
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        CommonFunctions.wait(8);
        //жмём Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        CommonFunctions.wait(8);

        new functional.MainPage().clickButtonText("Сохранить");

        return this;
    }

    /**
     * Импорт документа (массовый)
     */
    @Deprecated
    public PortalPage importDocumentRobotMassive(String typeImport, String wayToFolder) {
        if (!typeImport.isEmpty()) {
            $(By.xpath("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//span[text()='" + typeImport + "']")).click();
            CommonFunctions.wait(2);
            new MainPage().waitForLoading(10);
            $(By.xpath("//div[@class='z-window z-window-noborder z-window-highlighted z-window-shadow']//button[text()='ОК']")).click();
            CommonFunctions.wait(2);
            new MainPage().waitForLoading(10);
        }

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Робот не создан " + e);
        }

        //создаем массив файлов
        File[] listOfFiles = new File(wayToFolder).listFiles();
        String wayToFile = null;

        for (int i = 0; i < listOfFiles.length; i++) {
            //поочередно берем имена файлов из массива и создаем полный путь к каждому
            String fileName = listOfFiles[i].getName();
            wayToFile = wayToFolder + fileName;

            //кликаем по кнопке загрузки
            $(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']//div[@class='fileUpload btn']")).click();
            CommonFunctions.wait(10);
            new MainPage().waitForLoading(10);

            //заносим путь к файлу в буфер обмена
            StringSelection stringSelection = new StringSelection(wayToFile);
            java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);

            //вставляем путь к файлу из буфера с помощью робота
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            CommonFunctions.wait(8);
            //жмём Enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            CommonFunctions.wait(8);
        }
        new functional.MainPage().clickButtonText("Сохранить");

        return this;
    }


    /**
     * Сбросить колонки на СФ по умолчанию
     * */
    @Deprecated
    public PortalPage setDefaultColumns (String ... columns) {
        //найти первый столбец скроллера, нажать ПКМ
        /*
        ArrayList list = (ArrayList) $$(By.xpath("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']"));
        SelenideElement webElement = (SelenideElement) list.get(0);
        Actions actions = new Actions();
        actions.contextClick(webElement).perform();
        */
        $(By.xpath("//div[@class='doc-table-holder z-center']//div[@class='z-listheader-content']")).contextClick();
        CommonFunctions.wait(3);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='z-menupopup z-menupopup-shadow z-menupopup-open']"), 10);

        //в контекстном меню нажать "Колонки по умолчанию"
        $(By.xpath("//span[@class='z-menuitem-text'][text()='Колонки по умолчанию']")).click();
        CommonFunctions.wait(5);

        return this;
    }

    /**
     * Печать документа
     */
    @Deprecated
    public PortalPage printDocumentRobot(String wayToFile) {
        //проверяем, есть ли старая версия файла с таким именем, если есть - удаляем
        File file = new File(wayToFile);
        try {
            wayToFile = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(file.delete()){
            System.out.println("Файл успешно удален");
        } else {
            System.out.println("Файла не обнаружено");
        }
        CommonFunctions.wait(3);

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
        CommonFunctions.wait(5);
        //жмём Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        CommonFunctions.wait(7);

        return this;
    }


}

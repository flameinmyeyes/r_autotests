package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_01 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_01_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 01 Заполнение Заявки на получение услуги, подписание Заявки УКЭП и автоматическая передача Заявки на верификацию")
    @Link(name = "Test_04_07_01", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302431")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Блок «Сведения о демонстрационно-дегустационном павильоне»")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=pavilion&serviceId=a546931c-0eb9-4545-853a-8a683c0924f7&next_query=true");
        new GUIFunctions()
                .authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
//        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
        System.out.println($x("//div[text()='Номер заявки']/following-sibling::div").getText());

        refreshTab("//*[text()='Продолжить']", 10);
        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Страна нахождения павильона']")
                .inContainer("Сведения о демонстрационно-дегустационном павильоне")
                .inField("Страна нахождения павильона").selectValue(PROPERTIES.getProperty("Авторизация.Страна нахождения павильона")).assertValue()
                .waitForLoading();
        new GUIFunctions().clickButton("Далее")
                .waitForLoading();
    }

    @Step("Заполнение заявки")
    public void step02() throws AWTException {
        CommonFunctions.printStep();

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_HOME);
        $x("//*[text()='Дополнительные сведения']").scrollTo();
        new GUIFunctions().inContainer("Дополнительные сведения")
                .inField("Комментарий").inputValue(PROPERTIES.getProperty("Заполнение заявки.Комментарий")).assertValue();
    }

    @Step("Блок «Информация о продукции»")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Добавить +");
        new GUIFunctions().inContainer("Сведения о продукции")
                .inField("Каталог продукции").inputValue("1704")
                .waitForElementDisplayed("//*[contains(text(), 'Белёвская пастила с чёрной смородиной')]")
                .clickByLocator("//*[contains(text(), 'Белёвская пастила с чёрной смородиной')]");

        new GUIFunctions().inField("Количество ед. продукции").inputValue(PROPERTIES.getProperty("Информация о продукции.Количество ед. продукции")).assertValue()
                .inField("Единица измерения").selectValue(PROPERTIES.getProperty("Информация о продукции.Единица измерения")).assertValue()
                .inField("Общая стоимость партии товара, включая затраты на транспортировку (юань)").inputValue(PROPERTIES.getProperty("Информация о продукции.Общая стоимость партии товара"))
                .inField("Условия транспортировки и хранения продукции").inputValue(PROPERTIES.getProperty("Информация о продукции.Условия транспортировки и хранения продукции")).assertValue()
                .inField("Розничная продажа").setCheckboxON().assertCheckboxON()
                .inField("Оптовая продажа").setCheckboxON().assertCheckboxON()
                .inField("Оценка спроса (в рублях)").inputValue(PROPERTIES.getProperty("Информация о продукции.Оценка спроса"))
                .inField("Готовность к адаптации").setCheckboxON().assertCheckboxON()
                .inField("Наличие сертификата страны размещения").setCheckboxON().assertCheckboxON()
                .inField("Номер сертификата").inputValue(PROPERTIES.getProperty("Информация о продукции.Номер сертификата")).assertValue()
                .inField("Дата выдачи").inputValue(PROPERTIES.getProperty("Информация о продукции.Дата выдачи")).assertValue()
                .inField("Наименование органа, выдавшего сертификат").inputValue(PROPERTIES.getProperty("Информация о продукции.Наименование органа")).assertValue()
                .inField("Наличие презентационных материалов на английском языке или на языке страны размещения").setCheckboxON().assertCheckboxON()
                .inField("Наличие товарного знака в стране размещения").setCheckboxON().assertCheckboxON()
                .inField("Наименование ЭТП размещения продукции").selectValue(PROPERTIES.getProperty("Информация о продукции.Наименование ЭТП размещения продукции"))
                .inField("Данные дистрибьютора на рынке павильона").inputValue(PROPERTIES.getProperty("Информация о продукции.Данные дистрибьютора на рынке павильона")).assertValue()
//                .clickByLocator("//*[text()='Производитель']/preceding::div[@class='Radio_checkMark__18knp']")
                .clickButton("Добавить")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Подписать электронной подписью']");

    }

    @Step("Подписание заявки")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее");
        closeWebDriver();
    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh()");
            CommonFunctions.wait(1);
        }
    }
}

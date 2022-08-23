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

public class Test_04_07_02 extends Hooks {

//    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_021/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_02_properties.xml";
//    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Андрей Теребков")
    @Description("04 07 02 Внесение Клиентом изменений в состав сведений соглашения об объемах и номенклатуре продукции")
    @Link(name = "Test_04_07_02", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302518")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        step01();
        step02();
        step03();
        step04();
    }

//    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot(WAY_TEST + "screen.png");
//    }

    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();

//        open(PROPERTIES.getProperty("start_URL"));

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=pavilion&serviceId=a546931c-0eb9-4545-853a-8a683c0924f7&next_query=true");
        new GUIFunctions()
                .authorization("pavilion_exporter_top1@otr.ru", "Password1!", "1234");

        refreshTab("//*[text()='Продолжить']", 10);
        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Страна нахождения павильона']")
                .inContainer("Сведения о демонстрационно-дегустационном павильоне")
                .inField("Страна нахождения павильона").selectValue("Китайская Народная Республика")
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
                .inField("Комментарий").inputValue("Дополнительные сведения");
    }

    @Step("Информация о продукции")
    public void step03() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Добавить +");
        new GUIFunctions().inContainer("Сведения о продукции")
                .inField("Каталог продукции").inputValue("1704")
                .waitForElementDisplayed("//*[contains(text(), 'Белёвская пастила с чёрной смородиной')]")
                .clickByLocator("//*[contains(text(), 'Белёвская пастила с чёрной смородиной')]");

        new GUIFunctions().inField("Количество ед. продукции").inputValue("12")
                .inField("Единица измерения").selectValue("мм")
                .inField("Общая стоимость партии товара, включая затраты на транспортировку (китайский юань)").inputValue("25000")
                .inField("Условия транспортировки и хранения продукции").inputValue("Условия хранения")
                .inField("Розничная продажа").setCheckboxON()
                .inField("Оптовая продажа").setCheckboxON()
                .inField("Оценка спроса (в рублях)").inputValue("15000")
                .inField("Готовность к адаптации").setCheckboxON()
                .inField("Наличие сертификата страны размещения").setCheckboxON()
                .inField("Номер сертификата").inputValue("12345")
                .inField("Дата выдачи").inputValue("01.07.2022")
                .inField("Наименование органа, выдавшего сертификат").inputValue("Наименование")
                .inField("Наличие презентационных материалов на английском языке или на языке страны размещения").setCheckboxON()
                .inField("Наличие товарного знака в стране размещения").setCheckboxON()
                .inField("Наименование ЭТП размещения продукции").selectValue("Umico")
                .inField("Данные дистрибьютора на рынке павильона").inputValue("Данные")
                .inField("Номер декларации о соответствии").inputValue("11111")
                .inField("Номер сертификата соответствия").inputValue("2222")
                .clickButton("Добавить")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Подписать электронной подписью']");

    }

    @Step("Подписание заявки")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022")
                .clickButton("Подписать")
                .clickButton("Далее");
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

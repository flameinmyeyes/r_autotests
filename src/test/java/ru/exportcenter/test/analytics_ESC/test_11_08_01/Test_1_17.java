package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import ru.exportcenter.test.finplatforma.Test_08_10_02;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_1_17 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/analytics_ESC/Test_03_07_01_1/";

    public String requestNumber;

    @Owner(value="Петрищев Руслан")
    @Description("***")
    @Link(name="Test_1_17", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException, AWTException {
        step01();
    }

    @Step("Авторизация")
    public void step01() throws AWTException {

        System.out.println("Шаг 1,2");
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=Process_test_search_prod&serviceId=faf15b0f-2346-4353-b417-fb695bc26aef&next_query=true");
        new GUIFunctions()
                .authorization("demo_exporter", "password", "1234");

        for (int i = 0; i < 180; i++) {
            if (!$x("//*[text()='Поиск потенциальных иностранных покупателей']").isDisplayed()){
                CommonFunctions.wait(1);
            }else {
                break;
            }
        }

        System.out.println("Шаг 3");
        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        JupyterLabIntegration.uploadTextContent(requestNumber, WAY_TEST, "requestNumber.txt");
        System.out.println(requestNumber);
        refreshTab("//*[text()='Продолжить']", 10);
        new GUIFunctions().clickButton("Продолжить");

        System.out.println("Шаг 4");
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Описание продукции, планируемой на экспорт']");
        CommonFunctions.wait(1);

        System.out.println("Шаг 5");

        inputValueInField("Описание продукции, планируемой на экспорт", "1");

        $x("//span[text()='ТНВЭД']/ancestor::div[contains(@class,'Radio_container')]/div[contains(@class,'Radio_check')]").click();
        $x("//*[text()='Классификация продукции - код ТНВЭД']//ancestor::div[contains(@class,'Dropdown')]//following::input").setValue("5512110000");
        new GUIFunctions().waitForElementDisplayed("//*[contains(text(),'М2-ткани неотбеленные или отбеленные')]")
                .clickByLocator("//*[contains(text(),'М2-ткани неотбеленные или отбеленные')]");

        new GUIFunctions()
                .inField("Сфера применения продукции").selectValue("Товары народного потребления");
        CommonFunctions.wait(1);
        new GUIFunctions().inField("Целевая страна экспорта").selectValue("Республика Азербайджан");
        CommonFunctions.wait(1);
        new GUIFunctions().inField("Осуществлялись ли меры по охране или защите интеллектуальной собственности на целевых рынках?").selectValue("Нет, не требуется");
        CommonFunctions.wait(1);
        inputValueInField("Портрет потенциальных покупателей продукции", "1");

        System.out.println("Шаг 6");
        new GUIFunctions().clickButton("Далее")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='1. Экспортер']");

        System.out.println("Шаг 7");
        new GUIFunctions()
                .inContainer("1. Экспортер")
                .inField("Опыт экспортной деятельности").selectValue("Нет")
                .inField("Наличие сайта компании").selectValue("Нет")
                .inField("Наличие иностранной версии сайта").selectValue("Нет")
                .inField("Наличие специализированного сотрудника ВЭД в компании").selectValue("Нет");

        System.out.println("Шаг 8");
        new GUIFunctions().clickButton("2. Готовность к экспорту")
                .inContainer("2. Готовность к экспорту")
                .inField("Международная сертификация продукции/производства").selectValue("Сертификаты отсутствуют/потребность в них неизвестна")
                .inField("Опыт участия в международных выставках, бизнес-миссиях, конференциях за рубежом").selectValue("Нет")
                .inField("Наличие презентационных материалов и коммерческого предложения на официальном языке страны").selectValue("Нет")
                .inField("Наличие информации о таможенных барьерах (пошлины, квоты, лицензии, запреты)").selectValue("Нет")
                .inField("Необходимость адаптации экспортного продукта к поставке в целевую страну (маркировка, упаковка, наличие адаптированных сопровождающих)").selectValue("Уже адаптирован/Не требуется");

        System.out.println("Шаг 9");
        new GUIFunctions().clickButton("3. Перспектива")
                .inContainer("3. Перспектива")
                .inField("Наличие проявленного интереса со стороны потенциальных партнеров к продукции").selectValue("Нет")
                .inField("Наличие импорта аналогичной продукции из России в страну").selectValue("Нет")
                .inField("Уровень тарифных барьеров").selectValue("0-10%")
                .inField("Наличие нетарифных барьеров (квоты, сертификации, лицензирование)").selectValue("Нет");

        System.out.println("Шаг 10");
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Презентация компании и продукции']");

        System.out.println("Шаг 11");
//        new GUIFunctions().uploadFile("Презентация компании и продукции", "");

        System.out.println("Шаг 12");
        $x("//*[text()='Далее']").scrollTo();
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("(//input[@name='iHaveSign']/following-sibling::div)[3]");

        System.out.println("Шаг 13");
        $x("(//input[@name='iHaveSign']/following-sibling::div)[3]").click();

        System.out.println("Шаг 14");
        new GUIFunctions().uploadFile("2. Если информация в автоматически сформированной Анкете корректна, распечатайте Анкету, подпишите, отсканируйте и приложите скан-копию Анкеты в поле \"Загрузите документ\". Если информация в Анкете не верна, вернитесь назад и исправьте введенную информацию", "C:\\auto-tests\\АнкетаS_2022_184971.pdf");
//        new GUIFunctions().uploadFile("2. Если информация в автоматически сформированной Анкете корректна, распечатайте Анкету, подпишите, отсканируйте и приложите скан-копию Анкеты в поле \"Загрузите документ\". Если информация в Анкете не верна, вернитесь назад и исправьте введенную информацию", "/share/" + WAY_TEST + "АнкетаS_2022_184971.pdf");
        System.out.println("Шаг 15");
        new GUIFunctions().inField("Настоящим компания подтверждает, что ознакомлена и согласна с Правилами предоставления АО «Российский экспортный центр» услуги «Поиск потенциальных иностранных покупателей, включая предварительный контакт и проверку интереса».").setCheckboxON()
                .inField("Настоящим компания подтверждает, что данная анкета рассматривается в качестве оферты о заключении с АО «Российский экспортный центр» соглашения в электронной форме об оказании услуги «Поиск потенциальных иностранных покупателей, включая предварительный контакт и проверку интереса» в порядке и на условиях, предусмотренных Правилами предоставления АО «Российский экспортный центр» услуги «Поиск потенциальных иностранных покупателей, включая предварительный контакт и проверку интереса».").setCheckboxON();
        CommonFunctions.wait(2);

        System.out.println("Шаг 16");
        new GUIFunctions().clickButton("Завершить")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");

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

    public void inputValueInField(String field, String value) {
        $x("//*[text() = '" + field + "']//ancestor::div[contains(@class,'TextInput')]//following::textarea").setValue(value);
    }

    public void selectValueInField(String field, String value, String shortText) {
        $x("//*[text()='" + field + "']//ancestor::div[contains(@class,'Dropdown')]//following::input").setValue(shortText);
        new GUIFunctions().waitForElementDisplayed("//*[contains(text(), '" + value + "')]");
        $x("//*[contains(text(), '" + value + "')]").click();
    }
}

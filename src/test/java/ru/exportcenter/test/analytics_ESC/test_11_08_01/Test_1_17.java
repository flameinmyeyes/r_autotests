package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import functions.common.CommonFunctions;
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

import static com.codeborne.selenide.Selenide.*;

public class Test_1_17 extends Hooks {

    public String requestNumber;

    @Owner(value="***")
    @Description("***")
    @Link(name="Test_1_17", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
//        step02();
    }

    public void step02() throws InterruptedException, AWTException {

        Test_18_36 test_18_36 = new Test_18_36();
        test_18_36.steps();
    }

    @Step("Авторизация")
    public void step01(){

        System.out.println("Шаг 1,2");
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=Process_test_search_prod&serviceId=faf15b0f-2346-4353-b417-fb695bc26aef&next_query=true");
        new GUIFunctions()
                .authorization("demo_exporter", "password")
                .waitForElementDisplayed("//*[text()='Поиск потенциальных иностранных покупателей']");

        System.out.println("Шаг 3");
        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        System.out.println(requestNumber);
        refreshTab("//*[text()='Продолжить']", 10);
        new GUIFunctions().clickButton("Продолжить");

        System.out.println("Шаг 4");
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Описание продукции, планируемой на экспорт']");
        CommonFunctions.wait(1);

        System.out.println("Шаг 5");

        inputValueInField("Описание продукции, планируемой на экспорт", "1");
        $x("//*[text()='Классификация продукции - код ТНВЭД']//ancestor::div[contains(@class,'Dropdown')]//following::input").setValue("5512110000");
//        CommonFunctions.wait(1);
//        $x("//div[@class='KrDropdown_wrapper__338B1 KrDropdown_active__1Fofx']//input[1]").click();
        CommonFunctions.wait(10);
//        selectValueInField("Классификация продукции - код ТНВЭД","5512110000 Ткани неотбеленные или отбеленные, содержащие 85 мас.% или более полиэфирных волокон ", "5512110000");
//        new GUIFunctions().inField("Классификация продукции - код ТНВЭД").selectValue("5512110000 Ткани неотбеленные или отбеленные, содержащие 85 мас.% или более полиэфирных волокон ");
        new GUIFunctions()
//                .inField("Классификация продукции – код ТНВЭД").selectValue("5512110000 Ткани неотбеленные или отбеленные, содержащие 85 мас.% или более полиэфирных волокон ")
                .inField("Сфера применения продукции").selectValue("Товары народного потребления")
                .waitForLoading();
        CommonFunctions.wait(1);
        new GUIFunctions().inField("Целевая страна экспорта").selectValue("Республика Азербайджан")
                .waitForLoading();
        CommonFunctions.wait(1);
        new GUIFunctions().inField("Осуществлялись ли меры по охране или защите интеллектуальной собственности на целевых рынках?").selectValue("Нет, не требуется")
                .waitForLoading();
        CommonFunctions.wait(1);
        inputValueInField("Портрет потенциальных покупателей продукции", "1");

        System.out.println("Шаг 6");
        new GUIFunctions().clickButton("Далее")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='1. Экспортер']");

        System.out.println("Шаг 7");
        new GUIFunctions().clickButton("1. Экспортер")
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

        System.out.println("Шаг 15");
        new GUIFunctions().inField("Настоящим компания подтверждает, что ознакомлена и согласна с Правилами предоставления АО «Российский экспортный центр» услуги «Поиск потенциальных иностранных покупателей, включая предварительный контакт и проверку интереса».").setCheckboxON()
                .inField("Настоящим компания подтверждает, что данная анкета рассматривается в качестве оферты о заключении с АО «Российский экспортный центр» соглашения в электронной форме об оказании услуги «Поиск потенциальных иностранных покупателей, включая предварительный контакт и проверку интереса» в порядке и на условиях, предусмотренных Правилами предоставления АО «Российский экспортный центр» услуги «Поиск потенциальных иностранных покупателей, включая предварительный контакт и проверку интереса».").setCheckboxON();
        CommonFunctions.wait(2);

        System.out.println("Шаг 16");
        new GUIFunctions().clickButton("Завершить")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
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
//        new GUIFunctions().waitForLoading();
//        $x("//*[text()='" + value + "']").click();
    }
}

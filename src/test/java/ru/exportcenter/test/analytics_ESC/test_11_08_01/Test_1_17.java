package ru.exportcenter.test.analytics_ESC.test_11_08_01;

import framework.RunTestAgain;
import functions.common.CommonFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import static com.codeborne.selenide.Selenide.*;

public class Test_1_17 extends Hooks {

    @Owner(value="***")
    @Description("***")
    @Link(name="Test_11_08_01", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=138814859")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws InterruptedException {
        step01();
    }

    @Step("Авторизация")
    public void step01(){
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/ru/promo-service?key=Process_test_search_prod&serviceId=faf15b0f-2346-4353-b417-fb695bc26aef&next_query=true");

        //Ввести логин и пароль
        new GUIFunctions()
                .authorization("demo_exporter", "password")
                .waitForElementDisplayed("//*[text()='Продолжить']");

        //3
        refreshTab("//*[text()='Продолжить']", 10);

        //4
        new GUIFunctions().clickButton("Далее");

        //5
        new GUIFunctions().inField("Описание продукции, планируемой на экспорт").inputValue("1")
                .inField("Классификация продукции – код ТНВЭД").selectValue("5512110000")
                .inField("Сфера применения продукции").selectValue("Товары народного потребления")
                .inField("Целевая страна экспорта").selectValue("Республика Азербайджан")
                .inField("Осуществлялись ли меры по охране или защите интеллектуальной собственности на целевых рынках").selectValue("Нет, не требуется")
                .inField("Портрет потенциальных покупателей продукции").inputValue("1");

        //6
        new GUIFunctions().clickButton("Далее");

        //7
        new GUIFunctions().clickButton("1. Экспортер")
                .inContainer("1. Экспортер")
                .inField("Опыт экспертной деятельности").selectValue("Нет")
                .inField("Наличие сайта компании").selectValue("Нет")
                .inField("Наличие иностранной версии сайта").selectValue("Нет")
                .inField("Наличие специализированного сотрудника ВЭД в компании").selectValue("Нет");

        //8
        new GUIFunctions().clickButton("2. Готовность к экспорту")
                .inContainer("2. Готовность к экспорту")
                .inField("Международная сертификация продукции/производства").selectValue("Сертификаты отсутствуют / потребность в них неизвестна")
                .inField("Опыт участия в международных выставках, бизнес-миссиях, конференциях за рубежом").selectValue("Нет")
                .inField("Наличие презентационных материалов и коммерческого предложения на официальном языке страны").selectValue("Нет")
                .inField("Наличие информации о таможенных барьерах (пошлины, квоты, лицензии, запреты)").selectValue("Нет")
                .inField("Необходимость адаптации экспортного продукта к поставке в целевую страну (маркировка, упаковка, наличие адаптированных сопровождающих)").selectValue("Уже адаптирован / Не требуется");

        //9
        new GUIFunctions().clickButton("3. Перспектива")
                .inContainer("3. Перспектива")
                .inField("Наличие проявленного интереса со стороны потенциальных партнеров к продукции").selectValue("Нет")
                .inField("Наличие импорта аналогичной продукции из России в страну").selectValue("Нет")
                .inField("Уровень тарифных барьеров").selectValue("0-10%")
                .inField("Наличие нетарифных барьеров (квоты, сертификации, лицензирование)").selectValue("Нет");

        //10
        new GUIFunctions().clickButton("Далее");

        //11
        new GUIFunctions().uploadFile("Презентация компании и продукции", "");

        //12
        new GUIFunctions().clickButton("Далее");
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

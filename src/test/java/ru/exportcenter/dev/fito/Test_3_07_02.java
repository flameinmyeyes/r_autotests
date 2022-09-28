package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import functions.api.RESTFunctions;
import functions.api.SQLFunctions;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import functions.file.FileFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_3_07_02 extends Hooks {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/Test_3_07_02/";
    public String WAY_TO_PROPERTIES = Ways.DEV.getWay() + "/fito/Test_3_07_02/" + "Test_3_07_02_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Балашов Илья")
    @Description("3.07.02 Сценарий с отсутствием типа документа в списке \"Тип документа о происхождении груза\"")
    @Link(name = "Test_3_07_02", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308873")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step05();
        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    public void precondition() {
        //Предусловие: выполнить шаги 1-4 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_3_07_01 test_3_07_01 = new Test_3_07_01();
        test_3_07_01.WAY_TEST = this.WAY_TEST;
        test_3_07_01.step01();
        test_3_07_01.step02();
        test_3_07_01.step03();
        test_3_07_01.step04();
    }

    @Step("Блок  \"Условия поставки\"")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Условия поставки")
                    //Условия транспортировки
                    .inField("Вид транспорта").selectValue(P.getProperty("Условия поставки.Вид транспорта")).assertNoControl().assertValue()
                    .inField("Страна назначения").selectValue(P.getProperty("Условия поставки.Страна назначения")).assertNoControl().assertValue()
                    .inField("Пункт ввоза в стране назначения").inputValue(P.getProperty("Условия поставки.Пункт ввоза в стране назначения")).assertNoControl().assertValue()
                    //Документы на груз
                    .inField("Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»").selectValue(P.getProperty("Условия поставки.Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»")).assertNoControl().assertValue()
                    .inField("Наименование документа о происхождении груза").inputValue(P.getProperty("Условия поставки.Наименование документа о происхождении груза")).assertNoControl().assertValue()
                    .inField("Номер документа на груз").inputValue(P.getProperty("Условия поставки.Номер документа на груз")).assertNoControl().assertValue()
                    .inField("Дата").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue();
    }

    @Step("Шаг 6. Отмена заявки пользователем")
    public void step06() {
        CommonFunctions.printStep();

        //костыль: нужно проскроллить и кликать, пока не появится нужное всплывающее меню:
        new GUIFunctions().scrollTo("Запрос заключения о карантинном фитосанитарном состоянии");
        for(int i = 0; i < 10; i++) {
            if(!$x("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']/parent::div/following-sibling::div//li[text()='Отменить заявку']").isDisplayed()) {
                new GUIFunctions()
                        .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']/parent::div/following-sibling::div/button[@class='dropdown-icon']")
                        .waitForLoading();
                CommonFunctions.wait(1);
            }
        }

        new GUIFunctions()
                .clickByLocator("//span[text()='Запрос заключения о карантинном фитосанитарном состоянии']/parent::div/following-sibling::div//li[text()='Отменить заявку']")
                .waitForLoading();

        //pop-up уведомление о подтверждении удаления
        if($x("//div//button[@title='Да']").isDisplayed()) {
            new GUIFunctions().clickByLocator("//div//button[@title='Да']");
        }

        new GUIFunctions().waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

}

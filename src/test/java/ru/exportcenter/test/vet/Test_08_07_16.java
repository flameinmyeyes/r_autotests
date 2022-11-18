package ru.exportcenter.test.vet;

import com.codeborne.selenide.Selenide;
import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
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

import java.io.File;
import java.util.Properties;

public class Test_08_07_16 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_16/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_16/" + "Test_08_07_16_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.16 Общий отчет")
    @Link(name = "Test_08_07_16", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183200561")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void precondition() {
        //Предусловие: выполнить шаги 1-4 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_08_07_15 test_08_07_15 = new Test_08_07_15();
        test_08_07_15.WAY_TEST = this.WAY_TEST;
        test_08_07_15.steps();

    }

    @Step("Ввод данных в карточку \"Тип услуги\" ")
    public void step01() {
        CommonFunctions.printStep();

        //В поле Выберите тип услуги выбрать Оформить новое разрешение  Нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickByLocator("//a[text()='Сформировать отчет']")
                .waitForLoading()
                .inContainer("Выбор отчёта")
                .waitForElementDisplayed("//span[text()='Общий']");

    }

    @Step("В поле Выбор отчёта, выбираем - Общий ")
    public void step02() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inContainer("Выбор отчёта")
                .clickByLocator("//span[text()='Общий']")
                .waitForLoading()
                .inContainer("Общий отчёт")
                .clickByLocator("//span[text()='Полный']")
                .inField("Дата от").inputValue("01.10.2022")
                .inField("Дата до").inputValue("04.10.2022")
                .inContainer("Формирование отчетности по разрешениям на вывоз подконтрольной продукции")
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Сформированные отчеты']", 360);

    }
    @Step("Скачать и провертиь отчет")
    public void step03() {
        CommonFunctions.printStep();

        //удаляем все файлы в папке downloads
        FileFunc.recursiveDeleteWithoutMainDir(new File(Ways.DOWNLOADS.getWay()));

        //нажать на просмотр общего отчета
        new GUIFunctions()
                .inContainer("Сформированные отчеты")
                .clickByLocator("//div[text()='Общий отчет.pdf']");

        Selenide.sleep(10000);  // после правки бага с зависанием стенда, можно заменить на нормальное ожидание

        System.out.println("done");

        //поиск скачанного файла
        FileFunctions.searchFileInDirectoriesRecursive(new File(Ways.DOWNLOADS.getWay()),"Общий отчет");
        //очищаем папку скачивания
        FileFunc.recursiveDeleteWithoutMainDir(new File(Ways.DOWNLOADS.getWay()));

    }

}

package ru.exportcenter.test.spt;

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
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;


public class Test_07_07_23 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_23/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_23_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;
    private String processID;
    private String docUUID;
    private String token;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.23 Итог СТ-2 на английском языке: экспортер выбрал 1 товар, не подтвержден сертификат на шаге 8, экспортер решил создать новую заявку через кнопку Исправить информацию (Сербия)")
    @Link(name = "Test_07_07_23", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=194314823")
    @Test(retryAnalyzer = RunTestAgain.class)


    public void steps(){
        precondition();
        step01();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition(){
        CommonFunctions.printStep();
        Test_07_07_00 test_07_07_00 = new Test_07_07_00();
        test_07_07_00.url = "https://lk.t.exportcenter.ru/promo-service?key=service-spt&serviceId=dc83e6b2-138f-4a39-99ca-117d8f8a27c8&next_query=true";
        test_07_07_00.login = PROPERTIES.getProperty("Авторизация.Email");
        test_07_07_00.password = PROPERTIES.getProperty("Авторизация.Пароль");
        test_07_07_00.code = PROPERTIES.getProperty("Авторизация.Код");
        test_07_07_00.forma = PROPERTIES.getProperty("Форма");

        test_07_07_00.steps();
        requestNumber = test_07_07_00.requestNumber;
        processID = test_07_07_00.processID;
        docUUID = test_07_07_00.docUUID;
        token = test_07_07_00.token;
    }

    @Step("Ввод данных в карточку «Выдача сертификата о происхождении товара»")
    public void step01() {
        CommonFunctions.printStep();

        Test_07_07_00 test_07_07_00 = new Test_07_07_00();
        String smevFlag = "{\"smevSkip\":true,\"__coment1__\":\"//smevSkip=true//smevAction[positive,negative,techError]\",\"smevAction\":{\"vs1\":\"positive\",\"vs2\":\"positive\",\"vs3\":\"positive\",\"vs4\":\"positive\",\"vs5\":\"positive\",\"vs6\":\"positive\",\"vs7\":\"positive\",\"vs8\":\"negative\",\"vs9\":\"positive\",\"vs9pay\":\"positive\",\"vs9cmnt\":\"positive\",\"vs10\":\"positive\"},\"__comment2__\":\"//smevSkip=false//smevMode[dev,prod]\",\"smevMode\":\"dev\"}";
        test_07_07_00.Swagger(smevFlag);
        System.out.println("requestNumber = " + requestNumber);

//      Информация о заявителе / Информация об импортере
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
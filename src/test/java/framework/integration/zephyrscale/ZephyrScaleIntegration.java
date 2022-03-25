package framework.integration.zephyrscale;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import framework.Ways;
import functions.api.RESTFunctions;
import functions.common.DateFunctions;
import functions.file.JSONEditor;
import net.sf.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ZephyrScaleIntegration {

    public static final String LOGIN = "seledtsov.autotests"; //seledtsov.autotests
    public static final String PASSWORD = "xjJEEnajPah8DxS7"; //xjJEEnajPah8DxS7

    /*
    FAQ по REST API Zephyr Scale:
    https://support.smartbear.com/zephyr-scale-server/api-docs/v1/
     */

    /**
     * Отправить результат выполнения тесткейса в тестран
     */
//    public static void sendTestresultToTestrun(String testcase, String testrun, Map<String, String> params) {
//
//        try {
//            //проверяем, что статус тесткейса - "Автоматизирован". если нет - выходим из метода
//            String testcaseStatus = getTestcaseStatus(testcase);
//            if (!testcaseStatus.equals("Автоматизирован")) {
//                System.out.println("Тесткейс " + testcase + " не на статусе \"Автоматизирован\". Он не будет добавлен в тестран " + testrun);
//                return;
//            }
//
//            //проверяем, присутствует ли тесткейс в тестране. в зависимости от результата, определяем тип запроса (PUT/POST)
//            boolean testcaseIsInTestrun = isTestcaseInTestrun(testcase, testrun);
//            String requestType;
//            if (testcaseIsInTestrun) {
//                requestType = "PUT";
//                System.out.println("Тесткейс " + testcase + " уже присутствует в тестране " + testrun + ". Добавление в тестран не требуется.");
//            } else {
//                requestType = "POST";
//                System.out.println("Тесткейс " + testcase + " отсутствует в тестране " + testrun + ". Он будет добавлен в тестран.");
//            }
//
//            //собираем URl для REST-запроса
//            String requestURL = "https://job-jira.otr.ru/rest/atm/1.0/testrun/" + testrun + "/testcase/" + testcase + "/testresult";
//
//            //строим JSON для REST-запроса
//            String requestBody = JSONEditor.buildSimpleJSON(params);
//
//            //отправляем REST-запрос с результатами прохождения теста
//            RESTFunctions.sendRESTRequest(requestType, requestURL, requestBody, LOGIN, PASSWORD);
//            System.out.println("Результат выполнения тесткейса " + testcase + " успешно передан в тестран " + testrun);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            String failReason = null;
//            if (testcase.equals("")) {
//                failReason = "тесткейс не указан!";
//            }
//            if (testrun.equals("")) {
//                failReason = "тестран не указан!";
//            }
//            System.out.println("Не удалось отправить результат выполнения тесткейса в тестран по причине: " + failReason);
//        }
//
//    }

    /**
     * Отправить результат выполнения тесткейса в тестран
     */
    public static void sendTestresultToTestrun(String testcase, String testrun, JSONObject json) {

        try {
            //проверяем, присутствует ли тесткейс в тестране. в зависимости от результата, определяем тип запроса (PUT/POST)
            boolean testcaseIsInTestrun = isTestcaseInTestrun(testcase, testrun);
            String requestType;
            if (testcaseIsInTestrun) {
                requestType = "PUT";
                System.out.println("Тесткейс " + testcase + " уже присутствует в тестране " + testrun + ". Добавление в тестран не требуется.");
            } else {
                requestType = "POST";
                System.out.println("Тесткейс " + testcase + " отсутствует в тестране " + testrun + ". Он будет добавлен в тестран.");
            }

            //собираем URl для REST-запроса
            String requestURL = "https://job-jira.otr.ru/rest/atm/1.0/testrun/" + testrun + "/testcase/" + testcase + "/testresult";

            //перегоняем JSON в String
            String requestBody = json.toString();

            //отправляем REST-запрос с результатами прохождения теста
            RESTFunctions.sendRESTRequest(requestType, requestURL, requestBody, LOGIN, PASSWORD);
            System.out.println("Результат выполнения тесткейса " + testcase + " успешно передан в тестран " + testrun);

        } catch (Exception e) {
            e.printStackTrace();
            String failReason = null;
            if (testcase.equals("")) {
                failReason = "тесткейс не указан!";
            }
            if (testrun.equals("")) {
                failReason = "тестран не указан!";
            }
            System.out.println("Не удалось отправить результат выполнения тесткейса в тестран по причине: " + failReason);
        }

    }

    /**
     * Отправить файл вложения в результат выполнения тесткейса
     */
    public static void sendAttachmentToTestresult(String testcase, String testrun, File attachmentFile) {

        try {
            //получаем ID результата выполнения теста
            String testResultID = getTestresultIDFromTestrun(testcase, testrun);

            //собираем URl для REST-запроса
            String requestURL = "https://job-jira.otr.ru/rest/atm/1.0/testresult/" + testResultID + "/attachments";

            //отправляем REST-запрос с результатами прохождения теста
            RESTFunctions.sendRESTRequest("POST", requestURL, attachmentFile, LOGIN, PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
            String failReason = null;
            if (testcase.equals("")) {
                failReason = "тесткейс не указан!";
            }
            if (testrun.equals("")) {
                failReason = "тестран не указан!";
            }
            System.out.println("Не удалось отправить файл вложения в результат выполнения тесткейса по причине: " + failReason);
        }

    }

    /**
     * Получить результат выполнения теста из тестрана
     */
    public static String getTestresultFromTestrun(String testcase, String testrun) {

        String testResult = null;

        try {
            //проверяем, присутствует ли тесткейс в тестране. если да, то получаем его статус
            boolean testcaseIsInTestrun = isTestcaseInTestrun(testcase, testrun);
            if (!testcaseIsInTestrun) {
                System.out.println("Тесткейс " + testcase + " отсутствует в тестране " + testrun + ".");
                return null;
            }

            //достаем список всех тесткейсов из тестрана c помощью REST-запроса
            String getRequestURL = "https://job-jira.otr.ru/rest/atm/1.0/testrun/" + testrun;
            String getRequestResponse = RESTFunctions.sendRESTRequest("GET", getRequestURL, LOGIN, PASSWORD);

            //преобразовываем в JSON
            JsonObject json = JSONEditor.parseJSONfromString(getRequestResponse);

            //достаем из него JsonArray с именем items, в котором лежит список тесткейсов
            JsonArray jsonArray = json.getAsJsonArray("items");

            //ищем результат тесткейса в JsonArray
            for (JsonElement jsonElement : jsonArray) {
                String testCaseKey = jsonElement.getAsJsonObject().get("testCaseKey").toString().replace("\"", "");
                if (testCaseKey.equals(testcase)) {
                    testResult = jsonElement.getAsJsonObject().get("status").toString().replace("\"", "");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            String failReason = null;
            if (testcase.equals("")) {
                failReason = "тесткейс не указан!";
            }
            if (testrun.equals("")) {
                failReason = "тестран не указан!";
            }
            System.out.println("Не удалось получить результат выполнения тесткейса из тестрана по причине: " + failReason);
        }

        return testResult;
    }

    /**
     * Получить ID результата выполнения теста из тестрана
     */
    public static String getTestresultIDFromTestrun(String testcase, String testrun) {

        String testResultID = null;

        try {
            //проверяем, присутствует ли тесткейс в тестране. если да, то получаем его статус
            boolean testcaseIsInTestrun = isTestcaseInTestrun(testcase, testrun);
            if (!testcaseIsInTestrun) {
                System.out.println("Тесткейс " + testcase + " отсутствует в тестране " + testrun + ".");
                return null;
            }

            //достаем список всех тесткейсов из тестрана c помощью REST-запроса
            String getRequestURL = "https://job-jira.otr.ru/rest/atm/1.0/testrun/" + testrun;
            String getRequestResponse = RESTFunctions.sendRESTRequest("GET", getRequestURL, LOGIN, PASSWORD);

            //преобразовываем в JSON
            JsonObject json = JSONEditor.parseJSONfromString(getRequestResponse);

            //достаем из него JsonArray с именем items, в котором лежит список тесткейсов
            JsonArray jsonArray = json.getAsJsonArray("items");

            //ищем результат тесткейса в JsonArray
            for (JsonElement jsonElement : jsonArray) {
                String testCaseKey = jsonElement.getAsJsonObject().get("testCaseKey").toString().replace("\"", "");
                if (testCaseKey.equals(testcase)) {
                    testResultID = jsonElement.getAsJsonObject().get("id").toString().replace("\"", "");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            String failReason = null;
            if (testcase.equals("")) {
                failReason = "тесткейс не указан!";
            }
            if (testrun.equals("")) {
                failReason = "тестран не указан!";
            }
            System.out.println("Не удалось получить ID результата выполнения тесткейса из тестрана по причине: " + failReason);
        }

        return testResultID;
    }

    /**
     * Получить статус тесткейса
     */
    public static String getTestcaseStatus(String testcase) {

        String testCaseStatus = null;

        try {
            //получаем тесткейс c помощью REST-запроса
            String getRequestURL = "https://job-jira.otr.ru/rest/atm/1.0/testcase/" + testcase;
            String getRequestResponse = RESTFunctions.sendRESTRequest("GET", getRequestURL, LOGIN, PASSWORD);

            //преобразовываем в JSON
            JsonObject json = JSONEditor.parseJSONfromString(getRequestResponse);

            //получаем статус тесткейса
            testCaseStatus = json.get("status").toString().replace("\"", "");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не удалось получить статус тесткейса!");
        }

        return testCaseStatus;
    }

    /**
     * Получить список связанных с тесткейсом тасков
     */
    public static List<String> getIssuesFromTestcase(String testcase) {

        List<String> issuesList = new ArrayList<>();

        try {
            //получаем тесткейс c помощью REST-запроса
            String getRequestURL = "https://job-jira.otr.ru/rest/atm/1.0/testcase/" + testcase;
            String getRequestResponse = RESTFunctions.sendRESTRequest("GET", getRequestURL, LOGIN, PASSWORD);

            //преобразовываем в JSON
            JsonObject json = JSONEditor.parseJSONfromString(getRequestResponse);

            //достаем из него JsonArray с именем issueLinks, в котором лежит список багрепортов
            JsonArray issueLinks = json.getAsJsonArray("issueLinks");

            //перегоняем из JsonArray в List
            for (JsonElement issueLink : issueLinks) {
                String issueKey = issueLink.toString().replace("\"", "");
                issuesList.add(issueKey);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String failReason = null;
            if (testcase.equals("")) {
                failReason = "тесткейс не указан!";
            }
            System.out.println("Не удалось получить список связанных с тесткейсом тасков по причине: " + failReason);
        }

        return issuesList;
    }

    /**
     * Получить список связанных с тесткейсом тасков из последнего запуска теста
     */
    public static List<String> getIssuesFromLastTestcaseExecution(String testcase) {

        List<String> issuesList = new ArrayList<>();

        try {
            //достаем список всех тесткейсов из тестрана c помощью REST-запроса
            String getRequestURL = "https://job-jira.otr.ru/rest/atm/1.0/testcase/" + testcase + "/testresult/latest";
            String getRequestResponse = RESTFunctions.sendRESTRequest("GET", getRequestURL, LOGIN, PASSWORD);

            //преобразовываем в JSON
            JsonObject json = JSONEditor.parseJSONfromString(getRequestResponse);

            //достаем из него JsonArray с именем issueLinks, в котором лежит список тасков
            JsonArray issueLinks = json.getAsJsonArray("issueLinks");
            System.out.println();

            //перегоняем из JsonArray в List
            for (JsonElement issueLink : issueLinks) {
                String issueKey = issueLink.toString().replace("\"", "");
                issuesList.add(issueKey);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String failReason = null;
            if (testcase.equals("")) {
                failReason = "тесткейс не указан!";
            }
            System.out.println("Не удалось получить список связанных с тесткейсом тасков по причине: " + failReason);
        }

        return issuesList;
    }

    /**
     * Проверить, присутствует ли тесткейс в тестране
     */
    public static boolean isTestcaseInTestrun(String testcase, String testrun) {

        boolean testcaseIsInTestrun = false;

        try {
            //достаем список всех тесткейсов из тестрана c помощью REST-запроса в виде JSON
            String getRequestURL = "https://job-jira.otr.ru/rest/atm/1.0/testrun/" + testrun;
            String getRequestResponse = RESTFunctions.sendRESTRequest("GET", getRequestURL, LOGIN, PASSWORD);

            //преобразовываем в JSON
            JsonObject json = JSONEditor.parseJSONfromString(getRequestResponse);

            //достаем из него JsonArray с именем items, в котором лежит список тесткейсов
            JsonArray jsonArray = json.getAsJsonArray("items");

            //проверяем, присутствует ли наш тесткейс в JsonArray
            testcaseIsInTestrun = false;
            for (JsonElement jsonElement : jsonArray) {
                String testCaseKey = jsonElement.getAsJsonObject().get("testCaseKey").toString().replace("\"", "");
                if (testCaseKey.equals(testcase)) {
                    testcaseIsInTestrun = true;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return testcaseIsInTestrun;
    }

    /**
     * Получить пользователя Jira по тесткейсу
     */
    public static String getJiraUserFromTestcase(String testcase) {

        String jiraUser = null;

        try {
            //получаем тесткейс c помощью REST-запроса
            String getRequestURL = "https://job-jira.otr.ru/rest/atm/1.0/testcase/" + testcase;
            String getRequestResponse = RESTFunctions.sendRESTRequest("GET", getRequestURL, LOGIN, PASSWORD);

            //преобразовываем в JSON
            JsonObject json = JSONEditor.parseJSONfromString(getRequestResponse);

            //получаем пользователя Jira из поля поля "Ответственный ATE"
            jiraUser = json.get("customFields").getAsJsonObject().get("Ответственный ATE").toString().replace("\"", "");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не удалось получить пользователя Jira из тесткейса!");
        }

        return jiraUser;
    }

    /**
     * Получение пользователя Jira по владельцу теста
     */
    @Deprecated
    public static String getJiraUserFromTestOwner(String testOwner) {
        String jiraUser = null;
        if (testOwner.contains("алашов")) jiraUser = "balashov.ilya";
        if (testOwner.contains("амаев")) jiraUser = "kamaev.evgeniy";
        if (testOwner.contains("аверина")) jiraUser = "kaverina.marina";
        if (testOwner.contains("аксимова")) jiraUser = "JIRAUSER53933";
        if (testOwner.contains("ласовец")) jiraUser = "JIRAUSER53931";
        if (testOwner.contains("орожко")) jiraUser = "vorozhko.aleksandr";
        if (testOwner.contains("кубов")) jiraUser = "JIRAUSER53960";
        return jiraUser;
    }

    /**
     * Записать некорректные тесты в файл
     */
    public static void writeIncorrectTestsToFile(Class testClass, String testCase, String testStatus, String jiraUser) {
        if (testCase.equals("")) {
            String text = DateFunctions.dateToday("yyyy-MM-dd HH:mm") + " - " + testClass + " - " + "jiraUser: " + jiraUser + " - " + "testStatus: " + testStatus + "\n";

            try {
                Files.write(Paths.get(Ways.COMMON.getWay() + "incorrectTests.txt"), text.getBytes(), StandardOpenOption.APPEND);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}

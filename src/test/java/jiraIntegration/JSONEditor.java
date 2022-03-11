package jiraIntegration;

import com.google.gson.JsonObject;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class JSONEditor {

    //https://stackoverflow.com/questions/20117148/how-to-create-json-object-using-string

    //{
    //  "status": "Fail",
    //  "environment": "Firefox",
    //  "comment": "The test has failed on some automation tool procedure.",
    //  "assignedTo": "balashov.ilya",
    //  "executedBy": "balashov.ilyao",
    //  "executionTime": 180000,
    //  "actualStartDate": "2021-11-26T12:22:00-0300",
    //  "actualEndDate": "2021-11-26T12:22:00-0300",
    //  "customFields": {
    //    "CI Server": "Bamboo"
    //  },
    //  "issueLinks": ["TSE-76925", "TSE-76264"],
    //  "scriptResults": [
    //    {
    //      "index": 0,
    //      "status": "Fail",
    //      "comment": "This step has failed."
    //    }
    //  ]
    //}

    public static String buildSimpleJSON(Map<String, String> elements) {

        //{
        //  "status": "Fail",
        //  "assignedTo": "vitor.pelizza"
        //}

        //{
        //  "issueLinks": ["JQA-123", "JQA-456"]
        //}

//        {"scriptResults": [
//            {
//              "index": 0,
//              "status": "Fail",
//              "comment": "This step has failed."
//            }
//          ]}

        String message;
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> element : elements.entrySet()) {
            jsonObject.put(element.getKey(), element.getValue());
        }
        message = jsonObject.toString();
        return message;
    }

    @Deprecated
    public static void buildJSONforTestresult() {

        //строим JSON вида:
//        {
//            "status": "Fail",
//                "environment": null,
//                "comment": "Комментарий.",
//                "assignedTo": "balashov.ilya",
//                "executedBy": "balashov.ilya",
//                "executionTime": 180000,
//                "actualStartDate": "2021-11-26T12:22:00-0300",
//                "actualEndDate": "2021-11-26T12:22:00-0300",
//                "customFields": {
//            "CI Server": null
//        },
//            "issueLinks": ["TSE-76925"]
//        }

        String message;

        //основное тело запроса
        JSONObject json = new JSONObject();
        json.put("status", "Fail");
        json.put("environment", "Firefox");
        json.put("comment", "The test has failed on some automation tool procedure.");
        json.put("assignedTo", "vitor.pelizza");
        json.put("executedBy", "cristiano.caetano");
        json.put("executionTime", "180000");
        json.put("actualStartDate", "2016-02-14T19:22:00-0300");
        json.put("actualEndDate", "2016-02-15T19:22:00-0300");

        //кастомное поле
        JSONObject customFields = new JSONObject();
        customFields.put("CI Server", "Bamboo");
        json.put("customFields", customFields);

        //связанные баги
        JSONArray issueLinks = new JSONArray();
        issueLinks.add("JQA-123");
        issueLinks.add("JQA-456");
        json.put("issueLinks", issueLinks);

        //шаги теста
        JSONArray scriptResults = new JSONArray();
        JSONObject scriptResults_obj = new JSONObject();
        scriptResults_obj.put("index", 0);
        scriptResults_obj.put("status", "Fail");
        scriptResults_obj.put("comment", "This step has failed.");
        scriptResults.add(scriptResults_obj);
        json.put("scriptResults", scriptResults);

        message = json.toString();

        System.out.println("message: " + message);
    }

    public static JsonObject parseJSONfromString(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) jsonParser.parse(jsonString);
        } catch (Exception e) {
//            System.out.println("Не удалось распарсить указанное значение в JSON!");
        }
        return jsonObject;
    }

//    @Deprecated
//    public static String buildJSON(String incomingJSONString) {
//        String message;
//        JSONObject json = new JSONObject();
//
//        json.put("test1", "value1");
//
//        JSONObject jsonObj = new JSONObject();
//
//        jsonObj.put("id", 0);
//        jsonObj.put("name", "testName");
//        json.put("test2", jsonObj);
//
//        message = json.toString();
//        System.out.println(message);
//
//
//
//        //{"course":[{"id":3,"information":"test","name":"course1"}],"name":"student"}
//        JSONObject json = new JSONObject();
//        json.put("name", "student");
//
//        JSONArray array = new JSONArray();
//        JSONObject item = new JSONObject();
//        item.put("information", "test");
//        item.put("id", 3);
//        item.put("name", "course1");
////        array.put(item);
//        array.add(item);
//
//        json.put("course", array);
//
//        message = json.toString();
//
//        return message;
//    }

}

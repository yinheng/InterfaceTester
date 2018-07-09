package com.yinheng.interfacetester.runner;

import com.google.gson.*;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.util.TextUtils;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 尹恒 on 2018/7/9.
 */
public class ResponseParser {

    public void parseResponse(TestCase testCase, String reponse) throws OutPutKeyNotFoundException {
        JsonElement jsonElement = new JsonParser().parse(reponse);
        boolean isJsonArray = reponse.startsWith("[{") && reponse.endsWith("}]");
        List<Map<String, String>> reponseList = new ArrayList<Map<String, String>>();

        if(isJsonArray) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for(int i = 0; i < jsonArray.size(); i ++) {
                JsonObject jsonObject= jsonArray.get(i).getAsJsonObject();
                Map<String, String> subMap = parseJsonObject(testCase, jsonObject);
                reponseList.add(subMap);
            }
            testCase.setResponseList(reponseList);

        }else {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Map<String, String> reponseMap = parseJsonObject(testCase, jsonObject);
            reponseList.add(reponseMap);
            testCase.setResponseList(reponseList);
        }

        parseOutput(testCase);


    }

    public Map<String, String> parseJsonObject(TestCase testCase, JsonObject jsonObject) {
        Object[] keyArray = jsonObject.keySet().toArray();

        Map<String, String> responseMap = new HashMap<String, String>();
        for (Object key : keyArray) {
            String keyStr = key.toString();
            LogManager.getLogger().debug("key: " + key.toString() + " value: " + jsonObject.get(keyStr).getAsString());
            responseMap.put(keyStr, jsonObject.get(keyStr).getAsString());
        }
        return responseMap;
    }

    //遍历responseList的每个map,找到第一个map里存在outputKey，就不再遍历剩下的map
    public void parseOutput(TestCase testCase) throws OutPutKeyNotFoundException {
        String output = testCase.getOutput();

        if (!TextUtils.isEmpty(output)) {
            String[] outputArray = output.split(",");
            Map<String, String> outPutMap = new HashMap<String, String>();

            for(String outputKey: outputArray) {
                for(Map<String, String> responseMap: testCase.getResponseList()) {
                    if (responseMap.containsKey(outputKey)) {
                        outPutMap.put(outputKey, responseMap.get(outputKey));
                        break;
                    }
                }
            }
            if(outPutMap == null) {
                throw new OutPutKeyNotFoundException("outPutKey not found in responseMap");
            }else {
                testCase.setOutputMap(outPutMap);
            }
        }
    }
}

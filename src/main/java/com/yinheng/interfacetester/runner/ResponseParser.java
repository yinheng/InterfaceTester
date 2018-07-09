package com.yinheng.interfacetester.runner;

import com.google.gson.*;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.util.TextUtils;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 尹恒 on 2018/7/9.
 */
public class ResponseParser {

    public void parseResponse(TestCase testCase, String reponse) throws OutPutKeyNotFoundException {
        JsonElement jsonElement = new JsonParser().parse(reponse);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Object[] keyArray = jsonObject.keySet().toArray();

        Map<String, String> responseMap = new HashMap<String, String>();
        for (Object key : keyArray) {
            String keyStr = key.toString();
            LogManager.getLogger().debug("key: " + key.toString() + " value: " + jsonObject.get(keyStr).getAsString());
            responseMap.put(keyStr, jsonObject.get(keyStr).getAsString());
        }
        testCase.setResponseMap(responseMap);

        String outputKey = testCase.getOutput();
        if (!TextUtils.isEmpty(outputKey)) {
            if (responseMap.containsKey(outputKey)) {
                Map<String, String> outPutMap = new HashMap<String, String>();
                outPutMap.put(outputKey, responseMap.get(outputKey));
                testCase.setOutputMap(outPutMap);
                LogManager.getLogger().debug("outputKey: " + testCase.getOutputMap().get(outputKey));

            } else {
                throw new OutPutKeyNotFoundException("outPutKey not found in responseMap");
            }

        }
    }
}

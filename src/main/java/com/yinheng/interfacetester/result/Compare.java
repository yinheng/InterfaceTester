package com.yinheng.interfacetester.result;

import com.yinheng.interfacetester.data.model.Result;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.util.TextUtils;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compare {

    public void compareResult(TestCase testCase) {
        // All empty.
        if (TextUtils.isEmpty(testCase.getResponse())
                && TextUtils.isEmpty(testCase.getExpectedData())) {
            testCase.setResult(Result.SUCCESS);
            // Contains.
        } else if(!TextUtils.isEmpty(testCase.getResponse())
                && !TextUtils.isEmpty(testCase.getExpectedData())){
            // id:100,name:test
            String[] expectedArray = testCase.getExpectedData().split(",");
            List<Map<String, String>> expectedList = new ArrayList<Map<String, String>>();

            List<Map<String, String>> responseList = new ArrayList<Map<String, String>>();
            responseList = testCase.getResponseList();
            Boolean isSuccess = false;

            for(String subExpected: expectedArray) {

                if(!TextUtils.isEmpty(subExpected)) {
                    Map<String, String> expectedMap = new HashMap<String, String>();
                    String[] strArray = subExpected.split(":");
                    String key = strArray[0].trim();
                    String value = strArray[1].trim();

                    for(Map<String, String> responseMap: responseList) {
                        if(responseMap.containsKey(key)) {
                            if(responseMap.get(key).equals(value)) {
                                isSuccess = true;
                                break;
                            }
                        }
                    }
                    if(isSuccess) {
                        testCase.setResult(Result.SUCCESS);
                    }else {
                        testCase.setResult(Result.FAILURE);
                    }
                }
            }
        }else {
            testCase.setResult(Result.FAILURE);
        }
        LogManager.getLogger().debug("Result: " + testCase.getResult());
        //LogManager.getLogger().debug("compare: " + "response: " + testCase.getResponse() + " expactData: " + testCase.getExpectedData());
    }

    public void stringCompare(TestCase testCase) {
        if (!TextUtils.isEmpty(testCase.getResponse())
                && testCase.getResponse().contains(testCase.getExpectedData())) {
            testCase.setResult(Result.SUCCESS);
        } else {
            testCase.setResult(Result.FAILURE);
        }
    }
}

package com.yinheng.interfacetester.result;

import com.yinheng.interfacetester.data.model.Result;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.util.TextUtils;
import org.apache.logging.log4j.LogManager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

public class Compare {

    public void compareResult(TestCase testCase) throws IOException {
        // All empty.
        if (TextUtils.isEmpty(testCase.getResponse())
                && TextUtils.isEmpty(testCase.getExpectedData())) {
            testCase.setResult(Result.SUCCESS);
            // Contains.
        } else if(!TextUtils.isEmpty(testCase.getResponse())
                && !TextUtils.isEmpty(testCase.getExpectedData())){
            // expacted : id:100,name:test
            //responseList : [
            //{id:101, name: test1, type: 1},
            //{id:100, name: test, type: 2}
            // ]

            List<Map<String, String>> responseList = testCase.getResponseList();
            boolean isSuccess = true;

            BufferedReader br = new BufferedReader(new StringReader(testCase.getExpectedData()));
            String str;

            while ((str = br.readLine()) != null) {
                String[] strArray = str.split(":", 2);
                String key = strArray[0].trim();
                String value = strArray[1].trim();

                if(!eachLineCompare(responseList, key, value)) {
                    isSuccess = false;
                    break;
                }
            }

            if(isSuccess) {
                testCase.setResult(Result.SUCCESS);
            }else {
                testCase.setResult(Result.FAILURE);
            }

        }else {
            testCase.setResult(Result.FAILURE);
        }
        LogManager.getLogger().debug("Result: " + testCase.getResult());
        //LogManager.getLogger().debug("compare: " + "response: " + testCase.getResponse() + " expactData: " + testCase.getExpectedData());
    }

    private void stringCompare(TestCase testCase) {
        if (!TextUtils.isEmpty(testCase.getResponse())
                && testCase.getResponse().contains(testCase.getExpectedData())) {
            testCase.setResult(Result.SUCCESS);
        } else {
            testCase.setResult(Result.FAILURE);
        }
    }

    private boolean eachLineCompare(List<Map<String, String>> responseList, String key, String value) {
        for(Map<String, String> responseMap: responseList) {
            if(responseMap.containsKey(key)) {
                if(responseMap.get(key).equals(value)) {
                    return true;
                }
            }
        }
        return  false;
    }
}

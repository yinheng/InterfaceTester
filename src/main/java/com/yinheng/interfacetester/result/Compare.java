package com.yinheng.interfacetester.result;

import com.yinheng.interfacetester.data.model.Result;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.util.TextUtils;
import org.apache.logging.log4j.LogManager;

public class Compare {

    public void compareResult(TestCase testCase) {
        // All empty.
        if (TextUtils.isEmpty(testCase.getResponse())
                && TextUtils.isEmpty(testCase.getExpectedData())) {
            testCase.setResult(Result.SUCCESS);
            // Contains.
        } else if (!TextUtils.isEmpty(testCase.getResponse())
                && testCase.getResponse().contains(testCase.getExpectedData())) {
            testCase.setResult(Result.SUCCESS);
        } else {
            testCase.setResult(Result.FAILURE);
        }
        LogManager.getLogger().debug("Result: " + testCase.getResult());
        //LogManager.getLogger().debug("compare: " + "response: " + testCase.getResponse() + " expactData: " + testCase.getExpectedData());
    }
}

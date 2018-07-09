package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.sql.SQLException;


public class TestCaseRunner {

    public void runCase(TestCase testCase) throws Exception {
        LogManager.getLogger().debug("run case " + testCase.getTestName());

        // 1. Connect target with param.
        // 2. Get response.
        // 3. Set response to test case.

        // HTTP.

        TestCaseRequester testCaseRequester = testCase.getRequestType().newCaseRequester();
        testCase.setResponse(testCaseRequester.getResponse(testCase));

        LogManager.getLogger().debug("runCase response: " + testCase.getResponse());
    }
}

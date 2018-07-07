package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;
import org.apache.logging.log4j.LogManager;


public class TestCaseRunner {

    public void runCase(TestCase testCase) {
        LogManager.getLogger().debug("run case " + testCase.getTestName());
    }
}

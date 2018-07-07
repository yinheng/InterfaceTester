package com.yinheng.interfacetester;

import com.yinheng.interfacetester.data.TestData;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.result.Compare;
import com.yinheng.interfacetester.runner.ResponseFailException;
import com.yinheng.interfacetester.runner.TestCaseRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class InterfaceTester {

    private final Logger mLogger = LogManager.getLogger();

    private List<TestCase> mTestCases;

    @BeforeTest
    @Parameters({"path", "debug"})
    public void readData(String path, boolean debug) throws IOException, InvalidFormatException {
        BuildConfig.DEBUG = debug;

        TestData testData = new TestData();
        mTestCases = testData.readExcel(path);
        mLogger.debug("readData");
    }

    @Test
    public void run() throws IOException, ResponseFailException {
        for (TestCase testCase : mTestCases) {
            TestCaseRunner testCaseRunner = new TestCaseRunner();
            testCaseRunner.runCase(testCase);
            Compare compare = new Compare();
            compare.compareResult(testCase);
        }
        mLogger.debug("run");
    }

    @AfterTest
    public void report() {
        mLogger.debug("report");
    }
}

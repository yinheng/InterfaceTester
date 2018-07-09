package com.yinheng.interfacetester;

import com.yinheng.interfacetester.data.TestData;
import com.yinheng.interfacetester.data.model.ResultStatistics;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.report.ChartGenerator;
import com.yinheng.interfacetester.report.ExcelOprationFailException;
import com.yinheng.interfacetester.report.ResultStatisticsCreator;
import com.yinheng.interfacetester.report.SetValueToExcel;
import com.yinheng.interfacetester.result.Compare;
import com.yinheng.interfacetester.runner.ResponseFailException;
import com.yinheng.interfacetester.runner.TestCaseRunner;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class InterfaceTester {

    private List<TestCase> mTestCases;

    @BeforeTest
    @Parameters({"path", "debug"})
    public void readData(String path, boolean debug) throws IOException, InvalidFormatException {
        BuildConfig.DEBUG = debug;

        TestData testData = new TestData();
        mTestCases = testData.readExcel(path);
    }

    @Test(dataProvider = "testcaseProvider")
    public void run(TestCase testCase) throws Exception {
        TestCaseRunner testCaseRunner = new TestCaseRunner();
        testCaseRunner.runCase(testCase);
        Compare compare = new Compare();
        compare.compareResult(testCase);
    }


    @DataProvider(name = "testcaseProvider")
    public Object[][] provideTestcase() {

        Object[] testcaseArray = mTestCases.toArray();

        Object[][] res = new Object[testcaseArray.length][1];

        for (int i = 0; i < res.length; i++) {
            res[i][0] = testcaseArray[i];
        }

        return res;
    }

    @AfterTest
    @Parameters({"path", "reportPath"})
    public void report(String path, String reportPath) throws IOException, InvalidFormatException, ExcelOprationFailException {
        SetValueToExcel setValueToExcel = new SetValueToExcel();
        setValueToExcel.setResultToExcel(path, mTestCases);
        ResultStatisticsCreator resultStatisticsCreator = new ResultStatisticsCreator();
        ResultStatistics resultStatistics = resultStatisticsCreator.statisticsResult(mTestCases);
        ChartGenerator chartGenerator = new ChartGenerator();
        chartGenerator.generateChart(reportPath, resultStatistics.getSuccess(), resultStatistics.getFail());
    }
}

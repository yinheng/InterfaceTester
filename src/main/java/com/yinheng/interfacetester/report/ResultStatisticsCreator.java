package com.yinheng.interfacetester.report;

import com.yinheng.interfacetester.data.model.Result;
import com.yinheng.interfacetester.data.model.ResultStatistics;
import com.yinheng.interfacetester.data.model.TestCase;

import java.util.List;

public class ResultStatisticsCreator {

    public ResultStatistics statisticsResult(List<TestCase> testCases) {
        int passNum = 0;
        int failNum = 0;
        for (TestCase testCase : testCases) {

            if (testCase.getResult() == Result.SUCCESS) {
                passNum++;
            } else {
                failNum++;
            }
        }
        return new ResultStatistics(passNum, failNum);
    }
}

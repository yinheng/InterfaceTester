package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;

public interface TestCaseRequester {
    String getResponse(TestCase testCase) throws Exception;
}

package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;

import java.io.IOException;

public interface TestCaseRequester {
    String getResponse(TestCase testCase) throws IOException, ResponseFailException;
}

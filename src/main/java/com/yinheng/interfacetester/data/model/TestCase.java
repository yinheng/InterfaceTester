package com.yinheng.interfacetester.data.model;

import lombok.Data;

import java.util.Map;

@Data
public class TestCase {

    private int index;
    private String id;
    private String testName;
    private Level level;
    private RequestType requestType;
    private String httpHeader;
    private String target;
    private String expectedData;
    private String param;
    private String output;
    private Map<String,String> outputMap;
    private String response;
    private Map<String, String> responseMap;
    private Result result;

    private TestCaseConfigs configs;
}

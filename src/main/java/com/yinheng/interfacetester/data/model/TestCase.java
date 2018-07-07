package com.yinheng.interfacetester.data.model;

import lombok.Data;

@Data
public class TestCase {

    private String id;
    private String testName;
    private Level level;
    private RequestType requestType;
    private String httpHeader;
    private String target;
    private String expectedData;
    private String param;
    private String output;
    private String response;
    private Result result;

}

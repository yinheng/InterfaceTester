package com.yinheng.interfacetester.data;

import com.yinheng.interfacetester.data.model.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class TestDataTest {

    @Test
    public void testReadExcel() throws IOException, InvalidFormatException {
        TestData testData = new TestData();
        List<TestCase> testCases = testData.readExcel("E:\\Workspace-Wendy\\InterfaceTester\\src\\main\\resources\\Template.xls");
        for (TestCase testCase : testCases) {
            org.apache.logging.log4j.LogManager.getLogger().debug(testCase.toString());
        }
    }
}
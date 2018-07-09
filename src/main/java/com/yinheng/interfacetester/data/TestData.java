package com.yinheng.interfacetester.data;

import com.yinheng.interfacetester.BuildConfig;
import com.yinheng.interfacetester.data.model.Level;
import com.yinheng.interfacetester.data.model.RequestType;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.data.model.TestCaseConfigs;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestData {


    public List<TestCase> readExcel(String path) throws IOException, InvalidFormatException {
        List<TestCase> testCases = new ArrayList<TestCase>();

        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.getSheetAt(0);

        if (BuildConfig.DEBUG) {
            LogManager.getLogger().debug("sheet name: " + sheet.getSheetName());
        }

        testCases.clear();

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        for (Row row : sheet) {

            if (row.getRowNum() != 0) {
                // Check row.
                Cell idCell = row.getCell(0);
                String id = dataFormatter.formatCellValue(idCell);
                if (id == null || id.length() == 0) {
                    break;
                }

                TestCase testCase = new TestCase();

                for (Cell cell : row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    int columnIndex = cell.getColumnIndex();
                    Cell cell0 = sheet.getRow(0).getCell(columnIndex);
                    String cell0Value = dataFormatter.formatCellValue(cell0);
                    switch (cell0Value) {
                        case "ID":
                            testCase.setId(cellValue);
                            break;
                        case "TestCase":
                            testCase.setTestName(cellValue);
                            break;
                        case "Level":
                            testCase.setLevel(Level.valueOf(cellValue.trim()));
                            break;
                        case "Type":
                            testCase.setRequestType(RequestType.valueOf(cellValue.trim()));
                            break;
                        case "Http_Header":
                            testCase.setHttpHeader(cellValue);
                            break;
                        case "Target":
                            testCase.setTarget(cellValue);
                            break;
                        case "Expected_Data":
                            testCase.setExpectedData(cellValue);
                            break;
                        case "Parameter":
                            testCase.setParam(cellValue);
                            break;
                        case "Output":
                            testCase.setOutput(cellValue);
                            break;
                        case "Configs":
                            testCase.setConfigs(new TestCaseConfigs(cellValue));
                            break;

                    } // End switch.
                } // End for.

                testCases.add(testCase);
            } // End if.

        }

        workbook.close();
        return testCases;
    }
}

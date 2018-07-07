package com.yinheng.interfacetester.data;

import com.yinheng.interfacetester.data.model.Level;
import com.yinheng.interfacetester.data.model.RequestType;
import com.yinheng.interfacetester.data.model.Result;
import com.yinheng.interfacetester.data.model.TestCase;
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
        LogManager.getLogger().debug("sheet name: " + sheet.getSheetName());

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
                    switch (columnIndex) {
                        case 0:
                            testCase.setId(cellValue);
                            break;
                        case 1:
                            testCase.setTestName(cellValue);
                            break;
                        case 2:
                            testCase.setLevel(Level.valueOf(cellValue.trim()));
                            break;
                        case 3:
                            testCase.setRequestType(RequestType.valueOf(cellValue.trim()));
                            break;
                        case 4:
                            testCase.setHttpHeader(cellValue);
                            break;
                        case 6:
                            testCase.setTarget(cellValue);
                            break;
                        case 7:
                            testCase.setExpectedData(cellValue);
                            break;
                        case 8:
                            testCase.setParam(cellValue);
                            break;
                        case 9:
                            testCase.setOutput(cellValue);
                            break;
                        case 10:
                            testCase.setResponse(cellValue);
                            break;
                        case 12:
                            testCase.setResult(Result.valueOf(cellValue.trim()));
                            break;
                    } // End switch.
                } // End for.

                testCases.add(testCase);
            } // End if.

        }

        return testCases;
    }
}

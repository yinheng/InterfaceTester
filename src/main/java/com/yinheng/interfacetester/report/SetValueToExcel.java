package com.yinheng.interfacetester.report;

import com.google.common.io.Files;
import com.yinheng.interfacetester.data.model.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SetValueToExcel {

    public void setResultToExcel(String path, List<TestCase> testCases) throws IOException, InvalidFormatException, ExcelOprationFailException {
        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            TestCase testCase = testCases.get(i - 1);
            if (testCase.getResult() != null) {
                LogManager.getLogger().debug("rows: " + sheet.getLastRowNum());
                LogManager.getLogger().debug("testCase" + (i - 1) + ":response: " + testCase.getResponse());
                LogManager.getLogger().debug("testCase" + (i - 1) + ":result: " + testCase.getResult().name());
                Row row = sheet.getRow(i);
                row.getCell(10).setCellValue(testCase.getResponse());
                row.getCell(12).setCellValue(testCase.getResult().name());
            }
        }

        // Write to tmp.
        String tmpPath = path + ".tmp";
        FileOutputStream tmpStream = new FileOutputStream(tmpPath);
        workbook.write(tmpStream);

        // Close.
        workbook.close();
        tmpStream.close();

        if (new File(path).delete()) {
            Files.move(new File(tmpPath), new File(path));
        } else {
            throw new ExcelOprationFailException("excel delete fail");
        }
    }
}

package com.yinheng.interfacetester.report;

import com.google.common.io.Files;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChartGenerator {

    public void generateChart(String reportDir, int passNum, int failNum) throws IOException {

        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
        defaultPieDataset.setValue("Pass", passNum);
        defaultPieDataset.setValue("Fail", failNum);

        JFreeChart jFreeChart = ChartFactory.createPieChart("Result pie chart", defaultPieDataset);

        String charPath = reportDir + File.separator + "Chart.JPEG";

        Files.createParentDirs(new File(charPath));

        FileOutputStream fos = new FileOutputStream(charPath);
        ChartUtils.writeChartAsJPEG(fos, jFreeChart, 1000, 800);
        fos.close();
    }
}

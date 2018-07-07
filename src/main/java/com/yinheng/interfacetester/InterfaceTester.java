package com.yinheng.interfacetester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class InterfaceTester {

    private final Logger mLogger = LogManager.getLogger();

    @BeforeTest
    public void readData() {
        mLogger.debug("readData");
    }

    @Test
    public void run() {
        mLogger.debug("run");
    }

    @AfterTest
    public void report() {
        mLogger.debug("report");
    }
}

package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.data.model.TestCaseConfigs;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.sql.*;

/**
 * Created by 尹恒 on 2018/7/9.
 */
public class DbRequester implements TestCaseRequester {

    private static final String KEY_URL = "url";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    public String getResponse(TestCase testCase) throws SQLException, IOException {

        TestCaseConfigs configs = testCase.getConfigs();

        String url = configs.getStringOrThrow(KEY_URL);
        String name = configs.getStringOrThrow(KEY_NAME);
        String password = configs.getStringOrThrow(KEY_PASSWORD);

        LogManager.getLogger().debug(String.format("DbRequester, getConnection with \nurl %s \nname %s \npassword %s", url, name, password));

        Connection connection = DriverManager.getConnection(url, name, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(testCase.getParam());
        if (resultSet.next()) {
            org.apache.logging.log4j.LogManager.getLogger().debug("db response: " + resultSet.getString("name"));
            return "{id:"+ resultSet.getString("id") + "}";
        } else {
            return null;
        }
    }
}

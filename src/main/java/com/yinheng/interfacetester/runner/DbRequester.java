package com.yinheng.interfacetester.runner;

import com.google.gson.JsonObject;
import com.yinheng.interfacetester.data.model.TestCase;
import com.yinheng.interfacetester.data.model.TestCaseConfigs;
import org.apache.logging.log4j.LogManager;
import org.jfree.data.json.impl.JSONArray;
import org.jfree.data.json.impl.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        List<Map<String, String>> reponseList = new ArrayList<Map<String, String>>();

        resultSet.first();
        while (!resultSet.isAfterLast()) {
            Map<String, String> resultMap = new HashMap<String, String>();
            for (int i = 0; i < columnCount; i++) {
                String columnName = resultSetMetaData.getColumnName(i + 1);
                String value = resultSet.getString(i + 1);
                resultMap.put(columnName, value);
            }
            reponseList.add(resultMap);
            resultSet.next();
        }
        String result = JSONArray.toJSONString(reponseList);
        return result;
    }
}

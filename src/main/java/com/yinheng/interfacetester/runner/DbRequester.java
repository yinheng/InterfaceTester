package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;

import java.io.IOException;
import java.sql.*;
import java.util.logging.LogManager;

/**
 * Created by 尹恒 on 2018/7/9.
 */
public class DbRequester implements TestCaseRequester {

    private static final String URL="jdbc:mysql://172.16.61.212:23306/db_x_see?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String NAME="root";
    private static final String PASSWORD="raisecom@123";

    public String getResponse(TestCase testCase) throws IOException, ResponseFailException, ClassNotFoundException, SQLException {

        Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(testCase.getParam());
        if(resultSet.next()) {
        org.apache.logging.log4j.LogManager.getLogger().debug("db response: " + resultSet.getString("name"));
        return resultSet.getString("name");}
        else {
            return null;
        }
    }

}

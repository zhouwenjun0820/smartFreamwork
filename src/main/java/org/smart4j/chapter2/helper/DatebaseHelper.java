package org.smart4j.chapter2.helper;

import org.smart4j.chapter2.util.PropUtils;

import java.sql.*;
import java.util.Properties;

public class DatebaseHelper {
    private static  String DRIVER;
    private static  String URL;
    private static  String USERNAME;
    private static  String PASSWORD;
    static{
        Properties jdbcProp= PropUtils.loadProps("jdbc.properties");
        DRIVER =(String) jdbcProp.getProperty("jdbc.driver");
        URL = (String) jdbcProp.getProperty("jdbc.url");
        USERNAME = (String) jdbcProp.getProperty("jdbc.username");
        PASSWORD = (String) jdbcProp.getProperty("jdbc.password");
        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            System.out.println(e);
        }
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}

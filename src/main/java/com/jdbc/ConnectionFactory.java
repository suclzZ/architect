package com.jdbc;

import com.google.common.collect.Lists;

import java.sql.*;
import java.util.List;

/**
 * @author sucl
 * @since 2018/12/12
 */
public class ConnectionFactory {

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  jdbc:mysql://[host]:[port]/[db]
     *  jdbc:oracle:thin@[host]:[port]:[SID]
     *  jdbc:oracle:thin@//[host]:[port]//[serviceName]
     *
     */
    public void init(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
            PreparedStatement prep = connection.prepareStatement("select  * from user");
            ResultSet result = prep.executeQuery();
            List<String> rs = Lists.newArrayList();
            while (result.next()){
                rs.add( result.getString(2) );
            }
            System.out.println(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ConnectionFactory().init();
    }
}

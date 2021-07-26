package com.wyj.mybatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-06-30
 */
public class Main2 {

    private static final String URL = " jdbc:mysql://yz02:15733/wyj_test_db?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "test_rw";
    private static final String PSWD = "";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver", true, ClassLoader.getSystemClassLoader());

        Connection connection = DriverManager.getConnection(URL, USERNAME, PSWD);
        String sql = "select * from t_a where id > ? limit ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        //        pstmt.setString(1, "user_info");
        pstmt.setLong(1, 10L);
        pstmt.setLong(2, 1L);

        ResultSet rs = pstmt.executeQuery();
        System.out.println("id\tage\tname\t");

        while(rs.next()) {
            long id = rs.getLong("id");
            int age = rs.getInt("age");
            String nickName = rs.getString("name");
            System.out.println(id + "\t" + age + "\t" + nickName + "\t");
        }
        connection.close();
    }
}

package com.wyj.mybatis.jdbc;

import java.sql.*;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/30
 */
public class Main {

    private static final String URL = "jdbc:mysql://rm-bp11fo7r52fp6qc61xo.mysql.rds.aliyuncs.com:3306/student_card?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PSWD = "W#EVI7921Kjl";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection(URL, USERNAME, PSWD);
        String sql = "select * from user_info where id > ? limit ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
//        pstmt.setString(1, "user_info");
        pstmt.setLong(1, 10L);
        pstmt.setLong(2, 1L);

        ResultSet rs = pstmt.executeQuery();
        System.out.println("id\talipayUserId\tnickName\t");

        while(rs.next()) {
            long id = rs.getLong("id");
            String alipayUserId = rs.getString("alipay_user_id");
            String nickName = rs.getString("nick_name");
            System.out.println(id + "\t" + alipayUserId + "\t" + nickName + "\t");
        }
        connection.close();
    }

}

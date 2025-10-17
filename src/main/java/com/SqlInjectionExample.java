package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjectionExample {

    public void doLogin(String user, String pass) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=myuser&password=mypass");

            // Unsafe SQL query constructed by concatenating strings.
            String query = "SELECT * FROM users WHERE user = '" + user + "' AND password = '" + pass + "'";

            stmt = conn.createStatement();
            stmt.executeQuery(query);

            // ...

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
//    public void doLogin(String user, String pass) {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=myuser&password=mypass");
//
//            // Safe SQL query using prepared statements.
//            String query = "SELECT * FROM users WHERE user = ? AND password = ?";
//
//            pstmt = conn.prepareStatement(query);
//            pstmt.setString(1, user);
//            pstmt.setString(2, pass);
//
//            rs = pstmt.executeQuery();
//
//            // ...
//
//        } catch (SQLException se) {
//            // Handle errors for JDBC
//            se.printStackTrace();
//        } finally {
//            // Finally block used to close resources
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }
//    }
}

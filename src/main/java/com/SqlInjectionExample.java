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

//            // Safe SQL query using prepared statements.
//            String query = "SELECT * FROM users WHERE user = ? AND password = ?";

//            pstmt = conn.prepareStatement(query);
//            pstmt.setString(1, user);
//            pstmt.setString(2, pass);

//            rs = pstmt.executeQuery();

//            // ...

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
    
    // 额外的 SQL 注入示例,用于测试安全扫描工具
    
    public void getUserByEmail(String email) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=myuser&password=mypass");
            
            // SQL注入漏洞: 直接拼接用户输入
            String query = "SELECT * FROM users WHERE email = '" + email + "'";
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                System.out.println("User: " + rs.getString("username"));
            }
            
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public void deleteUser(String userId) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=myuser&password=mypass");
            
            // SQL注入漏洞: 使用字符串拼接构造 DELETE 语句
            String query = "DELETE FROM users WHERE id = " + userId;
            
            stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            
            System.out.println("Deleted " + result + " rows");
            
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public void updateUserStatus(String username, String status) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=myuser&password=mypass");
            
            // SQL注入漏洞: UPDATE 语句中的字符串拼接
            String sql = "UPDATE users SET status = '" + status + "' WHERE username = '" + username + "'";
            
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public void searchUsers(String keyword) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=myuser&password=mypass");
            
            // SQL注入漏洞: LIKE 查询中的字符串拼接
            String query = "SELECT * FROM users WHERE name LIKE '%" + keyword + "%' OR email LIKE '%" + keyword + "%'";
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                System.out.println("Found: " + rs.getString("name"));
            }
            
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

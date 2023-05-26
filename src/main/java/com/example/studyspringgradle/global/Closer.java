package com.example.studyspringgradle.global;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

public class Closer {
    public static void close(Connection conn, PreparedStatement pstmt, DataSource dataSource) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (conn != null) {
            close(conn, dataSource);
        }
    }

    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs, DataSource dataSource) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn != null)

            close(conn, dataSource);

    }

    private static void close(Connection conn, DataSource dataSource) {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}

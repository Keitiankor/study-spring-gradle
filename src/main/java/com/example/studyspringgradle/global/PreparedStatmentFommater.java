package com.example.studyspringgradle.global;

import java.sql.*;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PreparedStatmentFommater extends JdbcDaoSupport {
    private static PreparedStatement pstmt;

    private PreparedStatmentFommater(String sql) throws SQLException {
        try (Connection conn = getConnection()) {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
    }

    private PreparedStatmentFommater(String sql, String s1) throws SQLException {
        new PreparedStatmentFommater(sql);
        pstmt.setString(1, s1);
    }

    private PreparedStatmentFommater(String sql, String s1, String s2) throws SQLException {
        new PreparedStatmentFommater(sql, s1);
        pstmt.setString(2, s2);
    }

    private PreparedStatmentFommater(String sql, String s1, String s2, String s3) throws SQLException {
        new PreparedStatmentFommater(sql, s1, s2);
        pstmt.setString(3, s3);
    }

    private PreparedStatmentFommater(String sql, String s1, String s2, String s3, String s4) throws SQLException {
        new PreparedStatmentFommater(sql, s1, s2, s3);
        pstmt.setString(4, s4);
    }

    private PreparedStatmentFommater(String sql, String s1, String s2, String s3, String s4, String s5) throws SQLException {
        new PreparedStatmentFommater(sql, s1, s2, s3, s5);
        pstmt.setString(5, s5);
    }

    public static PreparedStatement getPreparedStatement(String sql)throws SQLException{
        new PreparedStatmentFommater(sql);
        return pstmt;
    }

    public static PreparedStatement getPreparedStatement(String sql, String s1) throws SQLException {
        new PreparedStatmentFommater(sql, s1);
        return pstmt;
    }

    public static PreparedStatement getPreparedStatement(String sql, String s1, String s2) throws SQLException {
        new PreparedStatmentFommater(sql, s1, s2);
        return pstmt;
    }

    public static PreparedStatement getPreparedStatement(String sql, String s1, String s2, String s3)
            throws SQLException {
        new PreparedStatmentFommater(sql, s1, s2, s3);
        return pstmt;
    }

    public static PreparedStatement getPreparedStatement(String sql, String s1, String s2, String s3, String s4)
            throws SQLException {
        new PreparedStatmentFommater(sql, s1, s2, s3, s4);
        return pstmt;
    }

    public static PreparedStatement getPreparedStatement(String sql, String s1, String s2, String s3, String s4, String s5)
            throws SQLException {
        new PreparedStatmentFommater(sql, s1, s2, s3, s4, s5);
        return pstmt;
    }

}

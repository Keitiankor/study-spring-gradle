package com.example.studyspringgradle.domain.account.dao;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.example.studyspringgradle.global.Closer;
import com.example.studyspringgradle.global.encrypt.SHA256;

import jakarta.annotation.PostConstruct;

@Repository
public class AccountRepositoryImpl extends JdbcDaoSupport implements AccountRepository {
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public AccountRepositoryImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public String assignmentAccount(String id, String password){
        String cryptogram = SHA256.encrypt(password);
        String sql = "" +
        "insert into user_account (account, account_password, reg_date) "+
        "values (?,?,UTC_TIMESTAMP);";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id);
            pstmt.setString(2, cryptogram);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            Closer.close(conn, pstmt, dataSource);
        }
        return id;
    }

    @Override
    public boolean checkAccountPass(String id, String password) {
        String cryptogram = SHA256.encrypt(password);
        String sql = "" +
                "select password from user_account where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String pw = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();

            if (rs.next()) {
                pw = rs.getString("password");
            }
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            Closer.close(conn, pstmt, rs, dataSource);
        }
        if (cryptogram == pw) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAccountAvailable(String id) {
        String sql = "" +
                "select account from user_account where account = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String s = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                s = rs.getString("account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Closer.close(conn, pstmt, rs, dataSource);
        }
        if (s == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int deleteAccount(String id, String password){
        String cryptogram = SHA256.encrypt(password);
        int accId = getAccountInfo(id, cryptogram);
        if(accId == 0){
            return 0;
        }
        String sql = ""+
        "delete from user_account where account_id = ?";


        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, accId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally{
            Closer.close(conn, pstmt, dataSource);
        }
        return accId;
    }

    @Override
    public int ChangePassword(String id, String oldPassword, String newPassword){
        String oldCryptogram = SHA256.encrypt(oldPassword);
        String newCryptogram = SHA256.encrypt(newPassword);
        int accId = getAccountInfo(id, oldCryptogram);
        if(accId == 0){
            return 0;
        }
        String sql = ""+
        "update user_account set account_password = ? where account_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newCryptogram);
            pstmt.setInt(2, accId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally{
            Closer.close(conn, pstmt, dataSource);
        }
        return accId;
    }

    private int getAccountInfo(String id, String password){
        String sql = ""+
        "select account_id from user_account where account = ? && account_password = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int accId = 0;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            if(rs.next()){
                accId = rs.getInt("account_id");
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            Closer.close(conn, pstmt, rs, dataSource);
        }
        return accId;

    }
}

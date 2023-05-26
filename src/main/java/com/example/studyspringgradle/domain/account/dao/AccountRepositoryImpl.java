package com.example.studyspringgradle.domain.account.dao;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.example.studyspringgradle.global.PreparedStatmentFommater;
import com.example.studyspringgradle.global.encrypt.SHA256;
import com.example.studyspringgradle.global.response.exception.business.WrongPasswordException;

import jakarta.annotation.PostConstruct;

@Repository
public class AccountRepositoryImpl extends JdbcDaoSupport implements AccountRepository {
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public AccountRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String assignmentAccount(String id, String password) {
        String cryptogram = SHA256.encrypt(password);
        String sql = """
                insert into user_account (account, account_password, reg_date)
                values (?,?,UTC_TIMESTAMP);
                    """;

        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, id, cryptogram);) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean checkAccountPass(String id, String password) {
        String cryptogram = SHA256.encrypt(password);
        String sql = """
                select password from user_account where id = ?
                    """;
        String pw = null;
        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, id)) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                pw = rs.getString("password");
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        if (cryptogram == pw) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAccountAvailable(String id) {
        String sql = """
                select account from user_account where account = ?
                    """;

        String s = null;

        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, id)) {
            pstmt.executeQuery();
            try (ResultSet rs = pstmt.getResultSet()) {
                if (rs.next()) {
                    s = rs.getString("account");
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (s == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int deleteAccount(String id, String password) {
        String cryptogram = SHA256.encrypt(password);
        int accId = getAccountInfo(id, cryptogram);
        if (accId == 0) {
            throw new WrongPasswordException();
        }
        String sql = """
                delete from user_account where account_id = ?
                    """;

        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, id)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accId;
    }

    @Override
    public int ChangePassword(String id, String oldPassword, String newPassword) {
        String oldCryptogram = SHA256.encrypt(oldPassword);
        String newCryptogram = SHA256.encrypt(newPassword);
        int accId = getAccountInfo(id, oldCryptogram);
        if (accId == 0) {
            throw new WrongPasswordException();
        }
        String sql = """
                update user_account set account_password = ? where account_id = ?
                    """;

        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, newCryptogram, Integer.toString(accId))) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accId;
    }

    private int getAccountInfo(String id, String password) {
        String sql = "" +
                "select account_id from user_account where account = ? && account_password = ?";

        int accId = 0;
        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, id, password)) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                accId = rs.getInt("account_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accId;

    }
}

package com.example.studyspringgradle.domain.board.dao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.domain.board.domain.PostedPost;
import com.example.studyspringgradle.global.PreparedStatmentFommater;
import com.example.studyspringgradle.global.encrypt.SHA256;

import jakarta.annotation.PostConstruct;

@Repository
public class BoardRepositoryImpl extends JdbcDaoSupport implements BoardRepository {
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public BoardRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<BoardPost> getAllPost() {
        String sql = """
                select id, title, reg_date, edit_date
                from board order by id desc;
                        """;

        List<BoardPost> boardPosts = new ArrayList<>();

        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql)) {
            pstmt.executeQuery();

            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                BoardPost bp = new BoardPost();
                bp.setTitle(rs.getString("title"));
                bp.setPostNumber(rs.getInt("id"));
                bp.setPostDate(rs.getTimestamp("reg_date"));
                bp.setEditedDate(rs.getTimestamp("edit_date"));
                boardPosts.add(bp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardPosts;
    }

    @Override
    public List<BoardPost> getPagePost(int limit, int offset) {
        String sql = """
                select id, title, reg_date, edit_date
                from board order by id desc limit ? offset ?;
                    """;

        List<BoardPost> boardPosts = new ArrayList<>();

        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, Integer.toString(limit), Integer.toString((offset - 1) * limit))) {
            pstmt.executeQuery();

            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                BoardPost bp = new BoardPost();
                bp.setTitle(rs.getString("title"));
                bp.setPostNumber(rs.getInt("id"));
                bp.setPostDate(rs.getTimestamp("reg_date"));
                bp.setEditedDate(rs.getTimestamp("edit_date"));
                boardPosts.add(bp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardPosts;
    }

    @Override
    public BoardPost getSingPost(int postId) {
        String sql = """
                select id, title, post, reg_date, edit_date
                from board where id = ?
                    """;

        BoardPost bp = new BoardPost();

        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, Integer.toString(postId))) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                bp.setPost((rs.getString("post")));
                bp.setTitle(rs.getString("title"));
                bp.setPostNumber(rs.getInt("id"));
                bp.setPostDate(rs.getTimestamp("reg_date"));
                bp.setEditedDate(rs.getTimestamp("edit_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bp;
    }

    @Override
    public PostedPost postNewPost(String title, String password, String content) {
        String cryptogram = SHA256.encrypt(password);
        String sql = """
                insert into board (title, post_password, post, reg_date)
                values (?, ?, ?, UTC_TIMESTAMP);
                select id,title from board order by id decs limit 1;
                    """;

        PostedPost pp = new PostedPost();
        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, title, cryptogram, content)) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();

            pp.setTitle(title);
            pp.setPost(content);
            if (rs.next()) {
                pp.setPostNumber(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pp;
    }

    @Override
    public PostedPost postNewPost(String title, String content, String account, String password) {
        String cryptogram = SHA256.encrypt(password);
        String sql = "" +
                "insert into board (title, post_password, post, reg_date, account_id)" +
                "values(?,?,?,UTC_TIMESTAMP,?)" +
                "select id,title from board order by id decs limit 1;";

        PostedPost pp = new PostedPost();
        try (PreparedStatement pstmt = PreparedStatmentFommater
        .getPreparedStatement(sql, title, cryptogram, content, account)){
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();

            pp.setTitle(title);
            pp.setPost(content);
            if (rs.next()) {
                pp.setPostNumber(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.getStackTrace();
            return pp;
        }
        return pp;
    }

}

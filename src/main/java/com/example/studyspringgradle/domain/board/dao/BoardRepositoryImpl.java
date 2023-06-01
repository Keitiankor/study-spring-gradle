package com.example.studyspringgradle.domain.board.dao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.domain.board.domain.BoardPosts;
import com.example.studyspringgradle.domain.board.domain.PostedPost;
import com.example.studyspringgradle.global.PreparedStatmentFommater;
import com.example.studyspringgradle.global.Tuple;
import com.example.studyspringgradle.global.encrypt.SHA256;
import com.example.studyspringgradle.global.response.exception.business.BadRequestException;
import com.example.studyspringgradle.global.response.exception.business.IsNullDataException;
import com.example.studyspringgradle.global.response.exception.business.WrongPostIdException;

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
    public List<BoardPosts> getAllPost() {
        String sql = """
                select id, title, reg_date, edit_date
                from board order by id desc;
                        """;

        List<BoardPosts> boardPosts = new ArrayList<>();

        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql)) {
            pstmt.executeQuery();

            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                BoardPosts bp = new BoardPosts();
                bp.setTitle(rs.getString("title"));
                bp.setPostNumber(rs.getInt("id"));
                bp.setPostDate(rs.getTimestamp("reg_date"));
                bp.setEditedDate(rs.getTimestamp("edit_date"));
                bp.setCommentCount(getCommentCount(bp.getPostNumber()));
                boardPosts.add(bp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardPosts;
    }

    @Override
    public List<BoardPosts> getPagePost(int limit, int offset) {
        int maxOffset = getMaxOffset() / limit;
        if (maxOffset < offset) {
            throw new BadRequestException();
        }
        String sql = """
                select id, title, reg_date, edit_date
                from board order by id desc limit ? offset ?;
                    """;

        List<BoardPosts> boardPosts = new ArrayList<>();

        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, Integer.toString(limit), Integer.toString((offset - 1) * limit))) {
            pstmt.executeQuery();

            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                BoardPosts bp = new BoardPosts();
                bp.setTitle(rs.getString("title"));
                bp.setPostNumber(rs.getInt("id"));
                bp.setPostDate(rs.getTimestamp("reg_date"));
                bp.setEditedDate(rs.getTimestamp("edit_date"));
                bp.setCommentCount(getCommentCount(bp.getPostNumber()));
                boardPosts.add(bp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardPosts;
    }

    @Override
    public BoardPost getSinglePost(int postId) {
        isExistPostId(postId);
        String sql = """
                select id, title, post, reg_date, edit_date
                from board where id = ?;
                """;

        BoardPost bp = new BoardPost();

        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, Integer.toString(postId))) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                Tuple<Integer, Integer> ldl = new Tuple<>(getLikeDislike(postId));
                bp.setPost((rs.getString("post")));
                bp.setTitle(rs.getString("title"));
                bp.setPostNumber(rs.getInt("id"));
                bp.setPostDate(rs.getTimestamp("reg_date"));
                bp.setEditedDate(rs.getTimestamp("edit_date"));
                bp.setCommentCount(getCommentCount(bp.getPostNumber()));
                bp.setComments(getCommentsOnPost(bp.getPostNumber()));
                bp.setLikes(ldl.getObjectA());
                bp.setDislikes(ldl.getObjectB());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bp;
    }

    private List<Tuple<String, Timestamp>> getCommentsOnPost(int postId) {
        String sql = """
                select comment, post_date
                from comments where id = ?;
                """;
        List<Tuple<String, Timestamp>> tupleList = new ArrayList<>();
        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, Integer.toString(postId))) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                Tuple<String, Timestamp> tuple = new Tuple<String, Timestamp>(rs.getString("comment"),
                        rs.getTimestamp("post_date"));
                tupleList.add(tuple);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tupleList;

    }

    private int getCommentCount(int postId) {
        String sql = """
                select count(*) from comments where id = ?
                """;
        int commentCount = 0;
        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, Integer.toString(postId))) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                commentCount = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentCount;
    }

    private Tuple<Integer, Integer> getLikeDislike(int postId) {
        String sql = """
                select likes, dislikes from board where id = ?
                """;
        Tuple<Integer, Integer> ldl = null;
        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, Integer.toString(postId))) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                ldl = Tuple.of(rs.getInt("likes"), rs.getInt("dislikes"));
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (ldl == null) {
            throw new IsNullDataException();
        }
        return ldl;
    }

    private int getMaxOffset() {
        String sql = """
                SELECT COUNT(*) from board;
                """;
        int maxOffset = -1;
        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql)) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                maxOffset = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxOffset;

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
        String sql = """
                insert into board (title, post_password, post, reg_date, account_id)
                values(?,?,?,UTC_TIMESTAMP,?)
                select id,title from board order by id decs limit 1;
                    """;

        PostedPost pp = new PostedPost();
        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, title, cryptogram, content, account)) {
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

    @Override
    public String postNewComment(String comment, int postId) {
        isExistPostId(postId);
        String sql = """
                insert into comment (post_id, comment, post_date)
                values (?, ?, UTC_TIMESTAMP);
                """;
        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, Integer.toString(postId), comment)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public void isExistPostId(int postId) {
        String sql = """
                select count(*) from board;
                """;
        boolean isExist = false;
        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql)) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                isExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!isExist) {
            throw new WrongPostIdException();
        }
    }

    @Override
    public boolean postLikeDislike(String account, String password, int postId, boolean likeDislike) {
        Tuple<Boolean, String> isAreadyldl = getAreadyldl(account, postId);
        int accId = getAccountId(account);

        if (isAreadyldl.getObjectA()) {
            return swapLikeDislike(isAreadyldl.getObjectB(), postId, accId, likeDislike);
        }
        String ldl = "dislikes";
        String ldld = "disliked";
        if (likeDislike) {
            ldl = "likes";
            ldld = "liked";
        }

        int count = getCountLikeDislike(ldl, postId);
        String sql = """
                insert into like_dislike (account_id, id, ?)
                values (?, ?, 1);
                update board set ? = '?' WHERE id = '?';
                """;
        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, ldld, Integer.toString(accId), Integer.toString(postId), ldl,
                        Integer.toString(count + 1), Integer.toString(postId))) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean swapLikeDislike(String isAreadyldl, int postId, int accId, boolean likeDislike) {
        int likedC = getCountLikeDislike("likes", postId);
        int dislikedC = getCountLikeDislike("dislikes", postId);
        boolean ldl = isAreadyldl.equals("have_liked");

        if (ldl == likeDislike) {
            if (likeDislike) {
                likedC--;
            } else {
                dislikedC--;
            }
        } else {
            if (likeDislike) {
                likedC++;
                dislikedC--;
            } else {
                likedC--;
                dislikedC++;
            }
        }
        String str1 = """
                update board set likes = ?, dislikes = ? where id = ?;
                """;
        String str2 = """
                update like_dislike set liked = ?, disliked = ? where id = ?, account_id = ?;
                        """;
        try (
                PreparedStatement pstmt = PreparedStatmentFommater
                        .getPreparedStatement(str1,
                                Integer.toString(likedC),
                                Integer.toString(dislikedC),
                                Integer.toString(postId))) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try (
                PreparedStatement pstmt = PreparedStatmentFommater
                        .getPreparedStatement(str2,
                                Boolean.toString(ldl == likeDislike ? false : likeDislike),
                                Boolean.toString(ldl == likeDislike ? false : !likeDislike),
                                Integer.toString(postId), Integer.toString(accId))) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private int getCountLikeDislike(String ldl, int postId) {
        String sql = """
                select ? from board where id = ?;
                """;
        int count = 0;
        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, ldl, Integer.toString(postId))) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                count = rs.getInt(ldl);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    private Tuple<Boolean, String> getAreadyldl(String account, int postid) {
        int accId = getAccountId(account);
        Tuple<Boolean, String> returnData = null;
        String sql = """
                select have_liked, have_disliked from like_dislike
                where account_id = ? & id = ?
                    """;
        try (PreparedStatement pstmt = PreparedStatmentFommater
                .getPreparedStatement(sql, Integer.toString(accId), Integer.toString(postid))) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            boolean liked = rs.getBoolean("have_liked");
            boolean disliked = rs.getBoolean("have_disliked");
            if (liked | disliked)
                returnData = liked ? new Tuple<Boolean, String>(true, "have_liked")
                        : new Tuple<Boolean, String>(true, "have_disliked");
            else
                returnData = new Tuple<Boolean, String>(false, "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnData;
    }

    private int getAccountId(String account) {
        String sql = """
                select account_id from user_account
                where account = ?
                """;
        int accId = 0;
        try (PreparedStatement pstmt = PreparedStatmentFommater.getPreparedStatement(sql, account)) {
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

package com.example.studyspringgradle.domain.board.dao;

import java.util.List;

import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.domain.board.domain.BoardPosts;
import com.example.studyspringgradle.domain.board.domain.PostedPost;

public interface BoardRepository {
    List<BoardPosts> getAllPost();

    List<BoardPosts> getPagePost(int limit, int offset);

    BoardPost getSinglePost(int postId);

    PostedPost postNewPost(String title, String password, String content);

    PostedPost postNewPost(String title, String content, String account, String password);

    String postNewComment(String comment, int postId);

    boolean postLikeDislike(String account, String password, int postId, boolean likeDislike);
}

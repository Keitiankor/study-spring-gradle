package com.example.studyspringgradle.domain.board.service;

import java.util.List;

import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.domain.board.domain.BoardPosts;
import com.example.studyspringgradle.domain.board.domain.PostedPost;

public interface BoardService {
    List<BoardPosts> getAllPostDao();

    List<BoardPosts> getPagePostDao(int limit, int offset);

    BoardPost getSinglePostDao(int postId);

    PostedPost postNewPostDao(String title, String password, String content);

    PostedPost postNewPostDao(String title, String content, String account, String password);

    String postNewCommnetDao(String comment, int postId);

    boolean postLikeDislike(String account, String password, int postId, boolean likeDislike);
}

package com.example.studyspringgradle.domain.board.dao;

import java.util.List;

import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.domain.board.domain.PostedPost;

public interface BoardRepository {
    List<BoardPost> getAllPost();

    List<BoardPost> getPagePost(int limit, int offset);

    BoardPost getSingPost(int postId);

    PostedPost postNewPost(String title, String password, String content);

    PostedPost postNewPost(String title, String content, String account, String password);
}

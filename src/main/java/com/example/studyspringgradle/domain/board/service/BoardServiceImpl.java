package com.example.studyspringgradle.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studyspringgradle.domain.board.dao.BoardRepositoryImpl;
import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.domain.board.domain.PostedPost;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepositoryImpl boardReposiroryImpl;

    private BoardServiceImpl(BoardRepositoryImpl boardReposiroryImpl) {
        this.boardReposiroryImpl = boardReposiroryImpl;
    }

    @Override
    public List<BoardPost> getAllPostDao() {
        return boardReposiroryImpl.getAllPost();
    }

    @Override
    public BoardPost getSinglePostDao(int postId) {
        return boardReposiroryImpl.getSingPost(postId);
    }

    @Override
    public List<BoardPost> getPagePostDao(int limit, int offset) {
        return boardReposiroryImpl.getPagePost(limit, offset);
    }

    @Override
    public PostedPost postNewPostDao(String title, String password, String content) {
        return boardReposiroryImpl.postNewPost(title, password, content);
    }

    @Override
    public PostedPost postNewPostDao(String title, String content, String account, String password) {
        return boardReposiroryImpl.postNewPost(title, content, account, password);
    }

}

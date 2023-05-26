package com.example.studyspringgradle.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.studyspringgradle.domain.board.dao.BoardRepositoryImpl;
import com.example.studyspringgradle.domain.board.domain.BoardPost;
import com.example.studyspringgradle.domain.board.domain.BoardPosts;
import com.example.studyspringgradle.domain.board.domain.PostedPost;
import com.example.studyspringgradle.global.response.exception.business.BadRequestException;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepositoryImpl boardReposiroryImpl;

    private BoardServiceImpl(BoardRepositoryImpl boardReposiroryImpl) {
        this.boardReposiroryImpl = boardReposiroryImpl;
    }

    @Override
    public List<BoardPosts> getAllPostDao() {
        return boardReposiroryImpl.getAllPost();
    }

    @Override
    public BoardPost getSinglePostDao(int postId) {
        return boardReposiroryImpl.getSinglePost(postId);
    }

    @Override
    public List<BoardPosts> getPagePostDao(int limit, int offset) {
        if(limit<5 || limit >10 || offset<0){
            throw new BadRequestException();
        }
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

    @Override
    public String postNewCommnetDao(String comment, int postId){
        return boardReposiroryImpl.postNewComment(comment, postId);
    }

    @Override
    public boolean postLikeDislike(String account, String password, int postId, boolean likeDislike){
        return boardReposiroryImpl.postLikeDislike(account, password, postId, likeDislike);
    }
}

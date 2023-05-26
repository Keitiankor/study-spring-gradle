package com.example.studyspringgradle.domain.board.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.studyspringgradle.domain.account.service.AccountService;
import com.example.studyspringgradle.domain.board.dto.*;
import com.example.studyspringgradle.domain.board.service.BoardService;
import com.example.studyspringgradle.global.response.exception.business.WrongPasswordException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/board")
@RequiredArgsConstructor
@Validated
public class BoardApi {

    private BoardService boardService;
    private AccountService accountService;

    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<?> getAllPosts() {
        return new GetAllPageResponse(boardService.getAllPostDao()).response();
    }

    @GetMapping(value = "/page/{offset}/{limit}")
    @ResponseBody
    public ResponseEntity<?> getPostsPage(
            @PathVariable int limit,
            @PathVariable int offset) {
        return new GetPagePostResponse(boardService.getPagePostDao(limit, offset)).response();
    }

    @GetMapping(value = "/post/{postId}")
    @ResponseBody
    public ResponseEntity<?> getSinglePost(
            @PathVariable int postId) {
        return new GetSinglePostRespones(boardService.getSinglePostDao(postId)).response();
    }

    @PostMapping(value = "/posting/{title}")
    @ResponseBody
    public ResponseEntity<?> postNewPost(
            @RequestHeader(value = "id", required = false) String account,
            @RequestHeader(value = "account_pw", required = false) String account_pw,
            @RequestHeader(value = "password", required = false) String password,
            @PathVariable String title,
            @RequestParam(value = "content", required = false) String content) {
        if (account == null) {
            return new PostNewPostResponse(boardService.postNewPostDao(title, password, content)).response();
        }
        if (accountService.checkAccountPassDao(account, password)) {
            return new PostNewPostResponse(boardService.postNewPostDao(title, content, account, account_pw)).response();
        }
        throw new WrongPasswordException();
    }

    @PostMapping(value = "/posting/{postId}/{comment}")
    @ResponseBody
    public ResponseEntity<?> postNewComment(
            @PathVariable int postId,
            @PathVariable String comment) {
        return new PostNewCommentResponse(boardService.postNewCommnetDao(comment, postId)).response();
    }

    @PostMapping(value = "/post/{postId}/{likeDislike}")
    @ResponseBody
    public ResponseEntity<?> postLikeDislike(
        @RequestHeader(value = "account", required = true) String account,
        @RequestHeader(value = "password", required = true) String password,
        @PathVariable int postId,
        @PathVariable boolean likeDislike){
            return new PostLikeDislikeResponse(boardService.postLikeDislike(account, password, postId, likeDislike)).response();
        }

}

package com.example.studyspringgradle.domain.board.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.studyspringgradle.domain.account.dto.CheckAccountPass;
import com.example.studyspringgradle.domain.account.service.AccountService;
import com.example.studyspringgradle.domain.board.dto.*;
import com.example.studyspringgradle.domain.board.service.BoardService;
import com.example.studyspringgradle.global.response.normal.ResponseCode;
import com.example.studyspringgradle.global.response.normal.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/board")
@RequiredArgsConstructor
@Validated
public class BoardApi {

    @Autowired
    BoardService boardService;
    
    @Autowired
    AccountService accountService;

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<?> getAllPosts() {
        return new GetAllPageResponse(boardService.getAllPostDao()).response();
    }

    @GetMapping(value = "/page")
    @ResponseBody
    public ResponseEntity<?> getPostsPage(
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset) {
        if (limit < 5 || limit > 10) {
            return new ResponseEntity<>(Response.of(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } else {
            return new GetPagePostResponse(boardService.getPagePostDao(limit, offset)).response();
        }
    }

    @GetMapping(value = "/post")
    @ResponseBody
    public ResponseEntity<?> getSinglePost(
            @RequestParam(value = "postid", required = true) int postId) {
        return new GetSinglePostRespones(boardService.getSinglePostDao(postId)).response();
    }



    @PostMapping(value = "/post")
    @ResponseBody
    public ResponseEntity<?> postNewPost(
            @RequestHeader(value = "id", required = false, defaultValue = "null") String account,
            @RequestHeader(value = "account_pw", required = false, defaultValue = "null") String account_pw,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "password", required = false, defaultValue = "null") String password,
            @RequestParam(value = "content", required = false, defaultValue = "null") String content) {
        if (account != null) {
            if (new CheckAccountPass(accountService.checkAccountPassDao(account, account_pw)).isRight()) {
                return new PostNewPostResponse(boardService.postNewPostDao(title, content, account, account_pw)).response();
            } else {
                return new ResponseEntity<>(Response.of(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new PostNewPostResponse(boardService.postNewPostDao(title, password, content)).response();
        }
    }

}
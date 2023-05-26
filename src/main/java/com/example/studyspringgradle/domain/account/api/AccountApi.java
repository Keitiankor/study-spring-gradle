package com.example.studyspringgradle.domain.account.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.studyspringgradle.domain.account.dto.ChangePasswordResponse;
import com.example.studyspringgradle.domain.account.dto.DeleteAccountResponse;
import com.example.studyspringgradle.domain.account.dto.PostNewAccountResponse;
import com.example.studyspringgradle.domain.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
@Validated
public class AccountApi {
    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseEntity<?> postAccount(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "password", required = true) String password) {
        return new PostNewAccountResponse(accountService.postNewAccountDao(id, password)).reponse();
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public ResponseEntity<?> deleteAccount(
            @RequestHeader(value = "password", required = true) String password,
            @RequestParam(value = "account", required = true) String account) {
        return new DeleteAccountResponse(accountService.deleteAccountDao(account, password)).response();
    }

    @PatchMapping(value = "/change")
    @ResponseBody
    public ResponseEntity<?> changePassword(
            @RequestHeader(value = "password", required = true) String oldPassword,
            @RequestParam(value = "account", required = true) String account,
            @RequestParam(value = "newpassword", required = true) String newPassword) {
        return new ChangePasswordResponse(accountService
                .ChangePasswordDao(account, oldPassword, newPassword))
                .response();
    }
}

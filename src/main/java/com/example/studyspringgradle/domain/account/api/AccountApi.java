package com.example.studyspringgradle.domain.account.api;

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
    private AccountService accountService;

    @PostMapping(value = "/register/{id}/{password}")
    @ResponseBody
    public ResponseEntity<?> postAccount(
            @PathVariable String id,
            @PathVariable String password) {
        return new PostNewAccountResponse(accountService.postNewAccountDao(id, password)).reponse();
    }

    @DeleteMapping(value = "/delete/{account}")
    @ResponseBody
    public ResponseEntity<?> deleteAccount(
            @RequestHeader(value = "password", required = true) String password,
            @PathVariable String account) {
        return new DeleteAccountResponse(accountService.deleteAccountDao(account, password)).response();
    }

    @PatchMapping(value = "/change/{account}")
    @ResponseBody
    public ResponseEntity<?> changePassword(
            @RequestHeader(value = "password", required = true) String oldPassword,
            @RequestHeader(value = "newpassword", required = true) String newPassword,
            @PathVariable String account) {
        return new ChangePasswordResponse(accountService.ChangePasswordDao(account, oldPassword, newPassword))
                .response();
    }
}

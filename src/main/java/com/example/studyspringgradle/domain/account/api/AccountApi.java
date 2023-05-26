package com.example.studyspringgradle.domain.account.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.studyspringgradle.domain.account.dto.ChangePasswordResponse;
import com.example.studyspringgradle.domain.account.dto.CheckAccountAvailableResponse;
import com.example.studyspringgradle.domain.account.dto.DeleteAccountResponse;
import com.example.studyspringgradle.domain.account.dto.PostNewAccountResponse;
import com.example.studyspringgradle.domain.account.service.AccountService;
import com.example.studyspringgradle.global.encrypt.RegexChecker;
import com.example.studyspringgradle.global.response.normal.Response;
import com.example.studyspringgradle.global.response.normal.ResponseCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
@Validated
public class AccountApi {
    @Autowired
    AccountService accountService;

    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseEntity<?> postAccount(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "password", required = true) String password) {
        if (RegexChecker.accountChecker(id) && RegexChecker.passwordChecker(password)) {
            if (new CheckAccountAvailableResponse(accountService.checkAccountAvailableDao(id)).isAvailable()) {
                return new PostNewAccountResponse(accountService.postNewAccountDao(id, password)).reponse();
            } else {
                return new ResponseEntity<>(Response.of(ResponseCode.CONFLICT), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(Response.of(ResponseCode.WRONG_ACCOUNT_FORMAT), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public ResponseEntity<?> deleteAccount(
            @RequestHeader(value = "password", required = true) String password,
            @RequestParam(value = "account", required = true) String account) {
        int isSuccess = new DeleteAccountResponse(accountService.deleteAccountDao(account, password)).getIsSuccess();
        if (isSuccess > 0) {
            return new ResponseEntity<>(Response.of(ResponseCode.RESPONSE_OK), HttpStatus.ACCEPTED);
        } else if (isSuccess == 0) {
            return new ResponseEntity<>(Response.of(ResponseCode.WRONG_PASSWORD), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(Response.of(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/change")
    @ResponseBody
    public ResponseEntity<?> changePassword(
            @RequestHeader(value = "password", required = true) String oldPassword,
            @RequestParam(value = "account", required = true) String account,
            @RequestParam(value = "newpassword", required = true) String newPassword) {
        int isSuccess = new ChangePasswordResponse(accountService.ChangePasswordDao(account, oldPassword, newPassword))
                .getIsSuccess();
        if (isSuccess > 0) {
            return new ResponseEntity<>(Response.of(ResponseCode.RESPONSE_OK), HttpStatus.ACCEPTED);
        } else if (isSuccess == 0) {
            return new ResponseEntity<>(Response.of(ResponseCode.WRONG_PASSWORD), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(Response.of(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}

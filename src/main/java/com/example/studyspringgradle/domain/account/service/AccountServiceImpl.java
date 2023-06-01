package com.example.studyspringgradle.domain.account.service;

import com.example.studyspringgradle.domain.account.dao.AccountRepositoryImpl;
import com.example.studyspringgradle.global.encrypt.RegexChecker;
import com.example.studyspringgradle.global.response.exception.business.AccountConflictException;
import com.example.studyspringgradle.global.response.exception.business.WrongAccountFormatException;
import com.example.studyspringgradle.global.response.exception.business.WrongPasswordException;
import com.example.studyspringgradle.global.response.exception.business.WrongPasswordFormatException;

public class AccountServiceImpl implements AccountService {
    private final AccountRepositoryImpl accountRepositoryImpl;

    private AccountServiceImpl(AccountRepositoryImpl accountRepositoryImpl) {
        this.accountRepositoryImpl = accountRepositoryImpl;
    }

    @Override
    public String postNewAccountDao(String id, String password) {
        if (!RegexChecker.accountChecker(id)) {
            throw new WrongAccountFormatException();
        }
        if (!RegexChecker.passwordChecker(password)) {
            throw new WrongPasswordFormatException();
        }
        if (!checkAccountAvailableDao(id)) { // is not available?
            throw new AccountConflictException();
        }

        return accountRepositoryImpl.assignmentAccount(id, password);
    }

    @Override
    public boolean checkAccountPassDao(String id, String password) {
        if (!accountRepositoryImpl.checkAccountPass(id, password)) {
            throw new WrongPasswordException();
        }
        return true;
    }

    @Override
    public boolean checkAccountAvailableDao(String id) {
        return accountRepositoryImpl.checkAccountAvailable(id);
    }

    @Override
    public int deleteAccountDao(String id, String password) {
        return accountRepositoryImpl.deleteAccount(id, password);
    }

    @Override
    public int ChangePasswordDao(String account, String oldPassword, String newPassword) {
        return accountRepositoryImpl.ChangePassword(account, oldPassword, newPassword);
    }
}

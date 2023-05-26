package com.example.studyspringgradle.domain.account.service;

import com.example.studyspringgradle.domain.account.dao.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountService {
    private final AccountRepositoryImpl accountRepositoryImpl;

    private AccountServiceImpl(AccountRepositoryImpl accountRepositoryImpl){
        this.accountRepositoryImpl = accountRepositoryImpl;
    }

    @Override
    public String postNewAccountDao(String id, String password){
        return accountRepositoryImpl.assignmentAccount(id, password);
    }

    @Override
    public boolean checkAccountPassDao(String id, String password){
        return accountRepositoryImpl.checkAccountPass(id, password);
    }

    @Override
    public boolean checkAccountAvailableDao(String id){
        return accountRepositoryImpl.checkAccountAvailable(id);
    }

    @Override
    public int deleteAccountDao(String id, String password){
        return accountRepositoryImpl.deleteAccount(id, password);
    }

    
}

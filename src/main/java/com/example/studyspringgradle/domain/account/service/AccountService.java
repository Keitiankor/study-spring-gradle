package com.example.studyspringgradle.domain.account.service;

public interface AccountService {
    String postNewAccountDao(String id, String password);

    boolean checkAccountPassDao(String id, String password);

    boolean checkAccountAvailableDao(String id);

    int deleteAccountDao(String id, String password);

    int ChangePasswordDao(String account, String oldPassword, String newPassword);

}

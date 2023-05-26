package com.example.studyspringgradle.domain.account.dao;

public interface AccountRepository {
    
    String assignmentAccount(String id, String password);
    
    boolean checkAccountPass(String id, String password);

    boolean checkAccountAvailable(String id);

    int deleteAccount(String id, String password);

}

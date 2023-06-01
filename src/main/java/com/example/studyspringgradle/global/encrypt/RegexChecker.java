package com.example.studyspringgradle.global.encrypt;

public class RegexChecker {
    public static boolean accountChecker(String account) {
        return account.matches("^[a-zA-Z][a-zA-Z0-9]{5,19}$");
    }

    public static boolean passwordChecker(String password) {
        return password.matches("^(?=.+[a-z])(?=.+[A-Z])(?=.+[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,24}$");
    }

}

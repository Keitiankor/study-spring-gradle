package com.example.studyspringgradle.global.encrypt;

public class RegexChecker {
    private static String accountPattern = "[a-zA-z]{1,1}[\\w]{1,19}";
    private static String passwordPattern = "[\\w!@#$%^&*]{1,24}";
    private static String passwordP1 ="[a-z]";
    private static String passwordP2 ="[A-Z]";
    private static String passwordP3 ="[!@#$%^&*]";

    public static boolean accountChecker(String account){
        return account.matches(accountPattern);
    }

    public static boolean passwordChecker(String password){
        if(passwordP1.matches(password)&&passwordP2.matches(password)&&passwordP3.matches(password)){
            return password.matches(passwordPattern);
        }else{
            return false;
        }
    }
    
}

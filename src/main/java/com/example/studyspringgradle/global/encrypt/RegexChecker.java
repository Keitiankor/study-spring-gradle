package com.example.studyspringgradle.global.encrypt;

public class RegexChecker {
    public static boolean accountChecker(String account){
        return account.matches("[a-zA-z]{1,1}[\\w]{1,19}");
    }

    public static boolean passwordChecker(String password){
        if(password.matches("[a-z]")&&password.matches("[A-Z]")&&password.matches("[!@#$%^&*]")){
            return password.matches("[\\w!@#$%^&*]{1,24}");
        }else{
            return false;
        }
    }
    
}

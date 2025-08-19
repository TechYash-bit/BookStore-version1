package com.TechYash_Bit.onlineBookStore.exception;

public class UserNameAndPasswordNotFound extends RuntimeException{
    public UserNameAndPasswordNotFound(String msg){
        super(msg);
    }
}

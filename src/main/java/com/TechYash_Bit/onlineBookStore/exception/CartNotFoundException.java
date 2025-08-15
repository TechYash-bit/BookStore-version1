package com.TechYash_Bit.onlineBookStore.exception;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(String msg){
        super(msg);
    }
}

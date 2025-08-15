package com.TechYash_Bit.onlineBookStore.exception;

public class OrderNotFoundException extends RuntimeException{
    public  OrderNotFoundException(String msg){
        super(msg);
    }
}

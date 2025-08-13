package com.TechYash_Bit.onlineBookStore.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponse <T>{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime localDateTime;

    private T data;
    private ApiError error;


    public ApiResponse() {
        this.localDateTime = LocalDateTime.now();
    }

    ApiResponse(ApiError error){
        this();
        this.error=error;
    }
    ApiResponse(T data){
        this();
        this.data=data;
    }
}

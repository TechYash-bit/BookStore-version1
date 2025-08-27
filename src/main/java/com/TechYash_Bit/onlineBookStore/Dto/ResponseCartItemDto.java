package com.TechYash_Bit.onlineBookStore.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@RequiredArgsConstructor
public class ResponseCartItemDto {

//    private Long id;
    private Long bookId;
    private String title;
    private int quantity;
    private double price;



}


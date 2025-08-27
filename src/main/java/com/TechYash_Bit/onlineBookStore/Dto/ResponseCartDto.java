package com.TechYash_Bit.onlineBookStore.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCartDto {

    private Long cartId;
    private List<ResponseCartItemDto> item;
    private double totalPrice;

}

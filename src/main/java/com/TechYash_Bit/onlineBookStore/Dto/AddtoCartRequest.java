package com.TechYash_Bit.onlineBookStore.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddtoCartRequest {
    Long bookId;
    int quantity;
}

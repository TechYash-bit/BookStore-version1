package com.TechYash_Bit.onlineBookStore.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDto {
    private Long id;
    private String userName;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String role; // CUSTOMER / ADMIN
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ResponseCartItemDto> carts;
    private List<ResponseOrderDto> orders;
}

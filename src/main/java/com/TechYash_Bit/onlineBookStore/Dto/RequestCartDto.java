package com.TechYash_Bit.onlineBookStore.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCartDto {
    @NotNull(message = "User id Must required")
    private Long userId;
    @NotNull(message = "Bookid must required")
    private Long bookId;
    @Positive(message = "Quantity must be positive ")
    private int quantity;
}

package com.TechYash_Bit.onlineBookStore.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestOrderDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    @NotNull(message = "Order date is required ")
    private LocalDateTime orderDate;
    @Positive
    private double totalAmount;
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;

}

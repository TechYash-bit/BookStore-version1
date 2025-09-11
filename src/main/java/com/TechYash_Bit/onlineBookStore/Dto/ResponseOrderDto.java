package com.TechYash_Bit.onlineBookStore.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDto {
    private Long  id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private String orderDate;
    private double totalAmount;
    private Long userId;
    private String paymentStatus;
    private String orderStatus;
    private List<ResponseOrderItemDto> itemDtos;

    public ResponseOrderDto(Long id, String string, double totalPrice, Long id1, String status, List<ResponseOrderItemDto> itemDtos) {
    }


}

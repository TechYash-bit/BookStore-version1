package com.TechYash_Bit.onlineBookStore.Dto;

import com.TechYash_Bit.onlineBookStore.enumClass.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseBookDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private BigDecimal price;
    private int stock;
    private Category category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime updatedAt;


}

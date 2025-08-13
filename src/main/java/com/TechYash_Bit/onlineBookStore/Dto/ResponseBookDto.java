package com.TechYash_Bit.onlineBookStore.Dto;

import com.TechYash_Bit.onlineBookStore.enumClass.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseBookDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private double price;
    private int stock;
    private Category category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime updatedAt;


}

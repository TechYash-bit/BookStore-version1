package com.TechYash_Bit.onlineBookStore.Dto;

import com.TechYash_Bit.onlineBookStore.Validations.ISBNValidation;
import com.TechYash_Bit.onlineBookStore.enumClass.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBookDto {

    @NotBlank(message = "Title required")
    private String title;
    @NotBlank(message = "Author required")
    private String author;
    @NotBlank(message = "Description required")
    @Size(max=200,message = "Decription can't exceed to 200 words ")
    private String description;
    @ISBNValidation
    private String isbn;
    @Positive(message = "Price must be Positive")
    private double price;
    @PositiveOrZero(message = "Quantity must be zero or more than zero")
    private int stock;
    @NotNull(message = "category must required")
    private Category category;
}

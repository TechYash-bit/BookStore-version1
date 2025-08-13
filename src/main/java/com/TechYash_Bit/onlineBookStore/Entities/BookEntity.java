package com.TechYash_Bit.onlineBookStore.Entities;

import com.TechYash_Bit.onlineBookStore.enumClass.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class BookEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Title is required")
        private String title;

        @NotBlank(message = "Author is required")
        private String author;

        @NotBlank(message = "Description is required")
        private String description;

        @NotBlank(message = "ISBN is required")
        private String isbn;

        @Positive(message = "Price must be positive")
        private double price;

        @PositiveOrZero(message = "Stock must be zero or more")
        private int stock;

        @Enumerated(EnumType.STRING) // अगर enum रखना है
        private Category category;

       @CreationTimestamp
        @Column(updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
}

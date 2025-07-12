package com.TechYash_Bit.onlineBookStore.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books") // optional, JPA will default to class name if omitted
public class BookEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;


        private String title;


        private String author;

        private String category;


        private double price;

        private int stock;
    }



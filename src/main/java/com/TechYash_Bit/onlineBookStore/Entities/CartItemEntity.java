package com.TechYash_Bit.onlineBookStore.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartItem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartId",nullable = false)
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "bookId",nullable = false)
    private  BookEntity boook;

    private int Quantity;
    private double price;
}

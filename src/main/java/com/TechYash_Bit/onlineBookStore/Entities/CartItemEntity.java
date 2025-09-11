package com.TechYash_Bit.onlineBookStore.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartItem")
public class CartItemEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
////    @ManyToOne
////    @JoinColumn(name = "cartId",nullable = false)
////    private CartEntity cart;
//@ManyToOne
//@JoinColumn(name = "cart_id")
//private CartEntity cart;
//
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "bookId",nullable = false)
//    private  BookEntity book;
//
//    private int Quantity;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

//    // Many items belong to one cart
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id", nullable = false)
//    private CartEntity cart;
//
//    // Each cart item refers to a book
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "book_id", nullable = false)
//    private BookEntity book;
//
//    // Quantity of this book in the cart
//    @Column(nullable = false)
//    private int quantity;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "cart_id", nullable = false)
private CartEntity cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @Column(nullable = false)
    private int quantity;


    private double price;


}

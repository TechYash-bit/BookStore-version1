package com.TechYash_Bit.onlineBookStore.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private UserEntity user;


    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true ,fetch = FetchType.EAGER)
    private List<CartItemEntity> cartItem;


    private double totalprice;
}

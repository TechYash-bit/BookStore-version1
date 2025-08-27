package com.TechYash_Bit.onlineBookStore.Repositories;

import com.TechYash_Bit.onlineBookStore.Entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItemEntity,Long> {

    List<CartItemEntity> findByCartId(Long id);
}

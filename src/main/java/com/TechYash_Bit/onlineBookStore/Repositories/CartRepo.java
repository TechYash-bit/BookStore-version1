package com.TechYash_Bit.onlineBookStore.Repositories;

import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Entities.CartEntity;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<CartEntity,Long> {

//    Optional<CartEntity> findByUserId(Long id);
//
//    boolean existsByUser_Id(Long userId);
//
//    long deleteByUser_Id(Long userId);
//

    // Fetch cart by user ID
    Optional<CartEntity> findByUser_Id(Long userId);

    // Check if cart exists for user
    boolean existsByUser_Id(Long userId);

    // Delete cart by user ID
    int deleteByUser_Id(Long userId);

}

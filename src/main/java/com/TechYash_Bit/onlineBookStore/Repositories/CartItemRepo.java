package com.TechYash_Bit.onlineBookStore.Repositories;

import com.TechYash_Bit.onlineBookStore.Entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItemEntity,Long> {

//    List<CartItemEntity> findByCartId(Long id);
//
//    CartItemEntity findByCartIdAndBookId(Long id, Long bookId);
//
//    boolean deleteByCartIdAndBookId(Long id, Long bookId);
Optional<CartItemEntity> findByCart_Id(Long cartId);
    List<CartItemEntity> findAllByCart_Id(Long cartId);
//    int deleteByCart_Id(Long cartId);

    int deleteByCart_IdAndBook_Id(Long cartId, Long bookId);
    // Example for book-specific queries
    Optional<CartItemEntity> findByCart_IdAndBook_Id(Long cartId, Long bookId);

}

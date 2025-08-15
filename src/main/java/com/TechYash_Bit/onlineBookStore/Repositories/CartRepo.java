package com.TechYash_Bit.onlineBookStore.Repositories;

import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Entities.CartEntity;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<CartEntity,Long> {

    List<CartEntity> findByUserId(Long id);
    Optional<CartEntity> findByUserIdAndBookId(Long userId, Long bookId);


}

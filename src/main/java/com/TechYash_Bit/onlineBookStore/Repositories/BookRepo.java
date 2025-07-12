package com.TechYash_Bit.onlineBookStore.Repositories;

import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<BookEntity,Integer> {
}

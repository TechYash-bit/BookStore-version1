package com.TechYash_Bit.onlineBookStore.Repositories;

import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepo extends JpaRepository<BookEntity,Long> {
    //@Query("select b.title as title,b.author as author,b.price as price from BookEntity b")
    //List<TitleAutorPriceDto> getBook();
}

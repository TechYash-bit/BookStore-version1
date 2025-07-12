package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.BookDto;
import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.BookRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    final private BookRepo bookRepo;
    private ModelMapper modelMapper;

    BookService(BookRepo bookRepo,ModelMapper modelMapper){
        this.bookRepo=bookRepo;
        this.modelMapper=modelMapper;
    }

    public List<BookDto> getAllBook(){
        List<BookEntity> list=bookRepo.findAll();
        return list.stream()
                .map(entity -> modelMapper.map(entity,BookDto.class) )
                .collect(Collectors.toList());
    }

    public BookDto AddBook(BookDto bookDto) {
        BookEntity bookEntity=modelMapper.map(bookDto,BookEntity.class);
        bookRepo.save(bookEntity);
        return modelMapper.map(bookEntity,BookDto.class);
    }
}

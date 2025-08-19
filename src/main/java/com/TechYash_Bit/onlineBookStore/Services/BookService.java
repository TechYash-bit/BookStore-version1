package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.RequestBookDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseBookDto;
import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.BookRepo;
import com.TechYash_Bit.onlineBookStore.exception.BookNotFoundException;
import com.TechYash_Bit.onlineBookStore.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    final private BookRepo bookRepo;
    @Autowired
    private ModelMapper modelMapper;



    public List<ResponseBookDto> getAllBook(){
        List<BookEntity> list=bookRepo.findAll();
        return list.stream()
                .map(entity -> modelMapper.map(entity, ResponseBookDto.class) )
                .collect(Collectors.toList());
    }

    public ResponseBookDto AddBook(RequestBookDto bookDto) {
        BookEntity bookEntity=modelMapper.map(bookDto,BookEntity.class);
        bookRepo.save(bookEntity);
        return modelMapper.map(bookEntity, ResponseBookDto.class);
    }



    public Optional<ResponseBookDto> findBookById(Long id) {
        return bookRepo.findById(id).map(bookEntity -> modelMapper.map(bookEntity, ResponseBookDto.class));
    }

    public ResponseBookDto updateBook(Long id, RequestBookDto bkdto) {
        BookEntity existing = bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not present with id " + id));

        modelMapper.map(bkdto, existing); // Automatically maps all updatable fields
        return modelMapper.map(bookRepo.save(existing), ResponseBookDto.class);
    }

    public boolean isBookExist(Long id){
        return bookRepo.existsById(id);
    }

    public ResponseBookDto partialUpdate(Long id, Map<String, Object> updates) {
        BookEntity bkentity = bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(BookEntity.class, key);
            if (field != null && !"id".equalsIgnoreCase(key)) { // Prevent ID change
                field.setAccessible(true);
                ReflectionUtils.setField(field, bkentity, value);
            }
        });
        return modelMapper.map(bookRepo.save(bkentity), ResponseBookDto.class);
    }
}

package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.RequestBookDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseBookDto;
import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.BookRepo;
import com.TechYash_Bit.onlineBookStore.exception.ResourseNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    final private BookRepo bookRepo;
    private ModelMapper modelMapper;

    BookService(BookRepo bookRepo,ModelMapper modelMapper){
        this.bookRepo=bookRepo;
        this.modelMapper=modelMapper;
    }

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
        BookEntity bkentity=bookRepo.findById(id)
                .orElseThrow(()->new ResourseNotFoundException("book not present with id "+id));
        bkentity.setTitle(bkdto.getTitle());
        bkentity.setCategory(bkdto.getCategory());
        bkentity.setAuthor(bkdto.getAuthor());
        bkentity.setStock(bkdto.getStock());
        bkentity.setPrice(bkdto.getPrice());
        bookRepo.save(bkentity);

        return modelMapper.map(bkentity, ResponseBookDto.class);

    }
    public boolean isBookExist(Long id){
        return bookRepo.existsById(id);
    }

    public ResponseBookDto partialUpdate(Long id, Map<String, Object> updates) {
        BookEntity bkentity = bookRepo.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Book not found with id " + id));
        updates.forEach((key,value)->{
            Field field= ReflectionUtils.findField(BookEntity.class,key);
            if (field!=null){
                field.setAccessible(true);
                ReflectionUtils.setField(field,bkentity,value);
            }
        });
        return modelMapper.map(bookRepo.save(bkentity), ResponseBookDto.class);
    }
}

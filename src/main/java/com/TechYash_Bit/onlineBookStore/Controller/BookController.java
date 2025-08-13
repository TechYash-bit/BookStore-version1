package com.TechYash_Bit.onlineBookStore.Controller;


import com.TechYash_Bit.onlineBookStore.Dto.RequestBookDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseBookDto;
import com.TechYash_Bit.onlineBookStore.Services.BookService;
import com.TechYash_Bit.onlineBookStore.exception.ResourseNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
@RequiredArgsConstructor
public class BookController {
    final  private BookService bookService;


    @GetMapping
    public ResponseEntity<List<ResponseBookDto>> getAllbook(){
        return ResponseEntity.ok(bookService.getAllBook());
    }

    @PostMapping
    public ResponseEntity<ResponseBookDto> addBook(@Valid @RequestBody RequestBookDto bookDto){
        ResponseBookDto bookDto1=bookService.AddBook(bookDto);
        return new ResponseEntity<>(bookDto1, HttpStatus.CREATED);

    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseBookDto> findBookById(@PathVariable Long id){
        Optional<ResponseBookDto> bookDto= bookService.findBookById(id);
        return bookDto.map(ResponseEntity::ok).orElseThrow(()-> new ResourseNotFoundException("book not found "+id));
        //return ResponseEntity.ok(bookService.findBookById(id)).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseBookDto> updateBookInfo(@PathVariable Long id, @Valid @RequestBody RequestBookDto bkdto){
        return ResponseEntity.ok(bookService.updateBook(id,bkdto));
    }


    @PatchMapping(path = "/{id}")
    public ResponseEntity<ResponseBookDto> partialUpdate(@PathVariable Long id, @Valid @RequestBody Map<String,Object> updates){
        ResponseBookDto bookDto=bookService.partialUpdate(id,updates);
        if (bookDto==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bookDto);
    }
}

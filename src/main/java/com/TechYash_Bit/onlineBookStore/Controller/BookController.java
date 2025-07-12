package com.TechYash_Bit.onlineBookStore.Controller;


import com.TechYash_Bit.onlineBookStore.Dto.BookDto;
import com.TechYash_Bit.onlineBookStore.Services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    final  private BookService bookService;

    BookController(BookService bookService){
        this.bookService=bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllbook(){
        return ResponseEntity.ok(bookService.getAllBook());
    }

    @PostMapping( path = "/addBook")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto){
        BookDto bookDto1=bookService.AddBook(bookDto);
        return new ResponseEntity<>(bookDto1, HttpStatus.CREATED);

    }
}

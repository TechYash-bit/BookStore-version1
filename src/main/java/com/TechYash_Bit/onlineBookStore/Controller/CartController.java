package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.RequestCartDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartDto;
import com.TechYash_Bit.onlineBookStore.Services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ResponseCartDto> addCart(@Valid @RequestBody RequestCartDto cartDto){
        return new ResponseEntity<>(cartService.addCart(cartDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/{cartid}/add-book/{bookid}")
    public ResponseEntity<List<ResponseCartDto>> addBook(@PathVariable Long cartid,@PathVariable Long bookid,@RequestParam int quantity){
        return ResponseEntity.ok(cartService.addBook(cartid,bookid,quantity));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseCartDto> getCart(@PathVariable Long id){
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseCartDto>> getcart(){
        return ResponseEntity.ok(cartService.getcart());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public ResponseEntity<String> deleteAllCart(){
        cartService.deleteAll();
        return ResponseEntity.ok("Cart delete successfully");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByCart(@PathVariable Long id){
        cartService.deleteByCartId(id);
        return ResponseEntity.ok("cart item delete successfully");
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseCartDto> updateCart(@PathVariable Long id,@Valid @RequestBody RequestCartDto cart){
       return ResponseEntity.ok(cartService.updateCart(id,cart));
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<ResponseCartDto>> getCartByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }
}

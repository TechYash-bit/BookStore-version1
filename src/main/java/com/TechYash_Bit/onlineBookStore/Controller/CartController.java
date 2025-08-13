package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.RequestCartDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartDto;
import com.TechYash_Bit.onlineBookStore.Services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/addCart")
    public ResponseEntity<ResponseCartDto> addCart(@RequestBody RequestCartDto cartDto){
        return new ResponseEntity<>(cartService.addCart(cartDto), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCart(){
        cartService.deleteAll();
        return ResponseEntity.ok("Cart delete successfully");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteByCart(@PathVariable Long cartId){
        cartService.deleteByCartId(cartId);
        return ResponseEntity.ok("cart item delete successfully");
    }


}

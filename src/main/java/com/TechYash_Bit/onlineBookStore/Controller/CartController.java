package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.AddtoCartRequest;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartDto;
import com.TechYash_Bit.onlineBookStore.Services.CartService;
import com.TechYash_Bit.onlineBookStore.advices.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> addCart(@RequestBody AddtoCartRequest cart,@PathVariable Long userId){
        cartService.addCart(cart,userId);
        return ResponseEntity.ok(new ApiResponse<>("Book added to cart"));
    }
    @GetMapping(path = "/{userId}")
    public ResponseEntity<ResponseCartDto> viewCar(@PathVariable long userId){
        return ResponseEntity.ok(cartService.viewCart(userId));
    }

}

package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.AddtoCartRequest;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartItemDto;
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

    @GetMapping(path = "/{userId}/{bookId}")
    public ResponseEntity<ResponseCartItemDto> getCartItem(@PathVariable Long userId,@PathVariable Long bookId){
        return ResponseEntity.ok(cartService.getCartItem(userId,bookId));
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long userId){
        boolean isDeleted=cartService.deleteCart(userId);
        if(isDeleted) return ResponseEntity.ok("Cart deleted .....");
        else {
            return  ResponseEntity.ok("cart Not deleted ");
        }
    }

    @DeleteMapping(path = "/{userId}/{bookId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long userId,@PathVariable Long bookId){
        boolean isDeleted=cartService.deleteCartItem(userId,bookId);
        if(isDeleted) return ResponseEntity.ok("cart Item deleted ........");
        else{
            return  ResponseEntity.ok("Cart item not deleted ");
        }
    }

    @PatchMapping ("/{userId}/{bookId}")
    public ResponseEntity<String> updateQuantity(@PathVariable Long userid,@PathVariable Long bookId,@RequestParam(defaultValue = "1") int quantity) {
    cartService.updateCartItem(userid,bookId,quantity);
    return ResponseEntity.ok("updated the quantity of book by "+quantity);
    }


}

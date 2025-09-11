package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.RequestOrderDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseOrderDto;
import com.TechYash_Bit.onlineBookStore.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<ResponseOrderDto> addOrder(@PathVariable long userId){
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

//    @GetMapping(path = "/{userId}")
//    public ResponseEntity<ResponseOrderDto> getOrderByUser(Long userId){
//        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
//    }

}

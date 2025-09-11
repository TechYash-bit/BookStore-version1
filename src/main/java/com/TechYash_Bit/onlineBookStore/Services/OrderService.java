package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.RequestOrderDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseOrderDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseOrderItemDto;
import com.TechYash_Bit.onlineBookStore.Entities.CartEntity;
import com.TechYash_Bit.onlineBookStore.Entities.OrderEntity;
import com.TechYash_Bit.onlineBookStore.Entities.OrderItemEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.*;
import com.TechYash_Bit.onlineBookStore.exception.CartNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Builder
@RequiredArgsConstructor
public class OrderService {
    final private OrderRepo orderRepo;
    final  private ModelMapper modelMapper;
    final private BookRepo bookRepo;
    final private UserRepo userRepo;
    final private CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;





    public ResponseOrderDto placeOrder(Long userId) {
        // 1. Fetch cart for user
        CartEntity cart = cartRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot place order.");
        }

        // 2. Create new order
        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        order.setStatus("PLACED");
        order.setPaymentStatus("PENDING");
        order.setTotalPrice(cart.getTotalprice());
        // If you add timestamp in entity with @CreationTimestamp → orderDate will be auto filled

        // 3. Convert cart items → order items
        List<OrderItemEntity> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItemEntity orderItem = new OrderItemEntity();
                    orderItem.setOrder(order);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice() * cartItem.getQuantity());
                    return orderItem;
                })
                .toList();

        order.setItems(orderItems);

        // 4. Save order
        OrderEntity savedOrder = orderRepo.save(order);

        // 5. Clear cart after placing order
        cartItemRepo.deleteAll(cart.getCartItems());
        cart.setCartItems(List.of());
        cart.setTotalprice(0.0);
        cartRepo.save(cart);

        // 6. Map to Response DTO
        List<ResponseOrderItemDto> itemDtos = savedOrder.getItems().stream()
                .map(item -> new ResponseOrderItemDto(
                        item.getBook().getId(),
                        item.getBook().getTitle(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new ResponseOrderDto(
                savedOrder.getId(),
                savedOrder.getDateTime().toString(),   // requires @CreationTimestamp in entity
                savedOrder.getTotalPrice(),
                savedOrder.getUser().getId(),
                savedOrder.getStatus(),
                itemDtos
        );
    }
//    public ResponseOrderDto getOrderByUserId(Long userId) {
//        Optional<OrderEntity>=orderRepo.findByUserId(userId).
//    }
}

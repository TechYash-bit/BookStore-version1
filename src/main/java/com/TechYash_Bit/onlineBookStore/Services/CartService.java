package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.AddtoCartRequest;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartItemDto;
import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Entities.CartEntity;
import com.TechYash_Bit.onlineBookStore.Entities.CartItemEntity;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.BookRepo;
import com.TechYash_Bit.onlineBookStore.Repositories.CartItemRepo;
import com.TechYash_Bit.onlineBookStore.Repositories.CartRepo;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import com.TechYash_Bit.onlineBookStore.exception.BookNotFoundException;
import com.TechYash_Bit.onlineBookStore.exception.CartNotFoundException;
import com.TechYash_Bit.onlineBookStore.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepo cartRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;
    private final CartItemRepo cartItemRepo;


    public void addCart(AddtoCartRequest cart, Long userId) {

        // 1. Find user
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 2. Find or create cart
        CartEntity cartExist = cartRepo.findByUser_Id(userId).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(user);
           newCart.setCartItems(new ArrayList<>());

            newCart.setTotalprice(0.0);
            return cartRepo.save(newCart);
        });

        // 3. Find book
        BookEntity book = bookRepo.findById(cart.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        // 4. Check if book already in cart
        Optional<CartItemEntity> cartItemOpt = cartExist.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(cart.getBookId()))
                .findFirst();

        if (cartItemOpt.isPresent()) {
            // 5a. If exists → update
            CartItemEntity cartItem = cartItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + cart.getQuantity());
            cartItem.setPrice(cartItem.getQuantity() * book.getPrice());
        } else {
            // 5b. If not → create new cart item
            CartItemEntity newItem = new CartItemEntity();
            newItem.setBook(book);
            newItem.setQuantity(cart.getQuantity());
            newItem.setPrice(cart.getQuantity() * book.getPrice());
            newItem.setCart(cartExist);

            cartExist.getCartItems().add(newItem);
        }
        double total=cartExist.getCartItems().stream().mapToDouble(CartItemEntity::getPrice).sum();
        cartExist.setTotalprice(total);
        // 6. Save updated cart
        cartRepo.save(cartExist);
    }

    public ResponseCartDto viewCart(long userId) {
        CartEntity cart=cartRepo.findByUser_Id(userId).orElseThrow(()->new CartNotFoundException("cart not foundf with userid : "+userId));
        List<ResponseCartItemDto> items = cart.getCartItems().stream()
                .map(item -> new ResponseCartItemDto(
                        item.getBook().getId(),
                        item.getBook().getTitle(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();
        return new ResponseCartDto(cart.getId(),items,cart.getTotalprice());
    }

    public ResponseCartItemDto getCartItem(Long userId, Long bookId) {
        CartEntity cart=cartRepo.findByUser_Id(userId).orElseThrow(()->new CartNotFoundException("cart Not found with  userId : "+userId));
        CartItemEntity cartItemEntity=cartItemRepo.findByCart_IdAndBook_Id(cart.getId(),bookId).orElseThrow(()->new RuntimeException("cart is empty"));
        ResponseCartItemDto responseCartItemDto= modelMapper.map(cartItemEntity,ResponseCartItemDto.class);
        responseCartItemDto.setTitle(cartItemEntity.getBook().getTitle());
        return responseCartItemDto;
    }

    public boolean deleteCart(Long userId) {
        boolean exist=cartRepo.existsByUser_Id(userId);
        if(exist){
            long delete=cartRepo.deleteByUser_Id(userId);
            if(delete==1)return true;
            return  false;
        }
        else {
            throw new CartNotFoundException("Cart not found");
        }
    }

    public boolean deleteCartItem(Long userId, Long bookId) {
        boolean exist=cartRepo.existsByUser_Id(userId);
        if(exist){
            CartEntity cart=cartRepo.findByUser_Id(userId).get();
            int delete=cartItemRepo.deleteByCart_IdAndBook_Id(cart.getId(),bookId);
            return delete>=1;

        }else{
            throw new CartNotFoundException("cart Not found ");
        }
    }

    public boolean updateCartItem(Long userid, Long bookId, int quantity) {
        CartEntity cart=cartRepo.findByUser_Id(userid).orElseThrow(()-> new CartNotFoundException("cart Not found"));
        CartItemEntity item=cartItemRepo.findByCart_IdAndBook_Id(cart.getId(),bookId).orElseThrow();
        if(item==null) throw  new RuntimeException("cart item not found");
        item.setQuantity(item.getQuantity()+quantity);
        cartItemRepo.save(item);
        return true;
    }


//

}

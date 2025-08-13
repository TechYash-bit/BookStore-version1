package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.RequestCartDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartDto;
import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Entities.CartEntity;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.BookRepo;
import com.TechYash_Bit.onlineBookStore.Repositories.CartRepo;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepo cartRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;

    public ResponseCartDto addCart(RequestCartDto cartDto) {
        UserEntity user=userRepo.findById(cartDto.getUserId()).orElseThrow(()->new RuntimeException("User Invalid : "+cartDto.getUserId()));
        BookEntity book=bookRepo.findById(cartDto.getBookId()).orElseThrow(()-> new RuntimeException("book invalid : "+cartDto.getBookId()));
        if(book.getStock()<cartDto.getQuantity()){
            throw new RuntimeException("Only "+book.getStock()+" are avaible in the stock");
        }
        CartEntity existingCart = cartRepo.findByUserAndBook(user, book).orElse(null);
        if(existingCart!=null){
            int newQuantity=existingCart.getQuantity()+cartDto.getQuantity();
            if(newQuantity<book.getStock()){
            existingCart.setQuantity(newQuantity);
            existingCart.setPrice(newQuantity*book.getPrice());
            return modelMapper.map(cartRepo.save(existingCart), ResponseCartDto.class);
            }
        }
        CartEntity cart=modelMapper.map(cartDto,CartEntity.class);
        cart.setUser(user);
        cart.setBook(book);
        cart.setPrice(cart.getQuantity()*book.getPrice());
        return modelMapper.map(cartRepo.save(cart), ResponseCartDto.class);

    }

    public void deleteAll() {
        cartRepo.deleteAll();
    }

    public void deleteByCartId(Long cartId) {
        cartRepo.deleteById(cartId);
    }
}

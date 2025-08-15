package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.RequestCartDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseCartDto;
import com.TechYash_Bit.onlineBookStore.Entities.BookEntity;
import com.TechYash_Bit.onlineBookStore.Entities.CartEntity;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.BookRepo;
import com.TechYash_Bit.onlineBookStore.Repositories.CartRepo;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import com.TechYash_Bit.onlineBookStore.exception.BookNotFoundException;
import com.TechYash_Bit.onlineBookStore.exception.CartNotFoundException;
import com.TechYash_Bit.onlineBookStore.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepo cartRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;

    public ResponseCartDto addCart(RequestCartDto cartDto) {
        UserEntity user=userRepo.findById(cartDto.getUserId()).orElseThrow(()->new UserNotFoundException("User Invalid : "+cartDto.getUserId()));
        BookEntity book=bookRepo.findById(cartDto.getBookId()).orElseThrow(()-> new UserNotFoundException("book invalid : "+cartDto.getBookId()));
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
        CartEntity cart=new CartEntity();
        cart.setUser(user);
        cart.setBook(book);
        cart.setQuantity(cartDto.getQuantity());
        cart.setPrice(cartDto.getQuantity()*book.getPrice());
        return modelMapper.map(cartRepo.save(cart), ResponseCartDto.class);

    }

    public void deleteAll() {
        cartRepo.deleteAll();
    }

    public void deleteByCartId(Long cartId) {
        cartRepo.deleteById(cartId);
    }

    public ResponseCartDto getCart(Long id) {
        return modelMapper.map(cartRepo.findById(id),ResponseCartDto.class);
    }

    public List<ResponseCartDto> getcart() {
        List<CartEntity> list=cartRepo.findAll();
        return list.stream().map(cartEntity -> modelMapper.map(cartEntity,ResponseCartDto.class)).toList();
    }

    public ResponseCartDto updateCart(Long id, @Valid RequestCartDto cart) {
        CartEntity cart1=cartRepo.findById(id).orElseThrow(()-> new UserNotFoundException("Cart with does not exist : "+id));
        UserEntity user=userRepo.findById(cart.getUserId()).orElseThrow(()->new CartNotFoundException("User with id : "+cart.getUserId()+" not present "));
        BookEntity book=bookRepo.findById(cart.getBookId()).orElseThrow(()->new CartNotFoundException("Book with id "+cart.getBookId()+" not found "));

        cart1.setUser(user);
        cart1.setBook(book);
        cart1.setQuantity(cart.getQuantity());
        cart1.setPrice(cart.getQuantity()*book.getPrice());

        return modelMapper.map(cartRepo.save(cart1),ResponseCartDto.class);

    }

    public List<ResponseCartDto> getCartByUserId(Long userId) {
        List<CartEntity> cartEntityList=cartRepo.findByUserId(userId);
        return cartEntityList.stream().map(cartEntity -> modelMapper.map(cartEntity,ResponseCartDto.class)).toList();
    }

    public List<ResponseCartDto> addBook(Long cartid, Long bookid, int quantity) {
        CartEntity cartEntity =cartRepo.findById(cartid).orElseThrow(()->new CartNotFoundException("Cart not  found with id : "+cartid));
        BookEntity bookEntity=bookRepo.findById(bookid).orElseThrow(()->new BookNotFoundException("Book not found with id :"+bookid));
        UserEntity user=cartEntity.getUser();

        if(bookEntity.getStock()<quantity) {
            throw new RuntimeException("Not enough stock present ");
        }
        CartEntity existingCart= cartRepo.findByUserIdAndBookId(user.getId(), bookEntity.getId()).orElse(null);
        if(existingCart!=null){
            existingCart.setQuantity(existingCart.getQuantity()+quantity);
            existingCart.setPrice(existingCart.getQuantity()*bookEntity.getPrice());
            cartRepo.save(existingCart);
        }
        else{
            CartEntity newCart=new CartEntity();
            newCart.setBook(bookEntity);
            newCart.setUser(user);
            newCart.setQuantity(quantity);
            newCart.setPrice(bookEntity.getPrice()*newCart.getQuantity());
            cartRepo.save(newCart);
        }
        bookEntity.setStock(bookEntity.getStock()-quantity);

        List<CartEntity> cartEntityList=cartRepo.findByUserId(user.getId());
        return cartEntityList.stream().map(cartEntity1 -> modelMapper.map(cartEntity1,ResponseCartDto.class)).toList();


    }
}

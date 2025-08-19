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


}

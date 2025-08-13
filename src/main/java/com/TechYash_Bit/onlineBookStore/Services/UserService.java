package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.RequestUserDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseUserDto;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
    final private UserRepo userRepo;
    final private ModelMapper modelMapper;

    public ResponseUserDto registerUser(RequestUserDto userDto) {
        UserEntity userEntity= modelMapper.map(userDto,UserEntity.class);
        if (userRepo.findByEmail(userEntity.getEmail()).isPresent()) {
          throw new RuntimeException("User with this email Already exists");
        }
        userRepo.save(userEntity);
        return modelMapper.map(userEntity, ResponseUserDto.class);

    }

    public ResponseUserDto getUserById(Long id) {
        UserEntity user=userRepo.findById(id).orElseThrow(()->new RuntimeException("User with userid: "+id+" not present"));
        return modelMapper.map(user,ResponseUserDto.class);
    }

    public List<ResponseUserDto> getAllUser() {
        List<UserEntity> users=userRepo.findAll();
        return users
                .stream()
                .map(userEntity -> modelMapper.map(userEntity,ResponseUserDto.class))
                .collect(Collectors.toList());
    }

    public ResponseUserDto updateUser(long id, @Valid RequestUserDto user) {
      UserEntity existingUser=userRepo.findById(id).orElseThrow(()->new RuntimeException("user not present with this id : "+id));
      UserEntity userWithSameEmail=userRepo.findByEmail(user.getEmail()).orElse(null);
        if (userWithSameEmail != null && !userWithSameEmail.getId().equals(id)) {
            throw new RuntimeException("Email already in use by another user");
        }
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        return modelMapper.map(userRepo.save(existingUser),ResponseUserDto.class);
    }
}

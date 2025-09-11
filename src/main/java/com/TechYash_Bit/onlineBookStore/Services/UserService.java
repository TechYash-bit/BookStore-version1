package com.TechYash_Bit.onlineBookStore.Services;

import com.TechYash_Bit.onlineBookStore.Dto.RequestUserDto;
import com.TechYash_Bit.onlineBookStore.Dto.ResponseUserDto;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import com.TechYash_Bit.onlineBookStore.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
        UserEntity user=userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User with userid: "+id+" not present"));
        return modelMapper.map(user,ResponseUserDto.class);
    }

    public List<ResponseUserDto> getAllUser() {
        List<UserEntity> users=userRepo.findAll();
        return users
                .stream()
                .map(userEntity -> modelMapper.map(userEntity,ResponseUserDto.class))
                .collect(Collectors.toList());
    }

    public ResponseUserDto updateUser(long id, @Valid RequestUserDto userDto) {
        UserEntity existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not present with this id : " + id));

        // Check for email uniqueness
        UserEntity userWithSameEmail = userRepo.findByEmail(userDto.getEmail()).orElse(null);
        if (userWithSameEmail != null && !userWithSameEmail.getId().equals(id)) {
            throw new RuntimeException("Email already in use by another user");
        }

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setAddress(userDto.getAddress());

        return modelMapper.map(userRepo.save(existingUser), ResponseUserDto.class);
    }

    public ResponseUserDto updateInfo(long id, Map<String, Object> updates) {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not present with : " + id));

        updates.forEach((key, value) -> {
            if (key.equalsIgnoreCase("email")) {
                UserEntity userWithSameEmail = userRepo.findByEmail(value.toString()).orElse(null);
                if (userWithSameEmail != null && !userWithSameEmail.getId().equals(id)) {
                    throw new RuntimeException("Email already in use by another user");
                }
            }

            Field field = ReflectionUtils.findField(UserEntity.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, userEntity, value);
            }
        });

        return modelMapper.map(userRepo.save(userEntity), ResponseUserDto.class);
    }

    public UserEntity getUserByUserName(String username) {
        UserEntity user=userRepo.findByUserName(username).orElseThrow(()->new UserNotFoundException("user not found with user name "+username));
    return  user;
    }

//    public UserEntity getUserByEmail(String email) {
//        UserEntity user=userRepo.findByEmail(email).orElse(null);
//        return modelMapper.map(user,UserEntity.class);
//    }

    public UserEntity getUserByEmail(String email) {
        UserEntity user = userRepo.findByEmail(email).orElse(null);
        if (user == null) {
            return null; // or throw exception
        }
        return user;
    }
    public UserEntity save(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }
}

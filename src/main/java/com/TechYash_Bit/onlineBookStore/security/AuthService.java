package com.TechYash_Bit.onlineBookStore.security;

import com.TechYash_Bit.onlineBookStore.Dto.LoginRequestDto;
import com.TechYash_Bit.onlineBookStore.Dto.LoginResponseDto;
import com.TechYash_Bit.onlineBookStore.Dto.RequestUserDto;
import com.TechYash_Bit.onlineBookStore.Dto.SignUpResponseDto;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import com.TechYash_Bit.onlineBookStore.exception.EmailPresentException;
import com.TechYash_Bit.onlineBookStore.exception.UserNameAndPasswordNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final jwtTokens authUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),loginRequestDto.getPassword())
        );
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNameAndPasswordNotFound("Invalid username or password");
        }
        UserEntity user=(UserEntity) authentication.getPrincipal();
        String token=authUtil.genrateJwtToken(user);
        return  new LoginResponseDto(token,user.getUsername());
    }

    public SignUpResponseDto signup(RequestUserDto signup) {
        if (userRepo.existsByUserName(signup.getUserName())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepo.existsByEmail(signup.getEmail())) {
            throw new EmailPresentException("Email already registered");
        }
        UserEntity user=modelMapper.map(signup,UserEntity.class);
        user.setPassword(passwordEncoder.encode(signup.getPassword()));

         return modelMapper.map(userRepo.save(user), SignUpResponseDto.class);

    }
}

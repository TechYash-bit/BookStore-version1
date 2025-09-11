package com.TechYash_Bit.onlineBookStore.security;

import com.TechYash_Bit.onlineBookStore.Dto.LoginRequestDto;
import com.TechYash_Bit.onlineBookStore.Dto.LoginResponseDto;
import com.TechYash_Bit.onlineBookStore.Dto.RequestUserDto;
import com.TechYash_Bit.onlineBookStore.Dto.SignUpResponseDto;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import com.TechYash_Bit.onlineBookStore.Services.UserService;
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
    private final JwtTokens authUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService user;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),loginRequestDto.getPassword())
        );
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ArithmeticException("Invalid username or password");
        }
        UserEntity user=(UserEntity) authentication.getPrincipal();
        String accesstoken=authUtil.genrateAccessToken(user);
        String refreshtoken =authUtil.genrateRefreshToken(user);
        sessionService.genrateNewSession(user,refreshtoken);

        return  new LoginResponseDto(accesstoken,refreshtoken,user.getUsername());
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

    public LoginResponseDto refreshtoken(String refereshtoken) {
        String username= authUtil.getUserNameFromeToken(refereshtoken);
        sessionService.isvalidate(refereshtoken);
        UserEntity user1=user.getUserByUserName(username);
        String accessToken= authUtil.genrateAccessToken(user1);

        return new LoginResponseDto(accessToken,refereshtoken,user1.getUsername());
    }
}

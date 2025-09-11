package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.LoginRequestDto;
import com.TechYash_Bit.onlineBookStore.Dto.LoginResponseDto;
import com.TechYash_Bit.onlineBookStore.Dto.RequestUserDto;
import com.TechYash_Bit.onlineBookStore.Dto.SignUpResponseDto;
import com.TechYash_Bit.onlineBookStore.security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
//    private TokenBlacklistService blacklistService;

    @Value("${dev.env}")
    private String dev;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest,
                                                  HttpServletResponse httpServletResponse){

        LoginResponseDto loginResponseDto=authService.login(loginRequestDto);
        Cookie cookie=new Cookie("refreshtoken",loginResponseDto.getRefreshtoken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(dev));
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<LoginResponseDto> getRefreshToken(HttpServletRequest request){
       String refereshtoken= Arrays.stream(request.getCookies())
                .filter(cookie->"refreshtoken".equals(cookie.getName())).findFirst()
               .map(cookie -> cookie.getValue())
                .orElseThrow(()->new AuthenticationServiceException("refresh token not found"));

       LoginResponseDto loginResponseDto=authService.refreshtoken(refereshtoken);
       return ResponseEntity.ok(loginResponseDto);

    }

    @PostMapping(path = "/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody RequestUserDto  signup){
        return ResponseEntity.ok(authService.signup(signup));
    }


//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
//        String actualToken = token.replace("Bearer ", "");
//        blacklistService.blacklistToken(actualToken); // store token in DB/Cache until expiration
//        return ResponseEntity.ok("Logged out successfully");
//    }
}

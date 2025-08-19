package com.TechYash_Bit.onlineBookStore.Controller;

import com.TechYash_Bit.onlineBookStore.Dto.LoginRequestDto;
import com.TechYash_Bit.onlineBookStore.Dto.LoginResponseDto;
import com.TechYash_Bit.onlineBookStore.Dto.RequestUserDto;
import com.TechYash_Bit.onlineBookStore.Dto.SignUpResponseDto;
import com.TechYash_Bit.onlineBookStore.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody RequestUserDto  signup){
        return ResponseEntity.ok(authService.signup(signup));
    }
}

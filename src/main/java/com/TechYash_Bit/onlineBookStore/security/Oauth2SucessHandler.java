package com.TechYash_Bit.onlineBookStore.security;

import com.TechYash_Bit.onlineBookStore.Dto.ResponseUserDto;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2SucessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtTokens authUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${dev.env}")
    private String dev;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        UserEntity user = userService.getUserByEmail(email);

        if (user == null) {
            user = UserEntity.builder()
                    .userName(email)
                    .email(email)
                    .name(oAuth2User.getAttribute("name"))
                    .address(oAuth2User.getAttribute("address"))
                    .password(passwordEncoder.encode("oauth2user"))
                    .phone("N/A") // default phone
                    .role("USER")// may be null
                    .build();

            user = userService.save(user);
        }

        // generate tokens
        String accessToken = authUtil.genrateAccessToken(user);
        String refreshToken = authUtil.genrateRefreshToken(user);

        Cookie cookie = new Cookie("refreshtoken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(dev));
        response.addCookie(cookie);

        String url = "http://localhost:8080?refreshToken=" + accessToken;
        response.sendRedirect(url);
    }

}

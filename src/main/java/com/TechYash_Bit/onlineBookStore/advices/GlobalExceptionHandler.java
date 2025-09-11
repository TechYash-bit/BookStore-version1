package com.TechYash_Bit.onlineBookStore.advices;

import com.TechYash_Bit.onlineBookStore.exception.*;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResponseNotFound(UserNotFoundException exception){
        ApiError apiError=ApiError.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
        return buildErrorResponseEntity(apiError);

    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleBookNotFound(BookNotFoundException e){
        ApiError apiError=ApiError.builder().status(HttpStatus.NOT_FOUND).message(e.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleOrderNotFound(OrderNotFoundException e){
        ApiError api=ApiError.builder().status(HttpStatus.NOT_FOUND).message(e.getMessage()).build();
        return buildErrorResponseEntity(api);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleCarNotFound(CartNotFoundException cart){
        ApiError apiError=ApiError.builder().status(HttpStatus.NOT_FOUND).message(cart.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }
    @ExceptionHandler(UserNameAndPasswordNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleUserNameAndPassword(UserNameAndPasswordNotFound userNameAndPasswordNotFound){
        ApiError api=ApiError.builder().message(userNameAndPasswordNotFound.getMessage()).status(HttpStatus.NOT_FOUND).build();
        return buildErrorResponseEntity(api);
    }
    @ExceptionHandler(EmailPresentException.class)
    public ResponseEntity<ApiResponse<?>> handleEmailPresentException(EmailPresentException email){
        ApiError api=ApiError.builder().status(HttpStatus.NOT_FOUND).message(email.getMessage()).build();
        return buildErrorResponseEntity(api);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception){
        ApiError apiError=ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
        return  buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex){
        ApiError api=ApiError.builder().status(HttpStatus.UNAUTHORIZED).message(ex.getLocalizedMessage()).build();
        return buildErrorResponseEntity(api);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(JwtException ex){
        ApiError api=ApiError.builder().status(HttpStatus.UNAUTHORIZED).message(ex.getLocalizedMessage()).build();
        return buildErrorResponseEntity(api);
    }
    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }
}

package com.TechYash_Bit.onlineBookStore.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ISBNValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ISBNValidation {

    String message() default "Invalid ISBN format (must be a valid ISBN-13)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

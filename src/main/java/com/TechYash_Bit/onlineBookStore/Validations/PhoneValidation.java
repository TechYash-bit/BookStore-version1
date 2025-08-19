package com.TechYash_Bit.onlineBookStore.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneValidator.class) // Link to the validator class
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneValidation {

    String message() default "Phone must be a valid 10-digit number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

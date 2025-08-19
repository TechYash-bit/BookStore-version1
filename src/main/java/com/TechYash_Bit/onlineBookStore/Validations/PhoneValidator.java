package com.TechYash_Bit.onlineBookStore.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneValidation,String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) {
            return false; // null values are invalid
        }
        return phone.matches("^[0-9]{10}$"); // Only 10-digit numbers allowed
    }
}

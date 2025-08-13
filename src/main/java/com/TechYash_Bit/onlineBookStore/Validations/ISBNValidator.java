package com.TechYash_Bit.onlineBookStore.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBNValidation,String> {
    private static final String ISBN_13_REGEX =
            "^(?:ISBN(?:-13)?:?\\s)?(?=[0-9]{13}$|" +
                    "(?=(?:[0-9]+[-\\s]){4})[-\\s0-9]{17}$)" +
                    "97[89][-\\s]?[0-9]{1,5}[-\\s]?[0-9]+[-\\s]?[0-9]+[-\\s]?[0-9]$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // null/empty handled by @NotBlank if required
        }
        return value.matches(ISBN_13_REGEX);
    }
}

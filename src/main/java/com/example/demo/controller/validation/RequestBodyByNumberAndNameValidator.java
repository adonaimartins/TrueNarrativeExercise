package com.example.demo.controller.validation;

import com.example.demo.controller.bodyObjects.RequestBodyByNumberAndName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequestBodyByNumberAndNameValidator implements ConstraintValidator<ValidateCompanyByNumberAndNameJson, RequestBodyByNumberAndName>
{

    @Override
    public void initialize(ValidateCompanyByNumberAndNameJson constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RequestBodyByNumberAndName value, ConstraintValidatorContext context) {
        return false;
    }
}

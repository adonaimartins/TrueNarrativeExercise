package com.example.demo.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = RequestBodyByNumberAndNameValidator.class)
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ValidateCompanyByNumberAndNameJson
{
    String message() default "Invalid Company body parameters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.example.bookstore.validation;

import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.validation.validators.RepeatedPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RepeatedPasswordValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatedPasswordMatch {
    String message() default "Repeated password doesn't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Throwable> exception() default RegistrationException.class;
}

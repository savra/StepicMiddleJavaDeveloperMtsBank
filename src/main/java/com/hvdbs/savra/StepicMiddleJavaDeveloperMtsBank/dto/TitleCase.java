package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TitleValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TitleCase {
    Language language() default Language.ANY;
    String message() default "Invalid title";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    enum Language {
        RU,
        EN,
        ANY
    }
}

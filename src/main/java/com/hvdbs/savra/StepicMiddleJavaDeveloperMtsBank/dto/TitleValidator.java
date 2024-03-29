package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto.TitleCase.Language.ANY;
import static com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto.TitleCase.Language.EN;

public class TitleValidator implements ConstraintValidator<TitleCase, String> {
    private TitleCase.Language language;
    private final Pattern pattern = Pattern.compile("^([А-Яа-я\"',:]+( [А-Яа-я\"',:]+)*|[A-Za-z\"',:]+( [A-Za-z\"',:]+)*)$");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!pattern.matcher(s).matches()) {
            return false;
        }

        String[] words = s.split(" ");

        return switch (language) {
            case RU -> {
                if (words[0].charAt(0) < 'А' || words[0].charAt(0) > 'Я') {
                    yield false;
                }

                for (int i = 1; i < words.length; i++) {
                    char firstLetter = words[i].charAt(0);
                    if (firstLetter < 'а' || firstLetter > 'я') {
                        yield false;
                    }
                }

                yield true;
            }
            case EN -> {
                if (words[0].charAt(0) < 'A'
                        || words[0].charAt(0) > 'Z'
                        || words[words.length - 1].charAt(0) < 'A'
                        || words[words.length - 1].charAt(0) > 'Z') {
                    yield false;
                }

                yield true;
            }
            case ANY -> true;
            default -> false;
        };
    }

    @Override
    public void initialize(TitleCase constraintAnnotation) {
        language = constraintAnnotation.language();
    }
}

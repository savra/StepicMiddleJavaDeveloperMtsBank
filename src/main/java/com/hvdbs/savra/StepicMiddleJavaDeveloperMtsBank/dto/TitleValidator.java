package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class TitleValidator implements ConstraintValidator<TitleCase, String> {
    private TitleCase.Language language;
    private static final Pattern pattern = Pattern.compile("^([А-Яа-я\"',:]+( [А-Яа-я\"',:]+)*|[A-Za-z\"',:]+( [A-Za-z\"',:]+)*)$");
    private static final Set<String> prepositions = Set.of("a", "but", "for", "or", "not", "the", "an");
    private static final Predicate<String> startWithUpperCaseEN = (source) -> source.charAt(0) >= 'A' && source.charAt(0) <= 'Z';
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!pattern.matcher(s).matches()) {
            return false;
        }

        String[] words = s.split(" ");

        return switch (language) {
            case RU -> {
                if (!(words[0].charAt(0) >= 'А' && words[0].charAt(0) <= 'Я')) {
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
                if (!(startWithUpperCaseEN.test(words[0]) && startWithUpperCaseEN.test(words[words.length - 1]))) {
                    yield false;
                }

                for (String word : words) {
                    if (prepositions.contains(word.toLowerCase())) {
                        if (startWithUpperCaseEN.test(word)) {
                            yield false;
                        }
                    } else {
                        if (!startWithUpperCaseEN.test(word)) {
                            yield false;
                        }
                    }
                }

                yield true;
            }
            case ANY -> true;
        };
    }

    @Override
    public void initialize(TitleCase constraintAnnotation) {
        language = constraintAnnotation.language();
    }
}

package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TitleValidatorTest {
    @Mock
    private TitleCase titleCase;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    private static final TitleValidator titleValidator = new TitleValidator();

    @DisplayName("Пустая строка не валидна")
    @Test
    void emptyString() {
        when(titleCase.language()).thenReturn(TitleCase.Language.ANY);
        titleValidator.initialize(titleCase);
        assertFalse(titleValidator.isValid("", constraintValidatorContext));
    }

    @DisplayName("Есть русские и английские символы в строке")
    @Test
    void characterMixing() {
        when(titleCase.language()).thenReturn(TitleCase.Language.ANY);
        titleValidator.initialize(titleCase);
        assertFalse(titleValidator.isValid("fff дд", constraintValidatorContext));
    }

    @DisplayName("Строка содержит табуляцию")
    @Test
    void stringContainsIllegalCharacters() {
        when(titleCase.language()).thenReturn(TitleCase.Language.ANY);
        titleValidator.initialize(titleCase);
        assertFalse(titleValidator.isValid("ff   f", constraintValidatorContext));
    }

    @DisplayName("Строка содержит перевод строки")
    @Test
    void stringContainsLineBreak() {
        when(titleCase.language()).thenReturn(TitleCase.Language.ANY);
        titleValidator.initialize(titleCase);
        assertFalse(titleValidator.isValid("f" + "\r\n" + "f", constraintValidatorContext));
    }

    @DisplayName("Строка содержит больше одного пробела между словами")
    @Test
    void stringContainsTwoSpacesBetweenWords() {
        when(titleCase.language()).thenReturn(TitleCase.Language.ANY);
        titleValidator.initialize(titleCase);
        assertFalse(titleValidator.isValid("ffff  kkkkk", constraintValidatorContext));
    }

    @DisplayName("Строка начинается с пробела")
    @Test
    void stringStartWithSpace() {
        when(titleCase.language()).thenReturn(TitleCase.Language.ANY);
        titleValidator.initialize(titleCase);
        assertFalse(titleValidator.isValid(" kkkkk", constraintValidatorContext));
    }

    @DisplayName("Строка заканчивается пробелом")
    @Test
    void stringEndWithSpace() {
        when(titleCase.language()).thenReturn(TitleCase.Language.ANY);
        titleValidator.initialize(titleCase);
        assertFalse(titleValidator.isValid("яяяя ", constraintValidatorContext));
    }

    @DisplayName("Английский заголовок. Первое слово написано со строчной буквы")
    @Test
    void stringStartWithLowerCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.EN);
        titleValidator.initialize(titleCase);

        assertFalse(titleValidator.isValid("ffff GGGG", constraintValidatorContext));
    }

    @DisplayName("Английский заголовок. Последнее слово написано со строчной буквы")
    @Test
    void lastWordStartWithLowerCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.EN);
        titleValidator.initialize(titleCase);

        assertFalse(titleValidator.isValid("GGGG ffff", constraintValidatorContext));
    }

    @DisplayName("Английский заголовок. Первое и последнее слово написаны с прописной буквы")
    @Test
    void firstAndLastWordsStartWithUpperCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.EN);
        titleValidator.initialize(titleCase);

        assertTrue(titleValidator.isValid("GGGG Ffff", constraintValidatorContext));
    }

    @DisplayName("Английский заголовок. Первое и последнее слово написаны с прописной и есть предлоги написанные с прописной буквы")
    @Test
    void firstAndLastWordsStartWithUpperCaseAndExistsPrepositionsStartWithUpperCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.EN);
        titleValidator.initialize(titleCase);

        assertFalse(titleValidator.isValid("GGGG For Ffff", constraintValidatorContext));
    }

    @DisplayName("Английский заголовок. Первое и последнее слово написаны с прописной и все предлоги написанные со строчной буквы")
    @Test
    void firstAndLastWordsStartWithUpperCaseAndNotExistsPrepositionsStartWithUpperCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.EN);
        titleValidator.initialize(titleCase);

        assertTrue(titleValidator.isValid("GGGG or Ffff but QqqqQ", constraintValidatorContext));
    }

    @DisplayName("Русский заголовок. Первое слово написано с прописной")
    @Test
    void firstWordStartWithLowerCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.RU);
        titleValidator.initialize(titleCase);

        assertFalse(titleValidator.isValid("яЯЯ БББ", constraintValidatorContext));
    }

    @DisplayName("Русский заголовок. Первое слово написано с прописной, остальные с прописной тоже")
    @Test
    void firstAndSomeOtherWordsStartWithUpperCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.RU);
        titleValidator.initialize(titleCase);

        assertFalse(titleValidator.isValid("ЯЯЯ БББ", constraintValidatorContext));
    }

    @DisplayName("Русский заголовок. Первое слово написано с прописной, остальные с прописной тоже")
    @Test
    void firstWordStartWithUpperCaseAndOtherWordsStartWithLowerCase() {
        when(titleCase.language()).thenReturn(TitleCase.Language.RU);
        titleValidator.initialize(titleCase);

        assertTrue(titleValidator.isValid("Яяя бб или ввввв но аааа", constraintValidatorContext));
    }
}
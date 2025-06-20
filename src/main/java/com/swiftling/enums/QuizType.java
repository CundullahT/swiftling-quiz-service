package com.swiftling.enums;

import com.swiftling.exception.UnknownQuizTypeException;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum QuizType {

    LEARNED_ONLY("Learned Only"), IN_PROGRESS_ONLY("In Progress Only"), ALL_MIXED("All Mixed");

    private final String value;

    QuizType(String value) {
        this.value = value;
    }

    private static final Map<String,QuizType> BY_VALUE =
            Stream.of(values())
                    .collect(Collectors.toMap(QuizType::getValue, s -> s));

    public static QuizType findByValue(String value) {
        QuizType quizType = BY_VALUE.get(value);
        if (quizType == null) {
            throw new UnknownQuizTypeException("Unknown Quiz Type: " + value);
        }
        return quizType;
    }

}

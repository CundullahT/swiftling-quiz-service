package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swiftling.enums.Language;
import com.swiftling.enums.QuizType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizHistoryDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID externalQuizResultId;

    @NotNull(message = "Date is a required field.")
    private LocalDate date;

    @NotNull(message = "Quiz Type is a required field.")
    private QuizType quizType;

    @NotNull(message = "Language is a required field.")
    private Language language;

    @NotNull(message = "Correct Answer Amount is a required field.")
    private Integer correctAnswerAmount;

    @NotNull(message = "Wrong Answer Amount is a required field.")
    private Integer wrongAnswerAmount;

    @NotNull(message = "Timed Out Answer Amount is a required field.")
    private Integer timedOutAnswerAmount;

    @NotNull(message = "Best Time is a required field.")
    private Integer bestTimeInSeconds;

    @NotNull(message = "Worst Time is a required field.")
    private Integer worstTimeInSeconds;

    @NotNull(message = "Learned Phrases is a required field.")
    private Map<UUID, Integer> learnedPhrases;

    @NotNull(message = "In Progress Phrases is a required field.")
    private Map<UUID, Integer> inProgressPhrases;

}

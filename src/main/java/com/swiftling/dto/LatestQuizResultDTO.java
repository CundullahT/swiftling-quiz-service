package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LatestQuizResultDTO {

    @NotNull(message = "Correct Answer Amount is a required field.")
    private Integer correctAnswerAmount;

    @NotNull(message = "Wrong Answer Amount is a required field.")
    private Integer wrongAnswerAmount;

    @NotNull(message = "Timed Out Answer Amount is a required field.")
    private Integer timedOutAnswerAmount;

}

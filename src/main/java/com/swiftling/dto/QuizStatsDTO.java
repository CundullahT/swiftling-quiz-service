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
public class QuizStatsDTO {

    @NotNull(message = "Overall Best Time In Seconds is a required field.")
    private Integer overallBestTimeInSeconds;

    @NotNull(message = "Latest Best Time In Seconds is a required field.")
    private Integer latestBestTimeInSeconds;

    @NotNull(message = "Latest Quiz Results is a required field.")
    private LatestQuizResultDTO latestQuizResults;

}

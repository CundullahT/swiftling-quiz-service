package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhraseStatusUpdateDTO {

    @NotNull(message = "Learned Phrases is a required field.")
    private Map<UUID, Integer> learnedPhrases;

    @NotNull(message = "In Progress Phrases is a required field.")
    private Map<UUID, Integer> inProgressPhrases;

}

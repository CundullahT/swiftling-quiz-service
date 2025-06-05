package com.swiftling.client;

import com.swiftling.dto.PhraseResponseDTO;
import com.swiftling.dto.PhraseStatusUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "swiftling-phrase-service")
public interface PhraseClient {

    @PutMapping("/api/v1/phrase/quiz-result")
    ResponseEntity<PhraseResponseDTO> changePhraseStatuses(@RequestBody @Valid PhraseStatusUpdateDTO statusUpdateDTO);

}

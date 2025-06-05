package com.swiftling.service.impl;

import com.swiftling.client.PhraseClient;
import com.swiftling.client.UserAccountClient;
import com.swiftling.dto.PhraseResponseDTO;
import com.swiftling.dto.QuizResultDTO;
import com.swiftling.dto.UserAccountResponseDTO;
import com.swiftling.entity.QuizResult;
import com.swiftling.enums.Language;
import com.swiftling.enums.QuizType;
import com.swiftling.exception.ExternalIdNotRetrievedException;
import com.swiftling.exception.PhraseStatusesCouldNotBeUpdatedException;
import com.swiftling.repository.QuizRepository;
import com.swiftling.service.QuizService;
import com.swiftling.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final MapperUtil mapperUtil;
    private final PhraseClient phraseClient;
    private final UserAccountClient userAccountClient;

    public QuizServiceImpl(QuizRepository quizRepository, MapperUtil mapperUtil, PhraseClient phraseClient, UserAccountClient userAccountClient) {
        this.quizRepository = quizRepository;
        this.mapperUtil = mapperUtil;
        this.phraseClient = phraseClient;
        this.userAccountClient = userAccountClient;
    }

    @Override
    public QuizResultDTO save(QuizResultDTO quizResultDTO) {

        QuizResult quizResultToSave = mapperUtil.convert(quizResultDTO, new QuizResult());

        quizResultToSave.setExternalQuizResultId(quizResultDTO.getExternalQuizResultId());
        quizResultToSave.setLanguage(Language.findByValue(quizResultDTO.getLanguage()));
        quizResultToSave.setQuizType(QuizType.findByValue(quizResultDTO.getQuizType()));
        quizResultToSave.setDate(LocalDate.now());
        quizResultToSave.setOwnerUserAccountId(getOwnerUserAccountId());

        updatePhraseStatuses(quizResultDTO);

        QuizResult savedQuizResult = quizRepository.save(quizResultToSave);

        return mapperUtil.convert(savedQuizResult, new QuizResultDTO());

    }

    private UUID getOwnerUserAccountId() {

        try {

            UUID ownerUserAccountId;

            ResponseEntity<UserAccountResponseDTO> response = userAccountClient.getUserAccountExternalId();

            if (Objects.requireNonNull(response.getBody()).isSuccess() && Objects.requireNonNull(response.getBody()).getData() != null) {
                ownerUserAccountId = (UUID) response.getBody().getData();
            } else {
                throw new ExternalIdNotRetrievedException("The external ID of the user account could not be retrieved.");
            }

            return ownerUserAccountId;

        } catch (Throwable exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new ExternalIdNotRetrievedException("The external ID of the user account could not be retrieved.");
        }

    }

    private void updatePhraseStatuses(QuizResultDTO quizResultDTO) {

        try {

            ResponseEntity<PhraseResponseDTO> response = phraseClient.changePhraseStatuses(quizResultDTO.getResultForEachPhrase());

            if (!Objects.requireNonNull(response.getBody()).isSuccess()) {
                throw new PhraseStatusesCouldNotBeUpdatedException("The statuses of the phrases in the quiz result could not be updated.");
            }

        } catch (Throwable exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new PhraseStatusesCouldNotBeUpdatedException("The statuses of the phrases in the quiz result could not be updated.");
        }

    }

}

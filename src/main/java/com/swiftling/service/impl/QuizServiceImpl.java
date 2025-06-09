package com.swiftling.service.impl;

import com.swiftling.client.PhraseClient;
import com.swiftling.client.UserAccountClient;
import com.swiftling.dto.*;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final MapperUtil mapperUtil;
    private final PhraseClient phraseClient;
    private final UserAccountClient userAccountClient;
    private final KafkaTemplate<String, QuizResultDTO> kafka;

    public QuizServiceImpl(QuizRepository quizRepository, MapperUtil mapperUtil, PhraseClient phraseClient, UserAccountClient userAccountClient, KafkaTemplate<String, QuizResultDTO> kafka) {
        this.quizRepository = quizRepository;
        this.mapperUtil = mapperUtil;
        this.phraseClient = phraseClient;
        this.userAccountClient = userAccountClient;
        this.kafka = kafka;
    }

    @Override
    public QuizResultDTO save(QuizResultDTO quizResultDTO) {

        QuizResult quizResultToSave = mapperUtil.convert(quizResultDTO, new QuizResult());

        quizResultToSave.setExternalQuizId(quizResultDTO.getExternalQuizId());
        quizResultToSave.setLanguage(Language.findByValue(quizResultDTO.getLanguage()));
        quizResultToSave.setQuizType(QuizType.findByValue(quizResultDTO.getQuizType()));
        quizResultToSave.setDate(LocalDate.now());

        UUID ownerUserAccountId = getOwnerUserAccountId();

        quizResultToSave.setOwnerUserAccountId(ownerUserAccountId);

        updatePhraseStatuses(quizResultDTO);

        QuizResult savedQuizResult = quizRepository.save(quizResultToSave);

        kafka.send("quiz-results", ownerUserAccountId.toString(), quizResultDTO);

        return mapperUtil.convert(savedQuizResult, new QuizResultDTO());

    }

    @Override
    public List<QuizHistoryDTO> getQuizHistory() {
        return quizRepository.findAllByOwnerUserAccountId(getOwnerUserAccountId()).stream()
                .map(quizResult -> mapperUtil.convert(quizResult, new QuizHistoryDTO()))
                .toList();
    }

    @Override
    public QuizStatsDTO getQuizStats() {

        UUID ownerUserAccountId = getOwnerUserAccountId();

        Optional<QuizResult> optionalQuizResult = quizRepository.findFirstByOwnerUserAccountIdOrderByDateDesc(ownerUserAccountId);
        QuizStatsDTO quizStatsDTO = new QuizStatsDTO();
        LatestQuizResultDTO latestQuizResultDTO = new LatestQuizResultDTO();

        if (optionalQuizResult.isEmpty()) {

            quizStatsDTO.setOverallBestTimeInSeconds(-1);

            quizStatsDTO.setLatestBestTimeInSeconds(-1);

            latestQuizResultDTO.setCorrectAnswerAmount(-1);
            latestQuizResultDTO.setWrongAnswerAmount(-1);
            latestQuizResultDTO.setTimedOutAnswerAmount(-1);

            quizStatsDTO.setLatestQuizResults(latestQuizResultDTO);

        } else {

            quizStatsDTO.setOverallBestTimeInSeconds(quizRepository.getOverallBestTimeInSeconds());

            quizStatsDTO.setLatestBestTimeInSeconds(optionalQuizResult.get().getBestTimeInSeconds());

            latestQuizResultDTO.setCorrectAnswerAmount(optionalQuizResult.get().getCorrectAnswerAmount());
            latestQuizResultDTO.setWrongAnswerAmount(optionalQuizResult.get().getWrongAnswerAmount());
            latestQuizResultDTO.setTimedOutAnswerAmount(optionalQuizResult.get().getTimedOutAnswerAmount());

            quizStatsDTO.setLatestQuizResults(latestQuizResultDTO);

        }

        return quizStatsDTO;

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

package com.swiftling.service.impl;

import com.swiftling.client.PhraseClient;
import com.swiftling.client.UserAccountClient;
import com.swiftling.dto.*;
import com.swiftling.entity.DailyStreak;
import com.swiftling.entity.QuizResult;
import com.swiftling.enums.Language;
import com.swiftling.enums.QuizType;
import com.swiftling.exception.ExternalIdNotRetrievedException;
import com.swiftling.exception.PhraseStatusesCouldNotBeUpdatedException;
import com.swiftling.exception.QuizCanNotBeDeletedException;
import com.swiftling.repository.DailyStreakRepository;
import com.swiftling.repository.QuizRepository;
import com.swiftling.service.QuizService;
import com.swiftling.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@EnableScheduling
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final MapperUtil mapperUtil;
    private final PhraseClient phraseClient;
    private final UserAccountClient userAccountClient;
    private final DailyStreakRepository dailyStreakRepository;

    public QuizServiceImpl(QuizRepository quizRepository, MapperUtil mapperUtil, PhraseClient phraseClient, UserAccountClient userAccountClient, DailyStreakRepository dailyStreakRepository) {
        this.quizRepository = quizRepository;
        this.mapperUtil = mapperUtil;
        this.phraseClient = phraseClient;
        this.userAccountClient = userAccountClient;
        this.dailyStreakRepository = dailyStreakRepository;
    }

    @Override
    public QuizResultDTO save(QuizResultDTO quizResultDTO) {

        QuizResult quizResultToSave = mapperUtil.convert(quizResultDTO, new QuizResult());

        quizResultToSave.setExternalQuizId(UUID.randomUUID());
        quizResultToSave.setLanguage(Language.findByCode(quizResultDTO.getLanguage().toLowerCase()));
        quizResultToSave.setQuizType(QuizType.findByValue(quizResultDTO.getQuizType()));
        quizResultToSave.setDate(LocalDate.now());

        UUID ownerUserAccountId = getOwnerUserAccountId();

        quizResultToSave.setOwnerUserAccountId(ownerUserAccountId);

        QuizResult savedQuizResult = quizRepository.save(quizResultToSave);

        updateDailyStreakAfterQuiz(ownerUserAccountId);

        updatePhraseStatuses(quizResultDTO);

        QuizResultDTO quizResultToReturn = mapperUtil.convert(savedQuizResult, new QuizResultDTO());

        quizResultToReturn.setLanguage(savedQuizResult.getLanguage().getValue());
        quizResultToReturn.setQuizType(savedQuizResult.getQuizType().getValue());

        return quizResultToReturn;

    }

    @Override
    public List<QuizHistoryDTO> getQuizHistory() {
        return quizRepository.findAllByOwnerUserAccountId(getOwnerUserAccountId()).stream()
                .map(quizResult -> {
                    QuizHistoryDTO quizHistoryToReturn = mapperUtil.convert(quizResult, new QuizHistoryDTO());
                    quizHistoryToReturn.setLanguage(quizResult.getLanguage().getValue());
                    return quizHistoryToReturn;
                })
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
                ownerUserAccountId = UUID.fromString((String) response.getBody().getData());
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

    private void updateDailyStreakAfterQuiz(UUID ownerUserAccountId) {

        DailyStreak dailyStreak = dailyStreakRepository.findByUserAccountId(ownerUserAccountId)
                .orElseGet(() -> {
                    DailyStreak newDailyStreak = new DailyStreak();
                    newDailyStreak.setUserAccountId(ownerUserAccountId);
                    newDailyStreak.setUpdatedToday(false);
                    return newDailyStreak;
                });

        if (dailyStreak.getUpdatedToday() == false) {

            dailyStreak.setDailyStreak(dailyStreak.getDailyStreak() == null ? 1 : dailyStreak.getDailyStreak() + 1);
            dailyStreak.setUpdatedToday(true);

            dailyStreakRepository.save(dailyStreak);

        }

    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void handleDailyStreakReset() {

        log.info("✅ Starting daily streak maintenance job...");

        dailyStreakRepository.resetStreaksWhereNotUpdatedToday();
        log.info("🟡 Streaks reset for users who didn't update today.");

        dailyStreakRepository.resetAllUpdatedTodayFlags();
        log.info("🔵 All updatedToday flags set to false.");

        log.info("✅ Finished daily streak maintenance job.");

    }

    @Override
    public DailyStreakDTO getDailyStreak() {

        DailyStreak dailyStreak = dailyStreakRepository.findByUserAccountId(getOwnerUserAccountId())
                .orElseGet(DailyStreak::new);

        DailyStreakDTO dailyStreakDTO = new DailyStreakDTO();

        dailyStreakDTO.setDailyStreak(dailyStreak.getDailyStreak());
        dailyStreakDTO.setUpdatedToday(dailyStreakDTO.getUpdatedToday());

        return dailyStreakDTO;

    }

    @Override
    public Map<UUID, DailyStreakDTO> getAllUsersDailyStreaks() {

        List<Object[]> results = dailyStreakRepository.findAllUserStreaksRaw();
        Map<UUID, DailyStreakDTO> streakMap = new HashMap<>();

        for (Object[] row : results) {

            UUID userId = (UUID) row[0];
            Integer streakAmount = (Integer) row[1];
            Boolean updatedToday = (Boolean) row[2];

            DailyStreakDTO dailyStreakDTO = new DailyStreakDTO();
            dailyStreakDTO.setDailyStreak(streakAmount);
            dailyStreakDTO.setUpdatedToday(updatedToday);

            streakMap.put(userId, dailyStreakDTO);

        }

        return streakMap;

    }

    @Override
    public void deleteAllByUser(UUID externalOwnerUserAccountId) {

        try {

            List<QuizResult> allUserPhrases = quizRepository.findAllByOwnerUserAccountId(externalOwnerUserAccountId);
            quizRepository.deleteAll(allUserPhrases);

        } catch (Throwable exception) {
            throw new QuizCanNotBeDeletedException("The quizzes can not be deleted.");
        }

    }

}

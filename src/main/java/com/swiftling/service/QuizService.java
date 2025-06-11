package com.swiftling.service;

import com.swiftling.dto.DailyStreakDTO;
import com.swiftling.dto.QuizHistoryDTO;
import com.swiftling.dto.QuizResultDTO;
import com.swiftling.dto.QuizStatsDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface QuizService {

    QuizResultDTO save(QuizResultDTO quizResultDTO);

    List<QuizHistoryDTO> getQuizHistory();

    QuizStatsDTO getQuizStats();

    void handleDailyStreakReset();

    DailyStreakDTO getDailyStreak();

    Map<UUID, DailyStreakDTO> getAllUsersDailyStreaks();

}

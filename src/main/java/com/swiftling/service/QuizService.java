package com.swiftling.service;

import com.swiftling.dto.QuizHistoryDTO;
import com.swiftling.dto.QuizResultDTO;
import com.swiftling.dto.QuizStatsDTO;

import java.util.List;

public interface QuizService {

    QuizResultDTO save(QuizResultDTO quizResultDTO);

    List<QuizHistoryDTO> getQuizHistory();

    QuizStatsDTO getQuizStats();

}

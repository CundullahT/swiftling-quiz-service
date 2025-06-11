package com.swiftling.service.impl;

import com.swiftling.dto.DailyStreakDTO;
import com.swiftling.dto.UserDailyStreakMessageDTO;
import com.swiftling.service.KafkaProducerService;
import com.swiftling.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@EnableScheduling
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final QuizService quizService;

    @Value("${kafka.topic.user-daily-streak}")
    private String userDailyStreakTopic;

    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, QuizService quizService) {
        this.kafkaTemplate = kafkaTemplate;
        this.quizService = quizService;
    }

    @Scheduled(cron = "0 0 20 * * ?")
    @Override
    public void sendAllUsersDailyStreaks() {

        log.info("Executing scheduled task to send daily streaks at 8 PM");

        try {

            Map<UUID, DailyStreakDTO> allUserStreaks = quizService.getAllUsersDailyStreaks();

            allUserStreaks.forEach((userId, streak) -> {
                UserDailyStreakMessageDTO message = UserDailyStreakMessageDTO.builder()
                        .userAccountId(userId)
                        .dailyStreakDTO(streak)
                        .timestamp(LocalDateTime.now())
                        .build();

                kafkaTemplate.send(userDailyStreakTopic, message);
                log.info("Sent streak message for user {} with streak {}", userId, streak);

            });

            log.info("Successfully sent daily streaks for {} users", allUserStreaks.size());

        } catch (Exception e) {
            log.error("Error sending daily streak messages to Kafka", e);
        }

    }

}

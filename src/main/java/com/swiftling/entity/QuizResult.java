package com.swiftling.entity;

import com.swiftling.enums.Language;
import com.swiftling.enums.QuizType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quiz_results")
public class QuizResult extends BaseEntity {

    @Column(unique = true, nullable = false)
    private UUID externalQuizResultId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private QuizType quizType;

    @Column(nullable = false)
    private Language language;

    @Column(nullable = false)
    private Integer correctAnswerAmount;

    @Column(nullable = false)
    private Integer wrongAnswerAmount;

    @Column(nullable = false)
    private Integer timedOutAnswerAmount;

    @Column(nullable = false)
    private Integer bestTimeInSeconds;

    @Column(nullable = false)
    private Integer worstTimeInSeconds;

    @Column(nullable = false, updatable = false)
    private UUID ownerUserAccountId;

}

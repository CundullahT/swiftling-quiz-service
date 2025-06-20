package com.swiftling.entity;

import com.swiftling.enums.Language;
import com.swiftling.enums.QuizType;
import jakarta.persistence.*;
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
    private UUID externalQuizId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizType quizType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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

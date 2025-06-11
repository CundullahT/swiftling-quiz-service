package com.swiftling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "daily_streak")
public class DailyStreak extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private UUID userAccountId;

    @Column(nullable = false)
    private Integer dailyStreak;

    @Column(nullable = false)
    private Boolean updatedToday;

}

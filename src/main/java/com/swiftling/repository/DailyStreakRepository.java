package com.swiftling.repository;

import com.swiftling.entity.DailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyStreakRepository extends JpaRepository<DailyStreak, Long> {

    Optional<DailyStreak> findByUserAccountId(UUID userAccountId);

    @Modifying
    @Query("UPDATE DailyStreak ds SET ds.dailyStreak = 0 WHERE ds.updatedToday = false")
    void resetStreaksWhereNotUpdatedToday();

    @Modifying
    @Query("UPDATE DailyStreak ds SET ds.updatedToday = false")
    void resetAllUpdatedTodayFlags();

    @Query("SELECT ds.userAccountId, ds.dailyStreak, ds.updatedToday FROM DailyStreak ds")
    List<Object[]> findAllUserStreaksRaw();

}

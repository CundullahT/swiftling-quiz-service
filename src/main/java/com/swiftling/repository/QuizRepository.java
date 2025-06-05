package com.swiftling.repository;

import com.swiftling.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findAllByOwnerUserAccountId(UUID ownerUserAccountId);

    Optional<QuizResult> findFirstByOwnerUserAccountIdOrderByDateDesc(UUID ownerUserAccountId);

    @Query(value = "SELECT MIN(best_time_in_seconds) FROM quiz_results", nativeQuery = true)
    Integer getOverallBestTimeInSeconds();

}

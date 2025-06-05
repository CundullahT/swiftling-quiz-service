package com.swiftling.repository;

import com.swiftling.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findAllByOwnerUserAccountId(UUID ownerUserAccountId);

}

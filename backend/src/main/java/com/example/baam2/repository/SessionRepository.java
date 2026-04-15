package com.example.baam2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.baam2.model.SessionModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    List<SessionModel> findAllByOwner_Id(Long id);

    @Query("SELECT s.id FROM SessionModel s WHERE s.isActive = true")
    List<Long> findAllActiveIds();
}
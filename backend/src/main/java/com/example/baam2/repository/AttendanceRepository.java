package com.example.baam2.repository;
import com.example.baam2.model.SessionModel;
import com.example.baam2.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.baam2.model.AttendanceModel;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {
    boolean existsByUserIdAndSessionId(Long userId, Long sessionId);
    List<AttendanceModel> findAllByUserId(Long id);

    @Modifying
    @Query("DELETE FROM AttendanceModel a WHERE a.user.id = :id")
    void deleteByUserId(Long id);

    @Modifying
    @Query("DELETE FROM AttendanceModel a WHERE a.session.id = :id")
    void deleteBySessionId(Long id);
}
package com.example.Graduation.Repository;

import com.example.Graduation.Entity.MainCommentData;
import com.example.Graduation.Entity.WriteCommentData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WriteCommentDataRepository extends JpaRepository<WriteCommentData, String> {
    Page<WriteCommentData> findByNumber(String number, Pageable pageable);
    Page<WriteCommentData> findByUserId(String id, Pageable pageable);
    WriteCommentData findByIdx(String idx);
    @Transactional
    @Modifying
    @Query("delete from WriteCommentData w where w.number = ?1")
    int deleteByNumber(String Number);
    WriteCommentData deleteAllByNumber(String Number);
}
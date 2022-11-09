package com.example.Graduation.Repository;

import com.example.Graduation.Entity.MainCommentData;
import com.example.Graduation.Entity.WriteCommentData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WriteCommentDataRepository extends JpaRepository<WriteCommentData, String> {
    Page<WriteCommentData> findByNumber(String number, Pageable pageable);
    Page<WriteCommentData> findByWrite_Id(String id, Pageable pageable);
    WriteCommentData findByIdx(String idx);

    WriteCommentData deleteByNumber(String Number);
    WriteCommentData deleteAllByNumber(String Number);
}
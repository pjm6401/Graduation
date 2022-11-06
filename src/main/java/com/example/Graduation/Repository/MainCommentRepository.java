package com.example.Graduation.Repository;



import com.example.Graduation.Entity.MainCommentData;

import com.example.Graduation.Entity.MainWriteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface MainCommentRepository extends JpaRepository<MainCommentData, String> {
    Page<MainCommentData> findByNumber (String Number, Pageable pageable);

    MainCommentData findByIdx(String idx);
    MainCommentData findByNumber(String Number);
    MainCommentData deleteByNumber(String Number);
    MainCommentData deleteAllByNumber(String Number);
}

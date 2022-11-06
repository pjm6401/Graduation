package com.example.Graduation.Repository;



import com.example.Graduation.Entity.MainWriteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MainWriteRepository extends JpaRepository<MainWriteData, String> {
    Page<MainWriteData> findByMonth (String month, Pageable pageable); // title 포함하는
    MainWriteData findByIdx(String Idx);
}

package com.example.Graduation.Repository;



import com.example.Graduation.Entity.WriteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface WriteRepository extends JpaRepository<WriteData, String> {
        Page<WriteData> findByTitleContaining (String search, Pageable pageable); // title 포함하는
}

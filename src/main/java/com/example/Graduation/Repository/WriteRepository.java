package com.example.Graduation.Repository;



import com.example.Graduation.Entity.WriteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface WriteRepository extends JpaRepository<WriteData, String> {
        Page<WriteData> findByTitleContaining (String search, Pageable pageable); // title 포함하는
        Page<WriteData> findByMenu (String menu, Pageable pageable); // 메뉴 분류
        Page<WriteData> findById (String id, Pageable pageable); // 작성자로 찾는다
}

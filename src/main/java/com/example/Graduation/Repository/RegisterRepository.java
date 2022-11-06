package com.example.Graduation.Repository;

import com.example.Graduation.Entity.RegisterData;

import org.springframework.data.jpa.repository.JpaRepository;


//회원가입
//<관리대상 엔티티, 대표값의 타입> 상속받아서 처리가능함
//확장받아서 사용하는것이다
public interface RegisterRepository extends JpaRepository<RegisterData, String> {
    RegisterData findByID(String ID);
    boolean existsByemail(String email);
    boolean existsByID(String ID);
}

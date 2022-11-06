package com.example.Graduation.Repository;

import com.example.Graduation.Entity.MainimgData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



//<관리대상 엔티티, 대표값의 타입> 상속받아서 처리가능함
//확장받아서 사용하는것이다
public interface MainImgRepository extends JpaRepository<MainimgData, String> {


}

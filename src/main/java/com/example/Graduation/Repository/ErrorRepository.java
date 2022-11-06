package com.example.Graduation.Repository;

import com.example.Graduation.Entity.ErrorData;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ErrorRepository extends JpaRepository<ErrorData, Integer> {
}

package com.example.Graduation.DTO;

import com.example.Graduation.Entity.ErrorData;
import com.example.Graduation.Entity.RegisterData;

import java.io.Serializable;

public class ErrorForm implements Serializable {
    private final Integer ErrorNum; //에러번호
    private final String ErrorContent; // 에러내용

    public ErrorForm(Integer ErrorNum, String ErrorContent) {
        this.ErrorNum = ErrorNum;
        this.ErrorContent = ErrorContent;
    }
    public ErrorData toEntity() {
        return new ErrorData(ErrorNum,ErrorContent);}
}

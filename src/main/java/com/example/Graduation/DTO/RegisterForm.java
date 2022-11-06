package com.example.Graduation.DTO;

import com.example.Graduation.Entity.RegisterData;


import java.io.Serializable;

public class RegisterForm implements Serializable {
    private final String ID;  // 아이디
    private String PW;  // 비밀번호
    private final String name; // 이름
    private final String email; // 이메일
    private final String phone; // 전화번호
    // entitiy -> Dto
    public RegisterForm( String ID, String PW, String name, String email, String phone) {
        this.ID = ID;
        this.PW = PW;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }



    @Override
    public String toString() {
        return "RegisterForm{" +
                "ID='" + ID + '\'' +
                ", PW='" + PW + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
    //entity 인 registerData 반환
    public RegisterData toEntity() {
        return new RegisterData(ID,PW,name,email,phone);
    }
}

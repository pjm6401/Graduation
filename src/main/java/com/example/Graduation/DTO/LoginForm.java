package com.example.Graduation.DTO;

import com.example.Graduation.Entity.RegisterData;
import lombok.Data;

@Data
public class LoginForm {

    private String ID;
    private String PW;
    private String location;
    public LoginForm(String ID, String PW,String location) {
        this.ID = ID;
        this.PW = PW;
        this.location = location;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "ID='" + ID + '\'' +
                ", PW='" + PW + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

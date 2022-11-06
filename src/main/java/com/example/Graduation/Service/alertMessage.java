package com.example.Graduation.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class alertMessage {
    String message =""; //화면에 출력할 메세지
    //String href =""; // Redirect 할 창
    public alertMessage(String message) {
        this.message = message;
    }
}

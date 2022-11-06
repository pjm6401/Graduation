package com.example.Graduation.Entity;

import lombok.*;
import javax.persistence.*;


@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "error")
@Entity // DB가 해당 객체 인식하게 해주는 객체선언
public class ErrorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    private String Econtent;
    public String setContent(String content){
        return Econtent = content;
    }

    public ErrorData toEntity(){
        return new ErrorData(num, Econtent);
    }
}

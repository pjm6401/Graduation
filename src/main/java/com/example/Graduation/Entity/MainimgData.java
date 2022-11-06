package com.example.Graduation.Entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mainimg")
@Entity // DB가 해당 객체 인식하게 해주는 객체선언
public class MainimgData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idx;

    private String file_name;

    private String file_path;

    @Override
    public String toString() {
        return "MainimgData{" +
                "idx='" + idx + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_path='" + file_path + '\'' +
                '}';
    }
    public void setFile_path(String s) {
        file_path = s;
    }
    public void setFile_name(String s){
        file_name = s;
    }
}

package com.example.Graduation.Entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "main_write")
@Entity // DB가 해당 객체 인식하게 해주는 객체선언
@Data
public class MainWriteData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idx;

    @NotNull
    private String title;
    @NotNull
    private String des; // 여행지

    @NotNull
    private String month; // 월

    private String lat; //위도
    private String lng; //경도

    @NotNull
    private String content1;

    private String file_name1;

    private String file_path1;

    @NotNull
    private String content2;

    private String file_name2;

    private String file_path2;

    @NotNull
    private String content3;

    private String file_name3;

    private String file_path3;

    private String write_time;

    @Override
    public String toString() {
        return "MainWriteData{" +
                "idx='" + idx + '\'' +
                ", title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", 月='" + month + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", content1='" + content1 + '\'' +
                ", file_name1='" + file_name1 + '\'' +
                ", file_path1='" + file_path1 + '\'' +
                ", content2='" + content2 + '\'' +
                ", file_name2='" + file_name2 + '\'' +
                ", file_path2='" + file_path2 + '\'' +
                ", content3='" + content3 + '\'' +
                ", file_name3='" + file_name3 + '\'' +
                ", file_path3='" + file_path3 + '\'' +
                ", write_time='" + write_time + '\'' +
                '}';
    }

    public void setFile_name1(String file_name1) {
        this.file_name1 = file_name1;
    }

    public void setFile_path1(String file_path1) {
        this.file_path1 = file_path1;
    }

    public void setFile_name2(String file_name2) {
        this.file_name2 = file_name2;
    }

    public void setFile_path2(String file_path2) {
        this.file_path2 = file_path2;
    }

    public void setFile_name3(String file_name3) {
        this.file_name3 = file_name3;
    }

    public void setFile_path3(String file_path3) {
        this.file_path3 = file_path3;
    }

    public void setWrite_time(String write_time) {
        this.write_time = write_time;
    }
}

package com.example.Graduation.Entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "writefile")
@Entity // DB가 해당 객체 인식하게 해주는 객체선언
@Data
public class WriteData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idx;

    @NotNull
    private String title;

    private String file_name;

    private String file_path;

    @NotNull
    private String content;

    @NotNull
    private String id;

    private String write_time;

    private String menu;

    @OneToMany(mappedBy = "number", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MainCommentData> mainCommentDataList = new ArrayList<>();

    @Override
    public String toString() {
        return "WriteData{" +
                ", title='" + title + '\'' +
                ", filename='" + file_name + '\'' +
                ", filepath='" + file_path + '\'' +
                ", content='" + content + '\'' +
                ", ID='" + id + '\'' +
                ", menu='" + menu + '\'' +
                '}';
    }

}

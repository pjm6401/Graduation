package com.example.Graduation.Entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "write_comment")
@Data
public class WriteCommentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idx;

    @NotNull
    private String number; // 글 idx

    @NotNull
    private String comment; // 댓글 내용
    @NotNull
    private String userId; // 작성자

    private String write_time;
}

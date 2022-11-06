package com.example.Graduation.Entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity // DB가 해당 객체 인식하게 해주는 객체선언
public class RegisterData {
    //@ID 대표값을 지정해준다
    //@GeneratedValue 1,2,3.... 자동지정

    @Id
    @NotNull
    @Column(unique = true) // ID는 중복이 안된다.
    private String ID;  // 아이디
    @NotNull
    private String PW;  // 비밀번
    @Size(min = 2, max = 20)
    private String name; // 이름
    @Email // Email형식 Anntation
    private String email; // 이메일
    @Size(min=10)
    @Pattern(regexp ="^[0,1]{3}+[0-9]{3,4}+[0-9]{4}$") // 번호 형식 0,1 세자리 + 0~9 3자리 or 4자리 + 0~9 4자리
    private String phone; // 전화번호



    @Override
    public String toString() {
        return "RegisterData{" +
                " ID='" + ID + '\'' +
                ", PW='" + PW + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                "}";
    }
    public void setPassword(String PW) {
        this.PW = PW;
    }

    public RegisterData toEntity(){
        return new RegisterData(ID,PW,name,email,phone);
    }


}

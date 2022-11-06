package com.example.Graduation.controller;

import com.example.Graduation.DTO.RegisterForm;
import com.example.Graduation.Entity.RegisterData;
import com.example.Graduation.Repository.RegisterRepository;
import com.example.Graduation.Service.Register_Login_outService;
import com.example.Graduation.Service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.persistence.Table;
import javax.validation.Valid;

@Controller
@Table(name = "user")

public class RegisterController {
    @Autowired
    private Register_Login_outService registerService;

    //스프링 부트가 자체적으로 알아서 해준다
    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결한다 즉 new 이렇게 객체를 선언 안해줘도 생성됨
    private RegisterRepository registerRepository;

    @GetMapping("/Modal/Register")
    public String newRegister() {
        return "/Modal/Register";
    }

    @Autowired
    PasswordEncoder passwordEncoder; // 암호화
    @PostMapping("/Register/create")
    public String createLogin(@Valid RegisterForm register, Model model) {

        System.out.println(register.toString());
        //1. DTO 반환 entitiy
        RegisterData Rdata = register.toEntity();
        System.out.println(Rdata.toString());
        registerService.register(register,model);

        model.addAttribute("message",registerService.getMessage());
        model.addAttribute("returnurl",registerService.getUrl());
        return "/util/message";
        }
    }



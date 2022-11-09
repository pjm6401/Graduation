package com.example.Graduation.controller;

import com.example.Graduation.DTO.LoginForm;
import com.example.Graduation.Entity.RegisterData;
import com.example.Graduation.Repository.RegisterRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class Login_outController {
    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/Login/create")
    public String createLogin(LoginForm loginForm, HttpSession session, Model model){
        String id = loginForm.getID();
        String pw = loginForm.getPW();
        String location = loginForm.getLocation();
        RegisterData user = registerRepository.findByID(id);
        if(user == null || !user.getID().equals(id)){
            System.out.println("ID가 다릅니다");
            model.addAttribute("message","ID가 없거나 다릅니다.");
            model.addAttribute("returnurl",location);
            return "/util/message";
        }
        else if(!passwordEncoder.matches(pw, user.getPW())) {
            System.out.println("비밀번호가 다릅니다.");
            model.addAttribute("message","비밀번호가 다릅니다.");
            model.addAttribute("returnurl",location);
            return "/util/message";
        }
        else {
            if(user.getID().equals("pjm6401")){
                session.setAttribute("Manager", user);
                System.out.println("로그인 성공. 매니저님 환영합니다");
                model.addAttribute("message","로그인 성공. 매니저님 환영합니다.");
            }
            else {
                session.setAttribute("user", user);
                System.out.println("로그인 성공.");
                model.addAttribute("message","로그인 성공. 환영합니다.");
            }
            model.addAttribute("returnurl",location);
            return "/util/message";
        }
    }

    @GetMapping("/logout/end")
    public String logout(HttpSession session, Model model){
        session.removeAttribute("user");
        session.removeAttribute("Manager");
        return "redirect:/Home";
    }
}

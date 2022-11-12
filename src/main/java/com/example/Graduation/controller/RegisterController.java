package com.example.Graduation.controller;

import com.example.Graduation.DTO.RegisterForm;
import com.example.Graduation.Entity.MainCommentData;
import com.example.Graduation.Entity.RegisterData;
import com.example.Graduation.Entity.WriteCommentData;
import com.example.Graduation.Entity.WriteData;
import com.example.Graduation.Repository.MainCommentRepository;
import com.example.Graduation.Repository.RegisterRepository;
import com.example.Graduation.Repository.WriteCommentDataRepository;
import com.example.Graduation.Repository.WriteRepository;
import com.example.Graduation.Service.MainService;
import com.example.Graduation.Service.Register_Login_outService;
import com.example.Graduation.Service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.persistence.Table;
import javax.validation.Valid;
import java.util.List;

@Controller
@Table(name = "user")

public class RegisterController {
    @Autowired
    private Register_Login_outService registerService;
    @Autowired
    private WriteService writeService;
    @Autowired
    private MainService mainService;
    //스프링 부트가 자체적으로 알아서 해준다
    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결한다 즉 new 이렇게 객체를 선언 안해줘도 생성됨
    private RegisterRepository registerRepository;
    @Autowired
    WriteRepository writeRepository;
    @Autowired
    WriteCommentDataRepository writeCommentDataRepository;
    @Autowired
    MainCommentRepository mainCommentRepository;
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

    @GetMapping("/MyPage/{id}")
    public String MyPage(@PathVariable String id, @PageableDefault(page = 0, size = 5,
            sort = "idx", direction = Sort.Direction.DESC) Pageable pageable,Model model){
        System.out.println(id);
        Page<WriteData> WriteList = null;
        Page<WriteCommentData> WCommentList = null;
        Page<MainCommentData> MCommentList = null;
        WriteList = writeService.boardListByID(id,pageable);
        WCommentList = writeService.WriteCommentListById(id, pageable);
        MCommentList = mainService.MainCommentListById(id,pageable);
        //작성 글 리스트
        int WriteListPage = WriteList.getPageable().getPageNumber();
        int WriteListNowPage = WriteList.getPageable().getPageNumber() + 1; // 페이지는 0부터 읽는다
        int WriteListPrePage = Math.max(WriteListPage - 1 , 0);
        int WriteListNextPage = Math.min(WriteListPage + 1 , WriteList.getTotalPages()-1);
        //작성 댓글 리스트 게시판
        int WCommentListPage = WCommentList.getPageable().getPageNumber();
        int WCommentListNowPage = WCommentList.getPageable().getPageNumber() + 1; // 페이지는 0부터 읽는다
        int WCommentListPrePage = Math.max(WriteListPage - 1 , 0);
        int WCommentListNextPage = Math.min(WriteListPage + 1 , WCommentList.getTotalPages()-1);
        //작성 댓글 리스트 메인글
        int MCommentListPage = MCommentList.getPageable().getPageNumber();
        int MCommentListNowPage = MCommentList.getPageable().getPageNumber() + 1; // 페이지는 0부터 읽는다
        int MCommentListPrePage = Math.max(WriteListPage - 1 , 0);
        int MCommentListNextPage = Math.min(WriteListPage + 1 , MCommentList.getTotalPages()-1);
        model.addAttribute("WriteList", WriteList);
        model.addAttribute("WCommentList", WCommentList);
        model.addAttribute("MCommentList", MCommentList);
        //총페이지
        model.addAttribute("WriteListPage", WriteListPage);
        model.addAttribute("WCommentListPage", WCommentListPage);
        model.addAttribute("MCommentListPage", MCommentListPage);
        //현재
        model.addAttribute("WriteListNowPage",WriteListNowPage);
        model.addAttribute("WCommentListNowPage",WCommentListNowPage);
        model.addAttribute("MCommentListNowPage",MCommentListNowPage);
        //이전
        model.addAttribute("WriteListPrePage",WriteListPrePage);
        model.addAttribute("WCommentListPrePage",WCommentListPrePage);
        model.addAttribute("MCommentListPrePage",MCommentListPrePage);
        //다음
        model.addAttribute("WriteListNextPage",WriteListNextPage);
        model.addAttribute("WCommentListNextPage",WCommentListNextPage);
        model.addAttribute("MCommentListNextPage",MCommentListNextPage);
        model.addAttribute("UserInfo",registerRepository.findByID(id));
        return "MyPage";
    }
}




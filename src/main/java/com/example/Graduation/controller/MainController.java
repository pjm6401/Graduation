package com.example.Graduation.controller;

import com.example.Graduation.DTO.CommentForm;
import com.example.Graduation.DTO.WeatherDTO;
import com.example.Graduation.Entity.MainCommentData;
import com.example.Graduation.Entity.MainWriteData;
import com.example.Graduation.Entity.MainimgData;
import com.example.Graduation.Repository.MainWriteRepository;
import com.example.Graduation.Service.MainService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainController {
    @Autowired
    MainService mainService;
    @Autowired
    MainWriteRepository mainWriteRepository;
    // 메인페이지 리스트 보여주기
    @GetMapping("/Main")
    public String main (Model model) {
        model.addAttribute("a",mainService.monthImg("1"));
        model.addAttribute("b",mainService.monthImg("2"));
        model.addAttribute("c",mainService.monthImg("3"));
        model.addAttribute("d",mainService.monthImg("4"));
        model.addAttribute("e",mainService.monthImg("5"));
        model.addAttribute("f",mainService.monthImg("6"));
        model.addAttribute("g",mainService.monthImg("7"));
        model.addAttribute("h",mainService.monthImg("8"));
        model.addAttribute("i",mainService.monthImg("9"));
        model.addAttribute("j",mainService.monthImg("10"));
        model.addAttribute("k",mainService.monthImg("11"));
        model.addAttribute("l",mainService.monthImg("12"));
        return "Main";
    }
    //사진 등록 페이지
    @GetMapping("/MainImgView")
    public String mainImgView(Model model){
        return "MainImgView";
    }

    @PostMapping("/MainImg/create")
    public String mainImgCreate(MainimgData mainimgData, @RequestParam(name = "files1") MultipartFile files1,
                                @RequestParam(name = "files2") MultipartFile files2,
                                @RequestParam(name = "files3") MultipartFile files3,
                                @RequestParam(name = "files4") MultipartFile files4,
                                @RequestParam(name = "files5") MultipartFile files5,
                                @RequestParam(name = "files6") MultipartFile files6,
                                @RequestParam(name = "files7") MultipartFile files7,
                                @RequestParam(name = "files8") MultipartFile files8,
                                @RequestParam(name = "files9") MultipartFile files9,
                                @RequestParam(name = "files10") MultipartFile files10,
                                @RequestParam(name = "files11") MultipartFile files11,
                                @RequestParam(name = "files12") MultipartFile files12,Model model) throws IOException {
        mainService.mainImgcreate(mainimgData,files1);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files2);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files3);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files4);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files5);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files6);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files7);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files8);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files9);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files10);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files11);
        mainimgData = new MainimgData();
        mainService.mainImgcreate(mainimgData,files12);
        System.out.println(mainimgData.toString());
        System.out.println("사진 등록 완료");
        model.addAttribute("message","사진 등록 완료");
        model.addAttribute("returnurl","Main");
        return "/util/message";
    }
    
    // mainBoard 보기
    @GetMapping("/MainBoard/{month}")
    public String mainBoard(Model model, @PathVariable String month, @PageableDefault(page = 0, size = 9,
            sort = "idx", direction = Sort.Direction.DESC) Pageable pageable){
        System.out.println(month);
        Page<MainWriteData> list= null;
        list = mainService.writeMonthlist(month,pageable);

        int page = list.getPageable().getPageNumber();
        int nowPage = list.getPageable().getPageNumber() + 1; // 페이지는 0부터 읽는다
        int prePage = Math.max(page - 1 , 0);
        int nextPage = Math.min(page + 1 , list.getTotalPages()-1);

        model.addAttribute("List", list);
        model.addAttribute("page", page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("prePage",prePage);
        model.addAttribute("nextPage",nextPage);
        return "MainBoard";
    }
    //main 게시글 상세 보기 페이지
    @GetMapping("/MainBoardView/{idx}")
    public String mainBoardView(Model model, @PathVariable String idx, @PageableDefault(page = 0, size = 10,
            sort = "idx", direction = Sort.Direction.DESC) Pageable pageable) throws IOException, ParseException {
        model.addAttribute("MainWriteview",mainService.MainWriteview(idx));
        System.out.println(idx);
        //날씨
        String lat = mainWriteRepository.findById(idx).get().getLat();
        String lng = mainWriteRepository.findById(idx).get().getLng();
        WeatherDTO weatherDTO = MainService.WeatherCoding(lat, lng);

        model.addAttribute("Weather", weatherDTO);
        //댓글
        Page<MainCommentData> list= null;

        list = mainService.MainCommentList(idx,pageable);
        System.out.println(list);
        int page = list.getPageable().getPageNumber();
        int nowPage = list.getPageable().getPageNumber() + 1; // 페이지는 0부터 읽는다
        int prePage = Math.max(page - 1 , 0);
        int nextPage = Math.min(page + 1 , list.getTotalPages()-1);

        model.addAttribute("List", list);
        model.addAttribute("page", page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("prePage",prePage);
        model.addAttribute("nextPage",nextPage);
        return "MainBoardview";
    }
    //main 글작성 페이지
    @GetMapping("/MainWrite")
    public String mainWrite(Model model){
        return "MainWrite";
    }
    //main 글작성 폼
    @PostMapping("/MainWrite/create")
    public String mainWriteCreate(MainWriteData mainWriteData, @RequestParam(name = "file1") MultipartFile file1,
                                  @RequestParam(name = "file2") MultipartFile file2,
                                  @RequestParam(name = "file3") MultipartFile file3,Model model ) throws IOException, ParseException {
        System.out.println(mainWriteData);
        mainService.mainWritecreate(mainWriteData,file1,file2,file3);
        String month = mainWriteData.getMonth();

        System.out.println("글 등록 완료");
        model.addAttribute("message","글 등록 완료");
        model.addAttribute("returnurl","MainBoard/"+month);
        return "/util/message";
    }
    //main 글 삭제
    @GetMapping("/delete/main/write/{idx}")
    public String DeleteMainView(@PathVariable String idx, Model model){
        MainWriteData mainWriteData;
        mainWriteData = mainWriteRepository.findByIdx(idx);
        String month = mainWriteData.getMonth();
        mainService.writeDelete(idx);
        model.addAttribute("message","글 삭제 완료");
        model.addAttribute("returnurl","MainBoard/"+month);
        return "/util/message";
    }
    //main 댓글작성
    @PostMapping("/MainBoardView/MainComment/create")
    public String mainCommentCreate(MainCommentData mainCommentData, Model model){
        mainService.CommentWrite(mainCommentData);
        System.out.println(mainCommentData);
        String number = mainCommentData.getNumber(); // MainBoardview 페이지
        model.addAttribute("message","글 등록 완료");
        model.addAttribute("returnurl","MainBoardView/"+number);
        return "/util/message";
    }
    //main 댓글 수정
    @PostMapping("MainBoardView/modify/main/comment")
    public String ModifyMainComment(Model model, CommentForm commentForm, MainCommentData mainCommentData){
        String id = commentForm.getUser(); // session id
        String idx = commentForm.getIdx(); // 댓글 번호
        String write_id = commentForm.getUserId(); // 작성 id
        String redirect = commentForm.getNumber(); // 다시 돌아갈 주소, 현재 글 idx
        boolean check = mainService.ModifyComment(id,idx,write_id,mainCommentData);
        if(check){
            System.out.println("수정완료");
            model.addAttribute("message","수정완료");
        }
        else {
            System.out.println("수정불가");
            model.addAttribute("message","작성자만 수정이 가능합니다.");
        }
        model.addAttribute("returnurl","MainBoardView/"+redirect);
        return "/util/message";
    }
    //main 댓글 삭제
    @GetMapping ("/delete/main/comment/{id}/{idx}/{write_id}/{return_idx}")
    public String DeleteMainComment(Model model, @PathVariable String id, @PathVariable String idx,@PathVariable String write_id,
                                    @PathVariable String return_idx){
        String redirect = return_idx; // 다시 돌아갈 주소
        boolean check = mainService.deletecomment(id,idx,write_id);
        if(check){
            System.out.println("삭제완료");
            model.addAttribute("message","삭제완료");
        }
        else {
            System.out.println("삭제불가");
            model.addAttribute("message","작성자가 아닙니다.");
        }
        model.addAttribute("returnurl","MainBoardView/"+redirect);
        return "/util/message";
    }

}

package com.example.Graduation.controller;

import com.example.Graduation.DTO.CommentForm;
import com.example.Graduation.DTO.WriteForm;
import com.example.Graduation.Entity.WriteCommentData;
import com.example.Graduation.Entity.WriteData;
import com.example.Graduation.Repository.WriteCommentDataRepository;
import com.example.Graduation.Repository.WriteRepository;
import com.example.Graduation.Service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.constant.Constable;

@Controller
public class WriteController {
    @Autowired
    private WriteService writeService;

    @Autowired
    WriteRepository writeRepository;
    @Autowired
    WriteCommentDataRepository writeCommentDataRepository;
    @GetMapping("/Board/{menu}") // 페이지 역순으로 정렬
    public String BoardList(Model model, @PageableDefault(page = 0, size = 10,
            sort = "idx", direction = Sort.Direction.DESC) Pageable pageable ,String search, @PathVariable String menu){
        Page<WriteData> list= null;
        Page<WriteData> list_= null;
        System.out.println(menu);
        if(search == null){
            list = writeService.boardList(menu,pageable);
        }
        else{
            list = writeService.WirteSearchlist(search,pageable);
        }

        //페이지 처리
        int page = list.getPageable().getPageNumber();
        int nowPage = list.getPageable().getPageNumber() + 1; // 페이지는 0부터 읽는다
        int prePage = Math.max(page - 1 , 0);
        int nextPage = Math.min(page + 1 , list.getTotalPages()-1);
        model.addAttribute("pageMenu",menu);
        model.addAttribute("List", list);
        model.addAttribute("page", page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("prePage",prePage);
        model.addAttribute("nextPage",nextPage);
        return "Board";
    }

    @GetMapping("/Write")
    public String main(Model model){
        return "Write";
    }

    //글 작성
    @GetMapping("/savePost")
    public String savePost(Model model, WriteData writeData, @RequestParam(value = "files" , required = false) MultipartFile files) throws Exception{
        System.out.println(writeData.toString());
        writeService.savePost(writeData,files);
        model.addAttribute("message","글 등록 완료");
        model.addAttribute("returnurl","Board");
        return "/util/message";
    }

    @PostMapping("/Write/create")
    public String newWrite(WriteData writeData, @RequestParam(value = "files" , required = false) MultipartFile files, Model model) throws IOException {
        System.out.println(writeData);
        writeService.write(writeData,files);
        String menu = writeData.getMenu();
        System.out.println("글 등록 완료");
        model.addAttribute("message","글 등록 완료");
        model.addAttribute("returnurl","Board/"+menu);
        return "/util/message";
    }
    //댓글 작성
    @PostMapping("/WriteComment/create")
    private String newComment(WriteCommentData writeCommentData, Model model){
        System.out.println(writeCommentData);
        System.out.println("글 등록 완료");
        String redirect = writeCommentData.getNumber();
        writeService.CommentWrite(writeCommentData);
        model.addAttribute("message","댓글 등록 완료");
        model.addAttribute("returnurl","WriteView/"+writeService.WriteView(redirect).getIdx());
        return "/util/message";
    }
    //main 댓글 수정
    @PostMapping("/modify/write/comment")
    public String modifyWriteComment(Model model, CommentForm commentForm, WriteCommentData writeCommentData){
        String id = commentForm.getUser(); // session id
        String idx = commentForm.getIdx(); // 댓글 번호
        String write_id = commentForm.getUserId(); // 작성 id
        String redirect = commentForm.getNumber(); // 다시 돌아갈 주소, 현재 글 idx
        System.out.println("아이디 : "+id +" " + " write_id");
        boolean check = writeService.modifyComment(id,idx,write_id,writeCommentData);
        if(check){
            System.out.println("수정완료");
            model.addAttribute("message","수정완료");
        }
        else {
            System.out.println("수정불가");
            model.addAttribute("message","작성자만 수정이 가능합니다.");
        }
        model.addAttribute("returnurl","WriteView/"+redirect);
        return "/util/message";
    }
    //main 댓글 삭제
    @GetMapping  ("/delete/write/comment/{id}/{idx}/{write_id}/{return_idx}")
    public String deleteWriteComment(Model model, @PathVariable String id, @PathVariable String idx,@PathVariable String write_id,
                                    @PathVariable String return_idx){
        String redirect = return_idx; // 다시 돌아갈 주소
        boolean check = writeService.deletecomment(id,idx,write_id);
        if(check){
            System.out.println("삭제완료");
            model.addAttribute("message","삭제완료");
        }
        else {
            System.out.println("삭제불가");
            model.addAttribute("message","작성자가 아닙니다.");
        }
        model.addAttribute("returnurl","WriteView/"+redirect);
        return "/util/message";
    }
    // 게시글 삭제 idx = 선택 게시글 pk , id = 세션 로그인 id, idt = 글 작성한 id
    @GetMapping ("/WriteView/{idx}/{id}/{idt}/delete")
    public String writeDelete(@PathVariable String idx,@PathVariable String id,@PathVariable String idt, Model model){
        if(id.equals(idt)){
            System.out.println(idx);
            writeService.writeDelete(idx);
            System.out.println("글 삭제 완료");
            model.addAttribute("message","게시글 삭제 완료");
        }
        else if(id.equals("pjm6401")){
            writeService.writeDelete(idx);
            System.out.println("글 삭제 완료");
            model.addAttribute("message","게시글 삭제 완료");
        }
        else{
            model.addAttribute("message","본인이 작성한 글만 삭제 가능합니다.");
        }
        model.addAttribute("returnurl","Board");
        return "/util/message";
    }
    //특정 게시글 수정 idx = 선택 게시글 pk , id = 세션 로그인 id, idt = 글 작성한 id
    @GetMapping("/WriteView/{idx}/{id}/{idt}/modify")
    public String writeModify(@PathVariable String idx, @PathVariable String id, @PathVariable String idt, Model model){
        if(id.equals(idt)){
            System.out.println("글 수정 페이지");
            model.addAttribute("writeview",writeService.WriteView(idx));
            return "WriteModify";
        }
        else{
            model.addAttribute("message","본인이 작성한 글만 수정 가능합니다.");
            model.addAttribute("returnurl","WriteView"+writeService.WriteView(idx));
            return "/util/message";
        }
    }
    //특정 게시글 업데이트
    @PostMapping("WriteUpdate")
    public String writeUpdate(WriteForm writeForm, WriteData writeData, @RequestParam(value = "file" , required = false) MultipartFile file, Model model) throws IOException {
        WriteData writeTemp = writeService.WriteView(writeForm.getIdx());
        writeTemp.setTitle(writeForm.getTitle());
        writeTemp.setContent(writeForm.getContent());
        writeService.write(writeTemp, file);
        System.out.println("글 수정 완료");
        model.addAttribute("message","게시글 수정 완료");
        model.addAttribute("returnurl","Board");
        return "/util/message";
    }
    //특정 게시글 보기
    @GetMapping("/WriteView/{idx}")
    public String WriteView(Model model, @PathVariable String idx, @PageableDefault(page = 0, size = 10,
    sort = "idx", direction = Sort.Direction.DESC) Pageable pageable ,String search){ // local host::8080/board/view?title=?
        Page<WriteCommentData> list= null;
        list = writeService.WriteCommentList(idx,pageable);
        //페이지 처리
        int page = list.getPageable().getPageNumber();
        int nowPage = list.getPageable().getPageNumber() + 1; // 페이지는 0부터 읽는다
        int prePage = Math.max(page - 1 , 0);
        int nextPage = Math.min(page + 1 , list.getTotalPages()-1);
        model.addAttribute("writeview",writeService.WriteView(idx));
        model.addAttribute("List", list);
        model.addAttribute("page", page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("prePage",prePage);
        model.addAttribute("nextPage",nextPage);
        return "WriteView";
    }

}

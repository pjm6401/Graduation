package com.example.Graduation.Service;


import com.example.Graduation.Entity.MainCommentData;
import com.example.Graduation.Entity.WriteCommentData;
import com.example.Graduation.Entity.WriteData;
import com.example.Graduation.Repository.WriteCommentDataRepository;
import com.example.Graduation.Repository.WriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class WriteService {
    @Autowired
    private WriteRepository writeRepository;
    @Autowired
    private WriteCommentDataRepository writeCommentDataRepository;
    //글 작성 처리
    public void write(WriteData writeData , MultipartFile files) throws IOException {
        //파일경로
        String projectpath = System.getProperty("user.dir")+"//src//main//resources//static//files";
        //식별자
        UUID uuid = UUID.randomUUID();
        //저장될 파일이름 생성
        String filename = uuid + "-" + files.getOriginalFilename();

        File saveFile = new File(projectpath,filename);
        files.transferTo(saveFile);
        if(filename.contains("JPEG") || filename.contains("GIF")
                || filename.contains("BMP") || filename.contains("PNG") || filename.contains("jpg")
                || filename.contains("jpeg") || filename.contains("png")) {
            writeData.setFile_name(filename);
            writeData.setFile_path("/files/" + filename);
        }
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String wirte_time = now.format(formatter);
        writeData.setWrite_time(wirte_time);
        System.out.println(writeData.toString());
        writeRepository.save(writeData);
    }
    //파일
    //특정 게시글 보기
    public WriteData WriteView(String idx){
        return writeRepository.findById(idx).get();
    }
    //게시글 리스트
    public Page<WriteData> boardList(Pageable pageable){
        return writeRepository.findAll(pageable);
    }
    public Page<WriteData> boardListByID(String id,Pageable pageable){
        return writeRepository.findById(id,pageable);
    }
    //Board 특정 개시글 댓글 불러오기
    public Page<WriteCommentData> WriteCommentList(String number, Pageable pageable) {
        return writeCommentDataRepository.findByNumber(number,pageable);
    }
    public Page<WriteCommentData> WriteCommentListById(String id, Pageable pageable) {
        return writeCommentDataRepository.findByUserId(id,pageable);
    }
    //특정 게시글 검색
    public Page<WriteData> WirteSearchlist(String search, Pageable pageable){
        return writeRepository.findByTitleContaining(search, pageable);
    }
    //특정 게시글 삭제
    @Transactional
    public void writeDelete(String idx){
        writeCommentDataRepository.deleteByNumber(idx);
        writeRepository.deleteById(idx);
        //writeCommentDataRepository.deleteByNumber(idx);
    }


    //main 특정 개시글 댓글 불러오기

    //댓글 등록
    public void CommentWrite(WriteCommentData writeCommentData){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String wirte_time = now.format(formatter);
        writeCommentData.setWrite_time(wirte_time);
        System.out.println(writeCommentData.toString());
        writeCommentDataRepository.save(writeCommentData);
    }
    //댓글 수정
    public boolean modifyComment(String id, String idx, String write_id, WriteCommentData writeCommentData) {
        WriteCommentData writeCommentDataTemp;
        System.out.println(id + " " + idx +" "+write_id);
        if(id.equals(write_id)){
            writeCommentDataTemp = writeCommentDataRepository.findByIdx(idx);
            System.out.println(writeCommentDataTemp);
            writeCommentDataTemp.setComment(writeCommentData.getComment());
            System.out.println(writeCommentDataTemp);
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String write_time = now.format(formatter);
            writeCommentDataTemp.setWrite_time(write_time);
            writeCommentDataRepository.save(writeCommentDataTemp);
            return true;
        }
        else return false;
    }
    //댓글 삭제
    public boolean deletecomment(String id, String idx, String write_id) {
        if(id.equals(write_id)){
            writeCommentDataRepository.deleteById(idx);
            return true;
        }
        else if(id.equals("pjm6401")){
            writeCommentDataRepository.deleteById(idx);
            return true;
        }
        else return false;
    }
}


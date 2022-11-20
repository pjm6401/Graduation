package com.example.Graduation.controller;

import com.example.Graduation.DTO.WeatherDTO;
import com.example.Graduation.Repository.MainWriteRepository;
import com.example.Graduation.Repository.WriteRepository;
import com.example.Graduation.Service.HomeService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    MainWriteRepository mainWriteRepository; // 메인
    @Autowired
    WriteRepository writeRepository; // 게시판
    @GetMapping("/Home") //연결요청  ()에 접속할 url주소
    public String home(Model model) throws IOException, ParseException {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String time = now.format(formatter);
        WeatherDTO weatherDTO = HomeService.WeatherCoding();
        model.addAttribute("Weather",weatherDTO.getMain());
        model.addAttribute("WeatherIcon",weatherDTO.getIcon());
        model.addAttribute("Temp",weatherDTO.getTemp());
        model.addAttribute("TempIcon",weatherDTO.getTempIcon());
        model.addAttribute("Time",time);
        model.addAttribute("Main",mainWriteRepository.findById("6").get());
        model.addAttribute("Board", writeRepository.findById("76").get());
        return "Home"; // /home 이 입력이 된다면 mustache라는 페이지를 가져와서 열어준다
    }
}

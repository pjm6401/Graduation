package com.example.Graduation.Service;


import com.example.Graduation.DTO.WeatherDTO;
import com.example.Graduation.Entity.MainCommentData;
import com.example.Graduation.Entity.MainWriteData;
import com.example.Graduation.Entity.MainimgData;
import com.example.Graduation.Repository.MainCommentRepository;
import com.example.Graduation.Repository.MainImgRepository;
import com.example.Graduation.Repository.MainWriteRepository;
import com.example.Graduation.Repository.RegisterRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class MainService {
    @Autowired
    MainImgRepository mainImgRepository; //매인 이미지 repository
    @Autowired
    MainWriteRepository mainWriteRepository; // 메인 글 repository
    @Autowired
    MainCommentRepository mainCommentRepository; //댓글
    @Autowired
    RegisterRepository registerRepository; // 작성자
    //mainIMG 등록
    public void mainImgcreate(MainimgData mainimgData, MultipartFile file) throws IOException {
        String projectpath = System.getProperty("user.dir") + "//src//main//resources//static//MainImg";
        //식별자
        UUID uuid = UUID.randomUUID();
        //저장될 파일이름 생성
        String filename = uuid + "-" + file.getOriginalFilename();

        File saveFile = new File(projectpath, filename);
        file.transferTo(saveFile);
        if (filename.contains("JPEG") || filename.contains("GIF")
                || filename.contains("BMP") || filename.contains("PNG") ||
                filename.contains("jpg") || filename.contains("jpeg") || filename.contains("png")) {
            mainimgData.setFile_path("/MainImg/" + filename);
            mainimgData.setFile_name(filename);
        }
        mainImgRepository.save(mainimgData);
    }

    //Main 글 등록
    public void mainWritecreate(MainWriteData mainWriteData, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException,
            ParseException {
        String projects = System.getProperty("user.dir") + "//src//main//resources//static//MainWriteFile";
        //식별자
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        //저장될 파일이름 생성
        String filename1 = uuid1 + "-" + file1.getOriginalFilename();
        String filename2 = uuid2 + "-" + file2.getOriginalFilename();
        String filename3 = uuid3 + "-" + file3.getOriginalFilename();

        File saveFile1 = new File(projects, filename1);
        File saveFile2 = new File(projects, filename2);
        File saveFile3 = new File(projects, filename3);
        file1.transferTo(saveFile1);
        if (filename1.contains("JPEG") || filename1.contains("GIF")
                || filename1.contains("BMP") || filename1.contains("PNG") ||
                filename1.contains("jpg") || filename1.contains("jpeg") || filename1.contains("png")) {
            mainWriteData.setFile_path1("/MainWriteFile/" + filename1);
            mainWriteData.setFile_name1(filename1);
        }
        file2.transferTo(saveFile2);
        if (filename2.contains("JPEG") || filename2.contains("GIF")
                || filename2.contains("BMP") || filename2.contains("PNG") ||
                filename2.contains("jpg") || filename2.contains("jpeg") || filename2.contains("png")) {
            mainWriteData.setFile_path2("/MainWriteFile/" + filename2);
            mainWriteData.setFile_name2(filename2);
        }
        file3.transferTo(saveFile3);
        if (filename3.contains("JPEG") || filename3.contains("GIF")
                || filename3.contains("BMP") || filename3.contains("PNG") ||
                filename3.contains("jpg") || filename3.contains("jpeg") || filename3.contains("png")) {
            mainWriteData.setFile_path3("/MainWriteFile/" + filename3);
            mainWriteData.setFile_name3(filename3);
        }
        //작성시간
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String write_time = now.format(formatter);
        mainWriteData.setWrite_time(write_time);
        //위,경도

        String[] test = geoCoding(mainWriteData.getDes());
        System.out.println(test[0] + " | " + test[1]);
        mainWriteData.setLat(test[0]);
        mainWriteData.setLng(test[1]);
        System.out.println(mainWriteData);
        mainWriteRepository.save(mainWriteData);
    }

    //mainBoard 목록 보여주기
    public Page<MainWriteData> writeMonthlist(String month, org.springframework.data.domain.Pageable pageable) {
        return mainWriteRepository.findByMonth(month,pageable);
    }
    //댓글 등록
    public void CommentWrite(MainCommentData mainCommentData){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String write_time = now.format(formatter);
        mainCommentData.setWrite_time(write_time);
        System.out.println(mainCommentData.toString());
        mainCommentRepository.save(mainCommentData);
    }
    //main 특정 개시글 보기
    public MainWriteData MainWriteview(String idx) throws IOException, ParseException {
        return mainWriteRepository.findById(idx).get();
    }
    //main 특정 개시글 댓글 불러오기
    public Page<MainCommentData> MainCommentList(String number, Pageable pageable) {
        return mainCommentRepository.findByNumber(number,pageable);
    }
    //main id 기준 댓글 불러오기
    public Page<MainCommentData> MainCommentListById(String id, Pageable pageable) {
        return mainCommentRepository.findByUserId(id,pageable);
    }
    //특정 개시글 삭제
    public void writeDelete(String idx){
        mainWriteRepository.deleteById(idx);
    }

    //댓글 삭제
    public boolean deletecomment(String id, String idx, String write_id){
        if(id.equals(write_id)){
            mainCommentRepository.deleteById(idx);
            return true;
        }
        else if(id.equals("pjm6401")){
            mainCommentRepository.deleteById(idx);
            return true;
        }
        else return false;
    }
    //댓글 수정
    public boolean ModifyComment(String id, String idx, String write_id,MainCommentData mainCommentData){
        MainCommentData mainCommentDataTemp;
        if(id.equals(write_id)){
            mainCommentDataTemp =  mainCommentRepository.findByIdx(idx);
            mainCommentDataTemp.setComment(mainCommentData.getComment());
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String write_time = now.format(formatter);
            mainCommentDataTemp.setWrite_time(write_time);
            mainCommentRepository.save(mainCommentDataTemp);
            return true;
        }
        else return false;
    }
    //main view 보여주기
    public MainimgData monthImg(String idx) {
        return mainImgRepository.findById(idx).get();
    }
    //특정 댓글 load
    public MainCommentData comment(String idx){return  mainCommentRepository.findByIdx(idx);}
    //위.경도 변환
    public static String[] geoCoding(String location) throws IOException, ParseException {
        String API_KEY = "AIzaSyA5ZUdN1MwmglPhlNgkPEKPHOGVfjYKiLg";
        String API = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                URLEncoder.encode(location, StandardCharsets.UTF_8) + "&key=" + API_KEY;
        String[] LatLng = new String[2];
        URL url = new URL(API);
        System.out.println(url);
        URLConnection conn = url.openConnection();
        BufferedReader read = null;
        read = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuffer docJson = new StringBuffer();
        String line;
        while ((line = read.readLine()) != null) {
            docJson.append(line);
        }
        String jsonString = docJson.toString();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONArray results = (JSONArray) jsonObject.get("results");
        for (Object result : results) {
            JSONObject resulted = (JSONObject) result;
            JSONObject geometric = (JSONObject) resulted.get("geometry");
            JSONObject locations = (JSONObject) geometric.get("location");
            Double Lat = (Double) locations.get("lat");
            Double Lng = (Double) locations.get("lng");
            String lat = Lat.toString();
            String lng = Lng.toString();
            LatLng[0] = lat;
            LatLng[1] = lng;
            System.out.println(lat + " | " + lng);
        }
            read.close();
            return LatLng;
    }
    //날씨
    public static WeatherDTO WeatherCoding(String lat, String lng) throws IOException, ParseException {

        String API_KEY = "ecac049f368fc4f5c5989492fcf32e5d";
        String API = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&appid="+API_KEY+"&units=metric";
        String[] Weather = new String[7];
        URL url = new URL(API);
        System.out.println(url);
        URLConnection conn = url.openConnection();
        BufferedReader read = null;
        read = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuffer docJson = new StringBuffer();
        String line;
        while ((line = read.readLine()) != null) {
            docJson.append(line);
        }
        String jsonString = docJson.toString();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONArray weather = (JSONArray) jsonObject.get("weather");
        JSONObject main = (JSONObject) jsonObject.get("main");
        String place = (String) jsonObject.get("name");
        Double temp = (Double) main.get("temp");
        Double feel_temp = (Double) main.get("feels_like");
        Double temp_min = (Double) main.get("temp_min");
        Double temp_max = (Double) main.get("temp_max");
        String tempToString = temp.toString();
        String feel_tempToString = feel_temp.toString();
        String temp_minToString = temp_min.toString();
        String temp_maxToString = temp_max.toString();
        String weatherMain = null;
        String weatherDescription = null;
        String weatherIcon = null; // JSON 숫자
        String weatherIconStr ; // 날씨 아이콘
        String tempIcon ; // 온도 아이콘
        for (Object o : weather) {
            JSONObject resulted = (JSONObject) o;
            weatherMain = (String) resulted.get("main");
            weatherDescription = (String) resulted.get("description");
            String weatherIconTemp = (String) resulted.get("icon");
            System.out.println(weatherIconTemp +"icon");
            String one = String.valueOf(weatherIconTemp.charAt(0));
            String two = String.valueOf(weatherIconTemp.charAt(1));
            weatherIcon = one+two;
            System.out.println(weatherIcon);
        }
        String [] TempChange = changeTemp(tempToString,feel_tempToString);
        String tempToInt =TempChange[0];
        String feel_tempToInt =TempChange[1];
        System.out.println(weatherIcon + " icon");
        weatherIconStr = choiceWeatherIcon(weatherIcon);
        tempIcon = choiceTempIcon(tempToString);
        System.out.println(weatherIconStr +" Str");
        WeatherDTO weatherDTO = new WeatherDTO(weatherMain,weatherDescription,weatherIconStr,
                tempToInt, tempIcon, feel_tempToInt,temp_minToString,temp_maxToString,place);

        read.close();
        weatherDTO.toString();
        return weatherDTO;
    }
    //choice icon
    public static String choiceWeatherIcon(String weatherIcon){
        String icon;
        switch (weatherIcon) {
            case "01" -> { // 맑음
                System.out.println(weatherIcon);
                icon = "fas fa-sun";
            }
            case "02" -> { // 해 + 구름
                System.out.println(weatherIcon);
                icon = "fas fa-cloud-sun";
            }
            case "03" ,"04" -> { // 구름  깨진 구름
                System.out.println(weatherIcon);
                icon = "fas fa-cloud";
            }
            case "09" -> { // shower rain
                System.out.println(weatherIcon);
                icon = "fas fa-cloud-rain";
            }
            case "10" -> { // rain
                System.out.println(weatherIcon);
                icon = "fas fa-cloud-showers-heavy";
            }
            case "11" -> { // 번개 구름
                System.out.println(weatherIcon);
                icon = "fas fa-cloud-bolt";
            }
            case "13" -> {
                System.out.println(weatherIcon);
                icon = "fas fa-snowflake";
            }
            case "50" -> {
                System.out.println(weatherIcon);
                icon = "fas fa-smog";
            }
            default -> {
                System.out.println(" 없습니다.");
                return null;
            }
        }
        return icon;
    }
    public static String choiceTempIcon(String tempIcon){
        String icon;
        double temp = Double.parseDouble(tempIcon);
        if(temp<=0){
            icon ="fa-solid fa-temperature-empty";
        }
        else if(temp>0 && temp<=20){
            icon ="fa-solid fa-temperature-half";
        }
        else {
            icon ="fa-solid fa-temperature-full";
        }
        return icon;
    }
    public static String[] changeTemp(String temp, String feel_temp){
        String T_first = String.valueOf(temp.charAt(0));
        String T_second =  String.valueOf(temp.charAt(1));
        String F_first = String.valueOf(feel_temp.charAt(0));
        String F_second =  String.valueOf(feel_temp.charAt(1));
        String NewTemp = T_first+T_second;
        String New_Feel_Temp = F_first + F_second;

        return new String[]{NewTemp,New_Feel_Temp};
    }
}






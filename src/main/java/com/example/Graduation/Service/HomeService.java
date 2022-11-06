package com.example.Graduation.Service;

import com.example.Graduation.DTO.WeatherDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HomeService {
    public static WeatherDTO WeatherCoding() throws IOException, ParseException {
        String lat = "37.27538";
        String lng = "127.05488";
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
        read.close();
        String [] TempChange = changeTemp(tempToString,feel_tempToString);
        String tempToInt =TempChange[0];
        String feel_tempToInt =TempChange[1];
        System.out.println(weatherIcon + " icon");
        weatherIconStr = choiceWeatherIcon(weatherIcon);
        tempIcon = choiceTempIcon(tempToString);
        System.out.println(weatherIconStr +" Str");
        WeatherDTO weatherDTO = new WeatherDTO(weatherMain,weatherDescription,weatherIconStr,
                tempToInt, tempIcon, feel_tempToInt,temp_minToString,temp_maxToString,place);


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

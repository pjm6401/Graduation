package com.example.Graduation.DTO;

public class WeatherDTO {
    private final String Main;
    private final String Description;
    private final String Icon;
    private final String Temp;
    private final String TempIcon;
    private final String Feel_Temp;
    private final String Temp_min;
    private final String Temp_max;
    private final String Place;

    public WeatherDTO(String main, String description, String icon, String temp, String tempIcon, String feel_Temp,
                      String temp_min, String temp_max, String place) {
        Main = main;
        Description = description;
        Icon = icon;
        Temp = temp;
        TempIcon = tempIcon;
        Feel_Temp = feel_Temp;
        Temp_min = temp_min;
        Temp_max = temp_max;
        Place = place;
    }

    public String getMain() {
        return Main;
    }

    public String getIcon() {
        return Icon;
    }

    public String getTemp() {
        return Temp;
    }

    public String getTempIcon() {
        return TempIcon;
    }

    @Override
    public String toString() {
        return "WeatherDTO{" +
                "Main='" + Main + '\'' +
                ", Description='" + Description + '\'' +
                ", Icon='" + Icon + '\'' +
                ", Temp='" + Temp + '\'' +
                ", TempIcon='" + TempIcon + '\'' +
                ", Feel_Temp='" + Feel_Temp + '\'' +
                ", Temp_min='" + Temp_min + '\'' +
                ", Temp_max='" + Temp_max + '\'' +
                ", Place='" + Place + '\'' +
                '}';
    }
}


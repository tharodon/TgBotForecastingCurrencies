package ru.liga.utils;

public class Transate {
    public static String translatorToRussian(String dayOfWeek){
        switch (dayOfWeek){
            case ("Mon"): return "Пн";
            case ("Tue"): return "Вт";
            case ("Wed"): return "Ср";
            case ("Thu"): return "Чт";
            case ("Fri"): return "Пт";
            case ("Sat"): return "Сб";
            case ("Sun"): return "Вс";
            default:break;
        }
        return dayOfWeek;
    }
}

package com.example.youngyeehomies.hssapp;


public class DateTimeConverter {

    public DateTimeConverter(){}

    public String convertDateAndTime(String date, String time) {

        String convertedDate, convertedTime, returnedDate;

        convertedDate = date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2);

        switch(time) {
            case("10:00 A.M."):
                convertedTime = "10:00:00";
                break;
            case("10:30 A.M."):
                convertedTime = "10:30:00";
                break;
            case("11:00 A.M."):
                convertedTime = "11:00:00";
                break;
            case("11:30 A.M."):
                convertedTime = "11:30:00";
                break;
            case("12:00 P.M."):
                convertedTime = "12:00:00";
                break;
            case("12:30 P.M."):
                convertedTime = "12:30:00";
                break;
            case("01:00 P.M."):
                convertedTime = "13:00:00";
                break;
            case("01:30 P.M."):
                convertedTime = "13:30:00";
                break;
            case("02:00 P.M."):
                convertedTime = "14:00:00";
                break;
            case("02:30 P.M."):
                convertedTime = "14:30:00";
                break;
            case("03:00 P.M."):
                convertedTime = "15:00:00";
                break;
            case("03:30 P.M."):
                convertedTime = "15:30:00";
                break;
            case("04:00 P.M."):
                convertedTime = "16:00:00";
                break;
            case("04:30 P.M."):
                convertedTime = "16:30:00";
                break;
            case("05:00 P.M."):
                convertedTime = "17:00:00";
                break;
            case("05:30 P.M."):
                convertedTime = "17:30:00";
                break;
            default:
                convertedTime = "";
                break;
        }

        returnedDate = convertedDate + " " + convertedTime;

        return returnedDate;
    }
}

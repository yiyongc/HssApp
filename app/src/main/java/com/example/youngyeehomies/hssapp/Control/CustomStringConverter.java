package com.example.youngyeehomies.hssapp.Control;

/*
** This class is a really simple converter to convert date and time displayed to the
** right format for the database. It also reads fields from database and converts it
** to a more "display friendly" format for the user.
*/

public class CustomStringConverter {

    public CustomStringConverter(){}

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


    public String convertDate (String date) {
        String convertedDate;

        convertedDate = date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2);

        return convertedDate;
    }

    public String convertTimeForSpinner (String time) {
        String convertedTime="";

        switch (time) {
            case ("1000"):
                convertedTime = "10:00 A.M.";
                break;
            case ("1030"):
                convertedTime = "10:30 A.M.";
                break;
            case ("1100"):
                convertedTime = "11:00 A.M.";
                break;
            case ("1130"):
                convertedTime = "11:30 A.M.";
                break;
            case ("1200"):
                convertedTime = "12:00 P.M.";
                break;
            case ("1230"):
                convertedTime = "12:30 P.M.";
                break;
            case ("1300"):
                convertedTime = "01:00 P.M.";
                break;
            case ("1330"):
                convertedTime = "01:30 P.M.";
                break;
            case ("1400"):
                convertedTime = "02:00 P.M.";
                break;
            case ("1430"):
                convertedTime = "02:30 P.M.";
                break;
            case ("1500"):
                convertedTime = "03:00 P.M.";
                break;
            case ("1530"):
                convertedTime = "03:30 P.M.";
                break;
            case ("1600"):
                convertedTime = "04:00 P.M.";
                break;
            case ("1630"):
                convertedTime = "04:30 P.M.";
                break;
            case ("1700"):
                convertedTime = "05:00 P.M.";
                break;
            case ("1730"):
                convertedTime = "05:30 P.M.";
                break;
            default:
                break;
        }

        return convertedTime;
    }
}

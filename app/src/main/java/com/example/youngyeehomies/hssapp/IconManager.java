package com.example.youngyeehomies.hssapp;

/**
 * Created by Young Yee on 13/4/2015.
 *
 * This class will display icons based on the Appointment Category ID
 */
public class IconManager {

    private int IconResource;

    public int setIconResource(int AppointmentCatID){

        switch(AppointmentCatID){
            case 20:
                IconResource = R.drawable.general_ic;
                break;
            case 21:
                IconResource = R.drawable.baby_ic;
                break;
            case 22:
                IconResource = R.drawable.ent_ic;
                break;
            case 23:
                IconResource = R.drawable.dental_ic;
                break;
            case 24:
                IconResource = R.drawable.women_ic;
                break;
            default:
                IconResource = R.drawable.default_cat_icon;
                break;
        }

        return this.IconResource;
    }





    public int getIconResource(){
        return this.IconResource;

    }

}

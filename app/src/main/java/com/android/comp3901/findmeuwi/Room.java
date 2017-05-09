package com.android.comp3901.findmeuwi;

import java.util.List;

/**
 * Created by Akinyele on 1/29/2017.
 */

public class Room  extends Place{
    /**
     * Constructs a new Address object set to the given Locale and with all
     * other fields initialized to null or false.
     *
     * @param locale
     */

    private String Description;
    private final double floor;

    //private string imageName;



    public Room(String id, String rmName, double mLatitude, double mLongitude,int known, double familiarity, double floor, String desc,int landmark) {
        super(id, rmName, mLatitude, mLongitude, "ROOM", known, familiarity, landmark);

        this.familiarity = familiarity;
        this.known = known;
        this.floor = floor;
        this.Description = desc;
    }

    public void updateDB(){

        DB_Helper.getInstance(FindMe.get()).updateRoom(super.getId(),known,familiarity);

    }




    /***
     *Getters and setters
     ***/
    public double getFloor() {
        return floor;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



}

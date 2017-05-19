package com.android.comp3901.findmeuwi.locations;

import com.android.comp3901.findmeuwi.services.DB_Helper;
import com.android.comp3901.findmeuwi.activities.FindMe;

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
    private final int floor;
    private final String building = "NA"; //TODO remove when building are included

    //private string imageName;



    public Room(String id, String rmName, double mLatitude, double mLongitude,int known, double familiarity, int floor, String desc,int landmark) {
        super(id, rmName, mLatitude, mLongitude, "ROOM", known, familiarity, landmark, floor);

        this.floor = floor;
        this.Description = desc;
    }

    @Override
    public void updateDB(){
         DB_Helper.getInstance(FindMe.get()).updateRoom(super.getId(),known,familiarity);
    }




    /***
     *Getters and setters
     ***/
    public double getFloor() {
        return floor;
    }


    @Override
    public String getInfo() {
        String info =  super.getInfo()+ "\r\n" +
                "Floor: " + getFloor()+ "\r\n" +
                "Building: " +getBuilding();
        return info;
    }






    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public String getBuilding() {
        return building;
    }
}

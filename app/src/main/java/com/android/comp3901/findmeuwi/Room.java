package com.android.comp3901.findmeuwi;

import java.util.List;

/**
 * Created by Akinyele on 1/29/2017.
 */

public class Room  extends Vertex{
    /**
     * Constructs a new Address object set to the given Locale and with all
     * other fields initialized to null or false.
     *
     * @param locale
     */

    private String Description;
    private final double floor;
    private int known;
    private double familiarity;
    //private string imageName;


    //TODO add room description parameter
    public Room(String id, String rmName, double mLatitude, double mLongitude, double floor, int known, double familiarity, String desc ) {
        super(id, rmName, mLatitude, mLongitude, "ROOM");

        this.familiarity = familiarity;
        this.known = known;
        this.floor = floor;
        this.Description = desc;
    }

    public void updateDB(){

        DB_Helper.getInstance(FindMe.get()).updateRoom(super.getId(),known,familiarity);

    }

    public  void updateFamiliarity(Double value){
        this.familiarity += value;
    }

    public boolean isKnown(){
        return this.known==1;
    }

    /***
     *Getters and setters
     ***/
    public double getFloor() {
        return floor;
    }

    public Integer getKnown() {
        return known;
    }

    public void setKnown(Integer known) {
        this.known = known;
    }

    public double getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(double familiarity) {
        this.familiarity = familiarity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



}

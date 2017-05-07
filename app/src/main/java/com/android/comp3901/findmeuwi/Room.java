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

    private List<String> Description;
    private final double floor;
    private boolean known;
    private double familiarity;
    //private string imageName;


    //TODO add room description parameter
    public Room(String id, String rmName, double mLatitude, double mLongitude, double floor) {
        super(id, rmName, mLatitude, mLongitude, "ROOM");

        this.floor = floor;
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

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public double getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(double familiarity) {
        this.familiarity = familiarity;
    }

    public List<String> getDescription() {
        return Description;
    }

    public void setDescription(List<String> description) {
        Description = description;
    }

}

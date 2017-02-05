package com.android.comp3901.findmeuwi;

import android.location.Address;

import java.util.List;
import java.util.Locale;

/**
 * Created by Akinyele on 1/29/2017.
 */

public class Room  {
    /**
     * Constructs a new Address object set to the given Locale and with all
     * other fields initialized to null or false.
     *
     * @param locale
     */
    private  final String rmName;
    private double mLatitude;
    private double mLongitude;
    private List<String> Description;
    private double floor;
    private boolean known;
    private double familiarity;
    //private string imageName;


    public Room(String rmName) {
        this.rmName = rmName;
    }


    public void setLat( Double Lat){
        this.mLatitude = Lat;
    }


    public void setLng( Double Lng){
        this.mLongitude = Lng;
    }

    public double getLat(){
        return this.mLatitude;
    }

    public double getLng(){
        return this.mLongitude;
    }


    public double getFloor() {
        return floor;
    }

    public void setFloor(double floor) {
        this.floor = floor;
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

    public String getRmName() {
        return rmName;
    }
}

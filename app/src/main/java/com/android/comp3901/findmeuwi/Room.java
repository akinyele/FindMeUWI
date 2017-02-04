package com.android.comp3901.findmeuwi;

import android.location.Address;

import java.util.List;
import java.util.Locale;

/**
 * Created by Akinyele on 1/29/2017.
 */

public class Room extends Address {
    /**
     * Constructs a new Address object set to the given Locale and with all
     * other fields initialized to null or false.
     *
     * @param locale
     */
    private double mLatitude;
    private double mLongitude;
    private List<String> Description;
    private double floor;
    private boolean known;
    private double familiarity;
    //private string imageName;


    public Room(Locale locale) {
        super(locale);
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



}

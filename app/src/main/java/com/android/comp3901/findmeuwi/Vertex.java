package com.android.comp3901.findmeuwi;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Akinyele on 2/10/2017.
 */

public class Vertex {
    final private String id;
    final private String name;
    final private String type;
    private int landmark;

    final private double lat;
    final private double lng;

    /*
    final private int level;
    final private string type;

     */


    public Vertex(String id, String name, double lat, double lng, String Type, int landmark) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.type = Type;
        this.landmark = landmark;
    }


    public Boolean isLandmark() {
        return landmark>1;
    }

    public void setLandmark(int landmark) {
        this.landmark = landmark;
    }

    public final String getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public final double getLat() {
        return lat;
    }

    public final double getLng() {
        return lng;
    }

    public final LatLng getLL(){

        return new LatLng(getLat(),getLng());
    }


    public final String getType() {
        return type;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}

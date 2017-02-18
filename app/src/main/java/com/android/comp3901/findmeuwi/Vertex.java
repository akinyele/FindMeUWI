package com.android.comp3901.findmeuwi;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Akinyele on 2/10/2017.
 */

public class Vertex {
    final private String id;
    final private String name;
    final private String type;

    final private double lat;
    final private double lng;

    /*
    final private int level;
    final private string type;

     */


    public Vertex(String id, String name, double lat, double lng, String Type) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.type = Type;

    }




    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public LatLng getLL(){

        return new LatLng(getLat(),getLng());
    }


    public String getType() {
        return type;
    }
}

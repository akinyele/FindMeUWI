package com.android.comp3901.findmeuwi.services;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;

import java.util.LinkedList;

/**
 * Created by Kyzer on 5/12/2017.
 * this class does the managing of the polylines used to diplay the grapth and the paths on the map
 *
 */

public class MapPolylines {

    LinkedList<Polyline> floorEdges;
    LinkedList<Polyline> secondFloorEdges;
    LinkedList<Polyline> thirdFloorEdges;

    GoogleMap googleMap;


    public MapPolylines(GoogleMap map){

        this.googleMap = map;


    }


}

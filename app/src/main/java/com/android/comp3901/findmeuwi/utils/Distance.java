package com.android.comp3901.findmeuwi.utils;

import android.location.Location;
import android.util.Log;

import com.android.comp3901.findmeuwi.locations.Vertex;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.android.comp3901.findmeuwi.ui.mapFragment.MapFrag.path;

/**
 * Created by Kyzer on 3/14/2017.
 * This class will provide all the calculation in regards to the distances between points
 */




public class Distance {

    public static Double R = 6371.00; // radius of earth in km


    public Distance(){}




    /**
     *   Find the total distance of a route
     */
    public static Double routeDistance(LinkedList<Vertex> route){
        double dist = 0.00;

        for(int i=0; i < route.size()-1 ; i++ ){

            Vertex current = route.get(i);
            Vertex next = route.get(i+1);


            dist = dist + find_distance(current.getLL(), next.getLL());
        }

        return dist;
    }


    /**
     * Uses Harversine's formula to calculate the distance between two LatLng
     * @param myLL the Lat long provided by your location
     * @param markerLL
     * @return returns the distance d in Km
     */
    public static Double find_distance(LatLng myLL, LatLng markerLL){

        double myLat = myLL.latitude;
        double myLng = myLL.longitude;

        double mlat = markerLL.latitude;
        double mlng = markerLL.longitude;



        Double dLat = rad(mlat - myLat);
        Double dLong = rad(mlng - myLng);
        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(rad(myLat)) * Math.cos(rad(myLat)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = R * c;

        Log.d("Distance  ",""+d);
        return d;
    }


    public static Vertex find_closest_marker(Location location) {

        LatLng LL = new LatLng( location.getLatitude(), location.getLongitude());
         Vertex start;

        ArrayList<Double> distances = new ArrayList();

        List<Vertex> points = path.getCNodes(); // uses nodes that are connected to an edge

        Integer closest = -1;

        for (int i = 0; i < points.size(); i++) {

            //Marker points Lat & Lng
            LatLng markerLL = points.get(i).getLL();


            double d = find_distance(LL,markerLL);
            distances.add(d);

            if (closest == -1 || d < distances.get(closest)) {
                closest = i;
            }
        }

        start = points.get(closest);
        return start;
    }

    public static Vertex find_closest_marker(LatLng location) {

        LatLng LL = location;

        Vertex start;
        ArrayList<Double> distances = new ArrayList();

        List<Vertex> points = path.getCNodes(); // uses nodes that are connected to an edge

        Integer closest = -1;

        for (int i = 0; i < points.size(); i++) {

            //Marker points Lat & Lng
            LatLng markerLL = points.get(i).getLL();


            double d = find_distance(LL,markerLL);
            distances.add(d);

            if (closest == -1 || d < distances.get(closest)) {
                closest = i;
            }
        }

        start = points.get(closest);
        return start;
    }




    private static double rad(double x) {
        return x * Math.PI / 180;
    }



}// End of distance Class

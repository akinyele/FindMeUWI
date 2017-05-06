package com.android.comp3901.findmeuwi;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;

/**
 * Created by Kyzer on 3/20/2017.
 *
 * This class is used to manage the markers on the map. It handle the creations and displaying of the map markers.
 * It holds the different types of markers used by the application.
 *
 *
 */

public class MapMarker {



    GoogleMap mGoogleMap;
    public static MapMarker instance;


    public static Marker startMarker, destMarker, marker;
    LinkedList<Marker> markers;




    private MapMarker(GoogleMap map){
        mGoogleMap = map;

    }

    public static MapMarker getInstance(){

            instance = new MapMarker(FindMe.mGoogleMap);

        return  instance;
    }


    public void addMarker(LatLng ll, String title, String snip, Integer type) {


        if (type == 1) {
            if (startMarker != null)
                startMarker.remove();

            MarkerOptions option = new MarkerOptions()
                    .title(title)
                    .snippet(snip)
                    //.icon(BitmapDescriptorFactory.fromResource(R.))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .position(ll);

            startMarker = mGoogleMap.addMarker(option);

        } else if (type == 2) {
            if (destMarker != null)
                destMarker.remove();

            MarkerOptions option = new MarkerOptions()
                    .title(title)
                    .snippet(snip)
                    //.icon(BitmapDescriptorFactory.fromResource(R.))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(ll);

            destMarker = mGoogleMap.addMarker(option);
            } else {}
    }

    public void addIcon(LatLng ll,String title, String snip, String type){
        //MarkerOptions node;
        switch (type){

            case "Building":
                MarkerOptions building;
                building = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.mipmap.building)).title(title).snippet(snip);
                marker = mGoogleMap.addMarker(building);
                break;
            case "Junction":
                MarkerOptions junction;
                junction = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.mipmap.junction)).title(title).snippet(snip);
                marker = mGoogleMap.addMarker(junction);
                break;
            case "Stairs":
                MarkerOptions stairs;
                stairs = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.mipmap.stair)).title(title).snippet(snip);
                marker = mGoogleMap.addMarker(stairs);
                break;
            default:
                break;
        }
    }






}//End of Marker Class

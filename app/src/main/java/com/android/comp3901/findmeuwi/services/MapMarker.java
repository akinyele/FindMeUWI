package com.android.comp3901.findmeuwi.services;

import android.util.Log;

import com.android.comp3901.findmeuwi.ui.mapFragment.mapFragment;
import com.android.comp3901.findmeuwi.locations.Vertex;
import com.android.comp3901.findmeuwi.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by Kyzer on 3/20/2017.
 *
 * This class is used to manage the markers on the map. It handle the creations and displaying of the map markers.
 * It holds the different types of markers used by the application.
 *
 *
 */

public class MapMarker {

    private static final String TAG = "com.android.comp3901";


    GoogleMap mGoogleMap;
    public static MapMarker instance;


    public static Marker startMarker, destMarker, marker;
     LinkedList<Marker> stairs_markers ;
     LinkedList<Marker> building_markers ;
     LinkedList<Marker> junction_markers ;
     LinkedList<Marker> knownMarkers;
     LinkedList<Marker> landMarkers;

    Set<LatLng> latLngs;// keeps a track of off the latlng that are already on the map.




    private MapMarker(GoogleMap map){
        mGoogleMap = map;
        this.stairs_markers = new LinkedList<>();
        this.building_markers = new LinkedList<>();
        this.junction_markers = new LinkedList<>();
        this.knownMarkers = new LinkedList<>();
        this.landMarkers = new LinkedList<>();
        this.latLngs = new HashSet<>();
    }

    public static MapMarker getInstance(){
            instance = new MapMarker(mapFragment.mGoogleMap);
        return  instance;
    }


    /**
     *
     * @param vertex
     * @param type deteremins what type of marker is being created.
     */
    public void addMarker(Vertex vertex, Integer type) {
        LatLng ll = vertex.getLL();
        String title = vertex.getName();
        String snip = vertex.getType();
        MarkerOptions option;

        switch (type){
            case 1:
                if (startMarker != null)
                    startMarker.remove();

                option= new MarkerOptions()
                        .title(title)
                        .snippet(snip)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start_marker_red_round))
                        .position(ll);
                startMarker = mGoogleMap.addMarker(option);
                startMarker.setTag(vertex);
                break;
            case 2:
                if (destMarker != null)
                destMarker.remove();
                option = new MarkerOptions()
                        .title(title)
                        .snippet(snip)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.dest_marker_blue))
                        .position(ll);
                destMarker = mGoogleMap.addMarker(option);
                destMarker.setTag(vertex);
                break;
            case 3:
                try {
                    Log.d(TAG, "addMarker: " + ll + " " + startMarker.getPosition());
                    if (ll.equals(destMarker.getPosition()) || ll.equals(startMarker.getPosition())) {
                        break;
                    }
                }catch (NullPointerException e){}

                option = new MarkerOptions()
                        .position(vertex.getLL())
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.poi))
                        .snippet("POI")
                        .title(vertex.getName());
                marker = mGoogleMap.addMarker(option);
                marker.setTag(vertex);

                knownMarkers.add(marker);
                break;
            default:
                break;
        }
        if (type == 1) {//Start marker


        } else if (type == 2) {//end marker

        } else {}



    }

    public void addIcon(Vertex node){

        LatLng ll = node.getLL();
        String title = node.getName();
        String type = node.getType();
        String snip = "Snip";

        MarkerOptions options;
        options = new MarkerOptions().position(ll).title(title).snippet(snip);

        switch (type.replaceAll("\\s","").toLowerCase()){

            case "building":
                options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.building));
                marker = mGoogleMap.addMarker(options);
                marker.setTag(node);
                if(node.isLandmark()){landMarkers.add(marker);}
                building_markers.add(marker);
                break;
            case "junction":
                MarkerOptions junction;
                junction = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.mipmap.junction)).title(title).snippet(snip).visible(false);
                marker = mGoogleMap.addMarker(junction);
                junction_markers.add(marker);
                break;
            case "stairs":
                MarkerOptions stairs;
                stairs = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.stair)).title(title).snippet("stairs").visible(false);
                marker = mGoogleMap.addMarker(stairs);
                stairs_markers.add(marker);
                break;
            case "place":
                MarkerOptions place;
                place = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_of_interest)).title(title).snippet("Landmark").visible(false);
                marker = mGoogleMap.addMarker(place);
                marker.setTag(node);
                landMarkers.add(marker);
                break;

            default:
                break;
        }
    }



    //TODO No longer need this method
    public void addPointOfInterestMarker(Vertex knownPoint){

        MarkerOptions known_place_options;
        known_place_options = new MarkerOptions()
                .position(knownPoint.getLL())
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.chat_bubble))
                .snippet("POI")
                .title(knownPoint.getName());
        marker.setTag(knownPoint);
        marker = mGoogleMap.addMarker(known_place_options);

        knownMarkers.add(marker);


    }





    public  void showStairs(boolean b){
        for ( Marker stair: stairs_markers) {
            stair.setVisible(b);
        }
    }


    public void showBuildings(boolean b) {

        for ( Marker building: building_markers) {
            building.setVisible(b);
        }
    }

    public void showJunctions(boolean b) {
        for ( Marker junction: junction_markers) {
            junction.setVisible(b);
        }
    }


    public void showLandmarks(Boolean b){
        for( Marker landmarks: landMarkers){
            landmarks.setVisible(b);
        }
    }


    public void removePOI(){

        if(!(knownMarkers.size() <1))
        {
             for (Marker marker: knownMarkers) {
                marker.remove();
                knownMarkers=new LinkedList<>();
            }
        }
    }


    public void removeStart() {
        try{
            startMarker.remove();
        }catch (NullPointerException e){

        }
    }
}//End of Marker Class

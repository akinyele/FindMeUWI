package com.android.comp3901.findmeuwi.services;

import android.util.Log;

import com.android.comp3901.findmeuwi.R;
import com.android.comp3901.findmeuwi.activities.FindMe;
import com.android.comp3901.findmeuwi.locations.Vertex;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kyzer on 5/12/2017.
 * this class does the managing of the polylines used to diplay the grapth and the paths on the map
 *
 */

public class MapPolylines {
    private static final String TAG = "com.android.comp3901";

    LinkedList<Polyline> floorEdges;
    LinkedList<Polyline> secondFloorEdges;
    LinkedList<Polyline> thirdFloorEdges;
    LinkedList<Polyline> descendingEdges;




    GoogleMap googleMap;


    public MapPolylines(GoogleMap map) {

        this.googleMap = map;
        this.floorEdges = new LinkedList<>();
        this.secondFloorEdges = new LinkedList<>();
        this.thirdFloorEdges = new LinkedList<>();
        this.descendingEdges = new LinkedList<>();

    }


    public void createGraph(){

        List<Edge> edges = FindMe.path.getEdges();
        List<Vertex> vertices = FindMe.path.getNodes();

        HashMap<String, Vertex> vertexHashMap = FindMe.path.getVertices();
        Polyline lane;
        Vertex v1,v2;


        for(Edge edge: edges){


            v1 = vertexHashMap.get(edge.getSource().getId());
            v2 = vertexHashMap.get(edge.getDestination().getId());



            //TODO make polyline clickable
            PolylineOptions options = new PolylineOptions()
                    .color(0x33606060)
                    .width(15)
                    .zIndex(01)
                    .clickable(true)
                    .add(v1.getLL(),v2.getLL());

            lane =  googleMap.addPolyline(options);
            lane.setClickable(true);

            Log.d(TAG, "createGraph: edge level : "+ edge.getLevel());

            //TODO fix the colors schemes for the polylines
            switch (edge.getLevel()){

                case 0:
                    lane.setColor(R.color.groundFloorColor);
                    lane.setVisible(false);

                    descendingEdges.add(lane);
                    break;
                case 1:
                    lane.setColor(0x33606060);
                    floorEdges.add(lane);
                    break;
                case 2:
                    lane.setColor(R.color.secondFloorColor);
                    lane.setVisible(false);
                    secondFloorEdges.add(lane);
                    break;
                case 3:
                    lane.setColor(R.color.colorAccent);
                    lane.setVisible(false);
                    thirdFloorEdges.add(lane);
                    break;
                default:
                    Log.d(TAG, "createGraph: NO LEVEL");
                    break;
            }
        }
    }

    public void createPath(){

    }

    public void showAllLevels(){

    }


    public void showLevelTwo(){

    }







}

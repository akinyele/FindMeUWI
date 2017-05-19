package com.android.comp3901.findmeuwi.services;

import android.graphics.Color;
import android.util.Log;

import com.android.comp3901.findmeuwi.R;
import com.android.comp3901.findmeuwi.activities.FindMe;
import com.android.comp3901.findmeuwi.locations.Vertex;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.android.comp3901.findmeuwi.activities.FindMe.mGoogleMap;

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
    LinkedList<Polyline> currPath = null;


    public MapPolylines(GoogleMap map) {

        this.googleMap = map;
        this.floorEdges = new LinkedList<>();
        this.secondFloorEdges = new LinkedList<>();
        this.thirdFloorEdges = new LinkedList<>();
        this.descendingEdges = new LinkedList<>();
        this.currPath = new LinkedList<>();

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
                    lane.setColor(Color.MAGENTA);
                   lane.setVisible(false);

                    descendingEdges.add(lane);
                    break;
                case 1:
                    lane.setColor(0x33606060);//
                    floorEdges.add(lane);
                    break;
                case 2:
                    lane.setColor(Color.YELLOW);
                  lane.setVisible(false);
                    secondFloorEdges.add(lane);
                    break;
                case 3:
                    lane.setColor(Color.CYAN);
                    lane.setVisible(false);
                    thirdFloorEdges.add(lane);
                    break;
                default:
                    Log.d(TAG, "createGraph: NO LEVEL");
                    break;
            }
        }
    }

    public void createPath(LinkedList<Vertex> route){

          if(!currPath.isEmpty())
              deletePath();

            route.size();
            LinkedList<LatLng> pnts = new LinkedList<>();

            for( int i = 0; i<route.size()-1; i++ ){
                LatLng  ll =  route.get(i).getLL();
                LatLng  ll2 =  route.get(i+1).getLL();
                PolylineOptions  options = new PolylineOptions()
                .width(5)
                .add(ll, ll2 )
                .zIndex(02);

                if(route.get(i).getLevel()==3){
                    options.color(Color.MAGENTA);
                }else if(route.get(i).getLevel()==2){
                    options.color(Color.YELLOW);
                }else if(route.get(i).getLevel()==0){
                    options.color(Color.BLUE);
                    }else {
                        options.color(Color.GREEN);
                    }

                    Polyline line = mGoogleMap.addPolyline(options);
                    currPath.add(line);
                }
        }

        public void showAllLevels(){

        }


    public void showLevelTwo(){

    }


    public void deletePath() {
        for (Polyline line:
             currPath) {
            line.remove();
        }
        currPath.clear();
    }
}

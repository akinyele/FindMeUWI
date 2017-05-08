package com.android.comp3901.findmeuwi;

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.os.SystemClock.uptimeMillis;


/**
 * Created on 5/5/2017.
 * Class is used to give the tracks the user and present relevant info on the journey
 *
 */

public class Tracker {
    Activity instance;
    Handler handler;
    MapMarker mapMarker;
    public Boolean arrived;

    ArrayList<Vertex> knownNodes;






    public Tracker(Activity instance){

        this.knownNodes =  this.getKnownNodes();
        this.instance = instance;
        this.mapMarker = MapMarker.getInstance();
        this.arrived = false;

    }

    public Boolean hasArrived() {
        return arrived;
    }

    /**
     * This method is used when the user is not tracking by location
     * it start a timer that when finish prompt the user and ask them
     * if the have arrived.
     * Based of the response it does the next required task.
     */
    public void startArrivalTimer(){

        handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                createDialog();
            }
        };

        handler.postDelayed(r, 10000);
    }



    public void locationTracking(Location location){

        LatLng  myLL = new LatLng(location.getLatitude(),location.getLongitude());
        LatLng dest = new LatLng( FindMe.destination.getLat(), FindMe.destination.getLng());

        Double closeDistance= 0.0014;
        Double distance_to_dest = Distance.find_distance(myLL,dest);


        if( distance_to_dest < closeDistance ){
            //TODO create arrival dialog and increment familiarity
            Toast.makeText(instance,"YOU HAVE ARRIVED AT YOUR DESTINATION", Toast.LENGTH_SHORT);
            arrived = true;




            return;
        }else{
            //make variable local
            for ( Vertex node : knownNodes) {

                if(Distance.find_distance(myLL,node.getLL())<closeDistance){
                    //TODO Pop up the know place

                    mapMarker.addKnowMarker(node);
                    return;
                }
            }

        }

    }

    private ArrayList<Vertex> getKnownNodes() {


        ArrayList<Vertex> nodes = new ArrayList<>();
        HashMap<String,Vertex> allnodes = Path.vertices;
        Boolean known;

        Iterator iter = allnodes.keySet().iterator();


        while (iter.hasNext()){

           String key = (String) iter.next();

            Vertex v = allnodes.get(key);

            if( v instanceof Room ){
                 known = ((Room) v).isKnown();
            }else {
                known = false;
            }

            if(known){
                nodes.add(v);
            }
        }

        return nodes;
    }





    /***
     * Creates Dialog box that gets displayed to user, asking them if they have arrived.
     */
     private void createDialog(){
         AlertDialog.Builder arrivalDialog = new AlertDialog.Builder(instance);
         View mView = instance.getLayoutInflater().inflate(R.layout.arrival_query_dialog, null);

         arrivalDialog.setView(mView);
         final AlertDialog dialog = arrivalDialog.create();
         dialog.show();

         Button yesBtn = (Button) mView.findViewById(R.id.arrival_dialog_yesBtn);
         yesBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(instance.getApplicationContext(),"Arrived at"+FindMe.destination.getName() ,Toast.LENGTH_SHORT).show();

                     // TODO: Check which class instance the destination is and increment the familiarity
                     if( FindMe.destination instanceof Room){

                         Log.d("ROOM TYPE  ", "TRUE");
                         ((Room) FindMe.destination).updateFamiliarity(1.0);
                         ((Room) FindMe.destination).updateDB();
                         Log.d("NEW Familiarity  ", ""+((Room) FindMe.destination).getFamiliarity());
                     }
                     dialog.dismiss();
             }
         });

         Button noBtn = (Button) mView.findViewById(R.id.arrival_dialog_noBtn);
         noBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(instance.getApplicationContext(),"Not Arrived",Toast.LENGTH_SHORT).show();

                 dialog.dismiss();

                 //TODO: restart the timer
             }
         });



        }













}

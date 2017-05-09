package com.android.comp3901.findmeuwi;

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;


/**
 * Created on 5/5/2017.
 * Class is used to give the tracks the user and present relevant info on the journey
 *
 */

public class Tracker {
    Activity instance;
    Handler handler;
    MapMarker mapMarker;
    Learner learner;
    public Boolean arrived;

    Snackbar directionsSnackbar;

    ArrayList<Vertex> knownNodes;






    public Tracker(Activity instance){

        this.knownNodes =  this.getPointsOfInterest();
        this.instance = instance;
        this.mapMarker = MapMarker.getInstance();
        this.arrived = false;
        this.learner = new Learner(instance);
        directionsSnackbar = Snackbar.make(instance.findViewById(R.id.app_bar_main), " ", Snackbar.LENGTH_LONG );


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


    /***
     * This mehthod is used to keep track of the user and pop of known
     * place the they are close to.
     * @param location Current Location that is given by the user.
     */
    public void locationTracking(Location location){

        if(Path.currPath == null){return;}


        LatLng  myLL = new LatLng(location.getLatitude(),location.getLongitude());
        LatLng dest = new LatLng( FindMe.destination.getLat(), FindMe.destination.getLng());

        Double closeDistance= 0.0014;//in Km
        Double distance_to_dest = Distance.find_distance(myLL,dest);


        if( distance_to_dest <= closeDistance ){ //Check if user have arrived
            //TODO create arrival dialog and increment familiarity clear path and land marks on arrival
            Toast.makeText(instance,"YOU HAVE ARRIVED AT YOUR DESTINATION", Toast.LENGTH_SHORT);
            arrived = true;



            return;
        }else{// checks for known point and landMarks along the way

            //make variable local
            for ( Vertex node : knownNodes) {

                if(Distance.find_distance(myLL,node.getLL())<closeDistance){

                //TODO Pop up the known places
                    knownNodes.remove(node);

                    String nodeType = node.getType().replaceAll("\\s","").toLowerCase();

                    //only pop up know rooms and land marks since building are always shown;
                    if(!nodeType.equals("building"))
                        mapMarker.addPointOfInterestMarker(node);

                //TODO show a snack bar showing the tell what just popped up and where it is relative to you


                    Directions.getDirection(node);

                    String message = node.getName()+" is on your ";
                    directionsSnackbar.setText(message);
                    directionsSnackbar.show();



                    return;
                }
            }

        }

    }

    /**
     * Gets the Landmarks and known nodes that will be pop during a tracking phase.
     *
     * @return the arrayList
     */
    private ArrayList<Vertex> getPointsOfInterest() {


        ArrayList<Vertex> nodes = new ArrayList<>();
        HashMap<String,Vertex> allnodes = Path.vertices;
        Boolean known;

        Iterator iter = allnodes.keySet().iterator();


        while (iter.hasNext()){

           String key = (String) iter.next();

            Vertex v = allnodes.get(key);


            if( v instanceof Place ){
                 known = ((Place) v).isKnown();
            }else if( v.isLandmark() ){
                known =true;
            } else {
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
                 dialog.dismiss();

                 // TODO: Check which class instance the destination is and increment the familiarity
                     if( FindMe.destination instanceof Place){

                         Log.d("ROOM TYPE  ", "TRUE");
                         learner.learner1(FindMe.destination);
                         ((Room) FindMe.destination).updateDB();
                         Log.d("NEW Familiarity  ", ""+((Room) FindMe.destination).getFamiliarity());
                     }



                 //TODO toast message
                 onArrival(FindMe.destination);
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

    private void onArrival(Vertex node) {


        String message = "You have arrived at " + node.getName();
        directionsSnackbar.setText(message);
        directionsSnackbar.show();
      }


}

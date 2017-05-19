package com.android.comp3901.findmeuwi.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.comp3901.findmeuwi.utils.Directions;
import com.android.comp3901.findmeuwi.utils.Distance;
import com.android.comp3901.findmeuwi.activities.FindMe;
import com.android.comp3901.findmeuwi.locations.Place;
import com.android.comp3901.findmeuwi.locations.Room;
import com.android.comp3901.findmeuwi.locations.Vertex;
import com.android.comp3901.findmeuwi.R;
import com.android.comp3901.findmeuwi.utils.Path;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.nearby.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created on 5/5/2017.
 * Class is used to give the tracks the user and present relevant info on the journey.
 * this is self contained and done withing a loop.
 *
 */

public class Tracker extends Thread {
    private static final String TAG = "com.android.comp3901";

    Activity instance;
    Handler handler;
    MapMarker mapMarker;
    Learner learner;
    private Runnable my_runnable;
    boolean arrival_timer_sent = false;



    Message msg;




    private Snackbar directionsSnackbar;
    private Path path;

    ArrayList<Vertex> knownNodes;

    //State Variable
    private Boolean hasArrived;


    //Finals
    public final float min_accurracy_error = 15;
    private final Double closeDistance= 0.0014;//in Km

    public Tracker(Activity instance){

        this.path = FindMe.path;
        this.instance = instance;
        this.knownNodes =  this.getPointsOfInterest();
        this.mapMarker = MapMarker.getInstance();
        this.learner = new Learner(instance);
        directionsSnackbar = Snackbar.make(instance.findViewById(R.id.app_bar_main), " ", Snackbar.LENGTH_LONG );
    }


    public void startTracker() throws InterruptedException {

            sleep(2000);

            Log.d(TAG, "startTracker: Thread Running");
            boolean pathExist = (path.getCurrPath() != null);
            if(!pathExist){
                synchronized (Path.lock) {
                    Log.d(TAG, "startTracker: No path, Thread paused");
                    Path.lock.wait();
                    Log.d(TAG, "startTracker: Thread Resumed");
                }
            }


            if(!FindMe.location_service_enabled && !arrival_timer_sent){
                arrival_timer_sent = true;
                FindMe.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ui thread");
                        startArrivalTimer();//Send arrival timer to main thread
                    }
                });
            }
          else{
                Location  location = FindMe.my_location;
                if ( !(location.getAccuracy()>min_accurracy_error ||  location.hasAccuracy() )){
                    Log.d(" Tracker: ", "Not Accurate");
                    Toast.makeText(instance, "Cant get accurate location", Toast.LENGTH_SHORT);
                }else {
                    locationTracking();
                }
            }
  }



    /**
     * This method is used when the user is not tracking by location
     * it starts a timer that when finished prompts the user and ask them
     * if the have arrived.
     * Based of the response it does the next required task.
     * NOTE that this method is ran with the the UI thread because it needs a
     * handle and it creates a dialog;
     */
    public void startArrivalTimer(){
        handler = new Handler();
        my_runnable = new Runnable() {
            @Override
            public void run() {
                if(!FindMe.location_service_enabled){
                    createDialog();
                }
            }
        };
        handler.postDelayed(my_runnable, 10000);

    }


    /***
     * This method is used to keep track of the user and pop of known
     * place the they are close to.
     */
    public void locationTracking(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Location  location = FindMe.my_location;
        Log.d(TAG , "tracking location :" + String.valueOf(location) );
        //if(Path.currPath == null){return;}


        LatLng  myLL = new LatLng(location.getLatitude(),location.getLongitude());
        LatLng dest = new LatLng( FindMe.destination.getLat(), FindMe.destination.getLng());


        Double distance_to_dest = Distance.find_distance(myLL,dest);

        Log.d(TAG, "Distance to destination" + distance_to_dest);

        if( distance_to_dest <= closeDistance ){ //Check if user have arrived
            //TODO create arrival dialog and increment familiarity clear path and land marks on arrival
            Toast.makeText(instance,"YOU HAVE ARRIVED AT YOUR DESTINATION", Toast.LENGTH_SHORT);
            hasArrived = true;
            onArrival(FindMe.destination);

        }else{// checks for known point and landMarks along the way

            //make variable local
            for ( Vertex node : knownNodes) {

                if(Distance.find_distance(myLL,node.getLL())<closeDistance){

                    //TODO Pop up the known places
                    knownNodes.remove(node);

                    String nodeType = node.getType().replaceAll("\\s","").toLowerCase();

                    //only pop up know rooms and land marks since building are always shown;
                       if(!nodeType.equals("building"))
                            //mapMarker.addPointOfInterestMarker(node);

                //TODO show a snack bar showing the tell what just popped up and where it is relative to you

                    Directions.getDirection(node);

                    String message = node.getName()+" is on your ";
                    directionsSnackbar.setText(message);
                    directionsSnackbar.show();


                }
            }
        }
    }








    /**
     * Gets the Landmarks and known nodes that will be pop during a tracking phase.
     *
     * @return the arrayList
     * //TODO delete method,(carried it over to findME)
     */
    private ArrayList<Vertex> getPointsOfInterest() {


        ArrayList<Vertex> nodes = new ArrayList<>();
        HashMap<String,Vertex> all_nodes = path.getVertices();
        Boolean known;

        Iterator iter = all_nodes.keySet().iterator();


        while (iter.hasNext()){

           String key = (String) iter.next();

            Vertex v = all_nodes.get(key);


            if( v instanceof Place){
                 known = ((Place) v).isKnown();
            }else if( v.isLandmark() ){
                known =true;
            }else if(v instanceof Room){
                known = ((Room) v).isKnown();
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

        if(!(instance).isFinishing())
        {
            //show dialog
            dialog.show();
        }

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
                         ((Place) FindMe.destination).updateDB();
                         Log.d("NEW Familiarity  ", ""+((Place) FindMe.destination).getFamiliarity());
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
                arrival_timer_sent = false;
                dialog.dismiss();
                //TODO: restart the timer
             }
         });
    }



    private void onArrival(Vertex node) {

        //RESET FLAGS NEEDED IN THE LOOP
        hasArrived = true;
        arrival_timer_sent = false;
        FindMe.trackerHandler.sendEmptyMessage(0);

        //TODO Clear Path show pop up image of destination;

        //Path.clearCurPath();



        Toast.makeText(instance, "You have arrived at " + node.getName(),Toast.LENGTH_SHORT);

      }



//      public void cancelHandler(){
//
//          if(handler!=null){
//              handler.removeCallbacks(my_runnable);
//          }
//         return;
//      }




    @Override
    public void run(){
        while(true){
            try {
                startTracker();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





}

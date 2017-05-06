package com.android.comp3901.findmeuwi;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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






    public Tracker(Activity instance){

        this.instance = instance;

    }



    /*
     *This method is used when the user is not tracking by location
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

        handler.postDelayed(r, 60000);
    }





     private void createDialog(){
         AlertDialog.Builder arrivalDialog = new AlertDialog.Builder(instance);
         View mView = instance.getLayoutInflater().inflate(R.layout.arrival_query_dialog, null);
         Toast.makeText(instance,"TIMER",Toast.LENGTH_SHORT);



         arrivalDialog.setView(mView);
         AlertDialog dialog = arrivalDialog.create();

         Button yesBtn = (Button) mView.findViewById(R.id.arrival_dialog_yesBtn);
         yesBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(instance.getApplicationContext(),"Arrived at"+FindMe.destination.getName() ,Toast.LENGTH_SHORT).show();


                 if( FindMe.destination instanceof Room){

                     //(Room)FindMe.destination

                 }




             }
         });

         Button noBtn = (Button) mView.findViewById(R.id.arrival_dialog_noBtn);
         noBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(instance.getApplicationContext(),"Not Arrived",Toast.LENGTH_SHORT).show();
             }
         });



         dialog.show();

     }











}

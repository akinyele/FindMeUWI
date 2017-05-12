package com.android.comp3901.findmeuwi.services;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.android.comp3901.findmeuwi.locations.Place;
import com.android.comp3901.findmeuwi.locations.Vertex;
import com.android.comp3901.findmeuwi.R;

/**
 * Created by Kyzer on 5/6/2017.
 *
 * This class is intended to manage the learning aspect of the vertices.
 * Its main focus is to increment to learning bits of each class based on
 * given criteria.
 *
 */

public class Learner {

    Snackbar knownValidotorSnackBar;
    Activity instance;

    public Learner(Activity instance){
        this.instance = instance;
    }






    public void learner1(Vertex node){

        if( node instanceof Place){
            Place place = ((Place) node);

            if(place.isKnown()){
                return;
            }else if(place.getFamiliarity() >= 5.0 ){
                isKnownValidation(place);
            }else {
                place.updateFamiliarity(1.0);
            }



        }else{
            Log.d("Learner: ", "Not Place");
            return;
        }

    }

    private void isKnownValidation(final Place node) {

        knownValidotorSnackBar = Snackbar.make(instance.findViewById(R.id.app_bar_main),"Is "+ node.getName()+" somewhere you know? ",Snackbar.LENGTH_INDEFINITE);
        knownValidotorSnackBar.setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                node.setKnown(1);
            }
        });

        //TODO update database after setting known bit.


        //knownValidotorSnackBar.setDuration();
        knownValidotorSnackBar.show();
    }


    public void setKnown(Place place){
        place.setKnown(1);
    }




}

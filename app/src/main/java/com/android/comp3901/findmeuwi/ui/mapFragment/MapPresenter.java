package com.android.comp3901.findmeuwi.ui.mapFragment;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.widget.ImageView;

import com.android.comp3901.findmeuwi.data.AppDbHelper;
import com.android.comp3901.findmeuwi.data.DbHelper;
import com.android.comp3901.findmeuwi.locations.Place;
import com.android.comp3901.findmeuwi.locations.Room;
import com.android.comp3901.findmeuwi.locations.Vertex;
import com.android.comp3901.findmeuwi.service.MyLocationService;
import com.android.comp3901.findmeuwi.service.MapMarker;
import com.android.comp3901.findmeuwi.service.MapPolylines;
import com.android.comp3901.findmeuwi.utils.Distance;
import com.android.comp3901.findmeuwi.utils.Path;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Kyzer on 5/24/2017.
 */

public class MapPresenter implements MapFragMvpPresenter {
    private static final String TAG = "Presenter TAG: ";
    final LatLng sci_tech = new LatLng(18.005072, -76.749544);


    private final Path path;
    private final MyLocationService location;
    private MapFragMvPView view;
    public static DbHelper dbHelper;
    private boolean location_service_enabled;
    private boolean isSourceSet;
    private Vertex start,destination;

    private static MapMarker mapMarkers;
    private MapPolylines mapPolylines;
    private LinkedList<Vertex> route;
    private GoogleMap mGoogleMap;


    public MapPresenter(MapFragMvPView view, Context ctx, GoogleMap mGoogleMap, GoogleApiClient mGoogleApiClient) {
        this.view = view;
        this.dbHelper = AppDbHelper.getInstance(ctx);
        this.path = new Path(dbHelper);
        this.location = MyLocationService.getInstance(ctx);
        this.mapMarkers = MapMarker.getInstance(mGoogleMap);
        this.mapPolylines = new MapPolylines(mGoogleMap);
        this.mGoogleMap = mGoogleMap;
}

    public void geoLocate() throws IOException {
        String cls = view.getDestText();
        Cursor res = dbHelper.findLocation(cls);
        setVertex(res, 2);
    }

    @Override
    public void getPath() {
//         location_service_enabled = ((ToggleButton) getView().findViewById(R.id.locationToggle)).isChecked(); //Finds out if user want to use their their location.
           //handles the start location configuration
        if (location_service_enabled && !isSourceSet) {
            //TODO Check location accuracy before using the point;
            LatLng myLL;
            Location my_location = location.getLocation();
            if(my_location!=null){
                myLL = new LatLng (my_location.getLatitude(), my_location.getLongitude());
            }else{
                myLL = new LatLng(my_location.getLatitude(),my_location.getLongitude());
            }

            start = Distance.find_closest_marker(myLL, path.getCNodes());
            isSourceSet = true;
        }else if(isSourceSet){

        }else{
            isSourceSet = setSource();
        }

        if (!isSourceSet) {
            // ask them to select a starting point
            view.makeToast("NO Source");
            return;
        } else if (destination == null) {
            view.makeToast("No Destination selected");
            return;
        } else {
            try {
                route = path.getPath(start.getId(), destination.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(route==null){
                view.makeToast("Could not find path from "+ start.getName()+ " to " + destination.getName() + " found.");
                return;
            }else {
                view.makeToast("Path to " + destination.getName() + " found.");
            }
        }

        mapPolylines.createPath(route);
        getPOI();
    }

    public boolean setSource() {
        if(location_service_enabled){return false;}

//        String startText = getSourceView.getText().toString();
        String startText = view.getStartText();
        Cursor res = dbHelper.findLocation(startText);
        return setVertex(res, 1);
    }

    public boolean setVertex(Cursor data, int type) {
        //TODO change method to just take the id and type;
        if (data.getCount() == 0) {
            // show message "no results found in places database"
            view.makeToast("Could not find ");
            return false;
        } else if (data.getCount() > 1) {
            //TODO more than one possible starting point found found. Create method to let them choose

            view.makeToast("Please select one these Rooms to start from.");
            return false;
        } else {
            switch (type) {
                case 1:
                    // create the starting node
                    start = path.getVertices().get(data.getString(0));
                    mapMarkers.addMarker(start, type);
                    view.goToLocation(start.getLL());
                    break;
                case 2:
                    //TODO allow destination to be building as well;
                    destination = path.getVertices().get(data.getString(0)); //takes the result and uses the id to find the location
                    mapMarkers.addMarker(destination, type);

                    view.makeToast(destination.getName() + " is here");
                    view.goToLocation(destination.getLL());
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private void getPOI(){
        LinkedList<Vertex> currPath = path.getCurrPath();
        ArrayList<Vertex> POI = getPointsOfInterest();
        for(int i = 0; i<currPath.size()-1; i++){
            LatLng midPoint  = SphericalUtil.interpolate(currPath.get(i).getLL(),currPath.get(i).getLL(), 0.5 );
            for( int n = 0 ; n< POI.size() ; n++){
                if( SphericalUtil.computeDistanceBetween(midPoint,POI.get(n).getLL()) < 15.0){
                    //closePoints.add(POI.remove(n));
                    Log.d(TAG, "getPOI: distance midPoint-POI.get(n).getLL() " +  SphericalUtil.computeDistanceBetween(midPoint,POI.get(n).getLL()));
                    mapMarkers.addMarker( POI.remove(n), 3);
                }
            }
        }
    }
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

    @Override
    public boolean toggleLocation(boolean checked) {
        location_service_enabled = !location_service_enabled;

        if (checked) {
            if ( location.isGPSEnabled() ) {
                //provide some method that Uses user Location as starting point.
                view.locationEnabled(true);
                view.makeToast("Getting Your Location");
                // Disable the text field when he user has MyLocationService connected
                location_service_enabled = true;
                return location_service_enabled;
            } else {
                view.makeToast("Please turn Locations on");
                view.setChecked(false);
                return false;
            }
        } else {
            if (location != null){ location.disconnect();}
            view.locationEnabled(false);
            view.goToLocation(sci_tech);
            view.disableSourceText(false);
            location_service_enabled = false;
            return location_service_enabled;
        }
    }

    @Override
    public void setPic(ImageView mImageView, String filepath){
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filepath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    /** Data Retrieval **/


    @Override
    public ArrayList<String> getRoomsFromDb() {
        return  dbHelper.roomList();
    }

    @Override
    public Collection<? extends String> getBuildingsFromDb() {
        return dbHelper.buildingList();
    }

}

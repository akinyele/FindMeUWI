package com.android.comp3901.findmeuwi;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.support.v4.app.FragmentActivity;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FindMe extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    Marker marker;
    DB_Helper dbHelper;
    Room destination, start, known;
    UiSettings mUiSettings;


    public void createRooms() throws IOException {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_me);
        dbHelper = new DB_Helper(this); //Creating databases
        dbHelper.generateDB();

        try {
            dbHelper.writeToSD(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (googleServicesCheck()) {
            Toast.makeText(this, "Perfect!!", Toast.LENGTH_LONG).show();
            initMap();
            try {
                createRooms();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    /*
    ** Initializes Map Fragment;
    **/
    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    /*
    ** Checks to see if the User has play services available.
    **/
    public boolean googleServicesCheck() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }


    /*
     * Function that tells the map what to do when its ready
     *
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();

         goToLocation(18.005072, -76.749544);
    }

    /*
     *  Method used to jump to location.
     */
    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 18);
        mGoogleMap.moveCamera(update);
    }

    /*
     *   This is the Method used to look up classes on the Campus by utilizing the search bar provided.
     *   It will get search through the database of class rooms and and create a destination object.
     */
    public void geoLocate(View view) throws IOException {

        EditText et = (EditText) findViewById(R.id.classSearch);
        String clss = et.getText().toString();

        //Geocoder gc = new Geocoder(this);
        // UpperRight LatLng 18.006363, -76.748434
        // LowerLet Latlng  18.003429, -76.750615


        Cursor res = dbHelper.findClasses(clss);
        if (res.getCount() == 0) {
            // show message "no results found in class database"
            Toast.makeText(this, "Could not find " + clss, Toast.LENGTH_LONG).show();

            return;
        } else if (res.getCount() > 1) {
            // more than one possible classes found. Create method to let them choose
            Toast.makeText(this, " Select a Class", Toast.LENGTH_LONG).show();
            return;
        } else {
            // create the room
            res.moveToFirst();
            destination = new Room(res.getString(0), res.getString(1), res.getDouble(2), res.getDouble(3));
        }


//            List<android.location.Address> list = gc.getFromLocationName(clss, 1);
//            List<android.location.Address> list = gc.getFromLocationName(clss, 1, 18.003429, -76.750615, 18.006363, -76.748434); //bounds searches to Science and Technology
//            android.location.Address address = list.get(0);
//            String locality = address.getLocality();
//            clss = locality;


        double lat = destination.getLat();
        double lng = destination.getLng();

        Toast.makeText(this, destination.getRmName() + " is here", Toast.LENGTH_LONG).show();

        goToLocation(lat, lng);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void toggleLocations(View view) {

        boolean checked = ((ToggleButton) view).isChecked();
        if (checked) {
            useMyLocation();
            Toast.makeText(this, "Getting Your Location", Toast.LENGTH_LONG).show();

        } else {
            if (mGoogleApiClient != null)
                mGoogleApiClient.disconnect();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mGoogleMap.setMyLocationEnabled(false);
            goToLocation(18.005072, -76.749544);
        }

    }

    public void useMyLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mGoogleMap.setMyLocationEnabled(true);          //Enables google tracking.
        mUiSettings.setMyLocationButtonEnabled(false);  //Disable the google compass button.



        if(mGoogleApiClient == null){
            mGoogleApiClient =  new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(this, this)
                    .build();
        }
        mGoogleApiClient.connect();

    }


    /*
        Handles  the maps type switching
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
        Handling of the locations services and tracking
     */
    LocationRequest mLocationRequest;
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Location Lost", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Couldn't receive your location", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location == null){
            Toast.makeText(this, "Cant get current Location", Toast.LENGTH_LONG).show();
        }else {
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,18);
            mGoogleMap.animateCamera(update);
            //Toast.makeText(this, "Located", Toast.LENGTH_LONG).show();
        }

    }

    public void addMarker(LatLng ll, String title, String snip){

        if(marker != null ){
            marker.remove();
        }

        MarkerOptions option = new MarkerOptions()
                                .title(title)
                                .snippet(snip)
                        //      .icon(BitmapDescriptorFactory.fromResource(R.))
                                .position(ll);
         marker = mGoogleMap.addMarker(option);

    }



//    AddPlaceRequest place = new AddPlaceRequest("C5",
//                                            new LatLng(18.004294, -76.750225),
//                                            "The University Of The West Indies - Mona",
//                                            Collections.singletonList(Place.TYPE_POINT_OF_INTEREST),
//                                            "(876) 927-1660" );
//
//    Places.GeoDataApi.addPlace(mGoogleApiClient, place).setResultCallback(new ResultCallback<PlaceBuffer>(){
//
//        @Override
//        public void onResult(PlaceBuffer places)
//        {
//
//            if (!places.getStatus().isSuccess()) {
//
//                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
//                places.release();
//                return;
//            }
//
//            final Place place = places.get(0);
//            newPlaceID = place.getId();
//            Log.i(TAG, "Place add result: " + place.getName());
//            places.release();
//        }
//    });

//}


}//End of FINDFME

package com.android.comp3901.findmeuwi;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FindMe extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    //Map Clients
    GoogleApiClient mGoogleApiClient;
    UiSettings mUiSettings;
    DB_Helper dbHelper;
    Tracker mapTracker;
    MapMarker mMarkers;


    //Map Objects

    Marker startMarker, destMarker, marker;
    LinkedList<Polyline> graphLines;
    Vertex source, known;
    LinkedList<Vertex> route;
    Polyline line;

    //Views
    private AutoCompleteTextView getSourceView;
    private AutoCompleteTextView classSearchView;




    //Static Variables
    static GoogleMap mGoogleMap;

    static Vertex start;
    static Vertex destination;
    static Path path;

    static Activity instance;
    static Activity  get(){ return instance;}







    /*
    ** Initializes Map Fragment;
    **/
    private void initMap() {
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    /*
     * Function that tells the map what to do when its ready
     *
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Initialisations
        path = new Path(dbHelper); //Initialises path object which creates graph


        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();
        mMarkers = MapMarker.getInstance();
        mapTracker = new Tracker(getActivity());


        displayGraph(); // Display the edges
        displayIcons(); // Diplay the node icons
        goToLocation(18.005072, -76.749544);

        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_icyBlue))); //Changes the way how the map looks


        if (!success) {
            Toast.makeText(this.getActivity(), "Style parsing failed.", Toast.LENGTH_LONG).show();
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        instance = this.getActivity();

        super.onCreate(savedInstanceState);
        dbHelper =  DB_Helper.getInstance(getActivity()); //Creating databases


        try {// writes database to sd card for debugging purposes.
            dbHelper.writeToSD(this.getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }





        //Creates the fragment view.
        View view = inflater.inflate(R.layout.activity_find_me, container, false);

        // Sets the on click listener for the fragment elements.
        Button find = (Button)view.findViewById(R.id.findBtn);
        find.setOnClickListener(this);

        ToggleButton location = (ToggleButton)view.findViewById(R.id.locationToggle);
        location.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        //Handles each on click method for a button.
        switch (v.getId()){
            case R.id.findBtn:
                try {
                    geoLocate(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.getPath:
                getPath(v);
                break;
            case R.id.locationToggle:
                toggleLocations(v);
                break;
            default:
                break;

        }


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        if (googleServicesCheck()) {
            Toast.makeText(this.getActivity() , "Perfect!!", Toast.LENGTH_LONG).show();
            initMap();


        setTextViews();


       }
    }


    /*
    This methods sets the text views.
 */
    @NonNull
    private void setTextViews() {


        String[] roomSugg = dbHelper.roomList().toArray(new String[dbHelper.roomList().size()]);
        ArrayAdapter<String> roomsArrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, roomSugg);


        getSourceView = (AutoCompleteTextView) getView().findViewById(R.id.getSource);
        getSourceView.setThreshold(1);
        getSourceView.setAdapter(roomsArrayAdapter);



        getSourceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* hide keyboard */
                ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                String selected = (String) parent.getItemAtPosition(position);
                setSource();
                Toast.makeText(getActivity(),"Selected :" + selected, Toast.LENGTH_SHORT);
            }
        });






        classSearchView = (AutoCompleteTextView) getView().findViewById(R.id.classSearch);
        classSearchView.setThreshold(1);
        classSearchView.setAdapter(roomsArrayAdapter);
        classSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* hide keyboard */
                ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);


                String selected = (String) parent.getItemAtPosition(position);

                try {
                    geoLocate(getActivity().findViewById(R.id.classSearch));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"Selected :" + selected, Toast.LENGTH_SHORT);


            }
        });


        return ;
    }







    /*
    ** Checks to see if the User has play services available.
    **/
    public boolean googleServicesCheck() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this.getActivity());
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this.getActivity(), isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this.getActivity(), "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }



    /*
     *  Handles  the maps type switching
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

        EditText et = (EditText) getView().findViewById(R.id.classSearch);
        String clss = et.getText().toString();

        Cursor res = dbHelper.findClasses(clss);
        if (res.getCount() == 0) {
            // show message "no results found in class database"
            Toast.makeText(this.getActivity(), "Could not find " + clss, Toast.LENGTH_LONG).show();
            return;
        } else if (res.getCount() > 1) {
            //TODO more than one possible classes found. Create method to let them choose
            Toast.makeText(this.getActivity(), " Select a Class", Toast.LENGTH_LONG).show();
            return;
        } else {
            // create the room
            //new Vertex(res.getString(0), res.getString(1), res.getDouble(2), res.getDouble(3),"Room");
            destination = Path.vertices.get(res.getString(res.getColumnIndex(DB_Helper.RT_ID)));
            mMarkers.addMarker(destination.getLL(), destination.getName(), destination.getId(), 2);
        }



        Toast.makeText(this.getActivity(), destination.getName() + " is here", Toast.LENGTH_LONG).show();
        goToLocation(destination.getLat(), destination.getLng());
    }


    /*
        This methods should search through the vertex table
        and find a the starting point based of where the user
        selected in the text field.
        It utilizes the text filed @getSource to search through the database.
     */
    public boolean setSource() {

        String startText = getSourceView.getText().toString();

        Cursor res = dbHelper.findClasses(startText);




        if (res.getCount() == 0) {
            // show message "no results found in places database"
            Toast.makeText(this.getActivity(), "Could not find " + startText, Toast.LENGTH_LONG).show();
            return false;
        } else if (res.getCount() > 1) {
            // more than one possible starting point found found. Create method to let them choose

            Toast.makeText(this.getActivity(), "Please select one these Rooms to start from.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // create the starting node
            start = new Vertex(res.getString(0), res.getString(1), res.getDouble(2), res.getDouble(3), "TEMP");

            mMarkers.addMarker(start.getLL(), start.getName(), start.getId(), 1);

        }


        return true;
    }



    /*
     * This methods is called when the toggle button is click.
     * If enabled the location services is used  and the user's location is used as starting point
     * It also Provides the google API tracking services
     *
     */
    public void toggleLocations(View view) {

        // Uses check box to tell when user want to user there location.
        boolean checked = ((ToggleButton) view).isChecked();


        if (checked) {

            if (isLocationEnabled(this.getActivity())) {
                //provide some method that Uses user Location as starting point.
                useMyLocation();
                Toast.makeText(this.getActivity(), "Getting Your Location", Toast.LENGTH_LONG).show();

                // Disable the text field when he user has locations connected
                EditText getSource = (EditText) getView().findViewById(R.id.getSource);
                getSource.setHint("YOUR LOCATION");
                getSource.getText().clear();
                getSource.setFocusable(false);

            } else {
                Toast.makeText(this.getActivity(), "Please turn Locations on", Toast.LENGTH_LONG).show();
                ((ToggleButton) view).setChecked(false);

            }


        } else {
            if (mGoogleApiClient != null)
                mGoogleApiClient.disconnect();

            if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //   TODO: Consider calling
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

            EditText getSource = (EditText) getView().findViewById(R.id.getSource);
            getSource.setHint("Choose a Starting point");
            getSource.setFocusableInTouchMode(true);

        }

    }


    /*
     * Checks to see if the user has location services enabled returns false if it is off.
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;  // Return true if the location services is turn on

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    /*
     * Configures google maps to enable and use the user location.
     */
    public void useMyLocation() {

        if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage((FragmentActivity)this.getActivity(), this)
                    .build();
        }
        mGoogleApiClient.connect();
    }



    /*
     *  Method that adds a marker to the map.
     */
    public void addMarker(Double lat, Double lng, String title, String snip, Integer type) {

        LatLng ll = new LatLng(lat, lng);


        if (type == 1) {
            if (startMarker != null)
                startMarker.remove();

            MarkerOptions option = new MarkerOptions()
                    .title(title)
                    .snippet(snip)
                    //.icon(BitmapDescriptorFactory.fromResource(R.))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .position(ll);

            startMarker = mGoogleMap.addMarker(option);

        } else if (type == 2) {
            if (destMarker != null)
                destMarker.remove();

            MarkerOptions option = new MarkerOptions()
                    .title(title)
                    .snippet(snip)
                    //.icon(BitmapDescriptorFactory.fromResource(R.))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(ll);

            destMarker = mGoogleMap.addMarker(option);


        } else {


        }


    }


    /*
        This Method Performs the Dijkstras algorithm on the graph for the current source and destination selected.
     */
    public void getPath(View view) {

        Boolean mylocation = ((ToggleButton) getView().findViewById(R.id.locationToggle)).isChecked(); //Finds out if user want to use their their location.
        Boolean isSourceSet;


        //handles the start location configuration
        if (mylocation) {
            if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //MapMarker.startMarker
            start = Distance.find_closest_marker(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
            isSourceSet = true;

        }else{
            isSourceSet = setSource();
        }



         if (!isSourceSet) {
            // ask them to select a starting point
            Toast.makeText(this.getActivity(), "NO Source", Toast.LENGTH_LONG).show();
            return;
        } else if (destination == null) {
            Toast.makeText(this.getActivity(), "No Destination selected", Toast.LENGTH_LONG).show();
            return;
        } else {
            route = path.getPath(start.getId(), destination.getId());
                if(route==null){
                    Toast.makeText(this.getActivity(), "Could not find path from "+ start.getName()+ " to " + destination.getName() + " found.", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Toast.makeText(this.getActivity(), "Path to " + destination.getName() + " found.", Toast.LENGTH_LONG).show();
                }
        }

        drawPath(route);
        if(!mylocation){
            mapTracker.startArrivalTimer();
        }
    }





    /*
     * Draws a path with the provided the set of vertices
     */
    public void drawPath(LinkedList<Vertex> route){

        if(line != null)
            line.remove();

        route.size();
        LinkedList<LatLng> pnts = new LinkedList<>();

        for( int i = 0; i<route.size(); i++ ){
            LatLng  ll =  route.get(i).getLL();
            pnts.add(ll);
        }

        PolylineOptions  options = new PolylineOptions()
                                    .width(5)
                                    .addAll(pnts)
                                    .zIndex(02)
                                    .color(Color.GREEN)
                                    ;

        line = mGoogleMap.addPolyline(options);

    }



    /*
     * Displays all the edges in the graph.
     */
    public void displayGraph(){

        List<Edge> edges =  path.getEdges();
        List<Vertex> vertices = path.getNodes();

        HashMap<String, Vertex> vertexHashMap = path.getVertices();
        Polyline lane;
        Vertex v1,v2;
        graphLines = new LinkedList<>();



        for(Edge edge: edges){


            v1 = vertexHashMap.get(edge.getSource().getId());


            v2 = vertexHashMap.get(edge.getDestination().getId());


            //TODO make polyline clickable
            PolylineOptions options = new PolylineOptions()
                    .color(0x33606060)
                    .width(15)
                    .zIndex(01)
                    .add(v1.getLL(),v2.getLL());

            lane =  mGoogleMap.addPolyline(options);
            graphLines.add(lane);


        }

    }


    public void displayIcons(){
        List<Edge> edges =  path.getEdges();
        HashMap<String, Vertex> vertexHashMap = path.getVertices();

        for( Vertex nodes: vertexHashMap.values()){
            mMarkers.addIcon(nodes.getLL(),nodes.getName(),"Snip",nodes.getType());
         }
    }


    /*
        Handling of the locations services and tracking
     */
    LocationRequest mLocationRequest;



    // This gets called when the user location is picked upped
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //creates a location request object that gets the users location
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); //make a  request for the user's location every 1 second

        if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this); //this methods uses the location services to display users location

    }


    // Methods that gets called when the user's location isn't available
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this.getActivity(), "Location Lost", Toast.LENGTH_LONG).show();


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this.getActivity(), "Couldn't receive your location", Toast.LENGTH_LONG).show();

    }

    //When the user
    @Override
    public void onLocationChanged(Location location) {

        if(location == null){
            Toast.makeText(this.getActivity(), "Cant get current Location", Toast.LENGTH_LONG).show();
        }else {



            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,18);
            mGoogleMap.animateCamera(update);


            //Toast.makeText(this, "Located", Toast.LENGTH_LONG).show();





        }
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






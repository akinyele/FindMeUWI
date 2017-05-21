package com.android.comp3901.findmeuwi.activities;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.comp3901.findmeuwi.R;

public class MainActivity extends AppCompatActivity implements SearchView.OnSuggestionListener, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    public static final String TAG = "com.android.comp3901";
    FragmentManager fragmentManager = getFragmentManager();

    private SearchView searchView;
    FindMe mapFrag;

    SwitchCompat landmark_switch;
    RadioButton popup_radio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        popup_radio = (RadioButton) findViewById(R.id.popup_view);

        /**views**/



        fragmentManager.beginTransaction().replace(R.id.content_frame, new FindMe(), "mapFrag" ).commit();
        FloatingActionButton route = (FloatingActionButton) findViewById(R.id.fbPath);

        route.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FindMe mapFrag = (FindMe) getFragmentManager().findFragmentByTag("mapFrag");

                if(mapFrag.isAdded()){
                    mapFrag.getPath();
                }
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_landmark);
        View actionview = MenuItemCompat.getActionView(menuItem);


        landmark_switch = (SwitchCompat) actionview.findViewById(R.id.drawer_landmark_switch);
        landmark_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mapFrag.mapMarkers.showLandmarks(landmark_switch.isChecked());
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(mapFrag.sheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN ){
            mapFrag.sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        else{
            super.onBackPressed();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.main, menu);
       getMenuInflater().inflate(R.menu.map_menu, menu);
       getMenuInflater().inflate(R.menu.theme_pop_menu,menu);
//        getMenuInflater().inflate(R.menu.search,menu);
//
//        createSearchView(menu);
//
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        FindMe findMeFrag = (FindMe) getFragmentManager().findFragmentByTag("mapFrag");
        //TODO Needs fixing
      if(findMeFrag != null){

            switch (item.getItemId()) {
               case R.id.mapTypeNormal:
                    findMeFrag.onOptionsItemSelected(item);
                    break;
                case R.id.mapTypeSatellite:
                    findMeFrag.onOptionsItemSelected(item);
                    break;
                case R.id.style1:
                    findMeFrag.setTheme(R.string.style_icyBlue);
                    break;
                case R.id.style2:
                    findMeFrag.setTheme(R.string.style_cobalt);
                    break;
                case R.id.style3:
                    findMeFrag.setTheme(R.string.style_chilled);
                    break;
                case R.id.style4:
                    findMeFrag.setTheme(R.string.style_mapBox);
                    break;
                case R.id.style5:
                    findMeFrag.setTheme(R.string.style_Rainforest_Fringe);
                    break;
                default:
                    break;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            /*Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
            startActivityForResult(intent, 1);*/

            Intent i = new Intent(MainActivity.this, add_landmarks.class);
            startActivity(i);




        } else if (id == R.id.nav_theme) {
            /*fm.beginTransaction().replace(R.id.content_frame, new gmapsfrag()).commit();*/
            Log.d("theme", "onNavigationItemSelected: ");
            PopupMenu popupMenu= new PopupMenu(MainActivity.this, popup_radio);
            popupMenu.getMenuInflater().inflate(R.menu.theme_pop_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_landmark) {
            landmark_switch.setChecked(!(landmark_switch.isChecked()));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView temp = (TextView) view;

        Toast.makeText(this, temp.getText(), Toast.LENGTH_SHORT);
    }


    @Override
    public boolean onSuggestionSelect(int position) {

        Cursor cursor= searchView.getSuggestionsAdapter().getCursor();
        cursor.moveToPosition(position);
        String suggestion =cursor.getString(1);//2 is the index of col containing suggestion name.
        searchView.setQuery(suggestion,true);//setting suggestion


        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {

        Cursor cursor= searchView.getSuggestionsAdapter().getCursor();
        cursor.moveToPosition(position);
        String suggestion =cursor.getString(1);//2 is the index of col containing suggestion name.
        searchView.setQuery(suggestion,true);//setting suggestion




        Toast.makeText(this, ""+ suggestion ,Toast.LENGTH_SHORT);
        return false;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        recreate();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resumed");
    }
}

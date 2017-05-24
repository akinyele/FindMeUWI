package com.android.comp3901.findmeuwi.ui.main;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.comp3901.findmeuwi.R;
import com.android.comp3901.findmeuwi.ui.mapFragment.MapFrag;
import com.android.comp3901.findmeuwi.ui.addLandmarks.AddLandmarks;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainMvpView, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener ,CompoundButton.OnCheckedChangeListener{

    private static final String TAG = "com.android.comp3901";
    private FragmentManager fragmentManager = getFragmentManager();
    private MapFrag mapFrag;

    //menu
    Menu menu;

    MainPresenter presenter;

    //views
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    //inner views
    SwitchCompat landmark_switch;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);

        fragmentManager.beginTransaction().replace(R.id.content_frame, new MapFrag(), "mapFrag" ).commit();

        //getting reference to the fragment added
        mapFrag = (MapFrag) getFragmentManager().findFragmentByTag("mapFrag");


        //initializing navigation bar and listener
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();
        menuItem = menu.findItem(R.id.nav_landmark);
        View action_view = MenuItemCompat.getActionView(menuItem);

        landmark_switch = ButterKnife.findById(action_view,R.id.drawer_landmark_switch);
        landmark_switch.setOnCheckedChangeListener(this);
   }

    @Override
    public void onBackPressed() {
        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(mapFrag.sheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN ){
            mapFrag.sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            presenter.bottomSheet();
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
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        MapFrag mapFragFrag = (MapFrag) getFragmentManager().findFragmentByTag("mapFrag");
        //TODO Needs fixing
      if(mapFragFrag != null){
             switch (item.getItemId()) {
               case R.id.mapTypeNormal:
                    mapFragFrag.setStyle("normal");
                    break;
                case R.id.mapTypeSatellite:
                    mapFragFrag.setStyle("satellite");
                    break;
                case R.id.style1:
                    mapFragFrag.setTheme(R.string.style_icyBlue);
                    break;
                case R.id.style2:
                    mapFragFrag.setTheme(R.string.style_cobalt);
                    break;
                case R.id.style3:
                    mapFragFrag.setTheme(R.string.style_chilled);
                    break;
                case R.id.style4:
                    mapFragFrag.setTheme(R.string.style_mapBox);
                    break;
                case R.id.style5:
                    mapFragFrag.setTheme(R.string.style_Rainforest_Fringe);
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
             Intent i = new Intent(MainActivity.this, AddLandmarks.class);
            startActivity(i);
        } else if (id == R.id.nav_theme) {
            /*fm.beginTransaction().replace(R.id.content_frame, new gmapsfrag()).commit();*/
            Log.d("theme", "onNavigationItemSelected: ");
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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resumed");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.drawer_landmark_switch:
                mapFrag.mapMarkers.showLandmarks(landmark_switch.isChecked());
                break;
        }

    }


}

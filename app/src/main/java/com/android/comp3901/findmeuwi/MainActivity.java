package com.android.comp3901.findmeuwi;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ObbInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SearchView.OnSuggestionListener, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    FragmentManager fragmentManager = getFragmentManager();
    Fragment mapFrag;
    DB_Helper db_helper;

    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



             fragmentManager.beginTransaction().replace(R.id.content_frame, new FindMe(), "mapFrag" ).commit();



        db_helper = DB_Helper.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton route = (FloatingActionButton) findViewById(R.id.fbPath);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
                startActivityForResult(intent, 1);
                /*Snackbar.make(view, "Add landmark?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        route.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FindMe mapFrag = (FindMe) getFragmentManager().findFragmentByTag("mapFrag");

                if(mapFrag.isAdded()){
                    mapFrag.getPath(view);
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


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.main, menu);
       getMenuInflater().inflate(R.menu.map_menu, menu);
//        getMenuInflater().inflate(R.menu.search,menu);
//
//        createSearchView(menu);
//



        return true;
    }

    private void createSearchView(Menu menu) {

        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        final SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, MainActivity.class)));


        searchView.setOnSuggestionListener(this);
        searchView.setIconifiedByDefault(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //mapFrag = fragmentManager.findFragmentByTag("mapFrag");

        FindMe findMeFrag = (FindMe) getFragmentManager().findFragmentByTag("mapFrag");



        //Needs fixing


        if(findMeFrag != null){

            switch (item.getItemId()) {
                case R.id.mapTypeNone:
                    findMeFrag.onOptionsItemSelected(item);
                    break;
                case R.id.mapTypeNormal:
                    findMeFrag.onOptionsItemSelected(item);
                    break;
                case R.id.mapTypeSatellite:
                    findMeFrag.onOptionsItemSelected(item);
                    break;
                case R.id.mapTypeTerrain:
                    findMeFrag.onOptionsItemSelected(item);
                    break;
                case R.id.mapTypeHybrid:
                    findMeFrag.onOptionsItemSelected(item);
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
        } else if (id == R.id.nav_gallery) {
            /*fm.beginTransaction().replace(R.id.content_frame, new gmapsfrag()).commit();*/

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
}

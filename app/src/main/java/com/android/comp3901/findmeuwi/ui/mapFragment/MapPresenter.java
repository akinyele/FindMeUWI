package com.android.comp3901.findmeuwi.ui.mapFragment;


import com.android.comp3901.findmeuwi.data.AppDbHelper;

/**
 * Created by Kyzer on 5/24/2017.
 */

public class MapPresenter implements MapFragMvpPresenter {

    private MapFragMvPView view;

    private AppDbHelper db;

    public MapPresenter(MapFragMvPView view) {
        this.view = view;
    }

}

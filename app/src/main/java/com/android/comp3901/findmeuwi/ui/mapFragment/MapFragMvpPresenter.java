package com.android.comp3901.findmeuwi.ui.mapFragment;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Kyzer on 5/24/2017.
 */

public interface MapFragMvpPresenter {

    ArrayList<String> getRoomsFromDb();
    Collection<? extends String> getBuildingsFromDb();
    void getPath();
    boolean toggleLocation(boolean checked);
    void setPic(ImageView infoImage, String filepath);
}

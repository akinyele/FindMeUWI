package com.android.comp3901.findmeuwi.ui.main;

import com.android.comp3901.findmeuwi.ui.mapFragment.MapFragMvpPresenter;

/**
 * Created by Kyzer on 5/23/2017.
 */

public class MainPresenter implements MainMvpPresenter {

    MainMvpView view;

    MainPresenter(MainMvpView view){
        this.view = view;
    }


    public void bottomSheet() {
    }

    public int getBottomSheetState() {
        return 1; //bottomSheetState;
    }
}

package com.android.comp3901.findmeuwi.ui.mapFragment;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kyzer on 5/24/2017.
 */

public interface MapFragMvPView {

    void makeToast(String msg);
    void goToLocation(LatLng ll);

    String getStartText();
    String getDestText();

    void disableSourceText(boolean b);

    void locationEnabled(boolean b);

    void setChecked(boolean b);
}

package com.android.comp3901.findmeuwi.ui.addLandmarks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import com.android.comp3901.findmeuwi.R;
import com.android.comp3901.findmeuwi.data.AppDbHelper;
import com.android.comp3901.findmeuwi.ui.mapFragment.MapFrag;
import com.android.comp3901.findmeuwi.utils.PhotoScalerAndSaver;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Kyzer on 5/14/2017.
 */

public class AddLandmarks extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String TAG = "com.android.comp3901.";
    private static AppDbHelper db;

    /** Views **/
    @BindView(R.id.landmark_name) EditText landmarkName;
    @BindView(R.id.landmark_desc) EditText landmarkDesc;
    @BindView(R.id.landmark_preview) ImageView landmarkImage;
    @BindView(R.id.landmark_location_textView) TextView location_text;
    /** data**/
    Bitmap imageBitmap;
    Location landmark_location;
    private Unbinder unbinder;

    private boolean hasLocation = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_landmark_activity);
        ButterKnife.bind(this);
        /**Objects Initialisation **/
        db = AppDbHelper.getInstance(getApplicationContext());
    }

    /** Set On Click Listeners*/
    @OnClick(R.id.get_landmark_location)
    public void imageBtnClick(View v){
        landmark_location = MapFrag.getLocation(getApplication() );
        //Log.d(TAG, "onClick: GetMy Location "+ MapFrag.my_location);
        if(landmark_location == null){
            Toast.makeText(AddLandmarks.this,"Unable to Obtain Location, Ensure GPS is turned on", Toast.LENGTH_SHORT).show();
            return;
        }
        location_text.setText("Latitude "+landmark_location.getLatitude() + " Longitude: " +landmark_location.getLatitude());
        hasLocation = true;
    }


    @OnClick(R.id.add_landmark_imageBtn)
    public void fabBtnClick(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @OnClick(R.id.save_landmark)
    public void saveBtnClick(View v){
        String name = landmarkName.getText().toString().trim();
        String desc = landmarkDesc.getText().toString().trim();
        Log.d(TAG, "onClick: "+name+" "+desc);

        if(!name.equals("") && !desc.equals("") && hasLocation && landmarkImage.getDrawable()!=null ){
            //check to see if all the fields have info in them
            Log.d(TAG, "onClick: "+ landmarkName.getTextSize() + " " + landmarkDesc.getTextSize());
            saveLandmark(name, desc, landmark_location.getLatitude(), landmark_location.getLongitude(), imageBitmap);
            setToast("Saved");
            finish();
        }else{
            setToast("Please Fill Out info");
        }
    }

    public void saveLandmark(String name, String desc, double lat, double lng, Bitmap img ){

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String image_id = name+"_"+timeStamp;

        PhotoScalerAndSaver.saveScaledPhotoToFile(img,image_id, getApplicationContext() );

        db.insertLandmark(lat,lng, name,desc,image_id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            landmarkImage.setImageBitmap(imageBitmap);

        }
    }

    private void setToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }



}

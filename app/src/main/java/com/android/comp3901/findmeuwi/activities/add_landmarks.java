package com.android.comp3901.findmeuwi.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import com.android.comp3901.findmeuwi.R;
import com.android.comp3901.findmeuwi.services.DB_Helper;
import com.android.comp3901.findmeuwi.ui.mapFragment.mapFragment;
import com.android.comp3901.findmeuwi.utils.PhotoScalerAndSaver;

import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyzer on 5/14/2017.
 */

public class add_landmarks extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String TAG = "com.android.comp3901.";
    private static DB_Helper db;

    private String mCurrentPhotoPath;

    /** Views **/
    private Button saveLandmarkBtn;
    private EditText landmarkName;
    private EditText landmarkDesc;
    private FloatingActionButton fab;
    private ImageView landmarkImage;
    private ImageButton getLocation;
    private TextView location_text;



    /** data**/
    Bitmap imageBitmap;
    Location landmark_location;



    private boolean hasLocation = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_landmark_activity);

        /**Objects Initialisation **/
        db = DB_Helper.getInstance(getApplicationContext());

        /**Inittialising views*/
        landmarkImage = (ImageView)findViewById(R.id.landmark_preview);
        saveLandmarkBtn = (Button)findViewById(R.id.save_landmark);
        landmarkName = (EditText)findViewById(R.id.landmark_name);
        landmarkDesc = (EditText)findViewById(R.id.landmark_desc);
        getLocation = (ImageButton)findViewById(R.id.get_landmark_location);
        location_text = (TextView)findViewById(R.id.landmark_location_textView) ;

        /** Set On Click Listeners*/

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            landmark_location = mapFragment.getLocation(getApplication() );
            //Log.d(TAG, "onClick: GetMy Location "+ mapFragment.my_location);
            if(landmark_location == null){
                Toast.makeText(add_landmarks.this,"Unable to Obtain Location, Ensure GPS is turned on", Toast.LENGTH_SHORT).show();
                    return;
            }
            location_text.setText("Latitude "+landmark_location.getLatitude() + " Longitude: " +landmark_location.getLatitude());
            hasLocation = true;

            }
        });

        saveLandmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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




        });

        /* Image intent */
        fab = (FloatingActionButton) findViewById(R.id.add_landmark_imageBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);




            }
        });
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

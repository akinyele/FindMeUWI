package com.android.comp3901.findmeuwi.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import com.android.comp3901.findmeuwi.R;
import com.google.android.gms.vision.face.Landmark;

import android.os.Environment;
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

import java.io.File;
import java.io.IOException;

/**
 * Created by Kyzer on 5/14/2017.
 */

public class add_landmarks extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String TAG = "com.android.comp3901.";

    private String mCurrentPhotoPath;

    /** Views **/
    private Button saveLandmarkBtn;
    private EditText landmarkName;
    private EditText landmarkDesc;
    private FloatingActionButton fab;
    private ImageView landmarkImage;
    private ImageButton getLocation;
    private TextView location_text;



    private boolean hasLocation = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_landmark_activity);

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

            Location landmark_location = FindMe.getLocation(getApplication() );
            //Log.d(TAG, "onClick: GetMy Location "+ FindMe.my_location);
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

                if(!name.equals("") && !desc.equals("") && hasLocation && landmarkImage.getDrawable()!=null ){
                    Log.d(TAG, "onClick: "+ landmarkName.getTextSize() + " " + landmarkDesc.getTextSize() );
                    setToast("Saved");
                    finish();
                }else{
                    setToast("Please Fill Out info");
                }
           }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            landmarkImage.setImageBitmap(imageBitmap);

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name


        String image_name = "";

        String imageFileName = "Landmark_" + image_name + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void setToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }
}

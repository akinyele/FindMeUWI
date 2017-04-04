package com.android.comp3901.findmeuwi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Kyzer on 3/26/2017.
 */

public class Searchable extends AppCompatActivity {

    TextView destinationText, sourceText;
    EditText dest, source;

    EditText et = (EditText) findViewById(R.id.classSearch);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_employess_searchable_activity);

        destinationText = (EditText) findViewById(R.id.classSearch);
        sourceText = (EditText) findViewById(R.id.getSource);

        int id = getIntent().getExtras().getInt("id");



//        myDatabase = new SqliteHandler(EmployeesSearchableActivity.this);
//
//        showEmployeesDescription(id);

    }





}

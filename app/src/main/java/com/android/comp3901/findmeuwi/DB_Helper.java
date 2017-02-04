package com.android.comp3901.findmeuwi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dylan on 2/4/2017.
 */

public class DB_Helper extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "findme";

    // Database Room table variable.
    public static final String ROOM_TABLE = "room_data";
    public static final String RT_NAME = "name";
    public static final String RT_LAT = "latitude";
    public static final String RT_LONG = "longitude";
    public static final String RT_DESC = "description";
    public static final String RT_FLOOR = "floor";
    public static final String RT_KNOWN = "known";
    public static final String RT_FAM = "familiarity";
    public static final String RT_BUILDING = "building";
    //public static final String RT_COL_8 = "image";







    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        SQLiteDatabase db = this.getWritableDatabase();



    }

//    public DB_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            //SLT2
            String InserRooms = "insert into " + ROOM_TABLE+ "values( " +
                                "'SLT 2', 18.0051221, -76.7497594, 'in front of slt 2 , Next to the  Deans Office', 1 , 0, True, 0)" ;
            db.execSQL("create database " + ROOM_TABLE + "( " +
                            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                            RT_NAME + "TEXT," +
                            RT_LAT + "INTEGER," +
                            RT_LONG + "INTEGER," +
                            RT_DESC + "BLOB," +
                            RT_FLOOR + "INTEGER," +
                            RT_KNOWN + "STRING," +
                            RT_FAM + "INTEGER ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + ROOM_TABLE +
                    "");

        onCreate(db);

    }
}

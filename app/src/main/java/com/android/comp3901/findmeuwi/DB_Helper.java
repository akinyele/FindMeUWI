package com.android.comp3901.findmeuwi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dylan on 2/4/2017.
 */

public class DB_Helper extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "findme.db";

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
            String InsertRooms = "INSERT INTO " + ROOM_TABLE + "(" +RT_NAME +", "+RT_LAT+", "+RT_LONG+", "+RT_DESC+", "+RT_FLOOR+",  "+RT_KNOWN+", "+RT_FAM+" )" +
                    //          Name, Lat, Lng, Description, Floor, Known, Familiarity
                                " VALUES ('SLT 2', 18.0051221, -76.7497594, 'in front of slt 2 , Next to the  Deans Office', 1, 'True', 0)" ;
        // db.execSQL("INSERT INTO " + TABLE_DATUM+ "(NAME, A, B, SCALEFACTOR, FEASTING ) VALUES ('WGS 84', 6378137, 6356752.314, 0.9996, 500000)");


        db.execSQL(       " CREATE TABLE IF NOT EXISTS  " + ROOM_TABLE + " (" +
                            " ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            RT_NAME + " TEXT, " +
                            RT_LAT + " REAL, " +
                            RT_LONG + " REAL, " +
                            RT_DESC + " TEXT, " +
                            RT_FLOOR + " REAL, " +
                            RT_KNOWN + " TEXT, " +
                            RT_FAM + " REAL ); ");

//      db.execSQL( "INSERT INTO " + ROOM_TABLE + "( name, latitude, longitude, description, floor, known, familiarity )" +
//                        " VALUES ('SLT 2', 18.0051221, -76.7497594, 'in front of slt 2 , Next to the  Deans Office', 1, 'True', 0)");
        db.execSQL(InsertRooms);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + ROOM_TABLE +
                    "");
        onCreate(db);
    }


    public Cursor findClasses( String rm ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + ROOM_TABLE + " WHERE LOWER("+ RT_NAME + ") like LOWER('"+ rm +"') ;", null);

        Cursor res2 = db.rawQuery( "SELECT * FROM " + ROOM_TABLE +" ;", null);

        return res2;
    }
}

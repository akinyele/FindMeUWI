package com.android.comp3901.findmeuwi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dylan on 2/4/2017.
 */

public class DB_Helper extends SQLiteOpenHelper{

    public static final Integer DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "findme.db";


    // Database For Vertices variable.
    public static final String VERTICES_TABLE = "vertices";
    public static final String V_NAME = "Vname";
    public static final String V_ID = "Vid";
    public static final String V_LAT = "latitude";
    public static final String V_LONG = "longitude";
    public static final String V_TYPE = "type";

    // Database for edges on the graph.
    public static final String EDGES_TABLE = "edges";
    public static final String E_SOURCE = "source";
    public static final String E_DESTINATION = "destination";


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
    // public static final String RT_COL_8 = "image";


    // Database for Buildings variable.
    public static final String BUILDING_TABLE = "buildings";
    public static final String B_NAME = "name";
    public static final String B_region = "region";


    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

     }

//    public DB_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {


            //SLT2
            String InsertRooms = "INSERT INTO " + ROOM_TABLE + "(" +RT_NAME +", "+RT_LAT+", "+RT_LONG+", "+RT_DESC+", "+RT_FLOOR+",  "+RT_KNOWN+", "+RT_FAM+" )" +
                    //          Name, Lat, Lng, Description, Floor, Known, Familiarity
                    " VALUES ('SLT 2', 18.0051221, -76.7497594, 'in front of slt 2 , Next to the  Deans Office', 1, 0 , 0) " +

                    ";" ;

                    // " VALUES ('', , ,'', , ,  )" ;




        db.execSQL(" CREATE TABLE " + ROOM_TABLE + " ( " +
                    RT_NAME + " TEXT, " +
                    RT_LAT + " REAL, " +
                    RT_LONG + " REAL, " +
                    RT_DESC + " TEXT, " +
                    RT_FLOOR + " REAL, " +
                    RT_KNOWN + " INT, " +
                    RT_FAM + " REAL, " +
                   "PRIMARY KEY ("+RT_LAT+","+RT_LONG+") ); ");

//        String InsertVertices = " INSERT INTO TABLE " +VERTICES_TABLE+ "("+V_ID+", "+ V_NAME+", "+V_LAT+", "+V_LONG+", "+V_TYPE+") " +
//                                 "Values('S1','SLT',18.0051221,-76.7497594,'ROOM') " ;

        // 18.004490, -76.750003 (infront of c5 )
        //

        db.execSQL(" CREATE TABLE IF NOT EXISTS " + VERTICES_TABLE + " (" +
                    " " +
                    V_ID  + " TEXT, " +
                    V_NAME + "TEXT, " +
                    V_LAT + " REAL, " +
                    V_LONG  + " REAL, " +
                    V_TYPE + " TEXT," +
                    "PRIMARY KEY ("+V_LAT+","+V_LONG+") ); " );


        db.execSQL(" CREATE TABLE IF NOT EXISTS " + EDGES_TABLE + " ( " +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    E_DESTINATION + " TEXT, " +
                    E_SOURCE + " TEXT ); ");



        db.execSQL(InsertRooms);
        //db.execSQL(InsertVertices);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ROOM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + VERTICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BUILDING_TABLE);
        onCreate(db);
    }



    public Cursor findClasses( String rm ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + ROOM_TABLE + " WHERE LOWER("+ RT_NAME + ") like LOWER('%"+ rm +"%') ;", null);

        return res;
    }

    public void generateDB(){
        generateRooms();
        return ;
    }


    public void generateRooms(){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rooms = new ContentValues();

        //" ('Chemistry Lecture Theatre', 18.004490, -76.750003, 'Description', 1 , 0 , 0) " +

        rooms.put(RT_NAME,"Chemistry Lecture Theatre");
        rooms.put(RT_LAT, 18.004490);
        rooms.put(RT_LONG,-76.750003);
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR, 1 );
        rooms.put(RT_KNOWN, 0 );
        rooms.put(RT_FAM, 0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_NAME,"Science Lecture Theatre 1");
        rooms.put(RT_LAT, 18.005166);
        rooms.put(RT_LONG, -76.749909 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR, 1);
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM, 0);
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


//        rooms.put(RT_NAME,"");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR, );
//        rooms.put(RT_KNOWN, );
//        rooms.put(RT_FAM, );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();


        db.close();
        return ;
    }

    public void generateVertices(){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vertices = new ContentValues();

    }



}

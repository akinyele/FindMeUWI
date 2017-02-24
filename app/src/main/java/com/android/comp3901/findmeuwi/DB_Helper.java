package com.android.comp3901.findmeuwi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Akinyele on 2/4/2017.
 */

public class DB_Helper extends SQLiteOpenHelper{

    public static final Integer DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "findme.db";
    private static String DB_PATH = "";



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
    public static final String E_WEIGHT = "weight";


    // Database Room table variable.
    public static final String ROOM_TABLE = "room_data";
    public static final String RT_ID = "ID";
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
        String InsertRooms = "INSERT INTO " + ROOM_TABLE + "(" +RT_ID +", " +RT_NAME +", "+RT_LAT+", "+RT_LONG+", "+RT_DESC+", "+RT_FLOOR+",  "+RT_KNOWN+", "+RT_FAM+" )" +
                    //          Name, Lat, Lng, Description, Floor, Known, Familiarity
                    " VALUES ('SLT 2', 'Science Lecture Theatre 2', 18.0051221, -76.7497594, 'in front of slt 2 , Next to the  Deans Office', 1, 0 , 0) ";

        db.execSQL(" CREATE TABLE " + ROOM_TABLE + " ( " +
                    RT_ID + " TEXT, " +
                    RT_NAME + " TEXT, " +
                    RT_LAT + " REAL, " +
                    RT_LONG + " REAL, " +
                    RT_DESC + " TEXT, " +
                    RT_FLOOR + " REAL, " +
                    RT_KNOWN + " INT, " +
                    RT_FAM + " REAL, " +
                   "PRIMARY KEY ("+RT_LAT+","+RT_LONG+") ); ");


        db.execSQL(" CREATE TABLE " + VERTICES_TABLE + " (" +
                    V_ID  + " TEXT, " +
                    V_NAME + " TEXT, " +
                    V_LAT + " REAL, " +
                    V_LONG  + " REAL, " +
                    V_TYPE + " TEXT," +
                    "PRIMARY KEY ("+V_LAT+","+V_LONG+") ); " );


        db.execSQL(" CREATE TABLE IF NOT EXISTS " + EDGES_TABLE + " ( " +
                    E_DESTINATION + " TEXT, " +
                    E_SOURCE + " TEXT, " +
                    E_WEIGHT + " INTEGER, " +
                    "PRIMARY KEY ("+E_DESTINATION+","+E_SOURCE+") );");

        db.execSQL(InsertRooms);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ROOM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + VERTICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BUILDING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EDGES_TABLE);
        onCreate(db);
    }


    public void generateDB(){
        generateRooms();
        generateEdges();
        generateVertices();
        return ;
    }

    public void generateRooms(){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rooms = new ContentValues();

        //" ('Chemistry Lecture Theatre', 18.004490, -76.750003, 'Description', 1 , 0 , 0) " +

        rooms.put(RT_ID,"C5");
        rooms.put(RT_NAME,"Chemistry Lecture Theatre");
        rooms.put(RT_LAT, 18.004506);
        rooms.put(RT_LONG,-76.749995);
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR, 1 );
        rooms.put(RT_KNOWN, 0 );
        rooms.put(RT_FAM, 0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"SLT 1");
        rooms.put(RT_NAME,"Science Lecture Theatre 1");
        rooms.put(RT_LAT, 18.005178);
        rooms.put(RT_LONG, -76.749861 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR, 1);
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM, 0);
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"SLT 2");
        rooms.put(RT_NAME,"Science Lecture Theatre 2");
        rooms.put(RT_LAT,18.005209 );
        rooms.put(RT_LONG,-76.749750 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"SLT 3 ");
        rooms.put(RT_NAME,"Science Lecture Theatre 3");
        rooms.put(RT_LAT,18.005384 );
        rooms.put(RT_LONG,-76.750077 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();
//
//        rooms.put(RT_ID," C2");
//        rooms.put(RT_NAME," Chemistry Lecture Theatre 2");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"C3 ");
//        rooms.put(RT_NAME,"Chemistry Lecture Theatre 3");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();


        rooms.put(RT_ID,"C6 ");
        rooms.put(RT_NAME,"Chemistry Lecture Theatre 6");
        rooms.put(RT_LAT,18.004673 );
        rooms.put(RT_LONG,-76.749995 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"C7 ");
        rooms.put(RT_NAME,"Chemistry Lecture Theatre 7");
        rooms.put(RT_LAT,18.004734 );
        rooms.put(RT_LONG,-76.749913 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"CHEPHY ");
        rooms.put(RT_NAME,"Chem/Phys Lecture Theatre");
        rooms.put(RT_LAT,18.004370 );
        rooms.put(RT_LONG,-76.749152);
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();
//
//        rooms.put(RT_ID,"COMPLR ");
//        rooms.put(RT_NAME,"Computing Lecture Room");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,3 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();

        rooms.put(RT_ID,"COMPLAB ");
        rooms.put(RT_NAME,"Computer Lab Lab 1");
        rooms.put(RT_LAT,18.005132 );
        rooms.put(RT_LONG,-76.750145 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();
//
//        rooms.put(RT_ID,"CPGR ");
//        rooms.put(RT_NAME,"Computing Post-Graduate Room");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
        rooms.put(RT_ID," CR1 ");
        rooms.put(RT_NAME,"Computer Science Tutorial Room 1");
        rooms.put(RT_LAT,18.005032 );
        rooms.put(RT_LONG,-76.750159 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," CR2");
        rooms.put(RT_NAME,"Computer Science Tutorial Room 2");
        rooms.put(RT_LAT,18.005068 );
        rooms.put(RT_LONG,-76.750106 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();
//
//        rooms.put(RT_ID,"NGR-LRA  ");
//        rooms.put(RT_NAME,"Engineering Lecture Room A ");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"NGR-LRB ");
//        rooms.put(RT_NAME,"Engineering Lecture Room B");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"NGR-LRC ");
//        rooms.put(RT_NAME,"Engineering Lecture Room C");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," IFLT ");
//        rooms.put(RT_NAME,"Inter-Faculty Lecture Theatre");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," VIRLAB");
//        rooms.put(RT_NAME,"Virtual Computer Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," MLT1");
//        rooms.put(RT_NAME,"Math Lecture Theatre 1");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"ML2 ");
//        rooms.put(RT_NAME,"Math Lecture Theatre 2");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"ML3 ");
//        rooms.put(RT_NAME,"Math Lecture Theatre 3");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"MLAB ");
//        rooms.put(RT_NAME,"Math Computer Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"ACL ");
//        rooms.put(RT_NAME,"Analytical Chemistry Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"BLT ");
//        rooms.put(RT_NAME,"FST Biology Lecture Theatre");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," C06P");
//        rooms.put(RT_NAME,"Preliminary Chemistry Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," CHETR1");
//        rooms.put(RT_NAME,"Chemistry Tutorial Room 1");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"CHETR2 ");
//        rooms.put(RT_NAME,"Chemistry Tutorial Room 2");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,3 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSADB  ");
//        rooms.put(RT_NAME,"DLS Advanced Biology Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSAQU ");
//        rooms.put(RT_NAME,"DLS Aquatic Sciences Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSCOM ");
//        rooms.put(RT_NAME,"DLS Computer Room");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSENT ");
//        rooms.put(RT_NAME,"DLS Entomology Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSFOR ");
//        rooms.put(RT_NAME,"DLS Forestry Eco Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," DLSINT");
//        rooms.put(RT_NAME,"DLS Introductory Lab 13");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSLB2  ");
//        rooms.put(RT_NAME,"DLS Lab 2");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSLB3 ");
//        rooms.put(RT_NAME,"DLS Lab 3");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," DLSLB4");
//        rooms.put(RT_NAME,"DLS Lab 4");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," DLSPBL");
//        rooms.put(RT_NAME,"DLS preliminary Biology Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," DLSPHY");
//        rooms.put(RT_NAME,"DLS Physiolog Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"ECNFRM ");
//        rooms.put(RT_NAME,"Electronics Conference Room");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSSM1 ");
//        rooms.put(RT_NAME,"DLS Seminar Room 1");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSSM4 ");
//        rooms.put(RT_NAME,"DLS Seminar Room 4");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSSM5 ");
//        rooms.put(RT_NAME,"DLS Seminar Room 5");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSSM6 ");
//        rooms.put(RT_NAME,"DLS Seminar Room 6");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"DLSSM7 ");
//        rooms.put(RT_NAME,"DLS Seminar Room 7");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," DLSSM8");
//        rooms.put(RT_NAME,"DLS Seminar Room 8");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID," EL2LAB");
//        rooms.put(RT_NAME,"Electronics Conference Room");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"NGR-S-L ");
//        rooms.put(RT_NAME," Soils Laboratory");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"FCL ");
//        rooms.put(RT_NAME,"Food Chemistry Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();

//        rooms.put(RT_ID,"GENPHY");
//        rooms.put(RT_NAME,"General Physics Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"GGCLAB");
//        rooms.put(RT_NAME,"Geography/Geology Computer Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//
//        rooms.put(RT_ID,"GGLAB1");
//        rooms.put(RT_NAME,"Laboratory 1");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"GGLAB2");
//        rooms.put(RT_NAME,"Laboratory 2");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"GGLAB3");
//        rooms.put(RT_NAME,"Laboratory 3");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"GGLAB4");
//        rooms.put(RT_NAME,"Laboratory 4");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"GGTUTR");
//        rooms.put(RT_NAME,"Geography/Geology Tutorial Room");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"PCL");
//        rooms.put(RT_NAME,"Physical Chemsitry Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"PHYS-A");
//        rooms.put(RT_NAME,"Physics Lecture Room A");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"PHYS-B");
//        rooms.put(RT_NAME,"Physics Lecture Room B");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"PHYS-C");
//        rooms.put(RT_NAME,"Physics Lecture Room C");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();

        rooms.put(RT_ID,"PHYS-D");
        rooms.put(RT_NAME,"Physics Lecture Room D");
        rooms.put(RT_LAT,18.004503 );
        rooms.put(RT_LONG,-76.749008 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

//        rooms.put(RT_ID,"PHYTUT");
//        rooms.put(RT_NAME,"Physics Tutorial Room");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,2 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"PREPHY");
//        rooms.put(RT_NAME,"Preliminary Physics Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,1 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();
//
//        rooms.put(RT_ID,"SOLARB");
//        rooms.put(RT_NAME,"Solar Lab");
//        rooms.put(RT_LAT, );
//        rooms.put(RT_LONG, );
//        rooms.put(RT_DESC, "Description");
//        rooms.put(RT_FLOOR,3 );
//        rooms.put(RT_KNOWN,0 );
//        rooms.put(RT_FAM,0 );
//        db.insert(ROOM_TABLE,null, rooms);
//        rooms.clear();



//        rooms.put(RT_ID,"");
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

        // 18.004678, -76.749723 CA
        // 18.004879, -76.749702 CB

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vertices = new ContentValues();

        vertices.put(V_ID, "SLT 2"  );
        vertices.put(V_NAME, "Science Lecture Theatre 2" );
        vertices.put(V_LAT, 18.005209 );
        vertices.put(V_LONG, -76.749750);
        vertices.put(V_TYPE, "ROOM");

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID, "SLT 1");
        vertices.put(V_NAME, "Science Lecture Theatre 1" );
        vertices.put(V_LAT,  18.005178);
        vertices.put(V_LONG, -76.749861);
        vertices.put(V_TYPE , "ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID, "C5" );
        vertices.put(V_NAME, "Chemistry Lecture Theatre" );
        vertices.put(V_LAT, 18.004506);
        vertices.put(V_LONG, -76.749995);
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,  "CA");
        vertices.put(V_NAME, "CA" );
        vertices.put(V_LAT, 18.004678);
        vertices.put(V_LONG, -76.749723);
        vertices.put(V_TYPE , "POINT");

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID, "CB" );
        vertices.put(V_NAME, "CB" );
        vertices.put(V_LAT, 18.004879 );
        vertices.put(V_LONG, -76.749702 );
        vertices.put(V_TYPE , "POINT");

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"Department of Mathematics");
        vertices.put(V_NAME, "Department of Mathematics");
        vertices.put(V_LAT,  18.004790);
        vertices.put(V_LONG, -76.749598);
        vertices.put(V_TYPE , "Building");

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"SLT3"  );
        vertices.put(V_NAME,"Science Lecture Theatre 3"  );
        vertices.put(V_LAT,18.005384  );
        vertices.put(V_LONG,-76.750077   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"C7"  );
        vertices.put(V_NAME,"C7 Chemistry Lecture Theatre"  );
        vertices.put(V_LAT, 18.4734 );
        vertices.put(V_LONG, -76749913  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"C6"  );
        vertices.put(V_NAME,"C6 Chemistry Lecture Theatre"  );
        vertices.put(V_LAT,18.004673  );
        vertices.put(V_LONG, -76.749995  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"COMLAB"  );
        vertices.put(V_NAME,"Computer Science Lab Building"  );
        vertices.put(V_LAT, 18.005242 );
        vertices.put(V_LONG, -76.750182  );
        vertices.put(V_TYPE ,"Building" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"CR1"  );
        vertices.put(V_NAME,"Computer Science Tutorial Room 1"  );
        vertices.put(V_LAT,18.005032  );
        vertices.put(V_LONG, -76.750106  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"CR2 "  );
        vertices.put(V_NAME,"Computer Science Tutorial Room 2"  );
        vertices.put(V_LAT,18.005068  );
        vertices.put(V_LONG, -76.750106  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"CHEPHY");
        vertices.put(V_NAME,"Chem/Phys Lecture Theatre" );
        vertices.put(V_LAT,18.004370  );
        vertices.put(V_LONG,-76.749152   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"COMLABR");
        vertices.put(V_NAME,"Computer Science Lab Room" );
        vertices.put(V_LAT,18.005132 );
        vertices.put(V_LONG,-76.750145 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"PHYS-D");
        vertices.put(V_NAME,"PHYS-D(PHYSICS LECTURE ROOM D" );
        vertices.put(V_LAT,18.005132 );
        vertices.put(V_LONG,-76.750145 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();








//
//        vertices.put(V_ID,  );
//        vertices.put(V_NAME,  );
//        vertices.put(V_LAT,  );
//        vertices.put(V_LONG,   );
//        vertices.put(V_TYPE , );
//
//        db.insert(VERTICES_TABLE,null,vertices);
//        vertices.clear();

        db.close();
    }

    public void generateEdges(){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues edges = new ContentValues();

        edges.put(E_SOURCE, "C5");
        edges.put(E_DESTINATION, "CA");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "CA");
        edges.put(E_DESTINATION, "C5");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "CA");
        edges.put(E_DESTINATION, "CB");
        edges.put(E_WEIGHT,1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "CB");
        edges.put(E_DESTINATION, "CA");
        edges.put(E_WEIGHT,1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "Department of Mathematics");
        edges.put(E_DESTINATION, "CB");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "CB");
        edges.put(E_DESTINATION, "Department of Mathematics");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "Department of Mathematics");
        edges.put(E_DESTINATION, "SLT 1");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "SLT 1");
        edges.put(E_DESTINATION, "Department of Mathematics");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "SLT 1");
        edges.put(E_DESTINATION, "SLT 2");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "SLT 2");
        edges.put(E_DESTINATION, "SLT 1");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

     db.close();
    }


    public Cursor findClasses( String rm ){
         SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + ROOM_TABLE + " WHERE LOWER("+ RT_NAME + ") like LOWER('%"+ rm +"%') " +
                "OR  LOWER("+ RT_ID + ") like LOWER('%"+ rm +"%');", null);

        res.moveToFirst();
        return res;
    }

    public Cursor getVertices(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + VERTICES_TABLE + " WHERE 1 ", null);

        res.moveToFirst();
        return res;
    }

    public  Cursor getEdges(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + EDGES_TABLE + " WHERE 1 ", null);

        res.moveToFirst();
        return res;

    }






    /*
        For debugging purposes to get database.
     */
     public void writeToSD(Context context) throws IOException {
        File sd = Environment.getExternalStorageDirectory();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DB_PATH = context.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
        }
        else {
            DB_PATH = context.getFilesDir().getPath() + context.getPackageName() + "/databases/";
        }

        if (sd.canWrite()) {
            String currentDBPath = DATABASE_NAME;
            String backupDBPath = "backupname.db";
            File currentDB = new File(DB_PATH, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        }
    }

}

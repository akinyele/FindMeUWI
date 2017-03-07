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

    public static final Integer DATABASE_VERSION = 10;
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


    /*
     *Generates all the initial database values
     */
    public void generateDB(){
        generateRooms();
        generateEdges();
        generateVertices();
        return ;
    }

    /*
     * Generate rooms
     */
    public void generateRooms(){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rooms = new ContentValues();

        //" ('Chemistry Lecture Theatre', 18.004490, -76.750003, 'Description', 1 , 0 , 0) " +

        rooms.put(RT_ID," CHETR1");
        rooms.put(RT_NAME,"Chemistry Tutorial Room 1");
        rooms.put(RT_LAT,18.004593 );
        rooms.put(RT_LONG,-76.750123 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"CHETR2 ");
        rooms.put(RT_NAME,"Chemistry Tutorial Room 2");
        rooms.put(RT_LAT,18.004577 );
        rooms.put(RT_LONG,-76.750150 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,3 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"ACL ");
        rooms.put(RT_NAME,"Analytical Chemistry Lab");
        rooms.put(RT_LAT,18.004767 );
        rooms.put(RT_LONG,-76.749870 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," C06P");
        rooms.put(RT_NAME,"Preliminary Chemistry Lab");
        rooms.put(RT_LAT,18.004200 );
        rooms.put(RT_LONG,-76.749581 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"PCL");
        rooms.put(RT_NAME,"Physical Chemistry Lab");
        rooms.put(RT_LAT,18.004030 );
        rooms.put(RT_LONG,-76.749437 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," C2");
        rooms.put(RT_NAME," Chemistry Lecture Theatre 2");
        rooms.put(RT_LAT,18.004348 );
        rooms.put(RT_LONG,-76.749758 );
        rooms.put(RT_DESC, "");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"C3 ");
        rooms.put(RT_NAME,"Chemistry Lecture Theatre 3");
        rooms.put(RT_LAT,18.004379 );
        rooms.put(RT_LONG,-76.749753 );
        rooms.put(RT_DESC, "");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

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

        rooms.put(RT_ID,"FCL ");
        rooms.put(RT_NAME,"Food Chemistry Lab");
        rooms.put(RT_LAT,18.004003 );
        rooms.put(RT_LONG,-76.749779 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"AIL");
        rooms.put(RT_NAME,"Advanced Inorganic Chemistry Lab");
        rooms.put(RT_LAT,18.004018 );
        rooms.put(RT_LONG,-76.749476 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
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


        rooms.put(RT_ID,"COMPLR ");
        rooms.put(RT_NAME,"Computing Lecture Room");
        rooms.put(RT_LAT,18.005974 );
        rooms.put(RT_LONG,-76.749685 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,3 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

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

        rooms.put(RT_ID,"CPGR ");
        rooms.put(RT_NAME,"Computing Post-Graduate Room");
        rooms.put(RT_LAT,18.005999 );
        rooms.put(RT_LONG,-76.749644 );
        rooms.put(RT_DESC, "");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

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

        rooms.put(RT_ID,"NGRM-WS  ");
        rooms.put(RT_NAME,"Mechanical Engineering Workshop ");
        rooms.put(RT_LAT,18.005130 );
        rooms.put(RT_LONG,-76.748741 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"NGR-LRA  ");
        rooms.put(RT_NAME,"Engineering Lecture Room A ");
        rooms.put(RT_LAT,18.004834 );
        rooms.put(RT_LONG,-76.749991 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"NGR-LRB ");
        rooms.put(RT_NAME,"Engineering Lecture Room B");
        rooms.put(RT_LAT,18.004794 );
        rooms.put(RT_LONG,-76.750063 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"NGR-Dra ");
        rooms.put(RT_NAME,"Engineering Drafting Room");
        rooms.put(RT_LAT,18.005722 );
        rooms.put(RT_LONG,-76.749694 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," IFLT ");
        rooms.put(RT_NAME,"Inter-Faculty Lecture Theatre");
        rooms.put(RT_LAT,18.005648 );
        rooms.put(RT_LONG,-76.748699 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID," MLT1");
        rooms.put(RT_NAME,"Math Lecture Theatre 1");
        rooms.put(RT_LAT,18.004970 );
        rooms.put(RT_LONG,-76.749379 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"ML2 ");
        rooms.put(RT_NAME,"Math Lecture Theatre 2");
        rooms.put(RT_LAT,18.004934 );
        rooms.put(RT_LONG,-76.749435 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"ML3 ");
        rooms.put(RT_NAME,"Math Lecture Theatre 3");
        rooms.put(RT_LAT,18.004888 );
        rooms.put(RT_LONG,-76.749454 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"MLAB ");
        rooms.put(RT_NAME,"Math Computer Lab");
        rooms.put(RT_LAT,18.004894 );
        rooms.put(RT_LONG,-76.749526 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"BLT ");
        rooms.put(RT_NAME,"FST Biology Lecture Theatre");
        rooms.put(RT_LAT,18.006292 );
        rooms.put(RT_LONG,-76.750432 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"DLSADB  ");
        rooms.put(RT_NAME,"DLS Advanced Biology Lab");
        rooms.put(RT_LAT,18.005958 );
        rooms.put(RT_LONG,-76.749625 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSAQU ");
        rooms.put(RT_NAME,"DLS Aquatic Sciences Lab");
        rooms.put(RT_LAT,18.005991 );
        rooms.put(RT_LONG,-76.750105 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSCOM ");
        rooms.put(RT_NAME,"DLS Computer Room");
        rooms.put(RT_LAT,18.005919 );
        rooms.put(RT_LONG,-76.749706 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSENT ");
        rooms.put(RT_NAME,"DLS Entomology Lab");
        rooms.put(RT_LAT,18.006293 );
        rooms.put(RT_LONG,-76.749763 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSFOR ");
        rooms.put(RT_NAME,"DLS Forestry Eco Lab");
        rooms.put(RT_LAT,18.005850 );
        rooms.put(RT_LONG,-76.749936 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," DLSINT");
        rooms.put(RT_NAME,"DLS Introductory Lab 13");
        rooms.put(RT_LAT,18.006448 );
        rooms.put(RT_LONG,-76.750304 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSLB2  ");
        rooms.put(RT_NAME,"DLS Lab 2");
        rooms.put(RT_LAT,18.005696 );
        rooms.put(RT_LONG,-76.750453 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSLB3 ");
        rooms.put(RT_NAME,"DLS Lab 3");
        rooms.put(RT_LAT,18.006031 );
        rooms.put(RT_LONG,-76.750602 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," DLSLB4");
        rooms.put(RT_NAME,"DLS Lab 4");
        rooms.put(RT_LAT,18.006031 );
        rooms.put(RT_LONG,-76.750602 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," DLSPBL");
        rooms.put(RT_NAME,"DLS preliminary Biology Lab");
        rooms.put(RT_LAT,18.006192 );
        rooms.put(RT_LONG,-76.750141 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," DLSPHY");
        rooms.put(RT_NAME,"DLS Physiology Lab");
        rooms.put(RT_LAT,18.006625 );
        rooms.put(RT_LONG,-76.750082 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"DLSSM1 ");
        rooms.put(RT_NAME,"DLS Seminar Room 1");
        rooms.put(RT_LAT,18.006090 );
        rooms.put(RT_LONG,-76.750511 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSSM2 ");
        rooms.put(RT_NAME,"DLS Seminar Room 2");
        rooms.put(RT_LAT,18.006448 );
        rooms.put(RT_LONG,-76.750304 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSSM3 ");
        rooms.put(RT_NAME,"DLS Seminar Room 3");
        rooms.put(RT_LAT,18.006625 );
        rooms.put(RT_LONG,-76.750082 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSSM4 ");
        rooms.put(RT_NAME,"DLS Seminar Room 4");
        rooms.put(RT_LAT,18.005859 );
        rooms.put(RT_LONG,-76.749871 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSSM5 ");
        rooms.put(RT_NAME,"DLS Seminar Room 5");
        rooms.put(RT_LAT,18.005859 );
        rooms.put(RT_LONG,-76.749871 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSSM6 ");
        rooms.put(RT_NAME,"DLS Seminar Room 6");
        rooms.put(RT_LAT,18.005932 );
        rooms.put(RT_LONG,-76.749734 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSSM7 ");
        rooms.put(RT_NAME,"DLS Seminar Room 7");
        rooms.put(RT_LAT,18.005943 );
        rooms.put(RT_LONG,-76.749906 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," DLSSM8");
        rooms.put(RT_NAME,"DLS Seminar Room 8");
        rooms.put(RT_LAT,18.005943 );
        rooms.put(RT_LONG,-76.749906 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"DLSMVL");
        rooms.put(RT_NAME,"DLS Molec & Virology Lab");
        rooms.put(RT_LAT,18.005943 );
        rooms.put(RT_LONG,-76.749906 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"GGCLAB");
        rooms.put(RT_NAME,"Geography/Geology Computer Lab");
        rooms.put(RT_LAT,18.006091 );
        rooms.put(RT_LONG,-76.748902 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"GGLAB1");
        rooms.put(RT_NAME,"Laboratory 1");
        rooms.put(RT_LAT,18.006136 );
        rooms.put(RT_LONG,-76.748880 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"GGLAB2");
        rooms.put(RT_NAME,"Laboratory 2");
        rooms.put(RT_LAT,18.006132 );
        rooms.put(RT_LONG,-76.749017 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"GGLAB3");
        rooms.put(RT_NAME,"Laboratory 3");
        rooms.put(RT_LAT,18.006134 );
        rooms.put(RT_LONG,-76.748966 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,0 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"GGLAB4");
        rooms.put(RT_NAME,"Laboratory 4");
        rooms.put(RT_LAT,18.005969 );
        rooms.put(RT_LONG,-76.749099 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,0 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"GGTUTR");
        rooms.put(RT_NAME,"Geography/Geology Tutorial Room");
        rooms.put(RT_LAT,18.006117 );
        rooms.put(RT_LONG,-76.748861 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"PHYS-A");
        rooms.put(RT_NAME,"Physics Lecture Room A");
        rooms.put(RT_LAT,18.005219 );
        rooms.put(RT_LONG,-76.749056 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"PHYS-B");
        rooms.put(RT_NAME,"Physics Lecture Room B");
        rooms.put(RT_LAT,18.005219 );
        rooms.put(RT_LONG,-76.749027 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"PHYS-C");
        rooms.put(RT_NAME,"Physics Lecture Room C");
        rooms.put(RT_LAT,18.005064 );
        rooms.put(RT_LONG,-76.748949 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

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

        rooms.put(RT_ID,"PHYS-E");
        rooms.put(RT_NAME,"Physics Lecture Room E");
        rooms.put(RT_LAT,18.005097 );
        rooms.put(RT_LONG,-76.749205 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"PHYTUT");
        rooms.put(RT_NAME,"Physics Tutorial Room");
        rooms.put(RT_LAT,18.005272 );
        rooms.put(RT_LONG,-76.749099 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"PREPHY");
        rooms.put(RT_NAME,"Preliminary Physics Lab");
        rooms.put(RT_LAT,18.004797 );
        rooms.put(RT_LONG,-76.748785 );
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

        rooms.put(RT_ID," VIRLAB");
        rooms.put(RT_NAME,"Virtual Computer Lab");
        rooms.put(RT_LAT,18.004736 );
        rooms.put(RT_LONG,-76748973 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"SOLARB");
        rooms.put(RT_NAME,"Solar Lab");
        rooms.put(RT_LAT,18.005569 );
        rooms.put(RT_LONG,-76.749115 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,3 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();


        rooms.put(RT_ID,"EL1LAB ");
        rooms.put(RT_NAME,"Level 1 Electronics Lab");
        rooms.put(RT_LAT,18.004574);
        rooms.put(RT_LONG,-76.748972 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," EL2LAB");
        rooms.put(RT_NAME,"Level 2 Electronics Lab");
        rooms.put(RT_LAT,18.004599 );
        rooms.put(RT_LONG,-76.748827 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,2 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID," EL3LAB");
        rooms.put(RT_NAME,"Level 3 Electronics Lab");
        rooms.put(RT_LAT,18.004718 );
        rooms.put(RT_LONG,-76.750202 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,0 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();

        rooms.put(RT_ID,"NGR-S-L ");
        rooms.put(RT_NAME," Soils Laboratory");
        rooms.put(RT_LAT,18.005288 );
        rooms.put(RT_LONG,-76.750332 );
        rooms.put(RT_DESC, "Description");
        rooms.put(RT_FLOOR,1 );
        rooms.put(RT_KNOWN,0 );
        rooms.put(RT_FAM,0 );
        db.insert(ROOM_TABLE,null, rooms);
        rooms.clear();



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

    /*
     * Generate Vertices
     */
    public void generateVertices(){

        // 18.004678, -76.749723 CA
        // 18.004879, -76.749702 CB

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vertices = new ContentValues();

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

        vertices.put(V_ID, "SLT 1");
        vertices.put(V_NAME, "Science Lecture Theatre 1" );
        vertices.put(V_LAT,  18.005178);
        vertices.put(V_LONG, -76.749861);
        vertices.put(V_TYPE , "ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID, "SLT 2" );
        vertices.put(V_NAME, "Science Lecture Theatre 2" );
        vertices.put(V_LAT, 18.005209 );
        vertices.put(V_LONG, -76.749750);
        vertices.put(V_TYPE, "ROOM");

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
        vertices.put(V_NAME,"Chemistry Lecture Theatre 7"  );
        vertices.put(V_LAT, 18.4734 );
        vertices.put(V_LONG, -76749913  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"C6"  );
        vertices.put(V_NAME,"Chemistry Lecture Theatre 6"  );
        vertices.put(V_LAT,18.004673  );
        vertices.put(V_LONG, -76.749995  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID, "C5" );
        vertices.put(V_NAME, "Chemistry Lecture Theatre 5" );
        vertices.put(V_LAT, 18.004506);
        vertices.put(V_LONG, -76.749995);
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"C2"  );
        vertices.put(V_NAME,"Chemistry Lecture Theatre 2"  );
        vertices.put(V_LAT,18. );
        vertices.put(V_LONG, -76.  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"C3"  );
        vertices.put(V_NAME,"Chemistry Lecture Theatre 3"  );
        vertices.put(V_LAT,18. );
        vertices.put(V_LONG, -76.  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"ACL"  );
        vertices.put(V_NAME,"Analytical Chemistry Lab  "  );
        vertices.put(V_LAT,18.004767  );
        vertices.put(V_LONG,-76.749870   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"AIL"  );
        vertices.put(V_NAME,"Advanced Inorganic Chemistry Lab  "  );
        vertices.put(V_LAT,18.004018  );
        vertices.put(V_LONG,-76.749476   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"PCL"  );
        vertices.put(V_NAME,"Physical Chemistry Lab  "  );
        vertices.put(V_LAT,18.004030  );
        vertices.put(V_LONG,-76.749437   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"C06P"  );
        vertices.put(V_NAME,"Preliminary Chemistry Lab"  );
        vertices.put(V_LAT,18.004200  );
        vertices.put(V_LONG,-76.749581   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"CHETR1"  );
        vertices.put(V_NAME,"Chemistry Tutorial 1"  );
        vertices.put(V_LAT,18.004593  );
        vertices.put(V_LONG,-76.750123   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"CHETR2"  );
        vertices.put(V_NAME,"Chemistry Tutorial 2"  );
        vertices.put(V_LAT,18.004577  );
        vertices.put(V_LONG,-76.750150   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"COMLABB"  );
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


        vertices.put(V_ID,"COMLAB");
        vertices.put(V_NAME,"Computer Science Lab Room" );
        vertices.put(V_LAT,18.005132 );
        vertices.put(V_LONG,-76.750145 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"COMPLR "  );
        vertices.put(V_NAME,"Computing Lecture Room  "  );
        vertices.put(V_LAT,18.005974  );
        vertices.put(V_LONG,-76.749685   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"CPGR "  );
        vertices.put(V_NAME,"Computing Post-Graduate Room  "  );
        vertices.put(V_LAT,18.005999  );
        vertices.put(V_LONG,-76.749644   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"Department of Computing"  );
        vertices.put(V_NAME,"Department of Computing Building"  );
        vertices.put(V_LAT,18.005749  );
        vertices.put(V_LONG,-76.750067   );
        vertices.put(V_TYPE ,"Building" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();



        vertices.put(V_ID,"NGR-LRA");
        vertices.put(V_NAME,"Engineering Lecture Room A");
        vertices.put(V_LAT,18.004834  );
        vertices.put(V_LONG,-76.749991  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"NGR-LRB");
        vertices.put(V_NAME,"Engineering Lecture Room B");
        vertices.put(V_LAT,18.004794  );
        vertices.put(V_LONG,-76.750063  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"NGRM-WS");
        vertices.put(V_NAME,"Mechanical Engineering Workshop");
        vertices.put(V_LAT,18.005130 );
        vertices.put(V_LONG,-76.748741);
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"NGR-Dra "  );
        vertices.put(V_NAME,"Engineering Drafting Room  "  );
        vertices.put(V_LAT,18.005722  );
        vertices.put(V_LONG,-76.749694   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"NGR-F-L "  );
        vertices.put(V_NAME,"Fluids Laboratory  "  );
        vertices.put(V_LAT,18.005569  );
        vertices.put(V_LONG,-76.749115   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"NGR-S-L "  );
        vertices.put(V_NAME,"Soils Laboratory  "  );
        vertices.put(V_LAT,18.005288  );
        vertices.put(V_LONG,-76.750332   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"DLSADB"  );
        vertices.put(V_NAME,"DLS Advanced Biology Lab"  );
        vertices.put(V_LAT,18.005958  );
        vertices.put(V_LONG,-76.749625   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSAQU"  );
        vertices.put(V_NAME,"DLS Aquatic Sciences Lab"  );
        vertices.put(V_LAT,18.005991  );
        vertices.put(V_LONG,-76.750105   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSCOM"  );
        vertices.put(V_NAME,"DLS Computer Room"  );
        vertices.put(V_LAT,18.005919  );
        vertices.put(V_LONG,-76.749706  );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSENT"  );
        vertices.put(V_NAME,"DLS Entomology Lab"  );
        vertices.put(V_LAT,18.006293  );
        vertices.put(V_LONG,-76.749763   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSFOR"  );
        vertices.put(V_NAME,"DLS Forestry Eco Lab"  );
        vertices.put(V_LAT,18.0058501  );
        vertices.put(V_LONG,-76.749936   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSINT"  );
        vertices.put(V_NAME,"DLS Introductory Lab 13"  );
        vertices.put(V_LAT,18.006471  );
        vertices.put(V_LONG,-76.750275   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"DLSLB2 "  );
        vertices.put(V_NAME,"DLS Lab 2 "  );
        vertices.put(V_LAT,18.005696  );
        vertices.put(V_LONG,-76.750453   );
        vertices.put(V_TYPE,"ROOM " );



        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSLB3 "  );
        vertices.put(V_NAME,"DLS Lab 3 "  );
        vertices.put(V_LAT,18.006031  );
        vertices.put(V_LONG,-76.750602   );
        vertices.put(V_TYPE,"ROOM " );



        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSLB4 "  );
        vertices.put(V_NAME,"DLS Lab 4 "  );
        vertices.put(V_LAT,18.006031  );
        vertices.put(V_LONG,-76.750602   );
        vertices.put(V_TYPE,"ROOM " );



        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSMVL "  );
        vertices.put(V_NAME,"Molec & Virology Lab "  );
        vertices.put(V_LAT,18.005943  );
        vertices.put(V_LONG,-76.749906   );
        vertices.put(V_TYPE,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSPBL "  );
        vertices.put(V_NAME,"DLS Preliminary Biology Lab "  );
        vertices.put(V_LAT,18.006192  );
        vertices.put(V_LONG,-76.750141  );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSPHY "  );
        vertices.put(V_NAME,"DLS Physiology Lab "  );
        vertices.put(V_LAT,18.006625  );
        vertices.put(V_LONG,-76.750082   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM1 "  );
        vertices.put(V_NAME,"DLS Seminar Room 1 "  );
        vertices.put(V_LAT,18.006090  );
        vertices.put(V_LONG,-76.750511   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM2 "  );
        vertices.put(V_NAME,"DLS Seminar Room 2 "  );
        vertices.put(V_LAT,18.006448  );
        vertices.put(V_LONG,-76.750304   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM3 "  );
        vertices.put(V_NAME,"DLS Seminar Room 3 "  );
        vertices.put(V_LAT,18.006625  );
        vertices.put(V_LONG,-76.750082   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM4 "  );
        vertices.put(V_NAME,"DLS Seminar Room 4 "  );
        vertices.put(V_LAT,18.005859  );
        vertices.put(V_LONG,-76.749871   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM5 "  );
        vertices.put(V_NAME,"DLS Seminar Room 5  "  );
        vertices.put(V_LAT,18.005859  );
        vertices.put(V_LONG,-76.749871   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM6 "  );
        vertices.put(V_NAME,"DLS Seminar Room 6 "  );
        vertices.put(V_LAT,18.005932  );
        vertices.put(V_LONG,-76.749734   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM7 "  );
        vertices.put(V_NAME,"DLS Seminar Room 7 "  );
        vertices.put(V_LAT,18.005943  );
        vertices.put(V_LONG,-76.749906   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"DLSSM8 "  );
        vertices.put(V_NAME,"DLS Seminar Room 8 "  );
        vertices.put(V_LAT,18.005943  );
        vertices.put(V_LONG,-76.749906   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"BLT");
        vertices.put(V_NAME,"FST Biology Lecture Theatre"  );
        vertices.put(V_LAT,18.006292  );
        vertices.put(V_LONG,-76.750432   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"EL1LAB "  );
        vertices.put(V_NAME,"Level 1 Electronics Lab  "  );
        vertices.put(V_LAT,18.004574  );
        vertices.put(V_LONG,-76.748972   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"EL2LAB "  );
        vertices.put(V_NAME,"Level 2 Electronics Lab  "  );
        vertices.put(V_LAT,18.004599  );
        vertices.put(V_LONG,-76.748827   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"EL3LAB "  );
        vertices.put(V_NAME,"Level 3 Electronics Lab  "  );
        vertices.put(V_LAT,18.004718  );
        vertices.put(V_LONG,-76.750202   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"FCL "  );
        vertices.put(V_NAME,"Food Chemistry Lab  "  );
        vertices.put(V_LAT,18.004003  );
        vertices.put(V_LONG,-76.749779   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"GGCLAB "  );
        vertices.put(V_NAME,"Geography/Geology Computer Lab  "  );
        vertices.put(V_LAT,18.006091  );
        vertices.put(V_LONG,-76.748902   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"GGLAB1 "  );
        vertices.put(V_NAME,"Laboratory 1  "  );
        vertices.put(V_LAT,18.006136  );
        vertices.put(V_LONG,-76.748880   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"GGLAB2 "  );
        vertices.put(V_NAME,"Laboratory 2  "  );
        vertices.put(V_LAT,18.006132  );
        vertices.put(V_LONG,-76.749017   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"GGLAB3 "  );
        vertices.put(V_NAME,"Laboratory 3  "  );
        vertices.put(V_LAT,18.006134  );
        vertices.put(V_LONG,-76.748966   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"GGLAB4 "  );
        vertices.put(V_NAME," Laboratory 4 "  );
        vertices.put(V_LAT,18.005969  );
        vertices.put(V_LONG,-76.749099   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"GGTUTR "  );
        vertices.put(V_NAME,"Geography/Geology Tutorial Room  "  );
        vertices.put(V_LAT,18.006117  );
        vertices.put(V_LONG,-76.748861   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"IFLT "  );
        vertices.put(V_NAME,"Inter-Faculty Lecture Theatre  "  );
        vertices.put(V_LAT,18.005648  );
        vertices.put(V_LONG,-76.748699   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"Department of Mathematics");
        vertices.put(V_NAME, "Department of Mathematics");
        vertices.put(V_LAT,  18.004790);
        vertices.put(V_LONG, -76.749598);
        vertices.put(V_TYPE , "Building");

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"MLAB "  );
        vertices.put(V_NAME,"Math Computer Lab "  );
        vertices.put(V_LAT,18.004894  );
        vertices.put(V_LONG,-76.749526   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"MLT1 "  );
        vertices.put(V_NAME,"Math Lecture Theatre 1"  );
        vertices.put(V_LAT,18.004970  );
        vertices.put(V_LONG,-76.749379   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"MLT2  "  );
        vertices.put(V_NAME,"Math Lecture Theatre 2 "  );
        vertices.put(V_LAT,18.004934  );
        vertices.put(V_LONG,-76.749435   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"MLT3 "  );
        vertices.put(V_NAME,"Math Lecture Theatre 3 "  );
        vertices.put(V_LAT,18.004888  );
        vertices.put(V_LONG,-76.749454   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"PHYTUT "  );
        vertices.put(V_NAME,"Physics Tutorial Room "  );
        vertices.put(V_LAT,18.005272  );
        vertices.put(V_LONG,-76.749099   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


        vertices.put(V_ID,"PHYS-A");
        vertices.put(V_NAME,"PHYSICS LECTURE ROOM A " );
        vertices.put(V_LAT,18.005219 );
        vertices.put(V_LONG,-76.749056 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"PHYS-B");
        vertices.put(V_NAME,"PHYSICS LECTURE ROOM B" );
        vertices.put(V_LAT,18.005168 );
        vertices.put(V_LONG,-76.749027 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();
        vertices.put(V_ID,"PHYS-C");
        vertices.put(V_NAME,"PHYSICS LECTURE ROOM C" );
        vertices.put(V_LAT,18.005064 );
        vertices.put(V_LONG,-76.748949 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"PHYS-D");
        vertices.put(V_NAME,"PHYSICS LECTURE ROOM D" );
        vertices.put(V_LAT,18.005132 );
        vertices.put(V_LONG,-76.750145 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"PHYS-E");
        vertices.put(V_NAME,"PHYSICS LECTURE ROOM E" );
        vertices.put(V_LAT,18.005097 );
        vertices.put(V_LONG,-76.749205 );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"PREPHY "  );
        vertices.put(V_NAME,"Preliminary Physics Lab "  );
        vertices.put(V_LAT,18.004797  );
        vertices.put(V_LONG,-76.748785   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"SOLARB "  );
        vertices.put(V_NAME,"Solar Lab "  );
        vertices.put(V_LAT,18.005569  );
        vertices.put(V_LONG,-76.749115   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"VIRLAB " );
        vertices.put(V_NAME,"Virtual Computer Lab "  );
        vertices.put(V_LAT,18.004736  );
        vertices.put(V_LONG,-76.748973   );
        vertices.put(V_TYPE,"ROOM " );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();

        vertices.put(V_ID,"CHEPHY");
        vertices.put(V_NAME,"Chem/Phys Lecture Theatre" );
        vertices.put(V_LAT,18.004370  );
        vertices.put(V_LONG,-76.749152   );
        vertices.put(V_TYPE ,"ROOM" );

        db.insert(VERTICES_TABLE,null,vertices);
        vertices.clear();


//
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

    /*
     * Generate Edges
     */
    public void generateEdges(){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues edges = new ContentValues();

        edges.put(E_SOURCE, "C5");
        edges.put(E_DESTINATION, "CA");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "CA");
        edges.put(E_DESTINATION, "CB");
        edges.put(E_WEIGHT,1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "Department of Mathematics");
        edges.put(E_DESTINATION, "CB");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();

        edges.put(E_SOURCE, "Department of Mathematics");
        edges.put(E_DESTINATION, "SLT 1");
        edges.put(E_WEIGHT, 1);
        db.insert(EDGES_TABLE,null,edges);
        edges.clear();


        edges.put(E_SOURCE, "SLT 1");
        edges.put(E_DESTINATION, "SLT 2");
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

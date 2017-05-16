package com.android.comp3901.findmeuwi.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.comp3901.findmeuwi.activities.FindMe;
import com.android.comp3901.findmeuwi.locations.Building;
import com.android.comp3901.findmeuwi.locations.Room;
import com.android.comp3901.findmeuwi.locations.Vertex;
import com.android.comp3901.findmeuwi.services.DB_Helper;
import com.android.comp3901.findmeuwi.services.Edge;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created on 2/17/2017.
 */

public class Path {
    public static final String TAG = "com.android.comp3901.";

    // Used in Path Finding method
    private List<Vertex> nodes;
    private static List<Vertex> connectedNodes;
    private List<Edge> edges;
    DB_Helper dbHelper;

    public static List<Vertex> getConnectedNodes() {
        return connectedNodes;
    }

    public LinkedList<Vertex> getCurrPath() {
        return currPath;
    }

    private LinkedList<Vertex> currPath;
    private Graph graph;
    private HashMap<String,Vertex> vertices; //List of all the vertices that are in the database

    public static final Object lock = new Object();


    /*
     *  Constructor methods which initializes and creates all the vertices and edges.
     */
    public Path(DB_Helper db){

        this.nodes = new ArrayList<Vertex>();
        this.connectedNodes = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
        this.vertices = new HashMap<String,Vertex>();
        this.dbHelper = db;

        Cursor verticesDB = dbHelper.getVertices();
        Cursor edgesDB = dbHelper.getEdges();


        //creates a vertex object for the values in the database
        while (!verticesDB.isAfterLast()) {
            Vertex location = creator(verticesDB);

            //Places the create vertex into a list and a HashMap
            nodes.add(location);
            vertices.put(verticesDB.getString(verticesDB.getColumnIndex(DB_Helper.V_ID)), location);

            verticesDB.moveToNext();
        }

        //creates a edge for each value in the database
        while ( !edgesDB.isAfterLast() ){

           Vertex v1 = vertices.get(edgesDB.getString(0));
           Vertex v2 =  vertices.get(edgesDB.getString(1));


            //creates the same edge twice, for going foward and one for going backwards
            Edge lane1 = new Edge(edgesDB.getString(0) + " -> " + edgesDB.getString(1),
                    vertices.get(edgesDB.getString(0)),
                    vertices.get(edgesDB.getString(1)),
                    (int) edgesDB.getDouble(2));

            Edge lane2 = new Edge(edgesDB.getString(1) + " -> " + edgesDB.getString(0),
                    vertices.get(edgesDB.getString(1)),
                    vertices.get(edgesDB.getString(0)),
                    (int) edgesDB.getDouble(2));


            connectedNodes.add(vertices.get(edgesDB.getString(0)));
            connectedNodes.add(vertices.get(edgesDB.getString(1)));

            edges.add(lane1);
            edges.add(lane2);

            edgesDB.moveToNext();
        }

        this.graph = new Graph(nodes,edges);
    }


    /***
     * This is when a path is created for the firt time
     * Uses thstra to gee dijknerates the shortest path
     * @param src
     * @param dest
     * @return
     */
    public LinkedList<Vertex>   getPath(String src, String dest) throws InterruptedException {

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        dijkstra.execute(vertices.get(src));

        currPath = dijkstra.getPath(vertices.get(dest));
        //assertNotNull(path);
        //assertTrue(path.size() > 0);

        synchronized (lock){
            Log.d(TAG, "getPath: Resuming Threads");
            lock.notify();
        }
        return currPath;
    }

    public HashMap<String,Vertex> getVertices(){ return vertices; };

    public Graph getGraph(){
        return graph;
    }


    /***
     * Used to get the nodes that are connected to the graph
     * @return
     */
    public List getCNodes() {   return  connectedNodes; }



    public List<Vertex> getNodes(){
        return nodes;
    }

    public List<Edge> getEdges(){
        return edges;
    }


    //TODO finish implementation
    public List<LatLng> getLatLngs(){
        ArrayList<LatLng> latLngs = new ArrayList<>();

        for(Vertex node: currPath){





        }

     return latLngs;
    }


    /*
     *Used when creating the vertices. Used to create the vertices of the right subclass
     */
    private Vertex creator(Cursor verticesDB){

        Vertex Location = null;
        String type =verticesDB.getString(verticesDB.getColumnIndex(DB_Helper.V_TYPE)).toLowerCase().replaceAll("\\s+","");
        String test =verticesDB.getString(verticesDB.getColumnIndex(DB_Helper.V_ID));

        Cursor res = dbHelper.findLocation( verticesDB.getDouble(verticesDB.getColumnIndex(DB_Helper.V_LAT)),
                verticesDB.getDouble(verticesDB.getColumnIndex(DB_Helper.V_LONG)),
                verticesDB.getString(verticesDB.getColumnIndex(DB_Helper.V_ID))
        );

        switch (type){
            case "room":
                if(res.getCount() == 0){
                    Log.d("GET ROOM", test);
                    assertEquals(1,res.getCount());
                }

                Location = new Room(res.getString(res.getColumnIndex(DB_Helper.RT_ID)),
                        res.getString(res.getColumnIndex(DB_Helper.RT_NAME)),//name
                        res.getDouble(res.getColumnIndex(DB_Helper.RT_LAT)), //latitude
                        res.getDouble(res.getColumnIndex(DB_Helper.RT_LONG)),//longtude
                        res.getInt(res.getColumnIndex(DB_Helper.RT_KNOWN)),
                        res.getDouble(res.getColumnIndex(DB_Helper.RT_FAM)),
                        res.getDouble(res.getColumnIndex(DB_Helper.RT_FLOOR)),
                        res.getString(res.getColumnIndex(DB_Helper.RT_DESC)),
                        res.getInt(res.getColumnIndex(DB_Helper.RT_LANDMARK))
                        );
                break;

            //TODO add when building have been added to the database.
           case "building":

               if(res.getCount() == 0){
                   Log.d("GET BUILDING", test);
                   assertEquals(1,res.getCount());
               }
                Location = new Building( res.getString(res.getColumnIndex(DB_Helper.B_ID)),
                        res.getString(res.getColumnIndex(DB_Helper.B_NAME)),
                        res.getDouble(res.getColumnIndex(DB_Helper.B_LAT)),
                        res.getDouble(res.getColumnIndex(DB_Helper.B_LONG)),
                        res.getString(res.getColumnIndex(DB_Helper.B_ROOMS)).split(","),
                        res.getInt(res.getColumnIndex(DB_Helper.B_FLOORS)),
                        res.getInt(res.getColumnIndex(DB_Helper.B_KNOWN)),
                        res.getDouble(res.getColumnIndex(DB_Helper.B_FAM)),
                        res.getInt(res.getColumnIndex(DB_Helper.B_LANDMARK)));
                        break;
            default:
                Location = new Vertex(verticesDB.getString(0),  //Vid
                        verticesDB.getString(verticesDB.getColumnIndex(DB_Helper.V_NAME)),//Vname
                        verticesDB.getDouble(verticesDB.getColumnIndex(DB_Helper.V_LAT)), //latitude
                        verticesDB.getDouble(verticesDB.getColumnIndex(DB_Helper.V_LONG)),//longtude
                        verticesDB.getString(verticesDB.getColumnIndex(DB_Helper.V_TYPE)),//type
                        verticesDB.getInt(verticesDB.getColumnIndex(DB_Helper.V_LANDMARK)));//Landmark
                break;
        }


        return Location;
    }

    public void remove() {
        currPath = null;
    }
}

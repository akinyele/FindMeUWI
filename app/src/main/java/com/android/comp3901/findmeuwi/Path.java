package com.android.comp3901.findmeuwi;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Dylan on 2/17/2017.
 */

public class Path {

    // Used in Path Finding method
    private List<Vertex> nodes;
    private List<Edge> edges;
    DB_Helper dbHelper;

    public Path(DB_Helper db){

        this.dbHelper = db;

    }


    /*
        Uses the dijkstra to generates the shortest path
     */
    public LinkedList<Vertex>   getPath(String src, String dest) {

        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        HashMap<String,Vertex> vertices = new HashMap<String,Vertex>();


        Cursor verticesDB = dbHelper.getVertices();
        Cursor edgesDB = dbHelper.getEdges();


        //creates a vertex object for the values in the database
        while (!verticesDB.isAfterLast()) {

            Vertex Locations = new Vertex(verticesDB.getString(0),
                    verticesDB.getString(1),
                    verticesDB.getDouble(2),
                    verticesDB.getDouble(3),
                    verticesDB.getString(4));
            nodes.add(Locations);
            vertices.put(verticesDB.getString(0), Locations);
            verticesDB.moveToNext();
        }

        //creates a edge for each value in the database
        while (!edgesDB.isAfterLast()){

            Edge lane = new Edge(edgesDB.getString(0) + " -> " + edgesDB.getString(0),
                    vertices.get(edgesDB.getString(0)),
                    vertices.get(edgesDB.getString(1)),
                    (int) edgesDB.getDouble(2));


            edges.add(lane);
            edgesDB.moveToNext();

        }


        Graph graph = new Graph(nodes,edges);
        DijkstraAlgorithm  dijkstra = new DijkstraAlgorithm(graph);

        dijkstra.execute(vertices.get(src));

        LinkedList<Vertex> path = dijkstra.getPath(vertices.get(dest));



        assertNotNull(path);
        assertTrue(path.size() > 0);

        return path;
    }

}

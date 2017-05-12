package com.android.comp3901.findmeuwi.utils;

/**
 * Created by Akinyele on 2/10/2017.
 * This represents the graph object with takes a list of vertex and edges.
 */

import com.android.comp3901.findmeuwi.locations.Vertex;
import com.android.comp3901.findmeuwi.services.Edge;

import java.util.List;

public class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }



}

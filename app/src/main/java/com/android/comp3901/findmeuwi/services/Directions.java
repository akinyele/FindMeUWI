package com.android.comp3901.findmeuwi.services;

import com.android.comp3901.findmeuwi.locations.Vertex;

/**
 * Created by Kyzer on 5/8/2017.
 *
 * This is intended to give relative location of a point according to the path
 * you are traversing.
 *
 *
 *
 */



public class Directions {
    public static final int x = 0;






    /**
     * This method takes your a point and the current path and tells the user
     * @return
     */

    public static String getDirection(Vertex point){

        return " ";
    }



    private double dotProduct( Coordinates start, Coordinates stop, Coordinates query){
        double d = 0.0;


        d= (stop.x - start.x)*(query.y - start.y) - (query.x - start.x)*(stop.y - start.y);

        return d;
    }




    private class Coordinates{
        double x;
        double y;


        public Coordinates(double x, double y){
            this.x = x;
            this.y = y;
        }
     }









}




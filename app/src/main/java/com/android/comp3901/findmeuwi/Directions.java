package com.android.comp3901.findmeuwi;

import android.location.Location;

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



    public Directions(){

    }


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


    private class Vector{

        private double[] start;
        private double[] stop;


        public Vector(double x1, double y1, double x2, double y2){
            this.start = new double[]{x1, x2};
            this.stop = new double[]{x2,x1};
        }


    }







}




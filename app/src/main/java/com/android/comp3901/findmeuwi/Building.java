package com.android.comp3901.findmeuwi;

/**
 * Created by Kyzer on 5/6/2017.
 */




public class Building extends Vertex {

    private String[] rooms;
    private  int floors;
    private  int known;
    private  double familiarity;





    public Building(String id, String name, double lat, double lng,String[] rooms, int floors, int known, double fam , int landmark) {
        super(id, name, lat, lng, "Building", landmark);

        this.rooms=rooms;
        this.floors = floors;
        this.known = known;
        this.familiarity = fam;


    }

    public boolean isKnown() {
        return known>0;
    }

    public String[] getRooms() {
        return rooms;
    }

    public void setRooms(String[] rooms) {
        this.rooms = rooms;
    }

    public int getFloors() {
       return this.floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public void setKnown(int known) {
        this.known = known;
    }

    public double getFamiliarity() {
       return this.familiarity;
    }

    public void setFamiliarity(double familiarity) {
        this.familiarity = familiarity;
    }
}

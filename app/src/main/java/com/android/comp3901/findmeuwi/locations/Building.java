package com.android.comp3901.findmeuwi.locations;

/**
 * Created by Kyzer on 5/6/2017.
 */




public class Building extends Place {

    private String[] rooms;
    private  int floors;





    public Building(String id, String name, double lat, double lng,String[] rooms, int floors, int known, double fam , int landmark) {
        super(id, name, lat, lng, "Building",known,fam, landmark,0);

        this.rooms=rooms;
        this.floors = floors;
    }

    public boolean isKnown() {
        return known>0;
    }

    public String[] getRooms() {
        return rooms;
    }

    public int getFloors() {
       return this.floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }


    @Override
    public String getInfo() {
        String info = super.getInfo()+"\r\n" +
                "Floors: "+getFloors()+ "\r\n" +
                "Rooms: "+ getRooms();
        return info;
    }

}

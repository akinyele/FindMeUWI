package com.android.comp3901.findmeuwi.locations;

/**
 * Created by Kyzer on 5/8/2017.
 */


//TODO add known and familiarity to place because because it would make sense for a place to be known or familiar

public class Place extends Vertex {


    Integer known;
    Double familiarity;




    public Place(String id, String name, double lat, double lng, String Type, Integer known, Double familiarity, int landMark) {
        super(id, name, lat, lng, Type, landMark);
        this.known= known;
        this.familiarity = familiarity;
    }

    public boolean isKnown(){
        return known>=1;
    }


    public int getKnown() {
        return known;
    }

    public void setKnown(Integer known) {
        this.known = known;
    }

    public double getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(Double familiarity) {
        this.familiarity = familiarity;
    }

    public String getInfo(){
       String info = "Name : "+getName() +" " +
               "ID :"+getId() ;

        return info;
    }

    public void updateDB(){

        return;
    }



    public  void updateFamiliarity(Double value){

        double fam = this.getFamiliarity();
        this.setFamiliarity( fam += value ) ;
    }












}

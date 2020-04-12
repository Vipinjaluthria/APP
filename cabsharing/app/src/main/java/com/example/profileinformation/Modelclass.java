package com.example.profileinformation;

public class Modelclass {
   private String Laguage;
    private String time;
    private String place;

      public  Modelclass()
      {

      }

    public Modelclass(String laguage, String time, String place) {
       this.Laguage = laguage;
        this.time = time;
        this.place = place;
    }

    public String getLaguage() {
        return Laguage;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }
}
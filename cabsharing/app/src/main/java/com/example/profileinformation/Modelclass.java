package com.example.profileinformation;

public class Modelclass {
    private String NAME;
    private String CONTACT;

    private String DRIVERNAME;
    private String ADDITIONALLUGGAGE;
    private String CARNAME;
    private String TIME;

    public String getTIME() {
        return TIME;
    }



    private String DATE;

    public Modelclass()
    {

    }
    public Modelclass(String name, String contact, String drivername, String additinllaguage, String cabname,String date,String time) {
        NAME = name;
        CONTACT = contact;
        DRIVERNAME = drivername;
        ADDITIONALLUGGAGE = additinllaguage;
        DATE=date;
        TIME=time;
        CARNAME = cabname;
    }
    public String getDATE() {
        return DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public String getCONTACT() {
        return CONTACT;
    }

    public String getDRIVERNAME() {
        return DRIVERNAME;
    }




    public String getADDITIONALLUGGAGE() {
        return ADDITIONALLUGGAGE;
    }

    public String getCARNAME() {
        return CARNAME;
    }
}

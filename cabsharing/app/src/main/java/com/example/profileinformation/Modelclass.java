package com.example.profileinformation;

public class Modelclass {
    private String NAME;
    private String CONTACT;

    private String DRIVERNAME;
    private String ADDITIONALLAGUAGE;
    private String CARNAME;

    public Modelclass()
    {

    }
    public Modelclass(String name, String contact, String drivername, String additinllaguage, String cabname) {
        NAME = name;
        CONTACT = contact;
        DRIVERNAME = drivername;
        ADDITIONALLAGUAGE = additinllaguage;

        CARNAME = cabname;
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




    public String getADDITIONALLAGUAGE() {
        return ADDITIONALLAGUAGE;
    }

    public String getCARNAME() {
        return CARNAME;
    }
}

package com.example.profileinformation;

public class Modelclass {
    private String NAME;
    private String CONTACT;

    private String DRIVERNAME;
    private String ADDITIONALLUGGAGE;
    private String CARNAME;
    private String TIME;
    private  String CARCAPACITY;


    private String DATE;
    private  String GENDER;
    private String CARNUMBER;


    public String getTIME() {
        return TIME;
    }



    public Modelclass()
    {

    }
    public Modelclass(String carnumber,String gendre,String name, String contact, String drivername, String additionalluggage, String carname,String date,String time,String carcapacity) {
       this. NAME = name;
        this.CONTACT = contact;
       this. DRIVERNAME = drivername;
      this.  GENDER=gendre;
      this.  ADDITIONALLUGGAGE = additionalluggage;
       this. DATE=date;
      this.  TIME=time;
       this. CARCAPACITY = carcapacity;
       this. CARNAME = carname;
       this. CARNUMBER=carnumber;
    }
    public String getCARCAPACITY() {
        return CARCAPACITY;
    }



    public String getDATE() {
        return DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public String getCARNUMBER() {
        return CARNUMBER;
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

    public String getGENDER() {
        return GENDER;
    }
}

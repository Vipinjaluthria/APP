package com.example.profileinformation;

public  class Listviews  {
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
    public Listviews()
    {

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

     public String getTIME() {
         return TIME;
     }

     public String getCARCAPACITY() {
         return CARCAPACITY;
     }

     public String getDATE() {
         return DATE;
     }

     public String getGENDER() {
         return GENDER;
     }

     public String getCARNUMBER() {
         return CARNUMBER;
     }

     public Listviews(String NAME, String CONTACT, String DRIVERNAME, String ADDITIONALLUGGAGE, String CARNAME, String TIME, String CARCAPACITY, String DATE, String GENDER, String CARNUMBER) {
         this.NAME = NAME;
         this.CONTACT = CONTACT;
         this.DRIVERNAME = DRIVERNAME;
         this.ADDITIONALLUGGAGE = ADDITIONALLUGGAGE;
         this.CARNAME = CARNAME;
         this.TIME = TIME;
         this.CARCAPACITY = CARCAPACITY;
         this.DATE = DATE;
         this.GENDER = GENDER;
         this.CARNUMBER = CARNUMBER;
     }
 }

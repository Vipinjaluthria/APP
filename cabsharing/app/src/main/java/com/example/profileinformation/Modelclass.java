package com.example.profileinformation;


import android.os.Parcel;
import android.os.Parcelable;

public class Modelclass implements Parcelable {
    private String NAME;
    private String PHONE;


    private String DRIVERNAME;
    private String ADDITIONALLUGGAGE;
    private String CARNAME;
    private String TIME;
    private  String CARCAPACITY;


    private String DATE;
    private  String GENDER;
    private String CARNUMBER;


    protected Modelclass(Parcel in) {
        NAME = in.readString();
        PHONE = in.readString();
        DRIVERNAME = in.readString();
        ADDITIONALLUGGAGE = in.readString();
        CARNAME = in.readString();
        TIME = in.readString();
        CARCAPACITY = in.readString();
        DATE = in.readString();
        GENDER = in.readString();
        CARNUMBER = in.readString();
    }

    public static final Creator<Modelclass> CREATOR = new Creator<Modelclass>() {
        @Override
        public Modelclass createFromParcel(Parcel in) {
            return new Modelclass(in);
        }

        @Override
        public Modelclass[] newArray(int size) {
            return new Modelclass[size];
        }
    };

    public String getTIME() {
        return TIME;
    }



    public Modelclass()
    {

    }
    public Modelclass(String contact,String carnumber,String gendre,String name,  String drivername, String additionalluggage, String carname,String date,String time,String carcapacity) {
       this. NAME = name;
       this.PHONE=contact;

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

    public String getPHONE() {
        return PHONE;
    }

    public String getCARNUMBER() {
        return CARNUMBER;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NAME);
        dest.writeString(PHONE);
        dest.writeString(DRIVERNAME);
        dest.writeString(ADDITIONALLUGGAGE);
        dest.writeString(CARNAME);
        dest.writeString(TIME);
        dest.writeString(CARCAPACITY);
        dest.writeString(DATE);
        dest.writeString(GENDER);
        dest.writeString(CARNUMBER);
    }
}

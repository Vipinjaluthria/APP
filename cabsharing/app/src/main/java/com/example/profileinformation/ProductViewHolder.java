package com.example.profileinformation;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ProductViewHolder extends RecyclerView.ViewHolder {
    private View view;

    ProductViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    void setNAME(String NAME) {
        TextView textView = view.findViewById(R.id.textView5);
        textView.setText(NAME);
    }

    void setCONTACT(String CONTACT) {
        TextView textView = view.findViewById(R.id.textView6);
        textView.setText(CONTACT);


    }
    void setCARNAME(String CARNAME) {
        TextView textView = view.findViewById(R.id.textView7);
        textView.setText(CARNAME);
    }

    void setCARCAPACITY(String CAPACITY) {
        TextView textView = view.findViewById(R.id.textView13);
        textView.setText(CAPACITY);
    }
    void setDRIVERNAME(String DRIVERNAME)
    {
        TextView textView = view.findViewById(R.id.textView8);
        textView.setText(DRIVERNAME);
    }
    void setGENDER(String GENDER)
    {
        TextView textView = view.findViewById(R.id.textView14);
        textView.setText(GENDER);
    }
    void setADDITIONALLUGGAGE(String ADDITIONALLUGGAGE)
    {
        TextView textView=view.findViewById(R.id.textView9);
        textView.setText(ADDITIONALLUGGAGE);
    }
    void setDATE(String DATE)
    {
        TextView textView=view.findViewById(R.id.textView15);
        textView.setText(DATE);
    }
    void setCARNUMBER(String CARNUMBER)
    {
        TextView textView=view.findViewById(R.id.textView10);
        textView.setText(CARNUMBER);
    }
    void setTIME(String TIME)
    {
        TextView textView=view.findViewById(R.id.textView11);
        textView.setText(TIME);
    }
}
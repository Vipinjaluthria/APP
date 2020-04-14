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
    void setDRIVERNAME(String DRIVERNAME)
    {
        TextView textView = view.findViewById(R.id.textView8);
        textView.setText(DRIVERNAME);
    }
    void setADDITIONALLAGUAGE(String ADDITIONALLAGUAGE)
    {
        TextView textView=view.findViewById(R.id.textView9);
        textView.setText(ADDITIONALLAGUAGE);
    }
}
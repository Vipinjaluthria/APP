package com.example.profileinformation;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

class ProductViewHolder extends RecyclerView.ViewHolder{
    private View view;
    private FirebaseAuth firebaseAuth;
    private recyclerviewclicklistner recyclerviewclicklistner;



    ProductViewHolder(View itemView,recyclerviewclicklistner recyclerviewclicklistner) {
        super(itemView);
        firebaseAuth=FirebaseAuth.getInstance();
        view = itemView;
        this.recyclerviewclicklistner = recyclerviewclicklistner;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerviewclicklistner.click(getAdapterPosition());
            }
        });

    }


    void setNAME(String NAME) {
        TextView name = view.findViewById(R.id.textView5);
        name.setText(NAME);
    }


    void setCARNAME(String CARNAME) {
        TextView carname = view.findViewById(R.id.textView7);
        carname.setText(CARNAME);
    }

    void setCARCAPACITY(String CAPACITY) {
        TextView carcapacity = view.findViewById(R.id.textView13);
        carcapacity.setText(CAPACITY);
    }

    void setDRIVERNAME(String DRIVERNAME) {
        TextView drivername = view.findViewById(R.id.textView8);
        drivername.setText(DRIVERNAME);
    }

    void setGENDER(String GENDER) {
        TextView gender = view.findViewById(R.id.textView14);
        gender.setText(GENDER);
    }

    void setADDITIONALLUGGAGE(String ADDITIONALLUGGAGE) {
        TextView luggage = view.findViewById(R.id.textView9);
        luggage.setText(ADDITIONALLUGGAGE);
    }

    void setDATE(String DATE) {
        TextView date = view.findViewById(R.id.textView15);
        date.setText(DATE);
    }

    void setCARNUMBER(String CARNUMBER) {
        TextView carnumber = view.findViewById(R.id.textView10);
        carnumber.setText(CARNUMBER);
    }

    void setTIME(String TIME) {
        TextView time = view.findViewById(R.id.textView11);
        time.setText(TIME);
    }

}
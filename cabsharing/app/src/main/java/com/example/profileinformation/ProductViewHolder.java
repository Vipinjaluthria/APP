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

    void setlaguage(String Laguage) {
        TextView textView = view.findViewById(R.id.textView5);
        textView.setText(Laguage);
    }

    void settime(String time) {
        TextView textView = view.findViewById(R.id.textView6);
        textView.setText(time);


    }

    void setplace(String place) {
        TextView textView = view.findViewById(R.id.textView7);
        textView.setText(place);
    }
}
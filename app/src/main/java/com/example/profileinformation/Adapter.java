package com.example.profileinformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ProductViewHolder>  {
    private List<Modelclass> modelclass;
    private recyclerviewclicklistner mrecyclerviewclicklistner;


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems, parent, false);

        return new ProductViewHolder(view, mrecyclerviewclicklistner);
    }

    Adapter(List<Modelclass> modelclass, recyclerviewclicklistner recyclerviewclicklistner) {
        this.modelclass = modelclass;
        this.mrecyclerviewclicklistner = recyclerviewclicklistner;
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        String Name = modelclass.get(position).getNAME();
        String Carname = modelclass.get(position).getCARNAME();
        String carnumber = modelclass.get(position).getCARNUMBER();
        String carcapacity = modelclass.get(position).getCARCAPACITY();
        String contact=modelclass.get(position).getPHONE();

        String gender = modelclass.get(position).getGENDER();
        String additionalluggage = modelclass.get(position).getADDITIONALLUGGAGE();
        String time = modelclass.get(position).getTIME();
        String date = modelclass.get(position).getDATE();
        String drivername = modelclass.get(position).getDRIVERNAME();
        holder.setADDITIONALLUGGAGE(additionalluggage);
        holder.setCARCAPACITY(carcapacity);
        holder.setCARNAME(Carname);
        holder.setCARNUMBER(carnumber);

        holder.setDATE(date);
        holder.setDRIVERNAME(drivername);
        holder.setGENDER(gender);
        holder.setNAME(Name);
        holder.setTIME(time);


    }

    @Override
    public int getItemCount() {
        return modelclass.size();
    }


}











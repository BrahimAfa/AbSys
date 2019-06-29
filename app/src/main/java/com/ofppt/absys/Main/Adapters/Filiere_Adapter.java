package com.ofppt.absys.Main.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.activeandroid.util.Log;
import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.Main.UI.Filiere_Menu;
import com.ofppt.absys.Main.UI.Groups_Menu;
import com.ofppt.absys.Main.UI.SplashScreen;
import com.ofppt.absys.R;

import java.util.List;



public class Filiere_Adapter extends RecyclerView.Adapter<Filiere_Adapter.MyViewHolder> {

    private List<FILIERES> FilereList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,code;
        public LinearLayout linearLayout;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.Fil_name);
            code = view.findViewById(R.id.Fil_code);
            linearLayout = view.findViewById(R.id.linear);
        }
    }


    public Filiere_Adapter(List<FILIERES> FilereList) {
        this.FilereList = FilereList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filiere_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final FILIERES ff = FilereList.get(position);
        holder.code.setText(ff._CodeFiliere);
        holder.name.setText(ff._Filiere);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Groups_Menu.class);
                intent.putExtra("codef",ff._CodeFiliere);
                intent.putExtra("nomf",ff._Filiere);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FilereList.size();
    }
}
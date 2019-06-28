package com.ofppt.absys.Main.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.R;

import java.util.List;

public class Filiere_Adapter extends RecyclerView.Adapter<Filiere_Adapter.MyViewHolder> {

    private List<FILIERES> FilereList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,code;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.Fil_name);
            code = view.findViewById(R.id.Fil_code);

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FILIERES ff = FilereList.get(position);
        holder.code.setText(ff._CodeFiliere);
        holder.name.setText(ff._Filiere);
    }

    @Override
    public int getItemCount() {
        return FilereList.size();
    }
}
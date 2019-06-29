package com.ofppt.absys.Main.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.Main.UI.Absence;
import com.ofppt.absys.R;

import java.util.List;

public class Group_Adapter extends RecyclerView.Adapter<Group_Adapter.MyViewHolder> {

    private List<GROUPES> FilereList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView code;
        public LinearLayout linearLayout;
        public MyViewHolder(View view) {
            super(view);
            code = view.findViewById(R.id.Grp_code);
            linearLayout = view.findViewById(R.id.linear2);

        }
    }


    public Group_Adapter(List<GROUPES> FilereList) {
        this.FilereList = FilereList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GROUPES ff = FilereList.get(position);
        holder.code.setText(ff._CodeGroupe);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Absence.class);
                intent.putExtra("codeg",ff._CodeGroupe);
                intent.putExtra("Filiere",ff._filieres._CodeFiliere);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FilereList.size();
    }
}
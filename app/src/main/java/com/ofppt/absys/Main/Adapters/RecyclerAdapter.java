package com.ofppt.absys.Main.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ofppt.absys.Main.Constants.Constants;
import com.ofppt.absys.Main.Interfaces.IOnChecked;
import com.ofppt.absys.Main.Models.STAGIAIRES;
import com.ofppt.absys.R;

import java.util.Calendar;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerviewAdapter";


        private List<STAGIAIRES> STAGIAIRES_LIST;
        private  Context ctx;
        private IOnChecked CheckedR;
        private final boolean[] Seance1State;
        private final boolean[] Seance2State;

    public RecyclerAdapter(List<STAGIAIRES> Stagiaires_List, Context ctx, IOnChecked CheckedResult) {
        STAGIAIRES_LIST = Stagiaires_List;
        this.ctx = ctx;
        this.CheckedR = CheckedResult;
        Seance1State= new boolean[STAGIAIRES_LIST.size()];
        Seance2State= new boolean[STAGIAIRES_LIST.size()];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder ;
        View view;
        //i Presntes The View Type
        //if I == TypeList (1) means that we passed the first element
        if (i==Constants.TYPE_LIST)
        {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_view,viewGroup,false);
             holder  = new ViewHolder( view,CheckedR,ctx,i);
            return holder;
        }
        //if I == TypeHead (0) means that we are  the first element Header
        else if(i==Constants.TYPE_HEAD)
        {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_heade,viewGroup,false);
             holder  = new ViewHolder( view,CheckedR,ctx,i);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (viewHolder._ViewType==Constants.TYPE_LIST)
        {
            viewHolder.Name.setText(String.format("%s %s", STAGIAIRES_LIST.get(i - 1)._Nom, STAGIAIRES_LIST.get(i - 1)._Prenom));
            viewHolder.SC1.setChecked(false);
            viewHolder.SC2.setChecked(false);

            if (Seance1State[i-1])
            {
                //Toast.makeText(this.ctx,"Check S1 in Viewholder box clicked : "+ i,Toast.LENGTH_SHORT).show();
                viewHolder.SC1.setChecked(true);
            }
            else {
                viewHolder.SC1.setChecked(false);
                //Toast.makeText(this.ctx,"UnCheck S1 in Viewholder box clicked : "+ i,Toast.LENGTH_SHORT).show();

            }
            if (Seance2State[i-1])
            {
                viewHolder.SC2.setChecked(true);

            }
            else {
                viewHolder.SC2.setChecked(false);
            }
            Log.d(TAG, "onBindViewHolder: List");
        }
        else if (viewHolder._ViewType==Constants.TYPE_HEAD)
        {
                    //in case if AM_PM variable is not initialized
                   if (Constants.AM_PM==-1)
                        {
                            Calendar cal = Calendar.getInstance();
                            Constants.AM_PM = cal.get(Calendar.AM_PM);
                        }

                   //if AM_PM == 0 means Afternoon
                if ( Constants.AM_PM==0)
                {
                    viewHolder.txtS1.setText(Constants.Seance1);
                    viewHolder.txtS2.setText(Constants.Seance2);

                }

                //if AM_PM == 1 means Morning
                else if (Constants.AM_PM==1)
                {
                    viewHolder.txtS1.setText(Constants.Seance3);
                    viewHolder.txtS2.setText(Constants.Seance4);
                }
        }
    }

    // List.size+1 is for the Header to appear Properly
    @Override
    public int getItemCount() {
        return STAGIAIRES_LIST.size()+1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
        TextView Name,txtS1,txtS2;
        CheckBox SC1,SC2;
        IOnChecked CheckedR;
        Context _ctx;
        int _ViewType;
        ViewHolder(View itemView, IOnChecked CheckedResult, Context ctx, int ViewType) {
            super(itemView);
            _ctx = ctx;
            CheckedR=CheckedResult;
            //itemView.setOnClickListener(this);
            //if we are binding List we are Initializing List Components
            if (ViewType==Constants.TYPE_LIST)
            {
                Name = itemView.findViewById(R.id.Nom);
                SC1 = itemView.findViewById(R.id.CheckSC1);
                SC2 = itemView.findViewById(R.id.CheckSC2);
                SC1.setOnCheckedChangeListener(this);
                SC2.setOnCheckedChangeListener (this);
                _ViewType = ViewType;
            }
            //if we are binding the Header we are Initializing Header Components

            else if (ViewType==Constants.TYPE_HEAD)
            {
                txtS1 = itemView.findViewById(R.id.txtS1);
                txtS2 = itemView.findViewById(R.id.txtS2);

                _ViewType = ViewType;

            }
        }
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            CheckedR.OnChecked(getAdapterPosition()-1);
                   switch(compoundButton.getId())
                   {
                       case R.id.CheckSC1:
                           if (compoundButton.isChecked())
                           {
                               if (!Seance1State[getAdapterPosition()-1]){
                                     Seance1State[getAdapterPosition()-1] = true;
                                   Constants.CheckedStudentSC1.add(STAGIAIRES_LIST.get(getAdapterPosition()-1));
                                   Toast.makeText(_ctx,"Check S1 box clicked : "+ getAdapterPosition(),Toast.LENGTH_SHORT).show();
                               }
                           }else {
                               if (Seance1State[getAdapterPosition()-1]){
                                    Seance1State[getAdapterPosition()-1] = false;
                                    Toast.makeText(_ctx,"UN Check S1 box clicked : "+ getAdapterPosition(),Toast.LENGTH_SHORT).show();
                                    Constants.CheckedStudentSC1.remove(STAGIAIRES_LIST.get(getAdapterPosition()-1));
                           }}
                           break;
                       case R.id.CheckSC2:
                           if (compoundButton.isChecked())
                           {
                               if (!Seance2State[getAdapterPosition()-1]){
                               Seance2State[getAdapterPosition()-1] = true;
                               Constants.CheckedStudentSC2.add(STAGIAIRES_LIST.get(getAdapterPosition()-1));
                               Toast.makeText(_ctx,"Check S2 box clicked : "+ getAdapterPosition(),Toast.LENGTH_SHORT).show();
                           }}else {
                               if (Seance2State[getAdapterPosition()-1]){
                               Seance2State[getAdapterPosition()-1] = false;
                               Toast.makeText(_ctx,"UN Check S2 box clicked : "+ getAdapterPosition(),Toast.LENGTH_SHORT).show();
                               Constants.CheckedStudentSC2.remove(STAGIAIRES_LIST.get(getAdapterPosition()-1));
                           }}
                           break;
                       default:
                           break;
                   }
        }
    }
    //this Overrided Function  determens in which Position we Are position=0 means Header
    //position>0 means List
    @Override
    public int getItemViewType(int position) {
        if (position==0)
        {
            return Constants.TYPE_HEAD;
        }
        return Constants.TYPE_LIST;
    }
}


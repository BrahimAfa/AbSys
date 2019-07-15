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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerviewAdapter";

        private int TYPE_FOOTER;
        private List<STAGIAIRES> STAGIAIRES_LIST;
        private  Context ctx;
        private IOnChecked CheckedR;
        private final boolean[] Seance1State;
        private final boolean[] Seance2State;
        private boolean CheckState;
        private String A_Seance="S";


    public RecyclerAdapter(List<STAGIAIRES> Stagiaires_List, Context ctx, IOnChecked CheckedResult) {
        STAGIAIRES_LIST = Stagiaires_List;
        this.ctx = ctx;
        this.CheckedR = CheckedResult;
        Seance1State= new boolean[STAGIAIRES_LIST.size()];
        Seance2State= new boolean[STAGIAIRES_LIST.size()];
        TYPE_FOOTER = STAGIAIRES_LIST.size()+1;

    }
    public RecyclerAdapter(List<STAGIAIRES> Stagiaires_List, Context ctx, IOnChecked CheckedResult,String Seance ,boolean CheckStateSC) {
        STAGIAIRES_LIST = Stagiaires_List;
        this.ctx = ctx;
        this.CheckedR = CheckedResult;
        Seance1State= new boolean[STAGIAIRES_LIST.size()];
        Seance2State= new boolean[STAGIAIRES_LIST.size()];
        TYPE_FOOTER = STAGIAIRES_LIST.size()+1;
        A_Seance=Seance;
        CheckState = CheckStateSC;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        //i Presntes The View Type
        //if I == TypeList (1) means that we passed the first element
        if (i==Constants.TYPE_LIST)
        {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_view,viewGroup,false);
            ViewHolder   holder  = new ViewHolder( view,CheckedR,ctx,i);
            return holder;
        }
        //if I == TypeHead (0) means that we are  the first element Header
        else if(i==Constants.TYPE_HEAD)
        {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_heade,viewGroup,false);
           ViewHolderHeader  holder  = new ViewHolderHeader(view,CheckedR,ctx);
            return holder;
        }
        else if(i==TYPE_FOOTER)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.absence_recycler_footer,viewGroup,false);
            ViewHolderFooter   holderFotter  = new ViewHolderFooter(view,CheckedR,ctx);
            return  holderFotter;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder)
        {
            ViewHolder HolderList = (ViewHolder) viewHolder;

            HolderList.Name.setText(String.format("%s %s", STAGIAIRES_LIST.get(i - 1)._Nom, STAGIAIRES_LIST.get(i - 1)._Prenom));
            HolderList.SC1.setChecked(false);
            HolderList.SC2.setChecked(false);
            if (Constants.AucunSc1State) {
                HolderList.SC1.setEnabled(!Constants.AucunSc1State);
            }
            if (Constants.AucunSc2State) {
                HolderList.SC2.setEnabled(!Constants.AucunSc2State);
            }
            if (Constants.AucunSc1State & Constants.AucunSc2State) {
                HolderList.SC2.setEnabled(false);
                HolderList.SC1.setEnabled(false);


            }
            HolderList.Comulee.setText(String.valueOf(STAGIAIRES_LIST.get(i-1)._Absence_Cumulee));

            if (Seance1State[i-1])
            {
                //Toast.makeText(this.ctx,"Check S1 in Viewholder box clicked : "+ i,Toast.LENGTH_SHORT).show();
                HolderList.SC1.setChecked(true);
            }
            else {
                HolderList.SC1.setChecked(false);
                //Toast.makeText(this.ctx,"UnCheck S1 in Viewholder box clicked : "+ i,Toast.LENGTH_SHORT).show();

            }
            if (Seance2State[i-1])
            {
                HolderList.SC2.setChecked(true);

            }
            else {
                HolderList.SC2.setChecked(false);
            }
            Log.d(TAG, "onBindViewHolder: List");
        }
        else if (viewHolder instanceof ViewHolderHeader)
        {
            ViewHolderHeader HeaderHolder = (ViewHolderHeader) viewHolder;
                    //in case if AM_PM variable is not initialized
                   if (Constants.AM_PM==-1)
                        {
                            Calendar cal = Calendar.getInstance();
                            Constants.AM_PM = cal.get(Calendar.AM_PM);
                        }

                   //if AM_PM == 0 means Afternoon
                if ( Constants.AM_PM==0)
                {
                    HeaderHolder.txtS1.setText(Constants.Seance1);
                    HeaderHolder.txtS2.setText(Constants.Seance2);
                }

                //if AM_PM == 1 means Morning
                else if (Constants.AM_PM==1)
                {
                    HeaderHolder.txtS1.setText(Constants.Seance3);
                    HeaderHolder.txtS2.setText(Constants.Seance4);
                }
        }
        else if (viewHolder instanceof ViewHolderFooter) {
            ViewHolderFooter HolderFotter = (ViewHolderFooter) viewHolder;
            if (! A_Seance.equals("S"))
                HolderFotter.ASC1.setChecked(Constants.AucunSc1State);
                HolderFotter.ASC2.setChecked(Constants.AucunSc2State);
        }
    }

    // List.size+1 is for the Header to appear Properly
    @Override
    public int getItemCount() {
        return STAGIAIRES_LIST.size()+2;
    }
    @Override
    public int getItemViewType(int position) {
        if (position==0)
        {
            return Constants.TYPE_HEAD;
        }
        else if(position==STAGIAIRES_LIST.size()+1)
        {
            return TYPE_FOOTER;
        }
        return Constants.TYPE_LIST;
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder{
        TextView txtS1,txtS2;
        IOnChecked CheckedR;
        Context _ctx;

        ViewHolderHeader(View itemView, IOnChecked CheckedResult, Context ctx) {
            super(itemView);
            _ctx = ctx;
            CheckedR=CheckedResult;
            //itemView.setOnClickListener(this);
            //if we are binding List we are Initializing List Components
            //if we are binding the Header we are Initializing Header Components

                txtS1 = itemView.findViewById(R.id.txtS1);
                txtS2 = itemView.findViewById(R.id.txtS2);

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        TextView Comulee,Name;
        CheckBox SC1,SC2;
        IOnChecked CheckedR;
        Context _ctx;
        ViewHolder(View itemView, IOnChecked CheckedResult, Context ctx, int ViewType) {
        super(itemView);
        _ctx = ctx;
        CheckedR=CheckedResult;
        //itemView.setOnClickListener(this);
        //if we are binding List we are Initializing List Components
        if (ViewType==Constants.TYPE_LIST)
        {
            Comulee = itemView.findViewById(R.id.AbsencComulee);
            Name = itemView.findViewById(R.id.Nom);
            SC1 = itemView.findViewById(R.id.CheckSC1);
            SC2 = itemView.findViewById(R.id.CheckSC2);
            SC1.setOnCheckedChangeListener(this);
            SC2.setOnCheckedChangeListener (this);
        }

    }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
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


        //this Overrided Function  determens in which Position we Are position=0 means Header
    //position>0 means List

}
    public class ViewHolderFooter extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        CheckBox ASC1,ASC2;
        IOnChecked CheckedR;
        Context _ctx;
        ViewHolderFooter(View itemView, IOnChecked CheckedResult, Context ctx) {
            super(itemView);
            _ctx = ctx;
            CheckedR=CheckedResult;
            //itemView.setOnClickListener(this);
            //if we are binding List we are Initializing List Components
            //if we are binding the Header we are Initializing Header Components
            ASC1 = itemView.findViewById(R.id.AucunSC1);
            ASC2 = itemView.findViewById(R.id.AucunSC2);
            ASC1.setOnCheckedChangeListener(this);
            ASC2.setOnCheckedChangeListener(this);
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch(buttonView.getId())
            {
                case R.id.AucunSC1:
                    if (buttonView.isChecked())
                    {
                        if ( !Constants.AucunSc1State){
                            CheckedR.OnAucunCheckedSC1(buttonView.isChecked());
                            Constants.AucunSc1State = buttonView.isChecked();
                        }
                    }
                    else {
                        if ( Constants.AucunSc1State){
                            CheckedR.OnAucunCheckedSC1(buttonView.isChecked());
                            Constants.AucunSc1State = buttonView.isChecked();
                        }
                    }

                    break;
                case R.id.AucunSC2:
                    if (buttonView.isChecked())
                    {
                        if ( !Constants.AucunSc2State){
                            CheckedR.OnAucunCheckedSC2(buttonView.isChecked());
                            Constants.AucunSc2State = buttonView.isChecked();
                        }
                    }
                    else {
                        if ( Constants.AucunSc2State){
                            CheckedR.OnAucunCheckedSC2(buttonView.isChecked());
                            Constants.AucunSc2State = buttonView.isChecked();
                        }
                    }

                    break;
                default:
                    break;
            }

        }
    }

}


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
import com.ofppt.absys.Main.Utils.HelperUtils;
import com.ofppt.absys.R;

import java.util.Calendar;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerviewAdapter";

    private int TYPE_FOOTER;
    private List<STAGIAIRES> STAGIAIRES_LIST;
    private  Context ctx;
    //this interface help me to track the selected Checkbox (interface as a parent and child as the one who implemented it)
    private IOnChecked CheckedR;
    //to track the selected Student (recyclerView doesn't remember the last changes that made to it's components)
    private final boolean[] Seance1State;
    private final boolean[] Seance2State;

    private String A_Seance="S";

    public RecyclerAdapter(List<STAGIAIRES> Stagiaires_List, Context ctx, IOnChecked CheckedResult) {
        STAGIAIRES_LIST = Stagiaires_List;
        this.ctx = ctx;
        this.CheckedR = CheckedResult;
        Seance1State= new boolean[STAGIAIRES_LIST.size()];
        Seance2State= new boolean[STAGIAIRES_LIST.size()];
        TYPE_FOOTER = STAGIAIRES_LIST.size()+1;

    }
    //this constructor is redundant
    //A_seance seance Parameter has no effect on this
    //this constructor is here just to update the recycler view if the Footer is changed (aucunCheckBox)
    // which triggers this adapter to refresh and set the column (S1 or S2) of the selected box (in the footer) to update
    public RecyclerAdapter(List<STAGIAIRES> Stagiaires_List, Context ctx, IOnChecked CheckedResult,String Seance ) {
        STAGIAIRES_LIST = Stagiaires_List;
        this.ctx = ctx;
        this.CheckedR = CheckedResult;
        Seance1State= new boolean[STAGIAIRES_LIST.size()];
        Seance2State= new boolean[STAGIAIRES_LIST.size()];
        TYPE_FOOTER = STAGIAIRES_LIST.size()+1;
        //i'll figure something later -_- for this useless Constructor
        A_Seance=Seance;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        //i represent The View Type
        //if I == TypeList (1) means that we are in the first element in the list
        if (i==Constants.TYPE_LIST)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_view,viewGroup,false);
            return new ViewHolder( view,ctx);
        }
        //if I == TypeHead (0) means that we are in the Header of this RecyclerView
        else if(i==Constants.TYPE_HEAD)
        {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_heade,viewGroup,false);
            return new ViewHolderHeader(view);
        }
        //if I == TYPE_FOOTER means that we are in Footer of this RV
        //We didn't put it in Constant Class cuz it changes based on Student list
        else if(i==TYPE_FOOTER)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.absence_recycler_footer,viewGroup,false);
            return new ViewHolderFooter(view,CheckedR);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //there is Three types of ViewHolder (Header,Footer, and main ViewHolder to show Student list)
        //parameter i is the current position in RV
        if (viewHolder instanceof ViewHolder) {
            ViewHolder HolderList = (ViewHolder) viewHolder;
            HolderList.Name.setText(String.format("%s %s", STAGIAIRES_LIST.get(i - 1)._Nom, STAGIAIRES_LIST.get(i - 1)._Prenom));
            HolderList.Comulee.setText(String.valueOf(STAGIAIRES_LIST.get(i - 1)._Absence_Cumulee));
            //this helps to set the checkboxes to its previous state.
            HolderList.SC1.setChecked(Seance1State[i - 1]);
            HolderList.SC2.setChecked(Seance2State[i - 1]);
            //this disables the column of checkboxes based on the selected box in the Footer
            // (check the Footer ViewHolder Class below for more information)
            HolderList.SC1.setEnabled(!Constants.AucunSc1State);
            HolderList.SC2.setEnabled(!Constants.AucunSc2State);
            Log.d(TAG, "onBindViewHolder: List on Position : "+i);
        }
        else if (viewHolder instanceof ViewHolderHeader) {
            ViewHolderHeader HeaderHolder = (ViewHolderHeader) viewHolder;
            //in case if AM_PM variable is not initialized for any reason
            if (Constants.AM_PM==-1) {
                HelperUtils.initPM_AM();
            }
            //if AM_PM == 0 means Afternoon
            if ( Constants.AM_PM==0) {
                HeaderHolder.txtS1.setText(Constants.Seance1);
                HeaderHolder.txtS2.setText(Constants.Seance2);
            }
            //if AM_PM == 1 means Morning
            else if (Constants.AM_PM==1) {
                HeaderHolder.txtS1.setText(Constants.Seance3);
                HeaderHolder.txtS2.setText(Constants.Seance4);
            }
        }
        else if (viewHolder instanceof ViewHolderFooter) {
            //set the footer checkboxes to their current state.
            ViewHolderFooter HolderFotter = (ViewHolderFooter) viewHolder;
            HolderFotter.ASC1.setChecked(Constants.AucunSc1State);
            HolderFotter.ASC2.setChecked(Constants.AucunSc2State);
        }
    }

    @Override
    public int getItemCount() {
        // List.size+2 is for the Header and Footer to appear Properly
        return STAGIAIRES_LIST.size()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return Constants.TYPE_HEAD;
        }
        else if(position==STAGIAIRES_LIST.size()+1) {
            return TYPE_FOOTER;
        }
        return Constants.TYPE_LIST;
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder{
        TextView  txtS1,txtS2;
        ViewHolderHeader(View itemView) {
            super(itemView);
            txtS1 = itemView.findViewById(R.id.txtS1);
            txtS2 = itemView.findViewById(R.id.txtS2);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        TextView Comulee, Name;
        CheckBox SC1, SC2;
        //this context is used only for toasts
        Context _ctx;
        ViewHolder(View itemView, Context ctx) {
            super(itemView);
            _ctx = ctx;
            Comulee = itemView.findViewById(R.id.AbsencComulee);
            Name = itemView.findViewById(R.id.Nom);
            SC1 = itemView.findViewById(R.id.CheckSC1);
            SC2 = itemView.findViewById(R.id.CheckSC2);
            SC1.setOnCheckedChangeListener(this);
            SC2.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            switch (compoundButton.getId()) {
                case R.id.CheckSC1:
                    //this "if statement" makes sure that we add the selected Student or remove it in the right time
                    // (checked : Add,unchecked: Remove)
                    if (compoundButton.isChecked()) {
                        //the current position checkbox is checked.(means it's true)
                        //this 'if statement' gets the previous state of this checkbox at this position
                        //(it should be false) and in case of it's been true for whatever reason don't do nothing
                        if (! Seance1State[getAdapterPosition() - 1]) {
                            //set this position to true cuz it's been checked
                            //(this way the adapter knows about the previous state of checkboxes while binding)
                            Seance1State[getAdapterPosition() - 1] = true;
                            //then adds this (current) student to the absence list.
                            Constants.CheckedStudentSC1.add(STAGIAIRES_LIST.get(getAdapterPosition() - 1));
                            Toast.makeText(_ctx, "Check S1 box clicked : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //the current position checkbox is unchecked.(means it's false)
                        //and this if statement does the same thing as the previous one.
                        if (Seance1State[getAdapterPosition() - 1]) {
                            Seance1State[getAdapterPosition() - 1] = false;
                            //here instead of adding the current student we remove it from the list
                            Constants.CheckedStudentSC1.remove(STAGIAIRES_LIST.get(getAdapterPosition() - 1));
                            Toast.makeText(_ctx, "UN Check S1 box clicked : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.CheckSC2:
                    //the same thing as the first case but only for the second checkbox (SC2)
                    if (compoundButton.isChecked()) {
                        if (! Seance2State[getAdapterPosition() - 1]) {
                            Seance2State[getAdapterPosition() - 1] = true;
                            Constants.CheckedStudentSC2.add(STAGIAIRES_LIST.get(getAdapterPosition() - 1));
                            Toast.makeText(_ctx, "Check S2 box clicked : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (Seance2State[getAdapterPosition() - 1]) {
                            Seance2State[getAdapterPosition() - 1] = false;
                            Toast.makeText(_ctx, "UN Check S2 box clicked : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            Constants.CheckedStudentSC2.remove(STAGIAIRES_LIST.get(getAdapterPosition() - 1));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
    public class ViewHolderFooter extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        CheckBox ASC1,ASC2;
        IOnChecked CheckedR;
        ViewHolderFooter(View itemView, IOnChecked CheckedResult) {
            super(itemView);
            CheckedR=CheckedResult;
            ASC1 = itemView.findViewById(R.id.AucunSC1);
            ASC2 = itemView.findViewById(R.id.AucunSC2);
            //we this we are saying to use the OnCheckedChangeListener that is defined in this class
            // (cuz it is implemented the OnCheckedChangeListener Interface)
            ASC1.setOnCheckedChangeListener(this);
            ASC2.setOnCheckedChangeListener(this);
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //based on the selected box i should enable or disable the column that's been selected
            switch(buttonView.getId())
            {
                case R.id.AucunSC1:
                    //this "if statement" makes sure that OnAucunCheckedSC1 get called in the right time
                    if (buttonView.isChecked()) {
                        /*
                        the current checkbox is checked.
                        means it's true
                        the "AucunSc1State" should be false (the previous state)
                        and on whatever reason AucunSc1State is true don do anything
                        */
                        if ( !Constants.AucunSc1State){
                            CheckedR.OnAucunCheckedSC1(buttonView.isChecked());
                            Constants.AucunSc1State = buttonView.isChecked();
                        }
                    }
                    else {
                        if (Constants.AucunSc1State){
                            CheckedR.OnAucunCheckedSC1(buttonView.isChecked());
                            Constants.AucunSc1State = buttonView.isChecked();
                        }
                    }

                    break;
                case R.id.AucunSC2:
                    if (buttonView.isChecked())
                    {
                        //same thing as the first case.
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


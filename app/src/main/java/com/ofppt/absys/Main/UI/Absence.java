package com.ofppt.absys.Main.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ofppt.absys.Main.Adapters.RecyclerAdapter;
import com.ofppt.absys.Main.Constants.Constants;
import com.ofppt.absys.Main.Fragements.ConfirmationDialog;
import com.ofppt.absys.Main.Interfaces.IOnChecked;
import com.ofppt.absys.Main.Interfaces.IOnInputListenner;
import com.ofppt.absys.Main.Models.ABSENCES;
import com.ofppt.absys.Main.Models.FORMATEURS;
import com.ofppt.absys.Main.Models.STAGIAIRES;
import com.ofppt.absys.R;

import java.util.Calendar;

public class Absence extends AppCompatActivity implements IOnInputListenner , IOnChecked {

    private RecyclerView Rview;
    private FloatingActionButton fabValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));

            // bar.setDisplayShowTitleEnabled();

        }
        ActiveAndroid.initialize(this);
        Calendar cal = Calendar.getInstance();
        Constants.AM_PM = cal.get(Calendar.AM_PM);
        Rview = findViewById(R.id.recycler_view);
        fabValidate =findViewById(R.id.fabValidate);

        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });


        RecyclerAdapter adapter = new RecyclerAdapter(STAGIAIRES.getAll(),this,this);
        DividerItemDecoration divider = new DividerItemDecoration(this,new LinearLayoutManager(this).getOrientation());
        Rview.setAdapter(adapter);
        Rview.setLayoutManager(new LinearLayoutManager(this));
        Rview.addItemDecoration(divider);
    }
    public void ShowDialog() {
        ConfirmationDialog confirmD = new ConfirmationDialog();
        confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");


    }
    @Override
    public void SendFormateur(FORMATEURS formateur) {
        String S1;
        String S2 ;

        if (Constants.AM_PM==0)
        {
            S1="S1";
            S2="S2";
        }else {
            S1="S3";
            S2="S4";
        }
        for (STAGIAIRES stgS: Constants.CheckedStudentSC1) {
            new ABSENCES(stgS,formateur,Calendar.getInstance().getTime(),S1).save();

        }
        for (STAGIAIRES stgS: Constants.CheckedStudentSC2) {
            new ABSENCES(stgS,formateur,Calendar.getInstance().getTime(),S2).save();

        }

        Toast.makeText(this, formateur._Nom+"  " +formateur._Prenom,Toast.LENGTH_LONG).show();


    }


    @Override
    public void onClick(int position) {

    }

    @Override
    public void OnChecked(int position) {

    }
}

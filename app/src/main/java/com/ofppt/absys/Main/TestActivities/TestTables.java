package com.ofppt.absys.Main.TestActivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.Main.Models.FORMATEURS;
import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.Main.Models.STAGIAIRES;
import com.ofppt.absys.Main.UI.ModFiliere;
import com.ofppt.absys.R;

public class TestTables extends AppCompatActivity implements View.OnClickListener{
    TextView txtformateur;
    TextView txtgroup;
    TextView txtfilier;
    TextView Comule;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tables);
        txtformateur= findViewById(R.id.txtformateur);
        txtgroup= findViewById(R.id.txtgroup);
        txtfilier= findViewById(R.id.txtfilier);
        Comule = findViewById(R.id.txtfAbcenceComule);
        txtformateur.setText(String.valueOf(FORMATEURS.getAll().size()));
        txtgroup.setText(String.valueOf(GROUPES.getAll().size()));
        txtfilier.setText(String.valueOf(STAGIAIRES.getbyGroup(GROUPES.getbycodeGroup("TDI202")).size()));
        double I=0;
        for (STAGIAIRES stg : STAGIAIRES.getbyGroup(GROUPES.getbycodeGroup("TDI202"))) {
            I+=stg._Absence_Cumulee;
        }
        Comule.setText(String.valueOf(I));
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            // bar.setDisplayShowTitleEnabled();
            bar.setTitle("Parametre");

        }

        MaterialButton xtrem = findViewById(R.id.Test_Option_Btn);
        createBottomSheetDialog();
        xtrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomSheetDialogFragment bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
//
//                //show it
//                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                bottomSheetDialog.show();
            }
        });
    }
    BottomSheetDialog bottomSheetDialog;
    MaterialButton xrem;
    MaterialButton xem ;
    private void createBottomSheetDialog() {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_modal, null);
            xrem = view.findViewById(R.id.Button_1);
            xem = view.findViewById(R.id.Button_2);

            xrem.setOnClickListener(this);
            xem.setOnClickListener(this);
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_1:
//                Comule.setText("btn1");
                Intent intent = new Intent(this, ModFiliere.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
                break;
            case R.id.Button_2:
                Log.e("xxx","Hello Bitch button 2");
                Comule.setText("btn2");

                bottomSheetDialog.dismiss();
                break;
        }
    }
}

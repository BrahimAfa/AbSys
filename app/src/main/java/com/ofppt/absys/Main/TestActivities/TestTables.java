package com.ofppt.absys.Main.TestActivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.util.Log;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ofppt.absys.Main.Models.FORMATEURS;
import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.Main.Models.STAGIAIRES;
import com.ofppt.absys.Main.UI.ModFiliere;
import com.ofppt.absys.Main.Utils.AESCrypt;
import com.ofppt.absys.R;
public class TestTables extends AppCompatActivity implements View.OnClickListener{
    TextView txtformateur;
    TextView txtgroup;
    TextView txtfilier;
    TextView Comule;
    TextInputLayout gg;
    AutoCompleteTextView FormateurSpinner;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tables);
        ActiveAndroid.initialize(this);
        txtformateur= findViewById(R.id.txtformateur);
        txtgroup= findViewById(R.id.txtgroup);
        txtfilier= findViewById(R.id.txtfilier);
        Comule = findViewById(R.id.txtfAbcenceComule);
        try {
            txtformateur.setText(AESCrypt.decrypt(FORMATEURS.getAll().get(1)._Crypto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtgroup.setText(FORMATEURS.getAll().get(1)._Crypto);
   //     FormateurSpinner = findViewById(R.id.FormateurSpinner);
        txtfilier.setText(String.valueOf(STAGIAIRES.getbyGroup(GROUPES.getbycodeGroup("TDI202")).size()));
        double I=0;
        for (STAGIAIRES stg : STAGIAIRES.getbyGroup(GROUPES.getbycodeGroup("TDI202"))) {
            I+=stg._Absence_Cumulee;
        }
        Comule.setText(String.valueOf(I));
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true) ;
            // bar.setDisplyShowTitleEnabled();
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

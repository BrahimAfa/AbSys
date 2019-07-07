package com.ofppt.absys.Main.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.util.Log;
import com.dd.morphingbutton.MorphingButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.R;

import java.util.List;
import java.util.Objects;

public class AddGroup extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    Spinner FilierSp;
    TextInputLayout txtCodeG;
    TextInputLayout txtAnnee;
    MorphingButton btnValider;
    private String CodeG;
    private String Annee;
    private FILIERES Filiere;
    private int mMorphCounter=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            // bar.setDisplayShowTitleEnabled();
            bar.setTitle("Group");

        }
        FilierSp = findViewById(R.id.Filier_spinner);
        txtCodeG = findViewById(R.id.CodeGr);
        txtAnnee = findViewById(R.id.AnneeGr);
        btnValider = findViewById(R.id.btnGr);
        List<FILIERES> Fill = new Select().from(FILIERES.class).execute();
        String[] CodeFF = new String[Fill.size()];
        for(int i=0;i<Fill.size();i++){
            CodeFF[i]= Fill.get(i)._CodeFiliere;
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(AddGroup.this,android.R.layout.simple_spinner_item, CodeFF);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FilierSp.setAdapter(adapter);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CodeG = Objects.requireNonNull(txtCodeG.getEditText(),"Matricule EditeText Is NUll").getText().toString();
                Annee = Objects.requireNonNull(txtAnnee.getEditText(),"First Name EditeText Is NUll").getText().toString();
                Filiere = FILIERES.getByCodeFiliere(Objects.requireNonNull(FilierSp,"First Name EditeText Is NUll").getSelectedItem().toString());

                Log.d("xxx", "onClick: "+Objects.requireNonNull(FilierSp,"First Name EditeText Is NUll").getSelectedItem().toString());
                AjouterGroup(btnValider);
            }
        });

    }
    private void AjouterGroup(final MorphingButton btnMorph) {
        if (mMorphCounter == 0) {
            mMorphCounter++;
            ClearFileds();
            morphToSquare(btnMorph, getResources().getInteger(R.integer.mb_animation));
        }
        else if (mMorphCounter == 1) {
            if (CodeInputValidate() & FnameInputValidate()) {
                morphToSuccess(btnMorph);
                txtCodeG.setError(null);
                txtAnnee.setError(null);

                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {

                        try {
                            new GROUPES(CodeG,Filiere,Integer.parseInt(Annee)).save();
                            mMorphCounter=0;

                            Toast.makeText(getApplicationContext(), "Group a Bien Ajouter", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(r, 1000);
            }
            else
            {
                morphToFailure(btnMorph, getResources().getInteger(R.integer.mb_animation));
                Handler handler= new Handler();
                Runnable r=new Runnable() {
                    public void run() {
                        morphToSquare(btnMorph, getResources().getInteger(R.integer.mb_animation));

                    }
                };
                handler.postDelayed(r, 1000);
            }
        }
    }

    private boolean FnameInputValidate(){
        if (CodeG.isEmpty() )
        {

            txtCodeG.setError("Input Field must not be Empty");
            return false;
        }
        else if (CodeG.length()>10)
        {
            txtCodeG.setError("Input Field must  be Less Than 30 Characters");
            return false;
        }
        txtCodeG.setError(null);

        return true;

    }

    private boolean CodeInputValidate(){
        if (Annee.isEmpty() )
        {

            txtAnnee.setError("Input Field must not be Empty");
            return false;
        }
        else if (Annee.length()>4)
        {
            txtAnnee.setError("Input Field must be Less Than 30 Characters");
            return false;
        }
        txtAnnee.setError(null);

        return true;

    }
    private void ClearFileds(){
        txtCodeG.getEditText().setText("");
        txtAnnee.getEditText().setText("");
        txtCodeG.requestFocus();
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius((int) getResources().getDimension(R.dimen.mb_corner_radius_2))
                .width((int) getResources().getDimension(R.dimen.mb_width_100))
                .height((int) getResources().getDimension(R.dimen.mb_height_56))
                .color(getResources().getColor((R.color.mb_blue)))
                .colorPressed(getResources().getColor(R.color.mb_blue_dark))
                .text(getString(R.string.valider));
        btnMorph.morph(square);
    }

    private void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(getResources().getInteger(R.integer.mb_animation))
                .cornerRadius((int) getResources().getDimension(R.dimen.mb_height_56))
                .width((int) getResources().getDimension(R.dimen.mb_height_56))
                .height((int) getResources().getDimension(R.dimen.mb_height_56))
                .color(getResources().getColor(R.color.mb_green))
                .colorPressed(getResources().getColor(R.color.mb_green_dark))
                .icon(R.drawable.ic_check);
        btnMorph.morph(circle);


    }

    private void morphToFailure(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius((int) getResources().getDimension(R.dimen.mb_height_56))
                .width((int) getResources().getDimension(R.dimen.mb_height_56))
                .height((int) getResources().getDimension(R.dimen.mb_height_56))
                .color(getResources().getColor(R.color.mb_red))
                .colorPressed(getResources().getColor(R.color.mb_red_dark))
                .icon(R.drawable.ic_close);
        btnMorph.morph(circle);
    }
}

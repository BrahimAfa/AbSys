package com.ofppt.absys.Main.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.Main.Models.FORMATEURS;
import com.ofppt.absys.Main.Utils.AESCrypt;
import com.ofppt.absys.R;

import java.util.Objects;

public class AddFiliere extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    TextInputLayout txtFNam;
    TextInputLayout txtCodeF;
    MorphingButton btnValider;
    private String Fname;
    private String CodeF;
    private int mMorphCounter=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filiere);

        txtCodeF = findViewById(R.id.CodeFil);
        txtFNam = findViewById(R.id.NomFil);
        btnValider = findViewById(R.id.btnFil);

        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            // bar.setDisplayShowTitleEnabled();
            bar.setTitle("Filiere");

        }

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CodeF = Objects.requireNonNull(txtCodeF.getEditText(),"Matricule EditeText Is NUll").getText().toString();
                // Log.d(TAG, "onClick: "+Matricule);
                Fname = Objects.requireNonNull(txtFNam.getEditText(),"First Name EditeText Is NUll").getText().toString();
                //Log.d(TAG, "onClick: "+Fname);
                AjouterFiliere(btnValider);
            }
        });

    }

    private void AjouterFiliere(final MorphingButton btnMorph) {
        if (mMorphCounter == 0) {
            mMorphCounter++;
            ClearFileds();
            morphToSquare(btnMorph, getResources().getInteger(R.integer.mb_animation));
        }
        else if (mMorphCounter == 1) {
            if (CodeInputValidate() & FnameInputValidate() ) {
                morphToSuccess(btnMorph);
                txtCodeF.setError(null);
                txtFNam.setError(null);

                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {

                        try {
                            new FILIERES(CodeF,Fname).save();
                            mMorphCounter=0;

                            Toast.makeText(getApplicationContext(), "Filiere a Bien Ajouter", Toast.LENGTH_LONG).show();
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
        if (Fname.isEmpty() )
        {

            txtFNam.setError("Input Field must not be Empty");
            return false;
        }
        else if (Fname.length()>30)
        {
            txtFNam.setError("Input Field must  be Less Than 30 Characters");
            return false;
        }
        txtFNam.setError(null);

        return true;

    }

    private boolean CodeInputValidate(){
        if (CodeF.isEmpty() )
        {

            txtCodeF.setError("Input Field must not be Empty");
            return false;
        }
        else if (CodeF.length()>30)
        {
            txtCodeF.setError("Input Field must be Less Than 30 Characters");
            return false;
        }
        txtCodeF.setError(null);

        return true;

    }
    private void ClearFileds(){
        txtCodeF.getEditText().setText("");
        txtFNam.getEditText().setText("");
        txtCodeF.requestFocus();
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

package com.ofppt.absys.Main.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ofppt.absys.Main.Models.FORMATEURS;
import com.ofppt.absys.Main.Utils.AESCrypt;
import com.ofppt.absys.R;

import java.util.Objects;

public class AddFormateur extends AppCompatActivity {
    //Widgets
    TextInputLayout txtCrypt;
    TextInputLayout txtFNam;
    TextInputLayout txtLNam;
    TextInputLayout txtMatricule;
    MorphingButton btnValider;

    //Vars
    private static final String TAG = "AddFormateur";
    private String Matricule;
    private String Fname;
    private String Lname;
    private String Crypt;
    private int mMorphCounter=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_formateur);
        //Initializing Widgets
        txtCrypt = findViewById(R.id.txtCrypt);
        txtFNam = findViewById(R.id.txtFName);
        txtLNam = findViewById(R.id.txtLName);
        txtMatricule = findViewById(R.id.txtMatricule);
        btnValider = findViewById(R.id.btnMorph);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Matricule = Objects.requireNonNull(txtMatricule.getEditText(),"Matricule EditeText Is NUll").getText().toString();
               // Log.d(TAG, "onClick: "+Matricule);
                Fname = Objects.requireNonNull(txtFNam.getEditText(),"First Name EditeText Is NUll").getText().toString();
                //Log.d(TAG, "onClick: "+Fname);
                Lname = Objects.requireNonNull(txtLNam.getEditText(),"Last Name EditeText Is NUll").getText().toString();
               // Log.d(TAG, "onClick: "+Lname);
                Crypt = Objects.requireNonNull(txtCrypt.getEditText(),"Crypte EditeText Is NUll").getText().toString();
               // Log.d(TAG, "onClick: "+Crypt);

                AjouterFormateur(btnValider);
            }
        });
    }

    private void AjouterFormateur(final MorphingButton btnMorph) {
        if (mMorphCounter == 0) {
            mMorphCounter++;
            ClearFileds();
            morphToSquare(btnMorph, getResources().getInteger(R.integer.mb_animation));
        }
        else if (mMorphCounter == 1) {
            if (MatriculeInputValidate() & LnameInputValidate() & FnameInputValidate() & CrypteInputValidate() ) {
                morphToSuccess(btnMorph);
                txtMatricule.setError(null);
                txtFNam.setError(null);
                txtLNam.setError(null);

                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {

                        try {
                            new FORMATEURS(Matricule, Lname, Fname, AESCrypt.encrypt(Crypt)).save();
                            mMorphCounter=0;

                            Toast.makeText(getApplicationContext(), "Formateur a Bien Ajouter", Toast.LENGTH_LONG).show();
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

    private boolean MatriculeInputValidate(){
        if (Matricule.isEmpty() )
        {
            txtMatricule.setError("Input Field must not be Empty");
            return false;
        }
        else if (Matricule.length()>10)
        {
            txtMatricule.setError("Input Field must  be Less Than 10 Characters");
            return false;
        }
        txtMatricule.setError(null);
        return true;

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

    private boolean LnameInputValidate(){
        if (Lname.isEmpty() )
        {

            txtLNam.setError("Input Field must not be Empty");
            return false;
        }
        else if (Lname.length()>30)
        {
            txtLNam.setError("Input Field must be Less Than 30 Characters");
            return false;
        }
        txtLNam.setError(null);

        return true;

    }

    private boolean CrypteInputValidate(){
        if (Crypt.isEmpty() )
        {

            txtCrypt.setError("Input Field must not be Empty");
            return false;
        }
        else if (Crypt.length()>50)
        {
            txtCrypt.setError("Input Field must  be Less Than 50 Characters");
            return false;
        }
        txtCrypt.setError(null);

        return true;

    }

    private void ClearFileds(){
        txtMatricule.getEditText().setText("");
        txtLNam.getEditText().setText("");
        txtFNam.getEditText().setText("");
        txtCrypt.getEditText().setText("");
        txtMatricule.requestFocus();


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

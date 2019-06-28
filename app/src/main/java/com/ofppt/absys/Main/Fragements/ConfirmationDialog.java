package com.ofppt.absys.Main.Fragements;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.dd.morphingbutton.MorphingButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ofppt.absys.Main.Interfaces.IOnInputListenner;
import com.ofppt.absys.Main.Models.FORMATEURS;
import com.ofppt.absys.Main.Utils.AESCrypt;
import com.ofppt.absys.R;

import java.util.Objects;

public class ConfirmationDialog extends DialogFragment {
    //widgets
    TextInputLayout txtCrypte;
    MorphingButton btnValider;
    ImageView imgClose;
    //Vars
    private String InputCrypte;
    private int mMorphCounter1=1;
    private FORMATEURS formateur;
    IOnInputListenner inputlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            inputlistener = (IOnInputListenner) context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString()+"Class Must be Impliment the IOnInpuListenner Interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.custom_dialog_box,container,false);
        txtCrypte = view.findViewById(R.id.txtCrypte);
        btnValider = view.findViewById(R.id.btnMorph);
        imgClose = view.findViewById(R.id.FragClosebtn);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Morph Actions Here
                InputCrypte = Objects.requireNonNull(txtCrypte.getEditText(),"Crypte Text Is Null").getText().toString().trim();

                onMorphButton1Clicked(btnValider);
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Close Fragùment
                DismissFragment();
               // Objects.requireNonNull(getDialog(),"Getdialog (ConfirmationDialog Class)  Must Not be Null").dismiss();
            }
        });
        return view;

    }
    private void onMorphButton1Clicked(final MorphingButton btnMorph) {
        if (mMorphCounter1 == 0) {
            mMorphCounter1++;
            morphToSquare(btnMorph, getResources().getInteger(R.integer.mb_animation));
            txtCrypte.setError(null);
        } else if (mMorphCounter1 == 1) {
            mMorphCounter1 = 0;
            if (Validate_Crypte(btnMorph))
                if (IsFormateurExist())
                {
                    morphToSuccess(btnMorph);
                    Handler handler= new Handler();
                    Runnable r=new Runnable() {
                        public void run() {
                            inputlistener.SendFormateur(formateur);
                            DismissFragment();
                        }
                    };
                    handler.postDelayed(r, 1000);


                }else {
                    morphToFailure(btnMorph, getResources().getInteger(R.integer.mb_animation));
                    txtCrypte.setError("Acune Formateur avec cette Crypte!!!!");
                }


        }


    }
    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }
    private boolean IsFormateurExist() {
        String Crypte ="";
        try {
            Crypte = AESCrypt.encrypt(InputCrypte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        formateur = FORMATEURS.getbyCrypte(Crypte);

        return formateur != null;
    }

    private boolean Validate_Crypte(MorphingButton btnMorph){
        if (InputCrypte.isEmpty())
        {
            morphToFailure(btnMorph, getResources().getInteger(R.integer.mb_animation));

            txtCrypte.setError("Input Field must not be Empty");
            return false;
        }
        else if (InputCrypte.length()>50)
        {
            morphToFailure(btnMorph, getResources().getInteger(R.integer.mb_animation));

            txtCrypte.setError("Input Field must  be Less Than 50 Characters");
            return false;

        }
        return true;

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
private void DismissFragment(){
    Objects.requireNonNull(getDialog(),"Getdialog (ConfirmationDialog Class)  Must Not be Null").dismiss();

}
}

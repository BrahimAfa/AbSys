package com.ofppt.absys.Main.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.Main.Models.GroupEnrg;
import com.ofppt.absys.Main.Models.STAGIAIRES;
import com.ofppt.absys.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Absence extends AppCompatActivity implements IOnInputListenner , IOnChecked {

    TextView group;
    TextView fili;
    SpinnerDialog spinnerDialog;
    private RecyclerView Rview;
    private FloatingActionButton fabValidate;
    private EditText FormateurComplete;
    String ExtraCodeGoup;
    //Vars
   // List<STAGIAIRES> listG;
  //  ArrayList<String> FormateurNames;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuv0, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_Setting:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            // action with ID action_settings was selected
            default:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);
        //change actionbar color from SOLID to Gradient
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setTitle("Affectation d'Absence");
        }
        //init active android ORM
        ActiveAndroid.initialize(this);

        group = findViewById(R.id.txtGroup);
        fili = findViewById(R.id.txtFilier);
        FormateurComplete = findViewById(R.id.FormateurComplete);
        //get the current time (morning or evening ) to define which seance is it now (S1,S2 or S3,S4)
        Calendar cal = Calendar.getInstance();
        Constants.AM_PM = cal.get(Calendar.AM_PM);

        Rview = findViewById(R.id.recycler_view);
        fabValidate =findViewById(R.id.fabValidate);
        //Fetching Code Group and from intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //getString function may return null pointer reference
        ExtraCodeGoup = extras != null ? extras.getString("codeg") : null;
        String filiere = extras != null ? extras.getString("Filiere") : null;
        group.setText(ExtraCodeGoup);
        fili.setText(filiere);
        //Professor dialog box
        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        //IMPORTANT TRY TO FIGURE AWAY TO REMOVE THIS LISTG FROM GLOBAL VARIABLE
       // listG =
        InitializingRecyclerView();
        //Searchable spinner "Professors Spinner"
        spinnerDialog=new SpinnerDialog(Absence.this,(ArrayList<String>) FORMATEURS.getAllNmaes(),"Selectioner Formateur",R.style.DialogAnimations_SmileWindow,"FERMER");// With 	Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                Toast.makeText(getApplicationContext(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                FormateurComplete.setText(item );
                Constants.FOMATEUR = FORMATEURS.getAll().get(position);
            }
        });
        FormateurComplete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        spinnerDialog.showSpinerDialog();
    }
});
    }

    private void InitializingRecyclerView(){
        RecyclerAdapter adapter = new RecyclerAdapter(STAGIAIRES.getbyGroup(GROUPES.getbycodeGroup(ExtraCodeGoup)),this,this);
        DividerItemDecoration divider = new DividerItemDecoration(this,new LinearLayoutManager(this).getOrientation());
        Rview.setAdapter(adapter);
        Rview.setLayoutManager(new LinearLayoutManager(this));
        Rview.addItemDecoration(divider);
        Constants.CheckedStudentSC1.clear();
        Constants.CheckedStudentSC2.clear();
        Constants.AucunSc1State = false;
        Constants.AucunSc2State = false;
    }
    // this it should be removed because its the same one as above and only thing changed is the seance parameter which is useless
    //(check the RecyclerAdapter Constructor)
    private void InitializingRecyclerView(String Seance){
        RecyclerAdapter adapter = new RecyclerAdapter(STAGIAIRES.getbyGroup(GROUPES.getbycodeGroup(ExtraCodeGoup)),this,this,Seance);
        Rview.setAdapter(adapter);
        Constants.CheckedStudentSC1.clear();
        Constants.CheckedStudentSC2.clear();
    }

    public void ShowDialog() {
        //showing dialog confirmation based on the following conditions
        //no one is absent in both first and second seance
        if (Constants.AucunSc1State & Constants.AucunSc2State) {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        }
        //no one is absent in first seance and there is someone absent in the second seance
        else if (Constants.AucunSc1State & Constants.CheckedStudentSC2.size() > 0) {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        }
        //no one is absent in second seance and there is someone absent in the first seance
        else if (Constants.AucunSc2State & Constants.CheckedStudentSC1.size() > 0) {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        }
        //there is someone absent in both in both first and second seance
        else if (Constants.CheckedStudentSC1.size()>0 && Constants.CheckedStudentSC2.size()>0) {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        } else Toast.makeText(getApplicationContext(),"Aucune Stagaire Selectioner!!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void SendFormateur(FORMATEURS formateur) {
        //this function is been trigger by the dialog fragment if the Professor is been selected or found
        //this two variables used to determine which seance this absence is been submitted
        String S1;
        String S2 ;
        if (Constants.AM_PM==0) {
            S1=Constants.Seance1;
            S2=Constants.Seance2;
        }
        else {
            S1=Constants.Seance3;
            S2=Constants.Seance4;
        }
        ActiveAndroid.beginTransaction();
        try {
            //this iff statement is used to make sure that the absence is been affected ()
            /*
            * in normal cases you can leave the check boxes of the students unchecked (which means no one is absent)
            * but to make sure that the one who is using this app is doing his job he should select the Aucun checkbox(in FOOTER)
            * so that the while exporting absence data this group will appear as it's been visited but there is no absence
            */

            if (Constants.AucunSc1State) {
                //here instead of Constants.FOMATEUR i have to pass the formateur parameter but for testing reasons
                new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S1,Constants.FOMATEUR).save();
            }
            if ( Constants.CheckedStudentSC1.size()>0) {
                GroupEnrg G_Enrg = new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S1,Constants.FOMATEUR);
                G_Enrg.save();
                for (STAGIAIRES stgS: Constants.CheckedStudentSC1) {
                    ABSENCES Ab = new ABSENCES(stgS,Constants.FOMATEUR,Calendar.getInstance().getTime(),S1);
                    Ab.save();
                    //this function update the current absence for this student by adding 2.5H
                    stgS.UpdateAbsenceComule();
                }
            }
            if (Constants.AucunSc2State) {
                new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S2,Constants.FOMATEUR).save();
            }
            if (Constants.CheckedStudentSC2.size() > 0) {
                GroupEnrg G_Enrg = new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S2,Constants.FOMATEUR);
                G_Enrg.save();
                for (STAGIAIRES stgS : Constants.CheckedStudentSC2) {

                    new ABSENCES(stgS, Constants.FOMATEUR, Calendar.getInstance().getTime(), S2).save();
                    stgS.UpdateAbsenceComule();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        InitializingRecyclerView();
        Toast.makeText(this, "Absence Submitted by : MR."+formateur._Nom+"  " +formateur._Prenom,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitializingRecyclerView();
    }

    @Override
    public void OnAucunCheckedSC1(Boolean CheckState) {
        Toast.makeText(this,"Test Acune SC1 "+CheckState,Toast.LENGTH_SHORT).show();
        InitializingRecyclerView(Constants.Seance1);
    }

    @Override
    public void OnAucunCheckedSC2(Boolean CheckState) {
        Toast.makeText(this,"Test Acune SC2 "+CheckState,Toast.LENGTH_SHORT).show();
        InitializingRecyclerView(Constants.Seance2);
    }
}

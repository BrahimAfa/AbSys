package com.ofppt.absys.Main.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
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

public class Absence extends AppCompatActivity implements IOnInputListenner , IOnChecked {

    TextView group;
    TextView fili;
    private RecyclerView Rview;
    private FloatingActionButton fabValidate;
    private AutoCompleteTextView FormateurComplete;
    String ExtraCodeGoup;
    //Vars
    List<STAGIAIRES> listG;
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
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setTitle("Affectation d'Absence");

            // bar.setDisplayShowTitleEnabled();

        }
        group = findViewById(R.id.txtGroup);
        fili = findViewById(R.id.txtFilier);
        FormateurComplete = findViewById(R.id.FormateurComplete);

        ActiveAndroid.initialize(this);
        Calendar cal = Calendar.getInstance();
        Constants.AM_PM = cal.get(Calendar.AM_PM);
        Rview = findViewById(R.id.recycler_view);
        fabValidate =findViewById(R.id.fabValidate);
        //Fetching Code Group from intent

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        ExtraCodeGoup = extras.getString("codeg");
        String fil = extras.getString("Filiere");
        group.setText(ExtraCodeGoup);
        fili.setText(fil);
        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
//        RecyclerAdapter adapter = new RecyclerAdapter(STAGIAIRES.getAll(),this,this);
        listG = STAGIAIRES.getbyGroup(GROUPES.getbycodeGroup(ExtraCodeGoup));
        InitializingRecyclerView();
        FormateurComplete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FORMATEURS.getAllNmaes()));

        FormateurComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constants.FOMATEUR = FORMATEURS.getAll().get(position);
                Toast.makeText(getApplicationContext(),Constants.FOMATEUR._Nom+" " +Constants.FOMATEUR._Prenom+"Test",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void InitializingRecyclerView(){
        RecyclerAdapter adapter = new RecyclerAdapter(listG,this,this);
        DividerItemDecoration divider = new DividerItemDecoration(this,new LinearLayoutManager(this).getOrientation());
        Rview.setAdapter(adapter);
        Rview.setLayoutManager(new LinearLayoutManager(this));
        Rview.addItemDecoration(divider);
        Constants.CheckedStudentSC1.clear();
        Constants.CheckedStudentSC2.clear();
        Constants.AucunSc1State = false;
        Constants.AucunSc2State = false;
    }
    private void InitializingRecyclerView(String Seance,boolean checkState){
        RecyclerAdapter adapter = new RecyclerAdapter(listG,this,this,Seance,checkState);
      //  DividerItemDecoration divider = new DividerItemDecoration(this,new LinearLayoutManager(this).getOrientation());
        Rview.setAdapter(adapter);
   //     Rview.setLayoutManager(new LinearLayoutManager(this));
      //  Rview.addItemDecoration(divider);
        Constants.CheckedStudentSC1.clear();
        Constants.CheckedStudentSC2.clear();
    }
    public void ShowDialog() {
        if (Constants.AucunSc1State & Constants.AucunSc2State) {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        }
        else if (Constants.AucunSc1State & Constants.CheckedStudentSC2.size() > 0) {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        }
        else if (Constants.AucunSc2State & Constants.CheckedStudentSC1.size() > 0) {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        }
        else if (Constants.CheckedStudentSC1.size()!=0 | Constants.CheckedStudentSC2.size()!=0)
        {
            ConfirmationDialog confirmD = new ConfirmationDialog();
            confirmD.show(getSupportFragmentManager(),"Confirmation Dialog");
        }else Toast.makeText(getApplicationContext(),"Aucune Stagaire Selectioner!!",Toast.LENGTH_LONG).show();



    }
    @Override
    public void SendFormateur(FORMATEURS formateur) {
        String S1;
        String S2 ;

        if (Constants.AM_PM==0)
        {
            S1=Constants.Seance1;
            S2=Constants.Seance2;
        }else {
            S1=Constants.Seance3;
            S2=Constants.Seance4;
        }
        ActiveAndroid.beginTransaction();
        try {
            if (Constants.AucunSc1State) {
                new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S1,Constants.FOMATEUR).save();
            }
            if ( Constants.CheckedStudentSC1.size()>0) {
                for (STAGIAIRES stgS: Constants.CheckedStudentSC1) {
                    new ABSENCES(stgS,formateur,Calendar.getInstance().getTime(),S1).save();
                    stgS.UpdateAbsenceComule();
                }
                new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S1,Constants.FOMATEUR).save();

            }
            if (Constants.AucunSc2State) {
                new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S2,Constants.FOMATEUR).save();
            }
            if (Constants.CheckedStudentSC2.size() > 0) {

                for (STAGIAIRES stgS : Constants.CheckedStudentSC2) {
                    new ABSENCES(stgS, formateur, Calendar.getInstance().getTime(), S2).save();
                    stgS.UpdateAbsenceComule();
                    new GroupEnrg(GROUPES.getbycodeGroup(ExtraCodeGoup),Calendar.getInstance().getTime(),S2,Constants.FOMATEUR).save();

                }
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        Constants.CheckedStudentSC1.clear();
        Constants.CheckedStudentSC1.clear();
        Toast.makeText(this, formateur._Nom+"  " +formateur._Prenom,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitializingRecyclerView();
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void OnChecked(int position) {

    }

    @Override
    public void OnAucunCheckedSC1(Boolean CheckState) {
        Toast.makeText(this,"Test Acune SC1",Toast.LENGTH_SHORT).show();
        InitializingRecyclerView(Constants.Seance1,CheckState);
        Constants.CheckedStudentSC1.clear();

    }

    @Override
    public void OnAucunCheckedSC2(Boolean CheckState) {
        Toast.makeText(this,"Test Acune SC2",Toast.LENGTH_SHORT).show();
        InitializingRecyclerView(Constants.Seance2,CheckState);
        Constants.CheckedStudentSC2.clear();


    }
}

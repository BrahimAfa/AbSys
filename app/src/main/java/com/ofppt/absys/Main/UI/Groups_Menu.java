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
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.ofppt.absys.Main.Adapters.Filiere_Adapter;
import com.ofppt.absys.Main.Adapters.Group_Adapter;
import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.Main.Utils.SharedPreference;
import com.ofppt.absys.R;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class Groups_Menu extends AppCompatActivity {


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
    RecyclerView Rview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups__menu);
        SharedPreference SharedData = SharedPreference.getInstance(Groups_Menu.this);
        TextView year = findViewById(R.id.Yeartxtg);
        year.setText(SharedData.getData("year"));
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String Code = extras.getString("codef");
        String nom = extras.getString("nomf");
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            // bar.setDisplayShowTitleEnabled();
            actionBar.setTitle("Groups");

        }
        //Action bar name
        actionBar.setTitle("Groups");
        Rview = findViewById(R.id.recycler_group);
        Log.e("xxxxx",SharedData.getData("year"));
        Log.e("xxxxx",Code+" "+nom);
        String x = SharedData.getData("year");
        year.setText(x);
//        List<GROUPES> listG = new Select().from(GROUPES.class).where("Filieres = ?",Code).execute();
        List<GROUPES> listG = GROUPES.getAll();
        List<GROUPES> listd = new ArrayList<>();
        for(int i =0;i<listG.size();i++){
            if(listG.get(i)._filieres._CodeFiliere.equals(Code)){
                Log.e("xxxxx",listG.get(i)._CodeGroupe);
                listd.add(listG.get(i));

            }
        }
        Group_Adapter adapter = new Group_Adapter(listd);
        DividerItemDecoration divider = new DividerItemDecoration(this,new LinearLayoutManager(this).getOrientation());
        Rview.setAdapter(adapter);
        Rview.setLayoutManager(new LinearLayoutManager(this));
        Rview.addItemDecoration(divider);
    }
}

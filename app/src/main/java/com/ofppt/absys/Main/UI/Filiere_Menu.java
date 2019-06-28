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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ofppt.absys.Main.Adapters.Filiere_Adapter;
import com.ofppt.absys.Main.Adapters.RecyclerAdapter;
import com.ofppt.absys.Main.Models.STAGIAIRES;
import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.Main.Utils.SharedPreference;
import com.ofppt.absys.R;

public class Filiere_Menu extends AppCompatActivity {


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
        setContentView(R.layout.activity_filiere__menu);
        SharedPreference SharedData = SharedPreference.getInstance(Filiere_Menu.this);
        TextView year = findViewById(R.id.txtYearf);
        Rview = findViewById(R.id.recycler_filiere);
        Log.e("xxxxx",SharedData.getData("year"));
        String x = SharedData.getData("year");
        year.setText(x);
        //Action bar name
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Filiere");
        Filiere_Adapter adapter = new Filiere_Adapter(FILIERES.getAll());
        DividerItemDecoration divider = new DividerItemDecoration(this,new LinearLayoutManager(this).getOrientation());
        Rview.setAdapter(adapter);
        Rview.setLayoutManager(new LinearLayoutManager(this));
        Rview.addItemDecoration(divider);

    }
}
